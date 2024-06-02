package Model;

import Model.Utils.ErrorTerm;
import Model.Utils.FormulaTerm;
import Model.Utils.Ref;
import Model.Utils.RefTerm;
import Model.Utils.TermFactory;
import java.util.List;
import javafx.scene.control.Cell;
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
    testCell1.updateCell(new RefTerm(testCell2.getRef()));
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
    testCell1.updateCell(new RefTerm(testCell2.getRef()));
    testCell2.updateCell(new RefTerm(testCell1.getRef()));
    Assertions.assertEquals(testCell1.getData().getResult(), "Circular reference detected");
  }
}
