package edu.nju.mutest.visitor.collector;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.nju.mutest.visitor.collector.cond.CollectionCond;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Adian
 */
public class ExpressionStmtCollector extends VoidVisitorAdapter<List<ExpressionStmt>> {

    /**
     * Visit expression stmt and collect
     * @param n A node whose type is ExpressionStmt
     * @param arg Collection list.
     */
    @Override
    public void visit(ExpressionStmt n, List<ExpressionStmt> arg) {
        super.visit(n, arg);
        arg.add(n);
    }

    public static List<ExpressionStmt> collect(MethodDeclaration md) {
        return collect(md, new NodeList<>());
    }

    public static List<ExpressionStmt> collect(
            MethodDeclaration md,
            CollectionCond<ExpressionStmt> cond
    ) {
        return collect(md, new NodeList<>(), cond);
    }

    public static List<ExpressionStmt> collect(
            MethodDeclaration md,
            List<ExpressionStmt> expStmts
    ) {
        ExpressionStmtCollector collector = new ExpressionStmtCollector();
        collector.visit(md, expStmts);
        return expStmts;
    }

    public static List<ExpressionStmt> collect(
            MethodDeclaration md,
            List<ExpressionStmt> expStmts,
            CollectionCond<ExpressionStmt> cond
    ) {
        ExpressionStmtCollector collector = new ExpressionStmtCollector();
        collector.visit(md, expStmts);
        return expStmts.stream().filter(cond::willCollect).collect(Collectors.toList());
    }

    public static List<ExpressionStmt> collect(
            Statement stmt,
            List<ExpressionStmt> expStmts,
            CollectionCond<ExpressionStmt> cond
    ) {
        ExpressionStmtCollector collector = new ExpressionStmtCollector();
        if(stmt instanceof IfStmt)
            collector.visit((IfStmt) stmt, expStmts);
        else if(stmt instanceof BlockStmt)
            collector.visit((BlockStmt) stmt, expStmts);
        else if(stmt instanceof ForStmt)
            collector.visit((ForStmt) stmt, expStmts);
        else if(stmt instanceof TryStmt)
            collector.visit((TryStmt) stmt, expStmts);
        else if(stmt instanceof ForEachStmt)
            collector.visit((ForEachStmt) stmt, expStmts);
        else if(stmt instanceof WhileStmt)
            collector.visit((WhileStmt) stmt, expStmts);
        if(cond == null)
            return expStmts;
        return expStmts.stream().filter(cond::willCollect).collect(Collectors.toList());
    }

    public static List<ExpressionStmt> collect(
            Statement stmt,
            List<ExpressionStmt> expStmts
    ) {
        return collect(stmt, expStmts, null);
    }




}
