package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_MARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EditCommand.createEditedPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteTestMarksCommandIntegrationTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {
        Person editedPerson = new PersonBuilder().withTags(VALID_TAG_FRIENDS)
                .withPhone("94351253").withEmail("alice@example.com").build();
        EditCommand.EditPersonDescriptor descriptor = new EditCommand.EditPersonDescriptor();
        Set<seedu.address.model.grade.Test> testList = new HashSet<>();
        testList.add(VALID_TEST_AMY);
        descriptor.setTests(testList);
        String[] nameKeywords = editedPerson.getName().fullName.split("\\s+");
        List<String> nameKeywordsList =
                new ArrayList<>(Arrays.asList(nameKeywords));
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(nameKeywordsList);

        AddTestMarksCommand addTestMarksCommand = new AddTestMarksCommand(nameContainsKeywordsPredicate,
                VALID_TEST_NAME_AMY, VALID_TEST_MARK_AMY, nameKeywordsList);

        String expectedMessage = String.format(Messages.MESSAGE_ADDED_TEST_LIST, editedPerson);
        editedPerson = createEditedPerson(editedPerson, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addTestMarksCommand, model, commandHistory, expectedMessage, expectedModel);

        descriptor = new EditCommand.EditPersonDescriptor();
        testList = new HashSet<>();
        testList.add(VALID_TEST_AMY);
        descriptor.setTests(testList);

        DeleteTestMarksCommand deleteTestMarksCommand = new DeleteTestMarksCommand(VALID_TEST_NAME_AMY);
        expectedMessage = String.format(DeleteTestMarksCommand.MESSAGE_SUCCESSFUL_DELETE_TEST, VALID_TEST_NAME_AMY);
        editedPerson = createEditedPerson(editedPerson, descriptor);
        Model expectedModelDelete = expectedModel;
        expectedModelDelete.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModelDelete.commitAddressBook();


        assertCommandSuccess(deleteTestMarksCommand, expectedModel, commandHistory, expectedMessage,
                expectedModelDelete);

    }

}

