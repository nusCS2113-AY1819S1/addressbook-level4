package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.EditJobDetailsCommand.EditJobOfferDescriptor;
import seedu.recruit.logic.commands.EditJobDetailsCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditJobDetailsCommand object
 */
public class EditJobDetailsCommandParser implements Parser<EditJobDetailsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditJobDetailsCommand
     * and returns an EditJobDetailsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditJobDetailsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_NAME, PREFIX_GENDER, PREFIX_AGE_RANGE, 
                        PREFIX_EDUCATION, PREFIX_SALARY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditJobDetailsCommand.MESSAGE_USAGE),
                    pe);
        }

        EditJobOfferDescriptor editJobOfferDescriptor = new EditJobOfferDescriptor();

        if (argMultimap.getValue(PREFIX_JOB).isPresent()) {
            editJobOfferDescriptor.setJob(ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get()));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editJobOfferDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }
        if (argMultimap.getValue(PREFIX_AGE_RANGE).isPresent()) {
            editJobOfferDescriptor.setAgeRange(ParserUtil.parseAgeRange(argMultimap.getValue(PREFIX_AGE_RANGE).get()));
        }
        if (argMultimap.getValue(PREFIX_EDUCATION).isPresent()) {
            editJobOfferDescriptor.setEducation(ParserUtil.parseEducation(argMultimap.getValue(PREFIX_EDUCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            editJobOfferDescriptor.setSalary(ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get()));
        }

        if (!editJobOfferDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditJobDetailsCommand.MESSAGE_NOT_EDITED);
        }

        return new EditJobDetailsCommand(ParserUtil.parseCompanyName(argMultimap.getValue(PREFIX_COMPANY_NAME).get()), index, editJobOfferDescriptor);
    }

}
