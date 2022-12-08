import java.util.List;


@SuppressWarnings("unused")
public class Day08b extends Day {

    private int[][] trees;

    Day08b() {
        super(8);
    }

    public int part1(List<String> input) {
        trees = parseInput(input);

        // find the number of trees visible from outside the woods

        // all border trees are visible
        int countVisible = 2 * trees.length + 2 * (trees[0].length - 2);

        // check the visibility of interior trees
        Coordinate current = Coordinate.firstInterior(trees);
        while (current.inRange(trees)) {
            if (visible(current)) {
                countVisible += 1;
            }
            current.nextInterior(trees);
        }

        return countVisible;
    }

    public int part2(List<String> input) {
        int maxScore = 0;

        // using the previously parsed trees, find the tree with the maximum scenic score
        Coordinate current = Coordinate.firstInterior(trees);
        while (current.inRange(trees)) {
            int score = scenicScore(current);
            if (score > maxScore) {
                maxScore = score;
            }
            current.nextInterior(trees);
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
     * @param current location of tree to be tested
     * @return true if the tree is taller than the trees to at least one edge, false otherwise
     */
    private boolean visible(Coordinate current) {
        return  highest(current, Coordinate.Direction.DOWN) || // Down
                highest(current, Coordinate.Direction.UP) || // Up
                highest(current, Coordinate.Direction.RIGHT) || // Right
                highest(current, Coordinate.Direction.LEFT);  // Left
    }

    /**
     * Computes whether the tree is taller than all trees to the edge in the direction of deltaRow, deltaColumn
     *
     * Preconditions: either deltaRow or deltaCol is zero, the other can only be 1 or -1
     *
     * @param current Row of tree to be tested
     * @param direction Column of tree to be tested
     * @return true if the tree is taller than any tree to the edge of the trees
     */
    private boolean highest(Coordinate current, Coordinate.Direction direction) {
        Coordinate test = new Coordinate(current);
        direction.next(test);

        while (test.inRange(trees)) {
            if (test.valueAt(trees) >= current.valueAt(trees)) {
                return false;
            }
            direction.next(test);
        }

        return true;
    }

    /**
     * Returns the product of the number of trees that are equal to or lower to the tree at row in each cardnial
     * direction as you move away from the tree.
     *
     * @param current Row of tree being checked
     * @return scenic score of tree being checked
     */
    private int scenicScore(Coordinate current) {
        return  notTaller(current, Coordinate.Direction.UP) *
                notTaller(current, Coordinate.Direction.DOWN) *
                notTaller(current, Coordinate.Direction.LEFT) *
                notTaller(current, Coordinate.Direction.RIGHT);
    }

    /**
     * Returns the number of trees
     * @param current
     * @param direction
     * @return
     */
    private int notTaller(Coordinate current, Coordinate.Direction direction) {
        int countTrees = 0;

        Coordinate test = new Coordinate(current);
        direction.next(test);

        while (test.inRange(trees)) {
            countTrees += 1;
            if (test.valueAt(trees) >= current.valueAt(trees)) {
                return countTrees;
            }
            direction.next(test);
        }

        return countTrees;
    }

    public static class Coordinate {
        private int row;
        private int col;

        public enum Direction {
            UP {
                @Override
                public void next(Coordinate current) {
                    current.row -= 1;
                }
            },
            DOWN {
                @Override
                public void next(Coordinate current) {
                    current.row += 1;
                }
            },
            LEFT {
                @Override
                public void next(Coordinate current) {
                    current.col -= 1;
                }
            },
            RIGHT {
                @Override
                public void next(Coordinate current) {
                    current.col += 1;
                }
            };
            public abstract void next(Coordinate current);
        }
        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Coordinate(Coordinate other) {
            this(other.row, other.col);
        }

        public Coordinate first(int[][] data) {
            return new Coordinate(0, 0);
        }

        public static Coordinate firstInterior(int[][] data) {
            return new Coordinate(1, 1);
        }

        public void next(int[][] data) {
            col += 1;
            if (col >= data[row].length) {
                col = 0;
                row += 1;
                if (row > data.length) {
                    col = row = -1;
                }
            }
        }

        public void nextInterior(int[][] data) {
            col += 1;
            if (col >= data[row].length - 1) {
                col = 1;
                row += 1;
                if (row >= data.length - 1) {
                    col = row = -1;
                }
            }
        }

        public int valueAt(int[][] data) {
            return data[row][col];
        }

        public boolean inRange(int[][] data) {
            return  row >= 0 &&
                    row < data.length &&
                    col >= 0  &&
                    col < data[row].length;
        }
    }
}
