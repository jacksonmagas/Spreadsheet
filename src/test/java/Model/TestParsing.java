package Model;

import Model.Utils.BiOperatorExpression;
import Model.Utils.Coordinate;
import Model.Utils.EmptyTerm;
import Model.Utils.ErrorTerm;
import Model.Utils.FunctionExpression;
import Model.Utils.ITerm;
import Model.Utils.NumberTerm;
import Model.Utils.ParenExpression;
import Model.Utils.RangeExpression;
import Model.Utils.ReferenceExpression;
import Model.Utils.StringTerm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test that parsing works properly for all types of input.
 * Jackson Magas
 */
public class TestParsing {
    @Test
    public void testString() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();

        Assertions.assertEquals(parser.parse("hello"), new StringTerm("hello"));
        Assertions.assertEquals(parser.parse("\"hello\""), new StringTerm("hello"));
        Assertions.assertEquals(parser.parse("\"hello world\""),
            new StringTerm("hello world"));
        // plaintext of the string should include the escaped quotes
        Assertions.assertNotEquals(parser.parse("\"hello world\"").toString(),
            new StringTerm("hello world").toString());
        Assertions.assertEquals(parser.parse("\"\""), new StringTerm(""));
        Assertions.assertEquals(parser.parse("\"  \""), new StringTerm("  "));
        // test internal quotes
        Assertions.assertInstanceOf(StringTerm.class, parser.parse(("\"he said \\\"hello\\\"\"")));
        Assertions.assertEquals(parser.parse("\"he said \\\"hello\\\"\""),
            new StringTerm("he said \"hello\""));
    }

    @Test
    public void testEmpty() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();
        Assertions.assertEquals(parser.parse(""), new EmptyTerm());
    }

    @Test
    public void testNumber() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();

        Assertions.assertEquals(parser.parse("0"), new NumberTerm(0));
        Assertions.assertEquals(parser.parse("-10"), new NumberTerm(-10));
        Assertions.assertEquals(parser.parse("0.0501"), new NumberTerm(0.0501));
        Assertions.assertEquals(parser.parse("0.0.1"), new ErrorTerm("0.0.1"));
    }

    @Test
    public void testParentheses() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();

        Assertions.assertEquals(parser.parse("=(\"hello\")"),
            new ParenExpression(new StringTerm("hello")));
        ITerm first = parser.parse("=(-5412.02)");
        ITerm second = new ParenExpression(new NumberTerm(-5412.02));
        Assertions.assertEquals(first,
            second);
    }

    @Test
    public void testFunctions() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();

        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=SUM(1, 0)"));
        Assertions.assertInstanceOf(ErrorTerm.class, parser.parse("=SUM"));
        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=SUM(0)"));
        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=AVG(SUM(5, 4, 3), 12, MAX(10, 3, 5))"));
    }

    @Test
    public void testOperators() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();
        String valueError = "#VALUE!";

        // test +
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 + 2"));
        Assertions.assertEquals("3", parser.parse("= 1 + 2").getResult());
        Assertions.assertEquals(valueError, parser.parse("= 1 + \"hello\"").getResult());
        Assertions.assertEquals(valueError, parser.parse("= \"a\" + \"b\"").getResult());
        // test -
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 - 2"));
        Assertions.assertEquals("-1", parser.parse("= 1 - 2").getResult());
        Assertions.assertEquals(valueError, parser.parse("= 1 - \"hello\"").getResult());
        Assertions.assertEquals(valueError, parser.parse("= \"a\" - \"b\"").getResult());
        // test *
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 * 2"));
        Assertions.assertEquals("2", parser.parse("= 1 * 2").getResult());
        Assertions.assertEquals(valueError, parser.parse("= 1 * \"hello\"").getResult());
        Assertions.assertEquals(valueError, parser.parse("= \"a\" * \"b\"").getResult());
        // test /
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 / 2"));
        Assertions.assertEquals("0.5", parser.parse("= 1 / 2").getResult());
        Assertions.assertEquals("#DIV/0!", parser.parse("= 1 / 0").getResult());
        Assertions.assertEquals(valueError, parser.parse("= 1 / \"hello\"").getResult());
        Assertions.assertEquals(valueError, parser.parse("= \"a\" / \"b\"").getResult());
        // test <
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 < 2"));
        Assertions.assertEquals("1", parser.parse("= 1 < 2").getResult());
        Assertions.assertEquals("0", parser.parse("= 2 < 1").getResult());
        Assertions.assertEquals(valueError, parser.parse("= 1 < \"hello\"").getResult());
        Assertions.assertEquals(valueError, parser.parse("= \"a\" < \"b\"").getResult());
        // test >
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 > 2"));
        Assertions.assertEquals("0", parser.parse("= 1 > 2").getResult());
        Assertions.assertEquals("1", parser.parse("= 2 > 1").getResult());
        Assertions.assertEquals(valueError, parser.parse("= 1 > \"hello\"").getResult());
        Assertions.assertEquals(valueError, parser.parse("= \"a\" > \"b\"").getResult());
        // test =
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 = 2"));
        Assertions.assertEquals("0", parser.parse("= 1 = 2").getResult());
        Assertions.assertEquals("1", parser.parse("= 1 = 1").getResult());
        Assertions.assertEquals(valueError, parser.parse("= 1 = \"hello\"").getResult());
        Assertions.assertEquals("0", parser.parse("= \"a\" = \"b\"").getResult());
        Assertions.assertEquals("1", parser.parse("= \"a\" = \"a\"").getResult());
        // test <>
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 <> 2"));
        Assertions.assertEquals("0", parser.parse("= 1 <> 1").getResult());
        Assertions.assertEquals("1", parser.parse("= 1 <> 2").getResult());
        Assertions.assertEquals(valueError, parser.parse("=\"hello\"<>1").getResult());
        Assertions.assertEquals("1", parser.parse("= \"a\" <> \"b\"").getResult());
        Assertions.assertEquals("0", parser.parse("= \"a\" <> \"a\"").getResult());
        // test &
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 & 2"));
        Assertions.assertEquals("1", parser.parse("= 1 & 2").getResult());
        Assertions.assertEquals("0", parser.parse("= 1 & 0").getResult());
        Assertions.assertEquals(valueError, parser.parse("= 1 & \"hello\"").getResult());
        // test |
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 | 2"));
        Assertions.assertEquals("1", parser.parse("= 1 | 2").getResult());
        Assertions.assertEquals("1", parser.parse("= 1 | 0").getResult());
        Assertions.assertEquals("0", parser.parse("= 0 | 0").getResult());
        Assertions.assertEquals(valueError, parser.parse("= 1 | \"hello\"").getResult());
        // assure : must be a range and check that outside a function it gives value error
        Assertions.assertInstanceOf(ErrorTerm.class, parser.parse("= 1:2"));
        Assertions.assertEquals(valueError, parser.parse("= $a1:$b2").getResult());
    }

    /**
     * Test parsing and basic functionality for references
     * Tests for references properly updating and circular references are in CellTests
     * Jackson Magas
     */
    @Test
    public void testReferenceParsing() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();
        spreadsheet.getCell(new Coordinate(1, 1)).updateCell("1");
        spreadsheet.getCell(new Coordinate(2, 4)).updateCell("2");
        spreadsheet.getCell(new Coordinate(3, 26)).updateCell("3");
        spreadsheet.getCell(new Coordinate(123, 27)).updateCell("4");

        Assertions.assertInstanceOf(ReferenceExpression.class, parser.parse("=$A1"));
        Assertions.assertEquals("1", parser.parse("=$A1").getResult());
        Assertions.assertInstanceOf(ReferenceExpression.class, parser.parse("=$d2"));
        Assertions.assertEquals("2", parser.parse("=$d2").getResult());
        Assertions.assertInstanceOf(ReferenceExpression.class, parser.parse("=$Z3"));
        Assertions.assertEquals("3", parser.parse("=$Z3").getResult());
        Assertions.assertInstanceOf(ReferenceExpression.class, parser.parse("=$AA123"));
        Assertions.assertEquals("4", parser.parse("=$AA123").getResult());
    }

    @Test
    public void testRanges() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();

        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=SUM($A1:$C3)"));
    }
}
