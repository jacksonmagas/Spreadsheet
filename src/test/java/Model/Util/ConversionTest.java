package Model.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Model.Utils.Conversions;
import Model.Utils.Coordinate;

/*
 * Test class for conversion functions
 * - Ezra
 */
public class ConversionTest {

    private static final String INVALID_NULL_REF = "null is not a valid Reference.";
    
    @Test
    public void testValidRef() {
        assertTrue(Conversions.isValidRef("$A1"));
        assertTrue(Conversions.isValidRef("$a1"));
        assertTrue(Conversions.isValidRef("$Z1"));
        assertTrue(Conversions.isValidRef("$Z11"));
        assertTrue(Conversions.isValidRef("$AB1"));
        assertTrue(Conversions.isValidRef("$Zb12"));
        assertTrue(Conversions.isValidRef("$AAAA11111"));
    }

    @Test
    public void testNotValidRef() {
        assertFalse(Conversions.isValidRef("A1"));
        assertFalse(Conversions.isValidRef("a1"));
        assertFalse(Conversions.isValidRef("$A"));
        assertFalse(Conversions.isValidRef("$AA"));
        assertFalse(Conversions.isValidRef("$11"));
        assertFalse(Conversions.isValidRef("$"));
        assertFalse(Conversions.isValidRef("Invalid Referance"));
    }

    @Test
    public void testInvalidRefRowConversion() {
        try {
            Conversions.row("null");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), INVALID_NULL_REF);
        }
    }

    @Test
    public void testInvalidRefColumnConversion() {
        try {
            Conversions.column("null");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), INVALID_NULL_REF);
        }
    }

    @Test
    public void testcolumnConversion() {
        assertEquals(Conversions.column("$A2"), 1);
        assertEquals(Conversions.column("$AB2"), 28);
    }

    @Test
    public void testRowConversion() {
        assertEquals(Conversions.row("$A2"), 2);
        assertEquals(Conversions.row("$A200"), 200);
        assertEquals(Conversions.row("$AA2"), 2);
        assertEquals(Conversions.row("$AB21"), 21);
    }

    @Test
    public void testColumnToString() {
        assertEquals(Conversions.columnToString(1), "A");
        assertEquals(Conversions.columnToString(25), "Y");
        assertEquals(Conversions.columnToString(27), "AA");
        try {
            Conversions.columnToString(0);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Column must be greater than zero");
        }
    }

    @Test
    public void testStringToCoordinates() {
        assertEquals(Conversions.stringToCoordinate("$A1"), new Coordinate(1, 1));
        assertEquals(Conversions.stringToCoordinate("$AA1"), new Coordinate(1, 27));
        try {
            Conversions.stringToCoordinate("bad ref");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "bad ref is not a valid Reference.");
        }
    }


}
