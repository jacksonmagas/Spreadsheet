package Model;

import static java.lang.Double.parseDouble;

import Model.Utils.Conversions;
import Model.Utils.Coordinate;
import Model.Utils.ITerm;
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
        defaultFormat = new CellFormat();
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

        }

        private List<Token> tokenize(String input) {
            List<Token> tokens = new ArrayList<>();
            for (String tok: input.split(" ")) {
                if (operators.contains(tok)) {
                    tokens.add(new Token(TokenType.operator, tok));
                } else if (functions.contains(tok)) {
                    tokens.add(new Token(TokenType.function, tok));
                } else if (Conversions.isValidRef(tok)) {
                    tokens.add(new Token(TokenType.reference, tok));
                } else {
                    try {
                        double doubleVal = Double.parseDouble(tok);
                        tokens.add(new Token(TokenType.number, Double.toString(doubleVal)));
                    } catch (NumberFormatException e) {
                        tokens.add(new Token(TokenType.string, tok));
                    }
                }
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
            term.recalculate();
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
