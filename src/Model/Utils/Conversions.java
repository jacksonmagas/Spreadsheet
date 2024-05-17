public final class Conversions {
    static String REF_FORMAT = "^\\$\\w*\\d*$";

    static boolean isValidRef(String ref) {
        return ref.matches("^\\$\\w*\\d*$");
    }

    /** get the column number from the cell reference like "$A1" 
     * 
     */
    static int column(String reference) {
        if (!isValidRef(reference)) {
            throw new IllegalArgumentException(reference + " is not a valid Reference.");
        }
        return Integer.parseInt(reference.split("\\$\\w*/")[1]);
        
    }

    // Get the cell




    
    // public static int convertToBase10(String base26) {
    //     if (base26 == null || base26.isEmpty()) {
    //         throw new IllegalArgumentException("Input string must not be null or empty");
    //     }

    //     int result = 0;
    //     int length = base26.length();

    //     for (int i = 0; i < length; i++) {
    //         char c = base26.charAt(i);
    //         if (c < 'A' || c > 'Z') {
    //             throw new IllegalArgumentException("Input string must contain only uppercase alphabetic characters (A-Z)");
    //         }
    //         int value = c - 'A' + 1;
    //         result = result * 26 + value;
    //     }

    //     return result;
    // }
    


    

    // // TODO: Written by GPT - TEST!!!!!!
    // private String columnToString() {
    //     StringBuilder result = new StringBuilder();
        
    //     while (column > 0) {
    //         int remainder = column % 26;
    //         char digit = (char) ('A' + remainder);
    //         result.append(digit);
    //         column /= 26;
    //     }

    //     return result.reverse().toString();
    // }
}