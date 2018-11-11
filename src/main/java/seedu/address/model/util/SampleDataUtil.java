package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.InventoryList;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.drink.Batch;
import seedu.address.model.drink.Date;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Name;
import seedu.address.model.drink.Price;
import seedu.address.model.drink.Quantity;
import seedu.address.model.drink.UniqueBatchList;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.ReadOnlyTransactionList;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionId;
import seedu.address.model.transaction.TransactionList;
import seedu.address.model.transaction.TransactionType;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    /**
     * Initialising sample drinks for use
     */
    // Coke
    public static final Batch COKE1 = new SampleBatchBuilder().withId("0001").withQuantity("10")
            .withDate("01/10/2018").build();
    public static final Batch COKE2 = new SampleBatchBuilder().withId("0002").withQuantity("20")
            .withDate("19/10/2018").build();
    public static final Batch COKE3 = new SampleBatchBuilder().withId("0003").withQuantity("30")
            .withDate("11/11/2018").build();
    //Green Tea
    public static final Batch GT1 = new SampleBatchBuilder().withId("0004").withQuantity("10")
            .withDate("03/10/2018").build();
    public static final Batch GT2 = new SampleBatchBuilder().withId("0005").withQuantity("20")
            .withDate("17/10/2018").build();
    public static final Batch GT3 = new SampleBatchBuilder().withId("0006").withQuantity("40")
            .withDate("10/11/2018").build();
    //Sprite
    public static final Batch SPRITE1 = new SampleBatchBuilder().withId("0007").withQuantity("30")
            .withDate("04/10/2018").build();
    public static final Batch SPRITE2 = new SampleBatchBuilder().withId("0008").withQuantity("20")
            .withDate("20/10/2018").build();
    public static final Batch SPRITE3 = new SampleBatchBuilder().withId("0009").withQuantity("40")
            .withDate("7/11/2018").build();
    //Milk Coffee
    public static final Batch MC1 = new SampleBatchBuilder().withId("0010").withQuantity("10")
            .withDate("01/11/2018").build();
    public static final Batch MC2 = new SampleBatchBuilder().withId("0011").withQuantity("30")
            .withDate("02/11/2018").build();
    public static final Batch MC3 = new SampleBatchBuilder().withId("0012").withQuantity("40")
            .withDate("20/11/2018").build();
    //Milk Tea
    public static final Batch MT1 = new SampleBatchBuilder().withId("0013").withQuantity("60")
            .withDate("01/11/2018").build();
    public static final Batch MT2 = new SampleBatchBuilder().withId("0014").withQuantity("20")
            .withDate("02/11/2018").build();
    public static final Batch MT3 = new SampleBatchBuilder().withId("0015").withQuantity("40")
            .withDate("20/11/2018").build();


    public static final Transaction BUY_COKE_1 = new Transaction(TransactionType.PURCHASE, new Date("01/10/2018"),
            new Drink(new Name("Coke")), new Quantity("30"), new Price("576"), new TransactionId("10923"));

    public static final Transaction SALE_COKE_1 = new Transaction(TransactionType.SALE, new Date("15/10/2018"),
            new Drink(new Name("Coke")), new Quantity("10"), new Price("360"), new TransactionId("12305"));

    public static final Transaction BUY_COKE_2 = new Transaction(TransactionType.PURCHASE, new Date("19/10/2018"),
            new Drink(new Name("Coke")), new Quantity("10"), new Price("192"), new TransactionId("14203"));
    public static final Transaction BUY_COKE_3 = new Transaction(TransactionType.PURCHASE, new Date("19/10/2018"),
            new Drink(new Name("Coke")), new Quantity("10"), new Price("192"), new TransactionId("14593"));

    public static final Transaction SALE_COKE_2 = new Transaction(TransactionType.SALE, new Date("25/10/2018"),
            new Drink(new Name("Coke")), new Quantity("10"), new Price("360"), new TransactionId("16550"));

    public static final Transaction BUY_COKE_4 = new Transaction(TransactionType.PURCHASE, new Date("11/11/2018"),
            new Drink(new Name("Coke")), new Quantity("30"), new Price("576"), new TransactionId("20395"));

    // Green Tea Transactions
    public static final Transaction BUY_GT_1 = new Transaction(TransactionType.PURCHASE, new Date("03/10/2018"),
            new Drink(new Name("Green Tea")), new Quantity("20"), new Price("336"), new TransactionId("11232"));

    public static final Transaction BUY_GT_2 = new Transaction(TransactionType.PURCHASE, new Date("17/10/2018"),
            new Drink(new Name("Green Tea")), new Quantity("20"), new Price("336"), new TransactionId("13213"));

    public static final Transaction SALE_GT_1 = new Transaction(TransactionType.SALE, new Date("8/11/2018"),
            new Drink(new Name("Green Tea")), new Quantity("10"), new Price("360"), new TransactionId("11232"));

    public static final Transaction BUY_GT_3 = new Transaction(TransactionType.PURCHASE, new Date("10/11/2018"),
            new Drink(new Name("Green Tea")), new Quantity("40"), new Price("672"), new TransactionId("19933"));

    // Sprite Transactions
    public static final Transaction BUY_SPRITE_1 = new Transaction(TransactionType.PURCHASE, new Date("4/10/2018"),
            new Drink(new Name("Sprite")), new Quantity("40"), new Price("672"), new TransactionId("12211"));

    public static final Transaction BUY_SPRITE_2 = new Transaction(TransactionType.PURCHASE, new Date("20/10/2018"),
            new Drink(new Name("Sprite")), new Quantity("20"), new Price("336"), new TransactionId("15302"));

    public static final Transaction SALE_SPRITE_1 = new Transaction(TransactionType.SALE, new Date("25/10/2018"),
            new Drink(new Name("Sprite")), new Quantity("10"), new Price("360"), new TransactionId("19201"));

    public static final Transaction BUY_SPRITE_3 = new Transaction(TransactionType.PURCHASE, new Date("7/11/2018"),
            new Drink(new Name("Sprite")), new Quantity("40"), new Price("336"), new TransactionId("20121"));

    private static final UniqueBatchList cokeBatches = new SampleBatchListBuilder().buildBatchList(COKE1, COKE2, COKE3);
    private static final UniqueBatchList greenTeaBatches = new SampleBatchListBuilder().buildBatchList(GT1, GT2, GT3);
    private static final UniqueBatchList spriteBatches =
            new SampleBatchListBuilder().buildBatchList(SPRITE1, SPRITE2, SPRITE3);
    private static final UniqueBatchList milkCoffeeBatches = new SampleBatchListBuilder().buildBatchList(MC1, MC2, MC3);
    private static final UniqueBatchList milkTeaBatches = new SampleBatchListBuilder().buildBatchList(MT1, MT2, MT3);


    public static Drink[] getSampleDrinks() {
        return new Drink[]{
            new Drink(new Name("Coke"), new Price("19.2"), new Price("36"), cokeBatches,
                    getTagSet("Popular")),
            new Drink(new Name("Green Tea"), new Price("16.8"), new Price("36"), greenTeaBatches,
                    getTagSet("Popular")),
            new Drink(new Name("Sprite"), new Price("16.8"), new Price("36"), spriteBatches,
                    getTagSet("Popular")),
            new Drink(new Name("Milk Coffee"), new Price("24"), new Price("43.2"), milkCoffeeBatches,
                    getTagSet()),
            new Drink(new Name("Milk Tea"), new Price("24"), new Price("43.2"), milkTeaBatches, getTagSet())
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
     * Initialising sample transactions for use
     */
    // Coke
    //Green Tea
    //Sprite
    //Milk Coffee
    //Milk Tea

    // TransactionType transactionType, Date transactionDate, Drink drinkTransacted,
    // Quantity quantityTransacted, Price amountMoney, long id
    // Coke Transactions








    private static List<Transaction> getSampleTransactions() {
        return new ArrayList<>(Arrays.asList(
                BUY_COKE_1, SALE_COKE_1, BUY_COKE_2, BUY_COKE_3, SALE_COKE_2, BUY_COKE_4,
                BUY_GT_1, BUY_GT_2, SALE_GT_1, BUY_GT_3,
                BUY_SPRITE_1, BUY_SPRITE_2, SALE_SPRITE_1, BUY_SPRITE_3));
    }


    public static ReadOnlyTransactionList getSampleTransactionList() {
        TransactionList sampleTl = new TransactionList();

        List<Transaction> transactions = getSampleTransactions();
        transactions.sort((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()));

        for (Transaction transaction : transactions) {
            sampleTl.addTransaction(transaction);
        }

        return sampleTl;
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
