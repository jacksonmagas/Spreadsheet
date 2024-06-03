package Model;

import Model.Utils.BiOperatorExpression;
import Model.Utils.Conversions;
import Model.Utils.Coordinate;
import Model.Utils.EmptyTerm;
import Model.Utils.ErrorTerm;
import Model.Utils.FunctionExpression;
import Model.Utils.FunctionExpression.FunctionType;
import Model.Utils.ITerm;
import Model.Utils.NumberTerm;
import Model.Utils.ParenExpression;
import Model.Utils.ReferenceExpression;
import Model.Utils.StringTerm;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javafx.util.Pair;

public class Spreadsheet implements ISpreadsheet {
    private final HashMap<Coordinate, SpreadsheetCell> cells;
    private final CellFormat defaultFormat;
    private final HashMap<Integer, CellFormat> rowDefaults;
    private final HashMap<Integer, CellFormat> columnDefaults;

    public Spreadsheet() {
        this.cells = new HashMap<>();
        // TODO implement methods for setting default color/font/etc for a row/col
        rowDefaults = new HashMap<>();
        columnDefaults = new HashMap<>();
        // TODO set default formatting options
        defaultFormat = new CellFormat(1, 2, 3, 4, 5, 6, 7, 8);
    }

    @Override
    public ICell getCell(Coordinate coordinate) {
        if (!cells.containsKey(coordinate)) {
            cells.put(coordinate, new SpreadsheetCell(coordinate));
        }
        return cells.get(coordinate);
    }

    // TODO is this used anywhere?
    @Override
    public UUID getId() {
        return null;
    }

    /**
     * Update the sheet based on a list of cells and new data for those cells
     * @param updates A list of coordinate, term pairs to update the sheet with
     * Jackson Magas
     */
    @Override
    public void updateSheet(List<Pair<Coordinate, ITerm>> updates) {
        for (Pair<Coordinate, ITerm> pair : updates) {
            getCell(pair.getKey()).updateCell(pair.getValue());
        }
    }

    /**
     * Class implementing formula parsing for this spreadsheet including looking up references
     */
    class FormulaParser {
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
        }

        public ITerm parse(String formula) {
            List<Token> tokens;
            try {
                tokens = tokenize(formula);
            } catch (ParseException e) {
                return new ErrorTerm(formula);
            }

            if (tokens.isEmpty()) {
                return new EmptyTerm();
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
                    }
                    return new BiOperatorExpression(token.strValue,
                        parse(tokens.subList(0, operatorIndex)),
                            parse(tokens.subList(operatorIndex + 1, tokens.size())));
                }
                ++operatorIndex;
            }
            throw new ParseException("Illegal leading value", 0);
        }

        private List<List<Token>> splitByCommas(List<Token> tokens) {
            List<List<Token>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            int i = 0;
            for (Token token : tokens) {
                if (token.type == TokenType.comma) {
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
            for (char c : input.toCharArray()) {
                if (c == ' ' && !tok.startsWith("\"")) {
                    ++idx;
                    continue;
                }
                tok += c;
                if (operators.contains(tok)) {
                    // check if + or - is part of a number
                    if (!("+-".contains(tok) && tokens.isEmpty()
                        || (!tokens.isEmpty() && tokens.getLast().type != TokenType.number))) {
                        tokens.add(new Token(TokenType.operator, tok));
                        tok = "";
                    }
                } else if (functions.contains(tok)) {
                    tokens.add(new Token(TokenType.function, tok));
                    tok = "";
                } else if (Conversions.isValidRef(tok)) {
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
                            tokens.add(new Token(TokenType.string, tok));
                            tok = "";
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
        private final Set<ICellListener> listeners;
        private ITerm term;
        private CellFormat format;

        SpreadsheetCell(Coordinate coordinate) {
            this.coordinate = coordinate;
            this.listeners = new HashSet<>();
            this.format = initFormat();
        }

        private CellFormat initFormat() {
            if (rowDefaults.containsKey(coordinate.getRow())) {
                return rowDefaults.get(coordinate.getRow());
            } else
                return columnDefaults.getOrDefault(coordinate.getColumn(), defaultFormat);
        }

        @Override
        public void updateCell(ITerm data) {
            term = data;
            if (term != null) {
                term.recalculate();
            }
        }


        @Override
        public Coordinate getCoordinate() {
            return coordinate;
        }

        @Override
        public ITerm getData() {
            return term;
        }

        @Override
        public CellFormat getFormatting() {
            return format;
        }

        @Override
        public boolean isEmpty() {
            return term == null;
        }

        /**
         * When a cell this depends on changes recalculate this
         * cell and all cells that depend on it
         * Jackson Magas
         */
        @Override
        public void handleUpdate() {
            term.recalculate();
            notifyListeners();
        }

        /**
         * Check if this cell appears in its dependency tree
         * @param source the Coordinate of the source cell
         * @return true if this cell depends on itself
         * Jackson Magas
         */
        @Override
        public boolean circularReference(ICell source) {
            for (ICellListener listener : listeners) {
                if (listener == source) {
                    return true;
                }
                if (listener.circularReference(source)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public void registerListener(ICellListener listener) {
            this.listeners.add(listener);
        }

        @Override
        public void notifyListeners() {
            if (!circularReference(this)) {
                for (ICellListener listener : listeners) {
                    listener.handleUpdate();
                }
            }
        }

        @Override
        public void setFormatting(CellFormat formatting) {
            this.format = formatting;
        }
    }
}
