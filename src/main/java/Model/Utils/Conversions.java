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
        Pattern validRef = Pattern.compile("^\\$[a-zA-Z]+\\d+$", Pattern.MULTILINE);
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
        Pattern p = Pattern.compile("^\\$[a-zA-Z]+(\\d+)$", Pattern.MULTILINE);
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
        Pattern p = Pattern.compile("^\\$([a-zA-Z]+)\\d+$");
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
            return column - 1;
        } else {
            throw new IllegalStateException("How did this happen");
        }
    }

    public static String columnToString(int column) {
        StringBuilder columnString = new StringBuilder();
        while (column >= 0) {
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

    /**
     * Convert a string of the form $A1 to a coordinate object
     * @param input the string to convert to coordinates
     * @return the coordinate object at that string location
     * Jackson Magas
     */
    public static Coordinate stringToCoordinate(String input) {
        return new Coordinate(row(input), column(input));
    }
}