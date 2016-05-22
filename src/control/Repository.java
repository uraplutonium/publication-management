package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import model.group.Group;
import model.group.Seminar;
import model.publication.Publication;
import model.user.AcademicStaff;
import model.user.RegisteredUser;

/**
 * The <code>Repository</code> class stores all the information about
 * staffs, publications, seminars, groups, administrator and some other
 * item in this system.
 * <p>
 * The <code>Repository</code> class is using Lazy Initialisation Pattern.
 * The six attributes in <code>Repository</code>: {@code staffMap},
 * {@code publicationMap}, {@code seminarMap}, {@code groupMap}, {@code mapSize}
 * and {@code administrator}, are all initialised as late as possible.
 * Namely, these six attributes will not be instantiated when constructor of
 * <code>Repository</code> is run. They will only be instantiated at the first
 * time they are used.
 * <p>
 * The <code>Repository</code> class is responsible for providing get and set
 * methods for each of the Maps and administrator, and also responsible for
 * Synchronising records with files in the file system. Two public and static
 * attributes {@link Repository#currentUserName} and {@link Repository#currentPublication}
 * are also provided by <code>Repository</code> class to represent which user
 * is login and which publication is viewed currently.
 * 
 * @see PMSystem
 * @see AcademicStaff
 * @see RegisteredUser
 * @see Publication
 * @see Seminar
 * @see Group
 */
public class Repository {
	
	/** The key of {@code staffMap} is a {@code String} representing the userName of a staff.
	 *  The value of {@code staffMap} is an {@link AcademicStaff} object.
	 *  
	 *  @see Map
	 *  @see AcademicStaff
	 */
	protected Map<String, AcademicStaff> staffMap;
	
	/** The key of {@code publicationMap} is a {@code String} representing the ID of a publication.
	 *  The value of {@code publicationMap} is an {@link Publication} object.
	 *  
	 *  @see Map
	 *  @see Publication
	 */
	protected Map<String, Publication> publicationMap;

	/** The key of {@code seminarMap} is a {@code Integer} representing the ID of a seminar.
	 *  The value of {@code seminarMap} is an {@link Seminar} object.
	 *  
	 *  @see Map
	 *  @see Seminar
	 */
	protected Map<Integer, Seminar> seminarMap;
	
	/** The key of {@code groupMap} is a {@code Integer} representing the number of a group.
	 *  The value of {@code groupMap} is an {@link Group} object.
	 *  
	 *  @see Map
	 *  @see Group
	 */
	protected Map<Integer, Group> groupMap;
	
	/** The key of {@code mapSize} is a {@code String} representing the name of map counter,
	 *  which could only be <code>seminarIDAutoIncrement</code> or <code>pubIDAutoIncrement</code>.
	 *  <p>
	 *  The value of {@code seminarIDAutoIncrement} represents how much ID of seminar has been used.
	 *  <p>
	 *  The value of {@code pubIDAutoIncrement} represents how much ID of publication has been used.
	 *  
	 *  @see Map
	 *  @see Group
	 */
	protected Map<String, Integer> mapSize;
	
	/** The entity of <code>administrator</code>. */
	protected RegisteredUser administrator;
	
	/** The path of files this <code>repository</code> save its data in, which should be a format of "filePath/". */
	private String filePath;

	/** UserName of the use that login this system currently. Null if it is a general user. */
	public static String currentUserName;
	
	/** ID of the publication that is viewed currently. Null if none is viewed currently. */
	public static String currentPublication;
	
	/**
	 * Initialises a newly created {@code Repository} object with all attributes null,
	 * since Lazy Initialisation Pattern. And the file path is the default folder.
	 */
	public Repository() {
		this("");
	}
	
	/**
	 * Initialises a newly created {@code Repository} object with all attributes null,
	 * since Lazy Initialisation Pattern. And the file path is the passed by parameter.
	 * 
	 * @param filePath
	 * 		  The relative of file path. It should be in format of "filePath/",
	 * 		  which mean all files will be read and saved from "[PMSystem folder]/filePath/".
	 */
	public Repository(String filePath) {
		currentUserName = null;
		currentPublication = null;
		
		staffMap = null;
		publicationMap = null;
		seminarMap = null;
		groupMap = null;
		administrator = null;
		mapSize = null;
		this.filePath = filePath;
		
		File fileDir = new File(filePath);
		fileDir.mkdirs();
	}
	
