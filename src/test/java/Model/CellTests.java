package Model;

import Model.Utils.TermFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for basic cell functions.
 */
public class CellTests {
  private ICell newCell(int row, int col) {
    return new SpreadsheetCell(row, col);
  }

  /**
   * Cell creation and value change tests.
   * Jackson Magas
   */
  @Test
  public void testValueChanges() {
    ICell testCell = newCell(0, 0);
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
    ICell testCell = newCell(0, 0);
    Assertions.assertEquals(testCell.getRef().getCell(), testCell);
    Assertions.assertEquals(testCell.getRef().getRow(), 0);
    Assertions.assertEquals(testCell.getRef().getColumn(), 0);
    testCell = newCell((int) 10E7, (int) 10E6);
    Assertions.assertEquals(testCell.getRef().getCell(), testCell);
    Assertions.assertEquals(testCell.getRef().getRow(), (int) 10E7);
    Assertions.assertEquals(testCell.getRef().getColumn(), (int) 10E6);
  }
}
