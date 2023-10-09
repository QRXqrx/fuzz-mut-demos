package edu.nju.mutest.visitor.modifier;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.Arrays;
import java.util.List;

/**
 * Add method declarations into an instance of ClassOrInterfaceDeclarations.
 * These instance doesn't represent inner class.
 *
 * @author Adian
 */
public class MethodDeclarationAdder extends ModifierVisitor<List<MethodDeclaration>> {

    @Override
    public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration cid, List<MethodDeclaration> toBeAdded) {
        super.visit(cid, toBeAdded);
        if(!cid.isNestedType()) { // Don't add test methods to nested class. Note that nested class and inner class are distinctly different terminology.
            NodeList<BodyDeclaration<?>> members = cid.getMembers();
            members.addAll(toBeAdded);
            cid.setMembers(members);
        }
        return cid;
    }

    public static void add(CompilationUnit cu, List<MethodDeclaration> toBeAdded) {
        MethodDeclarationAdder adder = new MethodDeclarationAdder();
        adder.visit(cu, toBeAdded);
    }

    public static void add(ClassOrInterfaceDeclaration cid, List<MethodDeclaration> toBeAdded) {
        MethodDeclarationAdder adder = new MethodDeclarationAdder();
        adder.visit(cid, toBeAdded);
    }

    public static void add(ClassOrInterfaceDeclaration cid, MethodDeclaration[] toBeAdded) {
        add(cid, Arrays.asList(toBeAdded));
    }

    public static void add(CompilationUnit cu, MethodDeclaration[] toBeAdded) {
        add(cu, Arrays.asList(toBeAdded));
    }


}
