import java.util.List;

@SuppressWarnings("unused")
public class Day03 extends Day {


    private static final String ITEM_PRIORITY =
            "?" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    Day03() {
        super(3);
    }

    @Override
    int part1(List<String> rucksacks) {
        // Initialize the total priority
        int totalPriority = 0;

        // Traverse through the input
        for (String rucksack: rucksacks) {
            // Split each rucksack into the two compartments
            String compartment1 = rucksack.substring(0, rucksack.length()/2);
            String compartment2 = rucksack.substring(rucksack.length()/2);

            // Find the item in compartment 1 that are in compartment 2
            String duplicateItem = getCommonItems(compartment1, compartment2);

            // Find the priority of the item and add it to the total
            totalPriority += ITEM_PRIORITY.indexOf(duplicateItem.substring(0,1));
        }

        return totalPriority;
    }

    /**
     * Returns the list of items found in both set1 and set2.
     *
     * @param set1  first list of items
     * @param set2  second list of items
     * @return items that appear in both sets
     */
    @SuppressWarnings({"IndexOfReplaceableByContains","StringContatenationInLoop"})
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


    @Override
    int part2(List<String> rucksacks) {
        // Compute the total score
        int totalPriority = 0;

        // Iterate over groups of 3 rucksacks
        for (int i = 0; i < rucksacks.size(); i += 3) {
            // Use a local variable for each rucksack
            String rucksack1 = rucksacks.get(i);
            String rucksack2 = rucksacks.get(i+1);
            String rucksack3 = rucksacks.get(i+2);

            // Find the item in common to all 3 rucksacks
            String duplicateItems = getCommonItems(rucksack1, rucksack2);
            duplicateItems = getCommonItems(duplicateItems, rucksack3);

            // Find the priority of the item and add it to the total
            totalPriority += ITEM_PRIORITY.indexOf(duplicateItems.substring(0,1));
        }



        return totalPriority;
    }


}
