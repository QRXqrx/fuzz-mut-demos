//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.bplustree;

public class BPlusTree<Value> {
    private int t;
    private Node<Value> root;
    private LeafNode<Value> first;
    private int minGap;
    private IntegerBloomFilter filter;

    public BPlusTree(int treeParam) {
        this.filter = null;
        this.t = treeParam;
        this.minGap = Integer.MAX_VALUE;
        this.root = new LeafNode(treeParam);
        this.first = (LeafNode)this.root;
    }

    public BPlusTree(int treeParam, int expectedNumberOfElements) {
        this(treeParam);
        this.filter = new IntegerBloomFilter(1.0E-6, expectedNumberOfElements);
    }

    private LeafNode<Value> findLeaf(int key) {
        Node current;
        for(current = this.root; !(current instanceof LeafNode); current = ((InternalNode)current).getChildNode(key)) {
        }

        return (LeafNode)current;
    }

    public Value search(int key) {
        boolean continueToSearch = true;
        if (this.filter != null) {
            continueToSearch = this.filter.contains(key);
        }

        if (continueToSearch) {
            return this.findLeaf(key).getValue(key);
        } else {
            System.out.println("Filter stopped the search.");
            System.out.println("Current False Positive Probability: " + this.filter.getCurrentFalsePositiveProbability());
            return null;
        }
    }

    public void insert(int key, Value value) {
        InsertionResult<Value> result = this.root.insert(key, value);
        if (result != null && result.getSplitRootKey() != null) {
            this.root = new InternalNode(this.t, result);
        }

        if (result != null && result.getMinGap() < this.minGap) {
            this.minGap = result.getMinGap();
        }

        if (this.filter != null) {
            this.filter.add(key);
        }

    }

    public String reverseInOrder() {
        Node current;
        for(current = this.root; !(current instanceof LeafNode); current = ((InternalNode)current).getMaxChildNode()) {
        }

        LeafNode<Value> leaf = (LeafNode)current;

        StringBuilder sb;
        for(sb = new StringBuilder(); leaf != null; leaf = leaf.getPrev()) {
            sb.append(leaf.reverseToString());
        }

        return sb.toString();
    }

    public int getSize() {
        return this.root.getNodeSize();
    }

    public int getMinGap() {
        return this.minGap;
    }

    public int order(int key) {
        int orderIndex = this.root.order(key);
        return orderIndex != -1 ? orderIndex + 1 : -1;
    }

    public String inOrder() {
        StringBuilder sb = new StringBuilder();

        for(LeafNode<Value> current = this.first; current != null; current = current.getNext()) {
            sb.append(current);
        }

        return sb.toString();
    }

    public String toString() {
        return this.root.getNodeSize() != 0 ? this.root.toString() : "";
    }
}
