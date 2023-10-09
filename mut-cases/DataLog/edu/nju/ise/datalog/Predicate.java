//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.datalog;

public class Predicate {
    private final String name;

    public Predicate(String name) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        } else {
            this.name = name;
        }
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else {
            return o instanceof Predicate ? ((Predicate)o).name.equals(this.name) : false;
        }
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return this.name;
    }
}
