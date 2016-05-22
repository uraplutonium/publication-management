package testCase;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import model.enumeration.StaffTitle;
import model.enumeration.UserRole;
import control.PMSystem;
import control.controller.AdministratorController;
import control.controller.GeneralUserController;

/**
 * Test cased for <code>AdministratorController</code>.
 *
 * @see AdministratorController
 * @see PMSystem
 * @see SelfResetRepository
 */
public class TestSystemAdmin extends PMSystem {

	/**
	 * Create an example that contains several staffs and publication and seminars for testing.
	 * Initialise the PMSystem and replace normal <code>Repository</code>
	 * with a <code>SelfResetRepository</code> at the beginning.
	 * <p>
	 * <blockquote><table cellpadding=1 cellspacing=0 summary="Split example showing regex, limit, and result">
     * <tr>
     *     <th>UserName | </th>
     *     <th>Book | </th>
     *     <th>Journal | </th>
     *     <th>Conference | </th>
     *     <th>Seminar</th>
     * </tr>
     * <tr><td align=center>hr101</td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     * <tr><td align=center>*tr102</td>
     *     <td align=center></td>
     *     <td align=center></td>
     *     <td align=center></td>
     *     <td align=center>1</td>
     * <tr><td align=center>ll103</td>
     *     <td align=center></td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     *     <td align=center></td>
     * <tr><td align=center>sb201</td>
     *     <td align=center>2</td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     *     <td align=center></td>
     * <tr><td align=center>cl202</td>
     *     <td align=center></td>
     *     <td align=center></td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     * <tr><td align=center>lm203</td>
     *     <td align=center></td>
     *     <td align=center></td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     * <tr><td align=center>me401</td>
     *     <td align=center></td>
     *     <td align=center></td>
     *      <td align=center></td>
     *     <td align=center></td>
     * <tr><td align=center>*cp901</td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     *     <td align=center></td>
     *     <td align=center>2</td>
     * </table></blockquote>
     */
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		repository = new SelfResetRepository("testCaseFolder/system_admin_test/");
		
		//////////////// login as administrator to add staff details ////////////////
		getGeneralUserController().loginout("Admin", "pwd123");
		
		// Group 1 - 3 people
		getAdministratorController().addStaff("hr101", "hr101", "hr101@york.ac.uk",
				"Howard Rheingold", StaffTitle.MR, 1);
		getAdministratorController().addStaff("tr102", "tr102", "tr102@york.ac.uk",
				"Tim O'Reilly", StaffTitle.PROFESSOR, 1);
		getAdministratorController().addStaff("ll103", "ll103", "ll103@york.ac.uk",
				"Linda Lawrey", StaffTitle.DOCTOR, 1);
		getAdministratorController().assignCoordinator("tr102");
				
		// Group 2 - 3 people
		getAdministratorController().addStaff("sb201", "sb201", "sb201@york.ac.uk",
				"Sergey Brin", StaffTitle.DOCTOR, 2);
		getAdministratorController().addStaff("cl202", "cl202", "cl202@york.ac.uk",
				"Cali Lewis", StaffTitle.MISS, 2);
		getAdministratorController().addStaff("lm203", "lm203", "lm203@york.ac.uk",
				"Loic Le Meur", StaffTitle.DOCTOR, 2);
				
		// Group 4 - 1 people
		getAdministratorController().addStaff("me401", "me401", "me401@york.ac.uk",
				"Mike Elgan", StaffTitle.PROFESSOR, 4);
		
		// Group 9 - 1 people
		getAdministratorController().addStaff("cp901", "cp901", "cp901@york.ac.uk",
				"Chris Pirillo", StaffTitle.MASTER, 9);
		getAdministratorController().assignCoordinator("cp901");
		
		getAdministratorController().loginout("Admin", null);
		
		//////////////// login as academic staff to add publication seminar details ////////////////
		
		// Group 1 - hr101(Howard Rheingold) - 2 publication
		getGeneralUserController().loginout("hr101", "hr101");
		Set<String> authorSet_101_1 = new HashSet<String>();
		authorSet_101_1.add("Linda Lawrey");
		Date date_101_1 = new Date();
		date_101_1.setYear(100);
		getAcademicStaffController().addBook("BK0001", "Modeling Information Sciences",
				authorSet_101_1, date_101_1, "University of Calgary Publishing", "Calgary, CA");
		Set<String> authorSet_101_2 = new HashSet<String>();
		Date date_101_2 = new Date();
		date_101_2.setYear(105);
		getAcademicStaffController().addConferencePaper("CP0001", "Testing Culture",
				authorSet_101_2, date_101_2, "New York, US");
		getAcademicStaffController().loginout("hr101", null);
		
