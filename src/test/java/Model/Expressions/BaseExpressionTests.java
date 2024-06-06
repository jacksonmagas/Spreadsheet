package Model.Expressions;

import Model.ICell;
import Model.Utils.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseExpressionTests {

    @Test
    public void testErrorTerm() {
        ITerm e1 = new ErrorTerm("error");
        ITerm e2 = new ErrorTerm("error");
        ITerm e3 = new ErrorTerm("Different");

        assertEquals(e1.toString(), "error");
        assertEquals(e1.references().size(), 0);
        assertTrue(e1.equals(e2));
        assertFalse(e1.equals(e3));
        assertTrue(e3.equals(e3));

        assertFalse(e3.equals(null));
    }

    @Test
    public void testEmptyTerm() {
        ITerm e1 = new EmptyTerm();
        ITerm e2 = new EmptyTerm();
        ITerm e3 = new NumberTerm(3);

        assertTrue(e1.equals(e2));
        assertFalse(e2.equals(e3));
    }

    @Test
    public void testNumberTermEquals() {
        ITerm e1 = new NumberTerm(5);
        ITerm e2 = new NumberTerm(5);
        ITerm e3 = new NumberTerm(6);

        assertTrue(e1.equals(e1));
        assertTrue(e1.equals(e2));
        assertFalse(e1.equals(e3));

        assertFalse(e2.equals(null));
    }

    @Test
    public void testParenExpressionEquals() {
        ITerm t1 = new ParenExpression(new EmptyTerm());
        ITerm t2 = new ParenExpression(new NumberTerm(5));
        ITerm t3 = new ParenExpression(new NumberTerm(5));
        ITerm t4 = new NumberTerm(5);

        assertTrue(t3.equals(t2));
        assertTrue(t1.isEmpty());
        assertFalse(t2.isEmpty());
        assertTrue(t3.equals(t4));
    }

    @Test
    public void referenceExpressionEqualsTest() {
        ICell c1 = mock(ICell.class);
        ICell c2 = mock(ICell.class);
        when(c1.getCoordinate()).thenReturn(new Coordinate(1, 1));
        when(c2.getCoordinate()).thenReturn(new Coordinate(2, 2));

        ITerm ref1 = new ReferenceExpression(c1);
        ITerm ref2 = new ReferenceExpression(c2);
        ITerm ref3 = new ReferenceExpression(c2);
        ITerm ref4 = new NumberTerm(5);

        assertTrue(ref3.equals(ref2));
        assertFalse(ref1.equals(ref3));

        assertFalse(ref1.equals(ref4));
        assertTrue(ref1.equals(ref1));
    }

    @Test
    public void rangeExpressionEqualsTest() {
        assertThrows(IllegalArgumentException.class, () -> new RangeExpression(List.of()));

        ICell c1 = mock(ICell.class);
        ICell c2 = mock(ICell.class);
        ICell c3 = mock(ICell.class);

        when(c1.getCoordinate()).thenReturn(new Coordinate(1, 1));
        when(c2.getCoordinate()).thenReturn(new Coordinate(2, 2));
        when(c3.getCoordinate()).thenReturn(new Coordinate(3, 3));

        when(c1.isEmpty()).thenReturn(true);
        when(c2.isEmpty()).thenReturn(false);
        when(c3.isEmpty()).thenReturn(false);

        when(c1.dependsOn(new Coordinate(1, 1))).thenReturn(true);
        when(c2.dependsOn(new Coordinate(2, 2))).thenReturn(true);
        when(c3.dependsOn(new Coordinate(3, 3))).thenReturn(true);

        ITerm range1 = new RangeExpression(List.of(c1, c2, c3));
        ITerm range2 = new RangeExpression(List.of(c1));

        assertFalse(range1.isEmpty());
        assertTrue(range2.isEmpty());

        List<Coordinate> coords = range1.references();
        assertEquals(coords.size(), 3);
        assertEquals(coords.get(0), new Coordinate(1, 1));
        assertEquals(coords.get(1), new Coordinate(2, 2));
        assertEquals(coords.get(2), new Coordinate(3, 3));

        assertTrue(range1.dependsOn(new Coordinate(2, 2)));
        assertFalse(range2.dependsOn(new Coordinate(2, 2)));
    }
}
