package gda2;

public class StringHelper {
	
	
	/**
	 * Determines whether the input text is valid
	 * @param text the input text to determine if valid
	 * @return returns true if the string is valid, false if not
	 */
	public static boolean isStringValid(String text) {
		if(text.contains(" ") || text.contains("/") || text.contains("\\") ) {
			return false;// you are not allowed to use spaces or slashes
		}
		return true;
	}
	
	/**
	 * Capitalizes or decapitalizes the string array
	 * @param array an array of type string that is an input
	 * @param isGoingToBeUpperCased Uppercases them or not
	 * @return
	 */
	public static String[] caseifyAllInToUpper(String[] array, boolean isGoingToBeUpperCased) {
		
		for(int i = 0; i < array.length; i++) {
			if(isGoingToBeUpperCased == true) {
				array[i] = array[i].toUpperCase();
			} else {
				array[i] = array[i].toLowerCase();
			}
		}
		return array;
	}
	
}
