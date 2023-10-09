package edu.nju.mutest.visitor.collector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.nju.mutest.visitor.collector.cond.CollectionCond;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Adian
 */
public class MethodDeclarationCollector extends VoidVisitorAdapter<List<MethodDeclaration>> {

    @Override
    public void visit(MethodDeclaration md, List<MethodDeclaration> arg) {
        super.visit(md, arg);
        arg.add(md);
    }

    public static List<MethodDeclaration> collect(ClassOrInterfaceDeclaration cd) {
        return collect(cd, new NodeList<>());
    }

    public static List<MethodDeclaration> collect(ClassOrInterfaceDeclaration cd, NodeList<MethodDeclaration> mds) {
        MethodDeclarationCollector mdCollector = new MethodDeclarationCollector();
        mdCollector.visit(cd, mds);
        return mds;
    }

    public static List<MethodDeclaration> collect(
            ClassOrInterfaceDeclaration cd,
            CollectionCond<MethodDeclaration> cond)
    {
        return collect(cd, new NodeList<>(), cond);
    }

    public static List<MethodDeclaration> collect(
            ClassOrInterfaceDeclaration cd,
            List<MethodDeclaration> mds,
            CollectionCond<MethodDeclaration> cond)
    {
        MethodDeclarationCollector mdCollector = new MethodDeclarationCollector();
        mdCollector.visit(cd, mds);
        return mds.stream()
                .filter(cond::willCollect)
                .collect(Collectors.toList());
    }

    public static List<MethodDeclaration> collect(
            CompilationUnit cu,
            CollectionCond<MethodDeclaration> cond)
    {
        return collect(cu, new NodeList<>(), cond);
    }

    public static List<MethodDeclaration> collect(
             CompilationUnit cu,
             List<MethodDeclaration> mds,
             CollectionCond<MethodDeclaration> cond)
    {
        MethodDeclarationCollector mdCollector = new MethodDeclarationCollector();
        mdCollector.visit(cu, mds);
        return mds.stream()
                .filter(cond::willCollect)
                .collect(Collectors.toList());
    }

    public static List<MethodDeclaration> collect(CompilationUnit cu) {
        return collect(cu, new NodeList<>());
    }

    public static List<MethodDeclaration> collect(CompilationUnit cu, List<MethodDeclaration> mds) {
        MethodDeclarationCollector mdCollector = new MethodDeclarationCollector();
        mdCollector.visit(cu, mds);
        return mds;

    }

    public static List<MethodDeclaration> collectWithNames(CompilationUnit cu, List<String> names) {
        List<MethodDeclaration> mds = collect(cu);
        return mds.stream().filter((md) -> names.contains(md.getNameAsString())).collect(Collectors.toList());
    }

    public static List<MethodDeclaration> collectWithNames(CompilationUnit cu, String[] names) {
        return collectWithNames(cu, Arrays.asList(names));
    }

}
