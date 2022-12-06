import java.util.List;

@SuppressWarnings("unused")
public class Day06 extends Day {

    Day06() {
        super(6);
    }

    public int part1(List<String> input) {
        String code = input.get(0);
        String chunk = "";

        for (int i = 0; i < code.length(); i++) {
            String next = code.substring(i,i+1);
            int pos = chunk.indexOf(next);
            if (pos >= 0) {
                chunk = chunk.substring(pos+1);
            }

            chunk += next;

            if (chunk.length() == 4) {
                System.out.println(i+1 + ": " + chunk);
                break;
            }
        }
        return 0;
    }



    public int part2(List<String> input) {
        String code = input.get(0);
        String chunk = "";

        for (int i = 0; i < code.length(); i++) {
            String next = code.substring(i,i+1);
            int pos = chunk.indexOf(next);
            if (pos >= 0) {
                chunk = chunk.substring(pos+1);
            }

            chunk += next;

            if (chunk.length() == 14) {
                System.out.println(i+1 + ": " + chunk);
                break;
            }
        }
        return 0;
    }

}
