//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.bplustree;

import java.util.ArrayList;
import java.util.List;

class LeafNode<Value> extends Node<Value> {
    private List<Value> values;
    private LeafNode<Value> next;
    private LeafNode<Value> prev;

    public LeafNode(int totalKeys) {
        super(totalKeys);
        this.values = new ArrayList(this.t - 1);
    }

    public LeafNode(int totalKeys, LeafNode<Value> prev) {
        this(totalKeys);
        this.prev = prev;
        this.next = prev.next;
        if (this.next != null) {
            this.next.prev = this;
        }

        prev.next = this;
    }

    public LeafNode(int totalKeys, LeafNode<Value> prev, List<Integer> keys, List<Value> values) {
        this(totalKeys, prev);
        this.keys.addAll(keys);
        this.values.addAll(values);
    }

    public int order(int key) {
        return this.keys.indexOf(key);
    }

    public Value getValue(int key) {
        int index = this.order(key);
        return index != -1 ? this.values.get(index) : null;
    }

    private InsertionResult<Value> insertNonFull(int key, Value value) {
        return this.insertNonFull(key, value, this.findLessThanOrEqualToKey(key));
    }

    private InsertionResult<Value> insertNonFull(int key, Value value, int index) {
        if (!this.keys.isEmpty() && (index != this.keys.size() - 1 || this.keys.get(index).compareTo(key) >= 0)) {
            this.keys.add(index, key);
            this.values.add(index, value);
        } else {
            this.keys.add(key);
            this.values.add(value);
        }

        return new InsertionResult(this.calculateGap(index));
    }

    public InsertionResult<Value> insert(int key, Value value) {
        int indexForInsertion = this.findLessThanOrEqualToKey(key);
        InsertionResult<Value> resultFromNonFullInsertion = null;
        if (indexForInsertion != -1 && indexForInsertion < this.keys.size() && this.keys.get(indexForInsertion).equals(key)) {
            this.values.set(indexForInsertion, value);
        } else {
            if (this.keys.size() >= this.t - 1) {
                int mid;
                if (this.t % 2 == 0) {
                    mid = this.keys.size() / 2;
                } else {
                    mid = (this.keys.size() + 1) / 2;
                }

                LeafNode nextSibling;
                if (indexForInsertion <= mid) {
                    nextSibling = new LeafNode(this.t, this, this.keys.subList(mid, this.keys.size()), this.values.subList(mid, this.values.size()));
                    this.keys = new ArrayList(this.keys.subList(0, mid));
                    this.values = new ArrayList(this.values.subList(0, mid));
                    resultFromNonFullInsertion = this.insertNonFull(key, value);
                } else {
                    nextSibling = new LeafNode(this.t, this, this.keys.subList(mid + 1, this.keys.size()), this.values.subList(mid + 1, this.values.size()));
                    this.keys = new ArrayList(this.keys.subList(0, mid + 1));
                    this.values = new ArrayList(this.values.subList(0, mid + 1));
                    resultFromNonFullInsertion = nextSibling.insertNonFull(key, value);
                }

                return new InsertionResult(this.keys.get(this.keys.size() - 1), this, nextSibling, resultFromNonFullInsertion);
            }

            resultFromNonFullInsertion = this.insertNonFull(key, value, indexForInsertion);
        }

        return resultFromNonFullInsertion;
    }

    protected int calculateGap(int index) {
        int leftGap = Integer.MAX_VALUE;
        int rightGap = Integer.MAX_VALUE;
        if (index >= 0) {
            Integer nextKey = null;
            if (index + 1 < this.keys.size()) {
                nextKey = this.keys.get(index + 1);
            } else if (this.next != null) {
                nextKey = this.next.keys.get(0);
            }

            if (nextKey != null) {
                rightGap = Math.abs(this.keys.get(index) - nextKey);
            }

            Integer prevKey = null;
            if (index - 1 >= 0) {
                prevKey = this.keys.get(index - 1);
            } else if (this.prev != null) {
                prevKey = this.prev.keys.get(this.prev.keys.size() - 1);
            }

            if (prevKey != null) {
                leftGap = Math.abs(this.keys.get(index) - prevKey);
            }
        }

        return Math.min(rightGap, leftGap);
    }

    public LeafNode<Value> getNext() {
        return this.next;
    }

    public LeafNode<Value> getPrev() {
        return this.prev;
    }

    public String reverseToString() {
        StringBuilder sb = new StringBuilder();

        for(int i = this.keys.size() - 1; i >= 0; --i) {
            sb.append(this.keys.get(i) + ",");
        }

        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < this.keys.size() - 1; ++i) {
            sb.append(this.keys.get(i) + ",");
        }

        sb.append(this.keys.get(this.keys.size() - 1));
        return sb.toString();
    }
}
