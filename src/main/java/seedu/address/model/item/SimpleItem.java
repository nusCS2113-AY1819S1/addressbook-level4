package seedu.address.model.item;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Simplified item with fields removed to be used in the status command
 */

public class SimpleItem {
    private final Name name;
    private final Quantity quantity;

    public SimpleItem(Name name, Quantity quantity) {
        requireAllNonNull(name, quantity);
        this.name = name;
        this.quantity = quantity;
    }
    public Name getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

}
