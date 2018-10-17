package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.logic.commands.SortByNameCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortByNameCommand object
 */
public class SortByNameCommandParser implements Parser<SortByNameCommand> {

    public static final String MESSAGE_EXCEED_TAG_LIMIT = "Please sort by only one tag.";
    /**
     * Parses the given {@code String} of arguments in the context of the SortByNameCommand
     * and returns an SortByNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortByNameCommand parse(String arg) throws ParseException {
        requireNonNull(arg);

        switch(arg) {
            case " n/":
                return new SortByNameCommand(PREFIX_NAME);
            case " g/":
                return new SortByNameCommand(PREFIX_GENDER);
            case " x/":
                return new SortByNameCommand(PREFIX_AGE);
            case " p/":
                return new SortByNameCommand(PREFIX_PHONE);
            case " e/":
                return new SortByNameCommand(PREFIX_EMAIL);
            case " a/":
                return new SortByNameCommand(PREFIX_ADDRESS);
            case " j/":
                return new SortByNameCommand(PREFIX_JOB);
            case " h/":
                return new SortByNameCommand(PREFIX_EDUCATION);
            case " s/":
                return new SortByNameCommand(PREFIX_SALARY);
            default:
                throw new ParseException(MESSAGE_EXCEED_TAG_LIMIT);
        }
    }
}
