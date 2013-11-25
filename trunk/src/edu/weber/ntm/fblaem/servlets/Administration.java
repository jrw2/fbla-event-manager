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

import edu.weber.ntm.fblaem.databaseio.Event;
import edu.weber.ntm.fblaem.databaseio.EventInstance;
import edu.weber.ntm.fblaem.databaseio.HibernateUtil;
import edu.weber.ntm.fblaem.databaseio.Login;
import edu.weber.ntm.fblaem.databaseio.School;
import edu.weber.ntm.fblaem.databaseio.Student;
import edu.weber.ntm.fblaem.databaseio.StudentTeam;
import edu.weber.ntm.fblaem.databaseio.StudentTeamId;
import edu.weber.ntm.fblaem.databaseio.Teacher;
import edu.weber.ntm.fblaem.databaseio.Team;

@WebServlet(
		name="Administration", 
		urlPatterns={"/Administration"}
)
public class Administration extends HttpServlet{
	
	private static final long serialVersionUID = 3743123062179776667L;
	private static Session sf = HibernateUtil.getSessionFactory().openSession();
	private Teacher teacher;
	private Login login;
	private Transaction tx;
	
	public Administration()
	{
		super();
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
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("text/html");
		
		try {
			
			tx = sf.beginTransaction();
			login = (Login) sf.get(Login.class, 1);
			
//			GET DATA TO PASS TO PAGE
			teacher = login.getTeacher();
			request.setAttribute("admin", teacher);
			request.setAttribute("schools", getSchoolsWithStudents());			
			request.setAttribute("events", getAllEvents());
			request.setAttribute("teacherLogins", getAllTeachers());
			
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
		
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
		
			case "addSchool":
				System.out.println("Adding School");
				Team newTeam = new Team(); 
				newTeam.setName(request.getParameter("teamName"));
				newTeam.setCreatedDate(new Date());
				newTeam.setEventInstanceID(Integer.parseInt(request.getParameter("eventInstanceId")));
				EventInstance newEventInstance = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
//				newTeam.setEventInstance(newEventInstance); // need to fill in eventInstance data.
				newEventInstance.getTeams().add(newTeam);
				
				sf.saveOrUpdate(newTeam);
//				sf.update(newEventInstance);
				tx.commit();				
				break;
			case "deleteSchool":
				System.out.println("Adding School");
//				Team newTeam = new Team(); 
//				newTeam.setName(request.getParameter("teamName"));
//				newTeam.setCreatedDate(new Date());
//				newTeam.setEventInstanceID(Integer.parseInt(request.getParameter("eventInstanceId")));
//				EventInstance newEventInstance = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
//				newTeam.setEventInstance(newEventInstance); // need to fill in eventInstance data.
//				newEventInstance.getTeams().add(newTeam);
				
//				sf.saveOrUpdate(newTeam);
//				sf.update(newEventInstance);
				tx.commit();				
				break;
			case "createEvent":
				System.out.println("Removing Team");
				Team removeTeam = (Team) sf.load(Team.class, new Integer(request.getParameter("teamId")));
				EventInstance removeTeamReference = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
				removeTeamReference.getTeams().remove(removeTeam);
				sf.saveOrUpdate(removeTeamReference);
				sf.delete(removeTeam);
				tx.commit();				
				break;		
				
			case "createLogin":
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
				
				tx.commit();				
				break;						
				
			case "deleteLogin":
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
				tx.commit();	
				
				break;

			case "modifyEvent":
				System.out.println("Registering Student");
				login = (Login) sf.get(Login.class, 1);
				teacher = login.getTeacher();
//				Team addStudentTeam = (Team) sf.load(Team.class, new Integer(request.getParameter("teamId")));
//				School school = (School) sf.load(School.class, teacher.getSchool().getId());
//				Student addStudent = new Student(school, request.getParameter("firstName"), request.getParameter("lastName"));
//				addStudent = (Student) sf.load(Student.class, new Integer(request.getParameter("teamId")));
//				Integer studentId = new Integer((Integer)sf.save(addStudent));
//				StudentTeamId newStudentTeamId = new StudentTeamId(studentId, new Integer(request.getParameter("teamId")));
//				StudentTeam  newStudentTeam = new StudentTeam(newStudentTeamId, addStudentTeam, addStudent);
//				addStudentTeam.getstudentTeams().add(newStudentTeam);
				
//				sf.saveOrUpdate(newStudentTeam);
				tx.commit();	
				
				break;				
			default:
				break;
				
		}
	}
	
	protected School getSchoolsWithStudents(){
		@SuppressWarnings("unchecked")
		List<School> school = (List<School>) sf.createQuery("from School s join fetch s.students ss").list();
		return school.get(0);
	}
	
	protected List<Event> getAllEvents(){
		Query query = sf.createQuery("from Event e join fetch e.eventInstances ei");
		return query.list();
	}
	
	protected List<Teacher> getAllTeachers(){
		@SuppressWarnings("unchecked")
		List<Teacher> teachers = (List<Teacher>) sf.createQuery("from teacher").list();
		return teachers;
	}
	
	protected List<Team> getStudentTeams(){
		Query query = sf.createQuery("from Team s join fetch s.studentTeams t");
		return query.list();
	}
	
}
