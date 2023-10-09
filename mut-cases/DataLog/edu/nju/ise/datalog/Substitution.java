//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.datalog;

import java.util.Iterator;
import java.util.LinkedList;

public class Substitution {
    private LinkedList<Variable> from = new LinkedList();
    private LinkedList<Value> to = new LinkedList();

    public Substitution() {
    }

    private Substitution(LinkedList<Variable> from, LinkedList<Value> to) {
        this.from = from;
        this.to = to;
    }

    public Substitution extend(Variable variable, Value value) {
        int index = this.from.indexOf(variable);
        if (index != -1 && !this.to.get(index).equals(value)) {
            return null;
        } else {
            Substitution s = new Substitution(this.from, this.to);
            s.from.add(variable);
            s.to.add(value);
            return s;
        }
    }

    public Datalog applyOn(Datalog atom) {
        Argument[] args = atom.getArguments();
        Argument[] newArgs = new Argument[args.length];

        for(int i = 0; i < args.length; ++i) {
            if (args[i].isVariable()) {
                int index = this.from.indexOf(args[i].getVariable());
                if (index != -1) {
                    newArgs[i] = Argument.value(this.to.get(index));
                } else {
                    newArgs[i] = args[i];
                }
            } else {
                newArgs[i] = args[i];
            }
        }

        return new Datalog(atom.getPredicate(), newArgs);
    }

    public LinkedList<Substitution> extendAll(LinkedList<Substitution> subs) {
        LinkedList<Substitution> res = new LinkedList();
        Iterator var3 = subs.iterator();

        while(var3.hasNext()) {
            Substitution s = (Substitution)var3.next();
            Substitution newS = this;

            for(int i = 0; i < s.from.size(); ++i) {
                newS = newS.extend(s.from.get(i), s.to.get(i));
                if (newS == null) {
                    break;
                }
            }

            if (newS != null) {
                res.add(newS);
            }
        }

        return res;
    }
}
