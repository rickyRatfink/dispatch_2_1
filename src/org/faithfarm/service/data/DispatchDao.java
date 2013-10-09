package org.faithfarm.service.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.faithfarm.dispatch.DispatchServlet;
import org.faithfarm.domain.Address;
import org.faithfarm.domain.Donation;
import org.faithfarm.domain.Donor;
import org.faithfarm.domain.SystemUser;
import org.faithfarm.util.Validator;

//import com.mysql.jdbc.PreparedStatement;

public class DispatchDao {

	private final static Logger LOGGER = Logger.getLogger(DispatchDao.class.getName());
	
	private Validator valid8r = new Validator();
	private String SERVER = "";
	private String uid = "";
	private String pwd = "";
	private String database = "";

	// private String this.getSERVER() = "ffarm_staging";
	// private String pwd="j35u59538";

	private Connection getConnection() throws SQLException,
			ClassNotFoundException {

		Properties prop = new Properties();
		LOGGER.setLevel(Level.INFO);
		
		try {
			// load a properties file
			// prop.load(new
			// FileInputStream("c:\\development\\workspace\\dispatch_2_1\\src\\properties\\config.properties"));
			prop.load(new FileInputStream("c:\\properties\\config.properties"));
			this.setUid(prop.getProperty("dbuser"));
			this.setPwd(prop.getProperty("dbpassword"));
			this.setDatabase(prop.getProperty("database"));
			this.setSERVER(prop.getProperty("dburl"));

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		Class.forName("com.mysql.jdbc.Driver");
		// System.out.println ("--jdbc:mysql://"+this.getSERVER()+"/" +
		// database+","+ uid+","+ pwd);
		Connection Conn = DriverManager.getConnection(
				"jdbc:mysql://" + this.getSERVER() + "/" + database, uid, pwd);

		return Conn;
	}

	public int getDailyLimit(String sDate, HttpSession session) {

		int limit = 0;
		int retCode = 1;
		ArrayList results = new ArrayList();
		Donor donor = new Donor();
		SystemUser user = (SystemUser)session.getAttribute("USER_"+session.getId());
		
		try {

			Connection Conn = this.getConnection();
			Statement Stmt = Conn.createStatement();
			StringBuffer s = new StringBuffer("SELECT DAILY_LIMIT FROM "
					+ this.getDatabase() + ".DAILY_LIMIT ");
			s.append("WHERE DISPATCH_DATE='" + sDate + "' AND farm_base='"+user.getFarmBase()+"' ");
			ResultSet RS = Stmt.executeQuery(s.toString());

			if (RS == null)
				limit = 0;
			else if (RS.next())
				limit = RS.getInt(1);

			RS.close();
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			retCode = 0;
			limit = 0;
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			retCode = 0;
			limit = 0;
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}

		return limit;
	}

	public int getDailyDispatchCount(String sDate, HttpSession session) {

		int count = 0;
		int retCode = 1;
		ArrayList results = new ArrayList();
		Donor donor = new Donor();
		SystemUser user = (SystemUser)session.getAttribute("USER_"+session.getId());
		
		try {

			Connection Conn = this.getConnection();
			Statement Stmt = Conn.createStatement();
			StringBuffer s = new StringBuffer("SELECT COUNT(DONATION_ID) FROM "
					+ this.getDatabase() + ".DONATION ");
			s.append("WHERE DISPATCH_DATE='" + sDate + "' and farm_base='"+user.getFarmBase()+"' ");
			ResultSet RS = Stmt.executeQuery(s.toString());
			if (RS == null)
				count = 0;
			else if (RS.next())
				count = RS.getInt(1);

			RS.close();
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			retCode = 0;
			count = 0;
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			retCode = 0;
			count = 0;
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}

		return count;
	}

	public Donor getDonor(Long id, HttpSession session) {
		int retCode = 1;
		ArrayList results = new ArrayList();
		Donor donor = new Donor();

		try {

			Connection Conn = this.getConnection();

			// Do something with the Connection
			Statement Stmt = Conn.createStatement();
			StringBuffer s = new StringBuffer("SELECT * FROM "
					+ this.getDatabase() + ".DONOR ");
			s.append("WHERE DONOR_ID=" + id);

			ResultSet RS = Stmt.executeQuery(s.toString());
			while (RS.next()) {

				donor.setDonorId(RS.getLong(1));
				donor.setLastname(RS.getString(2));
				donor.setFirstname(RS.getString(3));
				donor.setSuffix(RS.getString(4));
				donor.setContactPhone(RS.getString(5));
				donor.setEmailAddress(RS.getString(6));
				donor.setCreationDate(RS.getString(7));
				donor.setLastUpdatedDate(RS.getString(8));
				donor.setCreatedBy(RS.getString(9));
				donor.setUdpatedBy(RS.getString(10));
			}
			RS.close();
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}

		return donor;
	}

	public int getDonors(String firstname, String lastname, HttpSession session) {
		int retCode = 1;
		ArrayList results1 = new ArrayList();
		ArrayList results2 = new ArrayList();

		try {

			Connection Conn = this.getConnection();

			// Do something with the Connection
			Statement Stmt = Conn.createStatement();
			StringBuffer s = new StringBuffer("SELECT * FROM "
					+ this.getDatabase() + ".DONOR INNER JOIN "
					+ this.getDatabase()
					+ ".ADDRESS ON DONOR.DONOR_ID=ADDRESS.DONOR_ID ");
			s.append("WHERE FIRSTNAME LIKE '%" + firstname
					+ "%' and lastname like '%" + lastname + "%' ");

			ResultSet RS = Stmt.executeQuery(s.toString());
			while (RS.next()) {
				Donor donor = new Donor();
				Address addy = new Address();

				donor.setDonorId(RS.getLong(1));
				donor.setLastname(RS.getString(2));
				donor.setFirstname(RS.getString(3));
				donor.setSuffix(RS.getString(4));
				donor.setContactPhone(RS.getString(5));
				donor.setEmailAddress(RS.getString(6));
				donor.setCreationDate(RS.getString(7));
				donor.setLastUpdatedDate(RS.getString(8));
				donor.setCreatedBy(RS.getString(9));
				donor.setUdpatedBy(RS.getString(10));

				addy.setAddressId(RS.getLong(11));
				addy.setDonorId(RS.getLong(12));
				addy.setLine1(RS.getString(13));
				addy.setLine2(RS.getString(14));
				addy.setCity(RS.getString(15));
				addy.setState(RS.getString(16));
				addy.setZipcode(RS.getString(17));
				addy.setMajorIntersection(RS.getString(18));
				addy.setSubdivision(RS.getString(19));
				addy.setStreetSuffix(RS.getString(20));
				addy.setStructureType(RS.getString(21));
				addy.setUnit(RS.getString(22));
				addy.setBuilding(RS.getString(23));
				addy.setFloor(RS.getString(24));
				addy.setElevatorFlag(RS.getString(25));
				addy.setGateFlag(RS.getString(26));
				addy.setGateInstructions(RS.getString(27));
				addy.setCreatedBy(RS.getString(28));
				addy.setLastUpdatedBy(RS.getString(29));
				addy.setLastUpdatedDate(RS.getString(30));

				results1.add(donor);
				results2.add(addy);
			}
			RS.close();
			Stmt.close();
			Conn.close();
			session.setAttribute("RESULTS1_" + session.getId(), results1);
			session.setAttribute("RESULTS2_" + session.getId(), results2);

		} catch (SQLException E) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}

		return retCode;
	}

