package seedu.recruit.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.logic.CommandHistory;

public class AddJobDetailsCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullJobOffer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddJobDetailsCommand(null);
    }
}
