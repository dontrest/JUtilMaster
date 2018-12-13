package jUtil.util.string;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.IllegalFormatFlagsException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author  DontRest
* @version 1.0
* @since   2018-12-13 
*/

public class StringUtils {
	public static final String EMPTY_STRING = "";
	public static final String DASH = "-";
	private static final Character ZERO_CHAR = '0';
	public static boolean isBlankOrNull(String str){
		return str !=null && "".equals(str);
	}
	public static boolean isNotBlankOrNull(String... str){
		for (String string : str) {
			if(isBlankOrNull(string)){
				return false;
			}
		}
		return true;
	}
	public static boolean isBlankOrNullAll(String... str){
		for(String string : str){
			if(isNotBlankOrNull(string)){
				return false;
			}
		}
		return true;	
	}
	
	public static String getPadZeroLeft(String source, Integer targetLength) {
		// System.out.println(String.format("%05d", 123));
		if (!isBlankOrNull(source)) {
			Integer sourceLength = source.length();
			StringBuilder target = new StringBuilder();
			target.append(source);
			for (int i = sourceLength; i < targetLength; i++) {
				target.insert(0, ZERO_CHAR);
			}
			return target.toString();
		} else {
			return EMPTY_STRING;
		}
	}
	
	public static String concatList(List<String> list, String separator) {
		String result = "";
		int index = 1;

		for (String temp : list) {
			result += temp;
			if (index != list.size()) {
				result += separator;
			}
			index++;
		}

		return result;
	}
	
	/**
	 * function convert character to unicode
	 * 
	 * @param ch
	 * @return
	 */
	public static String stringToUnicode(char ch) {
		if (ch < 0x10) {
			return "\\u000" + Integer.toHexString(ch);
		} else if (ch < 0x100) {
			return "\\u00" + Integer.toHexString(ch);
		} else if (ch < 0x1000) {
			return "\\u0" + Integer.toHexString(ch);
		}
		return "\\u" + Integer.toHexString(ch);
	}
	
	/**
	 * function convert unicode to character
	 * 
	 * @param unicode
	 * @return
	 */
	public static char unicodeToString(String unicode) {

		Integer code = Integer.parseInt(unicode.substring(2), 16); // the integer 65 in base 10
		char ch = Character.toChars(code)[0]; // the letter 'A'
		return ch;
	}
	
	/**
	 * check numeric String
	 * 
	 * @param string
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean isNumber(String string) throws IllegalArgumentException {
		if (string != null && !string.equals("")) {
			char chars[] = string.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (!Character.isDigit(chars[i])) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * convert BigDecimal to String by format (ex "99,999,999.99" , "99,999,999")
	 * 
	 * @param BigDecimal
	 *            , String
	 * @return String
	 * @throws IllegalFormatFlagsException
	 */
	public static String bigDecimalToStringFormat(BigDecimal number, String format) {
		if (number != null && !isBlankOrNull(format)) {
			// แปลง format ให้อยู่ในรูปแบบที่สามารถใช้งานกับ class DecimalFormat ได้ เช่น "99,999,999.00" เป็น "##,###,###.00"
			if (format.indexOf('.') != -1) {
				// format มี ทศนิยม
				String[] formats = format.split("\\.");
				if (formats.length == 2) {
					StringBuilder formatBuilder = new StringBuilder();
					formatBuilder.append(formats[0].replaceAll("[0-9]", "#")).append(".").append(formats[1].replaceAll("[0-9]", "0"));
					format = formatBuilder.toString();
				} else {
					throw new IllegalFormatFlagsException(format);
				}
			} else {
				// format ไม่มี ทศนิยม
				format = format.replaceAll("[0-9]", "#");
			}
			// จัด format ให้ BigDecimal
			DecimalFormat decimalFormat = new DecimalFormat(format);
			return decimalFormat.format(number);
		} else {
			return "";
		}
	}
	
