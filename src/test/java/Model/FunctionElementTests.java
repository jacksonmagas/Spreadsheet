package Model;

import Model.Utils.Function.*;
import Model.Utils.IRef;
import Model.Utils.Ref;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FunctionElementTests {

    @Test
    public void numEqualsNumTrue() {
        IFunctionElement e1 = new NumberFunctionElement(3.5);
        IFunctionElement e2 = new NumberFunctionElement(3.5);
        assertTrue(e1.equals(e2));
        assertTrue(e2.equals(e1));
    }

    @Test
    public void numEqualsNumFalse() {
        IFunctionElement e1 = new NumberFunctionElement(3.5);
        IFunctionElement e2 = new NumberFunctionElement(1.25);
        assertFalse(e1.equals(e2));
        assertFalse(e2.equals(e1));
    }

    @Test
    public void numEqualsString() {
        IFunctionElement e1 = new NumberFunctionElement(3.5);
        IFunctionElement e2 = new StringFunctionElement("not Equal");
        assertFalse(e1.equals(e2));
    }

    @Test
    public void numEqualsRef() {
        IRef ref = new Ref(1, 1);
        IFunctionElement e1 = new NumberFunctionElement(3.5);
        IFunctionElement e2 = new RefFunctionElement(ref);
        assertFalse(e1.equals(e2));
    }

}
