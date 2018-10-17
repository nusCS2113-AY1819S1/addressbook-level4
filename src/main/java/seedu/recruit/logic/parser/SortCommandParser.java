package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.logic.commands.SortCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortByNameCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortByNameCommand
     * and returns an SortByNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String arg) throws ParseException {
        requireNonNull(arg);

        switch(arg) {
            case " n/":
                return new SortCommand(PREFIX_NAME);
            case " x/":
                return new SortCommand(PREFIX_AGE);
            case " e/":
                return new SortCommand(PREFIX_EMAIL);
            case " j/":
                return new SortCommand(PREFIX_JOB);
            case " h/":
                return new SortCommand(PREFIX_EDUCATION);
            case " s/":
                return new SortCommand(PREFIX_SALARY);
            default:
                throw new ParseException(SortCommand.MESSAGE_TAG_USAGE);
        }
    }
}
