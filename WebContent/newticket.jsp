<%@ page import="org.faithfarm.dispatch.DispatchServlet" %>
<%@ page import="java.util.ArrayList" %>


<%
String update = request.getParameter("update");
if (update==null) update="";
%>

<script type="text/javascript">

function moveOnMax(field,nextFieldID){
	  if(field.value.length >= field.maxLength){
	    document.getElementById(nextFieldID).focus();
	  }
	}

  function popitup(url) {
	newwindow=window.open(url,'name','resizable=no,scrollbars=0,height=140,width=600');
	if (window.focus) {newwindow.focus()}
	return false;
}


      function isNumberKey(evt)
      {
         var charCode = (evt.which) ? evt.which : event.keyCode
         if (charCode!=46 && (charCode > 31 && (charCode < 48 || charCode > 57)))
            return false;

         return true;
      }

window.gcoder = new google.maps.Geocoder();
$(document).ready(function() {
  

	$(".entry[name=zipcode]").change(function(e) {
	  gcoder.geocode({
	    'address': $(this).val()
	  }, function(res, status) {
	    window.res = res;
	    if(status == google.maps.GeocoderStatus.OK && res.length) {
	      for(var i=0;i<res[0].address_components.length;i++) {
	        var component = res[0].address_components[i];
	        
	        for(var x=0;x<component.types.length;x++) {
	          var tType = component.types[x];
	          if(tType == "administrative_area_level_1") {
	            $(".entry[name=state]").val(component.long_name);
	          }
	          if(tType == "locality") {
	            $(".entry[name=city]").val(component.long_name);
	          }
	        }
	      }
	    }
	  });
	});
	
});

 function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(26.153936, -80.153442),
          zoom: 15,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById('map_canvas'),
          mapOptions);

        var input = document.getElementById('searchTextField');
        var options = {
        		  types: ['(cities)'],
        		  componentRestrictions: {country: 'us'}
        		};
        var autocomplete = new google.maps.places.Autocomplete(input);

        autocomplete.bindTo('bounds', map);

        var infowindow = new google.maps.InfoWindow();
        var marker = new google.maps.Marker({
          map: map
        });

        google.maps.event.addListener(autocomplete, 'place_changed', function() {
          infowindow.close();
          var place = autocomplete.getPlace();
          if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
          } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);  // Why 17? Because it looks good.
          }

          var image = new google.maps.MarkerImage(
              place.icon,
              new google.maps.Size(71, 71),
              new google.maps.Point(0, 0),
              new google.maps.Point(17, 34),
              new google.maps.Size(35, 35));
          marker.setIcon(image);
          marker.setPosition(place.geometry.location);

          var address = '';
          if (place.address_components) {
            address = [
              (place.address_components[0] && place.address_components[0].short_name || ''),
              (place.address_components[1] && place.address_components[1].short_name || ''),
              (place.address_components[2] && place.address_components[2].short_name || '')
            ].join(' ');
          }

          infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
          infowindow.open(map, marker);
        });

        // Sets a listener on a radio button to change the filter type on Places
        // Autocomplete.
        function setupClickListener(id, types) {
          var radioButton = document.getElementById(id);
          google.maps.event.addDomListener(radioButton, 'click', function() {
            autocomplete.setTypes(types);
          });
        }

        setupClickListener('changetype-all', []);
        setupClickListener('changetype-establishment', ['establishment']);
        setupClickListener('changetype-geocode', ['geocode']);
      }
      google.maps.event.addDomListener(window, 'load', initialize);
  
  function printPage()
  {
    window.print();
  }

  function disableFields() {
    var count = document.forms[0].elements.length-1;
    var i;
    for (i=5;i<count;i++) {
      document.forms[0].elements[i].disabled=true;
    }
  }

  function enableFields() {
    var count = document.forms[0].elements.length-2;
    var i;
    for (i=5;i<count;i++) {
      document.forms[0].elements[i].enabled=true;
    }
  }

  
  function disableSave()
  {
  		document.getElementById("button1").disabled=true;
    	document.getElementById("button2").disabled=true;
    	document.getElementById("action").value="Save";
    	document.forms[0].submit();
  }
  function disableSavePrint()
  {
  		document.getElementById("button1").disabled=true;
    	document.getElementById("button2").disabled=true;
    	document.getElementById("action").value="Save & Print";
    	document.forms[0].submit();
  }
  function disableSaveChanges()
  {
  		document.getElementById("button1").disabled=true;
    	document.getElementById("button2").disabled=true;
    	document.getElementById("action").value="Save Changes";
    	document.forms[0].submit();
  }
  function disableSaveChangesPrint()
  {
  		document.getElementById("button1").disabled=true;
    	document.getElementById("button2").disabled=true;
    	document.getElementById("action").value="Save Changes & Print";
    	document.forms[0].submit();
  }
  

