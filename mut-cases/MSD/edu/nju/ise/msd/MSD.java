//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.msd;

public class MSD {
    private static final int BITS_PER_BYTE = 8;
    private static final int BITS_PER_INT = 32;
    private static final int R = 256;
    private static final int CUTOFF = 15;

    private MSD() {
    }

    public static void sort(String[] a) {
        int n = a.length;
        String[] aux = new String[n];
        sort(a, 0, n - 1, 0, aux);
    }

    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();

        return d == s.length() ? -1 : s.charAt(d);
    }

    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
        if (hi <= lo + 15) {
            insertion(a, lo, hi, d);
        } else {
            int[] count = new int[258];

            int r;
            int c;
            for(r = lo; r <= hi; ++r) {
                c = charAt(a[r], d);
                ++count[c + 2];
            }

            for(r = 0; r < 257; ++r) {
                count[r + 1] += count[r];
            }

            for(r = lo; r <= hi; ++r) {
                c = charAt(a[r], d);
                int var10002 = c + 1;
                int var10004 = count[c + 1];
                count[var10002] = count[c + 1] + 1;
                aux[var10004] = a[r];
            }

            for(r = lo; r <= hi; ++r) {
                a[r] = aux[r - lo];
            }

            for(r = 0; r < 256; ++r) {
                sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
            }

        }
    }

    private static void insertion(String[] a, int lo, int hi, int d) {
        for(int i = lo; i <= hi; ++i) {
            for(int j = i; j > lo && less(a[j], a[j - 1], d); --j) {
                exch(a, j, j - 1);
            }
        }

    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean less(String v, String w, int d) {
        for(int i = d; i < Math.min(v.length(), w.length()); ++i) {
            if (v.charAt(i) < w.charAt(i)) {
                return true;
            }

            if (v.charAt(i) > w.charAt(i)) {
                return false;
            }
        }

        return v.length() < w.length();
    }

    public static void sort(int[] a) {
        int n = a.length;
        int[] aux = new int[n];
        sort(a, 0, n - 1, 0, aux);
    }

    private static void sort(int[] a, int lo, int hi, int d, int[] aux) {
        if (hi <= lo + 15) {
            insertion(a, lo, hi, d);
        } else {
            int[] count = new int[257];
            int mask = 255;
            int shift = 32 - 8 * d - 8;

            int r;
            int c;
            for(r = lo; r <= hi; ++r) {
                c = a[r] >> shift & mask;
                ++count[c + 1];
            }

            for(r = 0; r < 256; ++r) {
                count[r + 1] += count[r];
            }

            for(r = lo; r <= hi; ++r) {
                c = a[r] >> shift & mask;
                aux[count[c]++] = a[r];
            }

            for(r = lo; r <= hi; ++r) {
                a[r] = aux[r - lo];
            }

            if (d != 4) {
                if (count[0] > 0) {
                    sort(a, lo, lo + count[0] - 1, d + 1, aux);
                }

                for(r = 0; r < 256; ++r) {
                    if (count[r + 1] > count[r]) {
                        sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
                    }
                }

            }
        }
    }

    private static void insertion(int[] a, int lo, int hi, int d) {
        for(int i = lo; i <= hi; ++i) {
            for(int j = i; j > lo && a[j] < a[j - 1]; --j) {
                exch(a, j, j - 1);
            }
        }

    }

    private static void exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
