package seedu.address.logic.parser.Member;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.MemberCommand.FindPostalcodeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.PostalcodeContainsKeywordPredicate;

/**
 * parses postalcode command
 */
public class FindPostalcodeCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPostalcodeCommand
     * and returns an FindPostalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPostalcodeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPostalcodeCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPostalcodeCommand(new PostalcodeContainsKeywordPredicate(Arrays.asList(nameKeywords)));
    }
}

