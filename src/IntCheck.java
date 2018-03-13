public class IntCheck {
	public static boolean isInteger(String str) {
		if (str == null) {
	        return false;
	   }
	   int length = str.length();
	   if (length == 0) {
	        return false;
	   }
		
    	for (int i = 0; i < length; i++) {
      	char c = str.charAt(i);
      	if (c < '0' || c > '9') {
         	return false;
        	}
    	}
   
		return true;
	}
}