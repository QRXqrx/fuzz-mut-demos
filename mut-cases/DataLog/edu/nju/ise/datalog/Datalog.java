//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.datalog;

import java.util.Arrays;

public class Datalog {
    private final Predicate predicate;
    private final Argument[] arguments;

    public Datalog(Predicate predicate, Argument... arguments) {
        if (predicate == null) {
            throw new NullPointerException("Predicate cannot be null");
        } else if (arguments == null) {
            throw new NullPointerException("Arguments cannot be null");
        } else {
            this.predicate = predicate;
            this.arguments = arguments;
        }
    }

    public Predicate getPredicate() {
        return this.predicate;
    }

    public Argument[] getArguments() {
        return this.arguments;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof Datalog)) {
            return false;
        } else {
            return ((Datalog)o).predicate.equals(this.predicate) && Arrays.equals(((Datalog)o).arguments, this.arguments);
        }
    }

    public int hashCode() {
        return 17 * this.predicate.hashCode() + 13 * Arrays.hashCode(this.arguments);
    }

    public boolean compatibleWith(Fact fact) {
        return this.substituteTo(fact) != null;
    }

    public Substitution substituteTo(Fact fact) {
        if (!fact.getPredicate().equals(this.predicate)) {
            return null;
        } else if (fact.getValues().length != this.arguments.length) {
            return null;
        } else {
            Substitution s = new Substitution();

            for(int i = 0; i < fact.getValues().length; ++i) {
                Argument arg = this.arguments[i];
                Value val = fact.getValues()[i];
                if (arg.isValue()) {
                    if (!val.equals(arg.getValue())) {
                        return null;
                    }
                } else {
                    s = s.extend(arg.getVariable(), val);
                    if (s == null) {
                        return null;
                    }
                }
            }

            return s;
        }
    }

    public Fact toFact() {
        Value[] values = new Value[this.arguments.length];

        for(int i = 0; i < this.arguments.length; ++i) {
            if (!this.arguments[i].isValue()) {
                return null;
            }

            values[i] = this.arguments[i].getValue();
        }

        return new Fact(this.predicate, values);
    }

    public String toString() {
        String res = this.predicate.toString();
        res = res + "(";

        for(int i = 0; i < this.arguments.length - 1; ++i) {
            res = res + this.arguments[i].toString();
            res = res + ",";
        }

        if (this.arguments.length > 0) {
            res = res + this.arguments[this.arguments.length - 1].toString();
        }

        res = res + ")";
        return res;
    }
}
