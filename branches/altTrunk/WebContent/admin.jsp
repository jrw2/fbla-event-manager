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
	function submit(type){
		
		$("#pageAction").val(type);
		
		var submit = true;
		
		if(type == 'reset'){
			submit = confirm("This will remove all school and student data.  Would you like to continue?");
		}
		
		if(submit == true){
			document.submissionForm.submit();
		}
		
	}

	function cancelEntry(type){

		if(type == "addSchool"){
			$("#schoolName").val("User Name");
			$("#schoolAddress").val("Password");
			$("#schoolCity").val("User Name");
			$("#schoolState").val("Password");
			$("#schoolZip").val("User Name");
			$("#schoolPhone").val("Password");	
		} else if(type == "createEvent") {
			$("#eventDescription").val("Event Description");
			$("#eventName").val("Event Name");
		} else if(type == "createLogin") {
			$("#userName").val("");
			$("#password").val("");
		} else if(type == "deleteLogin") {
			$("#schoolBilling").val("-1");
		} else if(type == "modifyEvent") {
			$("#modifyDescription").val("Enter New Description");
			$("#modifyName").val("Enter New Name");
		}
			
		showDiv(type);
		
	}
	
	function checkEntry(id, eventId){

		var replacement = "";

		if(id == "schoolName"){
			replacement = "School Name";
		}
		if(id == "schoolAddress"){
			replacement = "Street Address";
		}
		if(id == "schoolCity"){
			replacement =  "City";
		}
		if(id == "schoolState"){
			replacement = "School State";
		}
		
		if(id == "schoolZip"){
			replacement = "Zip Code";
		}
			
		if(id == "schoolPhone"){
			replacement = "Phone";
		}
		
		if(id == "eventDescription"){
			replacement = "Event Description";
		}
		
		if(id == "eventName"){
			replacement = "Event Name";
		}
		
		if(id == "numTeams"){
			replacement = "Number of Teams";
		}
		
		if(id == "teamSize"){
			replacement = "Max Team Size";
		}
		
		if(id == "modifyDescription"){
			replacement = "Enter New Description";
		}
		
		if(id == "modifyName"){
			replacement = "Enter New Name";
		}		
		if(replacement != "" && eventId == -1){
			
			$("#" + id).val(replacement);
			
		} else if (replacement != ""){
			
			$("#" + id + eventId).val(replacement);
			
		}
		
	}
</script>

<%@ include file="/includes/Shell/shell_body.jsp"%>

<!-- GET PAGE DATA -->
<%List<Event> events = (List<Event>)request.getAttribute("events");
List<School> schools = (List<School>)request.getAttribute("schools");
List<Teacher> teacherLogins = (List<Teacher>)request.getAttribute("teacherLogins");

String validation = (String)request.getAttribute("errorValue") != null ? (String)request.getAttribute("errorValue") : "";%>

