package seedu.address.model.drink;

import java.util.Comparator;

/**
 * This class holds all the comparators for the Batch and Drink classes
 */
public class Comparators {
    public static final Comparator<Batch> BATCHDATE = (Batch b1, Batch b2) -> b1.compareDateTo(b2);
}
