package edu.weber.ntm.fblaem.servlets;

import java.io.IOException;
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
	private static SessionFactory factory; 
	
	public EventRegistration()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		Transaction tx = null;
		
		try{
			factory = new Configuration().configure().buildSessionFactory();
			
		}catch (Throwable ex) { 
			  System.err.println("Failed to create sessionFactory object." + ex);
		     throw new ExceptionInInitializerError(ex); 
		}
		
		Session session = factory.openSession();
		
		try {
			
	        tx = session.beginTransaction();
			
			//	GET DATA TO PASS TO PAGE
			Login login = (Login) factory.getCurrentSession().get(Login.class, 1);
			Teacher teacher = login.getTeacher();
			List<School> school = DatabaseConnection.getSchoolWithStudents(teacher.getSchool().getId());		
			List<Event> events = DatabaseConnection.getAllEvents();
			
			// SET DATA FOR PAGE
			request.setAttribute("school", school.get(0));
			request.setAttribute("teacher", teacher);
			request.setAttribute("events", events);

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

}
