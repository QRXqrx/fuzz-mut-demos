package edu.nju.mutest.visitor.modifier;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.Collections;
import java.util.List;

/**
 * @author Adian
 */
public class ClassOrInterfaceDeclarationAdder
        extends ModifierVisitor<List<ClassOrInterfaceDeclaration>> {

    @Override
    public CompilationUnit visit(
            CompilationUnit cu,
            List<ClassOrInterfaceDeclaration> toBeAdded) {
        super.visit(cu, toBeAdded);
        toBeAdded.forEach(cu::addType);
        return cu;
    }

    public static void add(CompilationUnit cu, List<ClassOrInterfaceDeclaration> toBeAdded) {
        ClassOrInterfaceDeclarationAdder adder = new ClassOrInterfaceDeclarationAdder();
        adder.visit(cu, toBeAdded);
    }

    public static void add(CompilationUnit cu, ClassOrInterfaceDeclaration oneType) {
        add(cu, Collections.singletonList(oneType));
    }

}