	public int updateDonor(Donor donor, HttpSession session) {
		int retCode = 0;

		// Do something with the Connection
		try {
			Connection Conn = this.getConnection();
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE " + this.getDatabase() + ".DONOR SET ");
			query.append(" LASTNAME='" + donor.getLastname() + "',");
			query.append(" FIRSTNAME='" + donor.getFirstname() + "',");
			query.append(" SUFFIX='" + donor.getSuffix() + "',");
			query.append(" CONTACT_PHONE='" + donor.getContactPhone() + "',");
			query.append(" EMAIL_ADDRESS='" + donor.getEmailAddress() + "',");
			query.append(" LAST_UPDATED_DATE='" + donor.getLastUpdatedDate()
					+ "',");
			query.append(" UPDATED_BY='" + donor.getUdpatedBy() + "'");
			query.append(" WHERE DONOR_ID=" + donor.getDonorId());
			retCode = Stmt.executeUpdate(query.toString());
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("ERROR_" + session.getId(), E.getMessage());
		} catch (ClassNotFoundException e) {
			session.setAttribute("ERROR_" + session.getId(), e.getMessage());
			e.printStackTrace();
		}
		return retCode;
	}

	public int updateAddress(Address addy, HttpSession session) {
		int retCode = 0;

		// Do something with the Connection
		try {
			Connection Conn = this.getConnection();
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE " + this.getDatabase() + ".ADDRESS SET ");
			query.append(" LINE1='" + addy.getLine1() + "',");
			query.append(" LINE2='" + addy.getLine2() + "',");
			query.append(" CITY='" + addy.getCity() + "',");
			query.append(" STATE='" + addy.getState() + "',");
			query.append(" ZIPCODE='" + addy.getZipcode() + "',");
			query.append(" MAJOR_INTERSECTION='" + addy.getMajorIntersection()
					+ "',");
			query.append(" SUBDIVISION='" + addy.getSubdivision() + "',");
			query.append(" STREET_SUFFIX='" + addy.getStreetSuffix() + "',");
			query.append(" STRUCTURE_TYPE='" + addy.getStructureType() + "',");
			query.append(" UNIT='" + addy.getUnit() + "',");
			query.append(" BUILDING='" + addy.getBuilding() + "',");
			query.append(" FLOOR='" + addy.getFloor() + "',");
			query.append(" ELEVATOR_FLAG='" + addy.getElevatorFlag() + "',");
			query.append(" GATED_FLAG='" + addy.getGateFlag() + "',");
			query.append(" GATE_INSTRUCTIONS='" + addy.getGateInstructions()
					+ "',");
			query.append(" LAST_UPDATED_DATE='" + addy.getLastUpdatedDate()
					+ "',");
			query.append(" LAST_UPDATED_BY='" + addy.getLastUpdatedBy() + "'");
			query.append(" WHERE ADDRESS_ID=" + addy.getAddressId());
			retCode = Stmt.executeUpdate(query.toString());
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("ERROR_" + session.getId(), E.getMessage());
		} catch (ClassNotFoundException e) {
			session.setAttribute("ERROR_" + session.getId(), e.getMessage());
			e.printStackTrace();
		}
		return retCode;
	}