</script>

<jsp:include page="header.jsp" flush="true"/>

<% 
	String required = "<img src='images/required.png'/>"; 
    String f1Err = (String)request.getAttribute("field1Err");
	String f2Err = (String)request.getAttribute("field2Err");
	String f3Err = (String)request.getAttribute("field3Err");
	String f4Err = (String)request.getAttribute("field4Err");
	String f5Err = (String)request.getAttribute("field5Err");
	String f6Err = (String)request.getAttribute("field6Err");
	String f7Err = (String)request.getAttribute("field7Err");
	String f8Err = (String)request.getAttribute("field8Err");
	String f9Err = (String)request.getAttribute("field9Err");
	String f10Err = (String)request.getAttribute("field10Err");
	String f11Err = (String)request.getAttribute("field11Err");
	String f12Err = (String)request.getAttribute("field12Err");
	String f13Err = (String)request.getAttribute("field13Err");
	String f14Err = (String)request.getAttribute("field14Err");
	if (f1Err==null) f1Err="";	
	if (f2Err==null) f2Err="";	
	if (f3Err==null) f3Err="";	
	if (f4Err==null) f4Err="";	
	if (f5Err==null) f5Err="";	
	if (f6Err==null) f6Err="";	
	if (f7Err==null) f7Err="";	
	if (f8Err==null) f8Err="";	 
	if (f9Err==null) f9Err="";	
	if (f10Err==null) f10Err="";	
	if (f11Err==null) f11Err="";	
	if (f12Err==null) f12Err="";	
	if (f13Err==null) f13Err="";
	if (f14Err==null) f14Err="";	
	
	String message=(String)request.getAttribute("MESSAGE");	
	if (message==null) message="";
%>

<script language="javascript" type="text/javascript">
function ucase(obj) {
  obj.value=obj.value.toUpperCase();
}
</script>

