package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITYLEVEL;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetPriorityLevelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.prioritylevel.PriorityLevel;

//@@author jylee-git
/**
 * Parses the given {@code String} of arguments in the context of the SetPriorityLevelCommand
 * and returns a SetPriorityCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class SetPriorityLevelCommandParser implements Parser<SetPriorityLevelCommand> {

    @Override
    public SetPriorityLevelCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(userInput, CliSyntax.PREFIX_PRIORITYLEVEL);

        if (!arePrefixesPresent(argMultiMap, PREFIX_PRIORITYLEVEL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetPriorityLevelCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetPriorityLevelCommand.MESSAGE_USAGE), pe);
        }

        if (!ParserUtil.hasOnlyOnePrefixOfSpecifiedType(userInput, PREFIX_PRIORITYLEVEL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetPriorityLevelCommand.MESSAGE_USAGE));
        }

        PriorityLevel priorityLevel = ParserUtil.parsePriorityLevel(argMultiMap.getValue(PREFIX_PRIORITYLEVEL).get());
        return new SetPriorityLevelCommand(index, priorityLevel);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
