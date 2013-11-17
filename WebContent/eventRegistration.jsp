<%@page import="org.hibernate.Hibernate"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.StudentTeam"%>
<%@ include file="/includes/Shell/shell_top.jsp"%>

<!-- IMPORTS ------------------------------------------------- -->
<%@page import="edu.weber.ntm.fblaem.databaseio.Teacher"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.School"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Event"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.StudentTeam"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Student"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.DatabaseConnection"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.Transaction"%>

<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
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
	function cancelEntry(type, eventId){
		
		if(type == "student"){
			$("#firstName" + eventId).val("First Name");
			$("#lastName" + eventId).val("Last Name");
			showDiv("addStudent" + eventId);
		} else{
			$('#teamName' + eventId).val("Team Name");
			showDiv("addTeam" + eventId);
		}
		
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
		// set values needed in controller here.	
	}
</script>

<%@ include file="/includes/Shell/shell_body.jsp"%>

<!-- GET PAGE DATA -->
<%
List<Event> events = (List<Event>)request.getAttribute("events");

Teacher teacher = (Teacher)request.getAttribute("teacher");
School school = (School)request.getAttribute("school");
%>

<!-- |BEGIN PAGE CONTENT| -->
<!-- Submission Variables -->
<input type="hidden" id="eventId" value="">
<input type="hidden" id="pageAction" value="">

<div id="pageHeader" class="titleDiv" style="display: block; border-bottom: 3px solid; margin-bottom: 4px;">
		<div>
			Event Registration - <span style="font-size: 20px;"> <%=teacher.getFirstName() + " " + teacher.getLastName()%> | <%=school.getName()%></span>
		</div>
		<div id="navigation" style="font-size: 13px; margin-bottom: 10px;">
<%-- 		<%if(user.isAdmin()){ %> --%>
				<a href="admin.jsp">Admin</a>&nbsp;&nbsp;|&nbsp;&nbsp;
<%-- 		<%} %> --%>
			<a href="eventRegistration.jsp">Event Registration</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="">Profile</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="login.jsp">Logout</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="exportEvent?eventId=-1"><img src="<%=pdf%>"/> Export All Events</a>
			
		</div>
</div>

<%
Set<Student> availableStudents = (Set<Student>)school.getStudents();

for(int i=0; i < events.size(); i++){ 
	
	Event event = events.get(i);
	String maxTeams = Integer.toString(event.getMaxEntriesPerSchool());

	Set<StudentTeam> studentEventTeams = (Set<StudentTeam>)event.getStudentEventTeams();
	List<StudentTeam> schoolEventTeams = new ArrayList<StudentTeam>();
	System.out.println("here3");
	Iterator<StudentTeam> itr = (Iterator<StudentTeam>)studentEventTeams.iterator();

    while(itr.hasNext()) {
    	StudentTeam StudentTeam = (StudentTeam)itr.next();
    	if(StudentTeam.getTeacher().getId() == teacher.getId()){
    		schoolEventTeams.add(StudentTeam);        	
        }
    }

	if(schoolEventTeams.size() < event.getMaxEntriesPerSchool()){ 
		maxTeams = "href";
	}
	
%>
<!-- C form to use method post for when it submits, get for when grabbing the page (forward in post to go to get). -->
	<form name="eventRegistration<%=event.getId()%>" action="EventRegistration" method="post">
	
		<div id="availableEvents" class="pageContainer event">
		
			<div id="header" style="border-bottom: 2px solid;">
				<div id="title">
				
					<%=event.getName() %> | <%=event.getEventType() %> | <a href="exportEvent?eventId=" style="font-weight: normal;"><img src="<%=pdf%>"/> Export Event</a>
					
				</div>
				
				<div id="link" style="width: 90px;">
				
					<a <%=maxTeams%>="javascript:void(0)" onclick="showDiv('addTeam<%=event.getId()%>');">Add Team</a>
					
				</div>
				
				<div id="link">
				
					<a href="javascript:void(0)" onclick="showDiv('addStudent<%=event.getId()%>');">Register Student</a>
					
				</div>				
			</div>
			
			<div id="description" style="width: 780px; margin-bottom: 20px; margin-left: 9px;">
			
				<%=event.getDetails()%>
				
			</div>				
			
			<div id="addStudent<%=event.getId()%>" class="addEntryDiv" style="display: none">
		
				<input type="text" id="firstName<%=event.getId()%>" value="First Name" onFocus="this.value=''" onblur="checkEntry('firstName', <%=event.getId()%>)"/>
				<input type="text" id="lastName<%=event.getId()%>" value="Last Name" onFocus="this.value=''" onblur="checkEntry('lastName, <%=event.getId()%>')"/>
				
				<select id="team<%=event.getId()%>">
					<% 
					if(schoolEventTeams.size() > 0){
						%>
						<option value="-1">Select Team</option>
						<optgroup label="-------------------------"></optgroup>
						<%
						for(int team = 0; team < schoolEventTeams.size(); team++){
							
							// HOW DO I GET THE NUMBER OF REGISTERED STUDENTS FOR AN EVENT????
// 							StudentTeam StudentTeam = schoolEventTeams.get(team);
// 							String eventMaxed = "";
// // 							StudentTeam.getTeam().getMaxIndividuals()
// 							StudentTeam.getEventInstance().getEvent().
							Boolean maxStudentsForEvent = true;
							
							// if max # of teams reached do not allow this to create new team.
							if(!maxStudentsForEvent){%> 
								<option value="<%=i%>">Team <%=team%></option>
							<%}
						} 
					} else {%>
						<option>No Teams</option>
					<%}
					
					%>
					
				</select>
									
				<input type="submit" onclick="submitChanges(eventId, type)" value=" Register Student "/>
				<input type="button" onclick="cancelEntry('student', <%=event.getId()%>)" value=" Cancel "/>
			
			</div>
			
			<div id="addTeam<%=event.getId()%>" class="addEntryDiv" style="display: none">
		
				<input type="text" id="teamName<%=event.getId()%>" value="Team Name" onFocus="this.value=''" onblur="checkEntry('teamName')"/>
				
				<%					
				String disableSubmit = "disable";
				
				if(schoolEventTeams.size() < event.getMaxEntriesPerSchool()){
					
					disableSubmit = "";
					
				} %>
				
				<input type="submit" onclick="submitChanges(eventId, type)" <%=disableSubmit %>value=" Add Team "/>
				<input type="button" onclick="cancelEntry('team', <%=event.getId()%>)" value=" Cancel "/>
			
			</div>
			
			<div style="border-top: 2px solid;">
				<%
				Iterator<StudentTeam> schoolTeamsItr = (Iterator<StudentTeam>)schoolEventTeams.iterator();
				
			    while(schoolTeamsItr.hasNext()) {
				
					StudentTeam StudentTeam = (StudentTeam)schoolTeamsItr.next();%>
				
					<div style="border-color:#848369">
						<div id="title" style="width: 705px;">
						
							&nbsp;&nbsp;<%=StudentTeam.getTeam().getName()%> ( <%="?/" + StudentTeam.getTeam().getMaxIndividuals()%> )
							
						</div>
		
						<div id="link">
						
							<a <%=maxTeams%>="javascript:void(0)" onclick="remove(eventId, type, teamId);">Remove Team</a>
							
						</div>
						
					</div>
					
					<%
					// not sure how to get students registered for an event.  Need to not allow removal unless correct school
					for(int teamMembers = 0; teamMembers < 5; teamMembers++){ %>
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
<%@ include file="/includes/Shell/shell_footer.jsp"%>
