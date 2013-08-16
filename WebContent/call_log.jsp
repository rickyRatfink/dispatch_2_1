<%@ page import="org.faithfarm.dispatch.DispatchServlet" %>
<%@ page import="java.util.ArrayList" %>

<%

	String required = "<img src='images/required.png'/>"; 
    String f1Err = (String)request.getAttribute("field1Err");
	String f2Err = (String)request.getAttribute("field2Err");
	if (f1Err==null) f1Err="";
	if (f2Err==null) f2Err="";
	
		String message=(String)request.getAttribute("MESSAGE");	
	if (message==null) message="";
%>
<jsp:include page="header.jsp" flush="true"/>


<form method="POST" action="<%=request.getContextPath()%>/ticket?action=CallLog"> 
			<tr>
            	<td align="left" valign="center" border="0" bgcolor="#FFFFFF">
                        <h1>Call Information</h1>
                        <% if (message.length()>0) { %>
                        <h5><img src="images/success.png"/><%=message %></h5>
                        <% } %>
                		<table width="400" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td width="25"></td>
                            	<td class="fieldHeading" >Type Of Call<%=required%></td> 
                                <td class="fieldHeading" >Source<%=required%></td>
                                <td></td>
                            </tr>
                            <tr>
                            	<td width="25"></td>
                            	<td >
                                
                                	<select name="type"  <% if (f1Err.length()>0){%> class="ddlErr"<% } else { %> class="ddl"<%}%>>
                                    <option value="New Ticket">New Ticket</option>
                                     <option value="Cancel Ticket">Cancel Ticket</option>
                                      <option value="Donation Ticket">Donation Ticket</option>
                                       <option value="Other Ticket">Other Inquiry</option>
                                        <option value="Phone Reject">Phone Reject</option>
                                         <option value="Previously Written Ticket Inquiry">Previous Written Ticket Inquiry</option>
                                          <option value="Reschedule">Reschedule</option>                                   
                                  </select>
                                </td> 
                                <td>
                                	<%
                                     ArrayList ddl = (ArrayList)session.getAttribute("dllSource");
                                    %>
                                	<select name="source"  <% if (f2Err.length()>0){%> class="ddlErr"<% } else { %> class="ddl"<%}%>>
                                    <option value="">
                                    <%
                                    if (ddl!=null) {
                                      for (int j=0;j<ddl.size();j++) {
                                        %>
                                        <option 
                                            value="<%=ddl.get(j)%>">
                                          <%=ddl.get(j)%>
                                        </option>
                                        <%
                                      }
                                      %>
                                      <%
                                    }
                                    %>
                                  </select>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                            	<td></td>
                            	<td class="fieldError"><%=f1Err%></td>
                                <td class="fieldError"><%=f2Err%></td>
                                <td></td>
                                
                            </tr>
                            <tr>
                             <td colspan="3" align="right"></br><input type="submit" name="action" value="Save" class="imageButtonSaveOnly" title="Save" /></td>
                             <td></td>
                            </tr>
                  		</table>
                  </td>
            </tr>
           </form>      
<jsp:include page="footer.jsp" flush="true"/>