	//////////////////////////////////////// Getting Map Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Return the <code>staffMap</code>, and initialise it with reading <code>profile.group2</code>
	 * if not initialised yet.
	 * 
	 * @return staffMap
	 * @see Repository#readStaffMapFromExternalFileAsObject()
	 * @see Repository#writeStaffMapToExternalFileAsObject()
	 */
	public Map<String, AcademicStaff> getStaffMap() {
		if(staffMap == null) {
			File staffFile = new File(filePath + "profile.group2");
			
			// Does the file already exist
			if(!staffFile.exists())
			{
				// Try creating the file
				staffMap = new HashMap<String, AcademicStaff>();
				writeStaffMapToExternalFileAsObject();
			} else {
				readStaffMapFromExternalFileAsObject();
			}
		}
		return staffMap;
	}
	
	/**
	 * Return the <code>publicationMap</code>, and initialise it with reading <code>publication.group2</code>
	 * if not initialised yet.
	 * 
	 * @return publicationMap
	 * @see Repository#readPublicationMapFromExternalFileAsObject()
	 * @see Repository#writePublicationMapToExternalFileAsObject()
	 */
	public Map<String, Publication> getPublicationMap() {
		if(publicationMap == null) {
			File publicationFile = new File(filePath + "publication.group2");

			// Does the file already exist
			if(!publicationFile.exists())
			{
				// Try creating the file
				publicationMap = new HashMap<String, Publication>();
				writePublicationMapToExternalFileAsObject();
			} else {
				readPublicationMapFromExternalFileAsObject();
			}
		}
		return publicationMap;
	}
	
	/**
	 * Return the <code>seminarMap</code>, and initialise it with reading <code>seminar.group2</code>
	 * if not initialised yet.
	 * 
	 * @return seminarMap
	 * @see Repository#readSeminarMapFromExternalFileAsObject()
	 * @see Repository#writeSeminarMapToExternalFileAsObject()
	 */
	public Map<Integer, Seminar> getSeminarMap() {
		if(seminarMap == null) {
			File seminarFile = new File(filePath + "seminar.group2");

			// Does the file already exist
			if(!seminarFile.exists())
			{
				// Try creating the file
				seminarMap = new HashMap<Integer, Seminar>();
				writeSeminarMapToExternalFileAsObject();
			  
			} else {
				readSeminarMapFromExternalFileAsObject();
			}	
		}
		return seminarMap;
	}

	/**
	 * Return the <code>groupMap</code>, and initialise it with information in staffMap and
	 * publicationMap if not initialised yet.
	 * 
	 * @return groupMap
	 * @see Repository#getStaffMap()
	 * @see Repository#getSeminarMap()
	 */
	public Map<Integer, Group> getGroupMap() {
		if(groupMap == null) {
			groupMap = new HashMap<Integer, Group>();
			for(int i=0; i<9; i++) {
				groupMap.put(i+1, new Group(i+1));
			}
			
			for(AcademicStaff staff : getStaffMap().values()) {	// MUST get access to staffMap by and only by getStaffMap()
				Group group = groupMap.get(staff.getGroup());
				group.addStaff(staff.getUserName());
				if(staff.isCoordinator())
					group.setCoordinatorUserName(staff.getUserName());
			}
			
			for(Seminar seminar : getSeminarMap().values()) {	// MUST get access to seminarMap by and only by getSeminarMap()
				Group group = groupMap.get(seminar.getGroupNumber());
				group.addSeminar(seminar.getSeminarID());
			}
		}
		return groupMap;
	}

