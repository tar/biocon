package ru.bioinf.nina.two;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class TaskTwo {
    private static final String DATA_DIR = "data";
    private static final String TASK = "2";
    private static final double E = 0.000001;

    private static class Test {
        private int l = 0;
        private int n = 0;
        private double p = 0.0;
        private int k = 0;
        Random rand = new Random();

        public Test() {}

        char[] alphabet = new char[] { 'A', 'T', 'C', 'G' };
        Map<Character, Character> pairs = new HashMap<Character, Character>();
        {
            pairs.put('A', 'T');
            pairs.put('T', 'A');
            pairs.put('C', 'G');
            pairs.put('G', 'C');
        }

        char[] generate(int l) {
            char[] res = new char[l];
            for (int i = 0; i < res.length; i++) {
                res[i] = alphabet[Math.abs(rand.nextInt()) % 4];
            }
            return res;
        }

        Character[] read(char[] source, int l, int n, double p, int k) {
            String[] res = new String[l];
            for (int i = 1; i <= k; i++) {
                int start = Math.abs(rand.nextInt()) % l;
                for (int j = 0; j < n; j++) {
                    int pos = (start + j) < l ? start + j : j - 1;
                    char read = source[pos];
                    if ((p > E || p < -E) && rand.nextDouble() <= p) {
                        read = pairs.get(read);
                    }
                    res[pos] = res[pos] + read;
                }
            }
            return Arrays.stream(res).map((String x) -> {
                if (isNullOrEmpry(x)) {
                    return alphabet[Math.abs(rand.nextInt()) % 4];
                }else {
                    Map<Character, Integer> counts = new HashMap<>();
                    counts.put('A', 0);
                    counts.put('T', 0);
                    counts.put('C', 0);
                    counts.put('G', 0);
                    char gg = 0;
                    for (char gen : x.toCharArray()) {
                        gg = gen;
                        counts.put(gen, counts.getOrDefault(gen, 0) + 1);
                    }
                    if (counts.get(gg) < counts.get(pairs.get(gg))) {
                        return pairs.get(gg);
                    }else if (counts.get(gg) > counts.get(pairs.get(gg))) {
                        return gg;
                    } else {
                        if (rand.nextDouble() >= 0.5) {
                            return pairs.get(gg);
                        } else {
                            return gg;
                        }
                    }
                }
            }).toArray(Character[]::new);
        }

        private boolean isNullOrEmpry(String x) {
            if (x != null)
                return x.isEmpty();
            return true;
        }

        int countErrors(char[] source, Character[] read) {
            int res = 0;
            for (int i = 0; i < source.length; i++) {
                if (source[i] != read[i]) {
                    res++;
                }
            }
            return res;
        }

        public String solve() {

            //            double res = 0.0;
            //            res = 0.75 * Math.pow((l - n) * 1.0 / l * 1.0, k * 1.0);
            //            if (p < E && p > -E) {
            //                return String.valueOf(res * l);
            //            } else {
            //
            int expCount = 1000000;
                int errors = 0;
                for (int i = 0; i < expCount; i++) {
                    char[] source = generate(l);
                    Character[] read = read(source, l, n, p, k);
                    errors += countErrors(source, read);
                }
                return String.valueOf(errors * 1.0 / expCount * 1.0);

                //                double temp = 0.0;
                //                for (int i = 1; i <= k; i++) {
                //                    double tempp = 0.0;
                //                    if (k % 2 == 0 && i == k / 2) {
                //                        tempp += Math.pow(p, k / 2.0)* Math.pow(1-p, k / 2.0) * 0.5;
                //                    }
                //                    if (i >= k / 2 + 1) {
                //                        for (int j = i / 2 + 1; j <= i; j++) {
                //                            tempp += Math.pow(p, j * 1.0);
                //                        }
                //                    }
                //                    temp += Math.pow(1.0 * n / l, i) * tempp;
                //                }
                //                res = res + temp;
            //            }
            //            return String.valueOf(res * l);
        }

        public int getL() {
            return l;
        }

        public Test setL(int l) {
            this.l = l;
            return this;
        }

        public int getN() {
            return n;
        }

        public Test setN(int n) {
            this.n = n;
            return this;
        }

        public double getP() {
            return p;
        }

        public Test setP(double p) {
            this.p = p;
            return this;
        }

        public int getK() {
            return k;
        }

        public Test setK(int k) {
            this.k = k;
            return this;
        }

    }

    private static Test build(int l, int n, double p, int k) {
        return new Test().setL(l).setN(n).setP(p).setK(k);
    }

    public static void main(String[] args) {
        try {
            List<String> input = Files.readAllLines(Paths.get(DATA_DIR, TASK, "input.txt"));
            List<Test> tests = new LinkedList<TaskTwo.Test>();
            for (int i = 1; i < input.size(); i++) {
                String[] test = input.get(i).split(" ");
                tests.add(build(Integer.valueOf(test[0]), Integer.valueOf(test[1]), Double.valueOf(test[2]), Integer.valueOf(test[3])));
            }
            String result = tests.stream().map(Test::solve).collect(Collectors.joining("\n"));
            System.out.println(result);
            Files.writeString(Paths.get(DATA_DIR, TASK, "output.txt"), result, StandardOpenOption.CREATE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
