package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;

import seedu.recruit.logic.commands.SortCompanyCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCompanyCommand object
 */
public class SortCompanyCommandParser implements Parser<SortCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCompanyCommand
     * and returns an SortCompanyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCompanyCommand parse(String arg) throws ParseException {
        requireNonNull(arg);

        switch(arg.trim()) {
        case "c/":
            return new SortCompanyCommand(PREFIX_COMPANY_NAME);
        case "e/":
            return new SortCompanyCommand(PREFIX_EMAIL);
        default:
            throw new ParseException(SortCompanyCommand.MESSAGE_TAG_USAGE);
        }
    }
}
