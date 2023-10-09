//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.schedule;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    public Schedule() {
    }

    public List<Double> FCFS(List<Work> works) {
        double avgCircleTime = 0.0;
        double avgCircleTimeWP = 0.0;
        List<Double> lst = new ArrayList();

        for(int i = 0; i < works.size(); ++i) {
            if (i != 0) {
                works.get(i).setBeginTime(works.get(i - 1).getEndTime());
            } else {
                works.get(i).setBeginTime(works.get(i).getArrivalTime());
            }

            works.get(i).setEndTime(works.get(i).getBeginTime() + works.get(i).getServiceTime());
            avgCircleTime += works.get(i).getCircleTime();
            avgCircleTimeWP += works.get(i).getCircleWPTime();
        }

        avgCircleTime /= works.size();
        avgCircleTimeWP /= works.size();
        lst.add(avgCircleTime);
        lst.add(avgCircleTimeWP);
        return lst;
    }

    public List<Double> SJF(List<Work> works) {
        List<Double> lst = new ArrayList();
        double avgCircleTime = 0.0;
        double avgCircleTimeWP = 0.0;
        List<Work> ins = new ArrayList();
        int i = 0;
        int counter = 0;

        int j;
        while(counter < works.size()) {
            for(j = 0; j < works.size(); ++j) {
                if (!works.get(j).isIn() && works.get(j).getArrivalTime() <= i) {
                    ins.add(works.get(j));
                }
            }

            if (ins.isEmpty()) {
                ++i;
            } else {
                ins = sortByServiceTime(ins);
                Work now = (Work)((List)ins).get(0);
                now.setBeginTime(i);
                now.setEndTime(now.getBeginTime() + now.getServiceTime());
                now.setIn();
                ++counter;
                i = now.getEndTime();
                ins.clear();
            }
        }

        for(j = 0; j < works.size(); ++j) {
            avgCircleTime += works.get(j).getCircleTime();
            avgCircleTimeWP += works.get(j).getCircleWPTime();
        }

        avgCircleTime /= works.size();
        avgCircleTimeWP /= works.size();
        lst.add(avgCircleTime);
        lst.add(avgCircleTimeWP);
        return lst;
    }

    public static List<Work> sortByServiceTime(List<Work> ins) {
        for(int i = 0; i < ins.size(); ++i) {
            for(int j = i + 1; j < ins.size(); ++j) {
                Work aw = ins.get(i);
                int a = aw.getServiceTime();
                Work bw = ins.get(j);
                int b = bw.getServiceTime();
                if (a > b) {
                    ins.remove(j);
                    ins.add(i, bw);
                }
            }
        }

        return ins;
    }
}
