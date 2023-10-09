package edu.nju.mutest;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import edu.nju.mutest.visitor.collector.BinaryExprCollector;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.*;

public class JavaparserTest {


    @Test
    public void testBinaryExprCollector() throws FileNotFoundException {

        String srcPath = "/Users/adian/Desktop/research/fuzz-mut-demos/mutest-demo/src/main/java/edu/nju/mutest/example/Calculator.java";
        File srcFile = new File(srcPath);
        CompilationUnit cu = StaticJavaParser.parse(srcFile);

        List<BinaryExpr> binaryExprs = BinaryExprCollector.collect(cu);
        System.out.println(binaryExprs);
        for (BinaryExpr binaryExpr : binaryExprs) {
            binaryExpr.setOperator(MINUS);
            System.out.println(cu);
            System.out.println("=============================");
        }

    }

    @Test
    public void testGetPackageInfo() throws FileNotFoundException {
        String srcPath = "/Users/adian/Desktop/research/fuzz-mut-demos/mutest-demo/src/main/java/edu/nju/mutest/example/Calculator.java";
        File srcFile = new File(srcPath);
        CompilationUnit cu = StaticJavaParser.parse(srcFile);

        PackageDeclaration pd = cu.getPackageDeclaration().get();
        System.out.println(pd);
        System.out.println(pd.getClass());
        System.out.println(pd.getName());

    }

    @Test
    public void test() throws FileNotFoundException {
        String srcPath = "/Users/adian/Desktop/research/fuzz-mut-demos/mutest-demo/src/main/java/edu/nju/mutest/example/Calculator.java";
        File srcFile = new File(srcPath);
        CompilationUnit cu = StaticJavaParser.parse(srcFile);

        ClassOrInterfaceDeclaration calculatorClass = cu.getClassByName("Calculator").get();
        for (MethodDeclaration method : calculatorClass.getMethods()) {
//            System.out.println(method);
            NodeList<Statement> stmts = method.getBody().get().getStatements();
            assert stmts.size() == 1;
            ReturnStmt returnStmt = stmts.get(0).asReturnStmt();
            BinaryExpr binExpr = (BinaryExpr) returnStmt.getExpression().get();
            BinaryExpr.Operator op = binExpr.getOperator();
            System.out.println(op);
            System.out.println(op.getClass());
        }

    }

}
