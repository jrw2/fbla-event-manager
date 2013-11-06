package edu.weber.ntm.fblaem.databaseio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DatabaseConnection {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql:orthanc.jrw.org";
	static final String USER = "username";
	static final String PASS = "password";
	private final SessionFactory sessionFactory = getSessionFactory();
	
	private static final Log log = LogFactory.getLog(DatabaseConnection.class);
	/*
	private StudentHome studentHome = new StudentHome(sessionFactory);
	private SchoolHome schoolHome = new SchoolHome(sessionFactory);
	private EventHome eventHome = new EventHome(sessionFactory);
	private EventInstanceHome eventInstanceHome = new EventInstanceHome(sessionFactory);
	private EventTypeHome eventTypeHome = new EventTypeHome(sessionFactory);
	private LoginHome loginHome = new LoginHome(sessionFactory);
	private RoleHome roleHome = new RoleHome(sessionFactory);
	private StudentEventTeamHome studentEventTeamHome = new StudentEventTeamHome(sessionFactory);
	private TeacherHome teacherHome = new TeacherHome(sessionFactory);
	private TeamHome teamHome = new TeamHome(sessionFactory);
	*/
	protected SessionFactory getSessionFactory() {
		try {
			Configuration cfg = new Configuration();
			cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			sf.openSession();
			return sf;
			//return (SessionFactory) new InitialContext().lookup("SessionFactory");*
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
	public List<Event> getAllEvents()
	{
		Transaction  tx = sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession().createQuery("from Event e join e.eventInstances ei");
		List<Event> events = query.list();
		tx.commit();
		return events;
	}
	public List<Student> getAllStudents()
	{
		Transaction  tx = sessionFactory.getCurrentSession().beginTransaction();
		List<Student> students = sessionFactory.getCurrentSession().createQuery("from Student s inner join s.school ss").list();
		tx.commit();
		return students;
	}
	public Object getByID(int id, Class<?> cls)
	{
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		try
		{
			return sessionFactory.getCurrentSession().get(cls.getCanonicalName(), id);
		}
		finally
		{
			tx.commit();
		}		
	}
	public List<School> getAllSchools()
	{
		Transaction  tx = sessionFactory.getCurrentSession().beginTransaction();
		List<School> schools = sessionFactory.getCurrentSession().createQuery("from School").list();
		tx.commit();
		return schools;
	}
	public School getSchoolWithStudents(int id)
	{
		Transaction  tx = sessionFactory.getCurrentSession().beginTransaction();
		List<School> school = sessionFactory.getCurrentSession().createQuery("from School s inner join s.students ss where s.id = " + id).list();
		tx.commit();
		return (School) school.toArray()[0];
	}
	public Teacher getTeacherWithSchoolAndStudents(int id)
	{
		Transaction  tx = sessionFactory.getCurrentSession().beginTransaction();
		List<Teacher> teacher = sessionFactory.getCurrentSession()
				.createQuery("from Teacher t inner join t.school s inner join s.students ss where t.id = " + id).list();
		tx.commit();
		return (Teacher) teacher.toArray()[0];
	}
	
	
	
	/*public static void main(String[] args)
	{
		/*School school = new School();
		school.setName("Test School3");
		school.setPhone("1111111111");
		school.setState("UT");
		school.setStreetAddress("123 fake st");
		school.setZip("84015");
		school.setCity("Blah");
		SchoolHome sh = new SchoolHome();
		sh.attachDirty(school);
		
		
		School school = schoolHome.findById(1);
		Student studentEx = new Student();
		studentEx.setSchool(school);
		List<Student> students = sth.findByExample(studentEx);
		//Student student = (Student) sth.findByExample(instance)
		
	}*/
	public DatabaseConnection() throws SQLException
	{
	}
	/*
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
	}*/
	/*
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
	}*/
	/*public List<Event> getEvents(String Query)
	{
		try{
			
		
			List<Event> events = new ArrayList<Event>();
			rs = this.executeQuery(Query);
			while(rs.next())
			{
				//Event event = new Event(rs.getInt("id"), rs.getString("Name"),rs.getInt("EventTypeID"), rs.get)
			}
				private int ID = -1;
	private String Name;
	private int EventTypeID;
	private int MinTeamSize;
	private int MaxTeamSize;
	private int MaxEntriesPerScool;
	private Date CreatedDate;
	private String Details;
			return null;
		}
		finally{
			rs.close();
		}
	}*/
	
	
}