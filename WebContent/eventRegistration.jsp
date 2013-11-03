<%@ include file="/includes/shell/shell_top.jsp"%>
<%-- <%@ page import = "edu.weber.ntm.fblaem.beans.beanNameHere"%> --%>
<%-- <jsp:useBean id="" class="edu.weber.ntm.fblaem.beans.beanNameHere" scope="session"/> --%>
<%-- <jsp:setProperty name="beanNameHere" property="*"/> --%>

<%@ include file="/includes/shell/shell_header.jsp"%>

<script type="text/javascript">

	function clearNewStudent(){
		
		
		
	}

</script>

<style>
.event {
	
	margin-bottom: 20px;
	
}

.textInput {

	

}

</style>

<%@ include file="/includes/shell/shell_body.jsp"%>
<!-- Begin Page Content -->

<form action="" method="get">

	<div id="title" class="titleDiv">
			Event Registration - <span style="font-size: 20px;"> Teacher Name | School Representing <%System.out.println("Teacher Name | School Representing");%></span>
	</div>
	
	<div id="registerStudent" class="pageContainer event">

		<div class="textInput">
			Add Student
		
			First Name: <input type="text" id="firstName"/>
			
			Last Name: <input type="text" id="lastName"/>
		
			<input type="submit" value=" submit "/>
			
			<input type="submit" onclick="clearNewStudent()" value=" clear "/>
			
		</div>
		
	</div>
	
	<!-- loop through all of the different events  -->
	<%for(int i=0; i < 5; i++){ %>
		<div id="availableEvents" class="pageContainer event">
			<div>
				test
			</div>
		</div>
	<%} %>
</form>

<!-- End Page Content -->
<%@ include file="/includes/shell/shell_footer.jsp"%>
