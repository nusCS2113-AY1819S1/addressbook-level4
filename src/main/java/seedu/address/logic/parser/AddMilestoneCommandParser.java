package seedu.address.logic.parser;

import seedu.address.logic.commands.AddMilestoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Milestone;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;

//@@author JeremyInElysium
/**
 * Parses input arguments and creates a new AddMilestoneCommand object
 */
public class AddMilestoneCommandParser implements Parser<AddMilestoneCommand> {

    /**
     * Returns true if none of the prefixes contain empty {@code Optional} values in the given
     * {@code ArgumentMultiMap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public AddMilestoneCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_TITLE, PREFIX_MILESTONE, PREFIX_RANK);

        if(!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_MILESTONE, PREFIX_RANK)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMilestoneCommand.MESSAGE_USAGE));
        }

        String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        String milestoneDescription = ParserUtil.parseMilestoneDescription(argMultimap.getValue(PREFIX_MILESTONE).get());
        String rank = ParserUtil.parseRank(argMultimap.getValue(PREFIX_RANK).get());

        Milestone milestone = new Milestone(title, milestoneDescription, rank);

        return new AddMilestoneCommand(milestone);
    }
}
