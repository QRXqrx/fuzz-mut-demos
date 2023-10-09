//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.tan;

import java.util.ArrayList;

public class Node {
    int id;
    String name;
    ArrayList<Node> connectedNodes;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
        this.connectedNodes = new ArrayList();
    }

    public void connectNode(Node node) {
        if (this.id != node.id) {
            this.connectedNodes.add(node);
            node.connectedNodes.add(this);
        }
    }

    public boolean isEqual(Node node) {
        boolean isEqual = false;
        if (this.id == node.id) {
            isEqual = true;
        }

        return isEqual;
    }
}
