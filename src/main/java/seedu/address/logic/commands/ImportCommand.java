package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.IcsUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Friend;
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
    public static final String COMMAND_WORD_ALIAS = "im";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports your timetable from the file (.\\import_export\\[FILENAME].ics). "
            + "Parameters: "
            + "FILE_NAME (without the .ics extension) \n"
            + "Example: " + COMMAND_WORD
            + " my_import_file_name";

    public static final String MESSAGE_IMPORT_SUCCESS = "Imported timetable at: %1$s.";
    public static final String MESSAGE_EMPTY = "Could not obtain any TimeTable data from: %1$s"
            + "\nPlease check that the file is not empty, and contains timetable data.";
    public static final String MESSAGE_IO_ERROR =
            "Failed to read the file at: %1$s.\nPlease check the file exists.";
    private final Path filePath;

    /**
     * Creates an ImportCommand to import the .ics data, parse it, and add a {@code Person} with this timetable
     */
    public ImportCommand(Path filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person personToEdit = model.getUser();
        requireNonNull(personToEdit);

        Optional<TimeTable> optionalTimeTable;
        TimeTable timeTable;

        try {
            optionalTimeTable = IcsUtil.getInstance().readTimeTableFromFile(filePath);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_IO_ERROR, filePath.toString()));
        }

        if (!optionalTimeTable.isPresent()) {
            return new CommandResult(String.format(MESSAGE_EMPTY, filePath.toString()));
        }
        timeTable = optionalTimeTable.get();

        Person modifiedPerson = createModifiedPerson(personToEdit, timeTable);

        model.updatePerson(personToEdit, modifiedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        model.updateTimeTable(modifiedPerson.getTimeTable());
        return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, filePath.toString()));
    }

    /**
     * Creates and returns a {@code Person}, who has their (@code TimeTable) changed. (all else same.)
     */
    private static Person createModifiedPerson(Person personToEdit, TimeTable importedTimeTable) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        Set<Friend> updatedFriends = personToEdit.getFriends();

        return new Person(
                updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                importedTimeTable, updatedFriends);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImportCommand)) {
            return false;
        }

        return filePath.equals(((ImportCommand) other).filePath);
    }
}
