package org.faithfarm.service.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.faithfarm.domain.Address;
import org.faithfarm.domain.Donation;
import org.faithfarm.domain.Donor;
import org.faithfarm.domain.SystemUser;
import org.faithfarm.util.Validator;

//import com.mysql.jdbc.PreparedStatement;

public class DispatchDao {

	private Validator valid8r = new Validator();
	
	private Connection getConnection() throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver");

		Connection	Conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/ffarm_dev", "root", "admin");
		 

		return Conn;
	}
	
    public int searchTickets(String lastname, String firstname, String confirmation, String dispatchDate, HttpSession session ) {
    	int retCode=1;
    	ArrayList results = new ArrayList();
		
    	try {
 
		    Connection Conn=this.getConnection();
			
		    // Do something with the Connection
			Statement Stmt = Conn.createStatement();
			StringBuffer s = new StringBuffer("SELECT * FROM FFARM_DEV.DONATION ");
			s.append("INNER JOIN FFARM_DEV.DONOR ON DONOR.DONOR_ID=DONATION.DONOR_ID  ");
			s.append("INNER JOIN FFARM_DEV.ADDRESS ON DONOR.DONOR_ID=ADDRESS.DONOR_ID WHERE ");
			if (lastname.length()>0) 
				s.append("DONOR.LASTNAME='"+lastname+"' AND ");
			if (firstname.length()>0) 
				s.append("DONOR.FIRSTNAME='"+firstname+"' AND ");
			if (confirmation.length()>0) 
				s.append("DONATION.CONFIRMATION_NUMBER='"+confirmation+"' AND ");
			if (dispatchDate.length()>0) 
				s.append("DONATION.DISPATCH_DATE='"+dispatchDate+"' AND ");
			s.append ("1=1");
			
			ResultSet RS = Stmt.executeQuery(s.toString());
			while (RS.next()) {
				Donation d = new Donation();
				d.setDonationId(RS.getLong(1));
				d.setDonorId(RS.getLong(2));
				d.setConfirmation(RS.getString(3));
				d.setDispatchDate(RS.getString(4));
				d.setStatus(RS.getString(5));
				d.setSpecialFlag(RS.getString(6));
				d.setCallRequirements(RS.getString(7));
				d.setAc(RS.getString(8));
				d.setBedding(RS.getString(9));
				d.setBeddingQtyType(RS.getString(10));
				d.setBooks(RS.getString(11));
				d.setBooksQtyType(RS.getString(12));
				d.setClothing(RS.getString(13));
				d.setClothingQtyType(RS.getString(14));
				d.setComputer(RS.getString(15));
				d.setDesk(RS.getString(16));
				d.setChest(RS.getString(17));
				d.setArmoire(RS.getString(18));
				d.setDresser(RS.getString(19));
				d.setMirror(RS.getString(20));
				d.setNightstand(RS.getString(21));
				d.setHeadboard(RS.getString(22));
				d.setFootboard(RS.getString(23));
				d.setRails(RS.getString(24));
				d.setLamp(RS.getString(25));
				d.setLawnFurniture(RS.getString(26));
				d.setMattress(RS.getString(27));
				d.setMattressQtyType(RS.getString(28));
				d.setMiscHouseholdItems(RS.getString(29));
				d.setRefridgerator(RS.getString(30));
				d.setStove(RS.getString(31));
				d.setRecliner(RS.getString(32));
				d.setSofa(RS.getString(33));
				d.setLoveseat(RS.getString(34));
				d.setWallUnit(RS.getString(35));
				d.setTable(RS.getString(36));
				d.setChair(RS.getString(37));
				d.setTelevision(RS.getString(38));
				d.setTelevisionSize(RS.getString(39));
				d.setElectronics(RS.getString(40));
				d.setWasher(RS.getString(41));
				d.setDryer(RS.getString(42));
				d.setExerciseEquipment(RS.getString(43));
				d.setSpecialNotes(RS.getString(44));
				d.setCreationDate(RS.getString(45));
				d.setCreatedBy(RS.getString(46));
				d.setLastUpdatedDate(RS.getString(47));
				d.setUpdatedBy(RS.getString(48));
				d.setFarmBase(RS.getString(49));
				
				Donor donor = new Donor();
				donor.setDonorId(RS.getLong(50));
				donor.setLastname(RS.getString(51));
				donor.setFirstname(RS.getString(52));
				donor.setSuffix(RS.getString(53));
				donor.setContactPhone(RS.getString(54));
				donor.setEmailAddress(RS.getString(55));
				donor.setCreationDate(RS.getString(56));
				donor.setLastUpdatedDate(RS.getString(57));
				donor.setCreatedBy(RS.getString(58));
				donor.setUdpatedBy(RS.getString(59));
				
				Address addy = new Address();
				addy.setAddressId(RS.getLong(60));
				addy.setDonorId(RS.getLong(61));
				addy.setLine1(RS.getString(62));
				addy.setLine2(RS.getString(63));
				addy.setCity(RS.getString(64));
				addy.setState(RS.getString(65));
				addy.setZipcode(RS.getString(66));
				addy.setMajorIntersection(RS.getString(67));
				addy.setSubdivision(RS.getString(68));
				addy.setStreetSuffix(RS.getString(69));
				addy.setStructureType(RS.getString(70));
				addy.setUnit(RS.getString(71));
				addy.setBuilding(RS.getString(72));
				addy.setFloor(RS.getString(73));
				addy.setElevatorFlag(RS.getString(74));
				addy.setGateFlag(RS.getString(75));
				addy.setGateInstructions(RS.getString(76));
				addy.setCreatedBy(RS.getString(77));
				addy.setLastUpdatedBy(RS.getString(78));
				
				d.setDonor(donor);
				d.setAddress(addy);
				
				results.add(d);
			}
			session.setAttribute("RESULTS_"+session.getId(), results);
			RS.close();
			Stmt.close();
			Conn.close();
		} catch (SQLException E) { retCode=0;
			session.setAttribute("SYSTEM_ERROR",E.getMessage());
		} catch (ClassNotFoundException e) { retCode=0;
			session.setAttribute("SYSTEM_ERROR",e.getMessage());
			e.printStackTrace();
		}
    	
    	return retCode;
    }
    public boolean deleteSystemUser(int userId, HttpSession session) {
     	
    	boolean retCode=true;
    	
    	try {
 
		    Connection Conn=this.getConnection();
			
		    // Do something with the Connection
			Statement Stmt = Conn.createStatement();
			String query="DELETE FROM SYSTEM_USER WHERE USER_ID="+userId;
			retCode=Stmt.execute(query);
			Stmt.close();
			Conn.close();
    	} catch (SQLException E) { 
		session.setAttribute("SYSTEM_ERROR",E.getMessage());
		return false;
	} catch (ClassNotFoundException e) { retCode=false;
		session.setAttribute("SYSTEM_ERROR",e.getMessage());
		return false;
	}
    	return true;
    }
    public int searchSystemUsers(String farm, HttpSession session ) {
    	int retCode=1;
    	ArrayList results = new ArrayList();
		
    	try {
 
		    Connection Conn=this.getConnection();
			
		    // Do something with the Connection
			Statement Stmt = Conn.createStatement();
			StringBuffer s = new StringBuffer("SELECT USER_ID, USERNAME, USER_ROLE, LOGIN_COUNT, FARM_BASE FROM FFARM_DEV.SYSTEM_USER ");
			s.append("WHERE FARM_BASE='"+farm+"'  ");
			ResultSet RS = Stmt.executeQuery(s.toString());
			while (RS.next()) {
				SystemUser d = new SystemUser();				
				d.setUserId(RS.getInt(1));
				d.setUsername(RS.getString(2));
				d.setUserRole(RS.getString(3));
				d.setLoginCount(RS.getInt(4));
				d.setFarmBase(RS.getString(5));
				results.add(d);
			}
			session.setAttribute("RESULTS_"+session.getId(), results);
			RS.close();
			Stmt.close();
			Conn.close();
		} catch (SQLException E) { retCode=0;
			session.setAttribute("SYSTEM_ERROR",E.getMessage());
		} catch (ClassNotFoundException e) { retCode=0;
			session.setAttribute("SYSTEM_ERROR",e.getMessage());
			e.printStackTrace();
		}
    	
    	return retCode;
    }
	public boolean secureLogin(String username, String password,
			HttpSession session) {

		try {

		    Connection Conn=this.getConnection();
			
		    // Do something with the Connection
			Statement Stmt = Conn.createStatement();

			ResultSet RS = Stmt.executeQuery("SELECT * from SYSTEM_USER");

			SystemUser user = new SystemUser();

			while (RS.next()) {
				String uid = RS.getString(2);
				String pwd = RS.getString(3);

				user.setUserId(Integer.valueOf(RS.getString(1)));
				user.setUsername(RS.getString(2));
				user.setPassword(RS.getString(3));
				user.setCreationDate(RS.getString(4));
				user.setLastUpdatedDate(RS.getString(5));
				user.setUserRole(RS.getString(6));
				user.setFarmBase(RS.getString(7));
				user.setLoginCount(RS.getInt(8));
				
				
				if (username.trim().length() == 0
						|| username.equals("username")) {
					session.setAttribute("ERROR_" + session.getId(),
							"You must enter a username.");
					return false;
				}
				if (password.trim().length() == 0
						|| password.equals("password")) {
					session.setAttribute("ERROR_" + session.getId(),
							"You must enter a password.");
					return false;
				}

				if (username.equals(uid)) {

					if (password.equals(pwd)) {
						session.setAttribute("USER_" + session.getId(), user);
						this.updateLoginCount(user.getUserId(),session);
						
						return true;
					} else {
						session.setAttribute("ERROR_" + session.getId(),
								"The password entered is incorrect.");
						return false;
					}

				}
			}

			// Clean up after ourselves
			RS.close();
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("SYSTEM_ERROR" ,E.getMessage());
		} catch (ClassNotFoundException e) {
			session.setAttribute("SYSTEM_ERROR" ,e.getMessage());
			e.printStackTrace();
		}

		session.setAttribute("ERROR_" + session.getId(),
				"The username or password entered is not valid.");

		return false;
	}
	
	public int updateLoginCount (Integer id, HttpSession session) {
		
		int retCode=0;
		// Do something with the Connection
		try {
			Connection Conn=this.getConnection();
			Statement Stmt = Conn.createStatement();

		StringBuffer query = new StringBuffer();
		query.append("UPDATE FFARM_DEV.SYSTEM_USER SET LOGIN_COUNT=LOGIN_COUNT+1 WHERE USER_ID="+id+";");
		retCode = Stmt.executeUpdate(query.toString());

		Stmt.close();
		Conn.close();
	} catch (SQLException E) {
		session.setAttribute("ERROR_" + session.getId(),E.getMessage());
	} catch (ClassNotFoundException e) {
		session.setAttribute("ERROR_" + session.getId(),e.getMessage());
		e.printStackTrace();
	}
		
		return retCode;
	}
	