	public int updateDonation(Donation d, HttpSession session) {
		int retCode = 0;

		// Do something with the Connection
		try {
			Connection Conn = this.getConnection();
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();

			query.append("UPDATE " + this.getDatabase() + ".DONATION SET ");
			query.append(" DISPATCH_DATE='" + d.getDispatchDate() + "', ");
			query.append(" LOCATION='" + d.getLocation() + "', ");
			query.append(" STATUS='" + d.getStatus() + "', ");
			query.append(" SPECIAL_FLAG='" + d.getSpecialFlag() + "', ");
			query.append(" CALL_REQUIREMENTS='" + d.getCallRequirements()
					+ "', ");
			query.append(" AC='" + d.getAc() + "', ");
			query.append(" BEDDING='" + d.getBedding() + "', ");
			query.append(" BEDDING_QTY_TYPE='" + d.getBeddingQtyType() + "', ");
			query.append(" BOOKS='" + d.getBooks() + "', ");
			query.append(" BOOKS_QTY_TYPE='" + d.getBooksQtyType() + "', ");
			query.append(" CLOTHING='" + d.getClothing() + "', ");
			query.append(" CLOTHING_QTY_TYPE='" + d.getClothingQtyType()
					+ "', ");
			query.append(" COMPUTER='" + d.getComputer() + "', ");
			query.append(" DESK='" + d.getDesk() + "', ");
			query.append(" CHEST='" + d.getChest() + "', ");
			query.append(" ARMOIRE='" + d.getArmoire() + "', ");
			query.append(" DRESSER='" + d.getDresser() + "', ");
			query.append(" MIRROR='" + d.getMirror() + "', ");
			query.append(" NIGHTSTAND='" + d.getNightstand() + "', ");
			query.append(" HEADBOARD='" + d.getHeadboard() + "', ");
			query.append(" FOOTBOARD='" + d.getFootboard() + "', ");
			query.append(" RAILS='" + d.getRails() + "', ");
			query.append(" LAMP='" + d.getLamp() + "', ");
			query.append(" LAWN_FURNITURE='" + d.getLawnFurniture() + "', ");
			query.append(" MATTRESS='" + d.getMattress() + "', ");
			query.append(" MATTRESS_QTY_SIZE='" + d.getMattressQtyType()
					+ "', ");
			query.append(" MISC_HOUSEHOLD_ITEMS='" + d.getMiscHouseholdItems()
					+ "', ");
			query.append(" REFRIDGERATOR='" + d.getRefridgerator() + "', ");
			query.append(" STOVE='" + d.getStove() + "', ");
			query.append(" RECLINER='" + d.getRecliner() + "', ");
			query.append(" SOFA='" + d.getSofa() + "', ");
			query.append(" LOVESEAT='" + d.getLoveseat() + "', ");
			query.append(" WALL_UNIT='" + d.getWallUnit() + "', ");
			query.append(" TABLES='" + d.getTable() + "', ");
			query.append(" CHAIR='" + d.getChair() + "', ");
			query.append(" TABLE_TYPE='" + d.getTableType() + "', ");
			query.append(" CHAIR_TYPE='" + d.getChairType() + "', ");
			query.append(" TELEVISION='" + d.getTelevision() + "', ");
			query.append(" TELEVISION_SIZE='" + d.getTelevisionSize() + "', ");
			query.append(" ELECTRONICS='" + d.getElectronics() + "', ");
			query.append(" WASHER='" + d.getWasher() + "', ");
			query.append(" DRYER='" + d.getDryer() + "', ");
			query.append(" EXERCISE_EQUIPMENT='" + d.getExerciseEquipment()
					+ "', ");
			query.append(" BOOKCASE='" + d.getBookcase()
					+ "', ");
			query.append(" OTTOMAN='" + d.getOttoman()
					+ "', ");
			query.append(" SPECIAL_NOTES='" + d.getSpecialNotes() + "', ");
			query.append(" LAST_UPDATED_DATE='" + d.getLastUpdatedDate()
					+ "', ");
			query.append(" UPDATED_BY='" + d.getUpdatedBy()
					+ "' WHERE DONATION_ID=" + d.getDonationId());

			retCode = Stmt.executeUpdate(query.toString());
			

			
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("ERROR_" + session.getId(), E.getMessage());
		} catch (ClassNotFoundException e) {
			session.setAttribute("ERROR_" + session.getId(), e.getMessage());
			e.printStackTrace();
		}
		return retCode;
	}

