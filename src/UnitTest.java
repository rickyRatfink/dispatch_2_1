import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.faithfarm.domain.Donation;
import org.faithfarm.domain.Donor;
import org.faithfarm.domain.SystemUser;
import org.faithfarm.service.data.DispatchDao;
import org.faithfarm.util.Validator;


public class UnitTest {

	public static void main(String[] args) {
		
		Validator v = new Validator();
		DispatchDao dao = new DispatchDao();
		
		/*Donor d1 = new Donor();
		Donation d2 = new Donation();
		d2.setCreationDate(v.getEpoch());
		d2.setAc("5");
		d1.setFirstname("Test");
		d1.setLastname("User");
		d1.setCreationDate(v.getEpoch()+"");
		
		int retCode = dao.insertDonation(d1, d2, null);
		*/
		SystemUser user= new SystemUser();
		user.setUsername("rrratliff");
		user.setPassword("a999919");
		user.setUserRole("ADMIN");
		user.setFarmBase("BOYNTON BEACH");
		Long retCode = dao.insertSystemUser(user,  null);
		
		
	}

}
