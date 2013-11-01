package edu.weber.ntm.fblaem.databaseio;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.weber.ntm.fblaem.entities.Event;
import edu.weber.ntm.fblaem.entities.Student;

public class DatabaseConnection {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql:orthanc.jrw.org";
	static final String USER = "username";
	static final String PASS = "password";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	public DatabaseConnection() throws SQLException
	{
	}
	private ResultSet executeQuery(String Query) throws SQLException
	{
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			return stmt.executeQuery(Query);
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		finally{
			conn.close();
			stmt.close();
			rs.close();
		}
	}
	public List<Student> getStudents(String Query) throws SQLException
	{
		try{
			List<Student> students = new ArrayList<Student>();
			rs = this.executeQuery(Query);
			while(rs.next())
			{
				Student student = new Student(rs.getInt("id"), rs.getInt("SchooldID"), 
						rs.getString("FirstName"), rs.getString("LastName"), 
						 rs.getString("MiddleName"), rs.getDate("CreateDate"));
				students.add(student);
			}
			
			return students;
		}
		finally
		{
			rs.close();
		}
	}
	public List<Event> getEvents(String Query)
	{
		try{
			
		
			List<Event> events = new ArrayList<Event>();
			rs = this.executeQuery(Query);
			while(rs.next())
			{
				Event event = new Event(rs.getInt("id"), rs.getString("Name"),rs.getInt("EventTypeID"), rs.get)
			}
			/*	private int ID = -1;
	private String Name;
	private int EventTypeID;
	private int MinTeamSize;
	private int MaxTeamSize;
	private int MaxEntriesPerScool;
	private Date CreatedDate;
	private String Details;*/
			return null;
		}
		finally{
			rs.close();
		}
	}
	
	
}
