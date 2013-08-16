<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="styles/style.css" rel="stylesheet" type="text/css" />

</head>
  
<body topmargin="0" leftmargin="0" bgcolor="#383838">
 
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td width="100%" height="500" valign="center" align="center" border="0">
		
		<table width="486" height="229" cellpadding="0" cellspacing="0" border="0">
			<tr>
            	<td  width="486" align="center" valign="center" background="images/loginPanel.png" >
                <form method="POST" action="<%=request.getContextPath()%>/login"> 
                    <table align="center" border="0" >
                        <tr><td height="90" colspan="2"></td></tr>
                        <tr>
                            <td width="80"></td>
                            <td width="142" height="23">
                                <input type="text" size="20" maxlength="15" name="username" value="username" class="login" onfocus="javascript:this.value=''"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="80"></td>
                            <td width="142" height="23">
                                <input type="text" size="20" maxlength="15" name="password" value="password" class="login" onfocus="javascript:this.value='';this.type='password'"/>
                            </td>
                        </tr>
                     </table>
                      <table align="center" valign="center" border="0" >
                        <tr><td height="10" colspan="2"></td></tr>
                        <tr>
                        	<td width="200"></td>
                            <td width="206" valign="bottom" align="center">
                                <input type="submit" name="action" value="login" class="imageButtonLogin" title="Login" /> 
                            </td>
                            
                        </tr>
                    </table>
                  </form>
                </td>
           </tr>
		</table>
        
</td>
</tr>
</table>
				
</br></br></br></br></br></br>                
<div id="footer" align="center">
    Faith Farm Ministries, 9538 U.S. 441, Boynton Beach, FL 33472. &copy;2013</br>
    (561) 737-2222 | (561) 735-0227 FAX</br></br>					
</div>
			
</body>
</html>
