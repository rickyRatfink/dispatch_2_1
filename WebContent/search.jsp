<%@ page import="org.faithfarm.dispatch.DispatchServlet" %>
<%@ page import="org.faithfarm.domain.Donation" %>
<%@ page import="org.faithfarm.domain.Donor" %>
<%@ page import="org.faithfarm.domain.Address" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.faithfarm.domain.SystemUser" %>

<%

	String required = "<img src='images/required.png'/>"; 
    String f1Err = (String)request.getAttribute("field1Err");
	String f2Err = (String)request.getAttribute("field2Err");
	if (f1Err==null) f1Err="";
	if (f2Err==null) f2Err=""; 
	
	ArrayList results = (ArrayList)session.getAttribute("RESULTS_"+session.getId());
	if (results==null) results=new ArrayList();
	
 
	  SystemUser user = (SystemUser)session.getAttribute("USER_"+session.getId()); 
	  if (user==null) user=new SystemUser();

%>
<jsp:include page="header.jsp" flush="true"/>

<script language="javascript" type="text/javascript">
function ucase(obj) {
  obj.value=obj.value.toUpperCase();
}
</script>

<style type="text/css">

.tcalInput {
  background: #c3ccd0 url('images/cal.gif') 100% 50% no-repeat;
  border:1px solid #7e93b0;
	border-radius:2px;
	height: 10px;
	background-color:#e0e9f6;
	font: italic 10px Arial;
	color: #7e93b0;
}

</style>

<form method="POST" action="<%=request.getContextPath()%>/ticket"> 
<tr>
 			<td bgcolor="#ffffff" align="center"><br /><br />
            <table width="800" cellpadding="0" cellspacing="0" border="0">
			 <tr>
            	<td align="center" bgcolor="#FFFFFF" >
                       <table width="800" cellpadding="0" cellspacing="0" border="0" class="grid">
                        	 <tr>
                            	<td colspan="7" height="25" bgcolor="#3b3f41">&nbsp;&nbsp;Donations</td>
                            	<td bgcolor="#3b3f41" alight="right">
                            		<% if (results.size()>0) { %>
                            		<a href="<%=request.getContextPath()%>/ticket?action=Print"><img src="images/printer.png" height="20" width="20" title="Print Dispatches" alt="Print Dispatches"/></a>
                            		<% } %>
                            	</td>
                            </tr>   
                            <tr>
                            	<td height="23" valign="center" background="images/searchGroupBk.png" class="searchMenuHeader">
                                		<input type="text" name="lastname" size="25" maxlength="20" value="<%=session.getAttribute("lastname") %>" class="textboxSearch" onfocus="javascript:this.value='';" onkeyup="ucase(this)"/>
                                        <input type="text" name="firstname" size="20" maxlength="20" value="<%=session.getAttribute("firstname") %>" class="textboxSearch" onfocus="javascript:this.value='';" onkeyup="ucase(this)"/>
                                        <input type="text" name="zipcode" size="10" maxlength="10" value="<%=session.getAttribute("zipcode") %>" class="textboxSearch" onfocus="javascript:this.value='';" onkeyup="ucase(this)"/>
                                        <input type="text" name="confirmation" size="15" maxlength="15" value="<%=session.getAttribute("confirmation") %>" class="textboxSearch" onfocus="javascript:this.value='';" onkeyup="ucase(this)"/> 
                                        <input type="text" name="dispatchDate" size="30" maxlength="10" value="<%=session.getAttribute("dispatchDate") %>" class="tcal" onfocus="javascript:this.value='';" onkeyup="ucase(this)"/>
                                    
                                    <select name="status"  class="ddlSearch">
                                    <option value="">status</option>
                                    <%
                                    ArrayList ddl = (ArrayList)session.getAttribute("dllStatus");
                                    if (ddl!=null) {
                                      for (int j=0;j<ddl.size();j++) {
                                        %>
                                        <option 
                                            value="<%=ddl.get(j)%>"
                                            <%
                                            if
                                            (ddl.get(j).equals((String)session.getAttribute("status")))
                                            {%>selected<%}%>>
                                          <%=ddl.get(j)%>
                                        </option>
                                        <%
                                      }
                                      %>
                                      <%
                                    }
                                    %>
                                  </select>
                                  
                                  <select name="special"  class="ddlSearch">
                                 	 <option value="">special</option>
                                 	 <option value="YES" <% if ("YES".equals((String)session.getAttribute("special"))) {%>selected<%} %>>Yes</option>
                                 	 <option value="NO" <% if ("NO".equals((String)session.getAttribute("special"))) {%>selected<%} %>>No</option>
                                    
                                  </select>
                                         
                                         
                                         </td>
                                <td colspan="7" align="left" valign="center" background="images/searchGroupBk.png" class="searchMenuHeader">
                                	<input type="submit" name="action" value="SearchTickets" class="imageButtonSearch" title="Search Tickets" />
                                </td>
                            </tr>
                  			<tr>
                            	<td colspan="9">
                                    <table width="800" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td width="90" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">CONFIRMATION</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="150" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">DONOR</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="120" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">PICKUP DATE</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="100" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">ZIPCODE</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="70" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">SPECIAL</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="70" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">STATUS</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="70" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">FARM</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                           	<td height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">AGENT</td>
                                        </tr>
                                        <% 
										for (int i=0;i<results.size();i++) {
											Donation d = (Donation)results.get(i);
											Donor donor = d.getDonor();
											Address addy = d.getAddress();
										%>
                                         <tr>
                                            <td colspan="2" class="searchFieldResult">
                                            	<a href="<%=request.getContextPath()%>/ticket?action=Update&id1=<%=d.getDonor().getDonorId()%>&id2=<%=d.getAddress().getAddressId()%>&id3=<%=d.getDonationId()%>"><%=d.getDonationId() %></a>
                                            	<%
												 if (user.getUserRole().equals("Administrator")) {
												 %>&nbsp;&nbsp;
                                            	<a href="<%=request.getContextPath()%>/ticket?action=Delete&id=<%=d.getDonationId()%>"><img src="<%=request.getContextPath() %>/images/DeleteRed.png"/></a>
                                            	<% } %>
                                            </td>
                                            <td colspan="2" class="searchFieldResult"><%=donor.getLastname() %>,&nbsp;<%=donor.getFirstname() %></td>
                                            <td colspan="2" class="searchFieldResult"><%=d.getDispatchDate() %></td>
                                            <td colspan="2" class="searchFieldResult"><%=addy.getZipcode() %></td>
                                            <td colspan="2" class="searchFieldResult"><%=d.getSpecialFlag() %></td>
                                            <td colspan="2" class="searchFieldResult"><%=d.getStatus() %></td>
                                            <td colspan="2" class="searchFieldResult">
                                            <% if ("BOYNTON BEACH".equals(d.getFarmBase())) { %>
                                            	 BYN
                                            <% } else if ("FORT LAUDERDALE".equals(d.getFarmBase())) { %>
                                             	FTL
                                            <% } else { %>
                                            	OKE <% } %>
                                            </td>
                                            <td class="searchFieldResult"><%=d.getCreatedBy() %></td>
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
