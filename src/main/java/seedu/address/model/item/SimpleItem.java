package seedu.address.model.item;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

//@@author ChewKinWhye

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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SimpleItem)) {
            return false;
        }

        SimpleItem otherItem = (SimpleItem) other;
        return (otherItem.getName().toString().equals(getName().toString())
                && otherItem.getQuantity().toString().equals(getQuantity().toString()));
    }
}
