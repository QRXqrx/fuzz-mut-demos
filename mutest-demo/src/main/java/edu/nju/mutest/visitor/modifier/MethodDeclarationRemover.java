package edu.nju.mutest.visitor.modifier;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.List;

/**
 * @author Adian
 */
public class MethodDeclarationRemover extends ModifierVisitor<List<MethodDeclaration>> {

    @Override
    public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration cid, List<MethodDeclaration> toBeRemoved) {
        super.visit(cid, toBeRemoved);
        toBeRemoved.forEach(cid::remove);
        return cid;
    }

    public static void remove(CompilationUnit cu, List<MethodDeclaration> toBeRemoved) {
        MethodDeclarationRemover remover = new MethodDeclarationRemover();
        remover.visit(cu, toBeRemoved);
    }

    public static void remove(ClassOrInterfaceDeclaration cd, List<MethodDeclaration> toBeRemoved) {
        MethodDeclarationRemover remover = new MethodDeclarationRemover();
        remover.visit(cd, toBeRemoved);
    }


}
