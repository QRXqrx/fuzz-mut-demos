//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.sorting;

public final class Sorting {
    private static final int CUTOFF = 10;
    private static final int NUM_ITEMS = 1000;
    private static int theSeed = 1;

    public Sorting() {
    }

    public void insertionSort(int[] a) {
        for(int p = 1; p < a.length; ++p) {
            int tmp = a[p];

            int j;
            for(j = p; j > 0 && tmp < a[j - 1]; --j) {
                a[j] = a[j - 1];
            }

            a[j] = tmp;
        }

    }

    public boolean isSorted(int[] a) {
        for(int i = 0; i < a.length - 1; ++i) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }

        return true;
    }

    public static void quicksort(int[] a) {
        quicksort(a, 0, a.length - 1);
    }

    public static final void swapReferences(Object[] a, int index1, int index2) {
        Object tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }

    public static final void swap(int[] a, int index1, int index2) {
        int tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }

    private static int median3(int[] a, int left, int right) {
        int center = (left + right) / 2;
        if (a[center] < a[left]) {
            swap(a, left, center);
        }

        if (a[right] < a[left]) {
            swap(a, left, right);
        }

        if (a[right] < a[center]) {
            swap(a, center, right);
        }

        swap(a, center, right - 1);
        return a[right - 1];
    }

    private static void quicksort(int[] a, int left, int right) {
        if (left + 10 <= right) {
            int pivot = median3(a, left, right);
            int i = left;
            int j = right - 1;

            while(true) {
                while(true) {
                    ++i;
                    if (a[i] >= pivot) {
                        do {
                            --j;
                        } while(a[j] > pivot);

                        if (i >= j) {
                            swap(a, i, right - 1);
                            quicksort(a, left, i - 1);
                            quicksort(a, i + 1, right);
                            return;
                        }

                        swap(a, i, j);
                    }
                }
            }
        } else {
            insertionSort(a, left, right);
        }
    }

    private static void insertionSort(int[] a, int left, int right) {
        for(int p = left + 1; p <= right; ++p) {
            int tmp = a[p];

            int j;
            for(j = p; j > left && tmp < a[j - 1]; --j) {
                a[j] = a[j - 1];
            }

            a[j] = tmp;
        }

    }
}
