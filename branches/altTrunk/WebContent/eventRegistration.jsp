<%@ include file="/includes/Shell/shell_top.jsp"%>

<!-- IMPORTS ------------------------------------------------- -->
<%@page import="edu.weber.ntm.fblaem.databaseio.Teacher"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.School"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Event"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.StudentTeam"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Team"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Student"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.DatabaseConnection"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.EventInstance"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.Hibernate"%>
<%@page import="org.hibernate.HibernateException"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="org.hibernate.Query"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="org.hibernate.cfg.Configuration"%>
<%@page import="java.util.*"%>
<!-- --------------------------------------------------------- -->

<%@ include file="/includes/Shell/shell_header.jsp"%>

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

	function cancelEntry(type, eventInstanceId){
		
		if(type == "student"){
			$("#firstName" + eventInstanceId).val("First Name");
			$("#lastName" + eventInstanceId).val("Last Name");
			showDiv("addStudent" + eventInstanceId);
		} else{
			$('#teamName' + eventInstanceId).val("Team Name");
			showDiv("addTeam" + eventInstanceId);
		}
			
	}
	
	function checkEntry(id, eventInstanceId){
		if($("#" + id + eventInstanceId).val() == ""){
			if(id == "firstName"){
				$("#" + id + eventInstanceId).val("First Name");
			} else if(id == "lastName"){
				$("#" + id + eventInstanceId).val("Last Name");
			} else if(id == "teamName"){
				$("#" + id + eventInstanceId).val("Team Name");
			} 
		}
	}
	
	function addTeam(eventInstanceId){
		var newTeamName = $("#teamName" + eventInstanceId).val(); 
		if(newTeamName != "" && newTeamName != "Team Name"){
			$("#teamName").val($("#teamName" + eventInstanceId).val());
			$("#eventInstanceId").val(eventInstanceId);
			$("#pageAction").val("addTeam");
		} else {
			$("#errorMessage").val("Invalid Team Name");
			$("#pageAction").val("error");	
		}
		
		document.submissionForm.submit();
	}
	function removeTeam(eventInstanceId, teamId){
		$("#teamId").val(teamId);
		$("#eventInstanceId").val(eventInstanceId);
		$("#pageAction").val("removeTeam");
		
		document.submissionForm.submit();
	}
	function removeStudentFromTeam(studentId, teamId, eventInstanceId){
		$("#teamId").val(teamId);
		$("#eventInstanceId").val(eventInstanceId);
		$("#studentId").val(studentId);
		$("#pageAction").val("removeStudentFromTeam");
		
		document.submissionForm.submit();
	}		
	
	function addStudent(eventInstanceId){
		
			$("#firstName").val($("#firstName" + eventInstanceId).val());
			$("#lastName").val($("#lastName" + eventInstanceId).val());
			$("#eventInstanceId").val(eventInstanceId);
			$("#teamId").val($("#team" + eventInstanceId).val());
			$("#pageAction").val("registerStudent");
			
			if($("#team" + eventInstanceId).val() >= 0){
				document.submissionForm.submit();
			}
			
	}
	
</script>

<%@ include file="/includes/Shell/shell_body.jsp"%>

<!-- GET PAGE DATA -->
<%
List<Event> events = (List<Event>)request.getAttribute("events");
Teacher teacher = (Teacher)request.getAttribute("teacher");
School school = (School)request.getAttribute("school");
String validation = (String)request.getAttribute("errorValue") != null ? (String)request.getAttribute("errorValue") : "";
%>

