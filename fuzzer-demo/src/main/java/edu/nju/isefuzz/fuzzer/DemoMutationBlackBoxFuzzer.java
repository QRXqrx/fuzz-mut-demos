package edu.nju.isefuzz.fuzzer;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

public class DemoMutationBlackBoxFuzzer {


    /**
     * The entry point of fuzzing.
     */
    public static void main(String[] args) throws Exception {

        // Initialize. Parse args and prepare seed queue
        if (args.length != 3) {
            System.out.println("DemoMutationBlackBoxFuzzer: <classpath> <target_name> <out_dir>");
            System.exit(0);
        }
        String cp = args[0];
        String tn = args[1];
        File outDir = new File(args[2]);
        System.out.println("[FUZZER] cp: " + cp);
        System.out.println("[FUZZER] tn: " + tn);
        System.out.println("[FUZZER] outDir: " + outDir.getAbsolutePath());
        List<Seed> seedQueue = prepare();
//        List<Seed> crashingInputs = new ArrayList<>();

        // Dry-run phase. Run all the given seeds once.
//        List<Seed> seedQueue = prepare();

        // Main fuzzing loop.
        int fuzzRnd = 0;
        boolean findCrash = false;
        Set<ExecutionResult> observedRes = new HashSet<>();
        while (true) {
            // Seed scheduling: no seed prioritication and pick next seed. Update round number.
            Seed nextSeed = pickSeed(seedQueue, ++fuzzRnd);
            System.out.printf("[FUZZER] Pick seed `%s`, queue_size `%d`\n",
                    nextSeed, seedQueue.size());

            // Generate offspring inputs.
            Set<String> testInputs = generate(nextSeed);

            // Execute each test input.
            for (String ti : testInputs) {
                System.out.printf("[FUZZER] FuzzRnd No.%d, execute the target with input `%s`",
                        fuzzRnd, ti);
                ExecutionResult execRes = execute(cp, tn, ti);
                System.out.println(execRes.info);

                // Output analysis.
                // Update queue.
                Seed potentialSeed = new Seed(ti);
                if (seedQueue.contains(potentialSeed))
                    continue;
                // Identify crash
                if (execRes.isCrash()) {
                    // Exit directly once find a crash.
//                    System.out.printf("[FUZZER] Find a crashing input `%s`\n", ti);
//                    System.exit(0);
                    // Try to record these seeds.
                    findCrash = true;
                    potentialSeed.markCrashed();
                }
                // Identify favored seeds.
                if (!observedRes.contains(execRes)) {
                    potentialSeed.markFavored();
                    observedRes.add(execRes);
                    System.out.printf("[FUZZER] Find a favored seed `%s`\n", potentialSeed);
                }
                seedQueue.add(potentialSeed);
            }

            // Seed scheduling: seed retirement.
            if (seedQueue.size() > 500 || findCrash) {
                int oriSize = seedQueue.size();

                // Remove previously unfavored seeds.
                List<Seed> unfavoredSeeds = new ArrayList<>();
                seedQueue.stream()
                         .filter(s -> !s.isFavored)
                         .forEach(unfavoredSeeds::add);
                seedQueue.removeAll(unfavoredSeeds);
                System.out.printf("[FUZZER] Shrink queue, size: %d -> %d\n",
                        oriSize, seedQueue.size());

            }

            // Break to reach postprocess
            if (findCrash)
                break;

        } /* End of the main fuzzing loop */

        // Postprocess. Seed preservation (favored & crashed).
        postprocess(outDir, seedQueue);

    }

    /**
     * A simple ADT for seed inputs.
     */
    private static class Seed {

        String content;
        boolean isFavored;

        boolean isCrash;

        Seed(String content, boolean isFavored) {
            this.content = content;
            this.isFavored = isFavored;
            this.isCrash = false;
        }

        Seed(String content) {
            this(content, false);
        }

        public void markFavored() {
            this.isFavored = true;
        }

        public void markCrashed() {
            this.isCrash = true;
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof Seed)
                return ((Seed) that).content.equals(this.content);
            return false;
        }

        @Override
        public String toString() {
            String suffix = this.isFavored ? "@favored" : "@unfavored";
            return this.content + suffix;
        }
    }

    /**
     * An exemplified seed.
     */
