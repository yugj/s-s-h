package cn.yugj.hibernate;

import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.yugj.hibernate.persistence.Person;
import cn.yugj.hibernate.util.HibernateUtil;

public class PersistenceTest {
	
	private SessionFactory sf = null;
	
	@Before
	public void before() { 
		System.out.println("before..");
		sf = HibernateUtil.getSessionFactory();
	}
	
	@After
	public void after() { 
		System.out.println("after..");
		if(sf != null && !sf.isClosed()) { 
			sf.close();
			sf = null;
			System.out.println("sf closed");
		}
	}
	
	/**
	 * persist save在事物提交前就会得到id
	 * @throws Exception
	 */
	@Test
	public void persist() throws Exception { 
		Session s = sf.getCurrentSession();
		Person p = new Person();
		p.setName("test");
		p.setCode("code");
		p.setCreatetime(Calendar.getInstance());
		
		cn.yugj.hibernate.persistence.Test t = new cn.yugj.hibernate.persistence.Test();
		t.setName("test");
		
		s.beginTransaction();
		s.persist(t);
		System.out.println("t.id:" + t.getId());
		s.save(p);
		System.out.println("p.id:" + p.getId());
		Thread.sleep(10000);
		if(true)
			throw new Exception("throw ex");
		s.getTransaction().commit();
	}
	
	/**
	 * 用到对象时才加载
	 */
	@Test
	public void testLoad() { 
		Session s = sf.getCurrentSession();
		s.beginTransaction();
		Person p = (Person) s.load(Person.class, 1);
		/*System.out.println(p.getId());
		System.out.println(p.getName());*/
		s.getTransaction().commit();
	}
	
	/**
	 * 立即加载对象
	 * 和load一样，都是先加载缓存数据，没有才发SQL语句查找
	 */
	@Test
	public void testGet() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Person p = (Person) session.get(Person.class, 1);
        session.clear();
        Person p2 = (Person) session.get(Person.class, 1);
        System.out.println(p.getId());
        System.out.println(p.getName());
        session.getTransaction().commit();
    }
	
	
	/**
	 * 更新一个存在id的对象，没id的对象更更新报错
	 * 当对象没更改，update不发sql（save同）
	 * update person set code=?, createtime=?, name=? where id=?
	 */
	@Test
	public void testUpdate() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        /*Person p = (Person) session.get(Person.class, 1);
        p.setName("nn");*/
        Person p = new Person();
        p.setId(4);
        p.setName("testUpdate");
        p.setCode("testUpdate");
        p.setCreatetime(Calendar.getInstance());
        session.update(p);
        session.getTransaction().commit();
    }
	
	/**
	 * 对象存在id，进行Update操作
	 * 对象不存在id，进行Save操作
	 */
	@Test
	public void testSaveOrUpdate() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        /*Person p = (Person) session.get(Person.class, 1);
        p.setName("nn");*/
        Person p = new Person();
        //p.setId(100);
        p.setName("testSaveOrUpdate");
        p.setCode("testSaveOrUpdate");
        p.setCreatetime(Calendar.getInstance());
        session.saveOrUpdate(p);
        session.getTransaction().commit();
    }

	@Test
    public void testFlush() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Person t = (Person)session.load(Person.class , 1);
        t.setName( "tttt");
         //强制同步缓存和数据库内容
        session.flush();
        t.setName( "ttttt");
        session.getTransaction().commit();
         //session.close();
    }
	
	/**
	 * 当上下文里面没session则创建session，有则不创建
	 * getCurrentSession commit后自动关闭
	 */
	@Test
	public void testGetCurrentSession() { 
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		session.getTransaction().commit();
		System.out.println("currentSession:" + session.isOpen());
	}
	
	/**
	 *    永远打开新的session
	 * openSession需手动关闭session
	 * 并设置；current_session_context_class
	 */
	@Test
	public void testOpenSession() { 
		Session session = sf.openSession();
		session.beginTransaction();
		session.getTransaction().commit();
		System.out.println("openSession:" + session.isOpen());
	}

}
