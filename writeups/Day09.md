# [--- Day 9: Rope Bridge ---](https://adventofcode.com/2022/day/9)

## Summary
Rope Bridge would forces students to simplify the problem statement, ignoring the extraneous information and zeroing in on what is actually being requested. This is an important real world skill but is not entirely relevent for AP CSA students.

Building a Point class that contains all the functions required for this activity really simplifies the problem and shows the strength of object oriented programming when part two rolls around.

## Solution

The main loop for part one reads each line and splits it into a direction and a count. It then repeats the same action count times. It will move the head of the rope and if the tail is too far from the head it will move the tail. If the tail moves then we check if the tail has been in this position before, if not we add it to our list of visited positions.

```java
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
```
Most of the real work of the solution is done with the Point methods.

| Method | Purpose |
| ------ | ------- |
| move | move the knot one space in the direction (U,D,L,R) indicated |
| distanceTo | computes the maximum distance (either horizontal or vertical) from this point to another point |
| moveTo | uses the defined move rules to move one knot close to a second knot |
| containedIn | checks for the existance of this point in the previously visited file |

One gotcha is that you must copy the point before saving it. Since the move and moveTo methods mutate points, if you don't make a copy then you will never add any points to your list. Therefore a Point constructor that takes a Point is provided.

## Part 2

Part two asks us to handle more knots. We simply extend our two knot solution by changing the fixed head and tail variables to a ten element array. We then process each point in the array in order to meet the requirements.

```java
knots[0].move(direction);

for (int k = 1; k < 10; k++) {
if (knots[k].distanceTo(knots[k-1]) > 1) {
    knots[k].moveTo(knots[k-1]);
    if (k == 9 && !knots[k].containedIn(points)) {
        points.add(new Point(knots[k]));
    }
}
```