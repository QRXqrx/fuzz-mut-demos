package edu.nju.mutest.visitor.modifier;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.List;
import java.util.Optional;

/**
 * @author Adian
 */
public class ExpressionStmtRemover extends ModifierVisitor<List<ExpressionStmt>> {

    @Override
    public BlockStmt visit(BlockStmt bs, List<ExpressionStmt> toBeRemoved) {
        super.visit(bs, toBeRemoved);
        // Remove can only remove direct child nodes.
        toBeRemoved.forEach(bs::remove);
        return bs;
    }

    @Override
    public MethodDeclaration visit(MethodDeclaration md, List<ExpressionStmt> toBeRemoved) {
        super.visit(md, toBeRemoved);
        Optional<BlockStmt> opBody = md.getBody();
        if(!opBody.isPresent()) {
            System.out.println("Method " + md.getNameAsString() + " has no bs body!");
            return md;
        }
        // Remove can only remove direct child nodes.
        visit(opBody.get(), toBeRemoved);
        return md;
    }

    public static void remove(
        MethodDeclaration md, List<ExpressionStmt> toBeRemoved) {
        ExpressionStmtRemover remover = new ExpressionStmtRemover();
        remover.visit(md, toBeRemoved);
    }


}
