# Advent of Code 2022 for AP CSA

Please see [Advent of Code](https://adventofcode.com) for information about the programming challenges, leaderboards and stats.

## Goal

Each year I attempt to solve the Advent of Code using the AP CSA subset and write up my responses for the benefit of anyone who might want to try to use them as an extension in class.

I make every attempt to note where I have gone outside the lines.

## Write Ups
1. [--- Day 1: Calorie Counting ---](writeups/Day01.md)
    - `Array` algorithms
2. [--- Day 2: Rock Paper Scissors ---](writeups/Day03.md)
    - `If` statements
3. [--- Day 3: Rucksack Reorganization ---](writeups/Day03.md)
    - `String` methods (`substring`, `indexOf`)
    - Iterating over `Lists` and `Strings` multiple ways
4. [--- Day 4: Camp Cleanup ---](writeups/Day04.md)
   - `boolean` expressions
   - AP subset issues for String.split() and Integer.parseInt()
5. [--- Day 5: Supply Stacks ---](writeups/Day05.md)
   - Array and ArrayList algorithms
   - Some string parsing
6. [--- Day 6: Tuning Trouble ---](writeups/Day06.md)
   - for loop
   - String methods
      - indexOf
      - substring
7. [--- Day 7: No Space Left on Device ---](writeups/Day07.md)
   - Use a tree to represent a file system and answer questions about directory sizes.
   - My solution uses recursion.
   - Inner class used 
   - Integer.parseInt
8. [--- Day 8: Treetop Tree House ---](writeups/Day08.md)
   - Construct a 2D array of tree heights from the input and answer questions about relative tree heights in the cardinal directions.
   - Demonstrates count algorithm with 2D arrays
   - Demonstrates max algorithm with 2D arrays
   - Shows how to generify a method to traverse in cardinal directions
9. [--- Day 9: Rope Bridge ---](writeups/Day09.md)
   - ArrayLists (checking ArrayLists for matching objects)
   - Working with coordinate pairs
   - Lots of nested loops
10. [--- Day 10: Cathode-Ray Tube ---](writeups/Day10.md)
      - Substrings
      - Modulo
11. _missing_
12. [--- Day 12: Hill Climbing Algorithm ---](writeups/Day12.md)
    - Great extension project
    - Breadth first search (maze solving)
    - Lambdas (this is a bit out of AP spec)
    - Array Lists and 2D Arrays
13. [--- Day 13: Distress Signal ---](writeups/Day13.md)
      - Inheritance
      - Polymorphism
      - Arrays
      - String methods

## Notes

I wanted to use an existing framework to build out the Java code for each day and I chose poorly. Most frameworks for AoC hand the input to a solver method. Because the new [Code.org](https://code.org) AP CSA curriculum uses Scanner against files I may rewrite the early solutions to make use of that. This will remove some substring operations but will provide a subset compliant method for converting input into numeric values.

## Feedback 

Feedback, especially suggestions for improvement, are always welcome. Please find me on mastodon at [@scerruti@csed.social](https://csed.social/@scerruti).