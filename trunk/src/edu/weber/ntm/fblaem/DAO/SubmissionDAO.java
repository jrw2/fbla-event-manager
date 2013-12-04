package edu.weber.ntm.fblaem.DAO;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;

import edu.weber.ntm.fblaem.DAO.MasterDAO;
import edu.weber.ntm.fblaem.databaseio.Event;
import edu.weber.ntm.fblaem.databaseio.EventInstance;
import edu.weber.ntm.fblaem.databaseio.EventType;
import edu.weber.ntm.fblaem.databaseio.Login;
import edu.weber.ntm.fblaem.databaseio.Role;
import edu.weber.ntm.fblaem.databaseio.School;
import edu.weber.ntm.fblaem.databaseio.Student;
import edu.weber.ntm.fblaem.databaseio.StudentTeam;
import edu.weber.ntm.fblaem.databaseio.StudentTeamId;
import edu.weber.ntm.fblaem.databaseio.Teacher;
import edu.weber.ntm.fblaem.databaseio.Team;
import edu.weber.ntm.fblaem.servlets.LoginManagement;

public class SubmissionDAO extends MasterDAO {

	private String submissionType;
	private String pageAction;
	
	public SubmissionDAO(HttpServletRequest request, HttpServletResponse response, String submissionType) {
		super(request, response);
		this.submissionType = submissionType;
		this.pageAction = request.getParameter("pageAction");
	}
	
	public void process(){
		
		try {
			
			initializeSession();
			
			// Get Page
			switch(submissionType){
				case(TYPE_EVENT_REGISTRATION):
					eventDataSubmission();
					break;
				case(TYPE_ADMINISTRATION):
					adminDataSubmission();
					break;	
			}
			
			request.setAttribute("errorValue", request.getAttribute("errorMessage"));
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		} finally {
			
			endSession();
			
		}
		
		
	}
	
	private void eventDataSubmission(){
		
		switch (pageAction) {
		
			case "addTeam": addTeam(); break;
			case "removeTeam": removeTeam(); break;
			case "removeStudentFromTeam": removeStudentFromTeam(); break;
			case "registerStudent": registerStudent(); break;
				
			default:
				break;
			
		}
		
	}
	
	private void adminDataSubmission(){
		
		switch (pageAction) {
		
			case "createEvent": createEvent(); break;
			case "removeEvent": removeEvent(); break;		
			case "addSchool": addSchool(); break;
			case "deleteSchool": deleteSchool(); break;
			case "createLogin": createLogin(); break;
			case "deleteLogin": deleteLogin(); break;
			case "reset": reset(); break;
			
			default:
				break;
				
		}
		
	}

	// Event Registration Submission --------------------------------------------------------------------------------
	private void addTeam(){
		
		System.out.println("Adding Team");
		
		Team newTeam = new Team(); 
		newTeam.setName(request.getParameter("teamName"));
		newTeam.setCreatedDate(new Date());
		newTeam.setEventInstanceID(Integer.parseInt(request.getParameter("eventInstanceId")));
		EventInstance newEventInstance = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
		newEventInstance.getTeams().add(newTeam);
		
		sf.saveOrUpdate(newTeam);	
		
	}
	
	private void removeTeam(){
		
		System.out.println("Removing Team");
		
		Team removeTeam = (Team) sf.load(Team.class, new Integer(request.getParameter("teamId")));
		EventInstance removeTeamReference = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
		removeTeamReference.getTeams().remove(removeTeam);
		
		sf.saveOrUpdate(removeTeamReference);
		sf.delete(removeTeam);

	}
	
	private void removeStudentFromTeam(){
		
		System.out.println("Removing Student");
		
		Team removeStudentFromTeam = (Team) sf.load(Team.class, new Integer(request.getParameter("teamId")));
		Student student = new Student(new Integer(request.getParameter("studentId")));
		StudentTeamId studentTeamId = new StudentTeamId(new Integer(request.getParameter("studentId")), new Integer(request.getParameter("teamId")));
		
		StudentTeam studentTeam = (StudentTeam) sf.load(StudentTeam.class, studentTeamId);
		EventInstance removeFromInstance = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
		
		removeStudentFromTeam.getstudentTeams().remove(studentTeam);
		
		/* Add this back in if we want a team to get removed when all students are removed.
		if(removeStudentFromTeam.getstudentTeams().size() == 0){
			
			sf.delete(studentTeam);
			removeFromInstance.getTeams().remove(removeStudentFromTeam);
			
			sf.saveOrUpdate(removeFromInstance);
			sf.delete(removeStudentFromTeam);
			
		} else {
		*/	
			sf.delete(studentTeam);
			
//		}
		
	}
	
	private void registerStudent(){
		
		System.out.println("Registering Student");
		Team addStudentTeam = (Team) sf.load(Team.class, new Integer(request.getParameter("teamId")));
		School school = (School) sf.load(School.class, teacher.getSchool().getId());
		Student addStudent = new Student(school, request.getParameter("firstName"), request.getParameter("lastName"));
//		addStudent = (Student) sf.load(Student.class, new Integer(request.getParameter("teamId")));
		Integer studentId = new Integer((Integer)sf.save(addStudent));
		StudentTeamId newStudentTeamId = new StudentTeamId(studentId, new Integer(request.getParameter("teamId")));
		StudentTeam  newStudentTeam = new StudentTeam(newStudentTeamId, addStudentTeam, addStudent);
		addStudentTeam.getstudentTeams().add(newStudentTeam);
		
		sf.saveOrUpdate(newStudentTeam);
		
	}
	// General Data Retrieval
	private List<Event> getAllEvents(){
		
		Query query = sf.createQuery("from Event e join fetch e.eventInstances ei");
		return query.list();
		
	}
	
