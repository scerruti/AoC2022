import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Day13 extends Day {

    Day13() {
        super(13);
    }


    public int part1(List<String> input) {

        for (int i = 0; i < Math.ceil(input.size() / 3.0); i++) {
            PacketElement packet1 = PacketElement.createElement(input.get(i*3));
            PacketElement packet2 = PacketElement.createElement(input.get(i*3+1));

            System.out.format("Pair %d is in order: %b\n", i+1, packet1.compareTo(packet2));
        }

        return 0;
    }

    public int part2(List<String> input) {

        return 0;
    }

    private static abstract class PacketElement   {
        public static PacketElement createElement(String element) {
            PacketElement result;
            if (element.indexOf("[") == -1) {
                result = new IntElement(element);
            } else {
                result = new ListElement(element);
            }
            return result;
         }

        public int compareTo(PacketElement packetElement) {
            if (packetElement instanceof ListElement) {
                return compareTo((ListElement) packetElement);
            } else {
                return compareTo((IntElement) packetElement);
            }
        };

        public abstract int compareTo(ListElement packetElement);
        public abstract int compareTo(IntElement packetElement);
    }

    private static class ListElement extends PacketElement {
        PacketElement[] value;

        public ListElement(String element) {
            int leftBracket = element.indexOf("[");
            int rightBracket = element.lastIndexOf("]");

            String packetContents = element.substring(leftBracket + 1, rightBracket);
            if (packetContents.equals("")) {
                value =new PacketElement[0];
            } else {
                String[] packetElements = packetSplit(packetContents);

                value = new PacketElement[packetElements.length];
                for (int i = 0; i < packetElements.length; i++) {
                    value[i] = PacketElement.createElement(packetElements[i]);
                }
            }
        }

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

        public ListElement(IntElement intElement) {
            value = new PacketElement[]{intElement};
        }

        @Override
        public int compareTo(ListElement o) {
            int i = 0;

            while (i < value.length && i < o.value.length) {
                int result = value[i].compareTo(o.value[i]);

                if (result != 0) return result;
                i += 1;
            }

            return (o.value.length - value.length);
        }

        @Override
        public int compareTo(IntElement o) {
            ListElement wrap = new ListElement(o);
            return compareTo(wrap);
        }
    }

    private static class IntElement extends PacketElement {
        int value;

        public IntElement(String element) {
            this.value = Integer.parseInt(element);
        }

        @Override
        public int compareTo(IntElement o) {
            return this.value - ((IntElement) o).value;
        }

        @Override
        public int compareTo(ListElement o) {
            ListElement wrap = new ListElement(this);
            return wrap.compareTo(o);
        }
    }

}