package edu.weber.ntm.fblaem.databaseio;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.mail.Session;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

import edu.weber.ntm.fblaem.servlets.LoginManagement;

public class TestClass {
	@SuppressWarnings("unused")

	public static final SessionFactory sessionFactory = getSessionFactory();
	public static void main(String[] args)
	{
		DatabaseConnection db;
		try {
			/*
			Login l = (Login)db.getByID(Login.class, 1);
			Teacher t = l.getTeacher();
			School s = t.getSchool();
			*/
			/*
			List<Student> s = db.getAllStudents();
			s.contains(1);
			Student s1 = (Student) db.getByID(Student.class,1);
			s1.setFirstName("Preston");
			db.saveOrUpdate(s1);
			List<Student> students = db.getAllStudents();
			Teacher t = db.getTeacherWithSchoolAndStudents(1);
			Student firstStudentInSet = (Student) t.getSchool().getStudents().iterator().next();
			/*
			
			/*s = new Student();
			s.setFirstName("asdf");
			s.setLastName("asdf1");
			s.setSchool((School) db.getByID(School.class, 1));
			db.saveOrUpdate(s);*/
			

			
		
			PreparedStatement ps = ((SessionFactoryImplementor)sessionFactory).getConnectionProvider()
                    .getConnection().prepareStatement("{call FBLAEM_Reset}");
			ps.execute();
			System.out.println("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	protected static SessionFactory getSessionFactory() {
		try {
			Configuration cfg = new Configuration();
			cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			sf.openSession();
			return sf;
			//return (SessionFactory) new InitialContext().lookup("SessionFactory");*
		} catch (Exception e) {
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
}
