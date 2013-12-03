package edu.weber.ntm.fblaem.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import edu.weber.ntm.fblaem.databaseio.Event;
import edu.weber.ntm.fblaem.databaseio.HibernateUtil;
import edu.weber.ntm.fblaem.databaseio.School;
import edu.weber.ntm.fblaem.databaseio.Team;



/**
 * Servlet implementation class PDF
 */

public class PDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Session sf = HibernateUtil.getSessionFactory().openSession();
	Transaction  tx = sf.getSessionFactory().getCurrentSession().beginTransaction(); 
	private List<Event> getAllEvents(){
		
		Query query = sf.createQuery("from Event e join fetch e.eventInstances ei");
		//tx.commit();
		return query.list();
	}
	
	private List<Team> getStudentTeams(){
		Query query = sf.createQuery("from Team s join fetch s.studentTeams t");
		//tx.commit();
		return query.list();
	}
	private List count(){
		Query count =sf.createQuery("SELECT count(*) from Student s    ");
		
		return count.list(); 
	}
	

protected School getSchoolWithStudents(int schoolId){
		
	   @SuppressWarnings("unchecked")
	   List<School> school = (List<School>) sf.createQuery("select s from School s inner join fetch s.students s where s.id = " + schoolId).list();
	  // tx.commit();
	   // sf.close(); 
	   return school.get(0);
		
	}
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 Document document = new Document();
	 School s = new School(); 
		try {

	        
	        String firstName = "FBLAEM";
	        String lastName = "Registration";
	        
	        if(request.getParameter("firstName") != null && !request.getParameter("firstName").equals("")){
	        	firstName = request.getParameter("firstName");
	        }
	        
	        if(request.getParameter("lastName") != null && !request.getParameter("lastName").equals("")){
	        	lastName = request.getParameter("lastName");
	        }
	        
			response.setHeader("Content-Disposition",
	       		   	 " attachment; filename=\"example.pdf\"");
	        response.setContentType("application/pdf");
	      
	        PdfWriter.getInstance(document, response.getOutputStream());
	        document.open();
			for (Event e : getAllEvents()){
				
				document.add(new Paragraph ( e.getName())); 
			}
			for(Team t : getStudentTeams()){
			document.add(new Paragraph(t.getstudentTeams().toString() + "  " + count().toString())); 
			}
			
			
			
			document.add(new Paragraph("This is a PDF file created by " + 
					firstName + " " + lastName +
					". Current Date and time is .... "  +
					new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss").format(Calendar.getInstance().getTime())));
					
			document.close();
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}


 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  		   
		
	      /*  try {
	            // step 1
	            Document document = new Document();
	            // step 2
	            PdfWriter.getInstance(document, response.getOutputStream());
	            // step 3
	            response.setHeader("Content-Disposition",
		       		   	 " attachment; filename=\"example.pdf\"");
		        response.setContentType("application/pdf");
		           
	            document.open();
	            // step 4
	            document.add(new Paragraph("Hello World"));
	            document.add(new Paragraph(new Date().toString()));
	            // step 5
	            document.close();
	         
	        } catch (DocumentException de) {
	            throw new IOException(de.getMessage());
	        }
	    }
	    */
	}
}

