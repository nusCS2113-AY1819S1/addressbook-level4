package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.StockList;
import seedu.address.model.ReadOnlyStockList;
import seedu.address.model.item.Name;
import seedu.address.model.item.Item;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code StockList} with sample data.
 */
public class SampleDataUtil {
    public static Item[] getSampleItems() {
        return new Item[] {
            new Item(new Name("Arduino"), 10, 5, getTagSet("Lab1")),
            new Item(new Name("Raspberry Pi"), 10, 6, getTagSet("Lab2"))
        };
    }

    public static ReadOnlyStockList getSampleStockList() {
        StockList sampleSl = new StockList();
        for (Item sampleItem : getSampleItems()) {
            sampleSl.addItem(sampleItem);
        }
        return sampleSl;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
