package model.publication;

import java.util.Date;
import java.util.Set;

/**
 * The <code>Book</code> class represents book entities and is subclass of
 *  <code>Publication</code>.
 * <p>
 * Besides attributes provided by <code>Publication</code>, publisherName
 *  and publishPlace is provided by <code>Book</code> class.
 * 
 * @see Publication
 */
public class Book extends Publication {

	private static final long serialVersionUID = 3502967021578272195L;
	private String publisherName;
	private String publishPlace;
	
	/**
	 * Initialises a newly created {@code Book} object with attributes of the passed parameters.
	 * 
	 * @param IDNumber
	 *		  ID number of a {@code Book}, which is the primary key of a {@code Book}, 
	 *		  and is in format of "BK0000".
	 * @param publicationTitle
	 *		  Title of this {@code Book}.
	 * @param authorSet
	 *		  A {@link java.util.Set Set} of author's full names, which may not be recorded in this system.
	 * @param publishDate
	 *		  The date this {@code Book} is published, namely, is uploaded to this system.
	 *		  And is in {@link java.util.Date Date} type.
	 * @param publisherName
	 *		  The publisher's name of this {@code Book}.
	 * @param publishPlace
	 *		  The place where this {@code Book} is published.
	 * @param uploaderUserName
	 *		  The userName of the one who uploads this {@code Book}.
	 */
	public Book(String IDNumber, String publicationTitle, Set<String> authorSet,
			Date publishDate, String publisherName, String publishPlace, String uploaderUserName) {
		super(IDNumber, publicationTitle, authorSet, publishDate, uploaderUserName);
		this.publisherName = publisherName;
		this.publishPlace = publishPlace;
	}
	
	/**
	 * Return publisher's name.
	 * 
	 * @return The name of publisher.
	 */
	public String getPublisherName() {
		return publisherName;
	}
	
	/**
	 * Return the place this {@code Book} is published.
	 * 
	 * @return The place this {@code Book} is published.
	 */
	public String getPublishPlace() {
		return publishPlace;
	}
	
	/**
	 * Set the publisher's name of this {@code Book}.
	 * 
	 * @param newPublisherName
	 * 		  The new publisher's name.
	 */
	public void setPublisherName(String newPublisherName) {
		publisherName = newPublisherName;
	}
	
	/**
	 * Set the place this {@code Book} is published.
	 * 
	 * @param newPublishPlace
	 * 		  The new publishing place.
	 */
	public void setPublishPlace(String newPublishPlace) {
		publishPlace = newPublishPlace;
		
	}
	
}
