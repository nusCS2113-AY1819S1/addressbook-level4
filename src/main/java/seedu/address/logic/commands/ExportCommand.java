package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.ZoneId;

import seedu.address.commons.util.IcsUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.TimeTable;

/**
 * Export a timetable as a .ICS file to a user-specified filePath.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_WORD_ALIAS = "ex";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the currently shown timetable to the file (.\\import_export\\FILENAME.ics). "
            + "Parameters: "
            + "FILENAME (without .ics extension) \n"
            + "Example: " + COMMAND_WORD
            + " my_export_file_name";

    public static final String MESSAGE_EXPORT_SUCCESS = "Exported timetable to %1$s.";
    public static final String MESSAGE_EMPTY = "Timetable is empty. Export failed.";
    public static final String MESSAGE_IO_ERROR =
            "Failed to write the timetable to the path: ";
    private final Path filePath;

    /**
     * Creates an ExportCommand to export the specified person's timetable as .ics file
     */
    public ExportCommand(Path filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        //TODO: remove hardcoding of timezone in import and export command once TimeSlots are in UTC.
        ZoneId zoneIdSingapore = ZoneId.of("Asia/Shanghai");

        TimeTable timeTable = model.getTimeTable();
        if (timeTable.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY);
        }
        try {
            IcsUtil.getInstance().saveTimeTableToFile(timeTable, zoneIdSingapore, filePath);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_IO_ERROR + filePath.toString()));
        }
        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, filePath.toString()));
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

        return filePath.equals(((ExportCommand) other).filePath);
    }
}
