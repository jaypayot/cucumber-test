package com.luxbet.qa.framework.utils;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class StringHelper {
	
	private static Logger logger = Logger.getLogger(StringHelper.class);
	
	public static String GenerateMD5For(String StringValue) {
		logger.info("GenerateMD5For("+StringValue+")");
		try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(StringValue.getBytes());
                        
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;			
		} catch ( Exception e) {
			logger.error(e.getMessage());
		}
		
		return null;
	}

	public static String SumOf(String Value1, String Value2) {

		if(Value1 == null)
			return Value2;

		if(Value2 == null)
			return Value1;

		String sum = null;
		Double num1 = null;
		Double num2 = null;
		Boolean isDollarFormatted = false;

		if(Value1.contains("$") || Value2.contains("$"))
			isDollarFormatted = true;

		try {
			num1 = Double.parseDouble(RemoveDollarFrom(Value1));
			num2 = Double.parseDouble(RemoveDollarFrom(Value2));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if(num1 != null && num2 != null) {

			// Format to 2 decimal places
			DecimalFormat df = new DecimalFormat("#.00");
			df.setRoundingMode(RoundingMode.DOWN);

			sum = df.format(num1 + num2);
			if(isDollarFormatted)
				sum = "$"+sum;
		}

		return sum;
	}


	public static String RemoveCharFrom(String StringValue, String Char) {
		//logger.info("RemoveCharFrom("+StringValue+", "+Char+")");
		if(StringValue != null)
			StringValue = StringValue.replace(Char, "");
		
		return StringValue;
	}

	public static String RemoveSpecialCharsFromString(String numberString) {
		String numberStr = RemoveCharFrom(numberString, "$");
		numberStr = RemoveCharFrom(numberStr, ",");
		numberStr = RemoveCharFrom(numberStr, "%");
		return numberStr;
	}

	public static String RemoveDollarFrom(String StringValue) {
		return RemoveCharFrom(StringValue, "$");
	}

	public static int ConvertStringToInteger(String integerString) {
		return Integer.parseInt(integerString);
	}

	/**
	 * This method checks if a price value is thousand separated with commas
	 * @param numberString
	 * @return
	 */
	public static boolean IsThousandSeparated(String numberString) {
		String numberStr = RemoveCharFrom(numberString, "$");
		String actualNumberStr = RemoveCharFrom(numberStr, "%");
		String plainString = RemoveSpecialCharsFromString(numberString);
		int expectedNum = ConvertStringToInteger(plainString);
		String expectedString = String.format("%,d", expectedNum);
		if(actualNumberStr.equals(expectedString)) {
			return true;
		}
		else {
			return false;
		}
	}



    /**
     * This method check if it contains the whole matching word in string
     * @param text
     * @param word
     * @return
     */
    public static boolean containsWord(String text, String word) {
        String REGEX_FIND_WORD = "(?i).*?\\b%s\\b.*?";
        String regex = String.format(REGEX_FIND_WORD, Pattern.quote(word));
        return text.matches(regex);
    }

    /**
     * This method replaces one or more spaces with given character,
     * pass empty string if just want to remove spaces
     * @param text
     * @param splitChar
     * @return
     */
    public static String replaceSpacesWithAnyChar(String text, String splitChar) {
        return text.replaceAll("\\s+", splitChar); //replace space
    }

    /**
     * This method converts string to list,
     * I have used this method at multiple places in spectrum,
     * may come handy at other places as well
     * @param str
     * @param splitChar
     * @return
     */
    public static List<String> splitStringToList(String str, String splitChar) {
        return Arrays.asList(str.split(";"));
    }

    public static String incrementString(String str, int increment ){
        int result = Integer.parseInt(str) + increment;
        return Integer.toString(result);
    }

    /**
     * Check if string is numeric
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

	public static DateTime getDateTimeInTimeZone(String timeZone) {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMMyyyyHH:mm:ss");
		DateTime dateTime = new DateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timeZone)));
		return dateTime.toDateTime();
	}
}
