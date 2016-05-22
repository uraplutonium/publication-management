package testCase;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import model.enumeration.StaffTitle;

import org.junit.Before;
import org.junit.Test;

import control.PMSystem;
import control.controller.GeneralUserController;

/**
 * Test cased for <code>GeneralUserController</code>.
 *
 * @see GeneralUserController
 * @see PMSystem
 * @see SelfResetRepository
 */
public class TestGeneralUser extends PMSystem  {
	
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
		repository = new SelfResetRepository("testCaseFolder/general_user_test/");
		
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
	 * Test <code>getPublicationID()</code> and 
	 * <code>getPublicationID(String authorName, int year, int groupNumber)</code>.
	 */
	@Test
	public void testViewPublication() {		
		GeneralUserController controller = getGeneralUserController();
		
		assertEquals(9, controller.getPublicationID().size());	// there shall be 9 publication totally
		assertEquals(2, controller.getPublicationID("hr101", -1, -1).size());	// there shall be 2 publication of hr101
		assertEquals(0, controller.getPublicationID("tr102", -1, -1).size());	// there shall be 0 publication of tr102
		assertEquals(1, controller.getPublicationID("ll103", -1, -1).size());	// there shall be 1 publication of ll103
		assertEquals(1, controller.getPublicationID("cl202", -1, -1).size());	// there shall be 1 publication of cl202
		assertEquals(1, controller.getPublicationID("lm203", -1, -1).size());	// there shall be 1 publication of lm203
		assertEquals(0, controller.getPublicationID("me401", -1, -1).size());	// there shall be 0 publication of me401
		assertEquals(1, controller.getPublicationID("cp901", -1, -1).size());	// there shall be 1 publication of cp901

		assertEquals(2, controller.getPublicationID(null, 100, -1).size());		// there shall be 2 publication in 2000
		assertEquals(1, controller.getPublicationID(null, 103, -1).size());		// there shall be 1 publication in 2003
		assertEquals(3, controller.getPublicationID(null, 105, -1).size());		// there shall be 3 publication in 2005
		assertEquals(0, controller.getPublicationID(null, 107, -1).size());		// there shall be 0 publication in 2007
		assertEquals(3, controller.getPublicationID(null, 112, -1).size());		// there shall be 3 publication in 2012
		
		assertEquals(3, controller.getPublicationID(null, -1, 1).size());		// there shall be 3 publication in group 1
		assertEquals(5, controller.getPublicationID(null, -1, 2).size());		// there shall be 5 publication in group 2
		assertEquals(0, controller.getPublicationID(null, -1, 4).size());		// there shall be 0 publication in group 4
		assertEquals(1, controller.getPublicationID(null, -1, 9).size());		// there shall be 1 publication in group 9

		assertEquals(2, controller.getPublicationID(null, 105, 1).size());		// there shall be 2 publication in group 1 in 2005
		assertEquals(1, controller.getPublicationID("sb201", 105, -1).size());	// there shall be 1 publication of sb201 in 2005
		assertEquals(3, controller.getPublicationID("sb201", -1, 2).size());	// there shall be 3 publication of sb201 in group 2
		assertEquals(1, controller.getPublicationID("sb201", 112, 2).size());	// there shall be 1 publication of sb201 in group 2 in 2012
		
		assertEquals(null, controller.getPublicationID("nothing", -1, -1));		// return null when author does not exist
		assertEquals(null, controller.getPublicationID("hr101", -1, 2));		// return null when author does not match group number
		assertEquals(null, controller.getPublicationID(null, -1, 10));			// return null when input invalid group number
		assertEquals(null, controller.getPublicationID(null, -100, -1));		// return null when input invalid year
	}
	
	/**
	 * Test <code>requestPublicationCopy(String publicationID)</code>.
	 */
	@Test
	public void testRequestCopy(){
		GeneralUserController controller = getGeneralUserController();

		String[] userName = {"hr101"};
	//	String[] userName = {"hr101", "tr102", "ll103", "sb201", "cl202", "lm203", "me401", "cp901"};
		for(String user : userName) {	// for each of the staff
			for(String publicationID : controller.getPublicationID(user, -1, -1)) {	// for each publication for a staff
				// request a copy should return a email of the publication's uploader
				assertEquals((user + "@york.ac.uk"), controller.requestPublicationCopy(publicationID));
			}
		}
	}
	
}
