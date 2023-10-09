//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.bplustree;

import java.util.ArrayList;
import java.util.List;

abstract class Node<Value> {
    protected int t;
    protected List<Integer> keys;

    Node(int totalKeys) {
        this.t = totalKeys;
        this.keys = new ArrayList(this.t - 1);
    }

    public abstract InsertionResult<Value> insert(int var1, Value var2);

    public abstract int order(int var1);

    public int getNodeSize() {
        return this.keys.size();
    }

    protected int findLessThanOrEqualToKey(int key) {
        if (this.keys.size() > 0 && this.keys.get(this.keys.size() - 1).compareTo(key) < 0) {
            return this.keys.size();
        } else if (this.keys.size() > 0 && this.keys.get(0).compareTo(key) >= 0) {
            return 0;
        } else {
            int from = 0;
            int to = this.keys.size() - 1;

            while(from <= to) {
                int mid = (from + to) / 2;
                if (mid < this.keys.size() - 1 && this.keys.get(mid).compareTo(key) < 0 && this.keys.get(mid + 1).compareTo(key) > 0) {
                    return mid + 1;
                }

                if (this.keys.get(mid).compareTo(key) > 0) {
                    to = mid - 1;
                } else {
                    if (this.keys.get(mid).compareTo(key) >= 0) {
                        return mid;
                    }

                    from = mid + 1;
                }
            }

            return -1;
        }
    }
}
