package edu.nju.isefuzz;

import org.objectweb.asm.util.ASMifier;
import org.junit.Test;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ASMifierTest {

    public void diffier(String cp1, String cp2) throws Exception {
        PrintStream ps1 = new PrintStream(Files.newOutputStream(Paths.get("ASMifier_visit_code1.java")));
        PrintStream ps2 = new PrintStream(Files.newOutputStream(Paths.get("ASMifier_visit_code2.java")));
        System.setOut(ps1);
        ASMifier.main(new String[]{cp1});
        System.setOut(ps2);
        ASMifier.main(new String[]{cp2});
    }

    @Test
    public void testDiffier() throws Exception {
        String cp1 = "/Users/adian/Desktop/research/cov-demo/target/classes/edu/nju/isefuzz/HelloBranches1.class";
        String cp2 = "/Users/adian/Desktop/research/cov-demo/target/classes/edu/nju/isefuzz/HelloBranches.class";
        diffier(cp1, cp2);
    }

    @Test
    public void testASMifier() throws Exception {

        String classPath = "/Users/adian/Desktop/research/cov-demo/target/classes/edu/nju/isefuzz/HelloBranches1.class";
        ASMifier.main(new String[]{classPath});

    }

}
