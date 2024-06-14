package com.example.huskysheet.client.Expressions;

import com.example.huskysheet.client.Utils.Coordinate;
import java.util.List;
import java.util.Objects;

public class ParenExpression extends AbstractExpression {

    ITerm enclosed;

    public ParenExpression(ITerm enclosed) {
        super("(" + enclosed.toString() + ")");
        this.enclosed = enclosed;
        this.value = enclosed.getResult();
    }

    @Override
    public List<Coordinate> references() {
        return enclosed.references();
    }

    @Override
    public ResultType resultType() {
        return enclosed.resultType();
    }

    @Override
    public boolean isEmpty() {
        return enclosed.isEmpty();
    }

    @Override
    public void recalculate() {
        enclosed.recalculate();
        value = enclosed.getResult();
    }

    @Override
    public boolean dependsOn(Coordinate cellLoc) {
        return enclosed.dependsOn(cellLoc);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ITerm && o.equals(enclosed);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(enclosed);
    }

    /**
     * Get a list of the multiple results of this term
     *
     * @return a list of terms that are the result of the results
     */
    @Override
    public List<ITerm> getMultipleResults() {
        return enclosed.getMultipleResults();
    }
}
