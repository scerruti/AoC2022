import java.util.List;

@SuppressWarnings("unused")
public class Day08 extends Day {

    private int[][] trees;

    Day08() {
        super(8);
    }



    public int part1(List<String> input) {
        trees = parseInput(input);

        // find the number of trees visible from outside the woods

        // all border trees are visible
        int countVisible = 2 * trees.length + 2 * (trees[0].length - 2);

        // check the visibility of interior trees
        for (int row = 1; row < trees.length - 1; row++) {
            for (int col = 1; col < trees[row].length - 1; col++) {
                if (visible(row, col)) {
                    countVisible += 1;
                }
            }
        }

        return countVisible;
    }

    public int part2(List<String> input) {
        int maxScore = 0;

        // using the previously parsed trees, find the tree with the maximum scenic score
        for (int row = 1; row < trees.length - 1; row++) {
            for (int col = 1; col < trees[row].length - 1; col++) {
//                System.out.format("(%d,%d) ", row, col);
                int score = scenicScore(row, col);
//                System.out.println(score);
                if (score > maxScore) {
                    maxScore = score;
                }
            }
        }
        return maxScore;
    }

    /**
     * Parses the list of input lines into a 2D array of trees
     * @param input list of input where each character is the height of a tree
     * @return 2D int array of tree heights
     */
    private int[][] parseInput(List<String> input){
        int[][] trees = new int[input.size()][input.get(0).length()];

        for (int row = 0; row < input.size(); row++) {
            String currentRow = input.get(row);
            for (int tree = 0; tree < currentRow.length(); tree++) {
                trees[row][tree] = Integer.parseInt(currentRow.substring(tree, tree+1));
            }
        }

        return trees;
    }

    /**
     * Determines if the tree at row, col is visible outside the forest, that is, if it is taller than any tree
     * between it and the edge in the four cardinal directions
     * @param row y coordinate of the tree to be tested
     * @param col x coordinate of the tree to be tested
     * @return true if the tree is taller than the trees to at least one edge, false otherwise
     */
    private boolean visible(int row, int col) {
        return  highest(row, col, 1, 0) || // Down
                highest(row, col, -1,0) || // Up
                highest(row, col, 0, 1) || // Right
                highest(row, col, 0, -1);  // Left
    }

    /**
     * Computes whether the tree is taller than all trees to the edge in the direction of deltaRow, deltaColumn
     *
     * Preconditions: either deltaRow or deltaCol is zero, the other can only be 1 or -1
     *
     * @param row Row of tree to be tested
     * @param col Column of tree to be tested
     * @param deltaRow with deltaCol describes the direction to be tested
     * @param deltaCol with deltaRow describes the direction to be tested.
     * @return true if the tree is taller than any tree to the edge of the trees
     */
    private boolean highest(int row, int col, int deltaRow, int deltaCol) {
        int nextRow = row + deltaRow;
        int nextCol = col + deltaCol;

        while (0 <= nextRow && nextRow < trees.length && 0 <= nextCol && nextCol < trees[row].length) {
            if (trees[nextRow][nextCol] >= trees[row][col]) {
                return false;
            }
            nextRow += deltaRow;
            nextCol += deltaCol;
        }

        return true;
    }

    /**
     * Returns the product of the number of trees that are equal to or lower to the tree at row in each cardnial
     * direction as you move away from the tree.
     *
     * @param row Row of tree being checked
     * @param col Column of tree being checked
     * @return scenic score of tree being checked
     */
    private int scenicScore(int row, int col) {
        return  notTaller(row, col, 1, 0) *
                notTaller(row, col, -1, 0) *
                notTaller(row, col, 0, 1) *
                notTaller(row, col, 0, -1);
    }

    /**
     * Returns the number of trees
     * @param row
     * @param col
     * @param deltaRow
     * @param deltaCol
     * @return
     */
    private int notTaller(int row, int col, int deltaRow, int deltaCol) {
        int countTrees = 0;

        int nextRow = row + deltaRow;
        int nextCol = col + deltaCol;

        while (0 <= nextRow && nextRow < trees.length && 0 <= nextCol && nextCol < trees[row].length) {
            countTrees += 1;
            if (trees[nextRow][nextCol] >= trees[row][col]) {
//                System.out.print(countTrees + " ");
                return countTrees;
            }
            nextRow += deltaRow;
            nextCol += deltaCol;
        }

//        System.out.print(countTrees + " ");
        return countTrees;
    }
}
