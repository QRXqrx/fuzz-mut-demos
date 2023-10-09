//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.schedule;

import java.io.Serializable;

public class Work implements Serializable {
    private static final long serialVersionUID = 212980192843L;
    private String name;
    private int arrivalTime;
    private int serviceTime;
    private int beginTime;
    private int endTime;
    private boolean in = false;

    public void setIn() {
        this.in = true;
    }

    public boolean isIn() {
        return this.in;
    }

    public Work(String name, int t1, int t2) {
        this.name = name;
        this.arrivalTime = t1;
        this.serviceTime = t2;
    }

    public void setBeginTime(int t) {
        this.beginTime = t;
    }

    public int getBeginTime() {
        return this.beginTime;
    }

    public void setEndTime(int t) {
        this.endTime = t;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public int getCircleTime() {
        return this.endTime - this.arrivalTime;
    }

    public double getCircleWPTime() {
        return (double)this.getCircleTime() / (double)this.serviceTime;
    }

    public int getPriority() {
        return 0;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getServiceTime() {
        return this.serviceTime;
    }

    public String toString() {
        return "Work{name='" + this.name + '\'' + ", arrivalTime=" + this.arrivalTime + ", serviceTime=" + this.serviceTime + '}';
    }
}
