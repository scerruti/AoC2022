# [--- Day 8: Treetop Tree House ---](https://adventofcode.com/2022/day/8)

## Summary
In Day 8 we parse our input into a 2D int array of tree heights. We then count the number of trees visible from outside the forest and find the tree that is taller than the largest numer of trees around it in each cardinal direction.

This is a good set of 2D array problems but the need to traverse the array in all four directions may be a bit too advanced for most AP CSA students.

## Solution
We keep trees as a property of our solution so that we can use it for both parts and in all the private methods.

### Parse the data
Here again is our friend Integer.parseInt.

This is a fairly straightforward method to traverse a 2D array. Java assumes rectangular arrays, I do not in my traversal. While the problem statement is not clear on this point, it can be assumed the tree data is rectangular.
```java
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
```

## Part 1 Computing visibility
We count the visible trees by adding up all of the edge trees and then checking if each interior tree is visible.
```java
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
```
### Visibility is the _or_ of visibility (highest to edge) in each direction
```java
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
```
### Highest function genericized for any cardinal direction
This function would be better if the deltaRow and deltaCol were replace with an enumeration the handled the transformation of the coordinates. This would allow you to add additional directions and made the code easier to read. A corresponding change to use a coordinate pair rather than separate row and column variables would also be required.
```java
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
```
## Part 2 Maximum scenic view
The scenic view is defined as the product of the number of trees no taller than the candidate tree in each cardinal direction. Border trees do not need to be checked because they will always have one value that is zero.

We employ 2D traversal with the standard max algorithm, the only thing a little odd is the start and end of each loop. Note that I left my debugging print statements in place but commented out.
```java
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
```
### Scenic Score
Computing the scenic score is simply the product of a height measurement in each of the four directions.
```java
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
```
### Height Comparison
We use the same strategy as in part 1 to move away from the tree. This is a standard 1D search algorithm that counts the number of iterations until the target is found. The only difference is that the loop control variable can be row or col and can go in either direction.
```java
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
```
## Extension
Using a custom coordinate class. _This code has not been tested._

This might be what the class for using the a coordinate and an enum direction.
```java
public class Coordinate {
    private int row;
    private int col;
    
    public enum Direction {
        UP {
            @Override
            public Coordinate next(Coordinate current) {
                return new Coordinate(current.row - 1, col);
            }
        },
        DOWN {
            @Override
            public Coordinate next(Coordinate current) {
                return new Coordinate(current.row + 1, col);
            }
        },
        LEFT {
            @Override
            public Coordinate next(Coordinate current) {
                return new Coordinate(current.row, col - 1);
            }
        },
        RIGHT {
            @Override
            public Coordinate next(Coordinate current) {
                return new Coordinate(current.row, col + 1);
            }
        };
        public abstract Coordinate next(Coordinate current);
        public int valueAt(Coordinate current, int[][] trees) {
            return trees[current.row][current.col];
        }
    }
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
```
Using this class makes our other code much more legible.
```java
private int scenicScore(Coordinate current) {
    return  notTaller(current, Coordinate.UP) *
            notTaller(current, Coordinate.DOWN) *
            notTaller(current, Coordinate.RIGHT) *
            notTaller(current, Coordinate.LEFT;
}

private int notTaller(Coordinate current, Coordinate.Direction direction) {
    int countTrees = 0;

    Coordinate next = current.next(direction);

    while (next.inBounds(trees)) {
        countTrees += 1;
        if (Coordinate.valueAt(next, trees) >= Coordinate(current, trees)) {
            return countTrees;
        }
        next = next.next(direction);
    }

    return countTrees;
}
```

The in bounds function needs to be The main 2D array traversal would need to change as well. There is an argument to be made that the constant creation of coordinate objects in Java would be bad. That could be handled either with an object pool or by mutating the coordinate object. In general this second way of doing things is more OO because it hides some of the implementation details of the 2D array allowing you to replace it without changing much of the overlying code. For example, we could see a hex grid forest tomorrow. 