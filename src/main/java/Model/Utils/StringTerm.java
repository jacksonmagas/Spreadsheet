package Model.Utils;

import Model.Spreadsheet.FormulaParser.Token;

public class StringTerm implements ITerm {
    String value;

    public StringTerm(String value) {
        this.value = value;
    }

    @Override
    public String getResult() {
        return value;
    }
    
    public String toString() {
        return value;
    }

    @Override
    public void recalculate() {

    }
}
