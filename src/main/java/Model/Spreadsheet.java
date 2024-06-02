package Model;

import Model.Utils.Coordinate;
import Model.Utils.ITerm;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javafx.util.Pair;

public class Spreadsheet implements ISpreadsheet {
    private final HashMap<Coordinate, SpreadsheetCell> cells;

    public Spreadsheet() {
        this.cells = new HashMap<>();
    }

    @Override
    public ICell getCell(Coordinate coordinate) {
        if (cells.containsKey(coordinate)) {
            return cells.get(coordinate);
        } else {
            cells.put(coordinate, new SpreadsheetCell(coordinate));
            return cells.get(coordinate);
        }
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public void updateSheet(List<Pair<Coordinate, ITerm>> update) {

    }

    /**
     * Class implementing formula parsing for this spreadsheet including looking up references
     */
    class FormulaParser {

    }

    /**
     * Cell class for this spreadsheet
     */
    private class SpreadsheetCell implements ICell {
        private final Coordinate coordinate;
        private final Set<ICellListener> listeners;
        private ITerm term;
        private CellFormat format;

        SpreadsheetCell(Coordinate coordinate) {
            this.coordinate = coordinate;
            this.listeners = new HashSet<>();
            // TODO initialize format
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
