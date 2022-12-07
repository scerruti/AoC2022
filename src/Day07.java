import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Day07 extends Day {

    private final Node root = new Node("/");
    public static final int TOTAL_SPACE = 70000000;
    public static final int REQUIRED_SPACE = 30000000;

    Day07() {
        super(7);
    }



    public int part1(List<String> input) {
        buildTree(input);
        System.out.println(sumSmallDirectories());
        return 0;
    }

    public int part2(List<String> input) {
        int maximum_used_space = TOTAL_SPACE - REQUIRED_SPACE;
        int total_space = root.getTotalSize();

        int amount_to_free = total_space - maximum_used_space;

        // Find the smallest directory that will delete enough
        System.out.println(root.findBestFit(amount_to_free, TOTAL_SPACE));

        return 0;
    }

    private int sumSmallDirectories() {
        return root.sumSmallDirectories();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public void buildTree(List<String> input) {
        Node currentNode = null;

        for (String line: input) {
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
        }

        root.computeSize();
    }

    private static class Node {
        private final String name;
        private final Node parent;
        private int fileSize;
        private int totalSize;
        private final ArrayList<Node> children;

        public Node(String name) {
            this(name, null);
        }

        public Node(String name, Node parent) {
            this.name = name;
            children = new ArrayList<>();
            this.fileSize = 0;
            this.parent = parent;
        }

        public void addChild(String name) {
            children.add(new Node(name, this));
        }

        public void addFileSize(int size) {
            this.fileSize += size;
        }

        public int computeSize() {
            int size = fileSize;
            for (Node child: children) {
                size += child.computeSize();
            }
            totalSize = size;
            return size;
        }

        public Node getParent() {
            return parent;
        }

        public Node getChild(String name) {
            for (Node child : children) {
                if (name.equals(child.name)) return child;
            }
            return null;
        }

        public int sumSmallDirectories() {
            int sum = 0;

            for (Node child : children) {
                sum += child.sumSmallDirectories();
            }
            if (totalSize <= 100000) {
                sum += totalSize;
            }
            return sum;
        }

        public int getTotalSize() {
            return totalSize;
        }

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
    }
}