	/**
	 * Return the <code>mapSize</code> map, and initialise it with reading <code>systemMapSize.group2</code>
	 * if not initialised yet.
	 * 
	 * @return mapSize
	 * @see Repository#readMapSizeFromExternalFileAsObject()
	 * @see Repository#writeMapSizeToExternalFileAsObject()
	 */
	public Map<String, Integer> getMapSize(){
		if(mapSize == null) {
			File systemMapSizeFile = new File(filePath + "systemMapSize.group2");

			// Does the file already exist
			if(!systemMapSizeFile.exists())
			{
				// Try creating the file
				mapSize = new HashMap<String, Integer>();
				mapSize.put("seminarIDAutoIncrement", 100);
				mapSize.put("pubIDAutoIncrement", 100);
				writeMapSizeToExternalFileAsObject();
			} else {
				readMapSizeFromExternalFileAsObject();
			}
		}
		return mapSize;
	}
	
	/**
	 * Return the <code>administrator</code>, and initialise it with reading <code>systemAdmin.group2</code>
	 * if not initialised yet.
	 * 
	 * @return administrator
	 * @see Repository#readSystemAdminMapFromExternalFileAsObject()
	 * @see Repository#writeSystemAdminMapToExternalFileAsObject()
	 */
	public RegisteredUser getAdministrator() {
		if(administrator == null) {
			File systemAdminFile = new File(filePath + "systemAdmin.group2");

			// Does the file already exist
			if(!systemAdminFile.exists())
			{
				// Try creating the file
				administrator = new RegisteredUser("Admin","pwd123","admin@york.com" );
				writeSystemAdminMapToExternalFileAsObject();
			} else {
				readSystemAdminMapFromExternalFileAsObject();
			}
		}
		return administrator;
	}
	
	//////////////////////////////////////// File Updating \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Update the files about staff and administrator profiles,
	 * to keep them synchronised with <code>staffMap</code> and <code>administrator</code>.
	 * 
	 * @see Repository#writeStaffMapToExternalFileAsObject()
	 * @see Repository#writeSystemAdminMapToExternalFileAsObject()
	 */
	public void updateProfileFile() {
		writeStaffMapToExternalFileAsObject();
		writeSystemAdminMapToExternalFileAsObject();
	}
	
	/**
	 * Update the file about publication, to keep it synchronised with <code>publicationMap</code>.
	 * 
	 * @see Repository#writePublicationMapToExternalFileAsObject()
	 */
	public void updatePublicationFile() {
		writePublicationMapToExternalFileAsObject();
	}
	
	/**
	 * Update the file about seminar, to keep it synchronised with <code>seminarMap</code>.
	 * 
	 * @see Repository#writeSeminarMapToExternalFileAsObject()
	 */
	public void updateSeminarFile() {
		writeSeminarMapToExternalFileAsObject();
	}
	
	/**
	 * Update the file about counting of ID in staffMap and publicatoinMap,
	 * to keep it synchronised with <code>mapSize</code>.
	 * 
	 * @see Repository#writeMapSizeToExternalFileAsObject()
	 */
	public void updateMapSizeFile() {
		writeMapSizeToExternalFileAsObject();
	}	

