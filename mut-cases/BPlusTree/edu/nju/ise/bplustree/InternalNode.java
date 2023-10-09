//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.bplustree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class InternalNode<Value> extends Node<Value> {
    private List<Node<Value>> children;
    private int totalWeight;

    public InternalNode(int totalKeys) {
        super(totalKeys);
        this.totalWeight = 0;
        this.children = new ArrayList(this.t);
    }

    public InternalNode(int totalKeys, List<Integer> keys, List<Node<Value>> children) {
        this(totalKeys);
        this.keys.addAll(keys);
        this.children.addAll(children);

        Node child;
        for(Iterator var4 = children.iterator(); var4.hasNext(); this.totalWeight += child.getNodeSize()) {
            child = (Node)var4.next();
        }

    }

    public InternalNode(int totalKeys, InsertionResult<Value> split) {
        this(totalKeys);
        this.children.add(split.getLeftNode());
        this.children.add(split.getRightNode());
        this.keys.add(split.getSplitRootKey());
        this.totalWeight = split.getLeftNode().getNodeSize() + split.getRightNode().getNodeSize();
    }

    public int getNodeSize() {
        return this.totalWeight;
    }

    public int order(int key) {
        int orderIndex = 0;
        int childOrderIndex;
        if (this.keys.get(this.keys.size() - 1).compareTo(key) < 0) {
            orderIndex = this.totalWeight - this.children.get(this.children.size() - 1).getNodeSize();
            childOrderIndex = this.children.get(this.children.size() - 1).order(key);
            if (childOrderIndex == -1) {
                return -1;
            }

            orderIndex += childOrderIndex;
        } else {
            int i = 0;
            Integer currentKey = this.keys.get(i);

            while(i < this.keys.size() && currentKey.compareTo(key) < 0) {
                childOrderIndex = this.children.get(i).getNodeSize();
                if (childOrderIndex == -1) {
                    return -1;
                }

                orderIndex += childOrderIndex;
                if (i < this.keys.size() - 1) {
                    ++i;
                    currentKey = this.keys.get(i);
                } else {
                    ++i;
                }
            }

            orderIndex += this.children.get(i).order(key);
        }

        return orderIndex;
    }

    public InsertionResult<Value> insert(int key, Value value) {
        Node<Value> child = this.getChildNode(key);
        this.totalWeight -= child.getNodeSize();
        InsertionResult<Value> split = child.insert(key, value);
        this.totalWeight += child.getNodeSize();
        if (split != null && split.getSplitRootKey() != null) {
            int indexForInsertion = this.findLessThanOrEqualToKey(split.getSplitRootKey());
            if (this.keys.size() >= this.t - 1) {
                int mid;
                if (this.t % 2 == 0) {
                    mid = this.keys.size() / 2;
                } else {
                    mid = (this.keys.size() + 1) / 2;
                }

                InternalNode nextSibling;
                if (indexForInsertion <= mid) {
                    nextSibling = new InternalNode(this.t, this.keys.subList(mid, this.keys.size()), this.children.subList(mid, this.children.size()));
                    this.keys = new ArrayList(this.keys.subList(0, mid));
                    this.children = new ArrayList(this.children.subList(0, mid));
                    this.totalWeight -= nextSibling.getNodeSize();
                    this.insertNonFull(split);
                } else {
                    nextSibling = new InternalNode(this.t, this.keys.subList(mid + 1, this.keys.size()), this.children.subList(mid + 1, this.children.size()));
                    this.keys = new ArrayList(this.keys.subList(0, mid + 1));
                    this.children = new ArrayList(this.children.subList(0, mid + 1));
                    this.totalWeight -= nextSibling.getNodeSize();
                    nextSibling.insertNonFull(split);
                }

                Integer newRoot = this.keys.get(this.keys.size() - 1);
                this.keys.remove(this.keys.size() - 1);
                return new InsertionResult(newRoot, this, nextSibling, split);
            }

            this.insertNonFull(split, indexForInsertion);
        }

        return new InsertionResult(split.getMinGap());
    }

    private void insertNonFull(InsertionResult<Value> split) {
        this.insertNonFull(split, this.findLessThanOrEqualToKey(split.getSplitRootKey()));
    }

    private void insertNonFull(InsertionResult<Value> split, int index) {
        if (!this.keys.isEmpty() && index != this.keys.size()) {
            this.keys.add(index, split.getSplitRootKey());
            this.setChild(index, split.getLeftNode());
            if (index == 0) {
                this.addChild(index + 1, split.getRightNode());
            } else {
                this.addChild(index + 1, split.getRightNode());
            }
        } else {
            this.keys.add(split.getSplitRootKey());
            if (this.children.isEmpty()) {
                this.addChild(split.getLeftNode());
            } else {
                this.setChild(this.children.size() - 1, split.getLeftNode());
            }

            this.addChild(split.getRightNode());
        }

    }

    private void addChild(int index, Node<Value> node) {
        this.children.add(index, node);
        this.totalWeight += node.getNodeSize();
    }

    private void addChild(Node<Value> node) {
        this.children.add(node);
        this.totalWeight += node.getNodeSize();
    }

    private void setChild(int index, Node<Value> node) {
        this.totalWeight -= this.children.get(index).getNodeSize();
        this.children.set(index, node);
        this.totalWeight += node.getNodeSize();
    }

    public Node<Value> getChildNode(int key) {
        return this.children.get(this.findLessThanOrEqualToKey(key));
    }

    public Node<Value> getMaxChildNode() {
        return this.children.get(this.children.size() - 1);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < this.children.size() - 1; ++i) {
            sb.append(this.children.get(i).toString() + "#");
        }

        sb.append(this.children.get(this.children.size() - 1).toString());
        return sb.toString();
    }
}
