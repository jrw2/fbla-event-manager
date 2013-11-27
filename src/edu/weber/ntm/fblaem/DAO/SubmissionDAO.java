package edu.weber.ntm.fblaem.DAO;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import edu.weber.ntm.fblaem.DAO.MasterDAO;
import edu.weber.ntm.fblaem.databaseio.Event;
import edu.weber.ntm.fblaem.databaseio.EventInstance;
import edu.weber.ntm.fblaem.databaseio.School;
import edu.weber.ntm.fblaem.databaseio.Student;
import edu.weber.ntm.fblaem.databaseio.StudentTeam;
import edu.weber.ntm.fblaem.databaseio.StudentTeamId;
import edu.weber.ntm.fblaem.databaseio.Teacher;
import edu.weber.ntm.fblaem.databaseio.Team;

public class SubmissionDAO extends MasterDAO{

	private static final String TYPE_EVENT_REGISTRATION = "eventRegistration";
	private static final String TYPE_ADMINISTRATION = "administration";
	private String submissionType;
	private String pageAction;
	
	public SubmissionDAO(HttpServletRequest request, String submissionType) {
		super(request);
		this.submissionType = submissionType;
		this.pageAction = request.getParameter("pageAction");
	}
	
	
	public void process(){
		
		initializeSession();
		
		// Get Page
		switch(submissionType){
			case(TYPE_EVENT_REGISTRATION):
				eventDataSubmission();
				break;
			case(TYPE_ADMINISTRATION):
				administrationDataSubmission();
				break;	
		}
		
		request.setAttribute("errorValue", request.getAttribute("errorMessage"));
		
		endSession();
	}
	
	private void eventDataSubmission(){
		
		String pageAction = request.getParameter("pageAction");
		switch (pageAction) {
		
			case "addTeam": addTeam();
			case "removeTeam": removeTeam();
			case "removeStudentFromTeam": removeStudentFromTeam();
			case "registerStudent": registerStudent();
				
			default:
				break;
			
		}
		
	}
	
	private void administrationDataSubmission(){
		
		switch (pageAction) {
		
		case "addSchool": addSchool();
		case "deleteSchool": deleteSchool();
		case "createEvent": createEvent();
		case "createLogin": createLogin();
		case "deleteLogin": deleteLogin();
		case "modifyEvent": modifyEvent();
		default:
			break;
		}
		
	}

	// Event Registration Submission
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
		
		if(removeStudentFromTeam.getstudentTeams().size() == 0){
			
			sf.delete(studentTeam);
			removeFromInstance.getTeams().remove(removeStudentFromTeam);
			
			sf.saveOrUpdate(removeFromInstance);
			sf.delete(removeStudentFromTeam);
			
		} else {
			
			sf.delete(studentTeam);
			
		}
		
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
	
	// Administration Submission
	private void addSchool(){
		System.out.println("Adding School");
		Team newTeam = new Team(); 
		newTeam.setName(request.getParameter("teamName"));
		newTeam.setCreatedDate(new Date());
		newTeam.setEventInstanceID(Integer.parseInt(request.getParameter("eventInstanceId")));
		EventInstance newEventInstance = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
//		newTeam.setEventInstance(newEventInstance); // need to fill in eventInstance data.
		newEventInstance.getTeams().add(newTeam);
		
		sf.saveOrUpdate(newTeam);
//		sf.update(newEventInstance);
		tx.commit();		
	}
	
	private void deleteSchool(){
		System.out.println("Adding School");
	}
	
	private void createEvent(){
		
		System.out.println("Removing Team");
		
		Team removeTeam = (Team) sf.load(Team.class, new Integer(request.getParameter("teamId")));
		EventInstance removeTeamReference = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
		removeTeamReference.getTeams().remove(removeTeam);
		sf.saveOrUpdate(removeTeamReference);
		sf.delete(removeTeam);

	}
	
	private void createLogin(){
		
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
		
	}
	
	private void deleteLogin(){
		
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
	
	private void modifyEvent(){
		
	}
	
}
