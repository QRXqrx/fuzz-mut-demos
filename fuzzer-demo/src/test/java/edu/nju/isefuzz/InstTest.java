package edu.nju.isefuzz;

import org.junit.Test;
import org.objectweb.asm.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.objectweb.asm.Opcodes.*;

/**
 * Test ASM usage.
 */
public class InstTest {

    static int ASM_VER = ASM9;

    private static class CovClassAdapter extends ClassVisitor {

        protected CovClassAdapter(ClassVisitor classVisitor) {
            super(ASM_VER, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(
                int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
            if (mv != null)
                mv = new CovMethodAdapter(mv);
            return mv;
        }
    }

    private static class CovMethodAdapter extends MethodVisitor {

        protected CovMethodAdapter(MethodVisitor mv) {
            // Should register a method writer.
            super(ASM_VER, mv);
        }


        /**
         * Add an invocation to the constructed printCov()
         *
         * @param opcode the opcode of the type instruction to be visited. This opcode is either IFEQ,
         *     IFNE, IFLT, IFGE, IFGT, IFLE, IF_ICMPEQ, IF_ICMPNE, IF_ICMPLT, IF_ICMPGE, IF_ICMPGT,
         *     IF_ICMPLE, IF_ACMPEQ, IF_ACMPNE, GOTO, JSR, IFNULL or IFNONNULL.
         * @param label the operand of the instruction to be visited. This operand is a label that
         *     designates the instruction to which the jump instruction may jump.
         */
        @Override
        public void visitJumpInsn(int opcode, Label label) {
            switch (opcode) {
                case IF_ICMPNE:
                    System.out.println("IF_ICMPNE");
                    break;
                case GOTO:
                    System.out.println("GOTO");
                    break;
                case IF_ICMPLT:
                    System.out.println("IF_ICMPLT");
                    break;
                case IF_ICMPEQ:
                    System.out.println("IF_ICMPEQ");
                    break;
                default:
                    System.out.println(opcode);
            }
            super.visitJumpInsn(opcode, label);
            if (mv != null) {
                System.out.println("Insert a call to printCov()");
                mv.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches",
                        "printCov", "()V", false);
            }
        }


//        @Override
//        public void visitJumpInsn(int opcode, Label label) {
//            switch (opcode) {
//                case IF_ICMPNE:
//                    System.out.println("IF_ICMPNE");
//                    break;
//                case GOTO:
//                    System.out.println("GOTO");
//                    break;
//                case IF_ICMPLT:
//                    System.out.println("IF_ICMPLT");
//                    break;
//                case IF_ICMPEQ:
//                    System.out.println("IF_ICMPEQ");
//                    break;
//                default:
//                    System.out.println(opcode);
//            }
//            super.visitJumpInsn(opcode, label);
//            if (mv != null) {
//                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                mv.visitLdcInsn("[LOG] Reaching a branch!");
//                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
//                        "(Ljava/lang/String;)V", false);
//            }
//        }

    }

    @Test
    public void testAddInst1() throws IOException {
        // Locate target class
        String classPath = "/Users/adian/Desktop/research/cov-demo/target/classes/edu/nju/isefuzz/HelloBranches.class";
        File classFile = new File(classPath);

        // Read class file.
        ClassReader cr = new ClassReader(Files.newInputStream(classFile.toPath()));

        // Build class visitor chain
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cr.accept(cw, ClassReader.EXPAND_FRAMES); // Must accept, or other visitors cannot visit the given class file.

        // Bytecode manipulation: Inject statements.
        cw.visit(V11, ACC_PUBLIC, "edu/nju/isefuzz/HelloBranches",
                null, "java/lang/Object", null);

        // Create a static method for logging.
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "printCov", "()V", null, null);
        mv.visitCode();
//        Label label0 = new Label();
//        mv.visitLabel(label0);
//        mv.visitLineNumber(6, label0);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("[LOG] Reaching a branch!");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//        Label label1 = new Label();
//        mv.visitLabel(label1);
//        mv.visitLineNumber(7, label1);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 0);
        mv.visitEnd();

        // Append invocation to printCov for each branch in main
        cr = new ClassReader(cw.toByteArray());
        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        CovClassAdapter cca = new CovClassAdapter(cw);
        cr.accept(cca, ClassReader.EXPAND_FRAMES);

        // Output to local
        String outPath = "/Users/adian/Desktop/research/cov-demo/ignore/edu/nju/isefuzz/HelloBranches.class";
        File outFile = new File(outPath);
        File outDir = outFile.getParentFile();
        if (!outDir.exists())
            Files.createDirectories(outDir.toPath());
        Files.write(outFile.toPath(), cw.toByteArray());
        System.out.println("Output to " + outPath);

    }
    

