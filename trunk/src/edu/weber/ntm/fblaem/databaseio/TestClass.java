package edu.weber.ntm.fblaem.databaseio;

import java.sql.SQLException;
import java.util.List;

import javax.mail.Session;

public class TestClass {
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		DatabaseConnection db;
		try {
			db = new DatabaseConnection();
			Login l = (Login)db.getByID(Login.class, 1);
			Teacher t = l.getTeacher();
			School s = t.getSchool();
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
			
			
			
			System.out.println("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
