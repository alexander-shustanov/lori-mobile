package com.shustanov.lorimobile.data;

import java.util.ArrayList;
import java.util.List;

public class Commit {
    private final List<Object> commitInstances;
    private final List<Object> removeInstances;

    private Commit(List<Object> commitInstances, List<Object> removeInstances) {
        this.commitInstances = commitInstances;
        this.removeInstances = removeInstances;
    }

    public static class Builder {
        private final List<Object> commitInstances = new ArrayList<>();
        private final List<Object> removeInstances = new ArrayList<>();

        public Builder() {
        }

        public Builder commit(Object commitObject) {
            commitInstances.add(commitObject);
            return this;
        }

        public Builder remove(Object commitObject) {
            removeInstances.add(commitObject);
            return this;
        }

        public Commit build() {
            return new Commit(commitInstances, removeInstances);
        }
    }
}
