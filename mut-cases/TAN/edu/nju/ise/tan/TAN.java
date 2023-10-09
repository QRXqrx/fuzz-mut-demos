//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.tan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class TAN {
    private String filePath;
    private int attrNum;
    private String classAttrName;
    private String[] attrNames;
    private int[][] edges;
    private HashMap<String, Integer> attr2Column;
    private HashMap<String, ArrayList<String>> attr2Values;
    private ArrayList<Node> totalNodes;
    private ArrayList<String[]> totalDatas;

    public TAN(ArrayList<String[]> dataArray) {
        this.readDataFile(dataArray);
    }

    private void readDataFile(ArrayList<String[]> dataArray) {
        this.totalDatas = dataArray;
        this.attrNames = this.totalDatas.get(0);
        this.attrNum = this.attrNames.length;
        this.classAttrName = this.attrNames[this.attrNum - 1];
        this.edges = new int[this.attrNum][this.attrNum];
        this.totalNodes = new ArrayList();
        this.attr2Column = new HashMap();
        this.attr2Values = new HashMap();
        Node node = new Node(0, this.attrNames[this.attrNum - 1]);
        this.totalNodes.add(node);

        for(int i = 0; i < this.attrNames.length; ++i) {
            if (i < this.attrNum - 1) {
                node = new Node(i + 1, this.attrNames[i]);
                this.totalNodes.add(node);
            }

            this.attr2Column.put(this.attrNames[i], i);
        }

        for(int i = 1; i < this.totalDatas.size(); ++i) {
            String[] temp = this.totalDatas.get(i);

            for(int j = 0; j < temp.length; ++j) {
                ArrayList values;
                if (this.attr2Values.containsKey(this.attrNames[j])) {
                    values = this.attr2Values.get(this.attrNames[j]);
                } else {
                    values = new ArrayList();
                }

                if (!values.contains(temp[j])) {
                    values.add(temp[j]);
                }

                this.attr2Values.put(this.attrNames[j], values);
            }
        }

    }

    private Node constructWeightTree(ArrayList<Node[]> iArray) {
        ArrayList<Node> existNodes = new ArrayList();
        Iterator var6 = iArray.iterator();

        while(var6.hasNext()) {
            Node[] i = (Node[])var6.next();
            Node node1 = i[0];
            Node node2 = i[1];
            node1.connectNode(node2);
            this.addIfNotExist(node1, existNodes);
            this.addIfNotExist(node2, existNodes);
            if (existNodes.size() == this.attrNum - 1) {
                break;
            }
        }

        Node root = existNodes.get(0);
        return root;
    }

    private void confirmGraphDirection(Node currentNode) {
        ArrayList<Node> connectedNodes = currentNode.connectedNodes;
        int i = currentNode.id;
        Iterator var5 = connectedNodes.iterator();

        while(var5.hasNext()) {
            Node n = (Node)var5.next();
            int j = n.id;
            if (this.edges[i][j] == 0 && this.edges[j][i] == 0) {
                this.edges[i][j] = 1;
                this.confirmGraphDirection(n);
            }
        }

    }

    private void addParentNode() {
        Node parentNode = null;
        Iterator var2 = this.totalNodes.iterator();

        Node child;
        while(var2.hasNext()) {
            child = (Node)var2.next();
            if (child.id == 0) {
                parentNode = child;
                break;
            }
        }

        var2 = this.totalNodes.iterator();

        while(var2.hasNext()) {
            child = (Node)var2.next();
            parentNode.connectNode(child);
            if (child.id != 0) {
                this.edges[0][child.id] = 1;
            }
        }

    }

    public boolean addIfNotExist(Node node, ArrayList<Node> existNodes) {
        boolean canAdd = true;
        Iterator var4 = existNodes.iterator();

        while(var4.hasNext()) {
            Node n = (Node)var4.next();
            if (n.isEqual(node)) {
                canAdd = false;
                break;
            }
        }

        if (canAdd) {
            existNodes.add(node);
        }

        return canAdd;
    }

    private double calConditionPro(Node node, HashMap<String, String> queryParam) {
        double pro = 1.0;
        int id = node.id;
        ArrayList<Node> parentNodes = new ArrayList();
        ArrayList<String[]> priorAttrInfos = new ArrayList();
        ArrayList<String[]> backAttrInfos = new ArrayList();

        for(int i = 0; i < this.edges.length; ++i) {
            if (this.edges[i][id] == 1) {
                Iterator var12 = this.totalNodes.iterator();

                while(var12.hasNext()) {
                    Node temp = (Node)var12.next();
                    if (temp.id == i) {
                        parentNodes.add(temp);
                        break;
                    }
                }
            }
        }

        String value = queryParam.get(node.name);
        String[] attrValue = new String[]{node.name, value};
        priorAttrInfos.add(attrValue);
        Iterator var14 = parentNodes.iterator();

        while(var14.hasNext()) {
            Node p = (Node)var14.next();
            value = queryParam.get(p.name);
            attrValue = new String[]{p.name, value};
            backAttrInfos.add(attrValue);
        }

        pro = this.queryConditionPro(priorAttrInfos, backAttrInfos);
        return pro;
    }

    private double queryConditionPro(ArrayList<String[]> priorValues, ArrayList<String[]> backValues) {
        double pro = 0.0;
        double totalPro = 0.0;
        double backPro = 0.0;

        for(int i = 1; i < this.totalDatas.size(); ++i) {
            String[] tempData = this.totalDatas.get(i);
            boolean hasPrior = true;
            boolean hasBack = true;
            Iterator var14 = priorValues.iterator();

            int attrIndex;
            String[] array;
            while(var14.hasNext()) {
                array = (String[])var14.next();
                attrIndex = this.attr2Column.get(array[0]);
                if (!tempData[attrIndex].equals(array[1])) {
                    hasPrior = false;
                    break;
                }
            }

            var14 = backValues.iterator();

            while(var14.hasNext()) {
                array = (String[])var14.next();
                attrIndex = this.attr2Column.get(array[0]);
                if (!tempData[attrIndex].equals(array[1])) {
                    hasBack = false;
                    break;
                }
            }

            if (hasBack) {
                ++backPro;
                if (hasPrior) {
                    ++totalPro;
                }
            } else if (hasPrior && backValues.size() == 0) {
                ++totalPro;
                backPro = 1.0;
            }
        }

        if (backPro == 0.0) {
            pro = 0.0;
        } else {
            pro = totalPro / backPro;
        }

        return pro;
    }

    public double calHappenedPro(String queryParam) {
        double result = 1.0;
        HashMap<String, String> params = new HashMap();
        String[] array = queryParam.split(",");
        String[] var10 = array;
        int var11 = array.length;

        for(int var12 = 0; var12 < var11; ++var12) {
            String s = var10[var12];
            String[] array2 = s.split("=");
            params.put(array2[0], array2[1]);
        }

        String classAttrValue = params.get(this.classAttrName);
        this.constructBayesNetWork(classAttrValue);

        double temp;
        for(Iterator var14 = this.totalNodes.iterator(); var14.hasNext(); result *= temp) {
            Node n = (Node)var14.next();
            temp = this.calConditionPro(n, params);
            if (temp == 0.0) {
                temp = 0.001;
            }
        }

        return result;
    }

    private void constructBayesNetWork(String value) {
        ArrayList<Node[]> iArray = null;
        Node rootNode = null;
        Iterator var5 = this.totalNodes.iterator();

        while(var5.hasNext()) {
            Node n = (Node)var5.next();
            n.connectedNodes.clear();
        }

        this.edges = new int[this.attrNum][this.attrNum];
        iArray = new ArrayList();
        ArrayList<AttrMutualInfo> mInfoArray = this.calAttrMutualInfoArray(value);
        var5 = mInfoArray.iterator();

        while(var5.hasNext()) {
            AttrMutualInfo v = (AttrMutualInfo)var5.next();
            iArray.add(v.nodeArray);
        }

        rootNode = this.constructWeightTree(iArray);
        this.confirmGraphDirection(rootNode);
        this.addParentNode();
    }

    private ArrayList<AttrMutualInfo> calAttrMutualInfoArray(String value) {
        ArrayList<AttrMutualInfo> mInfoArray = new ArrayList();

        for(int i = 0; i < this.totalNodes.size() - 1; ++i) {
            Node node1 = this.totalNodes.get(i);
            if (node1.id != 0) {
                for(int j = i + 1; j < this.totalNodes.size(); ++j) {
                    Node node2 = this.totalNodes.get(j);
                    if (node2.id != 0) {
                        double iValue = this.calMutualInfoValue(node1, node2, value);
                        AttrMutualInfo mInfo = new AttrMutualInfo(iValue, node1, node2);
                        mInfoArray.add(mInfo);
                    }
                }
            }
        }

        Collections.sort(mInfoArray);
        return mInfoArray;
    }

    private double calMutualInfoValue(Node node1, Node node2, String value) {
        String[] array1 = new String[2];
        String[] array2;
        ArrayList<String[]> priorValues = new ArrayList<>();
        ArrayList<String[]> backValues = new ArrayList<>();
        double iValue = 0.0;
        array1[0] = this.classAttrName;
        array1[1] = value;
        backValues.add(array1);
        ArrayList<String> attrValues1 = this.attr2Values.get(node1.name);
        ArrayList<String> attrValues2 = this.attr2Values.get(node2.name);
        Iterator<String> var20 = attrValues1.iterator();

        while(var20.hasNext()) {
            String v1 = var20.next();

            double temp;
            for(Iterator var22 = attrValues2.iterator(); var22.hasNext(); iValue += temp) {
                String v2 = (String)var22.next();
                priorValues.clear();
                array1 = new String[]{node1.name, v1};
                priorValues.add(array1);
                array2 = new String[]{node2.name, v2};
                priorValues.add(array2);
                double pXiXj = this.queryConditionPro(priorValues, backValues);
                priorValues.clear();
                priorValues.add(array1);
                double pXi = this.queryConditionPro(priorValues, backValues);
                priorValues.clear();
                priorValues.add(array2);
                double pXj = this.queryConditionPro(priorValues, backValues);
                if (pXiXj != 0.0 && pXi != 0.0 && pXj != 0.0) {
                    temp = pXiXj * Math.log(pXiXj / (pXi * pXj)) / Math.log(2.0);
                } else {
                    temp = 0.0;
                }
            }
        }

        return iValue;
    }
}
