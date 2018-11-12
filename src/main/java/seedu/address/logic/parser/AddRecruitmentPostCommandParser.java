package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINIMUM_EXPERIENCE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddRecruitmentPostCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recruitment.JobDescription;
import seedu.address.model.recruitment.Post;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.WorkExp;


/**
 * Parses input arguments and creates a new AddRecruitmentPostCommand object
 */
public class AddRecruitmentPostCommandParser implements Parser<AddRecruitmentPostCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRecruitmentPostCommand
     * and returns an AddRecruitmentPostCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRecruitmentPostCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_JOB_POSITION, PREFIX_MINIMUM_EXPERIENCE, PREFIX_JOB_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_JOB_POSITION, PREFIX_MINIMUM_EXPERIENCE, PREFIX_JOB_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddRecruitmentPostCommand.MESSAGE_USAGE2));
        }
        Post post = ParserUtil.parsePost(argMultimap.getValue
                (PREFIX_JOB_POSITION).get());
        WorkExp workExp = ParserUtil.parseWorkExp(argMultimap.getValue(PREFIX_MINIMUM_EXPERIENCE)
                .get());
        JobDescription jobDescription = ParserUtil.parseJobDescription(argMultimap.getValue(PREFIX_JOB_DESCRIPTION)
                .get());
        Recruitment recruitment = new Recruitment (post, workExp, jobDescription);
        return new AddRecruitmentPostCommand(recruitment);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