	public Donation getDonation(Long id, HttpSession session) {
		int retCode = 1;
		ArrayList results = new ArrayList();
		Donation d = new Donation();

		try {

			Connection Conn = this.getConnection();
			String value = "";
			// Do something with the Connection
			Statement Stmt = Conn.createStatement();
			StringBuffer s = new StringBuffer("SELECT * FROM "
					+ this.getDatabase() + ".DONATION ");
			s.append("WHERE DONATION_ID=" + id);

			ResultSet RS = Stmt.executeQuery(s.toString());
			while (RS.next()) {
				d.setDonationId(RS.getLong(1));
				d.setDonorId(RS.getLong(2));
				d.setConfirmation(RS.getString(3));
				d.setDispatchDate(RS.getString(4));
				d.setStatus(RS.getString(5));
				d.setSpecialFlag(RS.getString(6));
				d.setCallRequirements(RS.getString(7));

				if (RS.getString(8) == null)
					value = "";
				else
					value = RS.getString(8);
				d.setAc(value);

				if (RS.getString(9) == null)
					value = "";
				else
					value = RS.getString(9);
				d.setBedding(value);

				if (RS.getString(10) == null)
					value = "";
				else
					value = RS.getString(10);
				d.setBeddingQtyType(value);

				if (RS.getString(11) == null)
					value = "";
				else
					value = RS.getString(11);
				d.setBooks(value);

				if (RS.getString(12) == null)
					value = "";
				else
					value = RS.getString(12);
				d.setBooksQtyType(value);

				if (RS.getString(13) == null)
					value = "";
				else
					value = RS.getString(13);
				d.setClothing(value);

				if (RS.getString(14) == null)
					value = "";
				else
					value = RS.getString(14);
				d.setClothingQtyType(value);

				if (RS.getString(15) == null)
					value = "";
				else
					value = RS.getString(15);
				d.setComputer(value);

				if (RS.getString(16) == null)
					value = "";
				else
					value = RS.getString(16);
				d.setDesk(value);

				if (RS.getString(17) == null)
					value = "";
				else
					value = RS.getString(17);
				d.setChest(value);

				if (RS.getString(18) == null)
					value = "";
				else
					value = RS.getString(18);
				d.setArmoire(value);

				if (RS.getString(19) == null)
					value = "";
				else
					value = RS.getString(19);
				d.setDresser(value);

				if (RS.getString(20) == null)
					value = "";
				else
					value = RS.getString(20);
				d.setMirror(value);

				if (RS.getString(21) == null)
					value = "";
				else
					value = RS.getString(21);
				d.setNightstand(value);

				if (RS.getString(22) == null)
					value = "";
				else
					value = RS.getString(22);
				d.setHeadboard(value);
				if (RS.getString(23) == null)
					value = "";
				else
					value = RS.getString(23);
				d.setFootboard(value);
				if (RS.getString(24) == null)
					value = "";
				else
					value = RS.getString(24);
				d.setRails(value);
				if (RS.getString(25) == null)
					value = "";
				else
					value = RS.getString(25);
				d.setLamp(value);
				if (RS.getString(26) == null)
					value = "";
				else
					value = RS.getString(26);
				d.setLawnFurniture(value);
				if (RS.getString(27) == null)
					value = "";
				else
					value = RS.getString(27);
				d.setMattress(value);
				if (RS.getString(28) == null)
					value = "";
				else
					value = RS.getString(28);

				d.setMattressQtyType(value);
				if (RS.getString(29) == null)
					value = "";
				else
					value = RS.getString(29);
				d.setMiscHouseholdItems(value);

				if (RS.getString(30) == null)
					value = "";
				else
					value = RS.getString(30);
				d.setRefridgerator(value);

				if (RS.getString(31) == null)
					value = "";
				else
					value = RS.getString(31);
				d.setStove(value);

				if (RS.getString(32) == null)
					value = "";
				else
					value = RS.getString(32);
				d.setRecliner(value);

				if (RS.getString(33) == null)
					value = "";
				else
					value = RS.getString(33);
				d.setSofa(value);

				if (RS.getString(34) == null)
					value = "";
				else
					value = RS.getString(34);
				d.setLoveseat(value);

				if (RS.getString(35) == null)
					value = "";
				else
					value = RS.getString(35);
				d.setWallUnit(value);

				if (RS.getString(36) == null)
					value = "";
				else
					value = RS.getString(36);
				d.setTable(value);

				if (RS.getString(37) == null)
					value = "";
				else
					value = RS.getString(37);
				d.setChair(value);

				if (RS.getString(38) == null)
					value = "";
				else
					value = RS.getString(38);
				d.setTelevision(value);

				if (RS.getString(39) == null)
					value = "";
				else
					value = RS.getString(39);
				d.setTelevisionSize(value);

				if (RS.getString(40) == null)
					value = "";
				else
					value = RS.getString(40);
				d.setElectronics(value);

				if (RS.getString(41) == null)
					value = "";
				else
					value = RS.getString(41);
				d.setWasher(value);

				if (RS.getString(42) == null)
					value = "";
				else
					value = RS.getString(42);
				d.setDryer(value);

				if (RS.getString(43) == null)
					value = "";
				else
					value = RS.getString(43);
				d.setExerciseEquipment(value);

				if (RS.getString(44) == null)
					value = "";
				else
					value = RS.getString(44);
				d.setSpecialNotes(value);

				if (RS.getString(45) == null)
					value = "";
				else
					value = RS.getString(45);
				d.setCreationDate(value);

				if (RS.getString(46) == null)
					value = "";
				else
					value = RS.getString(46);
				d.setCreatedBy(value);

				if (RS.getString(47) == null)
					value = "";
				else
					value = RS.getString(47);
				d.setLastUpdatedDate(value);

				if (RS.getString(48) == null)
					value = "";
				else
					value = RS.getString(48);
				d.setUpdatedBy(value);

				if (RS.getString(49) == null)
					value = "";
				else
					value = RS.getString(49);
				d.setFarmBase(value);
				
				if (RS.getString(50) == null)
					value = "";
				else
					value = RS.getString(50);
				d.setTableType(value);
				
				if (RS.getString(51) == null)
					value = "";
				else
					value = RS.getString(51);
				d.setChairType(value);
				
				if (RS.getString(52) == null)
					value = "";
				else
					value = RS.getString(52);
				d.setLocation(value);
				
				if (RS.getString(53) == null)
					value = "";
				else
					value = RS.getString(53);
				d.setBookcase(value);
				
				if (RS.getString(54) == null)
					value = "";
				else
					value = RS.getString(54);
				d.setOttoman(value);
			}
			RS.close();
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}

		return d;
	}

