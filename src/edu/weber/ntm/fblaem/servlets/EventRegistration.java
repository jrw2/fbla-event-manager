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

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.weber.ntm.fblaem.databaseio.*;

@WebServlet(name="EventRegistration", 
urlPatterns={"/EventRegistration"})
public class EventRegistration extends HttpServlet{
	private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	
	private static final long serialVersionUID = 3743123062179776667L;
	
	public EventRegistration()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		try {
			
			DatabaseConnection db = new DatabaseConnection();
			Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
			
			// LOGIC HERE FOR SUBMISSION OF NEW DATA
				
			//	GET DATA TO PASS TO PAGE
			Login login = (Login) sessionFactory.getCurrentSession().get(Login.class, 1);
			Teacher teacher = login.getTeacher();
			List<School> school = db.getSchoolWithStudents(teacher.getSchool().getId());		
			List<Event> events = db.getAllEvents();
			
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
		response.setContentType("text/html");
		RequestDispatcher rd = request.getRequestDispatcher("EventRegistration");
	
	}

}
