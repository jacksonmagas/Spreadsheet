package Model.Utils;

import java.util.regex.Pattern;

import Model.Utils.Function.FunctionOperation;

import java.util.regex.Matcher;
public final class Conversions {

    /**
     * Check if the string is a reference of the proper form
     * @param ref the string reference to a cell in the form "$A1" for first column first row
     * @return True if the string is a reference of the proper form
     */
    public static boolean isValidRef(String ref) {
        Pattern validRef = Pattern.compile("^\\$\\[a-zA-Z]+\\d+$");
        return validRef.matcher(ref).matches();
    }


    /**
     * Get the row number of the reference string.
     * @param reference the string reference to a cell in the form "$A1" for first column first row
     * @throws IllegalArgumentException if the string is not a reference
     * @return The 1 indexed row number
     */
    public static int row(String reference) {
        if (!isValidRef(reference)) {
            throw new IllegalArgumentException(reference + " is not a valid Reference.");
        }
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(reference);
        if (m.matches()) {
            String rowPart = m.group(1);
            return Integer.parseInt(rowPart);
        } else {
            throw new IllegalStateException("Valid reference didn't match any numbers");
        }

    }

    /**
     * Get the column number of the reference string.
     * @param reference the string reference to a cell in the form "$A1" for first column first row
     * @throws IllegalArgumentException if the string is not a reference
     * @return The 1 indexed column number
     */
    public static int column(String reference) {
        if (!isValidRef(reference)) {
            throw new IllegalArgumentException(reference + " is not a valid Reference.");
        }
        Pattern p = Pattern.compile("([a-zA-Z]+)");
        Matcher m = p.matcher(reference);
        if (m.matches()) {
            // From $AbC1 gets ['A', 'B', 'C']
            char[] columnPart = m.group(1).toUpperCase().toCharArray();
            int column = 0;
            int multiplier = 1;
            // loop in reverse order from least to most significant letter
            for (int i = columnPart.length - 1; i >= 0; --i) {
                // convert ascii letters to their alphabet position, so a=1, z=26
                int val = (int) columnPart[i] - 64;
                column += multiplier * val;
                multiplier *= 26;
            }
            return column;
        } else {
            throw new IllegalStateException("How did this happen");
        }
    }

    public static String columnToString(int column) {
        StringBuilder columnString = new StringBuilder();
        while (column > 0) {
            int remainder = column % 26;
            char digit = (char) (remainder + 'A');
            columnString.insert(0, digit);
            column = column / 26;
        }
        return columnString.toString();
    }

    static FunctionOperation stringToOp(String s) {
        switch (s) {
            case "+":
                return FunctionOperation.ADD;
            case "-":
                return FunctionOperation.SUB;
            case "*":
                return FunctionOperation.MULT;
            case "/":
                return FunctionOperation.DIV;
            case "<":
                return FunctionOperation.LESSTHAN;
            case ">":
                return FunctionOperation.GREATERTHAN;
            case "=":
                return FunctionOperation.EQUAL;
            case "<>":
                return FunctionOperation.NOTEQUAL;
            case "&":
                return FunctionOperation.AND;
            case "|":
                return FunctionOperation.OR;
            case ":":
                return FunctionOperation.RANGE;
            default:
                throw new IllegalArgumentException("Invalid function operation: \'" + s + "\'");
        }
    }

    
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