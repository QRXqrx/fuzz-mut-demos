//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.datalog;

public class Variable {
    private final String identifier;

    public Variable(String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier cannot be null");
        } else {
            this.identifier = identifier;
        }
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else {
            return o instanceof Variable ? ((Variable)o).identifier.equals(this.identifier) : false;
        }
    }

    public int hashCode() {
        return this.identifier.hashCode();
    }

    public String toString() {
        return "VAR:" + this.identifier;
    }
}
