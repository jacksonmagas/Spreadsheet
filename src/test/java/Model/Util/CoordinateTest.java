package Model.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Model.Utils.Coordinate;

/*
 * Test class for coordinates
 * - Ezra
 */
public class CoordinateTest {

    @Test
    public void testCoordinatesEqual() {
        Coordinate c1 = new Coordinate(1, 1);
        Coordinate c2 = new Coordinate(1, 1);
        Coordinate c3 = new Coordinate(1, 2);
        Coordinate c4 = new Coordinate(2, 1);
        Coordinate c5 = new Coordinate(2, 2);
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(c4));
        assertFalse(c1.equals(c5));
    }

    @Test
    public void testCoordinateComparison() {
        Coordinate c1 = new Coordinate(1, 1);
        Coordinate c2 = new Coordinate(1, 1);
        Coordinate c3 = new Coordinate(1, 2);
        Coordinate c4 = new Coordinate(2, 1);
        Coordinate c5 = new Coordinate(2, 2);
        assertEquals(c1.compareTo(c5), -1);
        assertEquals(c5.compareTo(c1), 1);
        assertEquals(c1.compareTo(c2), 0);
        assertEquals(c1.compareTo(c3), 0);
        assertEquals(c1.compareTo(c4), 0);
        assertEquals(c3.compareTo(c1), 0);
        assertEquals(c4.compareTo(c1), 0);
    }

    @Test
    public void testCoordinateToString() {
        Coordinate c1 = new Coordinate(1, 2);
        assertEquals(c1.toString(), "$B1");
    }

    @Test
    public void testGetColumnCoordinate() {
        Coordinate c1 = new Coordinate(1, 2);
        assertEquals(c1.getColumn(), 2);
    }

    @Test
    public void testRowColumnCoordinate() {
        Coordinate c1 = new Coordinate(1, 2);
        assertEquals(c1.getRow(), 1);
    }

    @Test
    public void testCoordinateInvalidGetRange() {
        Coordinate c1 = new Coordinate(1, 1);
        Coordinate c2 = new Coordinate(1, 2);
        Coordinate c3 = new Coordinate(2, 2);
        try {
            c3.getRange(c1);
            Assertions.fail("Invalid range does not throw error.");
        } catch (IllegalArgumentException e) {}
        try {
            c2.getRange(c1);
            Assertions.fail("Invalid range does not throw error."); // I believe this will fail. Must update get Range.
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void testCoordinateGetRange() {
        Coordinate c1 = new Coordinate(1, 1);
        Coordinate c2 = new Coordinate(1, 2);
        Coordinate c3 = new Coordinate(1, 2);
        Coordinate c4 = new Coordinate(2, 2);
        List<Coordinate> c1Toc4 = c1.getRange(c4);
        assertEquals(c1Toc4.size(), 4);
        assertTrue(c1Toc4.contains(c1));
        assertTrue(c1Toc4.contains(c2));
        assertTrue(c1Toc4.contains(c3));
        assertTrue(c1Toc4.contains(c4));
    }

    @Test
    public void testCoordinateFromString() {
        assertEquals(new Coordinate(1, 1), new Coordinate("$A1"));
        assertEquals(new Coordinate(1, 2), new Coordinate("$B1"));
        assertEquals(new Coordinate(2, 1), new Coordinate("$A2"));
        try {
            new Coordinate("bad ref");
            Assertions.fail("Constructing Coordinate from Invalid ref does not throw error.");
        } catch (IllegalArgumentException e) {}
    }

    // TODO: neg vals

}
