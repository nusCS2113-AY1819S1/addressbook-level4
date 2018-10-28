package seedu.address.model.item;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Loaner {
    private final Name itemName;
    private final Name loanerName;
    private final Quantity quantity;

    public Loaner(Name itemName, Name loanerName, Quantity quantity) {
        requireAllNonNull(itemName, loanerName, quantity);
        this.itemName = itemName;
        this.loanerName = loanerName;
        this.quantity = quantity;
    }
    public Loaner(Loaner loaner) {
        itemName = loaner.getItemName();
        loanerName = loaner.getLoanerName();
        quantity = loaner.getQuantity();
    }
    public Name getItemName() {
        return itemName;
    }

    public Quantity getQuantity() {
        return quantity;
    }
    public Name getLoanerName() {
        return loanerName;
    }
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getLoanerName())
                .append(" loanerName: ")
                .append(getLoanerName())
                .append(" itemName: ")
                .append(getItemName())
                .append(" Quantity: ")
                .append(getQuantity());
        return builder.toString();
    }
}
