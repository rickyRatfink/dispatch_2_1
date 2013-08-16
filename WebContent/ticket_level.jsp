<%@ page import="org.faithfarm.dispatch.DispatchServlet" %>
<%@ page import="java.util.ArrayList" %>

<%

	String required = "<img src='images/required.png'/>"; 
    String f1Err = (String)request.getAttribute("field1Err");
	String f2Err = (String)request.getAttribute("field2Err");
	if (f1Err==null) f1Err="";
	if (f2Err==null) f2Err="";
%>
<jsp:include page="header.jsp" flush="true"/>

<style type="text/css">
#input {}
.textbox1 {
	border:1px solid #456879;
	border-radius:3px;
	height: 13px;
	background-color:#e4e4e4;
	font: 10px Arial;
	color: #444446;
}
.tcalActive {
	border:1px solid #456879;
	border-radius:3px;
	height: 13px;
	background-color:#e4e4e4;
	font: 10px Arial;
	color: #444446;
}
.tcal {
	border:1px solid #456879;
	border-radius:3px;
	height: 13px;
	background-color:#e4e4e4;
	font: 10px Arial;
	color: #444446;
}
</style>

<form method="POST" action="<%=request.getContextPath()%>/ticket"> 
			<tr>
            	<td align="left" valign="center" border="0" bgcolor="#FFFFFF">
                        <h1>Adjust Daily Ticket Limit</h1>
                		<table width="300" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td width="25"></td>
                            	<td class="fieldHeading" >Pickup Date<%=required%></td> 
                                <td class="fieldHeading" >Limit<%=required%></td>
                                <td></td>
                            </tr>
                            <tr>
                            	<td width="25"></td>
                                <td><input 
                                      name="dispatchDate"
                                      size="12" class="tcal"
                                      value="<%=DispatchServlet.getDispatchDate()%>"
                                  />
                                </td>
                            	<td >
                                	<input 
                                      name="level"
                                      size="3" maxlength="3" <% if (f2Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox1"<%}%>
                                      value="<%=DispatchServlet.getLimit()%>"
                                  />
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
                             <td></td>
                             <td colspan="3" align="left"></br><input type="submit" name="action" value="SaveLevel" class="imageButtonSaveOnly" title="Save" /></td>
                             <td></td>
                            </tr>
                  		</table>
                  </td>
            </tr>
           </form>      
<jsp:include page="footer.jsp" flush="true"/>
