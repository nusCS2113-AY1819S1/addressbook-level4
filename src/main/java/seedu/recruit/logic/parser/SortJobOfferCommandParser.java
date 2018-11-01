package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.logic.commands.SortJobOfferCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortJobOfferCommand object
 */
public class SortJobOfferCommandParser implements Parser<SortJobOfferCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortJobOfferCommand
     * and returns an SortJobOfferCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortJobOfferCommand parse(String arg) throws ParseException {
        requireNonNull(arg);

        switch(arg.trim()) {
        case "c/":
            return new SortJobOfferCommand(PREFIX_COMPANY_NAME);
        case "j/":
            return new SortJobOfferCommand(PREFIX_JOB);
        case "xr/":
            return new SortJobOfferCommand(PREFIX_AGE_RANGE);
        case "h/":
            return new SortJobOfferCommand(PREFIX_EDUCATION);
        case "s/":
            return new SortJobOfferCommand(PREFIX_SALARY);
        case "r/":
            return new SortJobOfferCommand(PREFIX_REVERSE);
        default:
            throw new ParseException(SortJobOfferCommand.MESSAGE_TAG_USAGE);
        }
    }
}
