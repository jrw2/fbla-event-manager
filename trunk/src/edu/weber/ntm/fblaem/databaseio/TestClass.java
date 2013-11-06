package edu.weber.ntm.fblaem.databaseio;

import java.sql.SQLException;
import java.util.List;

public class TestClass {
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		DatabaseConnection db;
		try {
			db = new DatabaseConnection();
/*
			List<Student> s = db.getAllStudents();
			s.contains(1);*/
			Student s = (Student) db.getByID(Student.class,1);
			System.out.println(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
