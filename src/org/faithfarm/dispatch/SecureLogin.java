package org.faithfarm.dispatch;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.faithfarm.domain.SystemUser;
import org.faithfarm.service.data.DispatchDao;

  
public class SecureLogin extends HttpServlet {
     
	private DispatchDao dao = new DispatchDao();
     
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			   throws ServletException, IOException
			   { 
			      String next="";
			      
		 		  HttpSession session = req.getSession(true);		     
			      
			      String action=req.getParameter("action");
			      
			      if ("login".equals(action)) 
			    	  this.secureLogin(req, resp, session);			      
			      else if ("logout".equals(action)) {
			    	  session.invalidate();
			    	  req.getRequestDispatcher("/logout.jsp").forward(req, resp);
			      }
			    	  
	 } 
	 
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			 throws ServletException, IOException
			 {
			     doGet(req, resp);
			 }
	 
	 private void secureLogin(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException{
	      String username=req.getParameter("username");
	      String password=req.getParameter("password");
	      String next="";
	      
	      session.setAttribute("ERRORS", new ArrayList());
	      boolean success=dao.secureLogin(username, password, session);
	    
	      if (success) {
	    	  buildStateList(session);
	    	  this.loadLimits(req, session);
	    	  SystemUser user = (SystemUser)session.getAttribute("USER_" + session.getId());
	    	  if (user.getLoginCount().intValue()==0)
	    		  next="setpassword.jsp";	    	  
	    	  else
	    		  next="ticket"; 
	      }
	      else
	    	  next="login_error.jsp";
	      req.getRequestDispatcher("/"+next).forward(req, resp);
	    }
	 
	  private void loadLimits( HttpServletRequest req, HttpSession session) {
			GregorianCalendar calendar = new GregorianCalendar();
			
			Date now = calendar.getTime();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat df2 = new SimpleDateFormat("EEEE");
			int limit = dao.getDailyLimit(df.format(now), session);
			int count = dao.getDailyDispatchCount(df.format(now), session);
			
			req.setAttribute("LIMIT1", limit+"");
			req.setAttribute("COUNT1", count+"");
			req.setAttribute("DATE1", df2.format(now).substring(0,3)+" "+df.format(now));
						
			//Advance the calendar one day: 
			for (int day=1;day<7;day++) {
				Date tomorrow = calendar.getTime();
				tomorrow.setTime(now.getTime() + (24*day)*60*60*1000);
				String formattedDate = df.format(tomorrow);
				String sDay = df2.format(tomorrow);
				
				limit = dao.getDailyLimit(formattedDate, session);
				count = dao.getDailyDispatchCount(formattedDate, session);
				req.setAttribute("LIMIT"+(day+1), limit+"");
				req.setAttribute("COUNT"+(day+1), count+"");
				req.setAttribute("DATE"+(day+1), sDay.substring(0,3)+" "+formattedDate);
			}
						
	  }
	   public void buildStateList(HttpSession session)
	    {
		   
		   ArrayList suffix = new ArrayList();
		   suffix.add("Jr.");
		   suffix.add("Sr.");
		   suffix.add("I");
		   suffix.add("II");
		   suffix.add("III");
		   
		   ArrayList securityQuestion = new ArrayList();
		   securityQuestion.add("What is your mother's maiden name?");
		   securityQuestion.add("What city were you born?");
		   securityQuestion.add("What is the name of your first pet?");
		   securityQuestion.add("Who was your childhood best friend?");
		   securityQuestion.add("What is your favorite movie?");
		   securityQuestion.add("Who is your favorite actor or actress?");
		    
	      ArrayList states = new ArrayList();
	        states.add("Alabama");
	        states.add("Alaska");
	        states.add("Arizona");
	        states.add("Arkansas");
	        states.add("California");
	        states.add("Colorado");
	        states.add("Connecticut");
	        states.add("Delaware");
	        states.add("Dist of Columbia");
	        states.add("Florida");
	        states.add("Georgia");
	        states.add("Hawaii");
	        states.add("Idaho");
	        states.add("Illinois");
	        states.add("Indiana");
	        states.add("Iowa");
	        states.add("Kansas");
	        states.add("Kentucky");
	        states.add("Louisiana");
	        states.add("Maine");
	        states.add("Maryland");
	        states.add("Massachusetts");
	        states.add("Michigan");
	        states.add("Minnesota");
	        states.add("Mississippi");
	        states.add("Missouri");
	        states.add("Montana");
	        states.add("Nebraska");
	        states.add("Nevada");
	        states.add("New Hampshire");
	        states.add("New Jersey");
	        states.add("New Mexico");
	        states.add("New York");
	        states.add("North Carolina");
	        states.add("North Dakota");
	        states.add("Ohio");
	        states.add("Oklahoma");
	        states.add("Oregon");
	        states.add("Pennsylvania");
	        states.add("Rhode Island");
	        states.add("South Carolina");
	        states.add("South Dakota");
	        states.add("Tennessee");
	        states.add("Texas");
	        states.add("Utah");
	        states.add("Vermont");
	        states.add("Virginia");
	        states.add("Washington");
	        states.add("West Virginia");
	        states.add("Wisconsin");
	        states.add("Wyoming");
	 
	        ArrayList status = new ArrayList();
	        status.add("Pending");
	        status.add("Assigned");
	        status.add("Cancelled by Donor");
	        status.add("Completed");
	        status.add("In Route");
	        status.add("No Response");
	        status.add("Reschedule");
	        status.add("10/5");
	        
	        ArrayList callReq = new ArrayList();
	        callReq.add("tag(donation only)");
	        callReq.add("half-hour call");
	        callReq.add("one hour call");
	        
	        ArrayList location = new ArrayList();
	        location.add("inside");
	        location.add("carport");
	        location.add("outside");
	        location.add("porch");
	        location.add("other");
	        
	        ArrayList structureType = new ArrayList();
	        structureType.add("apartment");
	        structureType.add("business");
	        structureType.add("condominium");
	        structureType.add("home");
	        structureType.add("townhome");
	        structureType.add("assisted living facility");
	        structureType.add("mobile home");
	        structureType.add("");
	        
	        ArrayList streetSuffix = new ArrayList();
	        streetSuffix.add("avenue");
	        streetSuffix.add("boulevard");
	        streetSuffix.add("circle");
	        streetSuffix.add("court");
	        streetSuffix.add("drive");
	        streetSuffix.add("highway");
	        streetSuffix.add("lane");
	        streetSuffix.add("manor");
	        streetSuffix.add("parkway");
	        streetSuffix.add("place");
	        streetSuffix.add("road");
	        streetSuffix.add("street");
	        streetSuffix.add("terrace");
	        streetSuffix.add("way");
	        streetSuffix.add("other");
	        
	        ArrayList source = new ArrayList();
	        source.add("repeat donor/customer");
	        source.add("trucks");
	        source.add("news/magazine");
	        source.add("radio/tv ad");
	        source.add("friend/relative");
	        source.add("internet");
	        source.add("other");
	        
	        ArrayList gate = new ArrayList();
	        gate.add("callbox code");
	        gate.add("ask security");
	        gate.add("buzz owner by last name");
	        
	        ArrayList floor = new ArrayList();
	        for (int i = 1; i < 51; i++) {
	             floor.add(i);
	        }
	        
	        ArrayList qtyType = new ArrayList();
	        qtyType.add("boxes");
	        qtyType.add("bags");

	        ArrayList pieces = new ArrayList();
	        pieces.add("1 piece");
	        pieces.add("2 pieces");
	        pieces.add("3 pieces");
	        pieces.add("4 pieces");
	        pieces.add("5 pieces");
	        pieces.add("6 pieces");
	        pieces.add("7 pieces");
	        pieces.add("8 pieces");
	        pieces.add("9 pieces");
	        pieces.add("10 pieces");
	        
	        ArrayList mattress = new ArrayList();
	        mattress.add("twin");
	        mattress.add("full");
	        mattress.add("queen");
	        mattress.add("king");
	        
	        ArrayList tvsize = new ArrayList();
	        tvsize.add("9-inch");
	        tvsize.add("13-inch");
	        tvsize.add("21-inch");
	        tvsize.add("27-inch");
	        tvsize.add("31-inch");
	        tvsize.add("40-inch");
	        tvsize.add("48-inch");
	        tvsize.add("Other");
	        
	        
	        ArrayList tableType = new ArrayList();
	        tableType.add("Dining");
	        tableType.add("End");
	        tableType.add("Coffee");
	        tableType.add("Kitchen");
	        tableType.add("Other");
	        tableType.add("Mixed Types");
	        
	        ArrayList chairType = new ArrayList();
	        chairType.add("Dinette");
	        chairType.add("Kitchen table");
	        chairType.add("Other");
	        chairType.add("Mixed Types");
	        
	        
	        session.setAttribute("dllSuffix",convertToUpperCase(suffix));
	        session.setAttribute("dllSecurityQuestion",convertToUpperCase(securityQuestion));
	        session.setAttribute("dllLocation",convertToUpperCase(location));
	        session.setAttribute("dllStatus",convertToUpperCase(status));
	        session.setAttribute("dllLocation",convertToUpperCase(location));
	        session.setAttribute("dllStructure",convertToUpperCase(structureType));
	        session.setAttribute("dllStreetSuffix",convertToUpperCase(streetSuffix));
	        session.setAttribute("dllState",convertToUpperCase(states));
	        session.setAttribute("dllCallReq",convertToUpperCase(callReq));
	        session.setAttribute("dllGate",convertToUpperCase(gate));
	        session.setAttribute("dllFloor",floor);
	        session.setAttribute("dllSource",convertToUpperCase(source));
	        session.setAttribute("dllQtyType",convertToUpperCase(qtyType));
	        session.setAttribute("dllMattress",convertToUpperCase(mattress));
	        session.setAttribute("dllTvSize",convertToUpperCase(tvsize));
	        session.setAttribute("dllTableType",convertToUpperCase(tableType));
	        session.setAttribute("dllChairType",convertToUpperCase(chairType));
	        session.setAttribute("dllPieces",convertToUpperCase(pieces));
	        
	   }
	   
	   private ArrayList convertToUpperCase(ArrayList list) {
		   
		   ArrayList newList = new ArrayList();
		   
		   for (int i=0;i<list.size();i++) {
			   String value = (String)list.get(i);
			   newList.add(value.toUpperCase());
		   }
		   
		   return newList;
	   }
}
