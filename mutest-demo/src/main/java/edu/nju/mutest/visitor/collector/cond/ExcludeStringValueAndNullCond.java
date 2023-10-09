package edu.nju.mutest.visitor.collector.cond;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;

/**
 * @author Adian
 */
public class ExcludeStringValueAndNullCond implements CollectionCond<Expression> {
    @Override
    public boolean willCollect(Expression target) {
        return !(target instanceof StringLiteralExpr) &&
               !(target instanceof NullLiteralExpr);
    }
}
