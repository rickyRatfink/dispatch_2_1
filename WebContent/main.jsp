<jsp:include page="header.jsp" flush="true"/>
 
<tr>
 			<td bgcolor="#ffffff" align="center"><br /><br />
            <table width="800" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td align="center" class="barChartHeader">Donation Dispatch Statistics For The Next 7 Days</td>
                    </tr>
                    <tr> 
                        <td align="center">
                        	<% 
							 String sDate1=(String)request.getAttribute("DATE1");
							 String sLimit1=(String)request.getAttribute("LIMIT1");
							 String sCount1=(String)request.getAttribute("COUNT1");                            
							
							 String sDate2=(String)request.getAttribute("DATE2");
							 String sLimit2=(String)request.getAttribute("LIMIT2");
							 String sCount2=(String)request.getAttribute("COUNT2");   
							 
							  String sDate3=(String)request.getAttribute("DATE3");
							 String sLimit3=(String)request.getAttribute("LIMIT3");
							 String sCount3=(String)request.getAttribute("COUNT3");   
							 
							  String sDate4=(String)request.getAttribute("DATE4");
							 String sLimit4=(String)request.getAttribute("LIMIT4");
							 String sCount4=(String)request.getAttribute("COUNT4");   
							 
							  String sDate5=(String)request.getAttribute("DATE5");
							 String sLimit5=(String)request.getAttribute("LIMIT5");
							 String sCount5=(String)request.getAttribute("COUNT5");   
							 
							  String sDate6=(String)request.getAttribute("DATE6");
							 String sLimit6=(String)request.getAttribute("LIMIT6");
							 String sCount6=(String)request.getAttribute("COUNT6");   
							 
							 
							  String sDate7=(String)request.getAttribute("DATE7");
							 String sLimit7=(String)request.getAttribute("LIMIT7");
							 String sCount7=(String)request.getAttribute("COUNT7");                            
							%>
                            
                           <canvas id="canvas" height="450" width="700">&nbsp;</canvas>


							<script>                        
							
                                var barChartData = {
                                    labels : ["<%=sDate1%>","<%=sDate2%>","<%=sDate3%>","<%=sDate4%>","<%=sDate5%>","<%=sDate6%>","<%=sDate7%>"],
                                    datasets : [
                                        {
                                            fillColor : "rgba(220,220,220,.5)",
                                            strokeColor : "rgba(220,220,220,1)",
                                            data : [<%=sLimit1%>,<%=sLimit2%>,<%=sLimit3%>,<%=sLimit4%>,<%=sLimit5%>,<%=sLimit6%>,<%=sLimit7%>]
                                        },
                                        {
                                            fillColor : "rgba(151,187,205,0.5)",
                                            strokeColor : "rgba(151,187,205,1)",
                                            data : [<%=sCount1%>,<%=sCount2%>,<%=sCount3%>,<%=sCount4%>,<%=sCount5%>,<%=sCount6%>,<%=sCount7%>]
                                        }
                                    ]
                        
                                }
                        
                            var myLine = new Chart(document.getElementById("canvas").getContext("2d")).Bar(barChartData);
                        
                            </script>
                            							
                        </td>
                    </tr>
                
             </table>
             </td>
</tr>


<jsp:include page="footer.jsp" flush="true"/>

