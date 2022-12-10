import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class Day09 extends Day {

    Day09() {
        super(9);
    }



    public int part1(List<String> input) {
        ArrayList<Point> points = new ArrayList<>();
        Point head = new Point(0, 0);
        Point tail = new Point(head);
        points.add(new Point(tail));

        for (String line: input) {
            String[] parts = line.split(" ");
            String direction = parts[0];
            int count = Integer.parseInt(parts[1]);

            for (int i = 0; i < count; i++) {
                head.move(direction);

                if (tail.distanceTo(head) > 1) {
                    tail.moveTo(head);
                    if (!tail.containedIn(points)) points.add(new Point(tail));
                }
            }
        }

        return points.size();
    }

    public int part2(List<String> input) {
        ArrayList<Point> points = new ArrayList<>();
        Point[] knots = new Point[10];
        for (int i = 0; i < 10; i++) knots[i] = new Point(0,0);
        points.add(new Point(knots[9]));

        for (String line: input) {
            String[] parts = line.split(" ");
            String direction = parts[0];
            int count = Integer.parseInt(parts[1]);

            for (int i = 0; i < count; i++) {
                knots[0].move(direction);

                for (int k = 1; k < 10; k++) {
                    if (knots[k].distanceTo(knots[k-1]) > 1) {
                        knots[k].moveTo(knots[k-1]);
                        if (k == 9 && !knots[k].containedIn(points)) {
                            points.add(new Point(knots[k]));
                        }
                    }

                }
            }
//            printKnots(knots);
        }

        return points.size();
    }

    private void printKnots(Point[] knots) {
        for (int row = 15; row >= -5; row--) {
            for (int col = -12; col < 15; col++) {
                String c = ".";
                for (int k = 9; k >= 0; k--) {
                    if (knots[k].x == col && knots[k].y == row) {
                        c = Integer.toString(k);
                    }
                }
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println("\n\n");
    }

    private static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(Point otherPoint) {
            this(otherPoint.x, otherPoint.y);
        }

        public void move(String direction) {
            // This if could be a switch or an enum
            if (direction.equals("U")) {
                y += 1;
            } else if (direction.equals("D")) {
                y -= 1;
            } else if (direction.equals("R")) {
                x += 1;
            } else if (direction.equals("L")) {
                x -= 1;
            }
        }

        public int distanceTo(Point otherPoint) {
            return Math.max(Math.abs(x - otherPoint.x), Math.abs(y - otherPoint.y));
        }

        public void moveTo(Point otherPoint) {
            if (x == otherPoint.x) {
                y =  y > otherPoint.y ? y - 1 : y + 1;
            } else if (y == otherPoint.y) {
                x = x > otherPoint.x ? x - 1 : x + 1;
            } else {
                x = x > otherPoint.x?x - 1 : x + 1;
                y = y > otherPoint.y?y - 1 : y + 1;
            }
        }

        public boolean containedIn(ArrayList<Point> points) {
            for (Point p : points) {
                if (x == p.x && y == p.y) return true;
            }
            return false;
        }
    }
}
