package seedu.divelog.logic.parser;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.enums.Units;
import seedu.divelog.logic.commands.SetUnitsCommand;
import seedu.divelog.logic.parser.exceptions.ParseException;

/**
 * Implements the command for SetUnits
 */
public class SetUnitsCommandParser implements Parser<SetUnitsCommand> {
    /**
     * Parses the set_units command.
     * @param userInput - The arguments
     * @return the Command to execute
     * @throws ParseException if format is invalid
     */
    @Override
    public SetUnitsCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetUnitsCommand.MESSAGE_USAGE));
        }
        switch (trimmedArgs) {
        case "meters":
            return new SetUnitsCommand(Units.METERS);
        case "feet":
            return new SetUnitsCommand(Units.FEET);
        default:
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetUnitsCommand.MESSAGE_USAGE));
        }
    }
}