<!-- |BEGIN PAGE CONTENT| -->
<!-- Submission Type -->
<form name="submissionForm" action="Administraton" method="post">
<input type="hidden" name="pageAction" id="pageAction" value="">

	<div id="pageHeader" class="titleDiv" style="display: block; border-bottom: 3px solid; margin-bottom: 4px; width: 900px;">
		<div>
			Administration
		</div>
		<div id="navigation" style="font-size: 13px; margin-bottom: 10px;">
			<a href="javascript:void(0)" onclick="showDiv('createEvent');">New Event</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="showDiv('modifyEvent');">Modify Event</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="showDiv('addSchool');">Add School</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="showDiv('deleteSchool');">Delete School</a>&nbsp;&nbsp;|&nbsp;&nbsp;			
			<a href="javascript:void(0)" onclick="showDiv('createLogin');">Add Login</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="showDiv('deleteLogin');">Delete Login</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="submit('reset');">Reset Program</a>&nbsp;&nbsp;|&nbsp;&nbsp;			
			<a href="exportEvent?eventId=-1">Export All Events<img src="<%=pdf%>"/></a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="Logout">Logout</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		</div>
	</div>
	
	<div id="availableEvents" class="newData event">
	
		<div id="addSchool" class="addEntryDiv" style="display: none">
		
			<input type="text" id="schoolName" value="School Name" onFocus="this.value=''" onblur="checkEntry('schoolName', -1)"/>
			<input type="text" id="schoolAddress" value="Street Address" onFocus="this.value=''" onblur="checkEntry('schoolAddress', -1)"/>
			<input type="text" id="schoolCity" value="City" onFocus="this.value=''" onblur="checkEntry('schoolCity', -1)" />
			<input type="text" id="schoolState" value="UT" onFocus="this.value='UT'" onblur="checkEntry('schoolState', -1)" style="width: 10px;" disabled/>
			<input type="text" id="schoolZip" value="Zip Code" onFocus="this.value=''" onblur="checkEntry('schoolZip', -1)"/>
			<input type="text" id="schoolPhone" value="Phone" onFocus="this.value=''" onblur="checkEntry('schoolPhone', -1)" />
			
			<input type="button" onclick="submit('addSchool')" value=" Create Event "/>
			<input type="button" onclick="cancelEntry('addSchool')" value=" Cancel "/>
		
		</div>	

		<div id="deleteSchool" class="addEntryDiv" style="display: none">
		
			<select id="schools">
				<%if(schools.size() > 0){
					%>
					<option value="-1">Select School</option>
					<optgroup label="-------------------------"></optgroup>
					<%
					for(School school : schools){%>
						<option value="<%=school.getId()%>"><%=school.getName()%></option>
					<%}
				} else {%>
					<option>No Available Schools</option>
				<%}%>
				
			</select>		
			<input type="button" onclick="submit('deleteSchool')" value=" Delete School "/>
			<input type="button" onclick="cancelEntry('deleteSchool')" value=" Cancel "/>
		
		</div>		
		
		<div id="createEvent" class="addEntryDiv" style="display: none">
		
			<input type="text" id="eventDescription" style="width: 598px; height: 130px;" value="Event Description" onFocus="this.value=''" onblur="checkEntry('eventDescription', -1)"/>
			<input type="text" id="eventName" value="Event Name" onFocus="this.value=''" onblur="checkEntry('eventName', -1)"/>
			<input type="text" id="numTeams" value="Number of Teams" onFocus="this.value=''" onblur="checkEntry('numTeams', -1)"/>
			<input type="text" id="teamSize" value="Max Team Size" onFocus="this.value=''" onblur="checkEntry('teamSize', -1)"/>
			
			<input type="button" onclick="submit('createEvent')" value=" Create Event "/>
			<input type="button" onclick="cancelEntry('createEvent')" value=" Cancel "/>
		
		</div>
		
		<div id="createLogin" class="addEntryDiv" style="display: none">
		
			User Name: <input type="text" id="userName" value="" onFocus="this.value=''"/>
			Password: <input type="password" id="password" value="" onFocus="this.value=''"/>
			<select id="loginSchool">
				<%if(schools.size() > 0){
					%>
					<option value="-1">Select School</option>
					<optgroup label="-------------------------"></optgroup>
					<%
					for(School school : schools){%>
						<option value="<%=school.getId()%>"><%=school.getName()%></option>
					<%}
				} else {%>
					<option>No Available Schools</option>
				<%}%>
				
			</select>		
			<input type="button" onclick="submit('createLogin')" value=" Create Login "/>
			<input type="button" onclick="cancelEntry('createLogin')" value=" Cancel "/>
		
		</div>
		
		<div id="deleteLogin" class="addEntryDiv" style="display: none">
	
			<select id="deleteUserLogin" style="width: 300px;">
				<option value="-1">Select User Login</option>
				<optgroup label="-------------------------"></optgroup>
				<%
				for(int loginId = 0; loginId < teacherLogins.size(); loginId++){%>
					<option value="<%=teacherLogins.get(1).getId()%>">User Login Name - School <%=loginId%></option>
				<%} %>
			</select>
		
			<input type="button" onclick="submit('deleteLogin')" value=" Delete Login "/>
			<input type="button" onclick="cancelEntry('deleteLogin')" value=" Cancel "/>
	
		</div>	

		<div id="modifyEvent" class="addEntryDiv" style="display: none">
		
			<input type="text" id="modifyName" value="Enter New Name" onFocus="this.value=''" onblur="checkEntry('modifyName', -1)"/>
			
			<select id="loginSchool">
					<option value="-1">Select Event</option>
					<optgroup label="-------------------------"></optgroup>
					<%for(Event event : events){%>
						<option value="<%=event.getId()%>"><%=event.getName()%></option>
					<%}%>
			</select>				
			
			<input type="text" id="modifyDescription" style="width: 598px; height: 130px;" value="Enter New Description" onFocus="this.value=''" onblur="checkEntry('modifyDescription', -1)"/>
			
			<input type="button" onclick="modifyEvent()" value=" Modify Event "/>
			<input type="button" onclick="cancelEntry('modifyEvent')" value=" Cancel "/>						
		
		</div>			
		
	</div>	
</form>

<%for(int i=0; i < events.size(); i++){ 
	
	Event event = events.get(i);

	if(event.getEventInstances() != null){
		
		String maxTeamsPerSchool = Integer.toString(event.getMaxEntriesPerSchool());
		
		Iterator<EventInstance> itr = (Iterator<EventInstance>)event.getEventInstances().iterator();
	
	    while(itr.hasNext()) {
	    	
	    	EventInstance eventInstance = (EventInstance)itr.next();
	    	// if statement here to determine if instance is for this school.
	    	List<Team> studentInstanceTeams = new ArrayList<Team>();
	    	Set<Team> teams = (Set<Team>)eventInstance.getTeams();
			
			if(studentInstanceTeams.size() < event.getMaxEntriesPerSchool()){ 
				maxTeamsPerSchool = "href";
			}
			%>
			<form name="eventRegistration<%=eventInstance.getId()%>" action="EventRegistration" method="post">
			
				<div id="availableEvents" class="pageContainer event">
				
					<div id="header" style="border-bottom: 2px solid;">
						<div id="title">

							<%=event.getName() %> | <%=event.getEventType().getTypeName() %> | <a href="exportEvent?eventId=" style="font-weight: normal;"><img src="<%=pdf%>"/> Export Event</a>
							
						</div>
						
						<div id="link">
						
							<a href="javascript:void(0)" onclick="showDiv('modifyEvent('<%=eventInstance.getId()%>');">Modify Event</a>
							
						</div>				
					</div>
					<%// WE NEED to handle null her as they won't have a description sometimes. 
					// we need to 
					
					%>
					<div id="description" style="width: 780px; margin-bottom: 20px; margin-left: 9px;">

						<%=event.getDetails() != null ? event.getDetails() : "No Description"%>
						
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
