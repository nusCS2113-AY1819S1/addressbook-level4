package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDUCATION_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCandidateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.candidate.Address;
import seedu.address.model.candidate.Age;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.Education;
import seedu.address.model.candidate.Email;
import seedu.address.model.candidate.Gender;
import seedu.address.model.candidate.Job;
import seedu.address.model.candidate.Name;
import seedu.address.model.candidate.Phone;
import seedu.address.model.candidate.Salary;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCandidateCommand object
 */
public class AddCandidateCommandParser implements Parser<AddCandidateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCandidateCommand
     * and returns an AddCandidateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCandidateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GENDER, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCandidateCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Candidate candidate = new Candidate(name, gender, phone, email, address, tagList);

        return new AddCandidateCommand(candidate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
