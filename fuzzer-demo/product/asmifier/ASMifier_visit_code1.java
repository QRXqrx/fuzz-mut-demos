package asm.edu.nju.isefuzz;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.RecordComponentVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
public class HelloBranches1Dump implements Opcodes {

public static byte[] dump () throws Exception {

ClassWriter classWriter = new ClassWriter(0);
FieldVisitor fieldVisitor;
RecordComponentVisitor recordComponentVisitor;
MethodVisitor methodVisitor;
AnnotationVisitor annotationVisitor0;

classWriter.visit(V1_7, ACC_PUBLIC | ACC_SUPER, "edu/nju/isefuzz/HelloBranches1", null, "java/lang/Object", null);

classWriter.visitSource("HelloBranches1.java", null);

{
methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(3, label0);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
methodVisitor.visitInsn(RETURN);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLocalVariable("this", "Ledu/nju/isefuzz/HelloBranches1;", null, label0, label1, 0);
methodVisitor.visitMaxs(1, 1);
methodVisitor.visitEnd();
}
{
methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "printCov", "()V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(6, label0);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("[LOG] Reaching a branch!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLineNumber(7, label1);
methodVisitor.visitInsn(RETURN);
methodVisitor.visitMaxs(2, 0);
methodVisitor.visitEnd();
}
{
methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(11, label0);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitInsn(ARRAYLENGTH);
methodVisitor.visitInsn(ICONST_1);
Label label1 = new Label();
methodVisitor.visitJumpInsn(IF_ICMPEQ, label1);
Label label2 = new Label();
methodVisitor.visitLabel(label2);
methodVisitor.visitLineNumber(12, label2);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label3 = new Label();
methodVisitor.visitLabel(label3);
methodVisitor.visitLineNumber(13, label3);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Require 1 arg!!!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
Label label4 = new Label();
methodVisitor.visitLabel(label4);
methodVisitor.visitLineNumber(14, label4);
methodVisitor.visitInsn(ICONST_0);
methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "exit", "(I)V", false);
methodVisitor.visitLabel(label1);
methodVisitor.visitLineNumber(18, label1);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitInsn(ICONST_0);
methodVisitor.visitInsn(AALOAD);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toCharArray", "()[C", false);
methodVisitor.visitVarInsn(ASTORE, 1);
Label label5 = new Label();
methodVisitor.visitLabel(label5);
methodVisitor.visitLineNumber(19, label5);
methodVisitor.visitVarInsn(ALOAD, 1);
methodVisitor.visitInsn(ICONST_0);
methodVisitor.visitInsn(CALOAD);
methodVisitor.visitIntInsn(BIPUSH, 104);
Label label6 = new Label();
methodVisitor.visitJumpInsn(IF_ICMPNE, label6);
Label label7 = new Label();
methodVisitor.visitLabel(label7);
methodVisitor.visitLineNumber(20, label7);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label8 = new Label();
methodVisitor.visitLabel(label8);
methodVisitor.visitLineNumber(21, label8);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Catch h!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
Label label9 = new Label();
methodVisitor.visitLabel(label9);
methodVisitor.visitLineNumber(22, label9);
methodVisitor.visitVarInsn(ALOAD, 1);
methodVisitor.visitInsn(ICONST_1);
methodVisitor.visitInsn(CALOAD);
methodVisitor.visitIntInsn(BIPUSH, 101);
Label label10 = new Label();
methodVisitor.visitJumpInsn(IF_ICMPNE, label10);
Label label11 = new Label();
methodVisitor.visitLabel(label11);
methodVisitor.visitLineNumber(23, label11);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label12 = new Label();
methodVisitor.visitLabel(label12);
methodVisitor.visitLineNumber(24, label12);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Catch he!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
Label label13 = new Label();
methodVisitor.visitLabel(label13);
methodVisitor.visitLineNumber(25, label13);
methodVisitor.visitVarInsn(ALOAD, 1);
methodVisitor.visitInsn(ICONST_2);
methodVisitor.visitInsn(CALOAD);
methodVisitor.visitIntInsn(BIPUSH, 108);
Label label14 = new Label();
methodVisitor.visitJumpInsn(IF_ICMPNE, label14);
Label label15 = new Label();
methodVisitor.visitLabel(label15);
methodVisitor.visitLineNumber(26, label15);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label16 = new Label();
methodVisitor.visitLabel(label16);
methodVisitor.visitLineNumber(27, label16);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Catch hel!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
Label label17 = new Label();
methodVisitor.visitLabel(label17);
methodVisitor.visitLineNumber(28, label17);
methodVisitor.visitVarInsn(ALOAD, 1);
methodVisitor.visitInsn(ICONST_3);
methodVisitor.visitInsn(CALOAD);
methodVisitor.visitIntInsn(BIPUSH, 108);
Label label18 = new Label();
methodVisitor.visitJumpInsn(IF_ICMPNE, label18);
Label label19 = new Label();
methodVisitor.visitLabel(label19);
methodVisitor.visitLineNumber(29, label19);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label20 = new Label();
methodVisitor.visitLabel(label20);
methodVisitor.visitLineNumber(30, label20);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Catch hell!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
Label label21 = new Label();
methodVisitor.visitLabel(label21);
methodVisitor.visitLineNumber(31, label21);
methodVisitor.visitVarInsn(ALOAD, 1);
methodVisitor.visitInsn(ICONST_4);
methodVisitor.visitInsn(CALOAD);
methodVisitor.visitIntInsn(BIPUSH, 111);
Label label22 = new Label();
methodVisitor.visitJumpInsn(IF_ICMPNE, label22);
Label label23 = new Label();
methodVisitor.visitLabel(label23);
methodVisitor.visitLineNumber(32, label23);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label24 = new Label();
methodVisitor.visitLabel(label24);
methodVisitor.visitLineNumber(33, label24);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Catch hello!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
Label label25 = new Label();
methodVisitor.visitJumpInsn(GOTO, label25);
methodVisitor.visitLabel(label22);
methodVisitor.visitLineNumber(35, label22);
methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"[C"}, 0, null);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label26 = new Label();
methodVisitor.visitLabel(label26);
methodVisitor.visitLineNumber(36, label26);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Failed at (4, o)!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
methodVisitor.visitJumpInsn(GOTO, label25);
methodVisitor.visitLabel(label18);
methodVisitor.visitLineNumber(39, label18);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label27 = new Label();
methodVisitor.visitLabel(label27);
methodVisitor.visitLineNumber(40, label27);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Failed at (3, l)!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
methodVisitor.visitJumpInsn(GOTO, label25);
methodVisitor.visitLabel(label14);
methodVisitor.visitLineNumber(43, label14);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label28 = new Label();
methodVisitor.visitLabel(label28);
methodVisitor.visitLineNumber(44, label28);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Failed at (2, l)!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
methodVisitor.visitJumpInsn(GOTO, label25);
methodVisitor.visitLabel(label10);
methodVisitor.visitLineNumber(47, label10);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label29 = new Label();
methodVisitor.visitLabel(label29);
methodVisitor.visitLineNumber(48, label29);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Failed at (1, e)!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
methodVisitor.visitJumpInsn(GOTO, label25);
methodVisitor.visitLabel(label6);
methodVisitor.visitLineNumber(51, label6);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitMethodInsn(INVOKESTATIC, "edu/nju/isefuzz/HelloBranches1", "printCov", "()V", false);
Label label30 = new Label();
methodVisitor.visitLabel(label30);
methodVisitor.visitLineNumber(52, label30);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("Failed at (0, h)!");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
methodVisitor.visitLabel(label25);
methodVisitor.visitLineNumber(55, label25);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitInsn(RETURN);
Label label31 = new Label();
methodVisitor.visitLabel(label31);
methodVisitor.visitLocalVariable("args", "[Ljava/lang/String;", null, label0, label31, 0);
methodVisitor.visitLocalVariable("charArr", "[C", null, label5, label31, 1);
methodVisitor.visitMaxs(2, 2);
methodVisitor.visitEnd();
}
classWriter.visitEnd();

return classWriter.toByteArray();
}
}