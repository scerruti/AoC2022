# [--- Day 3: Rucksack Reorganization ---](https://adventofcode.com/2022/day/3)

## Summary

Day 3 is an excellent opportunity for students to practice String methods. In this challenge I will demonstrate iterating over a list of strings with an enhanced for loop and a for loop that steps by 3. Additionally, we will split a String in half and use indexOf to both find the intersection of two strings and compute the priority of an item (letter) using a constant ```String```.

_Note: I realized that I didn't like my writeups because I was rushing them. One point of using markdown was to allow me to show code examples during the write up. I may revise previous work at a future date but expect publication to be slightly delayed moving forward._

## Mispacked Items - Part 1

The elf responsible for packing has made some mistakes. We need to identify those mistakes. Each rucksack is represented by a String in the input and can be divided into two equal compartments by splitting the String in half. The misplaced items can be identified because they appear in both compartments of a rucksack. There is only one misplaced item in the rucksack but it may appear multiple times in the compartments.

### Iterate Over the Input

Because we will not need to reference a rucksack by index or modify the list of rucksacks it makes sense for us to use an enhanced `for` loop for our iteration.

```Java
        // Traverse through the input
        for (String rucksack: rucksacks) {

        }
```

### Split the Items
We can now divide the items in and individual rucksack into the two compartments. We will use the String length to accomplish this.
```Java
            // Split each ruckset into the two compartments
            String compartment1 = rucksack.substring(0, rucksack.length()/2);
            String compartment2 = rucksack.substring(rucksack.length()/2);
```

### Find the Common Item
The intersection of two strings can be found by iterating over the first `String` and using `String.indexOf` on the second. Since we know there will only be one common item we can break when we find it.
```java
            // Find the item in compartment 1 that are in compartment 2
            String duplicateItem = "?";
            for (int i = 0; i < compartment1.length(); i++) {
                duplicateItem = compartment1.substring(i, i+1);
                if (compartment2.indexOf(duplicateItem) != -1) {
                    break;
                }
            }
```
### Get the Priority of the Item
We are going to use a constant `String` to store the priority of each item. We are following these rules:
- Lowercase item types a through z have priorities 1 through 26.
- Uppercase item types A through Z have priorities 27 through 52.

We don't have a zero priority item, the `?` is simply a placeholder.
```java
    private static final String ITEM_PRIORITY =
            "?" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
```
To compute the priority we employ `String.indexOf` once more.
```java
            // Find the priority of the item and add it to the total
            totalPriority += ITEM_PRIORITY.indexOf(duplicateItem);
```

## Unstickered Badges - Part 2

The elves have apparently also forgotten to place stickers on their badge items. Luckily we have an easy way to identify the badge items as well. Each consecutive group of three elves share the same badge item and it is the only item they share.

### Iterate Over the Input
Once again we are going to go through the rucksacks, however this time we are going to process them in groups of three. That calls for a traditional `for` loop that uses a step. We will also create a separate `String` variable for each rucksack to make our code more readable. _Note that I am iterating over a `List`, if I were iterating over an `Array` I would use rucksacks.length._
```java
        // Iterate over groups of 3 rucksacks
        for (int i = 0; i < rucksacks.size(); i += 3) {
            // Use a local variable for each rucksack
            String rucksack1 = rucksacks.get(i);
            String rucksack2 = rucksacks.get(i+1);
            String rucksack3 = rucksacks.get(i+2);
            
        }
```

### Refactor
There are two principles of software development that are in play here. First is **DRY** (Don't repeat yourself) and the second is **YAGNI** (You aren't going to need it.) When I first wrote the code to do the intersection of the two compartments I didn't know that I would need it for part 2 so I wrote it in the part 1 method. We will also need to generalize it so that it can return the total list of common items rather than only the first. That will become apparent in a moment.

Extract this code into a method:
```java
            String duplicateItem = "?";
            for (int i = 0; i < compartment1.length(); i++) {
                duplicateItem = compartment1.substring(i, i+1);
                if (compartment2.indexOf(duplicateItem) != -1) {
                    break;
                }
            }
```
Let's review the changes.
```java
    /**
     * Returns the list of items found in both set1 and set2.
     * 
     * @param set1  first list of items
     * @param set2  second list of items
     * @return items that appear in both sets
     */
    private String getCommonItems(String set1, String set2) {
        String duplicateItems = "";
        for (int i = 0; i < set1.length(); i++) {
            String currentItem = set1.substring(i, i+1);
            if (set2.indexOf(currentItem) != -1) {
                duplicateItems += currentItem;
            }
        }
        return duplicateItems;
    }
```
1. A `String` method was created with two parameters.
2. The component variables were changed to set to make the method more generic.
3. We may now return multiple items
    1. rename `duplicateItem` as `duplicateItems`
    2. create a `currentItem` variable for the current item
    3. replace the `break` with the addition of the `currentItem` to the duplicate items.
    
### Find the badges
Each of the rucksacks in our group of three will only share the badge in common. We will start by finding what 1 & 2 have in common and then check that list against rucksack 3 to find the single item.
```java
            // Find the item in common to all 3 rucksacks
            String duplicateItems = getCommonItems(rucksack1, rucksack2);
            duplicateItems = getCommonItems(duplicateItems, rucksack3);
```

### Sum the Priorities
This code is identical to part 1, but it can't benefit from being refactored to a method. We actually accomplished **DRY** by making `ITEM_PRIORITY` a constant from the start.

```java

            // Find the priority of the item and add it to the total
            totalPriority += ITEM_PRIORITY.indexOf(duplicateItems.substring(0,1));

```