    @Test
    public void testAddInst() throws IOException {
        // Locate target class
        String classPath = "/Users/adian/Desktop/research/cov-demo/target/classes/edu/nju/isefuzz/HelloBranches.class";
        File classFile = new File(classPath);

        // Read class file.
        ClassReader cr = new ClassReader(Files.newInputStream(classFile.toPath()));

        // Build class visitor chain
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cr.accept(cw, ClassReader.EXPAND_FRAMES); // Must accept, or other visitors cannot visit the given class file.

        // Bytecode manipulation: Inject statements.
        cw.visit(V11, ACC_PUBLIC, "edu/nju/isefuzz/HelloBranches",
                null, "java/lang/Object", null);

        // Add a field.
//        cw.visitField(
//                // Modifier|访问权限，Interface中的变量都是public final static的
//                ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
//                "LESS", // 成员变量名
//                "V", // 变量类型
//                null,
//                -1 // 一个成员变量的常量值，这个参数是提供真·常量值的，即final static
//        ).visitEnd(); // 这个地方为什么要visitEnd一次？

        // Create a static method for logging.
        MethodVisitor mv = cw.visitMethod(
                ACC_PUBLIC + ACC_STATIC,    // Accessor: public static
                "printCov",                        // Method name
                "()V",                               // Descriptor: return type + parameter list
                null,                              // Signature
                null
        );
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("[LOG] Reaching a branch!");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitEnd();

        // Append invocation to printCov for each branch in main
        cr = new ClassReader(cw.toByteArray());
        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        CovClassAdapter cca = new CovClassAdapter(cw);
        cr.accept(cca, ClassReader.EXPAND_FRAMES);

        // Output to local
        String outPath = "/Users/adian/Desktop/research/cov-demo/ignore/edu/nju/isefuzz/HelloBranches.class";
        File outFile = new File(outPath);
        File outDir = outFile.getParentFile();
        if (!outDir.exists())
            Files.createDirectories(outDir.toPath());
        Files.write(outFile.toPath(), cw.toByteArray());
        System.out.println("Output to " + outPath);

    }

    @Test
    public void testClassWriter() throws IOException {
        // 0 is possibly default.
        ClassWriter cw = new ClassWriter(0);
        cw.visit( // Define class header -> Hybrid RTS中提到的"Class Header Change"
                V1_5, // Java版本：Java1.5，org.objectweb.asm.Opcodes提供了大量的常数
                ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, // Interface是public且abstract的
                "pkg/Comparable", // (Internal name) Qualified name of this class
                null, // 与泛型有关
                "java/lang/Object", // 父类的internal name
                new String[] {"pkg/Measurable"} // 实现的接口
                // 父类只有一个，所以是一个String；接口可以实现多个，所以是一个String[]
        );
        cw.visitField(
                // Modifier|访问权限，Interface中的变量都是public final static的
                ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
                "LESS", // 成员变量名
                "I", // 变量类型
                null,
                -1 // 一个成员变量的常量值，这个参数是提供真·常量值的，即final static
        ).visitEnd(); // 这个地方为什么要visitEnd一次？
        // 可以继续visitAnnotation，构建成员变量的注解
        // 可以继续visitAttribute，构建code attributes
        cw.visitField(
                ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
                "EQUAL",
                "I",
                null,
                0 // No need for boxing anymore
        ).visitEnd();
        cw.visitField(
                ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
                "GREATER",
                "I",
                null, // 没有用泛型的时候就是null
                1 // No need for boxing anymore
        ).visitEnd();
        cw.visitMethod(
                ACC_PUBLIC + ACC_ABSTRACT, // 接口中的方法都是public abstract的
                "compareTo",
                "(Ljava/lang/Object;)I",
                null, // 当目标方法没有用到泛型的时候，signature就是null
                null // An array of exceptions
        ).visitEnd(); // 调用visitEnd的目的可能是要终结某个构造链，每次End都代表类中的一个Component构造完毕了
        // 调用visitEnd之前
        // 可以继续调用visitAnnotation和visitAttribute
        // 可以继续构造这个方法的Method
        cw.visitEnd(); // 这个类构造完毕
        byte[] b = cw.toByteArray();

        // Write to local
        File classFile = new File("/Users/adian/Desktop/research/cov-demo/ignore/Comparable.class");
        Files.write(classFile.toPath(), b);
    }

}