		// Group 1 - tr102(Tim O'Reilly) - 0 publication, 1 seminar
		getGeneralUserController().loginout("tr102", "tr102");
		getCoordinatorController().addSeminar(1, "Requirements Engineering", "15:00", "01, Jan, 2012", "University of York");
		getCoordinatorController().loginout("tr102", null);
		
		// Group 1 - ll103(Linda Lawrey) - 1 publication
		getGeneralUserController().loginout("ll103", "ll103");
		Set<String> authorSet_103_1 = new HashSet<String>();
		authorSet_103_1.add("Tim O'Reilly");
		authorSet_103_1.add("Robert Scoble");
		Date date_103_1 = new Date();
		date_103_1.setYear(105);
		getAcademicStaffController().addJournalPaper("JP0001", "Defining Quality Intuitively",
				authorSet_103_1, date_103_1, "Technical Report", "TR-201-74", "pp.35-37");
		getAcademicStaffController().loginout("ll103", null);

		// Group 2 - sb201(Sergey Brin) - 3 publication
		getGeneralUserController().loginout("sb201", "sb201");
		Set<String> authorSet_201_1 = new HashSet<String>();
		authorSet_201_1.add("Robert Scoble");
		authorSet_201_1.add("Mike Elgan");
		Date date_201_1 = new Date();
		date_201_1.setYear(100);
		getAcademicStaffController().addBook("BK0002", "Essential Software Architecture", authorSet_201_1,
				date_201_1, "Dorset Hourse", "Southern California, US");
		Set<String> authorSet_201_2 = new HashSet<String>();
		authorSet_201_2.add("Amber Mac");
		authorSet_201_2.add("Zee M Kane");
		Date date_201_2 = new Date();
		date_201_2.setYear(105);
		getAcademicStaffController().addBook("BK0003", "Metrics for Small Projects", authorSet_201_2,
				date_201_2, "Prentice Hall", "London, UK");
		Set<String> authorSet_201_3 = new HashSet<String>();
		authorSet_201_3.add("Zee M Kane");
		authorSet_201_3.add("Mike Elgan");
		Date date_201_3 = new Date();
		date_201_3.setYear(112);
		getAcademicStaffController().addJournalPaper("JP0002", "Stakeholders in Requirements Engineering",
				authorSet_201_3, date_201_3, "IEEE Software, vol.24, no.2", "IEEE-3467-90", "pp.18-20");
		getAcademicStaffController().loginout("sb201", null);

		// Group 2 - cl202(Cali Lewis) - 1 publication
		getGeneralUserController().loginout("cl202", "cl202");
		Set<String> authorSet_202_1 = new HashSet<String>();
		authorSet_202_1.add("Chris Pirillon");
		Date date_202_1 = new Date();
		date_202_1.setYear(112);
		getAcademicStaffController().addConferencePaper("CP0002", "Elements of Software Science",
				authorSet_202_1, date_202_1, "North Holland");
		getAcademicStaffController().loginout("cl202", null);

		// Group 2 - lm203(Loic Le Meur) - 1 publication
		getGeneralUserController().loginout("lm203", "lm203");
		Set<String> authorSet_203_1 = new HashSet<String>();
		authorSet_203_1.add("Cali Lewis");
		Date date_203_1 = new Date();
		date_203_1.setYear(103);
		getAcademicStaffController().addConferencePaper("CP0003", "Aspect Oriented Programming",
				authorSet_203_1, date_203_1, "York, UK");
		getAcademicStaffController().loginout("lm203", null);

