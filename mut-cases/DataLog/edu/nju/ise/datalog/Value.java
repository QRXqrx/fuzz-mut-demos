//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.datalog;

public class Value {
    private final String value;

    public Value(String value) {
        if (value == null) {
            throw new NullPointerException("Value cannot be null");
        } else {
            this.value = value;
        }
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else {
            return o instanceof Value ? ((Value)o).value.equals(this.value) : false;
        }
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        return this.value;
    }
}
