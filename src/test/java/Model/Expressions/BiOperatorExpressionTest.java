package Model.Expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import Model.CellFormat;
import Model.ICell;
import Model.ICellListener;
import Model.Expressions.FunctionExpression.FunctionType;
import Model.Expressions.ITerm.ResultType;
import Model.Utils.Coordinate;

/*
 * Tests for binary operations.
 * - Ezra
 */
public class BiOperatorExpressionTest {

    private static final String VALUE_ERROR = "#VALUE!";

    /*
     * ICell Implementation for testing. Can lift if other classes want to use. (replace with mock?)
     */
    private class testCell implements ICell {

        private Coordinate coordinate;

        private testCell(){}

        private testCell(Coordinate coord) {
            this.coordinate = coord;
        }

        @Override
        public boolean dependsOn(Coordinate cellLoc) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'dependsOn'");
        }

        @Override
        public void updateCell(String data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'updateCell'");
        }

        @Override
        public Coordinate getCoordinate() {
            return this.coordinate;
        }

        @Override
        public String getData() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getData'");
        }

        @Override
        public String getPlaintext() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getPlaintext'");
        }

        @Override
        public CellFormat getFormatting() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getFormatting'");
        }

        @Override
        public boolean isEmpty() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
        }

        @Override
        public void handleUpdate() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'handleUpdate'");
        }

        @Override
        public void registerListener(ICellListener listener) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'registerListener'");
        }

        @Override
        public void notifyListeners() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'notifyListeners'");
        }

        @Override
        public void setFormatting(CellFormat formatting) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setFormatting'");
        }

        @Override
        public ResultType dataType() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'dataType'");
        }

    }
    
    @Test
    public void BiOpExpressionInvalidConstructionTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(2.0);
        try {
            new BiOperatorExpression("++", term1, term2);
            Assertions.fail("invalid operator does not throw error");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "invalid operator");
        }
    }

    @Test
    public void BiOpExpressionConstructionTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(2.0);
        ITerm op = new BiOperatorExpression("+", term1, term2);
        assertEquals(op.toString(), "1.0 + 2.0"); // Believe this is unimplemented TODO
    }

    @Test
    public void BiOpAdditionTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(2.0);
        ITerm op = new BiOperatorExpression("+", term1, term2);
        assertEquals(op.getResult(), "3.0");
    }

    @Test
    public void BiOpSubtractionTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(2.0);
        ITerm op = new BiOperatorExpression("-", term1, term2);
        assertEquals(op.getResult(), "-1.0");
    }

    @Test
    public void BiOpMultiplicationTest() {
        ITerm term1 = new NumberTerm(3.0);
        ITerm term2 = new NumberTerm(2.0);
        ITerm op = new BiOperatorExpression("*", term1, term2);
        assertEquals(op.getResult(), "6.0");
    }

    @Test
    public void BiOpDivisionTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(2.0);
        ITerm op = new BiOperatorExpression("/", term1, term2);
        assertEquals(op.getResult(), "0.5");
    }

    @Test
    public void BiOpLessThanTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(2.0);
        ITerm op1 = new BiOperatorExpression("<", term1, term2);
        ITerm op2 = new BiOperatorExpression("<", term2, term1);
        assertEquals(op1.getResult(), "1.0");
        assertEquals(op2.getResult(), "0.0");
    }

    @Test
    public void BiOpGreaterThanTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(2.0);
        ITerm op1 = new BiOperatorExpression(">", term1, term2);
        ITerm op2 = new BiOperatorExpression(">", term2, term1);
        assertEquals(op1.getResult(), "0.0");
        assertEquals(op2.getResult(), "1.0");
    }

    @Test
    public void BiOpEqualToTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(2.0);
        ITerm term3 = new NumberTerm(1.0);
        ITerm op1 = new BiOperatorExpression("=", term1, term2);
        ITerm op2 = new BiOperatorExpression("=", term1, term3);
        ITerm op3 = new BiOperatorExpression("=", term3, term1);
        assertEquals(op1.getResult(), "0.0");
        assertEquals(op2.getResult(), "1.0");
        assertEquals(op3.getResult(), "1.0");
    }

    @Test
    public void BiOpNotEqualToTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(2.0);
        ITerm term3 = new NumberTerm(1.0);
        ITerm op1 = new BiOperatorExpression("<>", term1, term2);
        ITerm op2 = new BiOperatorExpression("<>", term2, term1);
        ITerm op3 = new BiOperatorExpression("<>", term3, term1);
        assertEquals(op1.getResult(), "1.0");
        assertEquals(op2.getResult(), "1.0");
        assertEquals(op3.getResult(), "0.0");
    }

    @Test
    public void BiOpAndTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(1.0);
        ITerm term3 = new NumberTerm(0.0);
        ITerm op1 = new BiOperatorExpression("&", term1, term2);
        ITerm op2 = new BiOperatorExpression("&", term1, term3);
        ITerm op3 = new BiOperatorExpression("&", term3, term1);
        assertEquals(op1.getResult(), "1.0");
        assertEquals(op2.getResult(), "0.0");
        assertEquals(op3.getResult(), "0.0");
    }

    @Test
    public void BiOpOrTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(1.0);
        ITerm term3 = new NumberTerm(0.0);
        ITerm term4 = new NumberTerm(0.0);
        ITerm op1 = new BiOperatorExpression("|", term1, term2);
        ITerm op2 = new BiOperatorExpression("|", term1, term3);
        ITerm op3 = new BiOperatorExpression("|", term3, term1);
        ITerm op4 = new BiOperatorExpression("|", term3, term4);
        assertEquals(op1.getResult(), "1.0");
        assertEquals(op2.getResult(), "1.0");
        assertEquals(op3.getResult(), "1.0");
        assertEquals(op4.getResult(), "0.0");
    }

    @Test
    public void BiOpRangeTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(2.0);
        ITerm op1 = new BiOperatorExpression(":", term1, term2);
        assertEquals(op1.resultType(), ResultType.error);
        assertEquals(op1.getResult(), VALUE_ERROR);
    }

    /*
     * Tests operations between number terms and non-number terms
     */
    @Test
    public void BiOpInvalidTypeTest() {
        ITerm term1 = new NumberTerm(1.0);
        ITerm term2 = new NumberTerm(1.0);
        ITerm term3 = new CircularErrorTerm("");
        ITerm term4 = new EmptyTerm();
        List<ITerm> funcList = new ArrayList<ITerm>();
        funcList.add(term1);
        funcList.add(term2);
        ITerm term5 = new FunctionExpression(FunctionType.MIN, funcList);
        ITerm term6 = new ParenExpression(term1);
        List<ICell> testCells = new ArrayList<ICell>();
        testCells.add(new testCell(new Coordinate(1, 1)));
        testCells.add(new testCell(new Coordinate(2, 2)));
        ITerm term7 = new RangeExpression(testCells); // TODO: Constructor may throw out of bounds exception. Add checks to constructor
        ITerm term8 = new ReferenceExpression(new testCell(new Coordinate(1, 1)));
        ITerm term9 = new StringTerm("null");

        ITerm op1 = new BiOperatorExpression("+", term1, term3);
        assertEquals(op1.resultType(), ResultType.error);
        assertEquals(op1.getResult(), VALUE_ERROR);

        ITerm op2 = new BiOperatorExpression("+", term1, term4);
        assertEquals(op2.resultType(), ResultType.error);
        assertEquals(op2.getResult(), VALUE_ERROR);

        ITerm op3 = new BiOperatorExpression("+", term1, term5);
        assertEquals(op3.resultType(), ResultType.error);
        assertEquals(op3.getResult(), VALUE_ERROR);

        ITerm op4 = new BiOperatorExpression("+", term1, term6);
        assertEquals(op4.resultType(), ResultType.error);
        assertEquals(op4.getResult(), VALUE_ERROR);

        ITerm op5 = new BiOperatorExpression("+", term1, term7);
        assertEquals(op5.resultType(), ResultType.error);
        assertEquals(op5.getResult(), VALUE_ERROR);

        ITerm op6 = new BiOperatorExpression("+", term1, term8);
        assertEquals(op6.resultType(), ResultType.error);
        assertEquals(op6.getResult(), VALUE_ERROR);

        ITerm op7 = new BiOperatorExpression("+", term1, term9);
        assertEquals(op7.resultType(), ResultType.error);
        assertEquals(op7.getResult(), VALUE_ERROR);
    }
}
