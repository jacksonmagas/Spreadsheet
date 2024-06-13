package com.example.huskysheet.client.Expressions;

/**
 * Represents a term with no value
 * @author Jackson Magas
 */
public class EmptyTerm extends AbstractValueTerm {
    public EmptyTerm() {
        super("");
    }

    @Override
    public String getResult() {
        return "";
    }

    @Override
    public ResultType resultType() {
        return ResultType.empty;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EmptyTerm;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
