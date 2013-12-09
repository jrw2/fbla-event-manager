package edu.weber.ntm.fblaem.DAO;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import edu.weber.ntm.fblaem.databaseio.Event;
import edu.weber.ntm.fblaem.databaseio.School;
import edu.weber.ntm.fblaem.databaseio.Teacher;
import edu.weber.ntm.fblaem.exports.PDFGenerator;

public class DataDAO extends MasterDAO{

	private String requestType;
	
	public DataDAO(HttpServletRequest request, HttpServletResponse response, String requestType) {
		super(request, response);
		this.requestType = requestType;
	}
	/** Description of process()
	 * 
	 * 	
	 *    			
	 * @return			Gets page type to process.
	 */
	@Override
	public void process(){
		
		try {
			
			verifyActiveUser();
			initializeSession();
			
			// Get Page
			switch(requestType){
				case(TYPE_LOGIN):
					getLoginRedirect();
					break;
				case(TYPE_EVENT_REGISTRATION):
					getEventRegistration();
					break;
				case(TYPE_ADMINISTRATION):
					getAdministration(); // pdf uses same data, /PDF?EventId=-1&ViewType
					break;	
				case(TYPE_PDF):
					getPDF();
					break;	
			}
			
			request.setAttribute("errorValue", request.getAttribute("errorMessage"));
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		} finally {
			
			endSession();
			
		}
		
	}
	/** Description of getLoginRedirect()
	 * 
	 *	 	
	 *  			
	 * @return			Gets the role of the initial login. 
	 */
	// Initial load
	private void getLoginRedirect(){
		
		int loginType = login.getRole().getId();
		
		switch (loginType){
			case(ROLE_TYPE_ADMIN):
				getAdministration();
				break;
			case(ROLE_TYPE_TEACHER):
				getEventRegistration();
				break;
			default:
			try {
				response.sendRedirect("login.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
		
		}
		
	}
	/** Description of getEventRegistration()
	 * 
	 * 	
	 *    			
	 * @return			 Changes the view according to getLoginRedirect helper method.
	 */
	// Views
	private void getEventRegistration(){
		request.setAttribute("teacher", teacher);
		request.setAttribute("school", teacher.getSchool());			
		request.setAttribute("events", getAllEvents());
		
	}
	/** Description of getAdmin()
	 * 
	 * 	
	 *    			
	 * @return			 Changes the view according to getLoginRedirect helper method.
	 */
	private void getAdministration(){
		
		request.setAttribute("admin", teacher);
		request.setAttribute("schools", getSchools());			
		request.setAttribute("events", getAllEvents());
		request.setAttribute("teacherLogins", getAllTeachers());
		
	}
	/** Description of getPDF()
	 * 
	 * 	
	 *    			
	 * 		 -Changes the view according to getLoginRedirect helper method.
	 */
	private void getPDF(){
		
		if(request.getParameter("exportType").equals(PDFGenerator.EXPORT_ADMIN)){
			getAdministration(); // pdf uses same data
		} else if(request.getParameter("exportType").equals(PDFGenerator.EXPORT_TEACHER)){
			getEventRegistration(); // pdf uses same data
		}
		
		PDFGenerator pdfGen = new PDFGenerator();
		pdfGen.createDocument(request, response);
		
		String eventTitle = "all";
		
	}
	/** Description of getAllEvents()
	 * 
	 * 	
	 *    			
	 * @return			returns a list of all events.
	 */
	// General Data Retrieval
	protected List<Event> getAllEvents(){
		
		Query query = sf.createQuery("from Event e join fetch e.eventInstances ei");
		return query.list();
		
	}
	/** Description of getSchoolWithStudents()
	 * 
	 * 	
	 *    			
	 * @return			returns a list of schools with the students.
	 */
	protected List<School> getSchoolsWithStudents(){
		
		@SuppressWarnings("unchecked")
		List<School> schools = (List<School>) sf.createQuery("from School s join fetch s.students ss").list();
		return schools;
		
	}
	/** Description of getAllTeachers()
	 * 
	 * 	
	 *    			
	 * @return			returns a list of all teachers
	 */
	protected List<Teacher> getAllTeachers(){
		
		@SuppressWarnings("unchecked")
		List<Teacher> teachers = (List<Teacher>) sf.createQuery("from Teacher").list();
		return teachers;
		
	}
	/** Description of getAllEvents()
	 * 
	 * 	
	 *    			
	 * @return			returns a list of all schools.
	 */
	protected List<School> getSchools(){
		
		@SuppressWarnings("unchecked")
		List<School> schools = (List<School>) sf.createQuery("from School").list();
		return schools;
		
	}
	/** Description of verifyActiveUser()
	 * 
	 * 	
	 *    			
	 * 		-verifies the current logged in user.
	 */
	private void verifyActiveUser(){
		if(request.getUserPrincipal() == null)
		{		
			request.getSession().removeAttribute("isActive");
			request.getSession().invalidate();
			
			try {
				response.sendRedirect("login.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