	public Address getAddress(Long id, HttpSession session) {
		int retCode = 1;
		ArrayList results = new ArrayList();
		Address addy = new Address();

		try {

			Connection Conn = this.getConnection();

			// Do something with the Connection
			Statement Stmt = Conn.createStatement();
			StringBuffer s = new StringBuffer("SELECT * FROM "
					+ this.getDatabase() + ".ADDRESS ");
			s.append("WHERE ADDRESS_ID=" + id);
			ResultSet RS = Stmt.executeQuery(s.toString());
			while (RS.next()) {
				addy.setAddressId(RS.getLong(1));
				addy.setDonorId(RS.getLong(2));
				addy.setLine1(RS.getString(3));
				addy.setLine2(RS.getString(4));
				addy.setCity(RS.getString(5));
				addy.setState(RS.getString(6));
				addy.setZipcode(RS.getString(7));
				addy.setMajorIntersection(RS.getString(8));
				addy.setSubdivision(RS.getString(9));
				addy.setStreetSuffix(RS.getString(10));
				addy.setStructureType(RS.getString(11));
				addy.setUnit(RS.getString(12));
				addy.setBuilding(RS.getString(13));
				addy.setFloor(RS.getString(14));
				addy.setElevatorFlag(RS.getString(15));
				addy.setGateFlag(RS.getString(16));
				addy.setGateInstructions(RS.getString(17));
				addy.setCreatedBy(RS.getString(18));
				addy.setLastUpdatedBy(RS.getString(19));
				addy.setLastUpdatedDate(RS.getString(20));
			}
			RS.close();
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}

		return addy;
	}