	/**
	 * count String without thai vowel
	 * vovel thai 'ั', 'ิ','ี','ึ','ื','ุ','ู','็','่','้','๊','๋','์','ํ','ฺ'
	 * 
	 * @param String
	 * @return int length of string
	 */
	public static int countAliasLength(String str) {
		int strLen = str.length();
		int specialChLen = 0;
		for (int i = 0; i < strLen; i++) {
			int inputCh = (int) str.charAt(i);
			if ((inputCh > 3635 && inputCh < 3643) || (inputCh > 3654 && inputCh < 3662) || inputCh == 3633) {
				specialChLen++;
			}
		}
		return strLen - specialChLen;
	}
	
	/**
	 * add format to thai person id
	 * 
	 * @param String
	 *            thai person id without (-) ex. 1122399477443
	 * @return เลขบัตร ใน format #-####-#####-##-##
	 */
	public static String formatThaiNationId(String nationId) {

		if (nationId == null || nationId.length() != 13) {
			return null;
		}

		StringBuilder sb = new StringBuilder(17);
		int j = 0;
		for (int i = 0; i < 17; i++) {
			if (i == 1 || i == 6 || i == 12 || i == 15) {
				sb.append(DASH);
			} else {
				sb.append(nationId.substring(j, j + 1));

				j++;
			}
		}
		return sb.toString();
	}
	/**
	 * remove format thai person id
	 * 
	 * @param idCard
	 * @return String
	 */
	public static String noFormatIdCard(String idCard) {
		if (isBlankOrNull(idCard) || idCard.length() != 17) {
			return idCard;
		}
		return idCard.replaceAll("[-_]", "");
	}
	
	/**
	 * set format to cardStr.
	 * 
	 * @param cardStr
	 * @param format ex visa ("#### #### #### ####")
	 * @return
	 */
	public static String formatCard(String cardStr,String format) {
		if(isBlankOrNull(cardStr)){
			return cardStr;
		}
		String newString = "";
		char[] chrArr = format.toCharArray();
		int index = 0;
		for(char chr : chrArr){
			if(chr == '#'){
				newString += cardStr.charAt(index++);
			}else{
				newString += chr;
			}
		}
		return newString;
	}
	
	/**
	 * check email format
	 * @param email 
	 * @return boolean 
	 */
	public static boolean checkFormatEmail(String email){
		boolean result = true;
		String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		Matcher matcher =  pattern.matcher(email);
		return matcher.matches();

	}
	
	/**
	 * check thai person id format
	 * @param personId
	 * @return boolean 
	 */
	public static boolean checkDigitPersonId(String personId){
		if(personId.length() != 13){
			return false;
		}
		String digit = personId.substring(0, 12);
		String lastDigit = personId.substring(personId.length() - 1);
		try{
			Long id = Long.parseLong(digit);
			
			long base = 100000000000l; //สร้างตัวแปร เพื่อสำหรับให้หารเพื่อเอาหลักที่ต้องการ
	        int basenow; //สร้างตัวแปรเพื่อเก็บค่าประจำหลัก
	        int sum = 0; //สร้างตัวแปรเริ่มตัวผลบวกให้เท่ากับ 0
	        for(int i = 13; i > 1; i--) { //วนรอบตั้งแต่ 13 ลงมาจนถึง 2
	            basenow = (int)Math.floor(id/base); //หาค่าประจำตำแหน่งนั้น ๆ
	            id = id - basenow*base; //ลดค่า id ลงทีละหลัก
	            sum += basenow*i; //บวกค่า sum ไปเรื่อย ๆ ทีละหลัก
	            base = base/10; //ตัดค่าที่ใช้สำหรับการหาเลขแต่ละหลัก
	        }
	        int checkbit = (11 - (sum%11))%10; //คำนวณค่า checkbit
	        return ((checkbit+"").equals(lastDigit));
		}catch(Exception ex){
			return false;
		}
	}
}
