package edu.weber.ntm.fblaem.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		name="Logout", 
		urlPatterns={"/Logout"}
)

public class Logout extends HttpServlet{
	
	private static final long serialVersionUID = 3743123062179776667L;
	
	public Logout()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("isActive");
		request.getSession().invalidate();
		response.sendRedirect("login.jsp");
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("isActive");
		request.getSession().invalidate();
		response.sendRedirect("login.jsp");
		
	}


}
