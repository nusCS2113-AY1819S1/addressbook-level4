package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditTestMarksCommand.createEditedPerson;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

import seedu.address.commons.core.Messages;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.grade.Marks;
import seedu.address.model.grade.Test;
import seedu.address.model.grade.TestName;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;


public class AddTestMarksCommand extends Command {
    /**
     * A command to add person test name and marks
     */
    public static final String COMMAND_WORD = "add_testmarks";
    public static final String COMMAND_WORD_2 = "addt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add test and marks to persons whose names"
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice cs2113quiz1 67";


    public static final String MESSAGE_DUPLICATE_TEST = "This test already exists in the system";
    private final NameContainsKeywordsPredicate predicate;
    private final String testName;
    private final String testMarks;
    private final EditTestMarksCommand.EditPersonDescriptor editPersonDescriptor = null;

    public AddTestMarksCommand(NameContainsKeywordsPredicate predicate, String testName, String testMarks) {
        this.predicate = predicate;
        this.testName = testName;
        this.testMarks = testMarks;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        List<Person> personListName = model.getFilteredPersonList();
        EditTestMarksCommand.EditPersonDescriptor editPersonDescriptor =
                new EditTestMarksCommand.EditPersonDescriptor();
        Person personToEdit = personListName.get(0);

        Test test = new Test(new TestName(testName), new Marks(testMarks));

        Set<Test> testList = new HashSet<>();
        for (Test t : personToEdit.getTests()) {
            if (t.getTestName().testName.equals(test.getTestName().testName)) {
                throw new CommandException(MESSAGE_DUPLICATE_TEST);
            }
        }

        testList.addAll(personToEdit.getTests());
        testList.add(test);
        editPersonDescriptor.setTests(testList);

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);


        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(
                String.format(Messages.MESSAGE_ADDED_TEST_LIST, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTestMarksCommand // instanceof handles nulls
                && predicate.equals(((AddTestMarksCommand) other).predicate)); // state check
    }



}
