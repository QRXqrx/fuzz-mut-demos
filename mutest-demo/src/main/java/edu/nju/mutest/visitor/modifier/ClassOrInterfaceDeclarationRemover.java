package edu.nju.mutest.visitor.modifier;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.List;

/**
 * @author Adian
 */
public class ClassOrInterfaceDeclarationRemover
        extends ModifierVisitor<List<ClassOrInterfaceDeclaration>> {

    @Override
    public CompilationUnit visit(
            CompilationUnit cu,
            List<ClassOrInterfaceDeclaration> toBeRemoved) {
        super.visit(cu, toBeRemoved);
        toBeRemoved.forEach(cu::remove);
        return cu;
    }

    public static void remove(CompilationUnit cu, List<ClassOrInterfaceDeclaration> toBeRemoved) {
        ClassOrInterfaceDeclarationRemover remover = new ClassOrInterfaceDeclarationRemover();
        remover.visit(cu, toBeRemoved);
    }

}
