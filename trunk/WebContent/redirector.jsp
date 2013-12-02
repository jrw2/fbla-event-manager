<%@ include file="/includes/Shell/shell_top.jsp"%>

<!-- IMPORTS ------------------------------------------------- -->
<%@page import="javax.servlet.RequestDispatcher"%>
<%@page import="javax.servlet.ServletException"%>
<%@page import="javax.servlet.http.HttpServlet"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Student"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.EventInstance"%>
<!-- --------------------------------------------------------- -->

<%@ include file="/includes/Shell/shell_header.jsp"%>

<script type="text/javascript"></script>

<style></style>

<%@ include file="/includes/Shell/shell_body.jsp"%>
<!-- Begin Page Content -->

<%
	System.out.println("this is working asdfasdfasdf");
	ServletContext context = getServletContext();
	RequestDispatcher rd = context.getRequestDispatcher("/Login");
	rd.forward(request, response);
%>

<!-- End Page Content -->
<%@ include file="/includes/Shell/shell_footer.jsp"%>
