package com.example.huskysheet.client.Model;

import com.example.huskysheet.client.Expressions.BiOperatorExpression;
import com.example.huskysheet.client.Model.Spreadsheet.FormulaParser;
import com.example.huskysheet.client.Utils.Coordinate;
import com.example.huskysheet.client.Expressions.EmptyTerm;
import com.example.huskysheet.client.Expressions.ErrorTerm;
import com.example.huskysheet.client.Expressions.FunctionExpression;
import com.example.huskysheet.client.Expressions.ITerm;
import com.example.huskysheet.client.Expressions.NumberTerm;
import com.example.huskysheet.client.Expressions.ParenExpression;
import com.example.huskysheet.client.Expressions.ReferenceExpression;
import com.example.huskysheet.client.Expressions.StringTerm;
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
        parsesInto("2", "=IF(1, 2, 3)", parser);
        parsesInto("3", "=IF(0, 2, 3)", parser);
        parsesInto("hello", "=IF(0, 2, \"hello\")", parser);
        parsesInto("2", "=IF($A1:$A3)", parser);
        parsesInto(VALUE_ERROR, "=IF(\"test\", 2, 3)", parser);
        // test SUM
        parsesInto("3", "=SUM(0, 1, 2)", parser);
        parsesInto("6", "=SUM($A1:$A4)", parser);
        parsesInto("6", "=SUM($A2, $A3, $A4)", parser);
        parsesInto(VALUE_ERROR, "=SUM(0, 2, \"hello\")", parser);
        parsesInto(VALUE_ERROR, "=SUM($A1:$A5)", parser);
        // test MAX
        parsesInto("3.5", "=MAX(0, -1, 2, 3.5, 3.5)", parser);
        parsesInto("2", "=MAX($A1:$A3)", parser);
        parsesInto(VALUE_ERROR, "=MAX($A2, $A3, $A4, $A5)", parser);
        // test MIN
        parsesInto("-1", "=MIN(0, -1, 2, 3.5, 3.5)", parser);
        parsesInto("0", "=MIN($A1:$A3)", parser);
        parsesInto(VALUE_ERROR, "=MIN($A2, $A3, $A4, $A5)", parser);
        // test AVG
        parsesInto("1", "=AVG(0, 1, 2)", parser);
        parsesInto("1.5", "=AVG($A1:$A4)", parser);
        parsesInto("2", "=AVG($A2, $A3, $A4)", parser);
        parsesInto(VALUE_ERROR, "=AVG(0, 2, \"hello\")", parser);
        parsesInto(VALUE_ERROR, "=AVG($A1:$A5)", parser);
        // test CONCAT
        parsesInto("hello world", "=CONCAT(\"hello\", \" \", \"world\")", parser);
        parsesInto("hello world", "=CONCAT($A5, \" \", \"world\")", parser);
        parsesInto("hellofoobar", "=CONCAT($A5:$A6)", parser);
        parsesInto(VALUE_ERROR, "=CONCAT(1, \"foo\", -5)", parser);
        // test DEBUG
        parsesInto("1", "=DEBUG(1)", parser);
        parsesInto("1", "=DEBUG($A2)", parser);
        parsesInto("foobar", "=DEBUG($A6)", parser);
        parsesInto(VALUE_ERROR, "=DEBUG(1, 2)", parser);
        parsesInto(VALUE_ERROR, "=DEBUG($A1:$A5)", parser);
        // test COPY
        parsesInto("5.1", "=COPY(5.1, \"$B1\")", parser);
        parsesInto(VALUE_ERROR, "=COPY($A1, $b1)", parser);
        parsesInto(VALUE_ERROR, "=COPY($A2, \"hello\")", parser);
    }

    private static void parsesInto(String number, String formula, FormulaParser parser) {
        Assertions.assertEquals(number, parser.parse(formula).getResult());
    }

    @Test
    public void testOperators() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();

        // test +
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 + 2"));
        parsesInto("3", "= 1 + 2", parser);
        parsesInto(VALUE_ERROR, "= 1 + \"hello\"", parser);
        parsesInto(VALUE_ERROR, "= \"a\" + \"b\"", parser);
        // test -
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 - 2"));
        parsesInto("-1", "= 1 - 2", parser);
        parsesInto(VALUE_ERROR, "= 1 - \"hello\"", parser);
        parsesInto(VALUE_ERROR, "= \"a\" - \"b\"", parser);
        // test *
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 * 2"));
        parsesInto("2", "= 1 * 2", parser);
        parsesInto(VALUE_ERROR, "= 1 * \"hello\"", parser);
        parsesInto(VALUE_ERROR, "= \"a\" * \"b\"", parser);
        // test /
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 / 2"));
        parsesInto("0.5", "= 1 / 2", parser);
        parsesInto("#DIV/0!", "= 1 / 0", parser);
        parsesInto(VALUE_ERROR, "= 1 / \"hello\"", parser);
        parsesInto(VALUE_ERROR, "= \"a\" / \"b\"", parser);
        // test <
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 < 2"));
        parsesInto("1", "= 1 < 2", parser);
        parsesInto("0", "= 2 < 1", parser);
        parsesInto(VALUE_ERROR, "= 1 < \"hello\"", parser);
        parsesInto(VALUE_ERROR, "= \"a\" < \"b\"", parser);
        // test >
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 > 2"));
        parsesInto("0", "= 1 > 2", parser);
        parsesInto("1", "= 2 > 1", parser);
        parsesInto(VALUE_ERROR, "= 1 > \"hello\"", parser);
        parsesInto(VALUE_ERROR, "= \"a\" > \"b\"", parser);
        // test =
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 = 2"));
        parsesInto("0", "= 1 = 2", parser);
        parsesInto("1", "= 1 = 1", parser);
        parsesInto(VALUE_ERROR, "= 1 = \"hello\"", parser);
        parsesInto("0", "= \"a\" = \"b\"", parser);
        parsesInto("1", "= \"a\" = \"a\"", parser);
        // test <>
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 <> 2"));
        parsesInto("0", "= 1 <> 1", parser);
        parsesInto("1", "= 1 <> 2", parser);
        parsesInto(VALUE_ERROR, "=\"hello\"<>1", parser);
        parsesInto("1", "= \"a\" <> \"b\"", parser);
        parsesInto("0", "= \"a\" <> \"a\"", parser);
        // test &
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 & 2"));
        parsesInto("1", "= 1 & 2", parser);
        parsesInto("0", "= 1 & 0", parser);
        parsesInto(VALUE_ERROR, "= 1 & \"hello\"", parser);
        // test |
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 | 2"));
        parsesInto("1", "= 1 | 2", parser);
        parsesInto("1", "= 1 | 0", parser);
        parsesInto("0", "= 0 | 0", parser);
        parsesInto(VALUE_ERROR, "= 1 | \"hello\"", parser);
        // assure : must be a range and check that outside a function it gives value error
        Assertions.assertInstanceOf(ErrorTerm.class, parser.parse("= 1:2"));
        parsesInto(VALUE_ERROR, "= $a1:$b2", parser);
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
        parsesInto("1", "=$A1", parser);
        Assertions.assertInstanceOf(ReferenceExpression.class, parser.parse("=$d2"));
        parsesInto("2", "=$d2", parser);
        Assertions.assertInstanceOf(ReferenceExpression.class, parser.parse("=$Z3"));
        parsesInto("3", "=$Z3", parser);
        Assertions.assertInstanceOf(ReferenceExpression.class, parser.parse("=$AA123"));
        parsesInto("4", "=$AA123", parser);
    }

    @Test
    public void testRanges() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();

        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=SUM($A1:$C3)"));
    }
}
