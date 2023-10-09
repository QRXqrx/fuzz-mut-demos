package edu.nju.isefuzz;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * A demo instrumentor using ASM
 */
public class InstDemoVisitor extends ClassVisitor {

    public InstDemoVisitor(int api) {
        super(api);
    }

    public InstDemoVisitor() {
        this(Opcodes.ASM9);
    }

    @Override
    public void visitSource(String source, String debug) {
        super.visitSource(source, debug);
    }
}