	private School getSchoolsWithStudents(){
		
		@SuppressWarnings("unchecked")
		List<School> school = (List<School>) sf.createQuery("from School s join fetch s.students ss").list();
		return school.get(0);
		
	}
	
	private List<Teacher> getAllTeachers(){
		
		@SuppressWarnings("unchecked")
		List<Teacher> teachers = (List<Teacher>) sf.createQuery("from teacher").list();
		return teachers;
		
	}
	
	// Administration Submission --------------------------------------------------------------------------------
	private void createEvent(){
		
		System.out.println("Creating Event");
		
		EventType newEventType = (EventType) sf.load(EventType.class, new Integer(6));
		String description = request.getParameter("eventDescription");
		String name = request.getParameter("eventName");
		int minTeamSize = Integer.parseInt(request.getParameter("minTeamSize"));
		int maxTeamSize = Integer.parseInt(request.getParameter("maxTeamSize"));
		int maxEntries = Integer.parseInt(request.getParameter("maxEntries"));
		
		Event newEvent = new Event(newEventType, name, minTeamSize, maxTeamSize, maxEntries, new Date(), description);
		sf.save(newEvent);
		
//		List<School> schools = getAllSchools();
		
//		for(School school: schools){
			
		// Create event instances for all schools.
		EventInstance eI = new EventInstance(newEvent, new Date(), new Date(), new Date(), "WSU"/*, new Integer(school.getId())*/); // future team will implement scheduling.
		eI.setEventId(newEvent.getId());
		sf.save(eI);
		
		newEvent.getEventInstances().add(eI);
			
//		}
		
		sf.saveOrUpdate(newEvent);
	}
	
	private void removeEvent(){
		
		System.out.println("Removing Event");
		
		System.out.println("removeEvent: " + request.getParameter("removeEvent"));
		Event event = (Event) sf.load(Event.class, Integer.parseInt(request.getParameter("removeEvent")));
		sf.delete(event);
		
	}	
	
	private void addSchool(){
		
		System.out.println("Adding School");
		
		String name = request.getParameter("schoolName");
		String streetAddress = request.getParameter("schoolAddress");
		String city = request.getParameter("schoolCity");
		String state = request.getParameter("schoolState");
		String zip = request.getParameter("schoolZip");
		String phone = request.getParameter("schoolPhone");
		
		School newSchool = new School(name, streetAddress, city, state, zip, phone);
		
		// assign event instances
		
		sf.save(newSchool);
		
	}
	
	private void deleteSchool(){
		
		System.out.println("Adding School");
		
		School school = (School) sf.load(School.class, new Integer(request.getParameter("delSchool")));
		sf.delete(school);
		
	}	
	
	private void createLogin(){
		
		System.out.println("Creating Login");
		
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phone = request.getParameter("phone");
		String altPhone = request.getParameter("altPhone");
		String userName = request.getParameter("userName");
		String password = LoginManagement.hash(request.getParameter("password"));
		
		School school = (School) sf.load(School.class, Integer.parseInt(request.getParameter("loginSchool")));
		Role role = (Role) sf.load(Role.class, 15); // static for teacher until additional functionality added by future teams.
		Teacher teacher = new Teacher(school, email, firstName, lastName, phone, altPhone);
		Login newLogin = new Login(teacher, role, userName, password);
		
		sf.save(teacher);
		sf.save(newLogin);
		
	}
	
	private void deleteLogin(){
		
		System.out.println("Deleting Login");
		
		Login login = (Login) sf.load(Login.class, Integer.parseInt(request.getParameter("deleteUserLogin")));
		sf.delete(login);
		
	}

	private void reset(){
		
		System.out.println("Resetting System");
		Query query = sf.getNamedQuery("FBLAEM_Reset");
		query.executeUpdate();
		
		/*
		List<Teacher> teachers = (List<Teacher>) sf.createQuery("from teacher").list();
		List<EventInstance> eventInstances = (List<EventInstance>) sf.createQuery("from EventInstance").list();	
		List<Team> teams = (List<Team>) sf.createQuery("from Team").list();
		List<Login> logins = (List<Login>) sf.createQuery("from Login").list();
		List<School> schools = (List<School>) sf.createQuery("from School").list();
		List<Student> students = (List<Student>) sf.createQuery("from Student").list();
		List<StudentTeam> studentTeams = (List<StudentTeam>) sf.createQuery("from StudentTeam").list();

		for(Teacher teacher : teachers){
			sf.delete(teacher);
		}
		
		for(EventInstance eventInstance : eventInstances){
			sf.delete(eventInstance);
		}
		
		for(Team team : teams){
			sf.delete(team);
		}
		
		for(Login login : logins){
			sf.delete(login);
		}
		
		for(School school : schools){
			sf.delete(school);
		}
		
		for(Student student : students){
			sf.delete(student);
		}
		
		for(StudentTeam studentTeam : studentTeams){
			sf.delete(studentTeam);
		}
		*/
	}
	
	private List<School> getAllSchools(){
		
		return (List<School>)sf.createQuery("from School").list();
		
	}
	
}
