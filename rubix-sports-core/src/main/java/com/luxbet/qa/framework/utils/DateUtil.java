package com.luxbet.qa.framework.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String GetCurrentDateAus() {
		Date date = new Date();
		// Australia date format
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = formatter.format(date);
		
		return currentDate;
	}
	
	public static String GetCurrentDateUS() {
		Date date = new Date();
		// US date format
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String currentDate = formatter.format(date);

		return currentDate;
	}
	
	public static String formatToAus(Date date) {
		// Australia date format
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = formatter.format(date);
		
		return dateString;
	}
	
	public static String formatToUS(Date date) {
		// US date format
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		//DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
		String dateString = formatter.format(date);
		return dateString;
	}
	
	public static String formatToGerman(Date date) {
		// Australia date format
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String dateString = formatter.format(date);
		
		return dateString;
	}
	
	public static boolean isDateFormatAus(String shortDate) {
		Date date = null;
		try {
		    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		    date = df.parse(shortDate);
		} catch (ParseException exp) {
		    exp.printStackTrace();
		}
		return date != null;
	}
	
	public static boolean isDateFormatUS(String shortDate) {
		Date date = null;
		try {
		    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		    //DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
		    date = df.parse(shortDate);
		} catch (ParseException exp) {
		    exp.printStackTrace();
		}
		return date != null;
	}
	
	public static boolean isDateFormatGerman(String shortDate) {
		Date date = null;
		try {
		    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		    date = df.parse(shortDate);
		} catch (ParseException exp) {
		    exp.printStackTrace();
		}
		return date != null;
	}
	
	public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	public static Date getDate() {
		return new Date();
	}
	
	public static String getMonth() {
		 Calendar cal = Calendar.getInstance();
		 String mon = new SimpleDateFormat("MMM").format(cal.getTime());
		 return mon;
	}
	
	/**
	 * This method inputs a descriptive date as a string (eg. 1st January 1970) and converts it to a Date object and returns it.
	 * Added by ashoka on 22/09/2015
	 * @param dateString
	 * @return
	 */
	public static Date parseDate(String dateString){
	    Date date = null;
	    String[] formats = {"d'st' MMMM yyyy","dd'st' MMMM yyyy","d'nd' MMMM yyyy","dd'nd' MMMM yyyy","d'rd' MMMM yyyy","dd'rd' MMMM yyyy","d'th' MMMM yyyy","dd'th' MMMM yyyy"};
	    ParsePosition position = new ParsePosition(0);
	    for (String format : formats) {
	        position.setIndex(0);
	        position.setErrorIndex(-1);
	        date = new SimpleDateFormat(format).parse(dateString, position);
	        if (date != null) {
	            return date;
	        }
	    }
	    return date;
	}
	
	/**
	 * This method formats any date of format yyyy-MM-dd (eg. 1970-01-01) to Date object
	 * Added by ashoka on 22/09/2015
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDate(String dateString) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse(dateString);
		return d;
	}
}
