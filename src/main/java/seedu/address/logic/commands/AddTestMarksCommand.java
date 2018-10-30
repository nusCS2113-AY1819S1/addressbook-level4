package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditTestMarksCommand.createEditedPerson;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_MARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

import seedu.address.commons.core.Messages;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Marks;
import seedu.address.model.grade.Test;
import seedu.address.model.grade.TestName;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * A command to add person test name and marks
 */
public class AddTestMarksCommand extends Command {
    /**
     * A command to add person test name and marks
     */
    public static final String COMMAND_WORD = "add_testmarks";
    public static final String COMMAND_WORD_2 = "adt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": invalid command Add to add test and marks "
            + "the command format should be .\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice " + PREFIX_TEST_NAME + "cs2113quiz1 " + PREFIX_TEST_MARK + "67";


    public static final String MESSAGE_DUPLICATE_TEST = "This test already exists in the system";
    public static final String MESSAGE_PERSONNAME_NOT_FOUND = "This person cannot be found";
    public static final String MESSAGE_PERSON_DUPLICATE_FOUND = "There are more then one person's name contain the keyword found please indicate the full name";
    public static final String MESSAGE_SUCCESS = "New test marks added";
    private final NameContainsKeywordsPredicate predicate;
    private final String testName;
    private final String testMarks;
    private final List<String> nameList;
    private final EditTestMarksCommand.EditPersonDescriptor editPersonDescriptor = null;

    public AddTestMarksCommand(NameContainsKeywordsPredicate predicate, String testName, String testMarks, List<String> nameList) {
        requireNonNull(testMarks, testName);
        this.predicate = predicate;
        this.testName = testName;
        this.testMarks = testMarks;
        this.nameList = nameList;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        List<Person> personListName = model.getFilteredPersonList();

        if (personListName.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_PERSONNAME_NOT_FOUND);
        } else {
            if (personListName.size() > 1) {
                String fullName = "";
                for (String name :nameList) {
                    fullName += name + " ";
                }
                fullName = fullName.substring(0, fullName.length() - 1).toUpperCase();
                boolean checked = false;
                boolean duplicate = false;
                for (Person person: personListName) {

                    if (fullName.equals(person.getName().fullName.toUpperCase())) {
                        return insertIntoPerson(person, model);
                    } else if (!fullName.equals(person.getName().fullName.toUpperCase()) && person.getName().fullName.toUpperCase().contains(fullName)) {
                        duplicate = true;
                } else {
                        checked = true;
                    }
                }
                if (!checked && duplicate ) {
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                    throw new CommandException(MESSAGE_PERSON_DUPLICATE_FOUND);
                } else if (checked && !duplicate) {
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                    throw new CommandException(MESSAGE_PERSONNAME_NOT_FOUND);
                }
            } else {
                return insertIntoPerson(personListName.get(0), model);
            }
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(
                String.format(MESSAGE_PERSONNAME_NOT_FOUND, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTestMarksCommand // instanceof handles nulls
                && predicate.equals(((AddTestMarksCommand) other).predicate)); // state check
    }

    /**
     * This is to add test name and marks to the student whose name is indicate in the input
     */
    private CommandResult insertIntoPerson(Person person, Model model) throws CommandException {
        Person personToEdit = person;
        EditTestMarksCommand.EditPersonDescriptor editPersonDescriptor =
                new EditTestMarksCommand.EditPersonDescriptor();
        Test test = null;
        try {
            test = new Test(new TestName(testName), new Marks(testMarks), new Grade("Undefined"));
        } catch (IllegalArgumentException e) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(e.getMessage());
        }
        Set<Test> testList = new HashSet<>();
        for (Test t : personToEdit.getTests()) {
            if (t.getTestName().testName.equals(test.getTestName().testName)) {
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
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
}
