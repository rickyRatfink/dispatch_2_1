<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.faithfarm.domain.SystemUser" %>
   
<%   
	  SystemUser user = (SystemUser)session.getAttribute("USER_"+session.getId()); 
	  if (user==null) user=new SystemUser();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="styles/style.css" rel="stylesheet" type="text/css" />
<link href="styles/tcal.css" rel="stylesheet" type="text/css" />
<style>
    .dotted {border: 1px dotted #456879; border-style: none none dotted; color: #fff; background-color: #fff; }
</style>
<meta name = "viewport" content = "initial-scale = 1, user-scalable = no">
		<style>
			canvas{
			}
		</style>
<script src="https://maps.googleapis.com/maps/api/js?sensor=false&libraries=places"></script>
<script type="text/javascript" src="scripts/tcal.js"></script>
<script type="text/javascript" src="scripts/chart.js"></script>

</head>
  
<body topmargin="0" leftmargin="0" bgcolor="#383838">
 
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td width="25%"></td>
<td width="950" align="center">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td align="center" border="0" bgcolor="#FFFFFF">
					<div id="navigation"><div align="right">
                    <font style="font: italic 11px Arial;color: #e4e4e4;text-align:right;">(<%=user.getUsername()%>&nbsp;&nbsp;/&nbsp;&nbsp;<%=new java.util.Date()%>)&nbsp;&nbsp;</font></div>&nbsp;&nbsp;
                    </br><div align="left">&nbsp;&nbsp;<img src="images/new_logo.png" /></div>
                    <%
					if (user.getUserRole().equals("ADMIN")) {
					%>
                    <ul>
		            	<li><a href="<%=request.getContextPath()%>/ticket?action=Home">Home</a></li>
		                <li><a href="<%=request.getContextPath()%>/ticket?action=New">New Ticket</a></li>
						<li><a href="<%=request.getContextPath()%>/ticket?action=Search">View/Edit Ticket</a></li>
		                <li><a href="<%=request.getContextPath()%>/ticket?action=Level">Ticket Levels</a></li>
                        <li><a href="<%=request.getContextPath()%>/ticket?action=Users">User Accounts</a></li>
						<li><a href="<%=request.getContextPath()%>/login?action=logout">Logout</a></li>
					</ul> 
                    <% 
					}
					if (user.getUserRole().equals("AGENT")) {
					%>
                    <ul>
		            	<li><a href="<%=request.getContextPath()%>/ticket?action=Home">Home</a></li>
		                <li><a href="<%=request.getContextPath()%>/ticket?action=New">New Ticket</a></li>
						<li><a href="<%=request.getContextPath()%>/ticket?action=Search">View/Edit Ticket</a></li>
		                <li><a href="call_log.jsp">Call Log</a></li>
						<li><a href="<%=request.getContextPath()%>/login?action=logout">Logout</a></li>
					</ul>
                <% } %>
				</div>
				</td>
			</tr>
           <!-- <tr>
            <td bgcolor="#ffffff">
            
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
            	<td height="20" bgcolor="#FFFFFF" class="userDisplay">(<%=user.getUsername()%>&nbsp;&nbsp;/&nbsp;&nbsp;<%=new java.util.Date()%>)&nbsp;&nbsp;</td>
            </tr>           
            </table>
            </td>
            </tr>-->