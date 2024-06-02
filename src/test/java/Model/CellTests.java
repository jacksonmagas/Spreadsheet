package Model;

import Model.Utils.TermFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for basic cell functions.
 */
public class CellTests {
  ICell newCell(int row, int col) {
    return new SpreadsheetCell(row, col);
  }

  /**
   * Cell creation and value change tests.
   * Jackson Magas
   */
  @Test
  public void testValueChanges() {
    ICell testCell = newCell(1, 1);
    Assertions.assertTrue(testCell.isEmpty());
    Assertions.assertNull(testCell.getData());
    testCell.updateCell(TermFactory.parseString("\"hello\""));
    Assertions.assertEquals(testCell.getData(), TermFactory.parseString("hello"));
    testCell.updateCell(TermFactory.parseString("=$A1"));
    Assertions.assertEquals(testCell.getData(), TermFactory.parseString("=$A1"));
  }

  /**
   * Test creating cells at various locations
   * Jackson Magas
   */
  @Test
  public void testCellCreation() {
    // TODO rewrite for spreadsheet implementation
  }

  /**
   * Test creating cells at invalid locations
   * Jackson Magas
   */
  @Test
  public void testInvalidCellCreation() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {newCell(0, 1);});
    Assertions.assertThrows(IllegalArgumentException.class, () -> {newCell(1, 0);});
    Assertions.assertThrows(IllegalArgumentException.class, () -> {newCell(-1, 1);});
    Assertions.assertThrows(IllegalArgumentException.class, () -> {newCell(1, -1);});
  }
}
