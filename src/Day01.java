import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.max;

public class Day01 extends Day {


    Day01() {
        super(1);
    }

    @Override
    int part1(List<String> input) {
        int caloriesSum = 0; // Accumulates the calories for an elf
        int max = 0; // The largest elf calorie count seen so far

        // Look at each line of input
        for (String line: input) {

            // if we encounter a blank line the start and end values
            // will match. This means we should check this elf's
            // load against the max.
            if (line.length() == 0) {
                if (caloriesSum > max) {
                    max = caloriesSum;
                }
                // Don't forget to zero out the sum
                caloriesSum = 0;
            } else {
                // Otherwise just accumulate the calories
                caloriesSum += Integer.parseInt(line);
            }
        }

        // Don't forget to check if the last elf is carrying the most
        // This is required because there is no blank line at the end
        if (caloriesSum > max) {
            max = caloriesSum;
        }

        // return the largest calorie load
        return max;
    }

    @Override
    int part2(List<String> input) {
        // Instead of using our max algorithm we are going to keep track of all of
        // the elves calories by adding them into an ArrayList.
        // We use an ArrayList because we don't know the number of elves in advance
        int caloriesSum = 0;
        ArrayList<Integer> elfCalories = new ArrayList<>();

        for (String line: input) {
            if (line.length() == 0) {
                elfCalories.add(caloriesSum);
                caloriesSum = 0;
            } else {
                caloriesSum += Integer.parseInt(line);
            }
        }

        // Don't forget to add the last elf
        elfCalories.add(caloriesSum);

        // Now find the top three values
        // This is done by finding the maximum value and then removing it three times
        Integer temp = max(elfCalories);
        elfCalories.remove(temp);
        int result = temp;

        temp = max(elfCalories);
        elfCalories.remove(temp);
        result += temp;

        temp = max(elfCalories);
        // elfCalories.remove(temp); // This isn't necessary.
        result += temp;

        return result;
    }

    /**
     * returns the maximum value from an ArrayList of Integers.
     * There are better ways to do this, but this is APCSA subset compliant.
     *
     * Precondition: ArrayList has at least one element
     *
     * @param values    an ArrayList of Integers
     * @return          the largest Integer in the ArrayList
     */
    public Integer max(ArrayList<Integer> values) {
        Integer max = values.get(0);
        for (Integer value : values) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }
}
