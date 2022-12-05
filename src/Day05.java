import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Day05 extends Day {

    private ArrayList<String>[] stacks;

    Day05() {
        super(5);
    }

    public int part1(List<String> input) {
        int commandRow = buildStacks(input);

        for (;commandRow < input.size(); commandRow++) {
            String[] parts = input.get(commandRow).split(" ");
            int qty = Integer.parseInt(parts[1]);
            int src = Integer.parseInt(parts[3]) - 1;
            int dst = Integer.parseInt(parts[5]) - 1;

            for (int i = 0; i < qty; i ++) {
                String crate = stacks[src].remove(stacks[src].size() - 1);
                stacks[dst].add(crate);
            }
        }

        String result = "";
        for (ArrayList<String> stack : stacks) {
            if (stack != null && stack.size() > 0) {
                result += stack.get(stack.size() - 1);
            }
        }

        System.out.println(result);
        return 0;
    }

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


    public int part2(List<String> input) {
        int commandRow = buildStacks(input);

        for (;commandRow < input.size(); commandRow++) {
            String[] parts = input.get(commandRow).split(" ");
            int qty = Integer.parseInt(parts[1]);
            int src = Integer.parseInt(parts[3]) - 1;
            int dst = Integer.parseInt(parts[5]) - 1;

            for (int i = 0; i < qty; i ++) {
                String crate = stacks[src].remove(stacks[src].size() - 1);
                stacks[dst].add(stacks[dst].size() - i, crate);
            }
        }

        String result = "";
        for (ArrayList<String> stack : stacks) {
            if (stack != null && stack.size() > 0) {
                result += stack.get(stack.size() - 1);
            }
        }

        System.out.println(result);
        return 0;
    }

    public void printStacks() {
        for (ArrayList<String> stack : stacks) {
            if (stack == null) continue;
            for (String crate : stack) {
                System.out.print(crate + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
