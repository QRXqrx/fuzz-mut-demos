//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.nextday;

public class Month extends CalendarUnit {
    private Year y;
    private int[] sizeIndex = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Month(int pMonth, Year y) {
        this.setMonth(pMonth, y);
    }

    public void setMonth(int pMonth, Year y) {
        this.setCurrentPos(pMonth);
        this.y = y;
        if (!this.isValid()) {
            throw new IllegalArgumentException("Not a valid month");
        }
    }

    public int getMonth() {
        return this.currentPos;
    }

    public int getMonthSize() {
        if (this.y.isLeap()) {
            this.sizeIndex[1] = 29;
        } else {
            this.sizeIndex[1] = 28;
        }

        return this.sizeIndex[this.currentPos - 1];
    }

    public boolean increment() {
        ++this.currentPos;
        return this.currentPos <= 12;
    }

    public boolean isValid() {
        return this.y != null && this.y.isValid() && this.currentPos >= 1 && this.currentPos <= 12;
    }

    public boolean equals(Object o) {
        return o instanceof Month && this.currentPos == ((Month)o).currentPos && this.y.equals(((Month)o).y);
    }
}
