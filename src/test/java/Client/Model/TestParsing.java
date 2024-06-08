package Client.Model;

import Client.Model.Expressions.BiOperatorExpression;
import Client.Model.Spreadsheet;
import Client.Model.Utils.Coordinate;
import Client.Model.Expressions.EmptyTerm;
import Client.Model.Expressions.ErrorTerm;
import Client.Model.Expressions.FunctionExpression;
import Client.Model.Expressions.ITerm;
import Client.Model.Expressions.NumberTerm;
import Client.Model.Expressions.ParenExpression;
import Client.Model.Expressions.ReferenceExpression;
import Client.Model.Expressions.StringTerm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test that parsing works properly for all types of input.
 * Jackson Magas
 */
public class TestParsing {

    private static final String VALUE_ERROR = "#VALUE!";

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
        Assertions.assertInstanceOf(EmptyTerm.class, parser.parse(""));
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
        spreadsheet.getCell(new Coordinate("$A1")).updateCell("0");
        spreadsheet.getCell(new Coordinate("$A2")).updateCell("1");
        spreadsheet.getCell(new Coordinate("$A3")).updateCell("2");
        spreadsheet.getCell(new Coordinate("$A4")).updateCell("3");
        spreadsheet.getCell(new Coordinate("$A5")).updateCell("\"hello\"");
        spreadsheet.getCell(new Coordinate("$A6")).updateCell("\"foobar\"");

