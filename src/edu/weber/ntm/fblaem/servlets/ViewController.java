package edu.weber.ntm.fblaem.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.weber.ntm.fblaem.DAO.DataDAO;
import edu.weber.ntm.fblaem.DAO.SubmissionDAO;

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
		
		verifyActiveUser();
		
		processRequest();
		
	}
	
	/**
	 * Page forms submit to this method for processing.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.request = request;
		this.response = response;
		
		verifyActiveUser();
		
		if(request.getParameter("errorMessage") == null){
			processSubmission();
		}
		
		processRequest();
		
	}
	
	private void processRequest(){
		
		try {
			
			String viewType = getServletConfig().getInitParameter("viewType");
			
			// Initialize Data Access
			DataDAO dao = new DataDAO(request, viewType); 

			// Return data
			dao.process();
			
			// Redirect
			String redirection = viewType.equals("PDF") ? viewType : viewType + ".jsp"; // move pdf from servlet to DataDAO. 
			RequestDispatcher rd = request.getRequestDispatcher(redirection);
			
			response.setContentType("text/html");
			rd.forward(request, response);
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void processSubmission(){
		
		// Initialize Submission 
		String viewType = getServletConfig().getInitParameter("viewType");
		SubmissionDAO dao = new SubmissionDAO(request, viewType);
		
		// Process Submission
		dao.process();
		
		// Redirect
		processRequest();
	
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
