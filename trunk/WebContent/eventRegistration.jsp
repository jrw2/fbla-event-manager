<%@ include file="/includes/shell/shell_top.jsp"%>
<%-- <%@ page import = "edu.weber.ntm.fblaem.beans.beanNameHere"%> --%>
<%-- <jsp:useBean id="" class="edu.weber.ntm.fblaem.beans.beanNameHere" scope="session"/> --%>
<%-- <jsp:setProperty name="beanNameHere" property="*"/> --%>

<%@ include file="/includes/shell/shell_header.jsp"%>

<style>
	.event {
		margin-bottom: 20px;
		padding: 10px;
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
		if(type == "student"){
			$('#firstName' + eventId).reset();
			$('#lastName' + eventId).reset();
			$('#team' + eventId).val("-1");
		} else {
			$('#teamName' + eventId).reset();
		}
		
		showDiv(type);
	}
	
	function checkEntry(id, eventId){
		if($("#" + id).val() == ""){
			if(id == "firstName"){
				$("#" + id + eventId).val("First Name");
			} else if(id == "lastName"){
				$("#" + id + eventId).val("Last Name");
			} else if(id == "teamName"){
				$("#" + id + eventId).val("Team Name");
			} 
		}
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

<div id="pageHeader" class="titleDiv" style="display: block; border-bottom: 3px solid; margin-bottom: 4px;">
		<div>
			Event Registration - <span style="font-size: 20px;"> Teacher Name | School Representing <%System.out.println("Teacher Name | School Representing");%></span>
		</div>
		<div id="navigation" style="font-size: 13px; margin-bottom: 10px;">
<%-- 		<%if(user.isAdmin()){ %> --%>
				<a href="admin.jsp">Admin</a>&nbsp;&nbsp;|&nbsp;&nbsp;
<%-- 		<%} %> --%>
			<a href="eventRegistration.jsp">Event Registration</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="">Profile</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="login.jsp">Logout</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="exportEvent?eventId=-1">Export All Events</a>
			
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
					
						<a <%=maxTeams%>="javascript:void(0)" onclick="showDiv('addTeam<%=i%>');">Add Team</a>
						
					</div>
					
					<div id="link">
					
						<a href="javascript:void(0)" onclick="showDiv('addStudent<%=i%>');">Register Student</a>
						
					</div>				
				</div>
				
				<div id="description" style="width: 780px; margin-bottom: 20px; margin-left: 9px;">
				
					This is the event description.This is the event description.This is the event description.This is the event description.
					This is the event description.This is the event description.This is the event description.This is the event description.
					This is the event description.This is the event description.This is the event description.This is the event description.
					
				</div>				
				
				<div id="addStudent<%=i%>" class="addEntryDiv" style="display: none">
			
					<input type="text" id="firstName<%=i%>" value="First Name" onFocus="this.value=''" onblur="checkEntry('firstName', <%=i%>)"/>
					<input type="text" id="lastName<%=i%>" value="Last Name" onFocus="this.value=''" onblur="checkEntry('lastName, <%=i%>')"/>
					
					<select id="team<%=i%>">
						<option value="-1">Select Team</option>
						<optgroup label="-------------------------"></optgroup>
						<% // Switch this to grab actual list of available teams, and only show option to add if available.
						
						for(int team = 0; team < 5; team++){
							String eventMaxed = "";
							Boolean maxStudentsForEvent = true;
							
							// if max # of teams reached do not allow this to create new team.
							if(!maxStudentsForEvent){%> 
								<option value="<%=i%>">Team <%=team%></option>
							<%}
						} %>
					</select>
										
					<input type="submit" onclick="submitChanges(eventId, type)" value=" Register Student "/>
					<input type="button" onclick="cancelEntry('student', <%=i%>)" value=" Cancel "/>
				
				</div>
				
				<div id="addTeam<%=i%>" class="addEntryDiv" style="display: none">
			
					<input type="text" id="teamName<%=i%>" value="Team Name" onFocus="this.value=''" onblur="checkEntry('teamName')"/>
					
					<input type="submit" onclick="submitChanges(eventId, type)" value=" Add Team "/>
					<input type="submit" onclick="cancelEntry('team', <%=i%>)" value=" Cancel "/>
				
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
								
								<div id="link">
								
									<a <%=maxTeams%>="javascript:void(0)" onclick="remove(eventId, type, teamMemberId);" style="font-size: 13px; color: red;">X&nbsp;&nbsp;</a>
									
								</div>
								
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
