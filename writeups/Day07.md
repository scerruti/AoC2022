# [--- Day 7: No Space Left on Device ---](https://adventofcode.com/2022/day/7)

## Summary
Day 7 asks us to work with a classic file system tree structure keeping track of disk space used for each directory and subdirectory. The initial parsing as a twist in that you have to interpret the output of some shell commands to construct the directory tree.
There are plenty of opportunities for optimizations in this code. At later stages of the event these optimizations would be required, but at this stage they aren't required and leaving the code unoptimized might make is more understandable for new programmers.
### Recursion
Chief among the optimizations that could be made is the removal of the recursion. Once the tree is constructed it is traversed recursively to compute the total size of each directory based on the size of the files in it and the size of its subdirectories. This work could be done during the construction of the tree but that code could be subject to changes in the input format. Doing it separately also provides a good introduction to recursion which will again be used in part 2.

Recursion is not required here, the tree could be traversed without using recursion, however, because recursion is a topic in AP CSA, it is left here as an example.

## Solution
The solution can be broken out into five parts.
1. Parse the input
2. Build the directory tree
3. Compute the size of each directory.
4. Find the sum of space of the small directories.
5. Find the best directory to remove the smallest one that frees enough space.

## Parse the input

Each line is processed against an if statement to take an action to build the tree.

| Prefix | Response |
| ------ | -------- |
| "$ cd" | if parameter is ".." move to the parent node<br>if parameter is "/" move to the root node <br>othewise moved to the parameter named child node |
| "$ ls" | ignore |
| "dir" | create a new directory node named in the parameter and add it as a child to the current directory node |
| otherwise | add the size of the file to the current directory |
```java
    if ("$ cd".equals(line.substring(0,4))) {
        String name = line.substring(5);
        if ("..".equals(name)) {
            assert currentNode != null;
            currentNode = currentNode.getParent();
        } else if ("/".equals(name)) {
            currentNode = root;
        } else {
            assert currentNode != null;
            currentNode = currentNode.getChild(name);
        }
    } else if ("$".equals(line.substring(0,1))) {
        // Do Nothing
    } else if ("dir".equals(line.substring(0,3))) {
        String name = line.substring(4);
        assert currentNode != null;
        currentNode.addChild(name);
    } else {
        int position = line.indexOf(" ");
        assert currentNode != null;
        currentNode.addFileSize(Integer.parseInt(line.substring(0, position)));
    }
```

### A More Object Oriented Approach (beyond AP CSA)
A more Object Oriented approach (see my previous rants about using if statements instead of OOP) would be to use a factory to create an action item from the object. The process method could then perform the action based on the subclass. For example the code might look like:
```java
for (String line: input) {
    currentNode = Action.from(line).process(currentNode);
}

public abstract class Action<I extends Action> {
    public static I from(String line) {
        // There are a few strategies to build subclasses from a factory
        // without an if statement (and no, maps don't work here)
        
        // 1. Use reflection and build a lookup table
        // 2. Find all possible classes (reflection) and ask them if they handle the case
        //      This is very useful if multiple classes handle a case
        // 3. Have subclasses register with the Action when they are loaded (lookup table)
    }
}

public abstract class ChangeDir extends Action {
}

public class ChangeDirRoot extends ChangeDir {
    public Node process(Node currentNode) {
        return currentNode.root();
    }
}
```
Ultimately our goal here is to move specifics about commands into the classes that handle those commands. The benefit is the ability to add new functionality with the simple addition of a new Class. This means that others can extend your codee without having to edit it, think about plugins.

From an #AdventOfCode perspective this approach (or simplified using a lookup table) often helps when problems get chained together over multiple days with increasing complexity.

## Building the directory tree
Each node might look like this:
```java
private static class Node {
    private final String name;
    private final Node parent;
    private int fileSize;
    private int totalSize;
    private final ArrayList<Node> children;
    /* Implementation not shown */
}
```

We can see in the code above the process to work with the tree, for example to add a new node:
```java
    String name = line.substring(4);
    assert currentNode != null;
    currentNode.addChild(name);
```

## Compute the size of each directory
A recursive method of node is used to add the size of the files in the current node to the sum of the sizes of the subdirectories. This recursive method could be flattened with a stack into a simple iteration.
```java
public int computeSize() {
    int size = fileSize;
    for (Node child: children) {
        size += child.computeSize();
    }
    totalSize = size;
    return size;
}
```

The remainder of the solution is not very interesting because it just relies on the same recursion as above. The only interesting part is that in part 2 we pass around the best value encountered so far as a parameter to the recursion.
```java
public int findBestFit(int amountToFree, int bestFit) {
    int result = bestFit;
    if (bestFit > totalSize && totalSize > amountToFree) {
        result = totalSize;
    }
    for (Node child : children) {
        result = child.findBestFit(amountToFree, result);
    }
    return result;
}
```

### Lack of explicit base case
I guess we should also point out that there is no explicit base case when recursing trees. The recursion stops because there are no children so the code inside the for loop never runs.