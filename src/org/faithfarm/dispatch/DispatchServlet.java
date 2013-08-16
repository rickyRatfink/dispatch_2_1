package org.faithfarm.dispatch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.faithfarm.domain.Address;
import org.faithfarm.domain.Donation;
import org.faithfarm.domain.Donor;
import org.faithfarm.domain.SystemUser;
import org.faithfarm.service.data.DispatchDao;
import org.faithfarm.util.Validator;

public class DispatchServlet extends HttpServlet {

	private DispatchDao dao = new DispatchDao();
	private static Donor donor = new Donor();
	private static Address address = new Address();
	private static Donation donation = new Donation();
	private static SystemUser systemUser = new SystemUser();
	
	private static String dispatchDate="";
	private static String limit="";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		String action = req.getParameter("action");
		Validator valid8r = new Validator();
		boolean pass1 = false;
		
		SystemUser user = (SystemUser)session.getAttribute("USER_"+session.getId());
		
		this.setFields(req);
		
		if ("New".equals(action)) {
			this.setDonation(new Donation());
			this.setDonor(new Donor());
			this.setAddress(new Address());
			
			this.setFields(req);
			
			req.getRequestDispatcher("/newticket.jsp").forward(req, resp);
		}
		else if ("Search".equals(action)) {
			this.setDonation(new Donation());
			this.setDonor(new Donor());
			this.setAddress(new Address());
			this.setFields(req);
			session.setAttribute("RESULTS_"+session.getId(),null);
			req.getRequestDispatcher("/search.jsp").forward(req, resp);
		}
		else if ("Level".equals(action)) {
			this.setDispatchDate("");
			this.setLimit("");
			req.getRequestDispatcher("/ticket_level.jsp").forward(req, resp);
		}
		else if ("SaveLevel".equals(action)) {
			boolean success=true;
			
			String dispatchDate=valid8r.cleanData(req.getParameter("dispatchDate"));
			String sLimit=valid8r.cleanData(req.getParameter("level"));
			int limit=0;
			
			String fieldErr = valid8r.validateRequired("Pickup Date", dispatchDate);
			req.setAttribute("field1Err", fieldErr);
			if (fieldErr.length() > 0)
				success = false;
			else
				this.setDispatchDate(dispatchDate);
			
			fieldErr = valid8r.validateRequired("Daily Limit", sLimit);
			req.setAttribute("field2Err", fieldErr);
			if (fieldErr.length() > 0)
				success = false;
			else {
				try {
					this.setLimit(sLimit);
					limit=Integer.parseInt(sLimit);
				} catch (Exception e) {
					req.setAttribute("field2Err", "Daily Limit is invalid");
				}
			}
			
			int retCode =0;
			
			if (!success) 
				req.getRequestDispatcher("/ticket_level.jsp").forward(req, resp);
			else {
				retCode = dao.updateDailyLimit(dispatchDate, limit, user.getFarmBase(), user.getUsername(), session);
				//insert new limit if limit doesn't exist
				if (retCode==0) {
					
					retCode=dao.insertDailyLimit(dispatchDate, limit, user.getFarmBase(), user.getUsername(), session);
					if (retCode!=1)
						req.getRequestDispatcher("/error.jsp").forward(req, resp);
					else {
						req.setAttribute("MESSAGE", "daily limit successfully set.");
						req.getRequestDispatcher("/newticket.jsp").forward(req, resp);
					}
				}
				else {
					req.setAttribute("MESSAGE", "daily limit successfully updated.");
					req.getRequestDispatcher("/newticket.jsp").forward(req, resp);
				}
			}
		}
		else if ("SearchTickets".equals(action)) {
			String lastname=valid8r.cleanData(req.getParameter("lastname"));
			if ("lastname".equals(lastname)) lastname="";
			String firstname=valid8r.cleanData(req.getParameter("firstname"));
			if ("firstname".equals(firstname)) firstname="";
			String confirmation=valid8r.cleanData(req.getParameter("confirmation"));
			if ("confirmation#".equals(confirmation)) confirmation="";
			String dispatchDate=valid8r.cleanData(req.getParameter("dispatchDate"));
			if ("date".equals(dispatchDate)) dispatchDate="";
			
			int retCode = dao.searchTickets(lastname, firstname, confirmation, dispatchDate, session);
			if (retCode!=1)
				req.getRequestDispatcher("/error.jsp").forward(req, resp);
			else {
				req.getRequestDispatcher("/search.jsp").forward(req, resp);
			}
		} 
		else if ("ChangePassword".equals(action)) {
			String pwd1=valid8r.cleanData(req.getParameter("password1"));
			String pwd2=valid8r.cleanData(req.getParameter("password2"));
			String question=valid8r.cleanData(req.getParameter("question"));
			String answer=valid8r.cleanData(req.getParameter("answer"));
			
			pass1 = validateChangePassword(pwd1, pwd2, question, answer, req);
			
			if (pass1) {
				int retCode = dao.updateLoginCount(user.getUserId(), session);
				retCode = dao.updatePassword(user.getUserId(), pwd1, question, answer, session);
				if (retCode!=1)
					req.getRequestDispatcher("/error.jsp").forward(req, resp);
				else {
					req.setAttribute("MESSAGE_"+session.getId(),"Password successfully changed");
					req.getRequestDispatcher("/main.jsp").forward(req, resp);
				}
			} else
				req.getRequestDispatcher("/setpassword.jsp").forward(req, resp);
		}
		else if ("Save Ticket".equals(action)) {
			pass1 = validateTicket(req);
			donation.setCreationDate(valid8r.getEpoch()+"");
			donation.setCreatedBy(user.getUsername());
			donation.setStatus("Pending");
			donation.setFarmBase(user.getFarmBase());
			donation.setDonorId(this.getDonor().getDonorId());
			if (pass1) {
				int retCode=dao.insertDonation(donor, address, donation, session);
				if (retCode!=1)
					req.getRequestDispatcher("/error.jsp").forward(req, resp);
				else
					req.getRequestDispatcher("/newticket.jsp").forward(req, resp);
			}
			else
				req.getRequestDispatcher("/newticket.jsp").forward(req, resp);
		}
		
