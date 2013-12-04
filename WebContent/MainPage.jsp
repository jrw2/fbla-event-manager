<%@ include file="/includes/Shell/shell_top.jsp"%>
<% %>
<%-- <%@ page import = "edu.weber.ntm.fblaem.beans.beanNameHere"%> --%>
<%-- <jsp:useBean id="" class="edu.weber.ntm.fblaem.beans.beanNameHere" scope="session"/> --%>
<%-- <jsp:setProperty name="beanNameHere" property="*"/> --%>

<%@ include file="/includes/Shell/shell_header.jsp"%>

<script>
</script>

<style>
div.center
{
text-align:center;

}
div.left
{
text-align:left; 
}

</style>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ include file="/includes/Shell/shell_body.jsp"%>
<!-- Begin Page Content -->
<body>
<form>
<div>

            <div class ="left">Events </div>
            <div class = "center"> Students </div>   <br /> 
             <%

                 List<String> EventsList = new ArrayList<String>(); 
                 EventsList.add("Test Taking 101"); 
                 EventsList.add("Literature");
                 for(Iterator<String> i = EventsList.iterator(); i.hasNext(); ) {
                	
                	 String item = i.next();
                	 System.out.println(item);%>
                	     <div class = "left">
                	     -----
                 	   <%=item%> 
                 	     -----
                 	    </div>
                 	    
                	    <%}%>
                	    
               <%
            
                 List<String>Students = new ArrayList<String>(); 
                 Students.add("Little Joey"); 
                 Students.add("Little Megan");
                 for(Iterator<String> i = Students.iterator(); i.hasNext(); ) {
                	
                	 String item = i.next();
                	 System.out.println(item);%>
                	     <div class= "center">
                	     -----
                 	   <%=item%> 
                 	     -----
                 	    </div>
                 	    
                	    <%}%>
                	    
                	
                  
                	
                	
               
                
                
                
              
                     
                
            
      
	</div>
	
</form>
</body>
<!-- End Page Content -->
<%@ include file="/includes/Shell/shell_footer.jsp"%>
