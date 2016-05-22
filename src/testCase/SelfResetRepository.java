package testCase;

import java.util.HashMap;

import model.group.Seminar;
import model.publication.Publication;
import model.user.AcademicStaff;
import model.user.RegisteredUser;

import control.Repository;

/**
 * The <code>SelfResetRepository</code> class extends from {@link Repository}.
 * It initialises the files and all <code>HashMap</code> in it as empty status.
 * Namely, the <code>SelfResetRepository</code> class always reset all the
 * attributes and files when it is created.
 * 
 * @see Repository
 */
public class SelfResetRepository extends Repository {

	/**
	 * Initialise a newly created <code>SelfResetRepository</code> object with
	 * all attributes and files reseted.
	 * 
	 * @param filePath
	 * 		  The file path that this repository read and write files from.
	 */
	public SelfResetRepository(String filePath) {
		// call constructor of the Repository
		super(filePath);

		// reset all attributes in this repository
		administrator = new RegisteredUser("Admin","pwd123","admin@york.com" );
		mapSize = new HashMap<String, Integer>();
		mapSize.put("seminarIDAutoIncrement", 100);
		mapSize.put("pubIDAutoIncrement", 100);
		publicationMap = new HashMap<String, Publication>();
		seminarMap = new HashMap<Integer, Seminar>();
		staffMap = new HashMap<String, AcademicStaff>();
		
		// refresh all files with reseted attributes
		updateProfileFile();
		updatePublicationFile();
		updateSeminarFile();
		updateMapSizeFile();
		
		// reload attributes
		getAdministrator();
		getGroupMap();
		getMapSize();
		getPublicationMap();
		getSeminarMap();
		getStaffMap();
	}
}