<form method="POST" action="<%=request.getContextPath()%>/ticket"> 
			<tr>
				<td width="100%" align="center" valign="center" border="0" bgcolor="#FFFFFF">
                        <h1>Donor Information<% if ("Y".equals(update)) { %>&nbsp;<i>(Confirmation #<%=DispatchServlet.getDonation().getDonationId() %>)</i><%}%></h1>
                        <% if (message.length()>0) { %>
                        <h5><img src="images/success.png"/><%=message %></h5>
                        <% } %>
                		<table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td class="fieldHeading" >Last Name<%=required%></td> 
                                <td class="fieldHeading" >First Name<%=required%></td>
                                <td class="fieldHeading">Suffix</td>
                                <td></td>
                            </tr>
                                                       
                            <tr>
                            	<td class="searchField"><input type="text" size="20" maxlength="40" name="firstname"  value="<%=DispatchServlet.getDonor().getFirstname() %>" <% if (f2Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%> onkeyup="ucase(this)" /></td>
                                <td class="searchField"><input type="text" size="30" maxlength="40" name="lastname" value="<%=DispatchServlet.getDonor().getLastname() %>" <% if (f1Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%> onkeyup="ucase(this)" /></td>
                                <td class="searchField">
                                	<%
                                     ArrayList ddl = (ArrayList)session.getAttribute("dllSuffix");
                                    %>
                                	 <select name="suffix"  class="ddl">
                                    <option value="">
                                    <%
                                    if (ddl!=null) {
                                      for (int j=0;j<ddl.size();j++) {
                                        %>
                                        <option 
                                            value="<%=ddl.get(j)%>"
                                            <%
                                            if
                                            (ddl.get(j).equals(DispatchServlet.getDonor().getSuffix()))
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
                                </td>
                                <td class="searchField" width="50%" align="left">
                                	<input type="submit" name="action" value="SearchDonor" class="imageButtonDonorSearch" title="Donor Search" />
                                </td>
                            </tr>    
                            
                             <tr>
                            	<td class="fieldError"><%=f2Err%></td>
                                <td class="fieldError"><%=f1Err%></td>
                                <td></td>
                                <td</td>
                            </tr>
                                                   
                        </table>  
                        
                        <table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td class="fieldHeading" >Address Line 1<%=required%></td>
                            </tr>
                           
                            <tr>
                            	<td><input type="text" size="80" maxlength="100" name="line1" value="<%=DispatchServlet.getAddress().getLine1() %>" <% if (f3Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%> onkeyup="ucase(this)" /></td>
                            </tr>   
                            <tr>
                            	<td class="fieldError"><%=f3Err%></td>
                            </tr>                         
                        </table>
                        
                        <table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td class="fieldHeading" >Address Line 2</td>
                            </tr>
                           
                            <tr>
                            	<td><input type="text" size="80" name="line2" value="<%=DispatchServlet.getAddress().getLine2() %>" maxlength="100" class="textbox" onkeyup="ucase(this)" /></td>
                            </tr>                            
                        </table>
                        
                        <table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td class="fieldHeading" >City<%=required%></td>
                                <td class="fieldHeading" >State<%=required%></td>
                                <td class="fieldHeading">Zipcode<%=required%></td>
                                <td width="50%"></td>
                            </tr>
                           
                            <tr>
                            	<td><input type="text" size="25" maxlength="25" name="city" value="<%=DispatchServlet.getAddress().getCity() %>" <% if (f4Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%> onkeyup="ucase(this)" /></td>
                                <td>
                                	<%
                                     ddl = (ArrayList)session.getAttribute("dllState");
                                  %>
                                  <select name="state" <% if (f5Err.length()>0){%> class="ddlErr"<% } else { %> class="ddl"<%}%>>
                                    <option value="">
                                    </option>
                                    <%
                                    if (ddl!=null) {
                                      for (int j=0;j<ddl.size();j++) {
                                        %>
                                        <option 
                                            value="<%=ddl.get(j)%>"
                                            <%
                                            if
                                            (ddl.get(j).equals(DispatchServlet.getAddress().getState()))
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
                                </td>
                                <td><input type="text" size="10" name="zipcode" maxlength="10" value="<%=DispatchServlet.getAddress().getZipcode() %>" <% if (f6Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%> /></td>
                                <td width="50%"></td>
                            </tr>     
                            
                             <tr>
                             	<td class="fieldError"><%=f4Err%></td>
                            	<td class="fieldError"><%=f5Err%></td>
                                <td class="fieldError"><%=f6Err%></td>
                                <td></td>
                            </tr>                       
                        </table>  
                        
                        <table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td class="fieldHeading" >Contact Phone<%=required%></td>
                                <td class="fieldHeading" >Email Address<%=required%></td>
                                <td width="40%"></td>
                            </tr>
                           
                            <tr>
                            	<td>
                                	(<input id="phone1" type="text" size="3" maxlength="3" name="phone1" onkeyup="moveOnMax(this,'phone2')" onKeyPress="return isNumberKey(event)" <% if (f10Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%> <% if (DispatchServlet.getDonor().getContactPhone().length()==13 ) { %>value="<%=DispatchServlet.getDonor().getContactPhone().substring(1,4)%>"<%}%>/>)
                                	<input id="phone2" type="text" size="3" maxlength="3" name="phone2" onkeyup="moveOnMax(this,'phone3')" onKeyPress="return isNumberKey(event)"  <% if (f10Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%>  <% if (DispatchServlet.getDonor().getContactPhone().length()==13 ) { %>value="<%=DispatchServlet.getDonor().getContactPhone().substring(5,8)%>"<%}%>/>-
                                	<input id="phone3" type="text" size="4" maxlength="4" name="phone3" onKeyPress="return isNumberKey(event)" <% if (f10Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%>  <% if (DispatchServlet.getDonor().getContactPhone().length()==13 ) { %>value="<%=DispatchServlet.getDonor().getContactPhone().substring(9,13)%>"<%}%>/>
                                </td>
                                <td><input type="text" size="50" name="email" maxlength="50" value="<%=DispatchServlet.getDonor().getEmailAddress() %>" <% if (f11Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%> onkeyup="ucase(this)"/></td>
                                <td></td>
                            </tr>
                            
                            <tr>
                            	<td class="fieldError"><%=f10Err%></td>
                                <td class="fieldError"><%=f11Err%></td>
                                <td></td>
                            </tr>                            
                        </table>  
                        
                        <table>
                        <tr>
            				<td height="20" bgcolor="#FFFFFF"></td>
            			</tr>
                        </table>
                        <hr class='dotted' />
                        <h1>Location Information</h1>
                        <table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td class="fieldHeading" >Major Intersection<%=required%></td>
                                <td class="fieldHeading" >Subdivision</td>
                                <td width="55%"></td>
                             </tr>
                             <tr>
                            	<td><input type="text" size="30" maxlength="30" name="majorIntersection" value="<%=DispatchServlet.getAddress().getMajorIntersection() %>" <% if (f7Err.length()>0){%> class="textboxErr"<% } else { %> class="textbox"<%}%> onkeyup="ucase(this)" /></td>
                                <td><input type="text" size="30" maxlength="30" name="subdivision" value="<%=DispatchServlet.getAddress().getSubdivision() %>" class="textbox" " class="textbox" onkeyup="ucase(this)" /></td>
                                <td></td>
                             </tr>
                             <tr>
                             	<td class="fieldError"><%=f7Err%></td>
                                <td></td>
                                <td></td>
                             </tr>
                         </table>
                         <table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td class="fieldHeading" >Street Suffix<%=required%></td>
                                <td class="fieldHeading" >Structure Type<%=required%></td>
                                <td width="50" class="fieldHeading" >Unit</td>
                                <td class="fieldHeading" >Building</td>
                                <td class="fieldHeading" >Floor</td>
                                 <td width="40%"></td>
                             </tr>  
                              
                            <tr>
                            	<td>
                                  <%
                                     ddl = (ArrayList)session.getAttribute("dllStreetSuffix");
                                  %>
                                  <select name="streetSuffix" <% if (f8Err.length()>0){%> class="ddlErr"<% } else { %> class="ddl"<%}%>>
                                    <option value="">
                                    </option>
                                    <%
                                    if (ddl!=null) {
                                      for (int j=0;j<ddl.size();j++) {
                                        %>
                                        <option 
                                            value="<%=ddl.get(j)%>"
                                            <%
                                            if
                                            (ddl.get(j).equals(DispatchServlet.getAddress().getStreetSuffix()))
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
                                </td>
                                <td>
                                  <%
                                     ddl = (ArrayList)session.getAttribute("dllStructure");
                                  %>
                                  <select name="structureType" <% if (f9Err.length()>0){%> class="ddlErr"<% } else { %> class="ddl"<%}%>>
                                    <option value="">
                                    </option>
                                    <%
                                    if (ddl!=null) {
                                      for (int j=0;j<ddl.size();j++) {
                                        %>
                                        <option 
                                            value="<%=ddl.get(j)%>"
                                            <%
                                            if
                                            (ddl.get(j).equals(DispatchServlet.getAddress().getStructureType()))
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
                                </td>
                                <td><input type="text" size="5" maxlength="5" name="building" value="<%=DispatchServlet.getAddress().getBuilding() %>" class="textbox"  class="textbox" /></td>
                                <td><input type="text" size="5" maxlength="5" name="floor" value="<%=DispatchServlet.getAddress().getFloor() %>" class="textbox" class="textbox" /></td>
                                <td>
                                	<%
                                     ddl = (ArrayList)session.getAttribute("dllFloor");
                                  %>
                                  <select name="floor" <% if (f9Err.length()>0){%> class="ddlErr"<% } else { %> class="ddl"<%}%>>
                                    <option value="">
                                    </option>
                                    <%
                                    if (ddl!=null) {
                                      for (int j=0;j<ddl.size();j++) {
                                        %>
                                        <option 
                                            value="<%=ddl.get(j)%>"
                                            <%
                                            if
                                            (ddl.get(j).equals(DispatchServlet.getAddress().getFloor()))
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
                                </td>
                                <td></td>
                            </tr>   
                            <tr>
                             	<td class="fieldError"><%=f8Err%></td>
                                <td class="fieldError"><%=f9Err%></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                             </tr>                         
                        </table>  
                        
                         <table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td width="5" class="fieldHeading" ><input type="checkbox" name="elevatorFlag" value="YES" <% if (DispatchServlet.getAddress().getElevatorFlag().equals("Y")) {%>checked<%}%>/></td>
                                <td class="fieldHeading" >Check if elevator access is available</td>
                            </tr> 
                            <tr>
                            	<td width="5" class="fieldHeading" ><input type="checkbox" name="gateFlag" value="YES" <% if (DispatchServlet.getAddress().getGateFlag().equals("Y")) {%>checked<%}%>/></td>
                                <td class="fieldHeading" >Check if community is gated</td>
                            </tr> 
                            <tr>
                            	<td colspan="2" class="fieldHeading" >Gate Instructions</td>
                            </tr>
                            <tr>
                                <td colspan="2" class="fieldHeading" >
                                    <%
                                     ddl = (ArrayList)session.getAttribute("dllGate");
                                    %>
                                	 <select name="gateInstructions"   <% if (f14Err.length()>0){%> class="ddlErr"<% } else { %> class="ddl"<%}%>>
                                    <option value="">
                                    <%
                                    if (ddl!=null) {
                                      for (int j=0;j<ddl.size();j++) {
                                        %>
                                        <option 
                                            value="<%=ddl.get(j)%>"
                                            <%
                                            if
                                            (ddl.get(j).equals(DispatchServlet.getAddress().getGateInstructions()))
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
                                </td>
                            </tr> 
                            <tr>
                            	<td colspan="2" class="fieldError"><%=f14Err%></td>
                            </tr>
                            
                         </table>
                         
                        <table>
                        <tr>
            				<td height="20" bgcolor="#FFFFFF"></td>
            			</tr>
                        </table>
                        
                        <hr class='dotted' />
                         <h1>Donation Details</h1>
                         
                            <table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td width="120" class="fieldHeading" >Dispatch Date<%=required%></td>
                            </tr>
                            <tr>
                            	<td>
                                 <input 
                                      name="dispatchDate"
                                      size="12" class="tcal"
                                      value="<%=DispatchServlet.getDonation().getDispatchDate()%>"
                                  />
                                  </td>
                            </tr>
                            <tr>
                             	<td class="fieldError"><%=f13Err%></td>
                            </tr>
                            </table>
                            
                            <table width="95%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                            	<td width="5" class="fieldHeading" ><input type="checkbox" name="specialFlag" value="Y" <% if (DispatchServlet.getDonation().getSpecialFlag().equals("Y")) {%>checked<%}%>/></td>
                                <td class="fieldHeading" >Check if this donation is a special</td>
                            </tr> 
                         </table>
                        <table width="95%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                            	<td width="120" class="fieldHeading" >Call Requirements<%=required%></td>
                                <td class="fieldHeading" >
                                  <%
                                     ddl = (ArrayList)session.getAttribute("dllCallReq");
                                  %>
                                  <select name="callRequirements" <% if (f12Err.length()>0){%> class="ddlErr"<% } else { %> class="ddl"<%}%>>
                                    <option value="">
                                    </option>
                                    <%
                                    if (ddl!=null) {
                                      for (int j=0;j<ddl.size();j++) {
                                        %>
                                        <option 
                                            value="<%=ddl.get(j)%>"
                                            <%
                                            if
                                            (ddl.get(j).equals(DispatchServlet.getDonation().getCallRequirements()))
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
                                </td>
                            </tr>   
                            <tr>
                             	<td class="fieldError"><%=f12Err%></td>
                            </tr>                        
                         </table>
                        </br>
                           <table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td class="itemName">A/C</td>
                                <td class="itemQuantity"><input type="text" name="ac" value="<%=DispatchServlet.getDonation().getAc() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td class="itemName">Bedding</td>
                                <td class="itemQuantity">
                                	<input type="text" name="bedding" value="<%=DispatchServlet.getDonation().getBedding() %>" size="2" maxlength="2" class="textbox" />
                                 	<%
                                     ddl = (ArrayList)session.getAttribute("dllQtyType");
									  %>
									  <select name="beddingQtyType" class="ddl">
										<option value="">
										</option>
										<%
										if (ddl!=null) {
										  for (int j=0;j<ddl.size();j++) {
											%>
											<option 
												value="<%=ddl.get(j)%>"
												<%
												if
												(ddl.get(j).equals(DispatchServlet.getDonation().getBeddingQtyType()))
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
                                 </td>
                          		<td class="itemName">Books</td>
                                <td class="itemQuantity">
                                	<input type="text" name="books" value="<%=DispatchServlet.getDonation().getBooks() %>" size="2" maxlength="2" class="textbox" />
                                	<%
                                     ddl = (ArrayList)session.getAttribute("dllQtyType");
									  %>
									  <select name="booksQtyType" class="ddl">
										<option value=""></option>
										</option>
										<%
										if (ddl!=null) {
										  for (int j=0;j<ddl.size();j++) {
											%>
											<option 
												value="<%=ddl.get(j)%>"
												<%
												if
												(ddl.get(j).equals(DispatchServlet.getDonation().getBooksQtyType()))
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
                                </td>
                                <td></td>
                              </tr>
                              <tr><td colspan="6" height="1"></td></tr>
                              <tr>
                            	<td class="itemName">Clothing</td>
                                <td class="itemQuantity">
                                	<input type="text" name="clothing" value="<%=DispatchServlet.getDonation().getClothing() %>" size="2" maxlength="2" class="textbox" />
                                    <%
                                     ddl = (ArrayList)session.getAttribute("dllQtyType");
									  %>
									  <select name="clothingQtyType" class="ddl">
										<option value=""></option>
										<%
										if (ddl!=null) {
										  for (int j=0;j<ddl.size();j++) {
											%>
											<option 
												value="<%=ddl.get(j)%>"
												<%
												if
												(ddl.get(j).equals(DispatchServlet.getDonation().getClothingQtyType()))
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
                                </td>
                                <td class="itemName">Computer</td>
                                <td class="itemQuantity"><input type="text" name="computer" value="<%=DispatchServlet.getDonation().getComputer() %>" size="2" maxlength="2" class="textbox" /></td>
                          		<td class="itemName">Desk</td>
                                <td class="itemQuantity"><input type="text"name="desk" value="<%=DispatchServlet.getDonation().getDesk() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td></td>
                              </tr>
                              <tr><td colspan="6" height="1"></td></tr>
                              <tr>
                            	<td class="itemName">Chest</td>
                                <td class="itemQuantity"><input type="text" name="chest" value="<%=DispatchServlet.getDonation().getChest() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td class="itemName">Armoire</td>
                                <td class="itemQuantity"><input type="text" name="armoire" value="<%=DispatchServlet.getDonation().getArmoire() %>" size="2" maxlength="2" class="textbox" /></td>
                          		<td class="itemName">Dresser</td>
                                <td class="itemQuantity"><input type="text" name="dresser" value="<%=DispatchServlet.getDonation().getDresser() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td></td>
                              </tr>
                              <tr><td colspan="6" height="1"></td></tr>
                              <tr>
                            	<td class="itemName">Mirror</td>
                                <td class="itemQuantity"><input type="text" name="mirror" value="<%=DispatchServlet.getDonation().getMirror() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td class="itemName">Nightstand</td>
                                <td class="itemQuantity"><input type="text" name="nightstand" value="<%=DispatchServlet.getDonation().getNightstand() %>" size="2" maxlength="2" class="textbox" /></td>
                          		<td class="itemName">Headboard</td>
                                <td class="itemQuantity"><input type="text" name="headboard" value="<%=DispatchServlet.getDonation().getHeadboard() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td></td>
                              </tr>
                              <tr><td colspan="6" height="1"></td></tr>
                              <tr>
                            	<td class="itemName">Footboard</td>
                                <td class="itemQuantity"><input type="text" name="footboard" value="<%=DispatchServlet.getDonation().getFootboard() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td class="itemName">Rails</td>
                                <td class="itemQuantity"><input type="text" name="rails" value="<%=DispatchServlet.getDonation().getRails() %>" size="2" maxlength="2" class="textbox" /></td>
                          		<td class="itemName">Lamp</td>
                                <td class="itemQuantity"><input type="text" name="lamp" value="<%=DispatchServlet.getDonation().getLamp() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td></td>
                              </tr>
                              <tr><td colspan="6" height="1"></td></tr>
                              <tr>
                            	<td class="itemName">Lawn Furniture</td>
                                <td class="itemQuantity"><input type="text" name="lawnFurniture" value="<%=DispatchServlet.getDonation().getLawnFurniture() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td class="itemName">Mattress/Box Spring</td>
                                <td class="itemQuantity">
                                	<input type="text" size="2" maxlength="2" name="mattress" value="<%=DispatchServlet.getDonation().getMattress() %>" class="textbox" />
                                     <%
                                     ddl = (ArrayList)session.getAttribute("dllMattress");
									  %>
									  <select name="mattressQtySize" class="ddl">
										<option value=""></option>
										<%
										if (ddl!=null) {
										  for (int j=0;j<ddl.size();j++) {
											%>
											<option 
												value="<%=ddl.get(j)%>"
												<%
												if
												(ddl.get(j).equals(DispatchServlet.getDonation().getMattressQtyType()))
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
                                </td>
                          		<td class="itemName">Misc Household Items</td>
                                <td class="itemQuantity"><input type="text" name="miscHouseholdItems" value="<%=DispatchServlet.getDonation().getMiscHouseholdItems() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td></td>
                              </tr>
                              <tr><td colspan="6" height="1"></td></tr>
                              <tr>
                            	<td class="itemName">Refridgerator</td>
                                <td class="itemQuantity"><input type="text" name="refridgerator" value="<%=DispatchServlet.getDonation().getRefridgerator() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td class="itemName">Stove</td>
                                <td class="itemQuantity"><input type="text" name="stove" value="<%=DispatchServlet.getDonation().getStove() %>" size="2" maxlength="2" class="textbox" /></td>
                          		<td class="itemName">Recliner</td>
                                <td class="itemQuantity"><input type="text" name="recliner" value="<%=DispatchServlet.getDonation().getRecliner() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td></td>
                              </tr>
                              <tr><td colspan="6" height="1"></td></tr>
                               <tr>
                            	<td class="itemName">Sofa</td>
                                <td class="itemQuantity"><input type="text" name="sofa" value="<%=DispatchServlet.getDonation().getSofa() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td class="itemName">Loveseat</td>
                                <td class="itemQuantity"><input type="text" name="loveseat" value="<%=DispatchServlet.getDonation().getLoveseat() %>" size="2" maxlength="2" class="textbox" /></td>
                          		<td class="itemName">Wall Unit</td>
                                <td class="itemQuantity"><input type="text" name="wallUnit" value="<%=DispatchServlet.getDonation().getWallUnit() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td></td>
                              </tr>
                              <tr><td colspan="6" height="1"></td></tr>
                               <tr>
                            	<td class="itemName">Table</td>
                                <td class="itemQuantity"><input type="text" name="table" value="<%=DispatchServlet.getDonation().getTable() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td class="itemName">Chair</td>
                                <td class="itemQuantity"><input type="text" name="chair" value="<%=DispatchServlet.getDonation().getChair() %>" size="2" maxlength="2" class="textbox" /></td>
                          		<td class="itemName">Television</td>
                                <td class="itemQuantity"><input type="text" name="television" value="<%=DispatchServlet.getDonation().getTelevision() %>" size="2" maxlength="2" class="textbox" onClick="javascript:alert('Please notify the donor that Faith Farm does not accept televisions that are more than 10 years old.  (The year can be located on the serial tag on the back of the televion.)');"/>
                                <%
                                     ddl = (ArrayList)session.getAttribute("dllTvSize");
									  %>
									  <select name="televisionSize" class="ddl">
										<option value=""></option>
										<%
										if (ddl!=null) {
										  for (int j=0;j<ddl.size();j++) {
											%>
											<option 
												value="<%=ddl.get(j)%>"
												<%
												if
												(ddl.get(j).equals(DispatchServlet.getDonation().getTelevisionSize()))
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
                                </td>
                                <td></td>
                              </tr>
                              <tr><td colspan="6" height="1"></td></tr>
                               <tr>
                            	<td class="itemName">Electronics</td>
                                <td class="itemQuantity"><input type="text" name="electronics" value="<%=DispatchServlet.getDonation().getElectronics() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td class="itemName">Washer</td>
                                <td class="itemQuantity"><input type="text" name="washer" value="<%=DispatchServlet.getDonation().getWasher() %>" size="2" maxlength="2" class="textbox" /></td>
                          		<td class="itemName">Dryer</td>
                                <td class="itemQuantity"><input type="text" name="dryer"  value="<%=DispatchServlet.getDonation().getDryer() %>" size="2" maxlength="2" class="textbox" /></td>
                                <td></td>
                              </tr>
                              <tr><td colspan="6" height="1"></td></tr>
                               <tr>
                            	<td class="itemName">Exercise Equipment</td>
                                <td colspan="6" class="itemQuantity"><input type="text" name="exerciseEquipment" value="<%=DispatchServlet.getDonation().getExerciseEquipment() %>" size="2" maxlength="2" class="textbox" /></td>
                               </tr>
                               <tr><td colspan="6" height="1"></td></tr>
                               <tr>
                            	<td colspan="7" class="itemName" >Special Notes
                                	</br>
                                	<textarea class="area" name="specialNotes" onkeyup="ucase(this)"><%=DispatchServlet.getDonation().getSpecialNotes() %></textarea>
                                </td>
                               </tr>
                            </table>
                            
                            <table width="95%" cellpadding="0" cellspacing="0" border="0">
                        	<tr>
                            	<td width="5" class="fieldHeading" ><input type="checkbox" name="" value="Y"/></td>
                                <td class="fieldHeading" >
                                	I acknowledge that I have read Faith Farm's delivery/donation terms and policies to the customer.
                                </td>
                            </tr> 
                         </table>
                         
                         </br></br>
                         
                         <% if ("Y".equals(update)) { %>
							<input type="submit" name="action" value="Update Ticket" title="Update Ticket" />
                         	<input type="submit" name="action" value="Update & Print" title="Update Ticket & Print" />
                            <input type="hidden" name="donorId" value="<%=DispatchServlet.getDonor().getDonorId() %>"/>
                            <input type="hidden" name="addressId" value="<%=DispatchServlet.getAddress().getAddressId() %>"/>
                            <input type="hidden" name="donationId" value="<%=DispatchServlet.getDonation().getDonationId() %>"/>
                         <% } else { %>
                         	<input type="submit" name="action" value="Save Ticket" class="imageButtonSave" title="Save Ticket" />
                         	<input type="submit" name="action" value="Save & Print" class="imageButtonPrint" title="Save Ticket & Print" />
                         <% } %>
                          
               		
           	</td>
			</tr>
</form>			
<jsp:include page="footer.jsp" flush="true" />
