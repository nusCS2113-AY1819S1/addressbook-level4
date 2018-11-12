package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.divelog.commons.core.Messages.MESSAGE_ERROR_DIVES_OVERLAP;
import static seedu.divelog.commons.core.Messages.MESSAGE_ERROR_LIMIT_EXCEED;
import static seedu.divelog.commons.core.Messages.MESSAGE_INVALID_TIME_FORMAT;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.logic.parser.CliSyntax;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.DiveOverlapsException;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;

/**
 * Adds a dive to the divelog book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a dive to the divelog book. "
            + "Parameters: "
            + CliSyntax.PREFIX_DATE_START + "DATE_START (DDMMYYYY Format) "
            + CliSyntax.PREFIX_TIME_START + "START_TIME (24Hr Format) "
            + CliSyntax.PREFIX_DATE_END + "DATE_END (DDMMYYYY Format) "
            + CliSyntax.PREFIX_TIME_END + "END_TIME (24Hr Format) "
            + CliSyntax.PREFIX_SAFETY_STOP + "SAFETY_STOP_TIME (24Hr Format) \n\t"
            + CliSyntax.PREFIX_DEPTH + "DEPTH "
            + CliSyntax.PREFIX_LOCATION + "LOCATION"
            + CliSyntax.PREFIX_TIMEZONE + "TIMEZONE\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE_START + "07112018 "
            + CliSyntax.PREFIX_TIME_START + "0700 "
            + CliSyntax.PREFIX_DATE_END + "07112018 "
            + CliSyntax.PREFIX_TIME_END + "0800 "
            + CliSyntax.PREFIX_SAFETY_STOP + "0745 "
            + CliSyntax.PREFIX_DEPTH + "16 "
            + CliSyntax.PREFIX_LOCATION + "Sentosa "
            + CliSyntax.PREFIX_TIMEZONE + "+8";

    public static final String MESSAGE_SUCCESS = "New dive session added!";

    private DiveSession toAdd;

    /**
     * Creates an AddCommand to add the specified {@code DiveSession}
     */
    public AddCommand(DiveSession dive) {
        requireNonNull(dive);
        toAdd = dive;
    }


    /**
     *Checks if the dive to be added is a first dive or a repeat dive to calculate the correct pressure group,
     * then adds accordingly
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        try {
            model.addDiveSession(toAdd);
            model.commitDiveLog();
            if (model.getPlanningMode()) {
                model.plannerCountPlus();
            }
        } catch (LimitExceededException le) {
            throw new CommandException(MESSAGE_ERROR_LIMIT_EXCEED);
        } catch (InvalidTimeException ive) {
            throw new CommandException(MESSAGE_INVALID_TIME_FORMAT);
        } catch (DiveOverlapsException de) {
            throw new CommandException(MESSAGE_ERROR_DIVES_OVERLAP);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
