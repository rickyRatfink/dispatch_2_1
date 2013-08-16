<%@ page import="org.faithfarm.dispatch.DispatchServlet" %>
<%@ page import="org.faithfarm.domain.Donation" %>
<%@ page import="org.faithfarm.domain.Donor" %>
<%@ page import="org.faithfarm.domain.Address" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
 
<%
 
	String required = "<img src='images/required.png'/>"; 
    String f1Err = (String)request.getAttribute("field1Err");
	String f2Err = (String)request.getAttribute("field2Err");
	if (f1Err==null) f1Err="";
	if (f2Err==null) f2Err="";
	
	ArrayList results1 = (ArrayList)session.getAttribute("RESULTS1_"+session.getId());
	if (results1==null) results1=new ArrayList();
	ArrayList results2 = (ArrayList)session.getAttribute("RESULTS2_"+session.getId());
	if (results2==null) results2=new ArrayList();
	
%>
<jsp:include page="header.jsp" flush="true"/>

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
                            	<td colspan="9" height="25" bgcolor="#3b3f41">&nbsp;&nbsp;Donors</td>
                            </tr>   
                           
                  			<tr>
                            	<td colspan="9">
                                    <table width="800" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td width="90" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">ID</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="180" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">NAME</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="100" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">CITY</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="70" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">ZIPCODE</td>
                                            <td width="3" height="18" background="images/searchHeaderSpacer.png"></td>
                                            <td width="70" height="18" background="images/searchHeaderBk.png" class="searchFieldHeader">CREATE DATE</td>
                                            <td width="70" height="18" background="images/searchHeaderBk.png"></td>
                                         </tr>
                                        <%
										SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
										for (int i=0;i<results1.size();i++) {
											Donor donor = (Donor)results1.get(i);
											Address addy = (Address)results2.get(i);
										%>
                                         <tr>
                                            <td colspan="2" class="searchFieldResult"><a href="<%=request.getContextPath()%>/ticket?action=SelectDonor&id1=<%=donor.getDonorId()%>&id2=<%=addy.getAddressId()%>"><%=donor.getDonorId() %></a></td>
                                            <td colspan="2" class="searchFieldResult"><%=donor.getLastname() %>,&nbsp;<%=donor.getFirstname() %></td>
                                           <td colspan="2" class="searchFieldResult"><%=addy.getCity()%></td>
                                            <td colspan="2" class="searchFieldResult"><%=addy.getState()%></td>
                                            	<% String sDate="";
												   try {
													 sDate=sdf.format(new Date(new Long(donor.getCreationDate())));
                                                   } catch (NumberFormatException e) { 
                                                 	 sDate=donor.getCreationDate();
                                                 } %>
                                            <td colspan="2" class="searchFieldResult"><%=sDate%></td>
                                        </tr>
                                        <% } 
										if (results1.size()==0) {
										%>
                                        <tr>
                                            <td colspan="11" class="searchFieldResult">No Results</td>
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
