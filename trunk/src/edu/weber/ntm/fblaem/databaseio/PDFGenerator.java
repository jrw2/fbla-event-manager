package edu.weber.ntm.fblaem.databaseio;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import edu.weber.ntm.fblaem.DAO.MasterDAO;
import edu.weber.ntm.fblaem.databaseio.Event;
import edu.weber.ntm.fblaem.databaseio.School;
import edu.weber.ntm.fblaem.databaseio.Teacher;

public class PDFGenerator {
	
	public static String EXPORT_ADMIN = "admin";
	public static String EXPORT_TEACHER = "teacher";
	
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
		
	}
	
	private void generateTeacherExport(){
		
	}
}