	//////////////////////////////////////// File Handling \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Overwrite file <code>publication.group2</code> with <code>publicationMap</code>
	 * 
	 * @see FileOutputStream
	 * @see ObjectOutputStream
	 * @see Repository#publicationMap
	 */
	private void writePublicationMapToExternalFileAsObject(){		
		try
		{
			FileOutputStream outPublication = new FileOutputStream(filePath + "publication.group2");
			ObjectOutputStream outPublciationObj = new ObjectOutputStream(outPublication);
			outPublciationObj.writeObject(publicationMap);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Read <code>publication.group2</code> to update the <code>publicationMap</code>
	 * 
	 * @see FileInputStream
	 * @see ObjectInputStream
	 * @see Repository#publicationMap
	 */
	@SuppressWarnings("unchecked")
	private void readPublicationMapFromExternalFileAsObject(){
		try
		{
			FileInputStream inPublication = new FileInputStream(filePath + "publication.group2");
			ObjectInputStream inPublicationObj = new ObjectInputStream(inPublication);
			publicationMap = (HashMap<String, Publication>) inPublicationObj.readObject();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Overwrite file <code>profile.group2</code> with <code>staffMap</code>
	 * 
	 * @see FileOutputStream
	 * @see ObjectOutputStream
	 * @see Repository#staffMap
	 */
	private void writeStaffMapToExternalFileAsObject(){
		try
		{
			FileOutputStream outUserProfile = new FileOutputStream(filePath + "profile.group2");
			ObjectOutputStream outUserProfileObj = new ObjectOutputStream(outUserProfile);			
			outUserProfileObj.writeObject(staffMap);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Read <code>profile.group2</code> to update the <code>staffMap</code>
	 * 
	 * @see FileInputStream
	 * @see ObjectInputStream
	 * @see Repository#staffMap
	 */
	@SuppressWarnings("unchecked")
	private void readStaffMapFromExternalFileAsObject(){
		try
		{
			FileInputStream inUserProfile = new FileInputStream(filePath + "profile.group2");
			ObjectInputStream inUserProfileObj = new ObjectInputStream(inUserProfile);
			staffMap = (HashMap<String, AcademicStaff>) inUserProfileObj.readObject();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Overwrite file <code>seminar.group2</code> with <code>seminarMap</code>
	 * 
	 * @see FileOutputStream
	 * @see ObjectOutputStream
	 * @see Repository#seminarMap
	 */
	private void writeSeminarMapToExternalFileAsObject(){
		try
		{
			FileOutputStream outUserProfile = new FileOutputStream(filePath + "seminar.group2");
			ObjectOutputStream outUserProfileObj = new ObjectOutputStream(outUserProfile);
			outUserProfileObj.writeObject(seminarMap);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Read <code>seminar.group2</code> to update the <code>seminarMap</code>
	 * 
	 * @see FileInputStream
	 * @see ObjectInputStream
	 * @see Repository#seminarMap
	 */
	@SuppressWarnings("unchecked")
	private void readSeminarMapFromExternalFileAsObject(){
		try
		{
			FileInputStream inUserProfile = new FileInputStream(filePath + "seminar.group2");
			ObjectInputStream inUserProfileObj = new ObjectInputStream(inUserProfile);
			seminarMap = (HashMap<Integer, Seminar>) inUserProfileObj.readObject();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Overwrite file <code>systemAdmin.group2</code> with <code>administrator</code>
	 * 
	 * @see FileOutputStream
	 * @see ObjectOutputStream
	 * @see Repository#administrator
	 */
	private void writeSystemAdminMapToExternalFileAsObject(){
		try
		{
			FileOutputStream outSystemAdmin = new FileOutputStream(filePath + "systemAdmin.group2");
			ObjectOutputStream outSystemAdminObj = new ObjectOutputStream(outSystemAdmin);
			outSystemAdminObj.writeObject(administrator);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Read <code>systemAdmin.group2</code> to update the <code>systemAdmin</code>
	 * 
	 * @see FileInputStream
	 * @see ObjectInputStream
	 * @see Repository#administrator
	 */
	private void readSystemAdminMapFromExternalFileAsObject(){
		try
		{
			FileInputStream inSystemAdmin = new FileInputStream(filePath + "systemAdmin.group2");
			ObjectInputStream inSystemAdminObj = new ObjectInputStream(inSystemAdmin);
			administrator = (RegisteredUser) inSystemAdminObj.readObject();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Overwrite file <code>systemMapSize.group2</code> with <code>mapSize</code>
	 * 
	 * @see FileOutputStream
	 * @see ObjectOutputStream
	 * @see Repository#mapSize
	 */
	private void writeMapSizeToExternalFileAsObject(){
		try
		{
			FileOutputStream outSystemMapSize = new FileOutputStream(filePath + "systemMapSize.group2");
			ObjectOutputStream outSystemMapSizeObj = new ObjectOutputStream(outSystemMapSize);
			outSystemMapSizeObj.writeObject(mapSize);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Read <code>systemMapSize.group2</code> to update the <code>mapSize</code>
	 * 
	 * @see FileInputStream
	 * @see ObjectInputStream
	 * @see Repository#mapSize
	 */
	@SuppressWarnings("unchecked")
	private void readMapSizeFromExternalFileAsObject(){
		try
		{
			FileInputStream inSystemMapSize = new FileInputStream(filePath + "systemMapSize.group2");
			ObjectInputStream inSystemMapSizeObj = new ObjectInputStream(inSystemMapSize);
			mapSize = (Map<String, Integer>) inSystemMapSizeObj.readObject();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
