import java.util.ArrayList;

import static java.util.Collections.max;

public class Day01 extends Day {


    Day01() {
        super(1);
    }

    @Override
    int part1(String input) {
        // Traverse over the input breaking the string into substrings
        int start = 0; // Pointer to the current position in the string
        int end; // Location where the next line break can be found
        int caloriesSum = 0; // Accumulates the calories for an elf
        int max = 0; // The largest elf calorie count seen so far

        // This is a really cool way to do a loop using indexOf
        // it is slightly outside the subset as the two argument
        // indexOf method is not on the reference sheet. It could
        // be implemented by constantly dividing the string into
        // the calorieString and the remaining part.
        while ((end = input.indexOf("\n", start)) != -1) {
            // Extract the string representing one line
            String caloriesString = input.substring(start, end);

            // if we encounter a blank line the start and end values
            // will match. This means we should check this elf's
            // load against the max.
            if (end == start) {
                if (caloriesSum > max) {
                    max = caloriesSum;
                }
                // Don't forget to zero out the sum
                caloriesSum = 0;
            } else {
                // Otherwise just accumulate the calories
                caloriesSum += Integer.parseInt(caloriesString);
            }

            // Now move our pointer to the next character following the line break
            start = end + 1;
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
    int part2(String input) {
        // Instead of using our max algorithm we are going to keep track of all of
        // the elves calories by adding them into an ArrayList.
        // We use an ArrayList because we don't know the number of elves in advance
        int start = 0;
        int end;
        int caloriesSum = 0;
        ArrayList<Integer> elfCalories = new ArrayList<>();

        while ((end = input.indexOf("\n", start)) != -1) {
            String caloriesString = input.substring(start, end);
            if (end == start) {
                elfCalories.add(caloriesSum);
                caloriesSum = 0;
            } else {
                caloriesSum += Integer.parseInt(caloriesString);
            }
            start = end + 1;
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
