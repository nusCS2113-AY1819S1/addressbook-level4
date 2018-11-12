package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINIMUM_EXPERIENCE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditRecruitmentPostCommand;
import seedu.address.logic.commands.EditRecruitmentPostCommand.EditPostDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditRecruitmentPostCommand object
 */
public class EditRecruitmentPostCommandParser implements Parser<EditRecruitmentPostCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRecruitmentPostCommand
     * and returns an EditRecruitmentPostCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRecruitmentPostCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_JOB_POSITION,
                        PREFIX_MINIMUM_EXPERIENCE, PREFIX_JOB_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRecruitmentPostCommand.MESSAGE_USAGE), pe);
        }

        EditPostDescriptor editPostDescriptor = new EditPostDescriptor();
        if (argMultimap.getValue(PREFIX_JOB_POSITION).isPresent()) {
            editPostDescriptor.setPost(ParserUtil.parsePost(argMultimap.getValue(PREFIX_JOB_POSITION).get()));
        }
        if (argMultimap.getValue(PREFIX_MINIMUM_EXPERIENCE).isPresent()) {
            editPostDescriptor.setWorkExp(ParserUtil.parseWorkExp(
                    argMultimap.getValue(PREFIX_MINIMUM_EXPERIENCE).get()));
        }
        if (argMultimap.getValue(PREFIX_JOB_DESCRIPTION).isPresent()) {
            editPostDescriptor.setJobDescription(ParserUtil.parseJobDescription(
                    argMultimap.getValue(PREFIX_JOB_DESCRIPTION).get()));
        }

        if (!editPostDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRecruitmentPostCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRecruitmentPostCommand(index, editPostDescriptor);
    }

}
