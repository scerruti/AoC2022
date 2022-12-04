# [--- Day 4: Camp Cleanup ---](https://adventofcode.com/2022/day/4)

## Summary

Today's puzzle asks you to count overlaps or non-intersecting ranges in range pairs. To keep the code AP Java subset compliant I wrote methods similar to String.split() and Integer.parseInt(). I would not ask students to write these.

There is some work interpreting the puzzle to figure out the correct boolean statements and there is a possibility of using De Morgan's on part 2.

_Opinion: I probably wouldn't assign this to my students. If I did I would make sure they knew about String.split() and Integer.parseInt(). Discussing how to construct the boolean expressions is probably valid and deserves some attention in this writeup._

### Parse Int Method
This method turns a string into an int by iterating over it and using indexOf to convert digits to numeric values.
```java
    public static int parseInt(String number) {
        int value = 0;

        for (int i = 0; i < number.length(); i++) {
            value = value * 10;
            int placeValue = DIGITS.indexOf(number.substring(i,i+1));
            if (placeValue != -1) {
                value += placeValue;
            } else {
                throw new RuntimeException("Number parsing error for character " + i + " of " + number + ".");
            }
        }
        return value;
    }
```
### Split Method
The split method simply splits a string at a delimiter and passes all the pieces back in an ArrayList. This split will handle multiple occurrences of a single delimiter but assumes the delimiter is a single character.

```java
    public static ArrayList<String> split(String string, String delimiter) {
        int pos = string.indexOf(delimiter);
        ArrayList<String> result = new ArrayList<>();

        String temp = new String(string);

        while (pos != -1) {
            result.add(temp.substring(0, pos));
            temp = temp.substring(pos + 1);
            pos = temp.indexOf(delimiter);
        }
        result.add(temp);

        return result;
    }
```
### Extracted Method
The extracted method takes a line of input and returns an ArrayList with the four `int` values. It's broken out as a method because it will be used for both parts.
```java
    private static int[] extracted(String assignment) {
        ArrayList<String> pairs = split(assignment, ",");
        ArrayList<String> elf1 = split(pairs.get(0), "-");
        ArrayList<String> elf2 = split(pairs.get(1), "-");

        return new int[] {Integer.parseInt(elf1.get(0)), Integer.parseInt(elf1.get(1)),
                Integer.parseInt(elf2.get(0)), Integer.parseInt(elf2.get(1))};
    }
```

### Part 1
All you really need to do is figure out the correct boolean statement and apply it to the four `int` values. Either elf 1 starts and ends equal to or outside elf 2 or elf 2 starts and ends outside or equal to elf 1. Other than that it is a simple counting algorithm.
```java
            if (bounds[0] <= bounds[2] && bounds[1] >= bounds[3] ||
                bounds[2] <= bounds[0] && bounds[3] >= bounds[1]) {
```

### Part 2 
Part 2 is identical to part 1 with a simple change to the boolean expression. [Stack Overflow](https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap) has a good discussion of different ways to solve this problem.
```java
            if (bounds[1] >= bounds[2] && bounds[0] <= bounds[3] ) {
```