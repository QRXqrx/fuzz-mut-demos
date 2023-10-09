//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.datalog;

import java.util.Arrays;

public class Fact {
    private final Predicate predicate;
    private final Value[] values;

    public Fact(Predicate predicate, Value... values) {
        if (predicate == null) {
            throw new NullPointerException("Predicate cannot be null");
        } else if (values == null) {
            throw new NullPointerException("Values cannot be null");
        } else {
            this.predicate = predicate;
            this.values = values;
        }
    }

    public Predicate getPredicate() {
        return this.predicate;
    }

    public Value[] getValues() {
        return this.values;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof Fact)) {
            return false;
        } else {
            return ((Fact)o).predicate.equals(this.predicate) && Arrays.equals(((Fact)o).values, this.values);
        }
    }

    public int hashCode() {
        return 17 * this.predicate.hashCode() + 13 * Arrays.hashCode(this.values);
    }

    public String toString() {
        String res = this.predicate.toString();
        res = res + "(";

        for(int i = 0; i < this.values.length - 1; ++i) {
            res = res + this.values[i].toString();
            res = res + ",";
        }

        if (this.values.length > 0) {
            res = res + this.values[this.values.length - 1].toString();
        }

        res = res + ")";
        return res;
    }
}
