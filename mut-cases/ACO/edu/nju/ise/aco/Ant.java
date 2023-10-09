//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.aco;

import java.util.ArrayList;

public class Ant implements Comparable<Ant> {
    String currentPos;
    Double sumDistance;
    double[][] pheromoneMatrix;
    ArrayList<String> visitedCitys;
    ArrayList<String> nonVisitedCitys;
    ArrayList<String> currentPath;

    public Ant(double[][] pheromoneMatrix, ArrayList<String> nonVisitedCitys) {
        this.pheromoneMatrix = pheromoneMatrix;
        this.nonVisitedCitys = nonVisitedCitys;
        this.visitedCitys = new ArrayList();
        this.currentPath = new ArrayList();
    }

    public double calSumDistance() {
        this.sumDistance = 0.0;

        for(int i = 0; i < this.currentPath.size() - 1; ++i) {
            String lastCity = this.currentPath.get(i);
            String currentCity = this.currentPath.get(i + 1);
            this.sumDistance = this.sumDistance + ACO.disMatrix[Integer.parseInt(lastCity)][Integer.parseInt(currentCity)];
        }

        return this.sumDistance;
    }

    public void goToNextCity(String city) {
        this.currentPath.add(city);
        this.currentPos = city;
        this.nonVisitedCitys.remove(city);
        this.visitedCitys.add(city);
    }

    public boolean isBack() {
        boolean isBack = false;
        if (this.currentPath.size() != 0) {
            String startPos = this.currentPath.get(0);
            String endPos = this.currentPath.get(this.currentPath.size() - 1);
            if (this.currentPath.size() > 1 && startPos.equals(endPos)) {
                isBack = true;
            }

        }
        return isBack;
    }

    public boolean pathContained(String cityI, String cityJ) {
        boolean isContained = false;

        for(int i = 0; i < this.currentPath.size() - 1; ++i) {
            String lastCity = this.currentPath.get(i);
            String currentCity = this.currentPath.get(i + 1);
            if (lastCity.equals(cityI) && currentCity.equals(cityJ) || lastCity.equals(cityJ) && currentCity.equals(cityI)) {
                isContained = true;
                break;
            }
        }

        return isContained;
    }

    public int compareTo(Ant o) {
        return this.sumDistance.compareTo(o.sumDistance);
    }
}