		else if ("Users".equals(action)) {
			int retCode=dao.searchSystemUsers(user.getFarmBase(), session);
			if (retCode!=1)
				req.getRequestDispatcher("/error.jsp").forward(req, resp);
			else {
				req.getRequestDispatcher("/new_user.jsp").forward(req, resp);
			}
		}
		else if ("DeleteUser".equals(action)) {
			String id = req.getParameter("userId");
			boolean retCode=dao.deleteSystemUser(Integer.parseInt(id), session);
			if (!retCode)
				req.getRequestDispatcher("/error.jsp").forward(req, resp);
			else {
				int rc=dao.searchSystemUsers(user.getFarmBase(), session);
				if (rc!=1)
					req.getRequestDispatcher("/error.jsp").forward(req, resp);
				else {
					req.setAttribute("MESSAGE", "User successfully deleted.");
					req.getRequestDispatcher("/new_user.jsp").forward(req, resp);
				}
			}
		}
		else if ("NewUser".equals(action)) {
			SystemUser u = new SystemUser();
			u.setUsername("");
			this.setSystemUser(u);	
			
			req.getRequestDispatcher("create_user.jsp").forward(req, resp);
		}
		else if ("SaveUser".equals(action)) {
			boolean success=true;
			
			String username=valid8r.cleanData(req.getParameter("username"));
			String role=valid8r.cleanData(req.getParameter("role"));
			
			String fieldErr = valid8r.validateRequired("Username", username);
			req.setAttribute("field1Err", fieldErr);
			if (fieldErr.length() > 0)
				success = false;
			else
				this.getSystemUser().setUsername(username);
			
			fieldErr = valid8r.validateRequired("Role", role);
			req.setAttribute("field2Err", fieldErr);
			if (fieldErr.length() > 0)
				success = false;
			else
				this.getSystemUser().setUserRole(role);;
						
			int retCode =0;
			
			if (!success) 
				req.getRequestDispatcher("/create_user.jsp").forward(req, resp);
			else {
				this.getSystemUser().setFarmBase(user.getFarmBase());
				this.getSystemUser().setCreatedBy(user.getUsername());
				this.getSystemUser().setPassword(user.getUsername());
				
				long id = dao.insertSystemUser(this.getSystemUser(), session);
				//insert new limit if limit doesn't exist
				this.setSystemUser(new SystemUser());
				if (id==0)
					req.getRequestDispatcher("/error.jsp").forward(req, resp);
					else {
						
						req.setAttribute("MESSAGE", "New user successfully added.");
						retCode=dao.searchSystemUsers(user.getFarmBase(), session);
						if (retCode!=1)
							req.getRequestDispatcher("/error.jsp").forward(req, resp);
						else {
							req.getRequestDispatcher("/new_user.jsp").forward(req, resp);
						}
						req.getRequestDispatcher("/new_user.jsp").forward(req, resp);
					}
				}
			
		}
		else if ("CallLog".equals(action)) {
			String type=req.getParameter("type");
			String source=req.getParameter("source");
			pass1 = validateCallLog(type,source,req);
			
			if (pass1) {
				int retCode=dao.insertCallLog(type, source, user.getUsername(), user.getFarmBase(), session);
				if (retCode!=1)
					req.getRequestDispatcher("/error.jsp").forward(req, resp);
				else
					req.getRequestDispatcher("/newticket.jsp").forward(req, resp);
			} 
			else
				req.getRequestDispatcher("/call_log.jsp").forward(req, resp);
		}
		else
			req.getRequestDispatcher("/main.jsp").forward(req, resp);
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	public boolean validateCallLog(String type, String source, HttpServletRequest req) {
		boolean success = true;
		Validator valid8r = new Validator();

		String fieldErr = "";

		fieldErr = valid8r.validateRequired("Type", type);
		req.setAttribute("field1Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;
		
		fieldErr = valid8r.validateRequired("Source", source);
		req.setAttribute("field2Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;
		
		return success;
	}
	public boolean validateChangePassword(String pwd1, String pwd2, String question, String answer, HttpServletRequest req) {
		boolean success = true;
		Validator valid8r = new Validator();

		String fieldErr = "";

		fieldErr = valid8r.validateRequired("password", pwd1);
		req.setAttribute("field1Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;
		
		fieldErr = valid8r.validateRequired("password", pwd2);
		req.setAttribute("field2Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;
		
		if (!pwd1.equals(pwd2)) {
			req.setAttribute("field1Err", "Passwords do not match");
			req.setAttribute("field2Err", "Passwords do not match");
			success = false;
		}
		
		if (pwd1.length()<4) {
			req.setAttribute("field1Err", "Password must be at least 4 characters.");
			success=false;
		}
		
		if (pwd2.length()<4) {
			req.setAttribute("field2Err", "Password must be at least 4 characters.");
			success=false;
		}
		
		fieldErr = valid8r.validateRequired("security question", question);
		req.setAttribute("field3Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;
		
		fieldErr = valid8r.validateRequired("security answer", answer);
		req.setAttribute("field4Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;
		
		return success;
	}
	public boolean validateTicket(HttpServletRequest req) {
		boolean success = true;
		Validator valid8r = new Validator();

		String fieldErr = "";
		String field2Err = "";
		String field3Err = "";
		String field4Err = "";
		String field5Err = "";
		String field6Err = "";
		String field7Err = "";
		String field8Err = "";
		String field9Err = "";
		String field10Err = "";
		String field11Err = "";
		String field12Err = "";
		String field13Err = "";
		String field14Err = "";

		this.setFields(req);

		fieldErr = valid8r.validateRequired("Lastname", this.getDonor()
				.getLastname());
		req.setAttribute("field1Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r.validateRequired("Firstname", this.getDonor()
				.getFirstname());
		req.setAttribute("field2Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r.validateRequired("Address Line1", this.getAddress()
				.getLine1());
		req.setAttribute("field3Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r
				.validateRequired("City", this.getAddress().getCity());
		req.setAttribute("field4Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r.validateRequired("State", this.getAddress()
				.getState());
		req.setAttribute("field5Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r.validateRequired("Zipcode", this.getAddress()
				.getZipcode());
		req.setAttribute("field6Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r.validateRequired("Major Intersection", this
				.getAddress().getMajorIntersection());
		req.setAttribute("field7Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r.validateRequired("Street Suffix", this.getAddress()
				.getStreetSuffix());
		req.setAttribute("field8Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r.validateRequired("Structure Type", this.getAddress()
				.getStructureType());
		req.setAttribute("field9Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r.validatePhone(this.getDonor().getContactPhone());
		req.setAttribute("field10Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r.validateRequired("Email Address", this.getDonor()
				.getEmailAddress());
		req.setAttribute("field11Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;

		fieldErr = valid8r.validateRequired("Dispatch Date", this
				.getDonation().getDispatchDate());
		req.setAttribute("field13Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;	
		
		fieldErr = valid8r.validateRequired("Call Requirement", this
				.getDonation().getCallRequirements());
		req.setAttribute("field12Err", fieldErr);
		if (fieldErr.length() > 0)
			success = false;	
		

		return success;
	}

	private void setFields(HttpServletRequest req) {
		Validator valid8r = new Validator();

		this.getDonor().setLastname(
				valid8r.cleanData(req.getParameter("lastname")));
		this.getDonor().setFirstname(
				valid8r.cleanData(req.getParameter("firstname")));
		this.getDonor()
				.setSuffix(valid8r.cleanData(req.getParameter("suffix")));

		String phone1 = valid8r.cleanData(req.getParameter("phone1"));
		String phone2 = valid8r.cleanData(req.getParameter("phone2"));
		String phone3 = valid8r.cleanData(req.getParameter("phone3"));
		String phone = "(" + phone1 + ")" + phone2 + "-" + phone3;

		this.getDonor().setContactPhone(phone);
		this.getDonor().setEmailAddress(
				valid8r.cleanData(req.getParameter("email")));
		this.getAddress()
				.setLine1(valid8r.cleanData(req.getParameter("line1")));
		this.getAddress()
				.setLine2(valid8r.cleanData(req.getParameter("line2")));
		this.getAddress().setCity(valid8r.cleanData(req.getParameter("city")));
		this.getAddress()
				.setState(valid8r.cleanData(req.getParameter("state")));
		this.getAddress().setZipcode(
				valid8r.cleanData(req.getParameter("zipcode")));

		this.getAddress().setMajorIntersection(
				valid8r.cleanData(req.getParameter("majorIntersection")));
		this.getAddress().setSubdivision(
				valid8r.cleanData(req.getParameter("subdivision")));
		this.getAddress().setStreetSuffix(
				valid8r.cleanData(req.getParameter("streetSuffix")));
		this.getAddress().setStructureType(
				valid8r.cleanData(req.getParameter("structureType")));
		this.getAddress().setBuilding(
				valid8r.cleanData(req.getParameter("building")));
		this.getAddress().setUnit(valid8r.cleanData(req.getParameter("unit")));
		this.getAddress()
				.setFloor(valid8r.cleanData(req.getParameter("floor")));
		this.getAddress().setElevatorFlag(
				valid8r.cleanData(req.getParameter("elevatorFlag")));
		this.getAddress().setGateFlag(
				valid8r.cleanData(req.getParameter("gateFlag")));
		this.getAddress().setGateInstructions(
				valid8r.cleanData(req.getParameter("gateInstructions")));
		this.getDonation().setSpecialFlag(valid8r.cleanData(req.getParameter("specialFlag")));
		this.getDonation().setCallRequirements(valid8r.cleanData(req.getParameter("callRequirements")));
		this.getDonation().setDispatchDate(valid8r.cleanData(req.getParameter("dispatchDate")));
		this.getDonation().setMattressQtyType(valid8r.cleanData(req.getParameter("mattressQtySize")));
		this.getDonation().setTelevisionSize(valid8r.cleanData(req.getParameter("televisionSize")));
		this.getDonation().setBeddingQtyType(valid8r.cleanData(req.getParameter("beddingQtyType")));
		this.getDonation().setBooksQtyType(valid8r.cleanData(req.getParameter("booksQtyType")));
		this.getDonation().setClothingQtyType(valid8r.cleanData(req.getParameter("clothingQtyType")));
		
		this.getDonation().setAc(valid8r.cleanData(req.getParameter("ac")));
		this.getDonation().setBedding(valid8r.cleanData(req.getParameter("bedding")));
		this.getDonation().setBooks(valid8r.cleanData(req.getParameter("books")));
		this.getDonation().setClothing(valid8r.cleanData(req.getParameter("clothing")));
		this.getDonation().setComputer(valid8r.cleanData(req.getParameter("computer")));
		this.getDonation().setDesk(valid8r.cleanData(req.getParameter("desk")));
		this.getDonation().setChest(valid8r.cleanData(req.getParameter("chest")));
		this.getDonation().setArmoire(valid8r.cleanData(req.getParameter("armoire")));
		this.getDonation().setDresser(valid8r.cleanData(req.getParameter("dresser")));
		this.getDonation().setMirror(valid8r.cleanData(req.getParameter("mirror")));
		this.getDonation().setNightstand(valid8r.cleanData(req.getParameter("nightstand")));
		this.getDonation().setHeadboard(valid8r.cleanData(req.getParameter("headboard")));
		this.getDonation().setFootboard(valid8r.cleanData(req.getParameter("footboard")));
		this.getDonation().setRails(valid8r.cleanData(req.getParameter("rails")));
		this.getDonation().setLamp(valid8r.cleanData(req.getParameter("lamp")));
		this.getDonation().setLawnFurniture(valid8r.cleanData(req.getParameter("lawnFurniture")));
		this.getDonation().setMattress(valid8r.cleanData(req.getParameter("mattress")));
		this.getDonation().setMiscHouseholdItems(valid8r.cleanData(req.getParameter("miscHouseholdItems")));
		this.getDonation().setRefridgerator(valid8r.cleanData(req.getParameter("refridgerator")));
		this.getDonation().setStove(valid8r.cleanData(req.getParameter("stove")));
		this.getDonation().setRecliner(valid8r.cleanData(req.getParameter("recliner")));
		this.getDonation().setSofa(valid8r.cleanData(req.getParameter("sofa")));
		this.getDonation().setLoveseat(valid8r.cleanData(req.getParameter("loveseat")));
		this.getDonation().setWallUnit(valid8r.cleanData(req.getParameter("wallUnit")));
		this.getDonation().setTable(valid8r.cleanData(req.getParameter("table")));
		this.getDonation().setChair(valid8r.cleanData(req.getParameter("chair")));
		this.getDonation().setTelevision(valid8r.cleanData(req.getParameter("television")));
		this.getDonation().setElectronics(valid8r.cleanData(req.getParameter("electronics")));
		this.getDonation().setWasher(valid8r.cleanData(req.getParameter("washer")));
		this.getDonation().setDryer(valid8r.cleanData(req.getParameter("dryer")));
		this.getDonation().setExerciseEquipment(valid8r.cleanData(req.getParameter("exerciseEquipment")));
		this.getDonation().setSpecialNotes(valid8r.cleanData(req.getParameter("specialNotes")));
	} 

	public static Donor getDonor() {
		return donor;
	}

	public static void setDonor(Donor donor) {
		DispatchServlet.donor = donor;
	}

	public static Address getAddress() {
		return address;
	}

	public static void setAddress(Address address) {
		DispatchServlet.address = address;
	}

	public static Donation getDonation() {
		return donation;
	}

	public static void setDonation(Donation donation) {
		DispatchServlet.donation = donation;
	}

	public static String getDispatchDate() {
		return dispatchDate;
	}

	public static void setDispatchDate(String dispatchDate) {
		DispatchServlet.dispatchDate = dispatchDate;
	}

	public static String getLimit() {
		return limit;
	}

	public static void setLimit(String limit) {
		DispatchServlet.limit = limit;
	}

	public static SystemUser getSystemUser() {
		return systemUser;
	}

	public static void setSystemUser(SystemUser systemUser) {
		DispatchServlet.systemUser = systemUser;
	}

}
