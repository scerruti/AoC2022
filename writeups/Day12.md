# [--- Day 12: Hill Climbing Algorithm ---](https://adventofcode.com/2022/day/12)

## Summary
This writeup is occurring after the writeup for [Day 13](Day13.md) and is being done in the same fashion. I will be conducting the write up as I construct the code and then adding my debugging steps.

Please be aware that I have previously completed this day in Python and that I am limiting myself to Java 8 and, as much as possible, the [AP Java Subset (PDF)](https://secure-media.collegeboard.org/digitalServices/pdf/ap/ap-computer-science-a-java-subset.pdf) as described by the [AP Java Quick Reference(PDF)](https://apstudents.collegeboard.org/ap/pdf/ap-computer-science-a-java-quick-reference_0.pdf).

It is my opinion that Day 12 makes a good extension activity for extending capable AP CSA programmers for the main reason that it will introduce them to solving maps. I have had great success in engaging students in this type of activity and some robotics activities even include maze solving. I would provide them a reference for a simple algorithm to work from and have them figure out how to adapt it to this problem. I am working from [How to Solve a Maze using BFS in Python](https://levelup.gitconnected.com/solve-a-maze-with-python-e9f0580979a1).

## Creating the Elevation Map

This map differs from the example in that it contains elevations rather than walls. The fundamentals are the same, a rule to describe possible paths using a map. One secret that I have learned from doing Advent of Code is to put an impassable border on the edge of the map. This eliminates the needs for checking for out of range exceptions. Note that there are also some kludges in place to extract the start and end information from the map.
```java
    private int[][] createMap(List<String> input, int terminator) {
        int[][] result = new int[input.size()+2][input.get(0).length()+2];

        for (int i = 0; i < result[0].length; i++) {
            result[0][i] = terminator;
            result[result.length][i] = terminator;
        }

        for (int r = 0; r < input.size(); r++) {
            String currentRow = input.get(0);
            result[r+1][0] = result[r+1][result[r+1].length-1] = terminator;
            for (int c = 0; c < currentRow.length(); c++) {
                // This is a lot more straightforward than doing a substring
                // however it assumes the encoding of the String
                result[r+1][c+1] = (int) currentRow.charAt(c);
                if (currentRow.charAt(c) == 'S') {
                    setStart(r+1, c+1);
                    result[r+1][c+1] = (int) 'a';
                }
                if (currentRow.charAt(c) == 'E') {
                    setEnd(r+1, c+1);
                    result[r+1][c+1] = (int) 'z';
                }
            }
        }
        
        return result;
    }
```

## Track
We also create a second 2D array to keep track of our moves. It defaults to all zeros and we mark the start with a 1.

```java
        map = createMap(input, 999);
        track = new int[map.length][map[0].length]; // Defaults to all 0
        track[start[0]][start[1]] = 1;
```

## Breadth First Search

Conducting the breadth first search consists or repeating the same steps until the target is reached.
```java
        ArrayList<int[]> touched = new ArrayList<>();
        touched.add(start);
        
        int depth = 0;
        while (termination.check(touched) && touched.size() > 0) {
            depth += 1;
            touched = makeStep(depth, touched, elevation);
        }
```

Each step examines the neighbors of the previously modified cells. If it is a valid move they are marked on the track and added to a list of cells to be checked during the next step.
```java
    public ArrayList<int[]> makeStep(int depth, ArrayList<int[]> touched, Elevation elev) {
        ArrayList<int[]> newTouched = new ArrayList<>();

        for (int[] point : touched) {
            int r = point[0];
            int c = point[1];
            if (track[r][c] == depth) {
                int elevation = map[r][c];
                if (elev.check(r - 1, c, elevation) && track[r - 1][c] == 0) {
                    track[r - 1][c] = depth + 1;
                    newTouched.add(new int[]{r - 1, c});
                }
                if (elev.check(r + 1, c, elevation) && track[r + 1][c] == 0) {
                    track[r + 1][c] = depth + 1;
                    newTouched.add(new int[]{r + 1, c});
                }
                if (elev.check(r, c - 1, elevation) && track[r][c - 1] == 0) {
                    track[r][c - 1] = depth + 1;
                    newTouched.add(new int[]{r, c - 1});
                }
                if (elev.check(r, c + 1, elevation) && track[r][c + 1] == 0) {
                    track[r][c + 1] = depth + 1;
                    newTouched.add(new int[]{r, c + 1});
                }
            }
        }
        return newTouched;
    }
```

A note here: There is some complexity in both testing for the end of the search and in whether a move is valid. This was imlpemented to support part 2. It can easily be hardcoded for part 1.

### Debugging
I did not do a great job of documenting my debugging or, more importantly my optimizations. My code here is as it was before I debugged it so it should be possible to recreate my bugs.

## Part 2
Finding the way from the end to the nearest 'a' point is fairly easy. 
```java
    public int part2(List<String> input) {
        Termination termination = (touched) -> {for (int[] point : touched) if (map[point[0]][point[1]] == 'a') return true; return false;};
        Elevation elevation = (row, col, elev) -> map[row][col] - elev > -2;

        map = createMap(input, 0);
        track = new int[map.length][map[0].length]; // Defaults to all 0
        track[end[0]][end[1]] = 1;

        ArrayList<int[]> touched = new ArrayList<>();
        touched.add(end);

        int depth = 0;
        while (!termination.check(touched) && touched.size() > 0) {
            depth += 1;
            touched = makeStep(depth, touched, elevation);
        }
        return depth;
    }
```

The elevation check is reversed and the terminating condition is changed. We also need to switch the boundary value to something very low and set the starting point in the track to the coordinates of the end point.

*Note:* The code in the repository is more up-to-date than some of the code in this writeup. Please reference that code if something here doesn't look correct. Most importantly, I've done some IDE assisted optimizations to reduce duplicated code.