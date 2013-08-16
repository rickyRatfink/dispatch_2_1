<%@ page import="org.faithfarm.dispatch.DispatchServlet" %>
<%@ page import="java.util.ArrayList" %>

<%

	String required = "<img src='images/required.png'/>"; 
    String f1Err = (String)request.getAttribute("field1Err");
	String f2Err = (String)request.getAttribute("field2Err");
	String f3Err = (String)request.getAttribute("field3Err");
	String f4Err = (String)request.getAttribute("field4Err");
	if (f1Err==null) f1Err="";
	if (f2Err==null) f2Err="";
	if (f3Err==null) f3Err="";
	if (f4Err==null) f4Err="";
	
%>
<jsp:include page="header.jsp" flush="true"/>


<form method="POST" action="<%=request.getContextPath()%>/ticket?action=ChangePassword"> 
			<tr>
            	<td align="left" valign="center" border="0" bgcolor="#FFFFFF">
                        <h1>Set Password</h1>
                		<table width="400" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td width="25"></td>
                                <td class="instructions">Enter your new password.  The password must be between 4 and 10 characters. Numbers, letters, and special characters are allowed.</td>
                                <td></td>
                            </tr>
                            <tr>
                            	<td width="25"></td>
                            	 <td><input type="text" size="20" maxlength="15" name="password1" value="new password" class="login" onfocus="javascript:this.value='';this.type='password'"/>
                           </td> 
                                <td></td>
                            </tr>
                            <tr>
                            	<td></td>
                            	<td class="fieldError"><%=f1Err%></td>
                                <td></td>
                                
                            </tr>
                            <tr>
                            	<td width="25"></td>
                            	<td>    <input type="text" size="20" maxlength="15" name="password2" value="re-enter password" class="login" onfocus="javascript:this.value='';this.type='password'"/>
                           </td> 
                                <td></td>
                            </tr>
                            <tr>
                            	<td></td>
                            	<td class="fieldError"><%=f2Err%></td>
                                <td></td>
                                
                            </tr>
                            <tr><td colspan="3" height="25"></td></tr>
                            <tr>
                            	<td></td>
                                <td class="fieldHeading">Please select a security question</td>
                                <td></td>
                             </tr>
                            <tr>
                            	<td width="25"></td>
                            	 <td>
                                	<%
                                     ArrayList ddl = (ArrayList)session.getAttribute("dllSecurityQuestion");
                                    %>
                                	<select name="question"  <% if (f2Err.length()>0){%> class="ddlErr"<% } else { %> class="ddl"<%}%>>
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
                            	<td class="fieldError"><%=f3Err%></td>
                                <td></td>
                                
                            </tr>
                            <tr>
                            	<td></td>
                                <td class="fieldHeading">Security answer</td>
                                <td></td>
                             </tr>
                             <tr>
                             	<td></td>
                             	<td><input type="text" size="40" name="answer" maxlength="40" value="" <% if (f4Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%> /></td>
                                <td></td>
                             </tr>
                            <tr>
                            	<td></td>
                            	<td class="fieldError"><%=f4Err%></td>
                                <td></td>
                                
                            </tr>
                            <tr>
                             <td></td>
                             <td colspan="2" align="left"></br><input type="submit" name="action" value="Save" class="imageButtonSaveOnly" title="Save" /></td>
                            </tr>
                  		</table>
                  </td>
            </tr>
           </form>      
<jsp:include page="footer.jsp" flush="true"/>
