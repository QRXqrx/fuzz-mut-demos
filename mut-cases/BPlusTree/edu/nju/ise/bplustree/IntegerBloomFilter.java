//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.bplustree;

import java.util.BitSet;

public class IntegerBloomFilter {
    private final BitSet bitset;
    private final int bitSetSize;
    private final int bitsPerElement;
    private final int expectedNumberOfElements;
    private int totalElements;
    private final int k;
    private final int hashParam1;
    private final int hashParam2;

    public IntegerBloomFilter(int bitsPerElement, int expectedNumberOfElements, int totalHashFunctions) {
        this.expectedNumberOfElements = expectedNumberOfElements;
        this.k = totalHashFunctions;
        this.bitsPerElement = bitsPerElement;
        this.bitSetSize = bitsPerElement * expectedNumberOfElements;
        this.totalElements = 0;
        this.bitset = new BitSet(this.bitSetSize);
        this.hashParam1 = (int)(Math.random() * 100.0);
        this.hashParam2 = (int)(Math.random() * 100.0);
    }

    public IntegerBloomFilter(double falsePositiveProbability, int expectedNumberOfElements) {
        this((int)Math.ceil(-(Math.log(falsePositiveProbability) / (Math.log(2.0) * Math.log(2.0)))), expectedNumberOfElements, (int)Math.ceil(-Math.log(falsePositiveProbability) / Math.log(2.0)));
    }

    public double getExpectedFalsePositiveProbability() {
        return this.getFalsePositiveProbability(this.expectedNumberOfElements);
    }

    public double getFalsePositiveProbability(double elementsNumber) {
        return Math.pow(1.0 - Math.exp((double)(-this.k) * elementsNumber / (double)this.bitSetSize), this.k);
    }

    public double getCurrentFalsePositiveProbability() {
        return this.getFalsePositiveProbability(this.totalElements);
    }

    public void clear() {
        this.bitset.clear();
        this.totalElements = 0;
    }

    private int[] createHashes(int data, int hashes) {
        int[] hashValues = new int[hashes];

        for(int i = 0; i < hashes; ++i) {
            hashValues[i] = ((i + this.hashParam1) * data + this.hashParam2) % 701;
        }

        return hashValues;
    }

    public void add(int element) {
        int[] hashes = this.createHashes(element, this.k);
        int[] var3 = hashes;
        int var4 = hashes.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int hash = var3[var5];
            this.bitset.set(Math.abs(hash % this.bitSetSize), true);
        }

        ++this.totalElements;
    }

    public boolean contains(int element) {
        int[] hashes = this.createHashes(element, this.k);
        int[] var3 = hashes;
        int var4 = hashes.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int hash = var3[var5];
            if (!this.bitset.get(Math.abs(hash % this.bitSetSize))) {
                return false;
            }
        }

        return true;
    }

    public int getBitsPerElement() {
        return this.bitsPerElement;
    }

    public int getFilterSize() {
        return this.bitSetSize;
    }

    public int getTotalHashFunctions() {
        return this.k;
    }
}
