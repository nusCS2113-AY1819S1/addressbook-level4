package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.grade.Test;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * A command to delete certain test from student.
 */
public class DeleteTestMarksCommand extends Command {
    /**
     * A command to delete certain test from student.
     */
    public static final String COMMAND_WORD = "delete_test";
    public static final String COMMAND_WORD_2 = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": delete certain test from all student taking the test"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD_2 + " " + PREFIX_TEST_NAME + "cs2113quiz1 ";


    public static final String MESSAGE_NOT_FOUND_TEST = "%1$s is not Found please add first.";
    public static final String MESSAGE_SUCCESSFUL_DELETE_TEST = "%1$s have been successfully delete from all students";
    public static final String MESSAGE_NO_STUDENT = "No student in list to delete tests from";
    private final String testName;

    public DeleteTestMarksCommand(String testName) {
        requireNonNull(testName);
        this.testName = testName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> personListName = model.getFilteredPersonList();

        if (personListName.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_NO_STUDENT);
        } else {
            boolean anyUpdate = false;
            for (Person person: personListName) {
                boolean updateNeeded = false;
                for (Test test: person.getTests()) {
                    if (test.getTestName().testName.toUpperCase().equals(testName.toUpperCase())){
                        updateNeeded = true;
                    }
                }
                if (updateNeeded)
                    anyUpdate = editPersonTests(person,model);
            }

            if (anyUpdate)
                return new CommandResult(
                        String.format(MESSAGE_SUCCESSFUL_DELETE_TEST, testName));
        }

        throw new CommandException(
                String.format(MESSAGE_NOT_FOUND_TEST, testName));
    }
    /**
     * editPersonTests
     */
    private boolean editPersonTests(Person person, Model model) throws CommandException {
        Person personToEdit = person;
       EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();

        Set<Test> testList = new HashSet<>();
        testList.addAll(personToEdit.getTests());

        for (Test t : personToEdit.getTests()) {
            if (t.getTestName().testName.toUpperCase().equals(testName.toUpperCase())) {
                testList.remove(t);
            }
        }

        editPersonDescriptor.setTests(testList);
        Person editedPerson = EditCommand.createEditedPerson(personToEdit, editPersonDescriptor);
        model.updatePerson(personToEdit, editedPerson);
        model.commitAddressBook();
        return true;
    }

    }