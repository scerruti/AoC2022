# [--- Day 5: Supply Stacks ---](https://adventofcode.com/2022/day/5)

## Summary

Supply stacks is great for having students think about arrays and array lists. Some of the complexity of this solution can be removed if the students hardcode the number of stacks and sizes of stacks. My solution is overly complicated because it should work for any solution up to nine stacks and could easily be adjusted for a larger number.

### Subset Deviations
I once again resort to parseInt and split. In this case I think Scanner could have been used with the data as-is.

### buildStacks Method
I create a method to build my stacks data structure, an `Array` of `ArrayList` of `String`. It reads in a line, and indexes into the string to find crates. Crates are inserted at the beginning of the stack. I do have to go back and remove the stack numbers from the stacks which is as easy as identifying them in the input.

```java
    private int buildStacks(List<String> input) {
        stacks = new ArrayList[9];
        int row;
        for (row = 0; row < input.size() && input.get(row).length() != 0; row++) {
            for (int i = 1; i < input.get(row).length(); i += 4) {
                String crate = input.get(row).substring(i, i+1);
                int stackNum = i / 4;
        
                if (!crate.equals(" ")) {
                    if (stacks[stackNum] == null) {
                        stacks[stackNum] =  new ArrayList<>();
                        stacks[stackNum].add(crate);
                    } else {
                        stacks[stackNum].add(0, crate);
                    }
                }
            }
        }

        for (ArrayList<String> stack : stacks) {
            if (stack != null) {
                stack.remove(0);
            }
        }

        return row + 1;
    }
```

### Part 1
Parsing the commands to move crates is straightforward. I split the command and extract and convert the relative parts. Note that I have to subtract 1 from the stack numbers to make them 0 based.
```java
        for (;commandRow < input.size(); commandRow++) {
            String[] parts = input.get(commandRow).split(" ");
            int qty = Integer.parseInt(parts[1]);
            int src = Integer.parseInt(parts[3]) - 1;
            int dst = Integer.parseInt(parts[5]) - 1;
```
Once that is complete I simply move the crates from the `src` to the `dst`.
```java
            for (int i = 0; i < qty; i ++) {
                String crate = stacks[src].remove(stacks[src].size() - 1);
                stacks[dst].add(crate);
            }
        }
```
Finally the answer to the puzzle is constructed by building up a `String`.
```java
        String result = "";
        for (ArrayList<String> stack : stacks) {
            if (stack != null && stack.size() > 0) {
                result += stack.get(stack.size() - 1);
            }
        }
```
### Part 2 
Part 2 is identical to part 1 with a simple change to how crates get moved.
```java
            for (int i = 0; i < qty; i ++) {
                String crate = stacks[src].remove(stacks[src].size() - 1);
                stacks[dst].add(stacks[dst].size() - i, crate);
            }
```
Comparing the two shows how I simply add crates to the end in part 1 but in part 2 I have to add them with an index so they get added in reverse order.
## Comparison to Python
Python's more friendly list slicing and other tools makes the solution a bit shorter. Neither my Python or Java solution are optimal as I am attempting to keep them at a very basic level.