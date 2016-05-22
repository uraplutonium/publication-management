package model.group;

import java.io.Serializable;

/**
 * The <code>Seminar</code> class represents seminar entities, that include basic information about a seminar.
 * <p>
 * The <code>Seminar</code> class implements {@link java.io.Serializable Serializable} interface, which makes 
 * it allowed to write into files by {@link java.io.ObjectOutputStream ObjectOutputStream}.
 * 
 * @see Serializable
 */
public class Seminar implements Serializable  {

	private static final long serialVersionUID = -7995247710501127232L;
	private int seminarID;	// primary key
	private String topic;
	private String time;
	private String date;
	private String place;
	private int groupNumber;
	private boolean isPublished;
	
	/**
	 * Initialises a newly created <code>Seminar</code> object with attributes of the passed parameters.
	 * 
	 * @param seminarID
	 * 		  ID of a seminar, which is the primary key of <code>Seminar</code>, and is allocated by system automatically.
	 * @param topic
	 * 		  Topic of a seminar
	 * @param time
	 * 		  Time of seminar that will be hold
	 * @param date
	 * 		  Date of seminar that will be hold
	 * @param place
	 * 		  Place of seminar that will be hold
	 * @param groupNumber
	 * 		  Number of the group this seminar belongs to. It is the same with the coordinator's group number.
	 */
	public Seminar(int seminarID, String topic,String time, String date, String place, int groupNumber) {
		this.seminarID = seminarID;
		this.topic = topic;
		this.time = time;
		this.date = date;
		this.place = place;
		this.groupNumber = groupNumber;
		this.isPublished = false;
	}
	
	/**
	 * Return the ID of seminar.
	 * 
	 * @return ID of seminar
	 */
	public int getSeminarID() {
		return seminarID;
	}
	
	/**
	 * Return the topic of seminar.
	 * 
	 * @return Topic of seminar
	 */
	public String getTopic() {
		return topic;
	}
	
	/**
	 * Return the time of seminar that will be hold.
	 * 
	 * @return Time of seminar
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * Return the date of seminar that will be hold.
	 * 
	 * @return Date of seminar
	 */
	public String getDate(){
		return date;
	}
	
	/**
	 * Return the place of seminar that will be hold.
	 * 
	 * @return Place of seminar
	 */
	public String getPlace() {
		return place;
	}
	
	/**
	 * Return number of the group the seminar belongs to.
	 * 
	 * @return Number of the group the seminar belongs to
	 */
	public int getGroupNumber() {
		return groupNumber;
	}
	
	/**
	 * Return whether this seminar is published.
	 * 
	 * @return A boolean representing whether this seminar is published
	 */
	public boolean isPublished() {
		return isPublished;
	}
	
	/**
	 * Set a new topic to the seminar.
	 * 
	 * @param newTopic
	 * 		  New topic of the seminar
	 */
	public void setTopic(String newTopic) {
		topic = newTopic;
	}
	
	/**
	 * Set a new time to the seminar.
	 * 
	 * @param newTime
	 * 		  New time of the seminar
	 */
	public void setTime(String newTime) {
		time = newTime;
	}
	
	/**
	 * Set a new date to the seminar.
	 * 
	 * @param newDate
	 * 		  New date of the seminar
	 */
	public void setDate(String newDate){
		date = newDate;
	}
	
	/**
	 * Set a new place to the seminar.
	 * 
	 * @param newPlace
	 * 		  New place of the seminar
	 */
	public void setPlace(String newPlace) {
		place = newPlace;
	}
	
	/**
	 * Publish this seminar.
	 */
	public void publish() {
		isPublished = true;
	}
}
