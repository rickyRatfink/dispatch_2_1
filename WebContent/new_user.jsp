<%@ page import="org.faithfarm.dispatch.DispatchServlet" %>
<%@ page import="org.faithfarm.domain.SystemUser" %>
<%@ page import="java.util.ArrayList" %>

<%

	String required = "<img src='images/required.png'/>"; 
    String f1Err = (String)request.getAttribute("field1Err");
	String f2Err = (String)request.getAttribute("field2Err");
	if (f1Err==null) f1Err="";
	if (f2Err==null) f2Err="";
	
	ArrayList results = (ArrayList)session.getAttribute("RESULTS_"+session.getId());
	if (results==null) results=new ArrayList();
	
	String message=(String)request.getAttribute("MESSAGE");	
	if (message==null) message="";
%>
<jsp:include page="header.jsp" flush="true"/>


<form method="POST" action="<%=request.getContextPath()%>/ticket"> 
<tr>
 			<td bgcolor="#ffffff" align="center"><br /><br />
            <table width="800" cellpadding="0" cellspacing="0" border="0">
			 <tr>
            	<td align="center" bgcolor="#FFFFFF" >
                		<% if (message.length()>0) { %>
                        <h5><img src="images/success.png"/><%=message %></h5>
                        <% } %>
                       <table width="800" cellpadding="0" cellspacing="0" border="0" class="grid">
                        	 <tr>
                            	<td colspan="9" height="25" bgcolor="#3b3f41">&nbsp;&nbsp;User Accounts<a href="<%=request.getContextPath()%>/ticket?action=NewUser"><img src="images/newuser.png" height="20" width="20" alt="Create New System User" title="Create New System User"/></a></td>
                            </tr>   
                           
                  			<tr>
                            	<td colspan="9">
                                    <table width="800" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td width="90" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">USERNAME</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="150" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">ROLE</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="120" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">FARM</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="100" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">LOGINS</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="100" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">ACTION</td>
                                         </tr>
                                        <%
										for (int i=0;i<results.size();i++) {
											SystemUser d = (SystemUser)results.get(i);
										%>
                                         <tr>
                                            <td colspan="2" class="searchFieldResult"><%=d.getUsername() %></td>
                                            <td colspan="2" class="searchFieldResult"><%=d.getUserRole() %></td>
                                            <td colspan="2" class="searchFieldResult"><%=d.getFarmBase() %></td>
                                            <td colspan="2" class="searchFieldResult"><%=d.getLoginCount() %></td>
                                            <td colspan="2" class="searchFieldResult">
                                            	<a href="<%=request.getContextPath()%>/ticket?action=DeleteUser&userId=<%=d.getUserId() %>"><img src="images/cancelled.png" height="15" width="15" alt="Deactivate User" title="Deactivate User"/></a>
                                                <a href=""><img src="images/forgot-password.png" height="15" width="15" alt="Reset Password" title="Reset Password"/></a>
                                            </td>
                                         </tr>
                                        <% } 
										if (results.size()==0) {
										%>
                                        <tr>
                                            <td colspan="12" class="searchFieldResult">No Results</td>
                                        </tr>
                                        <% } %>
                                       
                                       
                                    </table>
                                 </td>
                             </tr>
                             
                        </table>
                  </td>
            </tr>
            </table>
            </td> 
           </form> 
</tr>     
<jsp:include page="footer.jsp" flush="true"/>
