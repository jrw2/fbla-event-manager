package edu.weber.ntm.fblaem.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import edu.weber.ntm.fblaem.databaseio.*;

@WebServlet(
		name="EventRegistration", 
		urlPatterns={"/EventRegistration"}
)
public class EventRegistration extends HttpServlet{
	
	private static final long serialVersionUID = 3743123062179776667L;
	private static Session sf = HibernateUtil.getSessionFactory().openSession();
	private Teacher teacher;
	private Login login;
	private Transaction tx;
	
	public EventRegistration()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getUserPrincipal() == null)
		{
			request.getSession().removeAttribute("isActive");
			request.getSession().invalidate();
			response.sendRedirect("Login.jsp");
		}
		processRequest(request, response);
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getUserPrincipal() == null)
		{		
			request.getSession().removeAttribute("isActive");
			request.getSession().invalidate();
			response.sendRedirect("Login.jsp");
		}
		if(request.getParameter("errorMessage") == null){
			processSubmission(request, response);
		}
		
		processRequest(request, response);
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("text/html");
		
		try {
			
			tx = sf.beginTransaction();
			login = getUser(request.getRemoteUser());
			
//			GET DATA TO PASS TO PAGE
			teacher = login.getTeacher();
			request.setAttribute("teacher", teacher);
			request.setAttribute("school", teacher.getSchool());			
			request.setAttribute("events", getAllEvents());
			request.setAttribute("errorValue", request.getAttribute("errorMessage"));
			
			tx.commit();
			
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
		
		String pageAction = request.getParameter("pageAction");
		Transaction  tx = sf.beginTransaction();
		switch (pageAction) {
		
			case "addTeam":
				System.out.println("Adding Team");
				Team newTeam = new Team(); 
				newTeam.setName(request.getParameter("teamName"));
				newTeam.setCreatedDate(new Date());
				newTeam.setEventInstanceID(Integer.parseInt(request.getParameter("eventInstanceId")));
				EventInstance newEventInstance = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
//				newTeam.setEventInstance(newEventInstance); // need to fill in eventInstance data.
				newEventInstance.getTeams().add(newTeam);
				
				sf.saveOrUpdate(newTeam);
//				sf.update(newEventInstance);
				break;

			case "removeTeam":
				System.out.println("Removing Team");
				Team removeTeam = (Team) sf.load(Team.class, new Integer(request.getParameter("teamId")));
				EventInstance removeTeamReference = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
				removeTeamReference.getTeams().remove(removeTeam);
				sf.saveOrUpdate(removeTeamReference);
				sf.delete(removeTeam);
				break;		
				
			case "removeStudentFromTeam":
				System.out.println("Removing Student");
				Team removeStudentFromTeam = (Team) sf.load(Team.class, new Integer(request.getParameter("teamId")));
				Student student = new Student(new Integer(request.getParameter("studentId")));
				StudentTeamId studentTeamId = new StudentTeamId(new Integer(request.getParameter("studentId")), new Integer(request.getParameter("teamId")));
				
				StudentTeam studentTeam = (StudentTeam) sf.load(StudentTeam.class, studentTeamId);
				EventInstance removeFromInstance = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
				
				removeStudentFromTeam.getstudentTeams().remove(studentTeam);
				
				if(removeStudentFromTeam.getstudentTeams().size() == 0){
					sf.delete(studentTeam);
					removeFromInstance.getTeams().remove(removeStudentFromTeam);
					sf.saveOrUpdate(removeFromInstance);
					sf.delete(removeStudentFromTeam);
				} else {
					
					sf.delete(studentTeam);
					
				}
				
				break;						
				
			case "registerStudent":
				System.out.println("Registering Student");
				login = (Login) sf.get(Login.class, 1);
				teacher = login.getTeacher();
				Team addStudentTeam = (Team) sf.load(Team.class, new Integer(request.getParameter("teamId")));
				School school = (School) sf.load(School.class, teacher.getSchool().getId());
				Student addStudent = new Student(school, request.getParameter("firstName"), request.getParameter("lastName"));
//				addStudent = (Student) sf.load(Student.class, new Integer(request.getParameter("teamId")));
				Integer studentId = new Integer((Integer)sf.save(addStudent));
				StudentTeamId newStudentTeamId = new StudentTeamId(studentId, new Integer(request.getParameter("teamId")));
				StudentTeam  newStudentTeam = new StudentTeam(newStudentTeamId, addStudentTeam, addStudent);
				addStudentTeam.getstudentTeams().add(newStudentTeam);
				
				sf.saveOrUpdate(newStudentTeam);
				
				break;
				
			default:
				break;
			
		}
		
		tx.commit();		
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
	
}
