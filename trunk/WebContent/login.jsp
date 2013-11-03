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
</style>

<%@ include file="/includes/shell/shell_body.jsp"%>
<!-- Begin Page Content -->

<form action="" method="get">

	<div id="main" style="width:100%; height: 100%;">
		<div id="title" class="centerPage">
			Welcome to the FBLA Event Manager
		</div>
		<div id="login" class="centerPage">
			<table>
				<tr><td>Username: </td><td><input type="text" id="username" style="width: 200px;" /></td></tr>
				<tr><td>Password: </td><td><input type="password" id="password" style="width: 200px;" /></td></tr>
			</table>	
		</div>
	</div>
	
</form>

<!-- End Page Content -->
<%@ include file="/includes/shell/shell_footer.jsp"%>
