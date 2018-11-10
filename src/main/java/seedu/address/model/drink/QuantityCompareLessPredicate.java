package seedu.address.model.drink;

import java.util.function.Predicate;

public class QuantityCompareLessPredicate implements Predicate<Drink> {
    public final Quantity quantity;

    public QuantityCompareLessPredicate(Quantity quantity){
        this.quantity = quantity;
    }

    @Override
    public boolean test(Drink drink) {
        return drink.getQuantity().getValue() < quantity.getValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuantityCompareLessPredicate // instanceof handles nulls
                && quantity.equals(((QuantityCompareLessPredicate) other).quantity)); // state check
    }
}
