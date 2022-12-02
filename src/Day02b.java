import java.util.List;

public class Day02b extends Day {


    Day02b() {
        super(2);
    }

    @Override
    int part1(List<String> input) {
        // Compute the total score
        int total = 0;

        // Traverse through the input
        for (String line: input) {
            // It is reasonable to assume that yourMove is char(0) and myMove is char(2)
            int yourMove = (int) line.charAt(0) - (int) 'A';
            int myMove = (int) line.charAt(2) - (int) 'X';

            total += 3 * ((myMove - yourMove + 4) % 3) + myMove + 1;
        }

        return total;
    }

    @Override
    int part2(List<String> input) {
        // Compute the total score
        int total = 0;


        // Traverse through the input
        for (String line: input) {
            // It is reasonable to assume that yourMove is char(0) and myMove is char(2)
            int yourMove = (int) line.charAt(0) - (int) 'A';
            int outcome = (int) line.charAt(2) - (int) 'X';
            int myMove = (outcome + yourMove - 1) % 3;

            total += 3 * ((myMove - yourMove + 4) % 3)  + myMove + 1;
        }

        return total;
    }
}
