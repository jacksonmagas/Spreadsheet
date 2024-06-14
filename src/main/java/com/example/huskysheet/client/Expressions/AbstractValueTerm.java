package com.example.huskysheet.client.Expressions;

import com.example.huskysheet.client.Utils.Coordinate;
import java.util.List;

/**
 * Represents the abstract concept of an equation term which only holds a fixed value.
 * @author Jackson Magas
 */
public abstract class AbstractValueTerm implements ITerm {
    protected final String plaintext;

    public AbstractValueTerm(String plaintext) {
        this.plaintext = plaintext;
    }

    @Override
    public List<Coordinate> references() {
        return List.of();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    // @author Jackson Magas
    @Override
    public void recalculate() {
        // intentionally empty value terms do not need to recalculate
    }

    @Override
    public boolean dependsOn(Coordinate cellLoc) {
        return false;
    }

    @Override
    public String toString() {
        return plaintext;
    }

    @Override
    public List<ITerm> getMultipleResults() {
        return List.of();
    }
}
