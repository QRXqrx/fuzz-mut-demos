package edu.nju.mutest.visitor.modifier;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.Arrays;
import java.util.List;

/**
 * Remove method declaration using name.
 *
 * @author Adian
 */
public class MDNameRemover extends ModifierVisitor<List<String>> {

    @Override
    public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration cid, List<String> names) {
        super.visit(cid, names);

        List<MethodDeclaration> toBeRemoved = new NodeList<>();
        for (MethodDeclaration md : cid.getMethods()) {
            if (names.contains(md.getNameAsString()))
                toBeRemoved.add(md);
        }

        toBeRemoved.forEach(cid::remove);

        return cid;
    }

    public static void remove(CompilationUnit cu, String[] names) {
        remove(cu, Arrays.asList(names));
    }

    public static void remove(CompilationUnit cu, List<String> names) {
        MDNameRemover remover = new MDNameRemover();
        remover.visit(cu, names);
    }

    public static void remove(ClassOrInterfaceDeclaration cd, List<String> names) {
        MDNameRemover remover = new MDNameRemover();
        remover.visit(cd, names);
    }

}
