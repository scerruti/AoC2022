import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Day10 extends Day {

    Day10() {
        super(10);
    }



    public int part1(List<String> input) {
        int x = 1;
        int clock = 1;
        int sum = 0;

        // Screen is used for part 2
        String[] screen = {
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................"
        };

        for (String line : input) {
            if (line.startsWith("noop")) {
                sum = report(clock, x, sum, screen);
                clock += 1;
            } else {
                sum = report(clock, x, sum, screen);
                clock += 1;
                sum = report(clock, x, sum, screen);
                clock += 1;
                x += Integer.parseInt(line.substring(line.indexOf(" ")+1));
            }
        }

        for (int row = 0; row < 6; row++) {
            System.out.println(screen[row]);
        }
        return sum;
    }

    public int part2(List<String> input) {
        // because of the nature of the problem, solved in part 1
        return 0;
    }

    private int report(int clock, int x, int sum, String[] screen) {
        if ((clock + 20) % 40 == 0) {
            sum += clock * x;
        }

        int pixel = (clock - 1) % 40 + 1;
        int line = (clock - 1) / 40;

        // Modifying the array of strings for part 2 in a method.
        if (x <= pixel && pixel <= x + 2) {
            screen[line] = screen[line].substring(0, pixel - 1) + "#" + screen[line].substring(pixel);
        }

        return sum;
    }


}
