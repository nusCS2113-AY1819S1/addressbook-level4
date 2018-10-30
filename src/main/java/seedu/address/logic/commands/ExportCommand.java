package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Filetype;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

//@@author jitwei98
/**
 * Exports the selected person in the address book to a csv/vcf file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the selected person in the address book"
            + "\nParameters: INDEX (must be a positive integer)"
            + "\nParameters: FILETYPE (must be either \"csv\" or \"vcf\") "
            + "\nExample: " + COMMAND_WORD + " 1 csv ";

    public static final String MESSAGE_EXPORT_PERSON_SUCCESS = "Exported Person: %1$s";
    private static final String MESSAGE_FAILURE = "Export failed!";

    private final Index targetIndex;
    private final Filetype filetype;

    public ExportCommand(Index index, Filetype filetype) {
        requireNonNull(index);
        requireNonNull(filetype);

        this.targetIndex = index;
        this.filetype = filetype;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToExport = lastShownList.get(targetIndex.getZeroBased());
        try {
            model.exportPerson(personToExport);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(String.format(MESSAGE_EXPORT_PERSON_SUCCESS, personToExport));
    }

    @Override
    public boolean equals(Object other) {
        // same object
        if (other == this) {
            return true;
        }

        // handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        // checks state
        ExportCommand e = (ExportCommand) other;
        return targetIndex.equals(e.targetIndex) && filetype.equals(e.filetype);
    }
}
