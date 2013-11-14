<%@ include file="/includes/shell/shell_top.jsp"%>
<%-- <%@ page import = "edu.weber.ntm.fblaem.beans.beanNameHere"%> --%>
<%-- <jsp:useBean id="" class="edu.weber.ntm.fblaem.beans.beanNameHere" scope="session"/> --%>
<%-- <jsp:setProperty name="beanNameHere" property="*"/> --%>

<%@ include file="/includes/shell/shell_header.jsp"%>

<style>
	.event {
		padding: 10px;
	}
	.newData {
		width: 800px; 
		margin-left: auto;
		margin-right: auto;
		overflow:hidden;	
	}
	.addEntryDiv {
		width: 90%;
		margin: 0 auto;
		text-align: center;
	}
	#registerStudent {
		width: 800px;
		margin-left: auto;
		margin-right: auto;
		position: relative;
	}
	#header {
		width: 790px;
		margin-bottom: 10px;
		float: left;
		horizontal-align: center;
	}
	#title {
		width: 590px;
		float: left;
		font-weight: bold;
	}
	#teamMembers {
		width: 590px;
		float: left;
		margin-left: 30px;
	}
	#link {
		float: left;
	}
</style>
<script type="text/javascript">

	function cancelEntry(type, eventId){
		
		if(type == "createLogin"){
			$("#userName").val("User Name");
			$("#password").val("Password");
		} else if(type == "createEvent") {
			$("#eventDescription").val("Event Description");
			$("#eventName").val("Event Name");
			$("#numTeams").val("Number of Teams");
			$("#teamSize").val("Team Size");
		} else if(type == "deleteLogin") {
			$("#deleteUserLogin").val("-1");
		} else if(type == "generateBilling") {
			$("#schoolBilling").val("-1");
		} else if(type == "modifyEvent") {
			$("#eventDescription" + eventId).val("Event Description");
			$("#eventName" + eventId).val("Event Name");
			$("#numTeams" + eventId).val("Number of Teams");
			$("#teamSize" + eventId).val("Team Size");					
		}
		
		showDiv(type);
		
	}
	
	function checkEntry(id, eventId){
		
		alert('id: ' + id);
		alert('eventId: ' + eventId);
		
		var elementId = "#" + id + eventId;
		
		if(id == "eventDescription"){
			replacement = "Event Description";
		}
		if(id == "eventName"){
			replacement = "Event name";
		}
		if(id == "numTeams"){
			replacement =  "Number of Teams";
		}
		if(id == "teamSize"){
			replacement = "Team Size";
		}
			
		$("#" + id + eventId).val(replacement);
		
	}
			
	function submitChanges(eventId, type){
		
	}
</script>

<%@ include file="/includes/shell/shell_body.jsp"%>

<!--  SUBMISSION LOGIC -->
<%


%>
<!-- |BEGIN PAGE CONTENT| -->
<!-- Submission Variables -->
<input type="hidden" id="eventId" value="">
<input type="hidden" id="pageAction" value="">

<div id="pageHeader" class="titleDiv" style="display: block; border-bottom: 3px solid; margin-bottom: 4px; width: 900px;">
		<div>
			Administration - <span style="font-size: 20px;">Admin Name <%System.out.println("Admin Name | School Representing");%></span>
		</div>
		<div id="navigation" style="font-size: 13px; margin-bottom: 10px;">
<%-- 		<%if(user.isAdmin()){ %> --%>
				<a href="admin.jsp">Admin</a>&nbsp;&nbsp;|&nbsp;&nbsp;
<%-- 		<%} %> --%>
			<a href="">Profile</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="eventRegistration.jsp">Event Registration</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="login.jsp">Logout</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="exportEvent?eventId=-1">Export All Events</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="showDiv('createEvent');">Add New Event</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="showDiv('createLogin');">Add New Login</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="showDiv('deleteLogin');">Delete Login</a>&nbsp;&nbsp;|&nbsp;&nbsp;
<%-- 		<%if(user.isAdmin()){ %> --%>
				<a href="javascript:void(0)" onclick="showDiv('generateBilling')">Billing</a>
<%-- 		<%} %> --%>
			
		</div>
