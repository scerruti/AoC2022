import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public abstract class Day {

    private final int n;
    private final String part1Label;
    private final String part2Label;

    Day(int n) {
        this(n, "Part 1 result", "Part 2 result");
    }

    Day(int n, String part1Label, String part2Label) {
        this.n = n;
        this.part1Label = part1Label;
        this.part2Label = part2Label;
    }


    void solve() {

        List<String> input;
        try {
            input = readAllLines(Path.of(String.format("resources/Day%02d.txt", n)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("Running Day %d%n", n);

        long start1 = System.currentTimeMillis();
        int res1 = part1(input);
        long time1 = System.currentTimeMillis() - start1;
        System.out.printf("%s: %d%n", part1Label, res1);
        System.out.printf("Part 1 took %d ms%n", time1);

        long start2 = System.currentTimeMillis();
        int res2 = part2(input);
        long time2 = System.currentTimeMillis() - start2;
        System.out.printf("%s: %d%n", part2Label, res2);
        System.out.printf("Part 2 took %d ms%n", time2);
    }

    abstract int part1(List<String> input);

    abstract int part2(List<String> input);

}