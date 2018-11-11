package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddTestMarksCommand.MESSAGE_PERSONNAME_NOT_FOUND;
import static seedu.address.logic.commands.AddTestMarksCommand.MESSAGE_PERSON_DUPLICATE_FOUND;
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
 * A command to edit person test marks
 */
public class EditTestMarksCommand extends Command {
    /**
     * A command to edit person test marks
     */
    public static final String COMMAND_WORD = "edit_test";
    public static final String COMMAND_WORD_2 = "edt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": edit test marks of students \n"
            + "the test should be an available test which was added before\n"
            + "Example: " + COMMAND_WORD_2 + " alice " + PREFIX_TEST_NAME + "cs2113PE " + PREFIX_TEST_MARK + "67";


    public static final String MESSAGE_NOT_FOUND_TEST = "Test Name is not Found please add first.";
    private final NameContainsKeywordsPredicate predicate;
    private final String testName;
    private final String testMarks;
    private final String testGrade;
    private final List<String> nameList;

    public EditTestMarksCommand(NameContainsKeywordsPredicate predicate, String testName,
                                String testMarks, String testGrade, List<String> nameList) {
        requireNonNull(testMarks);
        requireNonNull(testName);
        requireNonNull(predicate);
        requireNonNull(nameList);

        this.predicate = predicate;
        this.testName = testName;
        this.testMarks = testMarks;
        this.testGrade = testGrade;
        this.nameList = nameList;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditTestMarksCommand // instanceof handles nulls
                && predicate.equals(((EditTestMarksCommand) other).predicate)); // state check
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);


        List<Person> personListName = model.getFilteredPersonList();
        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        String grade = "Undefined";
        if (testGrade != null) {
            grade = testGrade;
        }
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
                        return editPersonMarks(person, model);
                    } else if (!fullName.equals(person.getName().fullName.toUpperCase())
                            && person.getName().fullName.toUpperCase().contains(fullName)) {
                        duplicate = true;
                    } else {
                        checked = true;
                    }
                }
                if (!checked && duplicate) {
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                    throw new CommandException(MESSAGE_PERSON_DUPLICATE_FOUND);
                } else if (checked && !duplicate) {
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                    throw new CommandException(MESSAGE_PERSONNAME_NOT_FOUND);
                } else if (checked && duplicate) {
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                    throw new CommandException(MESSAGE_PERSON_DUPLICATE_FOUND);
                }
            } else {
                return editPersonMarks(personListName.get(0), model);
            }
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_NOT_FOUND, model.getFilteredPersonList().size()));
    }
    /**
     * createEditedPerson
     */
    private CommandResult editPersonMarks(Person person, Model model) throws CommandException {
        Person personToEdit = person;
        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        Test test = null;
        Grade assignGrade = new Grade("Undefined");
        if (this.testGrade != null) {
            assignGrade = new Grade(this.testGrade);
        }
        try {
            test = new Test(new TestName(testName), new Marks(testMarks), assignGrade);
        } catch (IllegalArgumentException e) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(e.getMessage());
        }
        Set<Test> testList = new HashSet<>();
        testList.addAll(personToEdit.getTests());

        boolean checkExists = false;
        for (Test t : personToEdit.getTests()) {
            if (t.getTestName().testName.equals(test.getTestName().testName)) {
                checkExists = true;
                testList.remove(t);
                testList.add(test);
            }
        }

        if (!checkExists) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_NOT_FOUND_TEST);
        }

        editPersonDescriptor.setTests(testList);
        Person editedPerson = EditCommand.createEditedPerson(personToEdit, editPersonDescriptor);


        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();


        return new CommandResult(
                String.format(Messages.MESSAGE_UPDATED_TEST_LIST, model.getFilteredPersonList().size()));
    }


}

