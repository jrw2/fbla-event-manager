<%@ include file="/includes/shell/shell_top.jsp"%>
<%-- <%@ page import = "edu.weber.ntm.fblaem.beans.beanNameHere"%> --%>
<%-- <jsp:useBean id="" class="edu.weber.ntm.fblaem.beans.beanNameHere" scope="session"/> --%>
<%-- <jsp:setProperty name="beanNameHere" property="*"/> --%>

<%@ include file="/includes/shell/shell_header.jsp"%>

<script type="text/javascript">



</script>

<style>

#login {

	width: 300px;
	height: 75px;
	background-color: #ebdc96;
	padding: 10px;
	border: 1px solid;
	border-radius: 5px;
	
}

#title{

	font-size: 14px;
	font-weight: bold;
	margin-bottom: 10px;

}

</style>

<%@ include file="/includes/shell/shell_body.jsp"%>
<!-- Begin Page Content -->

<form action="j_security_check" method="post">

	<div id="main" style="width:100%; height: 100%;">
		<div id="title" class="centerPage">
			Welcome to the FBLA Event Manager
		</div>
		<div id="login" class="centerPage">
			<table>
				<tr><td>Username: </td><td><input type="text" id="username" name="j_username" style="width: 200px;" /></td></tr>
				<tr><td>Password: </td><td><input type="password" id="password" name="j_password" style="width: 200px;" /></td></tr>
			</table>	
		</div>
		<div class="centerPage" style="margin-top: 10px;">
			<input type="submit" value="Sign in"/>
		</div>		
	</div>
	
</form>

<!-- End Page Content -->
<%@ include file="/includes/shell/shell_footer.jsp"%>
