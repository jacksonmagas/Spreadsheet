package Model;

import Model.Expressions.BiOperatorExpression;
import Model.Expressions.CircularErrorTerm;
import Model.Utils.CellFormatDetails;
import Model.Utils.Conversions;
import Model.Utils.Coordinate;
import Model.Expressions.EmptyTerm;
import Model.Expressions.ErrorTerm;
import Model.Expressions.FunctionExpression;
import Model.Expressions.FunctionExpression.FunctionType;
import Model.Expressions.ITerm;
import Model.Expressions.ITerm.ResultType;
import Model.Expressions.NumberTerm;
import Model.Expressions.ParenExpression;
import Model.Expressions.RangeExpression;
import Model.Expressions.ReferenceExpression;
import Model.Expressions.StringTerm;
import Model.Utils.SpreadsheetSliceView;
import Model.Utils.SpreadsheetSliceView.Direction;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javafx.util.Pair;

public class Spreadsheet implements ISpreadsheet {
    private final HashMap<Coordinate, SpreadsheetCell> cells;
    private final Set<ISpreadsheetListener> listeners;
    private final CellFormatDetails defaultFormat;
    private final HashMap<Integer, CellFormatDetails> rowDefaults;
    private final HashMap<Integer, CellFormatDetails> columnDefaults;
    private final FormulaParser parser;
    private boolean updatingFromServer;
    int numRows;
    int numColumns;

    public Spreadsheet() {
        this.cells = new HashMap<>();
        // TODO implement methods for setting default color/font/etc for a row/col
        rowDefaults = new HashMap<>();
        columnDefaults = new HashMap<>();
        this.listeners = new HashSet<>();
        // TODO set default formatting options
        defaultFormat = new CellFormatDetails();

        this.parser = new FormulaParser();
        this.updatingFromServer = false;
        numRows = 0;
        numColumns = 0;
    }

    @Override
    public ICell getCell(Coordinate coordinate) {
        if (!cells.containsKey(coordinate)) {
            cells.put(coordinate, new SpreadsheetCell(coordinate));
        }
        numRows = Math.max(numRows, coordinate.getRow());
        numColumns = Math.max(numColumns, coordinate.getColumn());
        return cells.get(coordinate);
    }

    @Override
    public int numRows() {
        return numRows;
    }

    @Override
    public int numColumns() {
        return numColumns;
    }

    @Override
    public List<ICell> getRow(int rowNum) {
        return new SpreadsheetSliceView(this, Direction.row, rowNum);
    }

    @Override
    public List<ICell> getColumn(int colNum) {
        return new SpreadsheetSliceView(this, Direction.column, colNum);
    }

    /**
     * Update the sheet based on a list of cells and new data for those cells
     * @param updates A list of coordinate, text pairs to update the sheet with
     * Jackson Magas
     */
    @Override
    public void updateSheet(List<Pair<Coordinate, String>> updates) {
        updatingFromServer = true;
        for (Pair<Coordinate, String> pair : updates) {
            getCell(pair.getKey()).updateCell(pair.getValue());
        }
        updatingFromServer = false;
    }