<!-- |BEGIN PAGE CONTENT| -->
<!-- Submission Type -->
<form name="submissionForm" action="EventRegistration" method="post">
	<input type="hidden" name="pageAction" id="pageAction" value="">
	
	<!-- Team Submission / Removal -->
	<input type="hidden" name="teamName" id="teamName" value="">
	<input type="hidden" name="MaxIndividuals" id="MaxIndividuals" value=""> <!--  Not Implemented -->
	<input type="hidden" name="teamId" id="teamId" value=""> 
	
	<!-- Student Team Removal -->
	<input type="hidden" name="studentId" id="studentId" value="">
	
	<!-- General Submission -->
	<input type="hidden" name="eventInstanceId" id="eventInstanceId" value="">	
	<input type="hidden" name="eventId" id="eventId" value="">
	
	<!-- Student Submission -->
	<input type="hidden" name="firstName" id="firstName" value="">	
	<input type="hidden" name="lastName" id="lastName" value="">
	
	<!-- Validation -->
	<input type="hidden" id="errorMessage" value="">
</form>

<div id="pageHeader" class="titleDiv" style="display: block; border-bottom: 3px solid; margin-bottom: 4px;">
		<div>
			Event Registration - <span style="font-size: 20px;"> <%=teacher.getFirstName() + " " + teacher.getLastName()%> | <%=school.getName()%></span>
		</div>
		<div id="navigation" style="font-size: 13px; margin-bottom: 10px;">
<%-- 		<%if(user.isAdmin()){ %> --%>
				<a href="Administration">Admin</a>&nbsp;&nbsp;|&nbsp;&nbsp;
<%-- 		<%} %> --%>
			<a href="eventRegistration.jsp">Event Registration</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="exportEvent?eventId=-1"><img src="<%=pdf%>"/> Export All Events</a>
			<a href="Logout">Logout</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		</div>
		<%if(!validation.equals("")){ %>		
			<div id="navigation" style="font-size: 13px; margin-bottom: 10px;">
				
				<span style="font-size: 13px; font-weight: bold; color: red;"><%=validation%></span>
					
			</div>
		<%} %>	
</div>

