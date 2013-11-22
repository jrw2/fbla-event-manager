package edu.weber.ntm.fblaem.servlets;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.weber.ntm.fblaem.databaseio.*;

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

		Boolean x =request.isUserInRole("Administrator");
		Principal x1 = request.getUserPrincipal();
		HttpSession s = request.getSession();
		request.logout();
		request.getSession().invalidate();
		response.sendRedirect("login.jsp");
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getSession().invalidate();
		response.sendRedirect("Login.jsp");
		
	}


}
