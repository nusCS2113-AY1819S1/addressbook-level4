package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.logic.commands.SortCandidateCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCandidateCommand object
 */
public class SortCandidateCommandParser implements Parser<SortCandidateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCandidateCommand
     * and returns an SortCandidateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCandidateCommand parse(String arg) throws ParseException {
        requireNonNull(arg);

        switch(arg.trim()) {
        case "n/":
            return new SortCandidateCommand(PREFIX_NAME);
        case "x/":
            return new SortCandidateCommand(PREFIX_AGE);
        case "e/":
            return new SortCandidateCommand(PREFIX_EMAIL);
        case "j/":
            return new SortCandidateCommand(PREFIX_JOB);
        case "h/":
            return new SortCandidateCommand(PREFIX_EDUCATION);
        case "s/":
            return new SortCandidateCommand(PREFIX_SALARY);
        case "r/":
            return new SortCandidateCommand(PREFIX_REVERSE);
        default:
            throw new ParseException(SortCandidateCommand.MESSAGE_TAG_USAGE);
        }
    }
}
