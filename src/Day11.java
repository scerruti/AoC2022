import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Day11 extends Day {

    Day11() {
        super(11);
    }


    public int part1(List<String> input) {
        Monkey[] monkeys = parseInput(input);
        InitialReduction reduction = new InitialReduction();
        printInventory(0, monkeys);

        for (int i = 0; i < 20; i++) {
            monkey_round(monkeys, reduction);
//            printInventory(i, monkeys);
//            printBusiness(i, monkeys);
        }
        System.out.println(monkey_business(monkeys));
        return 0;
    }

    public int part2(List<String> input) {
        Monkey[] monkeys = parseInput(input);
        SubsequentReduction reduction = new SubsequentReduction();
        reduction.setCommonDivisor(computeCommonDivisor(monkeys));

        for (int i = 0; i < 10000; i++) {
            monkey_round(monkeys, reduction);
            if (i == 0 || i == 19 || (i + 1) % 1000 == 0) {
                printBusiness(i, monkeys);
            }
        }
        System.out.println(monkey_business(monkeys));
        return 0;
    }

    private Monkey[] parseInput(List<String> input) {
        Monkey[] monkeys = new Monkey[input.size() / 7 + 1];
        for (int i = 0; i < monkeys.length; i++) {
            monkeys[i] = new Monkey(input.subList(i * 7, i * 7 + 6));
        }
        return monkeys;
    }

    private void monkey_round(Monkey[] monkeys, WorryReduction op) {
        for (Monkey monkey : monkeys) {
            while (monkey.items.size() > 0) {
                monkey.business += 1;
                BigInteger item = monkey.items.remove(0);
                item = monkey.operation.operate(item);
                item = op.execute(item);
                if (item.signum() == -1) {
                    throw new RuntimeException("Overflow occurred");
                }
                if (item.mod(BigInteger.valueOf(monkey.test)).signum() == 0) {
                    throwTo(monkeys, monkey.nextTrue, item);
                } else {
                    throwTo(monkeys, monkey.nextFalse, item);
                }
            }
        }

    }

    private void throwTo(Monkey[] monkeys, int number, BigInteger item) {
        for (Monkey monkey : monkeys) {
            if (monkey.number == number) {
                monkey.items.add(item);
            }
        }
    }

    private BigInteger monkey_business(Monkey[] monkeys) {
        int highest = Math.max(monkeys[0].business, monkeys[1].business);
        int higher = Math.min(monkeys[0].business, monkeys[1].business);

        for (int m = 2; m < monkeys.length; m++) {
            int b = monkeys[m].business;
            if (b > higher) {
                if (b > highest) {
                    higher = highest;
                    highest = b;
                } else {
                    higher = b;
                }
            }
        }

        System.out.print(higher + " + " + highest + " = ");
        return BigInteger.valueOf(higher).multiply(BigInteger.valueOf(highest));
    }

    private int computeCommonDivisor(Monkey[] monkeys) {
        int result = 1;
        for (Monkey monkey : monkeys) {
            if (result % monkey.test != 0) {
                result *= monkey.test;
            }
        }

        return result;
    }

    private void printInventory(int round, Monkey[] monkeys) {
        System.out.format("Round: %d\n", round);
        for (Monkey monkey : monkeys) {
            for (BigInteger item : monkey.items) {
                System.out.print(item + ", ");
            }
            System.out.println();
        }
        System.out.format("\n");
    }

    private void printBusiness(int round, Monkey[] monkeys) {
        System.out.format("%6d: [", round);
        for (Monkey monkey : monkeys) {
            System.out.format("%d, ", monkey.business);
        }
        System.out.format("\n");
    }

    private static class Monkey {
        private final int number; // Monkey number
        private final ArrayList<BigInteger> items; // Items monkey is holding
        private final Operation<?> operation; // How your worry increases
        private final int test; // Division peformed to check the next monkey
        private final int nextTrue; // Monkey if test is true
        private final int nextFalse; // Monkey if test is false
        private int business = 0;

        public Monkey(List<String> monkeyDefinition) {
            this.number = Integer.parseInt(monkeyDefinition.get(0).split("[ :]")[1]);
            this.items = parseItems(monkeyDefinition.get(1).split(": ")[1]);
            this.operation = Operation.getOperation(monkeyDefinition.get(2));
            this.test = Integer.parseInt(monkeyDefinition.get(3).split("  Test: divisible by ")[1]);
            this.nextTrue = Integer.parseInt(monkeyDefinition.get(4).split("    If true: throw to monkey ")[1]);
            this.nextFalse = Integer.parseInt(monkeyDefinition.get(5).split("    If false: throw to monkey ")[1]);
        }

        private ArrayList<BigInteger> parseItems(String itemList) {
            String[] items = itemList.split(", ");
            ArrayList<BigInteger> result = new ArrayList<>(items.length);

            for (String item : items) {
                result.add(new BigInteger(item));
            }

            return result;
        }
    }

    private abstract static class Operation<T extends Operation<T>> {
        protected final Integer operand;

        public Operation(int operand) {
            this.operand = operand;
        }

        public Operation() {
            this(0);
        }

        public static Operation<?> getOperation(String line) {
            String[] parts = line.split(" ");
            String operator = parts[6];
            String operand = parts[7];

            if (operand.equals("old")) {
                if (operator.equals("*")) {
                    return new Square();
                }
            } else {
                int op = Integer.parseInt(operand);
                if (operator.equals("+")) {
                    return new Increment(op);
                } else if (operator.equals("*")) {
                    return new Multiply(op);
                }
            }

            throw new IllegalArgumentException("Operation not identified.");
        }

        public abstract BigInteger operate(BigInteger value);
    }

    private static class Increment extends Operation<Increment> {
        public Increment(int operand) {
            super(operand);
        }

        public BigInteger operate(BigInteger value) {
            return value.add(BigInteger.valueOf(operand));
        }
    }

    private static class Multiply extends Operation<Multiply> {
        public Multiply(int operand) {
            super(operand);
        }

        public BigInteger operate(BigInteger value) {
            return value.multiply(BigInteger.valueOf(operand));
        }
    }

    private static class Square extends Operation<Square> {
        public Square() {
            super();
        }

        public BigInteger operate(BigInteger value) {
            return value.pow(2);
        }
    }

    private static abstract class WorryReduction {
        public abstract BigInteger execute(BigInteger value);
    }

    private static class InitialReduction extends WorryReduction {
        @Override
        public BigInteger execute(BigInteger value) {
            return value.divide(BigInteger.valueOf(3));
        }
    }

    private static class SubsequentReduction extends WorryReduction {
        private int commonDivisor;

        @Override
        public BigInteger execute(BigInteger value) {
            return value.mod(BigInteger.valueOf(commonDivisor));
        }

        public void setCommonDivisor(int commonDivisor) {
            this.commonDivisor = commonDivisor;
        }
    }
}