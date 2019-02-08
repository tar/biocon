package ru.bioinf.nina.two;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskZero {
    private static final String DATA_DIR = "data";
    private static final String TASK0 = "0";

    private static class Test {
        private String dna;
        private String motiff;

        public Test() {}

        public String solve() {
            char[] mot = motiff.toLowerCase().toCharArray();
            char[] arr = dna.toLowerCase().toCharArray();
            List<Integer> res = new LinkedList<>();
            for (int i = 0; i < arr.length; i++) {
                int temp = 0;
                while (i + temp < arr.length && temp < mot.length && arr[i + temp] == mot[temp]) {
                    temp++;
                }
                if (temp == mot.length) {
                    res.add(i);
                }
            }
            return res.stream().map(x -> Integer.valueOf((x + 1)).toString()).collect(Collectors.joining(" "));
        }

        public String getDna() {
            return dna;
        }

        public Test setDna(String dna) {
            this.dna = dna;
            return this;
        }

        public String getMotiff() {
            return motiff;
        }

        public Test setMotiff(String motiff) {
            this.motiff = motiff;
            return this;
        }

    }

    private static Test build(String dna, String motiff) {
        return new Test().setDna(dna).setMotiff(motiff);
    }

    public static void main(String[] args) {
        try {
            List<String> input = Files.readAllLines(Paths.get(DATA_DIR, TASK0, "input.txt"));
            List<Test> tests = new LinkedList<TaskZero.Test>();
            for (int i = 1; i < input.size(); i++) {
                tests.add(build(input.get(i), input.get(++i)));
            }
            String result = tests.stream().map(Test::solve).collect(Collectors.joining("\n"));
            Files.writeString(Paths.get(DATA_DIR, TASK0, "output.txt"), result, StandardOpenOption.CREATE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
