import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Day12 extends Day {

    private int[][] map;
    private int[][] track;
    private final int[] start = new int[2];
    private final int[] end = new int[2];

    Day12() {
        super(12);
    }

    public int part1(List<String> input) {
        Termination termination = (touched) -> {for (int[] point : touched) if (Arrays.equals(point, end)) return true; return false;};
        Elevation elevation = (row, col, elev) -> map[row][col] - elev < 2;

        map = createMap(input, 999);
        return breadthFirstSearch(termination, elevation, start);
    }

    public int part2(List<String> input) {
        Termination termination = (touched) -> {for (int[] point : touched) if (map[point[0]][point[1]] == 'a') return true; return false;};
        Elevation elevation = (row, col, elev) -> map[row][col] - elev > -2;

        map = createMap(input, 0);
        return breadthFirstSearch(termination, elevation, end);
    }

    private int[][] createMap(List<String> input, int terminator) {
        int[][] result = new int[input.size()+2][input.get(0).length()+2];

        for (int i = 0; i < result[0].length; i++) {
            result[0][i] = terminator;
            result[result.length-1][i] = terminator;
        }

        for (int r = 0; r < input.size(); r++) {
            String currentRow = input.get(r);
            result[r+1][0] = result[r+1][result[r+1].length-1] = terminator;
            for (int c = 0; c < currentRow.length(); c++) {
                // This is a lot more straightforward than doing a substring
                // however it assumes the encoding of the String
                result[r+1][c+1] = currentRow.charAt(c);
                if (currentRow.charAt(c) == 'S') {
                    setStart(r+1, c+1);
                    result[r+1][c+1] = 'a';
                }
                if (currentRow.charAt(c) == 'E') {
                    setEnd(r+1, c+1);
                    result[r+1][c+1] = 'z';
                }
            }
        }

        return result;
    }

    private int breadthFirstSearch(Termination termination, Elevation elevation, int[] start) {
        track = new int[map.length][map[0].length]; // Defaults to all 0
        track[start[0]][start[1]] = 1;

        ArrayList<int[]> touched = new ArrayList<>();
        touched.add(start);

        int depth = 0;
        while (touched.size() > 0 && !termination.check(touched)) {
            depth += 1;
            touched = makeStep(depth, touched, elevation);
        }
        return depth;
    }

    public ArrayList<int[]> makeStep(int depth, ArrayList<int[]> touched, Elevation elev) {
        ArrayList<int[]> newTouched = new ArrayList<>();

        for (int[] point : touched) {
            int r = point[0];
            int c = point[1];
            if (track[r][c] == depth) {
                int elevation = map[r][c];
                processNeighbor(depth, elev, newTouched, r - 1, c, elevation);
                processNeighbor(depth, elev, newTouched, r + 1, c, elevation);
                processNeighbor(depth, elev, newTouched, r, c - 1, elevation);
                processNeighbor(depth, elev, newTouched, r, c + 1, elevation);
            }
        }
        return newTouched;
    }

    private void processNeighbor(int depth, Elevation elev, ArrayList<int[]> newTouched, int r, int c, int elevation) {
        if (track[r][c] == 0 && elev.check(r, c, elevation)) {
            track[r][c] = depth + 1;
            newTouched.add(new int[]{r, c});
        }
    }

    public void setStart(int row, int col) {
        this.start[0] = row;
        this.start[1] = col;
    }

    public void setEnd(int row, int col) {
        this.end[0] = row;
        this.end[1] = col;
    }

    private interface Elevation {
        boolean check(int r, int c, int elevation);
    }

    private interface Termination {
        boolean check(ArrayList<int[]> touched);
    }
}