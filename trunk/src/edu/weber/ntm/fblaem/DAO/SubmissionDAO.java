package edu.weber.ntm.fblaem.DAO;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
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
	/** Description of process()
	 * 
	 *  	
	 *			
	 * 		 -processes the page to be prepared for submission.
	 */
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
			
			request.setAttribute("errorValue", errorMessage);
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		} finally {
			
			endSession();
			
		}
		
		
	}
	/** Description of eventDataSubmission()
	 * 
	 * 	 	
	 *  			
	 *			 -Handles the page action type to be performed. 
	 */
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
	/** Description of adminDataSubmission()
	 * 
	 *  	
	 *		
	 * 			-Handles the admin page action to be performed. 
	 */
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
	/** Description of addTeam()
	 * 
	 * 	 	
	 * 		
	 *		 -Submits the addTeam registration submission.
	 */
	// Event Registration Submission --------------------------------------------------------------------------------
	private void addTeam(){
		
		System.out.println("Adding Team");
		
		Team newTeam = new Team(); 
		newTeam.setName(request.getParameter("teamName"));
		newTeam.setCreatedDate(new Date());
		newTeam.setEventInstanceID(Integer.parseInt(request.getParameter("eventInstanceId")));
		newTeam.setSchoolId(teacher.getSchool().getId());
		EventInstance newEventInstance = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
		newEventInstance.getTeams().add(newTeam);
		
		sf.saveOrUpdate(newTeam);	
		
	}
	/** Description of removeTeam()
	 * 
	 * 	 	
	 * 		
	 *		 -Submits the removeTeam registration submission.
	 */
	private void removeTeam(){
		
		System.out.println("Removing Team");
		
		Team removeTeam = (Team) sf.load(Team.class, new Integer(request.getParameter("teamId")));
		EventInstance removeTeamReference = (EventInstance) sf.load(EventInstance.class, new Integer(request.getParameter("eventInstanceId")));
		removeTeamReference.getTeams().remove(removeTeam);
		
		sf.saveOrUpdate(removeTeamReference);
		sf.delete(removeTeam);

	}
	/** Description of removeStudentFromTeam()
	 * 
	 * 	 	
	 * 		
	 *		 -Submits the removeStudentFromTeam registration submission.
	 */
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
	/** Description of registerStudent()
	 * 
	 * 	 	
	 * 		
	 *		 -Submits the registerStudent registration submission.
	 */
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
	/** Description of createEvent()
	 * 
	 * 	 	
	 * 		
	 *		 -Admin event creation tool.
	 */
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
		
		// Create event instances for all schools.
		EventInstance eI = new EventInstance(newEvent, new Date(), new Date(), new Date(), "WSU"/*, new Integer(school.getId())*/); // future team will implement scheduling.
		eI.setEventId(newEvent.getId());
		sf.save(eI);
		
		newEvent.getEventInstances().add(eI);
			
		sf.saveOrUpdate(newEvent);
	}
	/** Description of removeEvent()
	 * 
	 * 	 	
	 * 		
	 *		 -Admin event removal tool.
	 */
	private void removeEvent(){
		
		System.out.println("Removing Event");
		
		System.out.println("removeEvent: " + request.getParameter("removeEvent"));
		Event event = (Event) sf.load(Event.class, Integer.parseInt(request.getParameter("removeEvent")));
		sf.delete(event);
		
	}	
	/** Description of addSchool()
	 * 
	 * 	 	
	 * 		
	 *		 -Admin school addition tool.
	 */
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
	/** Description of deleteSchool()
	 * 
	 * 	 	
	 * 		
	 *		 -Admin school deletion tool.
	 */
	private void deleteSchool(){
		
		System.out.println("Adding School");
		
		School school = (School) sf.load(School.class, new Integer(request.getParameter("delSchool")));
		sf.delete(school);
		
	}	
	/** Description of createLogin()
	 * 
	 * 	 	
	 * 		
	 *		 -Admin create login tool.
	 */
	private void createLogin(){
		
		System.out.println("Creating Login");
		
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phone = request.getParameter("phone");
		String altPhone = request.getParameter("altPhone");
		String userName = request.getParameter("userName");
		String password = LoginManagement.hash(request.getParameter("password"));
		
		// Verify
		Login existingLogin = (Login) sf.createQuery("from Login as login where login.username='" + userName + "'").uniqueResult();
		
		if(existingLogin == null){
			
			School school = (School) sf.load(School.class, Integer.parseInt(request.getParameter("loginSchool")));
			Role role = (Role) sf.load(Role.class, 15); // static for teacher until additional functionality added by future teams.
			Teacher teacher = new Teacher(school, email, firstName, lastName, phone, altPhone);
			Login newLogin = new Login(teacher, role, userName, password);
			
			sf.save(teacher);
			sf.save(newLogin);
			
		} else {
			
			errorMessage = "Username already exists";
			
		}
		
	}
	/** Description of deleteLogin()
	 * 
	 * 	 	
	 * 		
	 *		 -Admin delete login tool.
	 */
	private void deleteLogin(){
		
		System.out.println("Deleting Logins");
		
		String userId = request.getParameter("deleteUserLogin");
		Teacher existingTeacher = (Teacher) sf.load(Teacher.class, new Integer(request.getParameter("deleteUserLogin")));
		Login existingLogin = (Login) sf.createQuery("from Login as login where login.teacherId=" + existingTeacher.getId()).uniqueResult();
		
		sf.delete(existingTeacher);
		sf.delete(existingLogin);
		
	}

	private void reset(){
		
		System.out.println("Resetting System");
		try {
			
			PreparedStatement st = sf.connection().prepareStatement("{call FBLAEM_Reset}"); 
			st.execute();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private List<School> getAllSchools(){
		
		return (List<School>)sf.createQuery("from School").list();
		
	}
	
}
