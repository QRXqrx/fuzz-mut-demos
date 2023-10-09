//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.binaryheap;

public class BinaryHeap {
    private static final int DEFAULT_CAPACITY = 100;
    private int currentSize;
    private int[] array;

    public BinaryHeap() {
        this(100);
    }

    public BinaryHeap(int capacity) {
        this.currentSize = 0;
        this.array = new int[capacity + 1];
    }

    public void insert(int x) throws Overflow {
        if (this.isFull()) {
            throw new Overflow();
        } else {
            int hole;
            for(hole = ++this.currentSize; hole > 1 && x < this.array[hole / 2]; hole /= 2) {
                this.array[hole] = this.array[hole / 2];
            }

            this.array[hole] = x;
        }
    }

    public int findMin() {
        return this.isEmpty() ? -1 : this.array[1];
    }

    boolean wellFormed() {
        if (this.array == null) {
            return false;
        } else if (this.currentSize >= 0 && this.currentSize < this.array.length) {
            for(int i = 1; i < this.currentSize; ++i) {
                if (i * 2 <= this.currentSize && this.array[i] > this.array[2 * i]) {
                    return false;
                }

                if (i * 2 + 1 <= this.currentSize && this.array[i] > this.array[2 * i + 1]) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public int deleteMin() {
        if (this.isEmpty()) {
            return -1;
        } else {
            int minItem = this.findMin();
            this.array[1] = this.array[this.currentSize--];
            this.percolateDown(1);
            return minItem;
        }
    }

    public void buildHeap() {
        for(int i = this.currentSize / 2; i > 0; --i) {
            this.percolateDown(i);
        }

    }

    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    public boolean isFull() {
        return this.currentSize == this.array.length - 1;
    }

    public void makeEmpty() {
        this.currentSize = 0;
    }

    private void percolateDown(int hole) {
        int child;
        int tmp;
        for(tmp = this.array[hole]; hole * 2 <= this.currentSize; hole = child) {
            child = hole * 2;
            if (child != this.currentSize && this.array[child + 1] < this.array[child]) {
                ++child;
            }

            if (this.array[child] >= tmp) {
                break;
            }

            this.array[hole] = this.array[child];
        }

        this.array[hole] = tmp;
    }
}