<%for(int i=0; i < events.size(); i++){ 
	
	Event event = events.get(i);

	if(event.getEventInstances() != null){
		
		String maxTeamsPerSchool = Integer.toString(event.getMaxEntriesPerSchool());
		
		Iterator<EventInstance> itr = (Iterator<EventInstance>)event.getEventInstances().iterator();
	
	    while(itr.hasNext()) {
	    	
	    	EventInstance eventInstance = (EventInstance)itr.next();
	    	// if statement here to determine if instance is for this school.
	    	List<Team> studentInstanceTeams = new ArrayList<Team>();
	    	Set<Student> students = (Set<Student>)school.getStudents();
	    	Set<Team> teams = (Set<Team>)eventInstance.getTeams();
			
			
			if(studentInstanceTeams.size() < event.getMaxEntriesPerSchool()){ 
				maxTeamsPerSchool = "href";
			}
			// REMOVE THE ID ADDITION TO FIELDS, BUT LEAVE IT ON FORM NAME AND JUST SUBMIT THE FORM WITH GENERIC FIELD NAMES.
			%>
			<form name="eventRegistration<%=eventInstance.getId()%>" action="EventRegistration" method="post">
			
				<div id="availableEvents" class="pageContainer event">
				
					<div id="header" style="border-bottom: 2px solid;">
						<div id="title">

							<%=event.getName() %> | <%=event.getEventType().getTypeName() %> | <a href="exportEvent?eventId=" style="font-weight: normal;"><img src="<%=pdf%>"/> Export Event</a>
							
						</div>
						
						<div id="link" style="width: 90px;">
						
							<a <%=maxTeamsPerSchool%>="javascript:void(0)" onclick="showDiv('addTeam<%=eventInstance.getId()%>');">Add Team</a>
							
						</div>
						
						<div id="link">
						
							<a href="javascript:void(0)" onclick="showDiv('addStudent<%=eventInstance.getId()%>');">Register Student</a>
							
						</div>				
					</div>
					<%// WE NEED to handle null her as they won't have a description sometimes. 
					// we need to 
					
					%>
					<div id="description" style="width: 780px; margin-bottom: 20px; margin-left: 9px;">

						<%=event.getDetails() != null ? event.getDetails() : "No Description"%>
						
					</div>				
					
					<div id="addStudent<%=eventInstance.getId()%>" class="addEntryDiv" style="display: none">
				
						<input type="text" id="firstName<%=eventInstance.getId()%>" value="First Name" onFocus="this.value=''" onblur="checkEntry('firstName', <%=eventInstance.getId()%>)"/>
						<input type="text" id="lastName<%=eventInstance.getId()%>" value="Last Name" onFocus="this.value=''" onblur="checkEntry('lastName, <%=eventInstance.getId()%>)"/>
						
						<select id="team<%=eventInstance.getId()%>">
							<%if(teams.size() > 0){
								%>
								<option value="-1">Select Team</option>
								<optgroup label="-------------------------"></optgroup>
								<%
								for(Team team : teams){
									// need to create a way to insert a single student team
									// Just allow them to create a team that has a single student.
									Boolean maxStudentsForEvent = (teams.size() < event.getMaxEntriesPerSchool()) ? false : true;
									
									// if max # of teams reached do not allow this to create new team.
									if(!maxStudentsForEvent){%> 
										<option value="<%=team.getId()%>"><%=team.getName()%></option>
									<%}
								} 
							} else {%>
								<option>No Teams</option>
							<%}%>
							
						</select>
											
						<input type="button" onclick="addStudent(<%=eventInstance.getId()%>);" value=" Register Student "/>
						<input type="button" onclick="cancelEntry('student', <%=eventInstance.getId()%>)" value=" Cancel "/>
					
					</div>
					
					<div id="addTeam<%=eventInstance.getId()%>" class="addEntryDiv" style="display: none">
				
						<input type="text" id="teamName<%=eventInstance.getId()%>" value="Team Name" onFocus="this.value=''" onblur="checkEntry('teamName', <%=eventInstance.getId()%>)"/>
						
						<%					
						String disableSubmit = "disable";
						// move the code from admin here for creating teams.
						if(studentInstanceTeams.size() < event.getMaxEntriesPerSchool()){
							
							disableSubmit = "";
							
						} %>
						
						<input type="button" onclick="addTeam(<%=eventInstance.getId()%>)" <%=disableSubmit %>value=" Add Team "/>
						<input type="button" onclick="cancelEntry('team', <%=eventInstance.getId()%>)" value=" Cancel "/>
					
					</div>
					
					<div style="border-top: 2px solid;">
						<%
					   for(Team team : teams){
						
							Set<StudentTeam> studentTeams = (Set<StudentTeam>)team.getstudentTeams();%>
						
							<div style="border-color:#848369">
								<div id="title" style="width: 705px;">
									<%
										String enrolledStudents = Integer.toString(team.getstudentTeams().size());
										String maxIndividuals = (team.getMaxIndividuals() == null) ? "No Max" : team.getMaxIndividuals();
									%>
									&nbsp;&nbsp;<%=team.getName()%> ( <%=enrolledStudents + " / " + maxIndividuals%> )
									
								</div>
				
								<div id="link">
								
									<a <%=maxTeamsPerSchool%>="javascript:void(0)" onclick="removeTeam(<%=eventInstance.getId()%>, <%=team.getId()%>);">Remove Team</a>
									
								</div>
								
							</div>
							
							<%
							for(StudentTeam studentTeam : studentTeams){%>
								<div style="border-color:#848369; margin-left: 200px;">
									
									<div id="link">
									
										<a <%=maxTeamsPerSchool%>="javascript:void(0)" onclick="removeStudentFromTeam(<%=studentTeam.getId().getStudentId()%>, <%=team.getId()%>, <%=eventInstance.getId()%>);" style="font-size: 13px; font-weight: bold; color: red;">X&nbsp;&nbsp;</a>
										
									</div>
									
									<div id="teamMem">
									
										<%=studentTeam.getStudent().getFirstName() + " " + studentTeam.getStudent().getLastName()%>
										
									</div>
					
								</div>
							<%} 
						} %>
					</div>
				</div>			
			</form>
		<%}
	}
} %>

<!-- End Page Content -->
<%@ include file="/includes/Shell/shell_footer.jsp"%>