	public int searchTickets(String lastname, String firstname,
			String confirmation, String dispatchDate, String status, String special, HttpSession session) {
		int retCode = 1;
		ArrayList results = new ArrayList();
		SystemUser user = (SystemUser)session.getAttribute("USER_"+session.getId());
		try {

			Connection Conn = this.getConnection();

			// Do something with the Connection
			Statement Stmt = Conn.createStatement();
			StringBuffer s = new StringBuffer("SELECT * FROM "
					+ this.getDatabase() + ".DONATION ");
			s.append("INNER JOIN " + this.getDatabase()
					+ ".DONOR ON DONOR.DONOR_ID=DONATION.DONOR_ID  ");
			s.append("INNER JOIN " + this.getDatabase()
					+ ".ADDRESS ON DONOR.DONOR_ID=ADDRESS.DONOR_ID WHERE 1=1 ");
			if (lastname.length() > 0)
				s.append("AND DONOR.LASTNAME='" + lastname + "'  ");
			if (firstname.length() > 0)
				s.append("AND DONOR.FIRSTNAME='" + firstname + "'  ");
			if (confirmation.length() > 0)
				s.append("AND DONATION.DONATION_ID='" + confirmation + "'  ");
			if (dispatchDate.length() > 0)
				s.append("AND DONATION.DISPATCH_DATE='" + dispatchDate + "'  ");
			if (status.length() > 0)
				s.append("AND DONATION.STATUS='" + status + "'  ");
			if (special.length() > 0)
				s.append("AND DONATION.SPECIAL_FLAG='" + special + "'  ");
			s.append("AND DONATION.FARM_BASE='"+user.getFarmBase().toUpperCase()+"'  ");
			
			System.out.println (s);
			
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
				d.setChairType(RS.getString(51));
				d.setTableType(RS.getString(50));
				d.setLocation(RS.getString(52));
				d.setBookcase(RS.getString(53));
				d.setOttoman(RS.getString(54));
				
				Donor donor = new Donor();
				donor.setDonorId(RS.getLong(55));
				donor.setLastname(RS.getString(56));
				donor.setFirstname(RS.getString(57));
				donor.setSuffix(RS.getString(58));
				donor.setContactPhone(RS.getString(59));
				donor.setEmailAddress(RS.getString(60));
				donor.setCreationDate(RS.getString(61));
				donor.setLastUpdatedDate(RS.getString(62));
				donor.setCreatedBy(RS.getString(63));
				donor.setUdpatedBy(RS.getString(64));

				Address addy = new Address();
				addy.setAddressId(RS.getLong(65));
				addy.setDonorId(RS.getLong(66));
				addy.setLine1(RS.getString(67));
				addy.setLine2(RS.getString(68));
				addy.setCity(RS.getString(69));
				addy.setState(RS.getString(70));
				addy.setZipcode(RS.getString(71));
				addy.setMajorIntersection(RS.getString(72));
				addy.setSubdivision(RS.getString(73));
				addy.setStreetSuffix(RS.getString(74));
				addy.setStructureType(RS.getString(75));
				addy.setUnit(RS.getString(76));
				addy.setBuilding(RS.getString(77));
				addy.setFloor(RS.getString(78));
				addy.setElevatorFlag(RS.getString(79));
				addy.setGateFlag(RS.getString(80));
				addy.setGateInstructions(RS.getString(81));
				addy.setCreatedBy(RS.getString(82));
				addy.setLastUpdatedBy(RS.getString(83));

				d.setDonor(donor);
				d.setAddress(addy);

				results.add(d);
			}
			session.setAttribute("RESULTS_" + session.getId(), results);
			DispatchServlet.setDonations(results);
			RS.close();
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}

