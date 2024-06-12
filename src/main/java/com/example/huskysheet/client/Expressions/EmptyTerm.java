package com.example.huskysheet.client.Expressions;

public class EmptyTerm extends AbstractValueTerm {
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