//    static Seed initSeed = new Seed("abcde", true);
    static Seed initSeed = new Seed("helln", true);

    /**
     * The preparation stage for fuzzing. At this stage, we tend to
     * collect seeds to build and corpus and minimize the corpus to
     * produce a selective seed queue for fuzzing
     */
    private static List<Seed> prepare() {
        return new ArrayList<>(Collections.singletonList(initSeed));
    }


    /**
     * Pick the next seed. Avoid out of bound.
     */
    private static Seed pickSeed(List<Seed> seeds, int rnd) {
        int pos = (rnd - 1) % seeds.size();
        return seeds.get(pos);
    }

    /**
     * The essential component of a mutation-based fuzzer. This method
     * mutates the given seed once to produce an offspring test input.
     * Here the method implements a simple mutator by adding the character
     * at the given position by step. Besides, this method ensures the
     * mutated character is in [a-z];
     *
     * @param sCont the content of the parent seed input.
     * @param pos   the position of the character to be mutated
     * @param step  the step of character flipping.
     * @return an offspring test input
     */
    private static String mutate(String sCont, int pos, int step) {
        // Locate target character
        char[] charArr = sCont.toCharArray();
        char oriChar = charArr[pos];

        // Mutate this char and make sure the result is in [a-z].
        char mutChar = oriChar + step > 'z' ?
                (char) ((oriChar + step) % 'z' - 1 + 'a') :
                (char) (oriChar + step);

        // Replace the char and return offspring test input.
        charArr[pos] = mutChar;
        return new String(charArr);
    }

    /**
     * Call (different flavors of) mutation methods/mutators several times
     * to produce a set of test inputs for subsequent test executions. This
     * method also showcases a simple power scheduling. The power, i.e., the
     * number of mutations, is affected by the flag {@link Seed#isFavored}.
     * A favored seed is mutated 10 times as an unfavored seed.
     *
     * @param seed  the parent seed input
     * @return a set of offspring test inputs.
     */
    private static Set<String> generate(Seed seed) {

        // Draw seed content, i.e., the real test input.
        String sCont = seed.content;

        // Power scheduling.
        int basePower = 5;
        int power = seed.isFavored ? basePower * 10 : basePower;

        // Test generation.
        Set<String> testInputs = new HashSet<>(power);
        for (int i = 0; i < power; i++) {
            // Avoid array out of bound.
            int pos  = i % sCont.length();
            int step = i / sCont.length() + 1;
            // Mutate.
            testInputs.add(mutate(sCont, pos, step));
        }

        return testInputs;
    }


    /**
     * A simple wrapper for execution result
     */
    private static class ExecutionResult {
        String info;
        int exitVal;

        ExecutionResult(String info, int exitVal) {
            this.info = info;
            this.exitVal = exitVal;
        }

        public boolean isCrash() {
            return exitVal != 0;
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ExecutionResult)
                return ((ExecutionResult) that).info.equals(this.info);
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(info);
        }
    }


    /**
     * An execution method for Java-main fuzz targets/drivers. The method
     * execute the given fuzz target once and return the output of the
     * fuzz target.
     *
     * @param cp classpath to the fuzz target
     * @param tn target name, essentially the fully qualified name of a
     *           java class
     * @param ti (the content of) the test input
     * @return the output of the fuzz target.
     * @throws IOException if the executor starts wrongly.
     */
    private static ExecutionResult execute(
            String cp, String tn, String ti) throws IOException, InterruptedException {
        // Construct executor
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", cp, tn, ti);

        // Redirect execution result to here and execute.
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        // Wait for execution to finish, or we cannot get exit value.
        p.waitFor();

        // Read execution info
        StringBuilder infoBuilder = new StringBuilder();
        String line;
        while (true) {
            line = br.readLine();
            if (line == null)
                break;
            else
                infoBuilder.append('\n');
            infoBuilder.append(line);
        }

        // Wrap and return execution result
        return new ExecutionResult(infoBuilder.toString(), p.exitValue());
    }

    private static void postprocess(File outDir, List<Seed> seeds) throws IOException {
        // Delete old outDir
        if (outDir.exists()) {
            FileUtils.forceDelete(outDir);
            System.out.println("[FUZZER] Delete old output directory.");
        }
        boolean res = outDir.mkdirs();
        if (res)
            System.out.println("[FUZZER] Create output directory.");
        File queueDir = new File(outDir, "queue");
        File crashDir = new File(outDir, "crash");
        res = queueDir.mkdir();
        if (res)
            System.out.println("[FUZZER] Create queue directory: " + queueDir.getAbsolutePath());
        res = crashDir.mkdir();
        if (res)
            System.out.println("[FUZZER] Create crash directory: " + crashDir.getAbsolutePath());
        // Record seeds.
        for (Seed s : seeds) {
            File seedFile;
            if (s.isCrash)
                seedFile = new File(crashDir, s.content);
            else
                seedFile = new File(queueDir, s.content);
            FileWriter fw = new FileWriter(seedFile);
            fw.write(s.content);
            fw.close();
            System.out.println("[FUZZER] Write test input to: " + seedFile.getAbsolutePath());
        }

    }

}
