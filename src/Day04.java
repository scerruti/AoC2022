import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Day04 extends Day {


    private static final String DIGITS = "0123456789";

    Day04() {
        super(4);
    }

    public int part1(List<String> assignments) {
        int count = 0;

        for (String assignment : assignments) {
            int[] bounds = extracted(assignment);
            if (bounds[0] <= bounds[2] && bounds[1] >= bounds[3] ||
                bounds[2] <= bounds[0] && bounds[3] >= bounds[1]) {
                count += 1;
            }
        }

        return count;
    }

    private static int[] extracted(String assignment) {
        ArrayList<String> pairs = split(assignment, ",");
        ArrayList<String> elf1 = split(pairs.get(0), "-");
        ArrayList<String> elf2 = split(pairs.get(1), "-");

        return new int[] {parseInt(elf1.get(0)), parseInt(elf1.get(1)),
                parseInt(elf2.get(0)), parseInt(elf2.get(1))};
    }

    public static int parseInt(String number) {
        int value = 0;

        for (int i = 0; i < number.length(); i++) {
            value = value * 10;
            int placeValue = DIGITS.indexOf(number.substring(i,i+1));
            if (placeValue != -1) {
                value += placeValue;
            } else {
                throw new RuntimeException("Number parsing error for character " + i + " of " + number + ".");
            }
        }
        return value;
    }

    public static ArrayList<String> split(String string, String delimiter) {
        int pos = string.indexOf(delimiter);
        ArrayList<String> result = new ArrayList<>();

        String temp = string;

        while (pos != -1) {
            result.add(temp.substring(0, pos));
            temp = temp.substring(pos + 1);
            pos = temp.indexOf(delimiter);
        }
        result.add(temp);

        return result;
    }

    public int part2(List<String> assignments) {
        int count = 0;

        for (String assignment : assignments) {
            int[] bounds = extracted(assignment);
            if (bounds[1] >= bounds[2] && bounds[0] <= bounds[3] ) {
                count += 1;
            }
        }

        return count;
    }


}
