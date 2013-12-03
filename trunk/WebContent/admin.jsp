<%@ include file="/includes/Shell/shell_top.jsp"%>

<!-- IMPORTS ------------------------------------------------- -->
<%@page import="edu.weber.ntm.fblaem.databaseio.Teacher"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.School"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Event"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.StudentTeam"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Team"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Student"%>
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
		margin-bottom: 4px;
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
		margin-top: 5px;
		margin-left: 9px;
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

	function submitData(type){

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
			$("#schoolName").val("School Name");
			$("#schoolAddress").val("Street Address");
			$("#schoolCity").val("City");
			$("#schoolState").val("State");
			$("#schoolZip").val("Zip");
			$("#schoolPhone").val("Phone");	
		} else if(type == "createEvent") {
			$("#eventDescription").val("Event Description");
			$("#eventName").val("Event Name");
			$("#numTeams").val("Number of Teams");
			$("#minTeamSize").val("Min Team Size");
			$("#maxTeamSize").val("Max Team Sizee");
			$("#maxEntries").val("Max School Entries");
		} else if(type == "createLogin") {
			$("#userName").val("");
			$("#password").val("");
			$("#email").val("Eamil");
			$("#firstName").val("First Name");
			$("#lastName").val("Last Name");
			$("#phone").val("Phone");
			$("#altPhone").val("Alt Phone");
		} else if(type == "deleteLogin") {
			$("#schoolBilling").val("-1");
		} else if(type == "modifyEvent") {
			$("#modifyDescription").val("Enter New Description");
			$("#modifyName").val("Enter New Name");
		}
			
		showDiv(type);
		
	}
	
	function checkEntry(id){

		var replacement = "";
			
			if(id == "email"){
				replacement = "Email";
			}
			
			if(id == "firstName"){
				replacement = "First Name";
			}
			
			if(id == "lastName"){
				replacement = "Last Name";
			}
			
			if(id == "phone"){
				replacement = "Phone";
			}
			
			if(id == "altPhone"){
				replacement = "Alt Phone";
			}
			
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
				replacement = "State";
			}
			
			if(id == "schoolZip"){
				replacement = "Zip";
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
			
			if(id == "minTeamSize"){
				replacement = "Min Team Size";
			}
			
			if(id == "maxTeamSize"){
				replacement = "Max Team Size";
			}
	
			if(id == "maxEntries"){
				replacement = "Max School Entries";
			}
			
			if(id == "modifyDescription"){
				replacement = "Enter New Description";
			}
			
			if(id == "modifyName"){
				replacement = "Enter New Name";
			}	

			if(replacement != "" && $("#" + id).val() == ""){
				
				$("#" + id).val(replacement);
				
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
<div id="pageHeader" class="titleDiv" style="display: block; border-bottom: 3px solid; margin-bottom: 4px; width: 930px;">
	<div>
		Administration
	</div>
	<div id="navigation" style="font-size: 13px; margin-bottom: 10px;">
		<a href="javascript:void(0)" onclick="showDiv('createEvent');">New Event</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="javascript:void(0)" onclick="showDiv('removeEvent');">Remove Event</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="javascript:void(0)" onclick="showDiv('addSchool');">Add School</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="javascript:void(0)" onclick="showDiv('deleteSchool');">Delete School</a>&nbsp;&nbsp;|&nbsp;&nbsp;			
		<a href="javascript:void(0)" onclick="showDiv('createLogin');">Add Login</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="javascript:void(0)" onclick="showDiv('deleteLogin');">Delete Login</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="javascript:void(0)" onclick="submitData('reset');">Reset System</a>&nbsp;&nbsp;|&nbsp;&nbsp;			
		<a href="exportEvent?eventId=-1">Export All Events&nbsp;<img src="<%=pdf%>"/></a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="Logout">LOGOUT</a>
	</div>
</div>
	
<form name="submissionForm" action="Administration" method="post">	
<!-- Submission Type -->
<input type="hidden" name="pageAction" id="pageAction" value="">
	<div id="availableEvents" class="newData event">

		<div id="createEvent" class="addEntryDiv" style="display: none; width: 54%;">
		
			<input type="text" name="eventDescription"  id="eventDescription" style="width: 598px; height: 130px;" value="Event Description" onFocus="this.value=''" onblur="checkEntry('eventDescription')"/>
			<input type="text" name="eventName" id="eventName" value="Event Name" onFocus="this.value=''" onblur="checkEntry('eventName')"/>
			<input type="text" name="numTeams" id="numTeams" value="Number of Teams" onFocus="this.value=''" onblur="checkEntry('numTeams')" style="width: 120px;"/>
			<input type="text" name="minTeamSize" id="minTeamSize" value="Min Team Size" onFocus="this.value=''" onblur="checkEntry('minTeamSize')" style="width: 120px;"/>
			<input type="text" name="maxTeamSize" id="maxTeamSize" value="Max Team Size" onFocus="this.value=''" onblur="checkEntry('maxTeamSize')" style="width: 100px;"/>
			<input type="text" name="maxEntries"  id="maxEntries" value="Max School Entries" onFocus="this.value=''" onblur="checkEntry('maxEntries')" style="width: 115px;"/>
			
			<input type="button" onclick="submitData('createEvent')" value=" Create Event "/>
			<input type="button" onclick="cancelEntry('createEvent')" value=" Cancel "/>
		
		</div>
	
		<div id="removeEvent" class="addEntryDiv" style="display: none; width: 54%;">
		
			<select id="removeEvent">
					<option value="-1">Select Event</option>
					<optgroup label="-------------------------"></optgroup>
					<%for(Event event : events){%>
						<option value="<%=event.getId()%>"><%=event.getName()%></option>
					<%}%>
			</select>				
			
			<input type="button" onclick="submitData('removeEvent')" value=" Remove Event "/>
			<input type="button" onclick="cancelEntry('removeEvent')" value=" Cancel "/>						
		
		</div>		
		
		<div id="addSchool" class="addEntryDiv" style="display: none">
		
			<input type="text" id="schoolName" value="School Name" onFocus="this.value=''" onblur="checkEntry('schoolName')"/>
			<input type="text" id="schoolAddress" value="Street Address" onFocus="this.value=''" onblur="checkEntry('schoolAddress')" style="width: 200px;"/>
			<input type="text" id="schoolCity" value="City" onFocus="this.value=''" onblur="checkEntry('schoolCity')" style="width: 100px;"/>
			<input type="text" id="schoolState" value="UT" onFocus="this.value='UT'" onblur="checkEntry('schoolState')" style="width: 16px;" disabled/>
			<input type="text" id="schoolZip" value="Zip" onFocus="this.value=''" onblur="checkEntry('schoolZip')" style="width: 50px;"/>
			<input type="text" id="schoolPhone" value="Phone" onFocus="this.value=''" onblur="checkEntry('schoolPhone')" style="width: 80px;"/>
			
			<input type="button" onclick="submitData('addSchool')" value=" Create Event "/>
			<input type="button" onclick="cancelEntry('addSchool')" value=" Cancel "/>
		
		</div>	

		<div id="deleteSchool" class="addEntryDiv" style="display: none">
		
			<select id="delSchool">
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
			<input type="button" onclick="submitData('deleteSchool')" value=" Delete School "/>
			<input type="button" onclick="cancelEntry('deleteSchool')" value=" Cancel "/>
		
		</div>		
		
		<div id="createLogin" class="addEntryDiv" style="display: none">
			<input type="text" id="firstName" value="First Name" onFocus="this.value=''" onblur="checkEntry('firstName')" style="width: 100px;"/>
			<input type="text" id="lastName" value="Last Name" onFocus="this.value=''" onblur="checkEntry('lastName')" style="width: 100px;"/>
			<input type="text" id="email" value="Email" onFocus="this.value=''" onblur="checkEntry('email')" style="width: 110px;"/>
			<input type="text" id="phone" value="Phone" onFocus="this.value=''" onblur="checkEntry('phone')" style="width: 80px;"/>
			<input type="text" id="altPhone" value="Alt Phone" onFocus="this.value=''" onblur="checkEntry('altPhone')" style="width: 80px;"/>
			
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
			</select><br />
			
			User Name: <input type="text" id="userName" value="" onFocus="this.value=''"/>
			Password: <input type="password" id="password" value="" onFocus="this.value=''"/>
			<input type="button" onclick="submitData('createLogin')" value=" Create Login "/>
			<input type="button" onclick="cancelEntry('createLogin')" value=" Cancel "/>
		
		</div>
		
		<div id="deleteLogin" class="addEntryDiv" style="display: none">
	
			<select id="deleteUserLogin" style="width: 300px;">
				<option value="-1">Select User Login</option>
				<optgroup label="-------------------------"></optgroup>
				<%
				for(int loginId = 0; loginId < teacherLogins.size(); loginId++){%>
					<option value="<%=teacherLogins.get(loginId).getId()%>">User Login Name - School <%=loginId%></option>
				<%} %>
			</select>
		
			<input type="button" onclick="submitData('deleteLogin')" value=" Delete Login "/>
			<input type="button" onclick="cancelEntry('deleteLogin')" value=" Cancel "/>
	
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
			<div id="availableEvents" class="pageContainer event">
			
				<div id="header" style="border-bottom: 2px solid;">
					<div id="title">

						<%=event.getName() %> | <%=event.getEventType().getTypeName() %> | <a href="exportEvent?eventId=" style="font-weight: normal;"><img src="<%=pdf%>"/> Export Event</a>
						
					</div>
					
				</div>

				<div id="description" style="width: 780px; margin-bottom: 20px; margin-left: 9px;">

					<%=event.getDetails() != null ? event.getDetails() : "No Description"%>
					
				</div>				
				
				<%if(teams != null && teams.size() > 0) { %>
					<div style="border-top: 2px solid;">
						<%
					   for(Team team : teams){
						
							Set<StudentTeam> studentTeams = (Set<StudentTeam>)team.getstudentTeams();%>
						
							<div style="border-color:#848369">
								<div id="title" style="width: 780px;">
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
									
									<div id="teamMem">
									
										<%=studentTeam.getStudent().getFirstName() + " " + studentTeam.getStudent().getLastName()%>
										
									</div>
					
								</div>
							<%} 
						} %>
					</div>
				<%} %>
			</div>			
		<%}
	}
} %>

<!-- End Page Content -->
<%@ include file="/includes/Shell/shell_footer.jsp"%>
