package com.example.huskysheet.client.Expressions;

import com.example.huskysheet.client.Utils.Coordinate;
import java.util.List;

public abstract class AbstractValueTerm implements ITerm {

    @Override
    public List<Coordinate> references() {
        return List.of();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    // Jackson Magas
    @Override
    public void recalculate() {
        // intentionally empty value terms do not need to recalculate
    }

    @Override
    public boolean dependsOn(Coordinate cellLoc) {
        return false;
    }
}
