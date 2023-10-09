//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.triangle;

public class Triangle {
    protected long lborderA = 0L;
    protected long lborderB = 0L;
    protected long lborderC = 0L;

    public Triangle(long lborderA, long lborderB, long lborderC) {
        this.lborderA = lborderA;
        this.lborderB = lborderB;
        this.lborderC = lborderC;
    }

    public boolean isTriangle() {
        return isTriangle(this);
    }

    public String getType() {
        return getType(this);
    }

    public static boolean isTriangle(Triangle triangle) {
        boolean isTriangle = false;
        if (triangle.lborderA > 0L && triangle.lborderB > 0L && triangle.lborderC > 0L &&
            diffOfBorders(triangle.lborderA, triangle.lborderB) < triangle.lborderC &&
            diffOfBorders(triangle.lborderB, triangle.lborderC) < triangle.lborderA &&
            diffOfBorders(triangle.lborderC, triangle.lborderA) < triangle.lborderB) {
            isTriangle = true;
        }

        return isTriangle;
    }

    public static String getType(Triangle triangle) {
        String strType = "Illegal";
        if (isTriangle(triangle)) {
            if (triangle.lborderA == triangle.lborderB && triangle.lborderB == triangle.lborderC) {
                strType = "Regular";
            } else if (triangle.lborderA != triangle.lborderB && triangle.lborderB != triangle.lborderC && triangle.lborderA != triangle.lborderC) {
                strType = "Scalene";
            } else {
                strType = "Isosceles";
            }
        }

        return strType;
    }

    public static long diffOfBorders(long a, long b) {
        return a > b ? a - b : b - a;
    }

    public long[] getBorders() {
        long[] borders = new long[]{this.lborderA, this.lborderB, this.lborderC};
        return borders;
    }

    public String toString() {
        return "Triangle{lborderA=" + this.lborderA + ", lborderB=" + this.lborderB + ", lborderC=" + this.lborderC + '}';
    }
}
