package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILELOCATION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.IcsUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TimeTable;
import seedu.address.model.tag.Tag;

/**
 * Imports a timetable from a user-provided .ICS file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_WORD_ALIAS = "i";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports and overwrites timetable for the person identified by the "
            + " number used in the displayed person list. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_FILELOCATION + "FILE_LOCATION \n"
            + "Example: " + COMMAND_WORD
            + " 1 "
            + PREFIX_FILELOCATION + "C:\\Users\\happycat96\\Downloads\\nusmods_calendar.ics";

    public static final String MESSAGE_SUCCESS = "Imported timetable for %1$s.";
    public static final String MESSAGE_EMPTY = "Timetable file empty.";
    public static final String MESSAGE_IO_ERROR =
            "IO error: your .ics file is corrupted/ not compatible.";


    private final Index index;
    private final Path filePath;

    /**
     * Creates an ImportCommand to import the .ics data, parse it, and add a {@code Person} with this timetable
     */
    public ImportCommand(Index index, Path filePath) {
        //requireNonNull(timeTable);

        this.index = index;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Optional<TimeTable> optionalTimeTable;
        TimeTable timeTable;

        try {
            optionalTimeTable = IcsUtil.getTimeTableFromFile(filePath);
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_IO_ERROR);
        }
        if (!optionalTimeTable.isPresent()) {
            return new CommandResult(String.format(MESSAGE_EMPTY));
        }
        timeTable = optionalTimeTable.get();
        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person modifiedPerson = createModifiedPerson(personToEdit, timeTable);

        model.updatePerson(personToEdit, modifiedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, modifiedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * Only the TimeTable is changed in the new Person!
     */
    private static Person createModifiedPerson(Person personToEdit, TimeTable importedTimeTable) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();

        TimeTable timeTable = importedTimeTable;

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, timeTable);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        return true;
    }


}
