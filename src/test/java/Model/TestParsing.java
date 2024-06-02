package Model;

import Model.Utils.EmptyTerm;
import Model.Utils.StringTerm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestParsing {

    @Test
    public void testString() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();

        Assertions.assertEquals(parser.parse("hello world"), new StringTerm("hello world"));
        Assertions.assertEquals(parser.parse("\"hello world\""),
            new StringTerm("hello world"));
        // plaintext of the string should include the escaped quotes
        Assertions.assertNotEquals(parser.parse("\"hello world\"").toString(),
            new StringTerm("hello world").toString());
        Assertions.assertEquals(parser.parse("\"\""), new StringTerm(""));
        Assertions.assertEquals(parser.parse("\"  \""), new StringTerm("  "));
    }

    @Test
    public void testEmpty() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();
        Assertions.assertEquals(parser.parse(""), new EmptyTerm());
    }

}