		return retCode;
	}

	public boolean deleteSystemUser(int userId, HttpSession session) {

		boolean retCode = true;

		try {

			Connection Conn = this.getConnection();

			// Do something with the Connection
			Statement Stmt = Conn.createStatement();
			String query = "DELETE FROM " + this.getDatabase()
					+ ".SYSTEM_USER WHERE USER_ID=" + userId;
			retCode = Stmt.execute(query);
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
			return false;
		} catch (ClassNotFoundException e) {
			retCode = false;
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			return false;
		}
		return true;
	}

	public int searchSystemUsers(String farm, HttpSession session) {
		int retCode = 1;
		ArrayList results = new ArrayList();

		try {

			Connection Conn = this.getConnection();

			// Do something with the Connection
			Statement Stmt = Conn.createStatement();
			StringBuffer s = new StringBuffer(
					"SELECT USER_ID, USERNAME, USER_ROLE, LOGIN_COUNT, FARM_BASE FROM "
							+ this.getDatabase() + ".SYSTEM_USER ");
			s.append("WHERE FARM_BASE='" + farm + "'  ");
			System.out.println(s.toString());
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
			session.setAttribute("RESULTS_" + session.getId(), results);
			RS.close();
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			retCode = 0;
			System.out.println(E.getMessage());
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			retCode = 0;
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			e.printStackTrace();
		}

		return retCode;
	}

	public boolean secureLogin(String username, String password,
			HttpSession session) {

		boolean success = true;
		ArrayList errors = new ArrayList();
		
		try {

			if (username.trim().length() == 0 || username.equals("username")) {
				errors.add("Username is required.");
				success = false;
				session.setAttribute("ERRORS", errors);
			}
			if (password.trim().length() == 0 || password.equals("password")) {
				errors.add("Password is required.");
				success = false;
				
			}
		
			if (!success) {
				session.setAttribute("ERRORS", errors);
				return false;
			}
			
			
			success=false;
			
				Connection Conn = this.getConnection();
	
				// Do something with the Connection
				String query="SELECT * from SYSTEM_USER WHERE USERNAME='"
						+ username
						+ "' AND PASSWORD='"
						+ password
						+ "'";
				Statement Stmt = Conn.createStatement();
				ResultSet RS = Stmt
						.executeQuery(query);
				LOGGER.log(Level.INFO, query);
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
					user.setGroup(RS.getString(15));
	
					if (username.equals(uid)) {
	
						if (password.equals(pwd)) {
							
							if ("Dispatch".equals(user.getGroup())) {
								session.setAttribute(
										"USER_" + session.getId(), user);
								this.updateLoginCount(user.getUserId(),
										session);
								success = true;
							} else {
								errors.add("Access denied. This user is not authorized to view this application.");
								session.setAttribute("ERRORS", errors);
								return false;
							}
						}
	
					}
	
				}
				
				// Clean up after ourselves
				RS.close();
				Stmt.close();
				Conn.close();
			
		} catch (SQLException E) {
			LOGGER.log(Level.SEVERE, E.getMessage());
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
		if (!success) {
			errors.add("The username or password entered is not valid.");
		    session.setAttribute("ERRORS", errors);
		} 
		
		return success;
	}

	public int updateLoginCount(Integer id, HttpSession session) {

		int retCode = 0;
		// Do something with the Connection
		try {
			Connection Conn = this.getConnection();
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE "
					+ this.getDatabase()
					+ ".SYSTEM_USER SET LOGIN_COUNT=LOGIN_COUNT+1 WHERE USER_ID="
					+ id + ";");
			retCode = Stmt.executeUpdate(query.toString());

			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("ERROR_" + session.getId(), E.getMessage());
		} catch (ClassNotFoundException e) {
			session.setAttribute("ERROR_" + session.getId(), e.getMessage());
			e.printStackTrace();
		}

		return retCode;
	}

	public int updatePassword(Integer id, String password, String question,
			String answer, HttpSession session) {

		int retCode = 0;
		// Do something with the Connection
		try {
			Connection Conn = this.getConnection();
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE " + this.getDatabase()
					+ ".SYSTEM_USER SET PASSWORD='" + password
					+ "', QUESTION='" + question.replace("'", "''")
					+ "', ANSWER='" + answer + "' WHERE USER_ID=" + id + ";");
			retCode = Stmt.executeUpdate(query.toString());

			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("ERROR_" + session.getId(), E.getMessage());
		} catch (ClassNotFoundException e) {
			session.setAttribute("ERROR_" + session.getId(), e.getMessage());
			e.printStackTrace();
		}

		return retCode;
	}

	public Long insertDonation(Donor donor, Address addy, Donation d,
			HttpSession session) {

		int retCode = 0;
		long confirmation = 0;
		Long key = new Long("0");

		try {

			if (donor.getDonorId() == null) {
				Long rc = this.insertDonor(donor, session);
				donor.setDonorId(rc);
				addy.setDonorId(rc);

				if (rc == 0)
					return rc;
				else {
					d.setDonorId(rc);
					confirmation = rc;
					rc = this.insertAddress(addy, session);
					addy.setAddressId(rc);
					if (rc == 0)
						return rc;
				}
			}
			Connection Conn = this.getConnection();

			// Do something with the Connection
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO " + this.getDatabase() + ".DONATION (");
			query.append(" DONOR_ID, DISPATCH_DATE, LOCATION, STATUS, SPECIAL_FLAG, CALL_REQUIREMENTS, AC, BEDDING, BEDDING_QTY_TYPE, BOOKS, BOOKS_QTY_TYPE, ");
			query.append(" CLOTHING, CLOTHING_QTY_TYPE, COMPUTER, DESK, CHEST, ARMOIRE, DRESSER, MIRROR, NIGHTSTAND, HEADBOARD, FOOTBOARD, RAILS, ");
			query.append(" LAMP, LAWN_FURNITURE, MATTRESS, MATTRESS_QTY_SIZE, MISC_HOUSEHOLD_ITEMS, REFRIDGERATOR, STOVE, RECLINER, SOFA, LOVESEAT, ");
			query.append(" WALL_UNIT, TABLES, TABLE_TYPE, CHAIR, CHAIR_TYPE, TELEVISION, TELEVISION_SIZE, ELECTRONICS, WASHER, DRYER, EXERCISE_EQUIPMENT, BOOKCASE, OTTOMAN, SPECIAL_NOTES, ");
			query.append(" CREATION_DATE, CREATED_BY, FARM_BASE) VALUES (");

			query.append(d.getDonorId() + ",");
			query.append("'" + d.getDispatchDate() + "',");
			query.append("'" + d.getLocation() + "',");
			query.append("'" + d.getStatus().toUpperCase() + "',");
			if (d.getSpecialFlag() == null || "".equals(d.getSpecialFlag())) {
				query.append("'NO',");
			} else
				query.append("'YES',");
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
			query.append("'" + d.getTableType() + "',");
			query.append("'" + d.getChair() + "',");
			query.append("'" + d.getChairType() + "',");
			query.append("'" + d.getTelevision() + "',");
			query.append("'" + d.getTelevisionSize() + "',");
			query.append("'" + d.getElectronics() + "',");
			query.append("'" + d.getWasher() + "',");
			query.append("'" + d.getDryer() + "',");
			query.append("'" + d.getExerciseEquipment() + "',");
			query.append("'" + d.getBookcase() + "',");
			query.append("'" + d.getOttoman() + "',");
			query.append("'" + d.getSpecialNotes() + "',");
			query.append("'" + valid8r.getEpoch() + "',");
			query.append("'" + d.getCreatedBy() + "', ");
			query.append("'" + d.getFarmBase() + "' );");

			Stmt = Conn.prepareStatement(query.toString(),
					Stmt.RETURN_GENERATED_KEYS);
			Stmt.executeUpdate(query.toString());
			ResultSet generatedKeys = Stmt.getGeneratedKeys();

			if (generatedKeys.next())
				key = generatedKeys.getLong(1);
			d.setDonationId(key);
			session.setAttribute("temp_donation",d );
			session.setAttribute("temp_donor",donor );
			session.setAttribute("temp_address",addy );
			
			// Clean up after ourselves
			Stmt.close();
			Conn.close();
		} catch (SQLException E) {
			session.setAttribute("SYSTEM_ERROR", E.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			session.setAttribute("SYSTEM_ERROR", e.getMessage());
		}

		return key;

	}

	public Long insertDonor(Donor d, HttpSession session) {

		Long key = new Long("0");

		try {
			Connection Conn = this.getConnection();
			// Do something with the Connection

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO " + this.getDatabase() + ".DONOR (");
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

			Connection Conn = this.getConnection();
			// Do something with the Connection

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO " + this.getDatabase() + ".ADDRESS (");
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

			Connection Conn = this.getConnection();

			// Do something with the Connection

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO " + this.getDatabase() + ".SYSTEM_USER (");
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

	public int insertCallLog(String type, String source, String user,
			String farm, HttpSession session) {

		int retCode = 0;

		try {

			Connection Conn = this.getConnection();

			// Do something with the Connection
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO " + this.getDatabase() + ".CALL_LOG (");
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

	public int insertDailyLimit(String dispatchDate, int limit, String farm,
			String user, HttpSession session) {

		int retCode = 0;

		try {

			Connection Conn = this.getConnection();

			// Do something with the Connection
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO " + this.getDatabase() + ".DAILY_LIMIT (");
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

	public int updateDailyLimit(String dispatchDate, int limit, String farm,
			String user, HttpSession session) {

		int retCode = 0;

		try {

			Connection Conn = this.getConnection();

			// Do something with the Connection
			Statement Stmt = Conn.createStatement();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE " + this.getDatabase() + ".DAILY_LIMIT ");
			query.append(" SET DAILY_LIMIT=" + limit + ", UPDATED_BY='" + user
					+ "', UPDATED_DATE='" + valid8r.getEpoch() + "' ");
			query.append(" WHERE DISPATCH_DATE='" + dispatchDate
					+ "' AND FARM_BASE='" + farm + "'");
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

	public String getSERVER() {
		return SERVER;
	}

	public void setSERVER(String sERVER) {
		SERVER = sERVER;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public Validator getValid8r() {
		return valid8r;
	}

	public void setValid8r(Validator valid8r) {
		this.valid8r = valid8r;
	}

}