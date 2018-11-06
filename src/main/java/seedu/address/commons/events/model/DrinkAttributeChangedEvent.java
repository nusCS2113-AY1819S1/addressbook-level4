package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.drink.Drink;

public class DrinkAttributeChangedEvent extends BaseEvent {
    private final Drink editedDrink;
    public DrinkAttributeChangedEvent(Drink drink) {
        this.editedDrink = drink;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Drink getEditedDrink() {
        return editedDrink;
    }
}
