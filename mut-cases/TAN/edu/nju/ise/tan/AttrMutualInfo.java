//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.tan;

public class AttrMutualInfo implements Comparable<AttrMutualInfo> {
    Double value;
    Node[] nodeArray;

    public AttrMutualInfo(double value, Node node1, Node node2) {
        this.value = value;
        this.nodeArray = new Node[2];
        this.nodeArray[0] = node1;
        this.nodeArray[1] = node2;
    }

    public int compareTo(AttrMutualInfo o) {
        return o.value.compareTo(this.value);
    }
}
