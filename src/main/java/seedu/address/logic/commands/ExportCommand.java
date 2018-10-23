package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILELOCATION;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.IcsUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.TimeTable;

/**
 * Export a timetable as a .ICS file to a user-specified filePath.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_WORD_ALIAS = "exim";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports timetable for the person identified by the "
            + " number used in the displayed person list. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_FILELOCATION + "FILE_LOCATION \n"
            + "Example: " + COMMAND_WORD
            + " 1 "
            + PREFIX_FILELOCATION + "C:\\just_a_folder_for_exports\\nusmods_calendar.ics";

    public static final String MESSAGE_SUCCESS = "Exported timetable to %1$s.";
    public static final String MESSAGE_EMPTY = "Timetable file empty.";
    public static final String MESSAGE_IO_ERROR =
            "IO error: failed to write the timetable to the path specified.";
    private final Index index;
    private final Path filePath;

    /**
     * Creates an ExportCommand to export the specified person's timetable as .ics file
     */
    public ExportCommand(Index index, Path filePath) {
        requireNonNull(index);
        requireNonNull(filePath);

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

        TimeTable timeTable;
        timeTable = model.getTimeTable();
        if (timeTable.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY);
        }
        try {
            IcsUtil.getInstance().saveTimeTableToFile(timeTable, filePath);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_IO_ERROR);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath.toString()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        return true;
    }


}