    @Override
    public void registerListener(ISpreadsheetListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(ISpreadsheetListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners(Coordinate coordinate, String update) {
        if (!updatingFromServer) {
            for (ISpreadsheetListener listener : listeners) {
                listener.handleUpdate(coordinate, update);
            }
        }
    }

    /**
     * Class implementing formula parsing for this spreadsheet including looking up references.
     * Jackson Magas
     */
     protected class FormulaParser {
        List<String> operators = Arrays.asList("+", "-", "*", "/", "<", ">", "=", "<>", "&", "|", ":");
        List<String> functions = Arrays.asList("IF", "SUM", "MAX", "MIN", "AVG", "CONCAT", "DEBUG");

        private enum TokenType {
            string, number, operator, function, reference, parenthesis, comma
        }

        /**
         * A token has a type and possibly a value.
         * type        : string value
         * string      : contents of the string
         * number      : numerical value as a string
         * operator    : which operator it is
         * function    : function name
         * reference   : cell location in $A1 format
         * parenthesis : ( or )
         * comma       : ,
         */
        private static class Token {
            TokenType type;
            String strValue;

            Token(TokenType type, String strValue) {
                this.type = type;
                this.strValue = strValue;
            }

            @Override
            public String toString() {
                return strValue;
            }
        }

        public ITerm parse(String formula) {
            if (formula.isEmpty()) {
                return new EmptyTerm();
            }

            List<Token> tokens;
            try {
                tokens = tokenize(formula);
            } catch (ParseException e) {
                return new ErrorTerm(formula);
            }

            // special case of just text or a number
            if (tokens.size() == 1) {
                if (tokens.getFirst().type == TokenType.string) {
                    return new StringTerm(tokens.getFirst().strValue);
                } else if (tokens.getFirst().type == TokenType.number) {
                    return new NumberTerm(Double.parseDouble(tokens.getFirst().strValue));
                } else {
                    return new ErrorTerm(formula);
                }
            }

            // not text or a number must start with =
            if (tokens.getFirst().type != TokenType.operator
                || !tokens.getFirst().strValue.equals("=")) {
                return new ErrorTerm(formula);
            }

            try {
                // already parsed the =
                return parse(tokens.subList(1, tokens.size()));
            } catch (ParseException e) {
                return new ErrorTerm(formula);
            }
        }

        private ITerm parse(List<Token> tokens) throws ParseException {
            if (tokens.isEmpty()) {
                return new EmptyTerm();
            }

            switch (tokens.getFirst().type) {
                case TokenType.function -> {
                    int parenIdx = 1;
                    if (tokens.size() >= 3
                        && tokens.get(parenIdx).type == TokenType.parenthesis
                        && tokens.get(parenIdx).strValue.equals("(")) {
                        if(closeParenIndex(tokens, parenIdx) == tokens.size() - 1){
                            List<List<Token>> args = splitByCommas(tokens.subList(parenIdx + 1, tokens.size() - 1));
                            List<ITerm> parsedArgs = new ArrayList<>();
                            for (List<Token> arg : args) {
                                ITerm parse = parse(arg);
                                parsedArgs.add(parse);
                            }
                            return new FunctionExpression(FunctionType.valueOf(tokens.getFirst().strValue), parsedArgs);
                        } else {
                            throw new ParseException("Illegal tokens after close parenthesis", 0);
                        }
                    } else {
                        throw new ParseException("Function no open parenthesis", 0);
                    }
                }
                case TokenType.string -> {
                    if (tokens.size() == 1) {
                        return new StringTerm(tokens.getFirst().strValue);
                    } else {
                        return parseOperator(tokens);
                    }
                }
                case TokenType.number -> {
                    if (tokens.size() == 1) {
                        return new NumberTerm(Double.parseDouble(tokens.getFirst().strValue));
                    } else {
                        return parseOperator(tokens);
                    }
                }
                case TokenType.operator -> {
                    throw new ParseException("illegal leading operator", 0);
                }
                case TokenType.parenthesis -> {
                    int match = closeParenIndex(tokens, 0);
                    if (match == tokens.size() - 1) {
                        return new ParenExpression(parse(tokens.subList(1, tokens.size() - 1)));
                    } else {
                        return parseOperator(tokens.subList(match + 1, tokens.size()));
                    }
                }
                case TokenType.reference -> {
                    // TODO implement ranges properly
                    if (tokens.size() == 1) {
                        Coordinate referenced = Conversions.stringToCoordinate(tokens.getFirst().strValue);
                        ICell cell = getCell(referenced);
                        return new ReferenceExpression(cell);
                    } else {
                        return parseOperator(tokens);
                    }
                }
                case TokenType.comma -> {
                    throw new ParseException("illegal leading comma", 0);
                }
            }
            throw new ParseException("Reached the end of parse function without parsing", 0);
        }

        private ITerm parseOperator(List<Token> tokens) throws ParseException {
            int operatorIndex = 0;
            for (Token token : tokens) {
                if (token.type == TokenType.operator) {
                    if (operatorIndex == tokens.size() - 1) {
                        throw new ParseException("Nothing after operator", 0);
                    } else if (token.strValue.equals(":")) {
                        if (tokens.size() != 3) {
                            throw new ParseException("Invalid range", 0);
                        }
                        Token left = tokens.get(operatorIndex - 1);
                        Token right = tokens.get(operatorIndex + 1);
                        if (left.type == TokenType.reference
                            && right.type == TokenType.reference
                            && Conversions.stringToCoordinate(left.strValue)
                                .compareTo(Conversions
                                    .stringToCoordinate(right.strValue)) == -1) {
                            return new RangeExpression(Conversions.stringToCoordinate(left.strValue)
                                .getRange(Conversions.stringToCoordinate((right.strValue))).stream()
                                .map(Spreadsheet.this::getCell).toList());
                        }
                    } else {
                        return new BiOperatorExpression(token.strValue,
                            parse(tokens.subList(0, operatorIndex)),
                            parse(tokens.subList(operatorIndex + 1, tokens.size())));
                    }
                }
                ++operatorIndex;
            }
            throw new ParseException("Illegal leading value", 0);
        }

        private List<List<Token>> splitByCommas(List<Token> tokens) {
            List<List<Token>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            int i = 0;
            int parenDepth = 0;
            for (Token token : tokens) {
                if (token.type == TokenType.parenthesis) {
                    parenDepth += Objects.equals(token.strValue, "(") ? 1 : -1;
                }
                if (token.type == TokenType.comma && parenDepth == 0) {
                    ++i;
                    result.add(new ArrayList<>());
                    continue;
                }
                result.get(i).add(token);
            }

            return result;
        }

        private int closeParenIndex(List<Token> tokens, int startIndex) throws ParseException {
            int oParenCount = 0;
            for (; startIndex < tokens.size(); startIndex++) {
                Token token = tokens.get(startIndex);
                if (token.type == TokenType.parenthesis) {
                    if (token.strValue.equals("(")) {
                        oParenCount++;
                    } else if (token.strValue.equals(")")) {
                        oParenCount--;
                    }
                }
                if (oParenCount == 0) {
                    return startIndex;
                }
            }
            throw new ParseException("unclosed parenthesis", 0);
        }

        private List<Token> tokenize(String input) throws ParseException {
            List<Token> tokens = new ArrayList<>();
            if (input.chars().allMatch(Character::isAlphabetic)) {
                tokens.add(new Token(TokenType.string, input));
                return tokens;
            }
            String tok = "";
            int idx = 0;
            boolean escaped = false;
            for (char c : input.toCharArray()) {
                if (c == ' ' && !tok.startsWith("\"")) {
                    ++idx;
                    continue;
                }
                if (c == '\\') {
                    escaped = true;
                }
                tok += c;
                if (operators.contains(tok)) {
                    // check if + or - is part of a number
                    // it is part of a number when it is followed by a number and preceded by
                    // nothing, an operator, a comma, or an open paren
                    if ("+-".contains(tok)
                        && idx < input.length() - 1 && Character.isDigit(input.charAt(idx + 1))
                        && (tokens.isEmpty() || tokens.getLast().type == TokenType.operator
                            || tokens.getLast().type == TokenType.comma
                            || (tokens.getLast().type == TokenType.parenthesis
                                && tokens.getLast().strValue.equals("(")))) {
                        ++idx;
                        continue;
                    }
                    // check if < is part of <>
                    if (tok.equals("<") && idx < input.length() - 1 && input.charAt(idx + 1) == '>') {
                        ++idx;
                        continue;
                    }
                    tokens.add(new Token(TokenType.operator, tok));
                    tok = "";
                } else if (functions.contains(tok)) {
                    tokens.add(new Token(TokenType.function, tok));
                    tok = "";
                } else if (Conversions.isValidRef(tok)) {
                    if (idx < input.length() - 1 && Character.isDigit(input.charAt(idx + 1))) {
                        idx++;
                        continue;
                    }
                    tokens.add(new Token(TokenType.reference, tok));
                    tok = "";
                } else if ("()".contains(tok))  {
                    tokens.add(new Token(TokenType.parenthesis, tok));
                    tok = "";
                } else if (tok.equals(",")) {
                    tokens.add(new Token(TokenType.comma, tok));
                    tok = "";
                } else {
                    try {
                        double doubleVal = Double.parseDouble(tok);
                        if (idx == input.length() - 1
                            || !(Character.isDigit(input.charAt(idx + 1)) || input.charAt(idx + 1) == '.')) {
                            tokens.add(new Token(TokenType.number, tok));
                            tok = "";
                        }
                    } catch (NumberFormatException e) {
                        if (tok.length() > 1 && tok.startsWith("\"") && tok.endsWith("\"")) {
                            if (!escaped) {
                                tokens.add(new Token(TokenType.string, tok));
                                tok = "";
                            } else {
                                escaped = false;
                            }
                        }
                    }
                }
                ++idx;
            }
            if (!tok.isEmpty()) {
                throw new ParseException("Failed to parse", 0);
            }
            return tokens;
        }
    }

    /**
     * Cell class for this spreadsheet
     * Jackson Magas
     */
    private class SpreadsheetCell implements ICell {
        private final Coordinate coordinate;
        private final Set<ICellListener> valueListeners;
        private ITerm term;
        private CellFormatDetails format;

        SpreadsheetCell(Coordinate coordinate) {
            this.coordinate = coordinate;
            this.valueListeners = new HashSet<>();
            this.format = initFormat();
            this.term = new EmptyTerm();
        }

        /**
         * Set the format of this cell to the default format.
         * Prioritizes row format, then column format, then default format.
         * Jackson Magas
         * @return The format of the cell
         */
        private CellFormatDetails initFormat() {
            if (rowDefaults.containsKey(coordinate.getRow())) {
                return rowDefaults.get(coordinate.getRow());
            } else
                return columnDefaults.getOrDefault(coordinate.getColumn(), defaultFormat);
        }

        /**
         * Update this cell with new user input.
         * Registers as a listener to all direct dependencies and
         * Jackson Magas
         * @param data the user input to parse
         */
        @Override
        public void updateCell(String data) {
            term = parser.parse(data);
            try {
                for (Coordinate refLoc : term.references()) {
                    getCell(refLoc).registerListener(this);
                }
            } catch (IllegalStateException e) {
                term = new CircularErrorTerm(this.getPlaintext());
            }
            Spreadsheet.this.notifyListeners(getCoordinate(), data);
            handleValueChange();
        }


        /**
         * Get the location of this cell.
         * Jackson Magas
         * @return the location of this cell
         */
        @Override
        public Coordinate getCoordinate() {
            return coordinate;
        }

        /**
         * Get the result of evaluating this cell.
         * Jackson Magas
         * @return the result of evaluating this cell as a string
         */
        @Override
        public String getData() {
            return term.getResult();
        }

        /**
         * Get the exact input from the user in this cell
         * Jackson Magas
         * @return the user input to this cell
         */
        @Override
        public String getPlaintext() {
            return term.toString();
        }

        /**
         * Get the format object with details about this cell's formatting
         * Jackson Magas
         * @return the format object
         */
        @Override
        public CellFormatDetails getFormatting() {
            return format;
        }

        /**
         * Does this cell have data
         * Jackson Magas
         * @return true if this cell is empty
         */
        @Override
        public boolean isEmpty() {
            return term.isEmpty();
        }

        /**
         * When a cell this depends on changes recalculate this
         * cell and all cells that depend on it
         * Jackson Magas
         */
        @Override
        public void handleValueChange() {
            term.recalculate();
            notifyListeners();
        }

        /**
         * Register the given listener as a listener for updates to this cell.
         * @throws IllegalStateException if this depends on the given listener
         * @param listener the listener to register
         */
        @Override
        public void registerListener(ICellListener listener) {
            if (listener instanceof ICell && this.dependsOn(((ICell) listener).getCoordinate())) {
                throw new IllegalStateException("Circular reference detected");
            } else {
                valueListeners.add(listener);
            }
        }

        @Override
        public void notifyListeners() {
            for (ICellListener listener : valueListeners) {
                listener.handleValueChange();
            }
        }

        @Override
        public void setFormatting(CellFormatDetails formatting) {
            this.format = formatting;
        }

        @Override
        public ResultType dataType() {
            return term.resultType();
        }

        /**
         * Every cell depends on itself
         * @param cellLoc the cell to search the dependency tree for
         * @return true if this cell depends on the given cell
         */
        @Override
        public boolean dependsOn(Coordinate cellLoc) {
            return getCoordinate().equals(cellLoc) || term.dependsOn(cellLoc);
        }
    }
}
