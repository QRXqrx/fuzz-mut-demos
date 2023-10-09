//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.datalog;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class Rule {
    private final Datalog head;
    private final Datalog[] body;

    public Rule(Datalog head, Datalog... body) {
        this.head = head;
        this.body = body;
    }

    public Datalog getHead() {
        return this.head;
    }

    public Datalog[] getBody() {
        return this.body;
    }

    public LinkedList<Fact> deriveOnce(Collection<Fact> database) {
        LinkedList<Datalog> bodyList = new LinkedList(Arrays.asList(this.body));
        LinkedList<Substitution> subs = findAllSubstitutions(database, bodyList);
        LinkedList<Fact> result = new LinkedList();
        Iterator var5 = subs.iterator();

        while(var5.hasNext()) {
            Substitution s = (Substitution)var5.next();
            result.add(s.applyOn(this.head).toFact());
        }

        return result;
    }

    private static LinkedList<Substitution> findAllSubstitutions(Collection<Fact> database, LinkedList<Datalog> workList) {
        LinkedList<Substitution> res = new LinkedList();
        if (workList.size() == 0) {
            res.add(new Substitution());
            return res;
        } else {
            Iterator var3 = database.iterator();

            while(true) {
                Substitution s;
                do {
                    if (!var3.hasNext()) {
                        return res;
                    }

                    Fact fact = (Fact)var3.next();
                    s = ((Datalog)workList.get(0)).substituteTo(fact);
                } while(s == null);

                LinkedList<Datalog> newWorkList = new LinkedList();

                for(int i = 1; i < workList.size(); ++i) {
                    newWorkList.add(s.applyOn((Datalog)workList.get(i)));
                }

                LinkedList<Substitution> recSub = findAllSubstitutions(database, newWorkList);
                res.addAll(s.extendAll(recSub));
            }
        }
    }

    public String toString() {
        String res = this.head.toString() + " :- ";

        for(int i = 0; i < this.body.length - 1; ++i) {
            res = res + this.body[i].toString();
            res = res + ",";
        }

        if (this.body.length > 0) {
            res = res + this.body[this.body.length - 1].toString();
        }

        return res + ".";
    }
}
