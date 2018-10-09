package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import java.util.stream.Stream;

import seedu.recruit.logic.commands.AddJobDetailsCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.AgeRange;
import seedu.recruit.model.joboffer.Job;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.joboffer.Salary;


/**
 * Parses input arguments and creates a new AddJobDetailsCommand object
 */
public class AddJobDetailsCommandParser implements Parser<AddJobDetailsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddJobDetailsCommand
     * and returns an AddJobDetailsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddJobDetailsCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(" " + args, PREFIX_COMPANY_NAME, PREFIX_JOB, PREFIX_GENDER,
                        PREFIX_AGE_RANGE, PREFIX_EDUCATION, PREFIX_SALARY);

        if (!arePrefixesPresent(argMultimap, PREFIX_COMPANY_NAME, PREFIX_JOB, PREFIX_GENDER, PREFIX_AGE_RANGE,
                PREFIX_EDUCATION, PREFIX_SALARY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobDetailsCommand.MESSAGE_USAGE));
        }

        CompanyName companyName = ParserUtil.parseCompanyName(argMultimap.getValue(PREFIX_COMPANY_NAME).get());
        Job job = ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        AgeRange ageRange = ParserUtil.parseAgeRange(argMultimap.getValue(PREFIX_AGE_RANGE).get());
        Education education = ParserUtil.parseEducation(argMultimap.getValue(PREFIX_EDUCATION).get());
        Salary salary = ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get());

        JobOffer jobOffer = new JobOffer(companyName, job, gender, ageRange, education, salary);

        return new AddJobDetailsCommand(jobOffer);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
