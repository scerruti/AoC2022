import java.util.ArrayList;
import java.util.List;

public class Day02 extends Day {


    Day02() {
        super(2);
    }

    @Override
    int part1(List<String> input) {
        // Compute the total score
        int total = 0;

        // Traverse through the input
        for (String line: input) {
            // Split each line into the opponents move (A,B,C) and my move (X,Y,Z)
            int pos = line.indexOf(" ");
            String yourMove = line.substring(0, pos);
            String myMove = line.substring(pos + 1);

            total += score(myMove, yourMove) + value(myMove);
        }

        return total;
    }

    /**
     * Returns the value of the move
     * @param myMove X - rock, Y - paper, Z - scissors
     * @return rock = 1, paper = 2, scissors = 3
     */
    private int value(String myMove) {
        if (myMove.equals("X")) {
            return 1;
        } else if (myMove.equals("Y")) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Computes the score for rock, paper, scissors
     *
     * If I win, 6 points, if we draw 3 points, if you win 0 points
     *
     * @param myMove    X = rock, Y = paper, Z = scissors
     * @param yourMove  A = rock, B = paper, C = scissors
     * @return 0, 3 or 6 depending on outcome
     */
    private int score(String myMove, String yourMove) {
        //Draw
        if ((myMove.equals("X") && yourMove.equals("A")) ||
                (myMove.equals("Y") && yourMove.equals("B")) ||
                (myMove.equals("Z") && yourMove.equals("C"))) {
            return 3;
        }

        // I win
        else if ((myMove.equals("X") && yourMove.equals("C")) ||
                (myMove.equals("Y") && yourMove.equals("A")) ||
                (myMove.equals("Z") && yourMove.equals("B"))) {
            return 6;
        }

        // You win
        else {
            return 0;
        }
    }

    @Override
    int part2(List<String> input) {
        // Compute the total score
        int total = 0;

        // Traverse through the input
        for (String line: input) {
            // Split each line into the opponents move (A,B,C) and outcome (X,Y,Z)
            int pos = line.indexOf(" ");
            String yourMove = line.substring(0, pos);
            String outcome = line.substring(pos + 1);
            String myMove = computeMove(yourMove, outcome);

            total += score(myMove, yourMove) + value(myMove);
        }

        return total;
    }

    /**
     * Returns the move to generate the specified outcome when faced with the opponents move
     *
     * @param yourMove A - rock, B - paper, C - scissors
     * @param outcome  X - lose, Y - draw, Z - win
     * @return X - rock, Y - paper, Z - scissors
     */
    private String computeMove(String yourMove, String outcome) {
        // Opponent played rock
        if (yourMove.equals("A")) {
            // Lose
            if (outcome.equals("X")) {
                return "Z";
            }
            // Draw
            else if (outcome.equals("Y")) {
                return "X";
            }
            // Win
            else {
                return "Y";
            }
        }

        // Opponent played paper
        else if (yourMove.equals("B")) {
            // Lose
            if (outcome.equals("X")) {
                return "X";
            }
            // Draw
            else if (outcome.equals("Y")) {
                return "Y";
            }
            // Win
            else {
                return "Z";
            }
        }

        // Opponent played scissors
        else {
            // Lose
            if (outcome.equals("X")) {
                return "Y";
            }
            // Draw
            else if (outcome.equals("Y")) {
                return "Z";
            }
            // Win
            else {
                return "X";
            }
        }
    }
}
