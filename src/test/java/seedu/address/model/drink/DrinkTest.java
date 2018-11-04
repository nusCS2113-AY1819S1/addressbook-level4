package seedu.address.model.drink;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DrinkTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Drink drink = new DrinkBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        person.getTags().remove(0);
    }
}