		// Group 9 - cp901(Chris Pirillon) - 1 publication, 2 seminars
		getGeneralUserController().loginout("cp901", "cp901");
		Set<String> authorSet_901_1 = new HashSet<String>();
		authorSet_901_1.add("Howard Rheingold");
		authorSet_901_1.add("Linus Torvalds");
		authorSet_901_1.add("Linda Lawrey");
		Date date_901_1 = new Date();
		date_901_1.setYear(112);
		getCoordinatorController().addBook("BK0004", "Languages for Formal Specification",
				authorSet_901_1, date_901_1, "Prentice Hall", "London, UK");
		getCoordinatorController().addSeminar(2, "Integrated Envirenment", "13:00", "01, Mar, 2012", "Kings College, London");
		getCoordinatorController().addSeminar(3, "Improving the Design of Existing Code", "14:00", "01, Apr, 2012", "University of York");
		getCoordinatorController().loginout("cp901", null);
	}
	
	/**
	 * Test <code>loginout(String userName, String password)</code> for administrator.
	 */
	@Test
	public void testAdminLoginout() {
		GeneralUserController generalUserController = getGeneralUserController();
		AdministratorController administratorController = getAdministratorController();
		
		assertEquals(UserRole.GENERAL_USER, generalUserController.loginout("Admin", "pwd"));		// login shall fail with wrong password
		assertEquals(UserRole.GENERAL_USER, generalUserController.loginout("administrator", "pwd123"));	// login shall fail with wrong userName
		assertEquals(UserRole.ADMINISTRATOR, generalUserController.loginout("Admin", "pwd123"));	// login shall success
		assertEquals(UserRole.GENERAL_USER, administratorController.loginout("Admin", null));		// logout shall success
	}
	
	/**
	 * Test <code>changeStatus(String userName)</code>.
	 */
	@Test
	public void testActiveStaff() {
		GeneralUserController generalUserController = getGeneralUserController();
		AdministratorController administratorController = getAdministratorController();
		
		generalUserController.loginout("Admin", "pwd123");
		
		// check before change status of cp901
		assertEquals(true, repository.getStaffMap().keySet().contains("cp901"));	// cp901 should be in the staffMap
		assertEquals(true, repository.getGroupMap().get(9).containsStaff("cp901"));	// cp901 should be in group 9 in groupMap
		assertEquals(true, administratorController.getStaffStatus("cp901"));		// cp901's status should be true
		assertEquals(1, administratorController.isCoordinator("cp901"));			// cp901 should be a coordinator
		assertEquals("cp901", repository.getGroupMap().get(9).getCoordinatorUserName());// cp901 should be the coordinator of group 9
		
		assertEquals(true, administratorController.changeStatus("cp901"));			// cp901 made a difficult decision to leave UoY
		
		// check after cp901 left
		assertEquals(true, repository.getStaffMap().keySet().contains("cp901"));	// cp901 should still be in the staffMap
		assertEquals(false, repository.getGroupMap().get(9).containsStaff("cp901"));// cp901 should not be in group 9 in groupMap
		assertEquals(false, administratorController.getStaffStatus("cp901"));		// cp901's status should be false
		assertEquals(0, administratorController.isCoordinator("cp901"));			// cp901 should not be a coordinator any longer
		assertEquals(null, repository.getGroupMap().get(9).getCoordinatorUserName());// the coordinator of group 9 should be null now
		
		assertEquals(true, administratorController.changeStatus("cp901"));			// cp901 made a more difficult decision to come back
		
		// check after cp901 come back
		assertEquals(true, repository.getStaffMap().keySet().contains("cp901"));	// cp901 should be in the staffMap
		assertEquals(true, repository.getGroupMap().get(9).containsStaff("cp901"));	// cp901 should be in group 9 in groupMap
		assertEquals(true, administratorController.getStaffStatus("cp901"));		// cp901's status should be true
		assertEquals(0, administratorController.isCoordinator("cp901"));			// cp901 should not be a coordinator any longer
		assertEquals(null, repository.getGroupMap().get(9).getCoordinatorUserName());// the coordinator of group 9 should be null now	
	
		assertEquals(false, administratorController.changeStatus("nothing"));	// should fail when trying to change a staff does not exist
	
		administratorController.loginout("Admin", null);
	}
	
	/**
	 * Test <code>assignCoordinator(String userName)</code>.
	 */
	@Test
	public void testAssignCoordinator() {
		GeneralUserController generalUserController = getGeneralUserController();
		AdministratorController administratorController = getAdministratorController();
		
		generalUserController.loginout("Admin", "pwd123");
		
		// check before assigning a coordinator
		assertEquals(0, administratorController.isCoordinator("cl202"));	// cl202 is not a coordinator
		assertEquals(null, repository.getGroupMap().get(2).getCoordinatorUserName());	// coordinator userName of group 2 should be null
		
		assertEquals(true, administratorController.assignCoordinator("cl202"));

		// check after assigning a coordinator
		assertEquals(1, administratorController.isCoordinator("cl202"));	// cl202 is a coordinator now
		assertEquals("cl202", repository.getGroupMap().get(2).getCoordinatorUserName());	// coordinator userName of group 2 should be cl202
		
		assertEquals(false, administratorController.assignCoordinator("cl202"));	// should fail if try to assign a staff that already is a coordinator
		
		// check before reassigning a coordinator
		assertEquals(0, administratorController.isCoordinator("lm203"));	// lm203 is not coordinator
		assertEquals(1, administratorController.isCoordinator("cl202"));		// cl202 is a coordinator
		assertEquals("cl202", repository.getGroupMap().get(2).getCoordinatorUserName());	// coordinator userName of group 2 should be cl202
	
		assertEquals(true, administratorController.assignCoordinator("lm203"));	// replace cl202 with lm203 as the new coordinator
		
		// check after reassigning a coordinator
		assertEquals(1, administratorController.isCoordinator("lm203"));	// lm203 is the new coordinator
		assertEquals(0, administratorController.isCoordinator("cl202"));		// cl202 is not a coordinator any longer
		assertEquals("lm203", repository.getGroupMap().get(2).getCoordinatorUserName());	// coordinator userName of group 2 should be lm203
		
		administratorController.loginout("Admin", null);
	}

}
