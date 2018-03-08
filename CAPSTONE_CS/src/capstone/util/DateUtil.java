package capstone.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling dates.
 * @author Ramon
 *
 */
public class DateUtil {

	/** The date pattern that is used for conversion. Change as you wish? */
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	
	/** The date formatter. */
	private static final DateTimeFormatter DATE_FORMATTER =
			DateTimeFormatter.ofPattern(DATE_PATTERN);
	
	/**
	 * Returns the given date as a well formatted string. The above defined 
	 * {@link DateUtil#DATE_PATTERN} is used.
	 */
	public static String format(LocalDate date){
		
		if(date == null){
			return null;
		}
		return DATE_FORMATTER.format(date);
	}
	
	public static LocalDate parse(String dateString){
		
		try{
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		}catch (DateTimeParseException e){
			return null;
		}
	}
	
	/**
	 * Checks the string whether it is a valid state.
	 */
	public static boolean validDate(String dateString){
		
		// Try to parse the string
		return DateUtil.parse(dateString) != null;
	}
}
