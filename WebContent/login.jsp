<%@ include file="/includes/Shell/shell_top.jsp"%>
<%-- <%@ page import = "edu.weber.ntm.fblaem.beans.beanNameHere"%> --%>
<%-- <jsp:useBean id="" class="edu.weber.ntm.fblaem.beans.beanNameHere" scope="session"/> --%>
<%-- <jsp:setProperty name="beanNameHere" property="*"/> --%>

<%@ include file="/includes/Shell/shell_header.jsp"%>

<script type="text/javascript">

</script>

<style>

#login {

	width: 250px;
	height: 50px;

	padding: 10px;
	border-radius: 5px;
	
}

#title{

	font-size: 20px;
	font-weight: bold;
	border-bottom: 1px solid;

}

#main{
	margin-top:100px;
}
</style>

<%@ include file="/includes/Shell/shell_body.jsp"%>
<!-- Begin Page Content -->

<%

Boolean loginError = false;

if(request.getParameter("failed") != null){
	
	loginError = true;
}

%>

<form action="j_security_check" method="post">

	<div id="main" style="width:100%; height: 100%;">
		<div id="title" class="centerPage">
			WELCOME TO THE FBLA EVENT MANAGER
		</div>
		<div id="login" class="centerPage">
			<table>
				<tr><td>Username: </td><td><input type="text" id="username" name="j_username" style="width: 150px;" /></td></tr>
				<tr><td>Password: </td><td><input type="password" id="password" name="j_password" style="width: 150px;" /></td></tr>
			</table>	
		</div>
		<div class="centerPage" style="margin-top: 10px;">
			<input type="submit" value="Sign in"/>
		</div>	
		<%if(loginError){ %>
			<div class="centerPage" style="margin-top: 10px;">
				<span style="color: red;">Incorrect Username and/or Password</span>
			</div>
		<%} %>			
			
	</div>
	
</form>

<!-- End Page Content -->
<%@ include file="/includes/Shell/shell_footer.jsp"%>
