//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.bplustree;

class InsertionResult<Value> {
    private final Integer splitRootKey;
    private final Node<Value> left;
    private final Node<Value> right;
    private int minGap;

    public InsertionResult(Integer key, Node<Value> left, Node<Value> right) {
        this.splitRootKey = key;
        this.left = left;
        this.right = right;
    }

    public InsertionResult(Integer key, Node<Value> left, Node<Value> right, InsertionResult<Value> resultFromNonFullNode) {
        this(key, left, right);
        this.minGap = resultFromNonFullNode.getMinGap();
    }

    public InsertionResult(int minGap) {
        this(null, null, null);
        this.minGap = minGap;
    }

    public Node<Value> getLeftNode() {
        return this.left;
    }

    public Node<Value> getRightNode() {
        return this.right;
    }

    public Integer getSplitRootKey() {
        return this.splitRootKey;
    }

    public int getMinGap() {
        return this.minGap;
    }
}
