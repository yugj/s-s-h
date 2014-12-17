package cn.yugj.hibernate.persistence;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "person")
public class Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name  = "code")
	private String code;
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createtime;
	
	public String getCode() {
		return code;
	}
	public Calendar getCreatetime() {
		return createtime;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setCreatetime(Calendar createtime) {
		this.createtime = createtime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}

}
