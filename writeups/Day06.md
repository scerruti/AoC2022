# [--- Day 6: Tuning Trouble ---](https://adventofcode.com/2022/day/6)

## Summary
Tuning trouble makes use of only a `for` loop and `String` methods. It is achievable for advance AP students, but they will need to give it some thought. Parts 1 and 2 only differ in the size of the unique sequence meaning the algorithm is identical.

### Solution
Create an empty candidate string to work with.
For each character of the input,
    If the letter of the input appears in our candidate string,
        Remove all the letters up to and including the match.
    Add the character to the end of the candidate string.
    If the candidate string has reached the target length,
        The unique sequence has been found!

### Here is the code for Part 1
Initialize some variables, code is the input, we are building sequences in chunk.
```java
        String code = input.get(0);
        String chunk = "";
```
Loop through the input string.
```java
        for (int i = 0; i < code.length(); i++) {
            String next = code.substring(i,i+1);
```

Look for our next character in the candidate string, if so remove all the characters up to and including that character.
```java
            int pos = chunk.indexOf(next);
            if (pos >= 0) {
                chunk = chunk.substring(pos+1);
            }
```
Add the next character to the chunk and see if we have hit the required length.
```java
            chunk += next;
            if (chunk.length() == 4) {
                System.out.println(i+1 + ": " + chunk);
                break;
            }
```

