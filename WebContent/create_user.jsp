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

<script language="javascript" type="text/javascript">
function ucase(obj) {
  obj.value=obj.value.toUpperCase();
}
</script>

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
.dll1 {
	border:1px solid #456879;
	border-radius:3px;
	height: 15px;
	background-color:#e4e4e4;
	font: 10px Arial;
	color: #444446;
}
</style>

<form method="POST" action="<%=request.getContextPath()%>/ticket"> 
			<tr>
            	<td align="left" valign="center" border="0" bgcolor="#FFFFFF">
                        <h1>Create New User</h1>
                		<table width="650" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td width="25"></td>
                                <td class="instructions">
                                   Enter a username for the new system account.  The username can be a maximum of 10 alphanumeric characters.  The password will automatically
                                   be their username.  The new user will be prompted to change their password on their first login.</br>
                                </td>
                             </tr>
                             <tr>
                            	<td width="25"></td>
                            	<td class="fieldHeading" >Username<%=required%></td> 
                              </tr>
                              <tr>
                            	<td width="25"></td>
                                <td><input 
                                      name="username"
                                      size="15" maxlength="15" class="textbox1"
                                      value="<%=DispatchServlet.getSystemUser().getUsername()%>" 
                                  />
                                </td>
                             </tr>
                             <tr>
                            	<td></td>
                            	<td class="fieldError"><%=f1Err%></td>
                             </tr>
                              <tr>
                              	<td width="25"></td>
                                <td class="fieldHeading" >Role<%=required%></td>
                                <td></td>
                              </tr>
                              <tr>
                              	<td width="25"></td>
                             	<td >
                                	<select name="role" class="dll1">
                                    	<option value+""></option>
                                    	<option value="ADMIN">ADMIN</option>
                                        <option value="AGENT">AGENT</option>
                                     </select>
                                </td> 
                            </tr>
                            <tr>
                            	<td></td>
                            	 <td class="fieldError"><%=f2Err%></td>
                            </tr>
                            <tr>
                             <td></td>
                             <td colspan="3" align="left"></br><input type="submit" name="action" value="SaveUser" class="imageButtonSaveOnly" title="Save" /></td>
                            </tr>
                  		</table>
                  </td>
            </tr>
           </form>      
<jsp:include page="footer.jsp" flush="true"/>
