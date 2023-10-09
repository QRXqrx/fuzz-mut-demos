package edu.nju.mutest.mutator;

import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

/**
 * An exemplified top interface for source-level mutation.
 */
public interface Mutator {

    /**
     * Find the positions that could be mutated by this mutator.
     */
    void locateMutationPoints();

    /**
     * Mutate the original program, which is represented with {@link CompilationUnit},
     * and get a list of mutated programs.
     *
     * @return a list of compilation units mutated from the original one.
     */
    List<CompilationUnit> mutate();

}
