//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.datalog;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class Program {
    private final Rule[] program;

    public Program(Rule... rules) {
        this.program = rules;
    }

    public Rule[] getProgram() {
        return this.program;
    }

    public boolean canDerive(Fact fact, Fact[] database) {
        Datalog atom = new Datalog(fact.getPredicate(), this.valToArg(fact.getValues()));
        return this.query(atom, database).length == 1;
    }

    private Argument[] valToArg(Value[] values) {
        Argument[] arguments = new Argument[values.length];

        for(int i = 0; i < values.length; ++i) {
            arguments[i] = Argument.value(values[i]);
        }

        return arguments;
    }

    public Fact[] query(Datalog atom, Fact[] database) {
        Fact[] allFacts = this.deriveAll(database);
        LinkedList<Fact> result = new LinkedList();
        Fact[] var5 = allFacts;
        int var6 = allFacts.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Fact fact = var5[var7];
            if (atom.compatibleWith(fact)) {
                result.add(fact);
            }
        }

        return result.toArray(new Fact[0]);
    }

    public Fact[] deriveAll(Fact[] database) {
        HashSet<Fact> allFacts = new HashSet(Arrays.asList(database));
        HashSet<Fact> oldFacts = new HashSet(Arrays.asList(database));
        int newFacts;
        do {
            Rule[] var5 = this.program;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Rule rule = var5[var7];
                allFacts.addAll(rule.deriveOnce(allFacts));
            }

            newFacts = allFacts.size() - oldFacts.size();
            oldFacts = (HashSet)allFacts.clone();
        } while(newFacts != 0);

        return allFacts.toArray(new Fact[0]);
    }
}
