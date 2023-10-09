package edu.nju.isefuzz;

/**
 * Util class provide capture methods
 */
public class CovUtil {


    private CovUtil() {}

    public static void printCov() {

        System.out.println("Reach here!");

    }

    /**
     * Logger util for executed lines
     *
     * @param fn filename
     * @param ln linenumber
     */
    public static void executeLine(String fn, int ln) {
        System.out.println(fn + ":" + ln);
    }




}
