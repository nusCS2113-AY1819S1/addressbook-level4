package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILELOCATION;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.FileLocation;
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
            + "Compatible with .ICS file exported from NUSMODS. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_FILELOCATION + "FILE_LOCATION \n"
            + "Example: " + COMMAND_WORD
            + " 1 "
            + PREFIX_FILELOCATION + "C:\\Users\\happycat96\\Downloads\\nusmods_calendar.ics";

    public static final String MESSAGE_SUCCESS = "Imported timetable for %1$s.";
    public static final String MESSAGE_IO_ERROR =
            "IO error: Ensure your path is correct, and that your .ics file has not been corrupted.";

    private final Index index;
    private final FileLocation fileLocation;

    /**
     * Creates an ImportCommand to import the .ics data, parse it, and add a {@code Person} with this timetable
     */
    public ImportCommand(Index index, FileLocation fileLocation) {
        //requireNonNull(timeTable);

        this.index = index;
        this.fileLocation = fileLocation;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        TimeTable importedTimeTable = ics2TimeTable(fileLocation);

        return new CommandResult(String.format(MESSAGE_SUCCESS, importedTimeTable));
    }

    /**
     * Attempts to read the file at the path specified. Returns a timetable object.
     */
    private TimeTable ics2TimeTable(FileLocation fileLocation) throws CommandException { //improve name pls
        if (false /*file is invalid/ unreadable*/) {
            throw new CommandException(MESSAGE_IO_ERROR);
        }
        return new TimeTable();
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, TimeTable importedTimeTable) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();

        TimeTable timeTable = personToEdit.getTimeTable();

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
