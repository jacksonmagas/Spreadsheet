package Model;

import Model.Utils.ReferenceExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for cells referencing other cells.
 */
public class CellReferenceTests extends CellTests {

  /**
   * Test cells subscribing as listeners and having the values
   * properly update.
   * Jackson Magas
   */
  @Test
  public void TestCellReference() {
    ICell testCell1 = newCell(1, 1);
    ICell testCell2 = newCell(1, 2);
    testCell1.updateCell(new ReferenceExpression(testCell2.getCoordinate()));
    testCell2.updateCell(TermFactory.parseString("\"hello\""));
    Assertions.assertEquals(testCell1.getData().getResult(), testCell2.getData().getResult());
  }

  /**
   * Test that circular references do not crash
   * Jackson Magas
   */
  @Test
  public void TestCircularReference() {
    ICell testCell1 = newCell(1, 1);
    ICell testCell2 = newCell(1, 2);
    testCell1.updateCell(new ReferenceExpression(testCell2.getCoordinate()));
    testCell2.updateCell(new ReferenceExpression(testCell1.getCoordinate()));
    Assertions.assertEquals(testCell1.getData().getResult(), "Circular reference detected");
  }
}