public int updatePassword (Integer id, String password, String question, String answer, HttpSession session) {
		
		int retCode=0;
		// Do something with the Connection
		try {
			Connection Conn=this.getConnection();
			Statement Stmt = Conn.createStatement();

		StringBuffer query = new StringBuffer();
		query.append("UPDATE FFARM_DEV.SYSTEM_USER SET PASSWORD='"+password+"', QUESTION='"+question.replace("'", "''")+"', ANSWER='"+answer+"' WHERE USER_ID="+id+";");
		retCode = Stmt.executeUpdate(query.toString());

		Stmt.close();
		Conn.close();
	} catch (SQLException E) {
		session.setAttribute("ERROR_" + session.getId(),E.getMessage());
	} catch (ClassNotFoundException e) {
		session.setAttribute("ERROR_" + session.getId(),e.getMessage());
		e.printStackTrace();
	}
		
		return retCode;
	}

	public int insertDonation(Donor donor, Address addy, Donation d, HttpSession session) {

		int retCode = 0;
		long confirmation = 0;
		
		try {

			if (donor.getDonorId() == null) {
				Long rc = this.insertDonor(donor, session);
				addy.setDonorId(rc);
				
				if (rc.intValue() == 0)
					return rc.intValue();
				else {
					d.setDonorId(rc);
					confirmation=rc;
					rc = this.insertAddress(addy, session);
					if (rc.intValue() == 0)
						return rc.intValue();
				}
			}
			Connection Conn=this.getConnection();
				
			// Do something with the Connection
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO FFARM_DEV.DONATION (");
			query.append(" DONOR_ID, DISPATCH_DATE, CONFIRMATION_NUMBER, STATUS, SPECIAL_FLAG, CALL_REQUIREMENTS, AC, BEDDING, BEDDING_QTY_TYPE, BOOKS, BOOKS_QTY_TYPE, ");
			query.append(" CLOTHING, CLOTHING_QTY_TYPE, COMPUTER, DESK, CHEST, ARMOIRE, DRESSER, MIRROR, NIGHTSTAND, HEADBOARD, FOOTBOARD, RAILS, ");
			query.append(" LAMP, LAWN_FURNITURE, MATTRESS, MATTRESS_QTY_SIZE, MISC_HOUSEHOLD_ITEMS, REFRIDGERATOR, STOVE, RECLINER, SOFA, LOVESEAT, ");
			query.append(" WALL_UNIT, TABLES, CHAIR, TELEVISION, TELEVISION_SIZE, ELECTRONICS, WASHER, DRYER, EXERCISE_EQUIPMENT, SPECIAL_NOTES, ");
			query.append(" CREATION_DATE, CREATED_BY, FARM_BASE) VALUES (");

			query.append(d.getDonorId() + ",");
			query.append("'" + d.getDispatchDate() + "',");
			query.append("'" + confirmation +"',");
			query.append("'" + d.getStatus() + "',");
			if (d.getSpecialFlag()==null||"".equals(d.getSpecialFlag())) {
				query.append("'No',");
			} else
				query.append("'Yes',");
			query.append("'" + d.getCallRequirements() + "',");
			query.append("'" + d.getAc() + "',");
			query.append("'" + d.getBedding() + "',");
			query.append("'" + d.getBeddingQtyType() + "',");
			query.append("'" + d.getBooks() + "',");
			query.append("'" + d.getBooksQtyType() + "',");
			query.append("'" + d.getClothing() + "',");
			query.append("'" + d.getClothingQtyType() + "',");
			query.append("'" + d.getComputer() + "',");
			query.append("'" + d.getDesk() + "',");
			query.append("'" + d.getChest() + "',");
			query.append("'" + d.getArmoire() + "',");
			query.append("'" + d.getDresser() + "',");
			query.append("'" + d.getMirror() + "',");
			query.append("'" + d.getNightstand() + "',");
			query.append("'" + d.getHeadboard() + "',");
			query.append("'" + d.getFootboard() + "',");
			query.append("'" + d.getRails() + "',");
			query.append("'" + d.getLamp() + "',");
			query.append("'" + d.getLawnFurniture() + "',");
			query.append("'" + d.getMattress() + "',");
			query.append("'" + d.getMattressQtyType() + "',");
			query.append("'" + d.getMiscHouseholdItems() + "',");
			query.append("'" + d.getRefridgerator() + "',");
			query.append("'" + d.getStove() + "',");
			query.append("'" + d.getRecliner() + "',");
			query.append("'" + d.getSofa() + "',");
			query.append("'" + d.getLoveseat() + "',");
			query.append("'" + d.getWallUnit() + "',");
			query.append("'" + d.getTable() + "',");
			query.append("'" + d.getChair() + "',");
			query.append("'" + d.getTelevision() + "',");
			query.append("'" + d.getTelevisionSize() + "',");
			query.append("'" + d.getElectronics() + "',");
			query.append("'" + d.getWasher() + "',");
			query.append("'" + d.getDryer() + "',");
			query.append("'" + d.getExerciseEquipment() + "',");
			query.append("'" + d.getSpecialNotes() + "',");
			query.append("'" + valid8r.getEpoch() + "',");
			query.append("'" + d.getCreatedBy() + "', ");
			query.append("'" + d.getFarmBase() + "' );");
			
			retCode = Stmt.executeUpdate(query.toString());

			// Clean up after ourselves
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
		}

		return retCode;

	}

	public Long insertDonor(Donor d, HttpSession session) {

		Long key = new Long("0");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection Conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/ffarm_dev", "root", "admin");

			// Do something with the Connection

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO FFARM_DEV.DONOR (");
			query.append(" LASTNAME, FIRSTNAME, SUFFIX, CONTACT_PHONE, EMAIL_ADDRESS, CREATION_DATE, CREATED_BY ) VALUES (");
			query.append("'" + d.getLastname() + "',");
			query.append("'" + d.getFirstname() + "',");
			query.append("'" + d.getSuffix() + "',");
			query.append("'" + d.getContactPhone() + "',");
			query.append("'" + d.getEmailAddress() + "',");
			query.append("'" + valid8r.getEpoch() + "',");
			query.append("'" + d.getCreatedBy() + "');");
			PreparedStatement Stmt = null;
			Stmt = Conn.prepareStatement(query.toString(),
					Stmt.RETURN_GENERATED_KEYS);
			Stmt.executeUpdate(query.toString());

			ResultSet generatedKeys = Stmt.getGeneratedKeys();

			if (generatedKeys.next())
				key = generatedKeys.getLong(1);

			// Clean up after ourselves
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}
		return key;
	}
	
	public Long insertAddress(Address d, HttpSession session) {

		Long key = new Long("0");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection Conn = this.getConnection();
			// Do something with the Connection

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO FFARM_DEV.ADDRESS (");
			query.append(" DONOR_ID,LINE1, LINE2, CITY, STATE, ZIPCODE, MAJOR_INTERSECTION, SUBDIVISION, STREET_SUFFIX, STRUCTURE_TYPE, ");
			query.append("UNIT, BUILDING, FLOOR, ELEVATOR_FLAG, GATED_FLAG, GATE_INSTRUCTIONS, CREATED_BY  ) VALUES (");
			query.append(d.getDonorId() + ",");
			query.append("'" + d.getLine1() + "',");
			query.append("'" + d.getLine2() + "',");
			query.append("'" + d.getCity() + "',");
			query.append("'" + d.getState() + "',");
			query.append("'" + d.getZipcode() + "',");
			query.append("'" + d.getMajorIntersection() + "',");
			query.append("'" + d.getSubdivision() + "',");
			query.append("'" + d.getStreetSuffix() + "',");
			query.append("'" + d.getStructureType() + "',");
			query.append("'" + d.getUnit() + "',");
			query.append("'" + d.getBuilding() + "',");
			query.append("'" + d.getFloor() + "',");
			query.append("'" + d.getElevatorFlag() + "',");
			query.append("'" + d.getGateFlag() + "',");
			query.append("'" + d.getGateInstructions() + "',");
			query.append("'" + d.getCreatedBy() + "');");
			
			PreparedStatement Stmt = null;
			Stmt = Conn.prepareStatement(query.toString(),
					Stmt.RETURN_GENERATED_KEYS);
			Stmt.executeUpdate(query.toString());

			ResultSet generatedKeys = Stmt.getGeneratedKeys();

			if (generatedKeys.next())
				key = generatedKeys.getLong(1);

			// Clean up after ourselves
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}
		return key;
	}
	
	
	public Long insertSystemUser(SystemUser d, HttpSession session) {

		Long key = new Long("0");

		try {
			
			Connection Conn=this.getConnection();
			
			// Do something with the Connection

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO FFARM_DEV.SYSTEM_USER (");
			query.append(" USERNAME, PASSWORD, CREATION_DATE, CREATED_BY, USER_ROLE, FARM_BASE ) VALUES (");
			query.append("'" + d.getUsername() + "',");
			query.append("'" + d.getPassword() + "',");
			query.append("'" + valid8r.getEpoch() + "',");
			query.append("'" + d.getCreatedBy() + "',");
			query.append("'" + d.getUserRole() + "',");
			query.append("'" + d.getFarmBase() + "');");
			
			PreparedStatement Stmt = null;
			Stmt = Conn.prepareStatement(query.toString(),
					Stmt.RETURN_GENERATED_KEYS);
			Stmt.executeUpdate(query.toString());

			ResultSet generatedKeys = Stmt.getGeneratedKeys();

			if (generatedKeys.next())
				key = generatedKeys.getLong(1);

			// Clean up after ourselves
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}
		return key;
	}
	
	public int insertCallLog(String type, String source, String user, String farm, HttpSession session) {

		int retCode = 0;

		try {

			Connection Conn=this.getConnection();
				
			// Do something with the Connection
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO FFARM_DEV.CALL_LOG (");
			query.append(" CALL_TYPE, SOURCE, CALL_DATE, CALL_AGENT, FARM_BASE) VALUES (");

			query.append("'" + type + "',");
			query.append("'" + source + "',");
			query.append("'" + valid8r.getEpoch() + "',");
			query.append("'" + farm + "',");
			query.append("'" + user + "' );");

			retCode = Stmt.executeUpdate(query.toString());

			// Clean up after ourselves
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
		}

		return retCode;

	}
	
	public int insertDailyLimit(String dispatchDate, int limit, String farm, String user, HttpSession session) {

		int retCode = 0;

		try {

			Connection Conn=this.getConnection();
				
			// Do something with the Connection
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO FFARM_DEV.DAILY_LIMIT (");
			query.append(" DISPATCH_DATE, DAILY_LIMIT, FARM_BASE, UPDATED_BY, UPDATED_DATE) VALUES (");
			query.append("'" + dispatchDate + "',");
			query.append(limit + ",");
			query.append("'" + farm + "',");
			query.append("'" + valid8r.getEpoch() + "',");
			query.append("'" + user + "' );");
			retCode = Stmt.executeUpdate(query.toString());

			// Clean up after ourselves
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
		}

		return retCode;

	}

	public int updateDailyLimit(String dispatchDate, int limit, String farm, String user, HttpSession session) {

		int retCode = 0;

		try {

			Connection Conn=this.getConnection();
				
			// Do something with the Connection
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE FFARM_DEV.DAILY_LIMIT ");
			query.append(" SET DAILY_LIMIT="+limit+", UPDATED_BY='"+user+"', UPDATED_DATE='"+valid8r.getEpoch()+"' ");
			query.append(" WHERE DISPATCH_DATE='"+dispatchDate+"' AND FARM_BASE='"+farm+"'");
			retCode = Stmt.executeUpdate(query.toString());

			// Clean up after ourselves
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
		}

		return retCode;

	}


}