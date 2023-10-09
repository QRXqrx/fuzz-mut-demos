//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.nextday;

public abstract class CalendarUnit {
    protected int currentPos;

    public CalendarUnit() {
    }

    protected void setCurrentPos(int pCurrentPos) {
        this.currentPos = pCurrentPos;
    }

    protected int getCurrentPos() {
        return this.currentPos;
    }

    protected abstract boolean increment();

    protected abstract boolean isValid();
}
