package edu.weber.ntm.fblaem.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.weber.ntm.fblaem.DAO.DataDAO;
import edu.weber.ntm.fblaem.DAO.MasterDAO;
import edu.weber.ntm.fblaem.DAO.SubmissionDAO;
import edu.weber.ntm.fblaem.databaseio.HibernateUtil;
import edu.weber.ntm.fblaem.databaseio.Login;

public class ViewController extends HttpServlet{
	
	private static final long serialVersionUID = 3743123062179776667L;

	HttpServletRequest request;
	HttpServletResponse response;
	
	public ViewController()
	{
		super();
	}
	

	/** 
	 * Intercepts URL servlet request. Servlet intercepts available in web.xml
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.request = request;
		this.response = response;
		
		processRequest();
		
		return;
		
	}
	
	/**
	 * Page forms submit to this method for processing.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.request = request;
		this.response = response;
		
		if(request.getParameter("errorMessage") == null){
			processSubmission();
		}
		
		processRequest();
		
		return;
		
	}
	/** Description of processRequest()
	 * 
	 * 	
	 *  			
	 * 			-Prepares the page request to be sent to DataDAO to be processed for the resubmission 
	 * 			of altered data.
	 */
	private void processRequest(){
		
		try {
			
			String viewType = getServletConfig().getInitParameter("viewType");
			HttpSession session = request.getSession(false);//false means don't create one if it doesn't exist
			if(session != null && !session.isNew()) {
				
				Session sf = HibernateUtil.getSessionFactory().getCurrentSession();
				@SuppressWarnings("unused")
				Transaction tx = sf.beginTransaction();
				
				Login login = (Login) sf.createQuery("from Login as login where login.username='" + request.getRemoteUser() + "'").uniqueResult();
				
				if(!viewType.equals("PDF")){
					if(login.getRole().getId() == MasterDAO.ROLE_TYPE_ADMIN){
						
						viewType = "admin";
						
					} else {
						
						viewType = "eventRegistration";
						
					}
				}
				
			    
			} else {
			    response.sendRedirect("login.jsp");
			}
			
			// Initialize Data Access
			DataDAO dao = new DataDAO(request, response, viewType); 

			// Return data
			dao.process();
			
			// Redirect
			String redirection = viewType.equals("PDF") ? viewType : viewType + ".jsp"; // move pdf from servlet to DataDAO. 
			
			RequestDispatcher rd = request.getRequestDispatcher(redirection);
			response.setContentType("text/html");
			
			rd.forward(this.request, this.response);
			
			// New Redirect for pdf
//			String redirection = viewType + ".jsp"; // move pdf from servlet to DataDAO. 
//			
//			if(!viewType.equals("PDF")){
//				response.setContentType("text/html");
//				RequestDispatcher rd = request.getRequestDispatcher(redirection);
//			} else {
//				response.setContentType("application/pdf");
//			}
//			
//			rd.forward(this.request, this.response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	/** Description of processSubmission(request, response)
	 * 
	 *	 	
	 * 			
	 * @return			 processes data changes to be submitted to JSP.
	 */
	private void processSubmission(){
		
		// Initialize Submission 
		String viewType = getServletConfig().getInitParameter("viewType");
		SubmissionDAO dao = new SubmissionDAO(request, response, viewType);
		
		// Process Submission
		dao.process();
		
	}
	
}
