# [--- Day 1: Calorie Counting ---](https://adventofcode.com/2022/day/1)

## Summary

This problem can be used to reenforce the concepts of Array and ArrayList algorithms. One method in this solution is a pure maximum value of an Integer ArrayList. It also shows the use of the substring function to break the input from a single String into multiple parts. 

## Topics
1. Traversing a string with indexOf.
1. Creating substrings.
1. Computing sums.
1. Finding the maximum value.
1. Converting strings to integers.

## Deviations from the AP CSA Java Subset

The Java subset does not include a way to covert Strings to Integers. I used the Integer wrapper method parseInt which can be very useful. This could be avoided by using scanner on the input file.

I used the indexOf with two arguments to show how to traverse a String using a while loop. This could easily be replaced by a more subset friendly approach that constantly builds a separate 'remaining' substring.

