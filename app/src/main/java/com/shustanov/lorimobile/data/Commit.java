package com.shustanov.lorimobile.data;

import java.util.ArrayList;
import java.util.List;

public class Commit {
    private final List<Object> commitInstances;

    private Commit(List<Object> commitInstances) {
        this.commitInstances = commitInstances;
    }

    public static class Builder {
        private final List<Object> commitInstances = new ArrayList<>();

        public Builder() {
        }

        public Builder commit(Object commitObject) {
            commitInstances.add(commitObject);
            return this;
        }

        public Commit build() {
            return new Commit(commitInstances);
        }
    }
}
