# [--- Day 9: Rope Bridge ---](https://adventofcode.com/2022/day/9)

## Summary
Rope Bridge would forces students to simplify the problem statement, ignoring the extraneous information and zeroing in on what is actually being requested. This is an important real world skill but is not entirely relevent for AP CSA students.

I've taken a couple of shortcuts away from the AP Java subset. I used the Point class from Java AWT. On a previous day I built a very similar coordinate class. A better solution would be to do that again and move some logic into that class. For example the class could have move(String direction), distanceTo(Point otherPoint) and moveTo(otherPoint) methods.

Also I have used a hash set to eaily keep track of unique points visited. 

## Solution
In my solution I move the head knot with each single move and then move every additional know to follow. I am going to start