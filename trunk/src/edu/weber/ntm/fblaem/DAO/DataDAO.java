package edu.weber.ntm.fblaem.DAO;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import edu.weber.ntm.fblaem.DAO.MasterDAO;
import edu.weber.ntm.fblaem.databaseio.Event;
import edu.weber.ntm.fblaem.databaseio.PDFGenerator;
import edu.weber.ntm.fblaem.databaseio.School;
import edu.weber.ntm.fblaem.databaseio.Teacher;

public class DataDAO extends MasterDAO{

	private String requestType;
	
	public DataDAO(HttpServletRequest request, HttpServletResponse response, String requestType) {
		super(request, response);
		this.requestType = requestType;
	}
	
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
					getEventRegistration(); // pdf uses same data, /PDF?EventId=-1&ViewType
					break;	
				case(TYPE_PDF):
					if(request.getParameter("exportType").equals(PDFGenerator.EXPORT_ADMIN)){
						getAdministration()(); // pdf uses same data
					} else if(request.getParameter("exportType").equals(PDFGenerator.EXPORT_TEACHER)){
						getEventRegistration(); // pdf uses same data
					}
					
					PDFGenerator.createDocument(request, response);
				
					break;	
			}
			
			request.setAttribute("errorValue", request.getAttribute("errorMessage"));
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		} finally {
			
			endSession();
			
		}
		
	}
	
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
	
	// Views
	private void getEventRegistration(){
		request.setAttribute("teacher", teacher);
		request.setAttribute("school", teacher.getSchool());			
		request.setAttribute("events", getAllEvents());
		
	}
	private void getAdministration(){
		
		request.setAttribute("admin", teacher);
		request.setAttribute("schools", getSchools());			
		request.setAttribute("events", getAllEvents());
		request.setAttribute("teacherLogins", getAllTeachers());
		
	}
	
	// General Data Retrieval
	protected List<Event> getAllEvents(){
		
		Query query = sf.createQuery("from Event e join fetch e.eventInstances ei");
		return query.list();
		
	}
	
	protected List<School> getSchoolsWithStudents(){
		
		@SuppressWarnings("unchecked")
		List<School> schools = (List<School>) sf.createQuery("from School s join fetch s.students ss").list();
		return schools;
		
	}
	
	protected List<Teacher> getAllTeachers(){
		
		@SuppressWarnings("unchecked")
		List<Teacher> teachers = (List<Teacher>) sf.createQuery("from Teacher").list();
		return teachers;
		
	}
	
	protected List<School> getSchools(){
		
		@SuppressWarnings("unchecked")
		List<School> schools = (List<School>) sf.createQuery("from School").list();
		return schools;
		
	}
	
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
