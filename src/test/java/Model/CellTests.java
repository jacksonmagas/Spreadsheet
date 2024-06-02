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
    Spreadsheet.FormulaParser parser = sheet.new FormulaParser();

    Assertions.assertTrue(testCell.isEmpty());
    Assertions.assertNull(testCell.getData());
    testCell.updateCell(parser.parse("\"hello\""));
    Assertions.assertEquals(testCell.getData(), parser.parse("hello"));
    testCell.updateCell(parser.parse("=$A1"));
    Assertions.assertEquals(testCell.getData(), parser.parse("=$A1"));
  }
}
