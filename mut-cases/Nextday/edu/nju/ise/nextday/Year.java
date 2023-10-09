//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.nextday;

public class Year extends CalendarUnit {
    public Year(int pYear) {
        this.setYear(pYear);
    }

    public void setYear(int pYear) {
        this.setCurrentPos(pYear);
        if (!this.isValid()) {
            throw new IllegalArgumentException("Not a valid month");
        }
    }

    public int getYear() {
        return this.currentPos;
    }

    public boolean increment() {
        ++this.currentPos;
        if (this.currentPos == 0) {
            this.currentPos = 1;
        }

        return true;
    }

    public boolean isLeap() {
        if (this.currentPos < 0 || (this.currentPos % 4 != 0 || this.currentPos % 100 == 0) && this.currentPos % 400 != 0) {
            return this.currentPos < 0 && (this.currentPos * -1 % 4 == 1 && this.currentPos * -1 % 100 != 1 || this.currentPos * -1 % 400 == 1);
        } else {
            return true;
        }
    }

    protected boolean isValid() {
        return this.currentPos != 0;
    }

    public boolean equals(Object o) {
        return o instanceof Year && this.currentPos == ((Year)o).currentPos;
    }
}
