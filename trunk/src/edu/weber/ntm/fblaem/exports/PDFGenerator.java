package edu.weber.ntm.fblaem.exports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.weber.ntm.fblaem.databaseio.Event;
import edu.weber.ntm.fblaem.databaseio.EventInstance;
import edu.weber.ntm.fblaem.databaseio.School;
import edu.weber.ntm.fblaem.databaseio.Student;
import edu.weber.ntm.fblaem.databaseio.StudentTeam;
import edu.weber.ntm.fblaem.databaseio.Teacher;
import edu.weber.ntm.fblaem.databaseio.Team;
import com.itextpdf.text.BaseColor;

public class PDFGenerator {

	public static final String EXPORT_ADMIN = "admin";
	public static final String EXPORT_TEACHER = "teacher";
	
	private static final int FONT_08 = 0;
	private static final int FONT_08_BOLD = 1;
	private static final int FONT_09_BOLD = 2;
	
	private int eventId;
	private String type, eventName;
	private Document document;
	
	public void createDocument(HttpServletRequest request, HttpServletResponse response){
		
		this.eventId = Integer.parseInt(request.getParameter("eventId"));
		this.type = request.getParameter("exportType");
		this.eventName = request.getParameter("eventName");
		this.document = new Document();
		
		String fileName = "FBLAEM_";
		fileName += type.equals(EXPORT_ADMIN) ? "All_Events" : eventName;
		
		response.setHeader("Content-Disposition", "inline; filename=" + eventName + ".pdf");
        response.setContentType("application/pdf");
        
        try {
        	
			PdfWriter.getInstance(document, response.getOutputStream());

	        document.open();
			
			switch(type){
				
				// Currently the same export basically, but split for future modifications.
				case(EXPORT_ADMIN):
					generateAdminExport(request, response);
					break;
				case(EXPORT_TEACHER):
					generateTeacherExport(request, response);
					break;
				
			}
			
			document.close();
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void generateAdminExport(HttpServletRequest request, HttpServletResponse response) throws DocumentException{
		
		List<Event> events = (List<Event>)request.getAttribute("events");
		List<School> schools = (List<School>)request.getAttribute("schools");
		List<Teacher> teacherLogins = (List<Teacher>)request.getAttribute("teacherLogins");
		
		Font defaultFont = getPdfFont(FONT_08);
		Font boldSmallFont = getPdfFont(FONT_08_BOLD);
		Font boldTitleFont = getPdfFont(FONT_09_BOLD);
		
		float headerWidths[] = new float[]{30, 30, 40}; // percentages
		float titleHeaderwidths[] = {100}; 
		PdfPTable titleTable = createTable(titleHeaderwidths, 100);
		
		titleTable.getDefaultCell().setPadding(0);
		titleTable.getDefaultCell().setBorderWidth(0);
		titleTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		String title = (String)request.getAttribute("singleEventTitle");
		
		String allEvents = (eventId == -1 ? "All Events" : "Event Name: " + title);
		titleTable.addCell(new Phrase("Administrator - "  + allEvents, boldTitleFont)); document.add(titleTable);
		createDivider(titleTable, 1);
		
		for(int i=0; i < events.size(); i++){ 
			
			Event event = events.get(i);
			
			int studentEnrollements = 0;
			
			if(event.getEventInstances() != null){
				
				String maxTeamsPerSchool = Integer.toString(event.getMaxEntriesPerSchool());
				
				Iterator<EventInstance> itr = (Iterator<EventInstance>)event.getEventInstances().iterator();
			
			    while(itr.hasNext()){
			    	
			    	PdfPTable eventTable = createTable(headerWidths, 100);
					eventTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			    	
			    	EventInstance eventInstance = (EventInstance)itr.next();
			    	List<Team> studentInstanceTeams = new ArrayList<Team>();
			    	Set<Team> teams = (Set<Team>)eventInstance.getTeams();
					
					if(studentInstanceTeams.size() < event.getMaxEntriesPerSchool()){ 
						maxTeamsPerSchool = "href";
					}
					
					eventTable.addCell(new Phrase(event.getName(), boldSmallFont));
					
					eventTable.getDefaultCell().setColspan(2);
					eventTable.addCell(new Phrase(event.getEventType().getTypeName(), boldSmallFont));
					
					eventTable.getDefaultCell().setColspan(3);
					eventTable.addCell(new Phrase(event.getDetails() != null ? event.getDetails() : "No Description", defaultFont));

					for(Team team : teams){
					
						Set<StudentTeam> studentTeams = (Set<StudentTeam>)team.getstudentTeams();
						String enrolledStudents = Integer.toString(team.getstudentTeams().size());
						String maxIndividuals = (team.getMaxIndividuals() == null) ? "No Max" : team.getMaxIndividuals();
						
						eventTable.getDefaultCell().setColspan(3);
						eventTable.addCell(new Phrase(team.getName() + "(" + enrolledStudents + "/" + maxIndividuals + ")", boldSmallFont));
						
						for(StudentTeam studentTeam : studentTeams){
							
							eventTable.getDefaultCell().setColspan(1);
							eventTable.addCell(new Phrase("", boldSmallFont));
							eventTable.getDefaultCell().setColspan(2);
							eventTable.addCell(new Phrase(studentTeam.getStudent().getFullName(), defaultFont));
							
							if(eventId == -1 || eventId == event.getId()){
								studentEnrollements++;
							}
							
						} 
					}
					
					if(eventId == -1 || eventId == event.getId()){
						
						createDivider(eventTable, 3);
						eventTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
						eventTable.getDefaultCell().setColspan(2);
						eventTable.addCell(new Phrase("", boldSmallFont));
						eventTable.addCell(new Phrase("Total Enrollments: " + studentEnrollements, boldSmallFont));
						eventTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
						eventTable.getDefaultCell().setColspan(1);
						document.add(eventTable);
						
					} 
					
			    }
			    
			}
			
		} 
		
	}
	
	private void generateTeacherExport(HttpServletRequest request, HttpServletResponse response) throws DocumentException{
		
		List<Event> events = (List<Event>)request.getAttribute("events");
		Teacher teacher = (Teacher)request.getAttribute("teacher");
		School school = (School)request.getAttribute("school");
		
		Font defaultFont = getPdfFont(FONT_08);
		Font boldSmallFont = getPdfFont(FONT_08_BOLD);
		Font boldTitleFont = getPdfFont(FONT_09_BOLD);
		
		float headerWidths[] = new float[]{30, 30, 40}; // percentages
		float titleHeaderwidths[] = {100}; 
		PdfPTable titleTable = createTable(titleHeaderwidths, 100);
		
		titleTable.getDefaultCell().setPadding(0);
		titleTable.getDefaultCell().setBorderWidth(0);
		titleTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		String title = (String)request.getAttribute("singleEventTitle");
		
		String allEvents = (eventId == -1 ? "All Events" : "Event Name: " + title);
		titleTable.addCell(new Phrase(teacher.getFullName() + "(" + school.getName() + ") - "  + allEvents, boldTitleFont)); document.add(titleTable);
		createDivider(titleTable, 1);
		
		for(int i=0; i < events.size(); i++){ 
			
			Event event = events.get(i);
			
			int studentEnrollements = 0;
			
			if(event.getEventInstances() != null){
				
				String maxTeamsPerSchool = Integer.toString(event.getMaxEntriesPerSchool());
				
				Iterator<EventInstance> itr = (Iterator<EventInstance>)event.getEventInstances().iterator();
			
			    while(itr.hasNext()){

					PdfPTable eventTable = createTable(headerWidths, 100);
					eventTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			    	
			    	EventInstance eventInstance = (EventInstance)itr.next();
			    	List<Team> studentInstanceTeams = new ArrayList<Team>();
			    	Set<Student> students = (Set<Student>)school.getStudents();
			    	Set<Team> teams = (Set<Team>)eventInstance.getTeams();
					
					if(studentInstanceTeams.size() < event.getMaxEntriesPerSchool()){ 
						maxTeamsPerSchool = "href";
					}
					
					eventTable.addCell(new Phrase(event.getName(), boldSmallFont));
					
					eventTable.getDefaultCell().setColspan(2);
					eventTable.addCell(new Phrase(event.getEventType().getTypeName(), boldSmallFont));
					
					eventTable.getDefaultCell().setColspan(3);
					eventTable.addCell(new Phrase(event.getDetails() != null ? event.getDetails() : "No Description", defaultFont));

					for(Team team : teams){
					
						Set<StudentTeam> studentTeams = (Set<StudentTeam>)team.getstudentTeams();
						String enrolledStudents = Integer.toString(team.getstudentTeams().size());
						String maxIndividuals = (team.getMaxIndividuals() == null) ? "No Max" : team.getMaxIndividuals();
						
						eventTable.getDefaultCell().setColspan(3);
						eventTable.addCell(new Phrase(team.getName() + "(" + enrolledStudents + "/" + maxIndividuals + ")", boldSmallFont));
						
						for(StudentTeam studentTeam : studentTeams){
							
							eventTable.getDefaultCell().setColspan(1);
							eventTable.addCell(new Phrase("", boldSmallFont));
							eventTable.getDefaultCell().setColspan(2);
							eventTable.addCell(new Phrase(studentTeam.getStudent().getFullName(), defaultFont));
							
							if(eventId == -1 || eventId == event.getId()){
								
								studentEnrollements++; // in case she wants teachers to have the number enrolled
								
							} 
							
						} 
					} 
					
					if(eventId == -1 || eventId == event.getId()){
						
						createDivider(eventTable, 3);
						eventTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
						eventTable.getDefaultCell().setColspan(2);
						eventTable.addCell(new Phrase("", boldSmallFont));
						eventTable.addCell(new Phrase("Total Enrollments: " + studentEnrollements, boldSmallFont));
						eventTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
						eventTable.getDefaultCell().setColspan(1);
						document.add(eventTable);
						
					} 					
					
			    }
			}
			
		} 
		
	}
	
	public Font getPdfFont(int font_type){
		
		Font font = new Font();
		
		switch(font_type){
			case FONT_08:			font = FontFactory.getFont(FontFactory.HELVETICA, 8f, Font.NORMAL, BaseColor.BLACK); break;
			case FONT_08_BOLD:		font = FontFactory.getFont(FontFactory.HELVETICA, 8f, Font.BOLD, BaseColor.BLACK); break; 
			case FONT_09_BOLD:		font = FontFactory.getFont(FontFactory.HELVETICA, 9f, Font.BOLD, BaseColor.BLACK); break;
		}
		
		return font;
		
	}
	
	public PdfPTable createTable(float[] widths, float percentage){
		
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(percentage);
		
		return table;
		
	}
	
	public void createDivider(PdfPTable table, int colspan){
		
		table.getDefaultCell().setColspan(colspan);
		table.getDefaultCell().setBackgroundColor(BaseColor.BLACK);
		table.addCell(new Phrase("", new Font()));
		table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
		table.addCell(new Phrase("", new Font()));
		
	}

}
