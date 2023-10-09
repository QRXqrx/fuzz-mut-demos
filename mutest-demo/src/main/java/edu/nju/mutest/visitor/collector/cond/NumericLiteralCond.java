package edu.nju.mutest.visitor.collector.cond;

import com.github.javaparser.ast.expr.LiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;

/**
 * @author Adian
 */
public class NumericLiteralCond implements CollectionCond<LiteralExpr> {
    @Override
    public boolean willCollect(LiteralExpr target) {
        return !(target instanceof StringLiteralExpr);
    }
}
