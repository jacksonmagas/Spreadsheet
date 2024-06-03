package Model;

import Model.Utils.BiOperatorExpression;
import Model.Utils.EmptyTerm;
import Model.Utils.ErrorTerm;
import Model.Utils.FunctionExpression;
import Model.Utils.ITerm;
import Model.Utils.NumberTerm;
import Model.Utils.ParenExpression;
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
        Assertions.assertEquals(parser.parse("\"hello\""), new StringTerm("hello"));//TODO
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
        Assertions.assertInstanceOf(FunctionExpression.class, parser.parse("=AVG(SUM(5, 4, 3), 12, MAX($A1:$B1))"));
    }

    @Test
    public void testOperators() {
        Spreadsheet spreadsheet = new Spreadsheet();
        Spreadsheet.FormulaParser parser = spreadsheet.new FormulaParser();

        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 + 2"));
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 * 2"));
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 / 2"));
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 < 2"));
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 > 2"));
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 = 2"));
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 <> 2"));
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 & 2"));
        Assertions.assertInstanceOf(BiOperatorExpression.class, parser.parse("= 1 | 2"));
        Assertions.assertInstanceOf(ErrorTerm.class, parser.parse("= 1:2"));
    }

    @Test
    public void testReferences() {

    }

    @Test
    public void testRanges() {
        throw new UnsupportedOperationException();
    }
}
