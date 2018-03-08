package capstone.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class CACIFile {
	
	private final StringProperty fileName;
	private final StringProperty fileLocation;
	private final StringProperty street;
	private final IntegerProperty postalCode;
	private final StringProperty city;
	private final ObjectProperty<LocalDate> dateCreated;

	/**
	 * Default constructor
	 */
	public CACIFile(){
		
		this(null, null);
	}
}