        // test parsing
        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=SUM(1, 0)"));
        Assertions.assertInstanceOf(ErrorTerm.class, parser.parse("=SUM"));
        Assertions.assertInstanceOf(ErrorTerm.class, parser.parse("=SUM)"));
        Assertions.assertInstanceOf(ErrorTerm.class, parser.parse("=SUM("));
        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=SUM(0)"));
        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=SUM($A1:$A5)"));
        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=SUM($A1:$A4, $A5)"));
        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=AVG(SUM(5, 4, 3), 12, MAX(10, 3, 5))"));
        // test IF
        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=IF(1, 2, 3)"));
        Assertions.assertEquals("2", parser.parse("=IF(1, 2, 3)").getResult());
        Assertions.assertEquals("3", parser.parse("=IF(0, 2, 3)").getResult());
        Assertions.assertEquals("hello", parser.parse("=IF(0, 2, \"hello\")").getResult());
        Assertions.assertEquals("2", parser.parse("=IF($A1:$A3)").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=IF(\"test\", 2, 3)").getResult());
        // test SUM
        Assertions.assertEquals("3", parser.parse("=SUM(0, 1, 2)").getResult());
        Assertions.assertEquals("6", parser.parse("=SUM($A1:$A4)").getResult());
        Assertions.assertEquals("6", parser.parse("=SUM($A2, $A3, $A4)").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=SUM(0, 2, \"hello\")").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=SUM($A1:$A5)").getResult());
        // test MAX
        Assertions.assertEquals("3.5", parser.parse("=MAX(0, -1, 2, 3.5, 3.5)").getResult());
        Assertions.assertEquals("2", parser.parse("=MAX($A1:$A3)").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=MAX($A2, $A3, $A4, $A5)").getResult());
        // test MIN
        Assertions.assertEquals("-1", parser.parse("=MIN(0, -1, 2, 3.5, 3.5)").getResult());
        Assertions.assertEquals("0", parser.parse("=MIN($A1:$A3)").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=MIN($A2, $A3, $A4, $A5)").getResult());
        // test AVG
        Assertions.assertEquals("1", parser.parse("=AVG(0, 1, 2)").getResult());
        Assertions.assertEquals("1.5", parser.parse("=AVG($A1:$A4)").getResult());
        Assertions.assertEquals("2", parser.parse("=AVG($A2, $A3, $A4)").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=AVG(0, 2, \"hello\")").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=AVG($A1:$A5)").getResult());
        // test CONCAT
        Assertions.assertEquals("hello world", parser.parse("=CONCAT(\"hello\", \" \", \"world\")").getResult());
        Assertions.assertEquals("hello world", parser.parse("=CONCAT($A5, \" \", \"world\")").getResult());
        Assertions.assertEquals("hellofoobar", parser.parse("=CONCAT($A5:$A6)").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=CONCAT(1, \"foo\", -5)").getResult());
        // test DEBUG
        Assertions.assertEquals("1", parser.parse("=DEBUG(1)").getResult());
        Assertions.assertEquals("1", parser.parse("=DEBUG($A2)").getResult());
        Assertions.assertEquals("foobar", parser.parse("=DEBUG($A6)").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=DEBUG(1, 2)").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=DEBUG($A1:$A5)").getResult());
    }

    @Test
    public void testOperators() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();

        // test +
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 + 2"));
        Assertions.assertEquals("3", parser.parse("= 1 + 2").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= 1 + \"hello\"").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= \"a\" + \"b\"").getResult());
        // test -
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 - 2"));
        Assertions.assertEquals("-1", parser.parse("= 1 - 2").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= 1 - \"hello\"").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= \"a\" - \"b\"").getResult());
        // test *
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 * 2"));
        Assertions.assertEquals("2", parser.parse("= 1 * 2").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= 1 * \"hello\"").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= \"a\" * \"b\"").getResult());
        // test /
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 / 2"));
        Assertions.assertEquals("0.5", parser.parse("= 1 / 2").getResult());
        Assertions.assertEquals("#DIV/0!", parser.parse("= 1 / 0").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= 1 / \"hello\"").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= \"a\" / \"b\"").getResult());
        // test <
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 < 2"));
        Assertions.assertEquals("1", parser.parse("= 1 < 2").getResult());
        Assertions.assertEquals("0", parser.parse("= 2 < 1").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= 1 < \"hello\"").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= \"a\" < \"b\"").getResult());
        // test >
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 > 2"));
        Assertions.assertEquals("0", parser.parse("= 1 > 2").getResult());
        Assertions.assertEquals("1", parser.parse("= 2 > 1").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= 1 > \"hello\"").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= \"a\" > \"b\"").getResult());
        // test =
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 = 2"));
        Assertions.assertEquals("0", parser.parse("= 1 = 2").getResult());
        Assertions.assertEquals("1", parser.parse("= 1 = 1").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= 1 = \"hello\"").getResult());
        Assertions.assertEquals("0", parser.parse("= \"a\" = \"b\"").getResult());
        Assertions.assertEquals("1", parser.parse("= \"a\" = \"a\"").getResult());
        // test <>
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 <> 2"));
        Assertions.assertEquals("0", parser.parse("= 1 <> 1").getResult());
        Assertions.assertEquals("1", parser.parse("= 1 <> 2").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("=\"hello\"<>1").getResult());
        Assertions.assertEquals("1", parser.parse("= \"a\" <> \"b\"").getResult());
        Assertions.assertEquals("0", parser.parse("= \"a\" <> \"a\"").getResult());
        // test &
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 & 2"));
        Assertions.assertEquals("1", parser.parse("= 1 & 2").getResult());
        Assertions.assertEquals("0", parser.parse("= 1 & 0").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= 1 & \"hello\"").getResult());
        // test |
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 | 2"));
        Assertions.assertEquals("1", parser.parse("= 1 | 2").getResult());
        Assertions.assertEquals("1", parser.parse("= 1 | 0").getResult());
        Assertions.assertEquals("0", parser.parse("= 0 | 0").getResult());
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= 1 | \"hello\"").getResult());
        // assure : must be a range and check that outside a function it gives value error
        Assertions.assertInstanceOf(ErrorTerm.class, parser.parse("= 1:2"));
        Assertions.assertEquals(VALUE_ERROR, parser.parse("= $a1:$b2").getResult());
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
