package edu.nju.mutest.mutator;


import com.github.javaparser.ast.CompilationUnit;

abstract public class AbstractMutator implements Mutator {

    protected CompilationUnit origCU;

    public AbstractMutator(CompilationUnit cu) {
        this.origCU = cu;
    }

    public void setOrigCU(CompilationUnit origCU) {
        this.origCU = origCU;
    }

}
