package edu.weber.ntm.fblaem.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.weber.ntm.fblaem.databaseio.*;

@WebServlet(name="EventRegistration", 
urlPatterns={"/EventRegistration"})
public class EventRegistration extends HttpServlet{
	
	private static final long serialVersionUID = 3743123062179776667L;
	public static final SessionFactory sessionFactory = getSessionFactory();
	
	public EventRegistration()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		Transaction tx = null;
		
//		try{
//			factory = new Configuration().configure().buildSessionFactory();
//			
//		}catch (Throwable ex) { 
//			  System.err.println("Failed to create sessionFactory object." + ex);
//		     throw new ExceptionInInitializerError(ex); 
//		}
		
//		Session session = factory.openSession();
		try {
			
			// set this up to be created during login, and then use the same sessionFactory throughout. (do in loginManagement)
			tx = sessionFactory.getCurrentSession().beginTransaction();
			
			//	GET DATA TO PASS TO PAGE
			Login login = (Login) sessionFactory.getCurrentSession().get(Login.class, 1);
			
			Teacher teacher = login.getTeacher();
			List<School> school = DatabaseConnection.getSchoolWithStudents(teacher.getSchool().getId());		
			List<Event> events = DatabaseConnection.getAllEvents();
//			HashMap<Integer, Event> eventTypes = new HashMap<Integer, Event>();
//			
//			for (Event event : events) {
//				eventTypes.put(event.getId(), event);
//			}
			// SET DATA FOR PAGE
			request.setAttribute("school", school.get(0));
			request.setAttribute("teacher", teacher);
			request.setAttribute("events", events);
//			request.setAttribute("eventTypeMap", eventTypes);
			
			// eventInstance everything tied too. event is master event

			// FORWARD TO PAGE
			RequestDispatcher rd = request.getRequestDispatcher("eventRegistration.jsp");
			rd.forward(request, response);
			
			tx.commit();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// process updates and forward to servlet for display using doGet()
		// LOGIC HERE FOR SUBMISSION OF NEW DATA
		response.setContentType("text/html");
		RequestDispatcher rd = request.getRequestDispatcher("EventRegistration");
	
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
