package Model;

import Model.Utils.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for basic cell functions.
 */
public class CellTests {
  /**
   * Cell creation and value change tests.
   * Jackson Magas
   */
  @Test
  public void testValueChanges() {
    Spreadsheet sheet = new Spreadsheet();
    ICell testCell = sheet.getCell(new Coordinate(0, 0));

    Assertions.assertTrue(testCell.isEmpty());
    Assertions.assertNull(testCell.getData());
    testCell.updateCell("\"hello\"");
    Assertions.assertEquals(testCell.getData(), "hello");
    testCell.updateCell("=$A1");
    Assertions.assertEquals(testCell.getData(), "");
  }
}
