package edu.nju.mutest.visitor.collector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class outer class declarations.
 *
 * @author Adian
 */
public class ClassDeclarationCollector
        extends VoidVisitorAdapter<List<ClassOrInterfaceDeclaration>>
{
    @Override
    public void visit(ClassOrInterfaceDeclaration n, List<ClassOrInterfaceDeclaration> arg) {
        super.visit(n, arg);
        arg.add(n);
    }

    public static List<ClassOrInterfaceDeclaration> collect(CompilationUnit cu) {
        return collect(cu, new NodeList<>());
    }


    public static List<ClassOrInterfaceDeclaration> collect(
        CompilationUnit cu,
        List<ClassOrInterfaceDeclaration> cds
    ) {
        ClassDeclarationCollector collector = new ClassDeclarationCollector();
        collector.visit(cu, cds);
        cds = cds.stream()
                .filter((cd) -> !cd.isNestedType() && !cd.isInterface() && !cd.isInnerClass())
                .collect(Collectors.toList());
        return cds;
    }
}
