package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.ModelManagerTestUserStub;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.TimeTable;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalTimeSlots;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_nonEmptyTimeTable_success() {
        Model model = new ModelManagerTestUserStub(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManagerTestUserStub(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = ClearCommand.MESSAGE_SUCCESS;

        Person lastPerson = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253")
                .withTags("friends")
                .withTimeTable(new TimeTable(TypicalTimeSlots.getTypicalTimeTable())).build();

        Person editedPerson = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253")
                .withTags("friends")
                .withTimeTable(new TimeTable()).build();

        model.matchUserToPerson("Alice Pauline");

        ClearCommand command = new ClearCommand();
        expectedModel.updatePerson(lastPerson, editedPerson);
        expectedModel.commitAddressBook();
        expectedModel.updateTimeTable(new TimeTable());

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

}
