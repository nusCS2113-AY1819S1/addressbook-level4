package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.InventoryList;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.drink.*;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    /**
     * Initialising sample drinks for use
     */
    /*
    // Coke
    public static final Batch COKE1 = new SampleBatchBuilder().withId("0001").withQuantity("10").
            withDate("01/11/2018").build();
    public static final Batch COKE2 = new SampleBatchBuilder().withId("0002").withQuantity("20")
            .withDate("02/11/2018").build();
    public static final Batch COKE3 = new SampleBatchBuilder().withId("0003").withQuantity("30")
            .withDate("20/11/2018").build();
    //Green Tea
    public static final Batch GT1 = new SampleBatchBuilder().withId("0004").withQuantity("10").
            withDate("01/11/2018").build();
    public static final Batch GT2 = new SampleBatchBuilder().withId("0005").withQuantity("20")
            .withDate("02/11/2018").build();
    public static final Batch GT3 = new SampleBatchBuilder().withId("0006").withQuantity("40")
            .withDate("20/11/2018").build();
    //Sprite
    public static final Batch SPRITE1 = new SampleBatchBuilder().withId("0007").withQuantity("30").
            withDate("01/11/2018").build();
    public static final Batch SPRITE2 = new SampleBatchBuilder().withId("0008").withQuantity("20")
            .withDate("02/11/2018").build();
    public static final Batch SPRITE3 = new SampleBatchBuilder().withId("0009").withQuantity("40")
            .withDate("20/11/2018").build();
    //Milk Coffee
    public static final Batch MC1 = new SampleBatchBuilder().withId("0010").withQuantity("10").
            withDate("01/11/2018").build();
    public static final Batch MC2 = new SampleBatchBuilder().withId("0011").withQuantity("30")
            .withDate("02/11/2018").build();
    public static final Batch MC3 = new SampleBatchBuilder().withId("0012").withQuantity("40")
            .withDate("20/11/2018").build();
    //Green Tea
    public static final Batch MT1 = new SampleBatchBuilder().withId("0013").withQuantity("60").
            withDate("01/11/2018").build();
    public static final Batch MT2 = new SampleBatchBuilder().withId("0014").withQuantity("20")
            .withDate("02/11/2018").build();
    public static final Batch MT3 = new SampleBatchBuilder().withId("0015").withQuantity("40")
            .withDate("20/11/2018").build();

    public static final List<Batch> cokeBatches = Arrays.asList(COKE1, COKE2, COKE3);
    public static final List<Batch> cokeBatches = Arrays.asList(COKE1, COKE2, COKE3);
    public static final List<Batch> cokeBatches = Arrays.asList(COKE1, COKE2, COKE3);
    public static final List<Batch> cokeBatches = Arrays.asList(COKE1, COKE2, COKE3);
    public static final List<Batch> cokeBatches = Arrays.asList(COKE1, COKE2, COKE3);
    UniqueBatchList cokeBatchList = new UniqueBatchList();
    static UniqueBatchList greenTeaBatchList = new UniqueBatchList();
    static UniqueBatchList spriteBatchList = new UniqueBatchList();
    static UniqueBatchList milkCoffeeBatchList = new UniqueBatchList();
    static UniqueBatchList milkTeaBatchList = new UniqueBatchList();

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }
    */

    public static Drink[] getSampleDrinks() {
        return new Drink[] {
            new Drink(new Name("Coke"), new Price("1.5"), new Price("0.8"), getTagSet("Popular")),
            new Drink(new Name("Green Tea"), new Price("1.5"), new Price("0.7"), getTagSet("Popular")),
            new Drink(new Name("Sprite"), new Price("1.5"), new Price("0.7"), getTagSet("Popular")),
            new Drink(new Name("Milk Coffee"), new Price("1.8"), new Price("1.0")),
            new Drink(new Name("Milk Tea"), new Price("1.8"), new Price("1.0"))
        };
    }

    public static ReadOnlyInventoryList getSampleInventoryList() {
        InventoryList sampleIl = new InventoryList();
        for (Drink sampleDrink : getSampleDrinks()) {
            sampleIl.addDrink(sampleDrink);
        }
        return sampleIl;
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
