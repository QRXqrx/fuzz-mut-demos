//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.aco;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class ACO {
    public static final int INPUT_CITY_NAME = 1;
    public static final int INPUT_CITY_DIS = 2;
    public static double[][] disMatrix;
    public static int currentTime;
    private String filePath;
    private final int antNum;
    private final double alpha;
    private final double beita;
    private final double p;
    private final double Q;
    private Random random;
    private ArrayList<String> totalCitys;
    private ArrayList<Ant> totalAnts;
    private double[][] pheromoneMatrix;
    private ArrayList<String> bestPath;
    private Map<String, Double> pheromoneTimeMap;

    public ACO(ArrayList<String[]> dataArray, int antNum, double alpha, double beita, double p, double Q) {
        this.antNum = antNum;
        this.alpha = alpha;
        this.beita = beita;
        this.p = p;
        this.Q = Q;
        currentTime = 0;
        this.readDataFile(dataArray);
    }

    private void readDataFile(ArrayList<String[]> dataArray) {
        int flag = -1;
        this.totalCitys = new ArrayList();
        Iterator var6 = dataArray.iterator();

        while(true) {
            while(var6.hasNext()) {
                String[] array = (String[])var6.next();
                if (array[0].equals("#") && this.totalCitys.size() == 0) {
                    flag = 1;
                } else if (array[0].equals("#") && this.totalCitys.size() > 0) {
                    int size = this.totalCitys.size();
                    disMatrix = new double[size + 1][size + 1];
                    this.pheromoneMatrix = new double[size + 1][size + 1];

                    for(int i = 0; i < size; ++i) {
                        for(int j = 0; j < size; ++j) {
                            disMatrix[i][j] = -1.0;
                            this.pheromoneMatrix[i][j] = -1.0;
                        }
                    }

                    flag = 2;
                } else if (flag == 1) {
                    this.totalCitys.add(array[0]);
                } else {
                    int src = Integer.parseInt(array[0]);
                    int des = Integer.parseInt(array[1]);
                    disMatrix[src][des] = Double.parseDouble(array[2]);
                    disMatrix[des][src] = Double.parseDouble(array[2]);
                }
            }

            return;
        }
    }

    private double calIToJProbably(String cityI, String cityJ, int currentTime) {
        double pro = 0.0;
        double n = 0.0;
        int i = Integer.parseInt(cityI);
        int j = Integer.parseInt(cityJ);
        double pheromone = this.getPheromone(currentTime, cityI, cityJ);
        n = 1.0 / disMatrix[i][j];
        if (pheromone == 0.0) {
            pheromone = 1.0;
        }

        pro = Math.pow(n, this.alpha) * Math.pow(pheromone, this.beita);
        return pro;
    }

    public String selectAntNextCity(Ant ant, int currentTime) {
        String nextCity = null;
        if (ant.currentPath.size() == 0) {
            nextCity = String.valueOf(this.random.nextInt(this.totalCitys.size()) + 1);
            return nextCity;
        } else if (ant.nonVisitedCitys.isEmpty()) {
            nextCity = ant.currentPath.get(0);
            return nextCity;
        } else {
            double proTotal = 0.0;
            ArrayList<String> allowedCitys = ant.nonVisitedCitys;
            double[] proArray = new double[allowedCitys.size()];

            int j;
            for(j = 0; j < allowedCitys.size(); ++j) {
                nextCity = allowedCitys.get(j);
                proArray[j] = this.calIToJProbably(ant.currentPos, nextCity, currentTime);
                proTotal += proArray[j];
            }

            for(j = 0; j < allowedCitys.size(); ++j) {
                proArray[j] /= proTotal;
            }

            double randomNum = this.random.nextInt(100) + 1;
            randomNum /= 100.0;
            if (randomNum == 1.0) {
                randomNum -= 0.01;
            }

            double tempPro = 0.0;

            for(j = 0; j < allowedCitys.size(); ++j) {
                if (randomNum > tempPro && randomNum <= tempPro + proArray[j]) {
                    nextCity = allowedCitys.get(j);
                    break;
                }

                tempPro += proArray[j];
            }

            return nextCity;
        }
    }

    private double getPheromone(int t, String cityI, String cityJ) {
        double pheromone = 0.0;
        String key = MessageFormat.format("{0},{1},{2}", cityI, cityJ, t);
        if (this.pheromoneTimeMap.containsKey(key)) {
            pheromone = this.pheromoneTimeMap.get(key);
        }

        return pheromone;
    }

    private void refreshPheromone(int t) {
        double pheromone = 0.0;
        double lastTimeP = 0.0;
        Iterator var9 = this.totalCitys.iterator();

        label41:
        while(var9.hasNext()) {
            String i = (String)var9.next();
            Iterator var11 = this.totalCitys.iterator();

            while(true) {
                String j;
                do {
                    if (!var11.hasNext()) {
                        continue label41;
                    }

                    j = (String)var11.next();
                } while(i.equals(j));

                String key = MessageFormat.format("{0},{1},{2}", i, j, t - 1);
                if (this.pheromoneTimeMap.containsKey(key)) {
                    lastTimeP = this.pheromoneTimeMap.get(key);
                } else {
                    lastTimeP = 0.0;
                }

                double addPheromone = 0.0;
                Iterator var13 = this.totalAnts.iterator();

                while(var13.hasNext()) {
                    Ant ant = (Ant)var13.next();
                    if (ant.pathContained(i, j)) {
                        addPheromone += this.Q / ant.calSumDistance();
                    }
                }

                pheromone = this.p * lastTimeP + addPheromone;
                key = MessageFormat.format("{0},{1},{2}", i, j, t);
                this.pheromoneTimeMap.put(key, pheromone);
            }
        }

    }

    public void antStartSearching(int loopCount) {
        int count = 0;
        String selectedCity = "";
        this.pheromoneTimeMap = new HashMap();
        this.totalAnts = new ArrayList();

        Iterator var4;
        for(this.random = new Random(); count < loopCount; ++count) {
            this.initAnts();

            do {
                var4 = this.totalAnts.iterator();

                while(var4.hasNext()) {
                    Ant ant = (Ant)var4.next();
                    selectedCity = this.selectAntNextCity(ant, currentTime);
                    ant.goToNextCity(selectedCity);
                }
            } while(!this.totalAnts.get(0).isBack());

            ++currentTime;
            this.refreshPheromone(currentTime);
        }

        Collections.sort(this.totalAnts);
        this.bestPath = this.totalAnts.get(0).currentPath;
        System.out.println(MessageFormat.format("经过{0}次循环遍历，最终得出的最佳路径：", count));
        System.out.print("entrance");
        var4 = this.bestPath.iterator();

        while(var4.hasNext()) {
            String cityName = (String)var4.next();
            System.out.print(MessageFormat.format("-->{0}", cityName));
        }

    }

    private void initAnts() {
        this.totalAnts.clear();

        for(int i = 0; i < this.antNum; ++i) {
            ArrayList<String> nonVisitedCitys = (ArrayList)this.totalCitys.clone();
            Ant tempAnt = new Ant(this.pheromoneMatrix, nonVisitedCitys);
            this.totalAnts.add(tempAnt);
        }

    }
}
