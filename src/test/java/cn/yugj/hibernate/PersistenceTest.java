package cn.yugj.hibernate;

import java.util.Calendar;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.yugj.hibernate.persistence.Person;

public class PersistenceTest {
	
	@Test
	public void persist() { 
		SessionFactory sf = new Configuration().configure().buildSessionFactory(
			    new StandardServiceRegistryBuilder().build() );
		
		Person p = new Person();
		p.setName("test");
		p.setCode("code");
		p.setCreatetime(Calendar.getInstance());
		sf.openSession().persist(p);
		sf.close();
		
		//test
	}
}
