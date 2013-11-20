<%@page import="org.hibernate.Hibernate"%>
<%@ include file="/includes/Shell/shell_top.jsp"%>

<!-- IMPORTS ------------------------------------------------- -->
<%@page import="edu.weber.ntm.fblaem.databaseio.Teacher"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.School"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Event"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.StudentTeam"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Team"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.Student"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.DatabaseConnection"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.HibernateUtil"%>
<%@page import="edu.weber.ntm.fblaem.databaseio.EventInstance"%>
<%@page import="org.hibernate.Session"%>

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
Session sf = HibernateUtil.getSessionFactory().openSession();

Set<Student> availableStudents = (Set<Student>)school.getStudents();
Transaction  tx = sf.beginTransaction();
Query query = sf.createQuery("from Event e join fetch e.eventInstances ei");
events = query.list();
for(int i=0; i < events.size(); i++){ 
	
	Event event = events.get(i);
	
	if(event.getEventInstances() != null){
		
// 		EventInstance eventInstance = event.getEventInstaces()
		
		String maxTeamsPerSchool = Integer.toString(event.getMaxEntriesPerSchool());
		
		// how to get the number of teams enrolled for this school for an event???
		
// 		Set<StudentTeam> studentEventTeams = (Set<StudentTeam>)event.getStudentTeams();
// 		List<StudentTeam> schoolTeams = new ArrayList<StudentTeam>();
		
		
				
		Iterator<EventInstance> itr = (Iterator<EventInstance>)event.getEventInstances().iterator();
	
		// HAS TO BE A BETTER WAY TO GO ABOUT THIS (TOMORROW UNDERSTAND DB BETTER AND WRITE GETTERS)
	    while(itr.hasNext()) {
	    	
	    	List<Team> studentInstanceTeams = new ArrayList<Team>();
	    	EventInstance eventInstance = (EventInstance)itr.next();
	    	Set<Student> students = (Set<Student>)school.getStudents();
	    	
	    	for(Student student : students){
	    		 
	    		Set<StudentTeam> studentTeams = (Set<StudentTeam>)student.getStudentTeams();
	    		
	    		for(StudentTeam studentTeam : studentTeams){
	    			
	    			if(studentTeam.getTeam().getEventInstance().getId() == eventInstance.getId()){
	    				
	    				studentInstanceTeams.add(studentTeam.getTeam());
	    				
	    			}
	    			
	    		}
	    		
	    	}
			
			
			if(studentInstanceTeams.size() < event.getMaxEntriesPerSchool()){ 
				maxTeamsPerSchool = "href";
			}
			
		%>
			<!-- form to use method post for when it submits, get for when grabbing the page (forward in post to go to get). -->
			<form name="eventRegistration<%=eventInstance.getId()%>" action="EventRegistration" method="post">
			
				<div id="availableEvents" class="pageContainer event">
				
					<div id="header" style="border-bottom: 2px solid;">
						<div id="title">
						
							<%=event.getName() %> | <%=event.getEventType() %> | <a href="exportEvent?eventId=" style="font-weight: normal;"><img src="<%=pdf%>"/> Export Event</a>
							
						</div>
						
						<div id="link" style="width: 90px;">
						
							<a <%=maxTeamsPerSchool%>="javascript:void(0)" onclick="showDiv('addTeam<%=eventInstance.getId()%>');">Add Team</a>
							
						</div>
						
						<div id="link">
						
							<a href="javascript:void(0)" onclick="showDiv('addStudent<%=eventInstance.getId()%>');">Register Student</a>
							
						</div>				
					</div>
					
					<div id="description" style="width: 780px; margin-bottom: 20px; margin-left: 9px;">
					
						<%=event.getDetails()%>
						
					</div>				
					
					<div id="addStudent<%=eventInstance.getId()%>" class="addEntryDiv" style="display: none">
				
						<input type="text" id="firstName<%=eventInstance.getId()%>" value="First Name" onFocus="this.value=''" onblur="checkEntry('firstName', <%=eventInstance.getId()%>)"/>
						<input type="text" id="lastName<%=eventInstance.getId()%>" value="Last Name" onFocus="this.value=''" onblur="checkEntry('lastName, <%=eventInstance.getId()%>)"/>
						
						<select id="team<%=eventInstance.getId()%>">
							<% 
							if(studentInstanceTeams.size() > 0){
								%>
								<option value="-1">Select Team</option>
								<optgroup label="-------------------------"></optgroup>
								<%
								for(int team = 0; team < studentInstanceTeams.size(); team++){
									// need to create a way to insert a single student team
									// Just allow them to create a team that has a single student.
									Boolean maxStudentsForEvent = (studentInstanceTeams.size() < event.getMaxEntriesPerSchool()) ? true : false;
									
									// if max # of teams reached do not allow this to create new team.
									if(!maxStudentsForEvent){%> 
										<option value="<%=i%>"><%=studentInstanceTeams.get(team).getName()%></option>
									<%}
								} 
							} else {%>
								<option>No Teams</option>
							<%}
							
							%>
							
						</select>
											
						<input type="submit" onclick="submitChanges(eventId, type)" value=" Register Student "/>
						<input type="button" onclick="cancelEntry('student', <%=eventInstance.getId()%>)" value=" Cancel "/>
					
					</div>
					
					<div id="addTeam<%=event.getId()%>" class="addEntryDiv" style="display: none">
				
						<input type="text" id="teamName<%=eventInstance.getId()%>" value="Team Name" onFocus="this.value=''" onblur="checkEntry('teamName')"/>
						
						<%					
						String disableSubmit = "disable";
						// move the code from admin here for creating teams.
						if(studentInstanceTeams.size() < event.getMaxEntriesPerSchool()){
							
							disableSubmit = "";
							
						} %>
						
						<input type="submit" onclick="submitChanges(eventId, type)" <%=disableSubmit %>value=" Add Team "/>
						<input type="button" onclick="cancelEntry('team', <%=event.getId()%>)" value=" Cancel "/>
					
					</div>
					
					<div style="border-top: 2px solid;">
						<%
					   for(int iT = 0; iT < studentInstanceTeams.size(); iT++){
						
							Team team = studentInstanceTeams.get(iT);%>
						
							<div style="border-color:#848369">
								<div id="title" style="width: 705px;">
								
									&nbsp;&nbsp;<%=team.getName()%> ( <%="?/" + team.getMaxIndividuals()%> )
									
								</div>
				
								<div id="link">
								
									<a <%=maxTeamsPerSchool%>="javascript:void(0)" onclick="remove(eventId, type, teamId);">Remove Team</a>
									
								</div>
								
							</div>
							
							<%
							// not sure how to get students registered for an event.  Need to not allow removal unless correct school
							for(int teamMembers = 0; teamMembers < 5; teamMembers++){ %>
								<div style="border-color:#848369; margin-left: 200px;">
									
									<div id="link">
									
										<a <%=maxTeamsPerSchool%>="javascript:void(0)" onclick="remove(eventId, type, teamMemberId);" style="font-size: 13px; color: red;">X&nbsp;&nbsp;</a>
										
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
		<%}
	}
} %>

<!-- End Page Content -->
<%@ include file="/includes/Shell/shell_footer.jsp"%>
