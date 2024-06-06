package Model;

import com.example.huskysheet.model.Expressions.ITerm.ResultType;
import com.example.huskysheet.service.ICell;
import com.example.huskysheet.service.Spreadsheet;
import com.example.huskysheet.utils.Coordinate;
import com.example.huskysheet.model.Expressions.EmptyTerm;
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
    ICell testCell = sheet.getCell(new Coordinate(1, 1));

    Assertions.assertTrue(testCell.isEmpty());
    Assertions.assertEquals(new EmptyTerm().getResult(), testCell.getData());
    testCell.updateCell("\"hello\"");
    Assertions.assertEquals(testCell.getData(), "hello");
    testCell.updateCell("=$A2");
    Assertions.assertEquals(testCell.getData(), "");
  }

  /**
   * Test that reference cells properly change their values when the cell they reference changes.
   * Jackson Magas
   */
  @Test
  public void testUnderlyingValueChanges() {
    Spreadsheet sheet = new Spreadsheet();
    ICell a1 = sheet.getCell(new Coordinate(1, 1));
    ICell a2 = sheet.getCell(new Coordinate(2, 1));
    ICell a3 = sheet.getCell(new Coordinate(3, 1));
    ICell b1 = sheet.getCell(new Coordinate(1, 2));
    ICell b2 = sheet.getCell(new Coordinate(2, 2));
    ICell b3 = sheet.getCell(new Coordinate(3, 2));
    ICell c1 = sheet.getCell(new Coordinate(1, 3));
    ICell c2 = sheet.getCell(new Coordinate(2, 3));
    ICell c3 = sheet.getCell(new Coordinate(3, 3));

    a1.updateCell("\"foo\"");
    a2.updateCell("=$A1");
    Assertions.assertEquals("foo", a2.getData());
    a1.updateCell("=\"bar\"");
    Assertions.assertEquals("bar", a1.getData());

    a3.updateCell("=$A2");
    b1.updateCell("=$A3");
    b2.updateCell("=DEBUG($B1)");
    b3.updateCell("=($B2)");
    c1.updateCell("=$B3");
    c2.updateCell("=(($C1))");
    c3.updateCell("=$C2");

    Assertions.assertEquals("bar", c3.getData());
    a1.updateCell("=-5.23");
    Assertions.assertEquals("-5.23", c3.getData());
  }

  @Test
  public void testCircularReference() {
    Spreadsheet sheet = new Spreadsheet();
    sheet.getCell(new Coordinate(1, 1)).updateCell("=$A2");
    sheet.getCell(new Coordinate(2, 1)).updateCell("=$A1");

    Assertions.assertEquals(ResultType.error, sheet.getCell(new Coordinate(2, 1)).dataType());
    Assertions.assertEquals(ResultType.error, sheet.getCell(new Coordinate(1, 1)).dataType());

    sheet.getCell(new Coordinate(1, 2)).updateCell("=AVG($B2+5, 6, 3 * ($B2 > 7))");
    sheet.getCell(new Coordinate(2, 2)).updateCell("=$B1");
    Assertions.assertEquals(ResultType.error, sheet.getCell(new Coordinate(1, 2)).dataType());
    Assertions.assertEquals(ResultType.error, sheet.getCell(new Coordinate(2, 2)).dataType());
    sheet.getCell(new Coordinate(2, 2)).updateCell("3");
    Assertions.assertEquals(ResultType.number, sheet.getCell(new Coordinate(1, 2)).dataType());
    Assertions.assertEquals("4.666666666666667", sheet.getCell(new Coordinate(1, 2)).getData());
  }
}