</div>
<div id="availableEvents" class="newData event">

	<div id="createEvent" class="addEntryDiv" style="display: none">
	
		<input type="text" id="eventDescription<%=-3%>" style="width: 598px; height: 130px;" value="Event Description" onFocus="this.value=''" onblur="checkEntry('eventDescription', -3)"/>
		<input type="text" id="eventName<%=-3%>" value="Event Name" onFocus="this.value=''" onblur="checkEntry('eventName', -3)"/>
		<input type="text" id="numTeams<%=-3%>" value="Number of Teams" onFocus="this.value=''" onblur="checkEntry('numTeams', -3)"/>
		<input type="text" id="teamSize<%=-3%>" value="Team Size" onFocus="this.value=''" onblur="checkEntry('teamSize', -3)"/>
		
		<input type="submit" onclick="submitChanges(eventId, type)" value=" Modify Event "/>
		<input type="submit" onclick="cancelEntry('createEvent', -3)" value=" Cancel "/>
	
	</div>
	
	<div id="createLogin" class="addEntryDiv" style="display: none">
	
		User Name: <input type="text" id="userName" value="" onFocus="this.value=''" onblur="checkEntry('userName', -4)"/>
		Password: <input type="password" id="password" value="" onFocus="this.value=''" onblur="checkEntry('password, -4')"/>
		
		<input type="submit" onclick="submitChanges(eventId, type)" value=" Create Login "/>
		<input type="submit" onclick="cancelEntry('createLogin', -4)" value=" Cancel "/>
	
	</div>
	
	<div id="deleteLogin" class="addEntryDiv" style="display: none">

		<select id="deleteUserLogin" style="width: 300px;">
			<option value="-1">Select User Login</option>
			<optgroup label="-------------------------"></optgroup>
			<%
			for(int loginId = 0; loginId < 5; loginId++){%>
				<option value="<%=loginId%>">User Login Name - School <%=loginId%></option>
			<%} %>
		</select>
	
		<input type="submit" onclick="submitChanges(eventId, type)" value=" Delete Login "/>
		<input type="submit" onclick="cancelEntry('deleteLogin', -5)" value=" Cancel "/>

	</div>	
	
	<div id="generateBilling" class="addEntryDiv" style="display: none">

		<select id="schoolBilling" style="width: 300px;">
			<option value="-1">Select School to Bill</option>
			<optgroup label="-------------------------"></optgroup>
			<option value="-1">All Schools</option> <!-- Pass into export to print all billings -->
			<% 
			for(int schoolId = 0; schoolId < 5; schoolId++){%>
				<option value="<%=schoolId%>">School<%=schoolId%></option>
			<%} %>
		</select>
	
		<input type="submit" onclick="submitChanges(eventId, type)" value=" Create Billing "/>
		<input type="submit" onclick="cancelEntry('generateBilling', -6)" value=" Cancel "/>

	</div>		

</div>	

<!-- 
	loop through all of the different events.
	Replace i with the eventTitle and then use that event title to access and submit the specific event.
-->
 
	<%for(int i=0; i < 5; i++){ 
		
		String maxTeams = "";
		Boolean maxTeamsForEvent = true;
		
		if(maxTeamsForEvent){ // if max # of teams reached do not allow this to create new team.
			maxTeams = "href";
		}
		
	%>
	
		<form name="eventRegistration<%=i%>" action="" method="get">
		
			<div id="availableEvents" class="pageContainer event">
			
				<div id="header" style="border-bottom: 2px solid;">
					<div id="title">
					
						Event Name - <a href="exportEvent?eventId=" style="font-weight: normal;">Export Event</a>
						
					</div>
					
					<div id="link" style="width: 90px;">
					
						<a <%=maxTeams%>="javascript:void(0)" onclick="showDiv('modifyEvent<%=i%>');">Modify Event</a>
						
					</div>
					
					<div id="link">
					
						<a href="javascript:void(0)" onclick="">Remove Event</a>
						
					</div>				
				</div>
				
				<div id="description" style="width: 780px; margin-bottom: 20px; margin-left: 9px;">
				
					This is the event description.This is the event description.This is the event description.This is the event description.
					This is the event description.This is the event description.This is the event description.This is the event description.
					This is the event description.This is the event description.This is the event description.This is the event description.
					
				</div>						
		
				
				<div id="modifyEvent<%=i%>" class="addEntryDiv" style="display: none">
			
					<input type="text" id="eventDescription<%=i%>" style="width: 598px; height: 130px;" value="Event Description" onFocus="this.value=''" onblur="checkEntry('eventDescription',<%=i%>)"/>
					<input type="text" id="eventName<%=i%>" value="Event Name" onFocus="this.value=''" onblur="checkEntry('eventName',<%=i%>)"/>
					<input type="text" id="numTeams<%=i%>" value="Number of Teams" onFocus="this.value=''" onblur="checkEntry('numTeams',<%=i%>)"/>
					<input type="text" id="teamSize<%=i%>" value="Team Size" onFocus="this.value=''" onblur="checkEntry('teamSize',<%=i%>)"/>
					
					<input type="submit" onclick="submitChanges(eventId, type)" value=" Modify Event "/>
					<input type="submit" onclick="cancelEntry('modifyEvent', <%=i%>)" value=" Cancel "/>
				
				</div>
				
				<div style="border-top: 2px solid;">
					<%for(int teams=0; teams < 4; teams++){ %>
						<div style="border-color:#848369">
							<div id="title" style="width: 705px;">
							
								&nbsp;&nbsp;Team # <%=teams%> ( 3/5 )
								
							</div>
			
							<div id="link">
							
								<a <%=maxTeams%>="javascript:void(0)" onclick="remove(eventId, type, teamId);">Remove Team</a>
								
							</div>
							
						</div>
						
						<%for(int teamMembers = 0; teamMembers < 5; teamMembers++){ %>
							<div style="border-color:#848369; margin-left: 200px;">
								
								<div id="teamMem">
								
									Norm Johnson <%=teamMembers%>
									
								</div>
				
							</div>
						<%} %>
					<%} %>
				</div>
			</div>			
			
		</form>
		
	<%} %>

<!-- End Page Content -->
<%@ include file="/includes/shell/shell_footer.jsp"%>
