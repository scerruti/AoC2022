# [--- Day 13: Distress Signal ---](https://adventofcode.com/2022/day/13)

## Summary
Day 13 brings us a parsing and comparison problem with lists and integers. Rather than code my solution and then write it up I am going to attempt to plan, code and test.

### Define the Data Structure
> Packet data consists of lists and integers. Each list starts with [, ends with ], and contains zero or more comma-separated values (either integers or other lists). Each packet is always a list and appears on its own line.

First we will create an element that can be added into a list. We will make the superclass abstract and then create subtypes for lists and integers.
```java
    private static abstract class Element {
    }
    
    private static class ListElement extends Element {
        Element[] value;
    }
    
    private static class IntElement extends Element {
        int value;
    }
```

### Parse the Input Data to Create a packet
To keep the code that deals with this type together, we will create a factory method
in Element that creates an `Element[]`. It is useful that the definition requires that every packet is a list.
```java
        public static PacketElement createElement(String element) {
            PacketElement result;
            if (element.indexOf("[") == -1) {
                result = new IntElement(element);
            } else {
                result = new ListElement(element);
            }
            return result;
         }
```
We will rely on the constructors to create the elements, the `IntElement` is simple.
```java
        public IntElement(String element) {
            this.value = Integer.parseInt(element);
        }
```
The `ListElement` is a bit more complex, giving us the ability to construct our nested lists.
```java
        public ListElement(String element) {
            int leftBracket = element.indexOf("[");
            int rightBracket = element.indexOf("]");

            String packetContents = element.substring(leftBracket + 1, rightBracket);
            String[] packetElements = packetContents.split(",");

            value = new PacketElement[packetElements.length];
            for (int i = 0; i < packetElements.length; i++) {
                value[i] = PacketElement.createElement(packetElements[i]);
            }
        }
```
### Testing the Parser
Before we move on we should test our parser. Ideally we would just check in the debugger. Writing code to test the parseing means debugging two sets of code. Test driven development on the other hand would work well here.

### Writing a compareTo Method
The heart of this challenge is to check if the packets are in order. This implies that our `PacketElement` class should implement a `compareTo` method to establish the natural order.
>* If both values are integers, the lower integer should come first. If the left integer is lower than the right integer, the inputs are in the right order. If the left integer is higher than the right integer, the inputs are not in the right order. Otherwise, the inputs are the same integer; continue checking the next part of the input.
```java
    private static class IntElement extends PacketElement {

        /* Some code omitted */
    
        public int compareTo(IntElement o) {
            return this.value - ((IntElement) o).value;
        }
    }
```
>* If both values are lists, compare the first value of each list, then the second value, and so on. If the left list runs out of items first, the inputs are in the right order. If the right list runs out of items first, the inputs are not in the right order. If the lists are the same length and no comparison makes a decision about the order, continue checking the next part of the input.
```java
    private static class ListElement extends PacketElement {
    
        /* Some code omitted. */
        
        public int compareTo(ListElement o) {
            int i = 0;
            ListElement other = (ListElement) o;

            while (i < value.length && i < other.value.length) {
                int result = value[i].compareTo(other.value[i]);

                if (result != 0) return result;
            }

            return other.value.length - value.length;
        }
    }
```
>* If exactly one value is an integer, convert the integer to a list which contains that integer as its only value, then retry the comparison. For example, if comparing [0,0,0] and 2, convert the right value to [2] (a list containing 2); the result is then found by instead comparing [0,0,0] and [2].

This rule needs to be implemented in both subclasses.
```java
    private static class ListElement extends PacketElement {
    
        /* Some code omitted. */
    
        public int compareTo(ListElement o) {
            int i = 0;
            ListElement other = (ListElement) o;
    
            while (i < value.length && i < other.value.length) {
                int result = value[i].compareTo(other.value[i]);
    
                if (result != 0) return result;
            }
    
            return other.value.length - value.length;
        }
    }
```

```java
    private static class IntElement extends PacketElement {

    /* Some code omitted */
        public int compareTo(ListElement o) {
            ListElement wrap = new ListElement(this);
            return wrap.compareTo(o);
        }
    }
```

While this looks great, it won't actually work. We don't have a method to compare `PacketElement`. We can build one but we will need to use a trick to make it work correctly. We are going to use double dispatch but it would reverse the order so we multiply the result by -1 to reverse it back.
```java
    public int compareTo(Object o) {
            PacketElement e = (PacketElement) o;
            return e.compareTo(this) * -1;
        }
```

### Part 1 Solution
Loop through each pair of packets and determine if they are in order.

```java
        for (int i = 0; i < Math.ceil(input.size() / 3.0); i++) {
            PacketElement packet1 = PacketElement.createElement(input.get(i));
            PacketElement packet2 = PacketElement.createElement(input.get(i+1));

            System.out.format("Pair %d is in order: %b", i+1, packet1.compareTo(packet2));
        }
```

### Bugs, errors and stupid mistakes

#### Infinite Recursion
Sometime this can be hard. The code immediately fails because my mental model was wrong. To fix my compareTo I corrected the compareTo and added 2 abstract methods in `PacketElement` to handle arguments of `PacketElement` type. 

```java
        public int compareTo(PacketElement packetElement) {
            if (packetElement instanceof ListElement) {
                return compareTo((ListElement) packetElement);
            } else {
                return compareTo((IntElement) packetElement);
            }
        };

        public abstract int compareTo(ListElement packetElement);
        public abstract int compareTo(IntElement packetElement);
```

Both of these abstract classes are already present in the subclasses.

#### Loop Control Variable not Changed
In `ListElement`, comparing to `ListElements`, a while loop traverses the List. In the code `i` was not incremented leading to an infinite loop.

#### Bad Array Indexes

In the main code I forgot to account for looping by 3 when selecting input strings. That means I immediately attempt to translate a blank line into a packet. Since this is a violation of the preconditions, the program crashes.
```java
            PacketElement packet1 = PacketElement.createElement(input.get(i*3));
            PacketElement packet2 = PacketElement.createElement(input.get(i*3+1));
```
#### Finding the First Close Bracket
My code found the first close bracket when in my head I expected it to find the last bracket. This caused a substring releated crash on the fist set of nested lists. Changed code to use lastIndexOf.
```java
            int rightBracket = element.lastIndexOf("]");
```

#### Split Way to Aggressive
The line `String[] packetElements = packetContents.split(",");` splits up a packet by commas regardless if the comma is found inside another list. We need to write a quick piece of bracket counting code to fix that.
```java
        private String[] packetSplit(String packetContents) {
            ArrayList<String> result = new ArrayList<>();

            int start = 0;
            int pos = 0;
            int level = 0;
            while (pos < packetContents.length()) {
                String currentLetter = packetContents.substring(pos, pos+1);;
                if (level == 0 && currentLetter.equals(",")) {
                    result.add(packetContents.substring(start, pos));
                    start = pos + 1;
                } else if (currentLetter.equals("[")) {
                    level += 1;
                } else if (currentLetter.equals("]")) {
                    level -= 1;
                }
                pos += 1;
            }
            result.add(packetContents.substring(start));

            return result.toArray(new String[0]);
        }
```
# Empty Lists
After the split fiasco, empty lists were predictable and showed up in pair 6. An explicit check when processing lists to return a zero length list fixes the issue.