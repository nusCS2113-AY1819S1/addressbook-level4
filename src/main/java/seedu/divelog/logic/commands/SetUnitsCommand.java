package seedu.divelog.logic.commands;

import seedu.divelog.commons.core.ApplicationState;
import seedu.divelog.commons.enums.Units;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.model.Model;

/**
 * The command for setting the desired units
 */
public class SetUnitsCommand extends Command {
    public static final String COMMAND_WORD = "set_units";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " meters|feet : Sets the display units. \n Example:"
            + "\t" + COMMAND_WORD + " feet: sets the display units to feet.";
    private final Units units;
    public SetUnitsCommand(Units units) {
        this.units = units;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        ApplicationState.getInstance().setUnits(units);
        return new CommandResult("Units updated successfully");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SetUnitsCommand)) {
            return false;
        }
        return ((SetUnitsCommand) obj).units == units;
    }
}
