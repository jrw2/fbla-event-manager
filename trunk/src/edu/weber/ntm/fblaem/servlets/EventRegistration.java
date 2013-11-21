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

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.weber.ntm.fblaem.databaseio.*;

@WebServlet(name="EventRegistration", 
urlPatterns={"/EventRegistration"})
public class EventRegistration extends HttpServlet{
	
	private static final long serialVersionUID = 3743123062179776667L;
	private static Session sf = HibernateUtil.getSessionFactory().openSession();
	
	public EventRegistration()
	{
		super();
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("text/html");
		
		try {
			
			Transaction  tx = sf.beginTransaction();
			
			//	GET DATA TO PASS TO PAGE
			Login login = (Login) sf.get(Login.class, 1);
			
			Teacher teacher = login.getTeacher();
			request.setAttribute("teacher", teacher);
			request.setAttribute("school", getSchoolWithStudents(teacher.getSchool().getId()));			
			request.setAttribute("events", getAllEvents());
			request.setAttribute("errorValue", request.getAttribute("errorMessage"));
			
			tx.commit();
			
//			request.setAttribute("studentTeams", getStudentTeams());
			
			/*
			Team - id, eventInstanceId
			StudentTeam - studentId, teamId
			EventInstance - id, eventId
			Event has information.

			1. Get Events
			2. Loop through events
			3. get EventInstance where eventId = event
			4. getTeams using eventInstanceId
			5. getStudentTeam using teamId
			*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		RequestDispatcher rd = request.getRequestDispatcher("eventRegistration.jsp");
		
		try {
			
			rd.forward(request, response);
			
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void processSubmission(HttpServletRequest request, HttpServletResponse response){
		
		// submission logic
		
	}
	
	protected School getSchoolWithStudents(int schoolId){
		
		@SuppressWarnings("unchecked")
		List<School> school = (List<School>) sf.createQuery("select s from School s inner join fetch s.students ss where s.id = " + schoolId).list();
		
		return school.get(0);
		
		
	}
	
	protected List<Event> getAllEvents(){
		Query query = sf.createQuery("from Event e join fetch e.eventInstances ei");
		return query.list();
	}
	
	protected List<Team> getStudentTeams(){
		Query query = sf.createQuery("from Team s join fetch s.studentTeams t");
		return query.list();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processRequest(request, response);
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("errorMessage") == null){
			processSubmission(request, response);
		}
		
		processRequest(request, response);
		
	}


}
