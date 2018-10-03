package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timetable.Appt;

/**
 * Parses input arguments and creates a new {@code AddApptCommand} object
 */
public class AddApptCommandParser implements Parser<AddApptCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddApptCommand}
     * and returns a {@code AddApptCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddApptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPT_INFO);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE), ive);
        }

        // there is no appt class yet, so just return the appt info for now
        String appt = argMultimap.getValue(PREFIX_APPT_INFO).orElse("");
        return new AddApptCommand(index, new Appt(appt));
    }
}
