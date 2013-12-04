package edu.weber.ntm.fblaem.exports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;

//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Font;
//import com.lowagie.text.FontFactory;
//import com.lowagie.text.Phrase;
//import com.lowagie.text.pdf.BaseFont;
//import com.lowagie.text.pdf.PdfPTable;

import edu.weber.ntm.fblaem.DAO.MasterDAO;
import edu.weber.ntm.fblaem.databaseio.Event;
import edu.weber.ntm.fblaem.databaseio.School;
import edu.weber.ntm.fblaem.databaseio.Teacher;

public class PDFGenerator {
	/*
	public static String EXPORT_ADMIN = "admin";
	public static String EXPORT_TEACHER = "teacher";
	
	private static int FONT_08 = 0;
	private static int FONT_08_BOLD = 1;
	private static int FONT_09_BOLD = 2;
	
	private int eventId;
	private String type, eventName;
	private document;
	
	public void createDocument(HttpServletRequest request, HttpServletResponse response){
		
		this.eventId = Integer.parseInt(request.getParameter("eventId"));
		this.type = request.getParameter("exportType");
		this.eventName = request.getParameter("eventName");
		this.document = new Document();
		
		String fileName = "FBLAEM_";
		fileName += type.equals(EXPORT_ADMIN) ? "All_Events" : eventName;
		
		response.setHeader("Content-Disposition", "inline; filename=" + eventName + ".pdf");
        response.setContentType("application/pdf");
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
		
		switch(type){
			
			case(EXPORT_ADMIN):
				generateAdminExport();
				break;
			case(EXPORT_TEACHER):
				generateTeacherExport();
				break;
			
		}
		
		document.close();
		
	}
	
	private void generateAdminExport(){
		
		List<Event> events = (List<Event>)request.getAttribute("events");
		List<School> schools = (List<School>)request.getAttribute("schools");
		List<Teacher> teacherLogins = (List<Teacher>)request.getAttribute("teacherLogins");
		
		Font defaultFont = getPdfFont(FONT_08);
		Font boldSmallFont = getPdfFont(FONT_08_BOLD);
		Font boldTitleFont = getPdfFont(FONT_09_BOLD);
		
		int titleHeaderwidths[] = {100}; // percentage
		PdfPTable titleTable = new PdfPTable(titleHeaderwidths, 100);
		titleTable.getDefaultCell().setPadding(0);
		titleTable.getDefaultCell().setBorderWidth(0);
		
		String allEvents = eventId == -1 ? "All Events" : "Event Name: " + eventId;
		titleTable.addCell(new Phrase("Teacher Name, School Name - " + allEvents, boldTitleFont)); document.add(titleTable);
		
		for(int i=0; i < events.size(); i++){ 
			
			Event event = events.get(i);

			if(event.getEventInstances() != null){
				
				String maxTeamsPerSchool = Integer.toString(event.getMaxEntriesPerSchool());
				
				Iterator<EventInstance> itr = (Iterator<EventInstance>)event.getEventInstances().iterator();
			
			    while(itr.hasNext()) {
			    	
			    	EventInstance eventInstance = (EventInstance)itr.next();
			    	// if statement here to determine if instance is for this school.
			    	List<Team> studentInstanceTeams = new ArrayList<Team>();
			    	Set<Team> teams = (Set<Team>)eventInstance.getTeams();
					
					if(studentInstanceTeams.size() < event.getMaxEntriesPerSchool()){ 
						maxTeamsPerSchool = "href";
					}
//							<div id="title">

//								=event.getName()  | =event.getEventType().getTypeName()  | <a href="exportEvent?eventId=" style="font-weight: normal;"><img src="=pdf"/> Export Event</a>
								
//							</div>
							

//						<div id="description" style="width: 780px; margin-bottom: 20px; margin-left: 9px;">
//
//							=event.getDetails() != null ? event.getDetails() : "No Description"
//							
//						</div>				
						
						if(teams != null && teams.size() > 0) {

							for(Team team : teams){
								
								Set<StudentTeam> studentTeams = (Set<StudentTeam>)team.getstudentTeams();
							
									String enrolledStudents = Integer.toString(team.getstudentTeams().size());
									String maxIndividuals = (team.getMaxIndividuals() == null) ? "No Max" : team.getMaxIndividuals();
//										&nbsp;&nbsp;=team.getName() ( =enrolledStudents + " / " + maxIndividuals )
										
								for(StudentTeam studentTeam : studentTeams){
										
									studentTeam.getStudent().getFirstName() + " " + studentTeam.getStudent().getLastName();
											
								} 
							} 
						} 
				}
			}
			
			int headerWidths[] = new int[]{10, 15, 52, 13, 10};
			PdfPTable eventTable = new PdfPTable(titleHeaderwidths, 100);
			
			if(eventId == -1 || eventId == event.getId()){
				
				document.add(eventTable);
				
			} 
			
		}

		
	}
	
	private void generateTeacherExport(){
		
		List<Event> events = (List<Event>)request.getAttribute("events");
		Teacher teacher = (Teacher)request.getAttribute("teacher");
		School school = (School)request.getAttribute("school");
		
		Font defaultFont = getPdfFont(FONT_08);
		Font boldSmallFont = getPdfFont(FONT_08_BOLD);
		Font boldTitleFont = getPdfFont(FONT_09_BOLD);
		
		int titleHeaderwidths[] = {100}; // percentage
		PdfPTable titleTable = new PdfPTable(titleHeaderwidths, 100);
		titleTable.getDefaultCell().setPadding(0);
		titleTable.getDefaultCell().setBorderWidth(0);
		
		String allEvents = eventId == -1 ? "All Events" : "Event Name: " + eventId;
		titleTable.addCell(new Phrase("Teacher Name, School Name - " + allEvents, boldTitleFont)); document.add(titleTable);
		
		for(int i=0; i < events.size(); i++){ 
			
			Event event = events.get(i);

			if(event.getEventInstances() != null){
				
				String maxTeamsPerSchool = Integer.toString(event.getMaxEntriesPerSchool());
				
				Iterator<EventInstance> itr = (Iterator<EventInstance>)event.getEventInstances().iterator();
			
			    while(itr.hasNext()){
			    	
			    	EventInstance eventInstance = (EventInstance)itr.next();
			    	List<Team> studentInstanceTeams = new ArrayList<Team>();
			    	Set<Student> students = (Set<Student>)school.getStudents();
			    	Set<Team> teams = (Set<Team>)eventInstance.getTeams();
					
					if(studentInstanceTeams.size() < event.getMaxEntriesPerSchool()){ 
						maxTeamsPerSchool = "href";
					}
//						<div id="header" style="border-bottom: 2px solid;">
//							<div id="title">
//
//								=event.getName()  | =event.getEventType().getTypeName()  | <a href="exportEvent?eventId=" style="font-weight: normal;"><img src="=pdf"/> Export Event</a>
//								
//							</div>
//							
//							<div id="link" style="width: 90px;">
//							
//								<a =maxTeamsPerSchool="javascript:void(0)" onclick="showDiv('addTeam=eventInstance.getId()');">Add Team</a>
//								
//							</div>
//							
//							<div id="link">
//							
//								<a href="javascript:void(0)" onclick="showDiv('addStudent=eventInstance.getId()');">Register Student</a>
//								
//							</div>				
//						</div>

//						=event.getDetails() != null ? event.getDetails() : "No Description"

						for(Team team : teams){
						
							Set<StudentTeam> studentTeams = (Set<StudentTeam>)team.getstudentTeams();
						
							String enrolledStudents = Integer.toString(team.getstudentTeams().size());
							String maxIndividuals = (team.getMaxIndividuals() == null) ? "No Max" : team.getMaxIndividuals();
//							&nbsp;&nbsp;=team.getName() ( =enrolledStudents + " / " + maxIndividuals )
									
							for(StudentTeam studentTeam : studentTeams){
//									<div id="teamMem">
//										<a href ="javascript:void(0)" onclick="removeStudentFromTeam(=studentTeam.getId().getStudentId(), =team.getId(), =eventInstance.getId());"><img src="=remove"/></a>									
//										=studentTeam.getStudent().getFirstName() + " " + studentTeam.getStudent().getLastName()
//	-- 									<a href ="javascript:void(0)" onclick="removeStudentFromTeam(=studentTeam.getId().getStudentId(), =team.getId(), =eventInstance.getId());" style="font-size: 12px; color: red;">&nbsp;remove</a> --
//									</div>
					
							} 
						} 
			    }
			}
			
			int headerWidths[] = new int[]{10, 15, 52, 13, 10};
			PdfPTable eventTable = new PdfPTable(titleHeaderwidths, 100);
			
			if(eventId == -1 || eventId == event.getId()){
				
				document.add(eventTable);
				
			} 
			
		} 
		
	}
	
	public Font getPdfFont(int font){
		
		switch(font){
			case FONT_08:				font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0)); break;
			case FONT_08_BOLD:			font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new Color(0, 0, 0)); break; 
			case FONT_09_BOLD:			font = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0, 0, 0)); break;
		}
		
		return font;
		
	}
	*/
}
