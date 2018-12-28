package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIGINAL_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIGINAL_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditMedicalReportCommand;
import seedu.address.logic.commands.EditMedicalReportCommand.EditReportDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicalreport.ReportDate;
import seedu.address.model.medicalreport.Title;

//@@author chewkahmeng
/**
 * Parses input arguments and creates a new EditMedicalReport object
 */
public class EditMedicalReportCommandParser implements Parser<EditMedicalReportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditMedicalReportCommand
     * and returns an EditMedicalReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditMedicalReportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ORIGINAL_TITLE,
                PREFIX_ORIGINAL_DATE, PREFIX_TITLE, PREFIX_DATE, PREFIX_INFO);
        if (!arePrefixesPresent(argMultimap, PREFIX_ORIGINAL_TITLE, PREFIX_ORIGINAL_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditMedicalReportCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditMedicalReportCommand.MESSAGE_USAGE), pe);
        }
        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_ORIGINAL_TITLE).get());
        ReportDate reportDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_ORIGINAL_DATE).get());
        EditReportDescriptor editReportDescriptor = new EditReportDescriptor();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editReportDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editReportDescriptor.setReportDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_INFO).isPresent()) {
            editReportDescriptor.setInformation(ParserUtil.parseInformation(argMultimap.getValue(PREFIX_INFO).get()));
        }
        if (!editReportDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMedicalReportCommand.MESSAGE_REPORT_NOT_EDITED);
        }
        return new EditMedicalReportCommand(index, title, reportDate, editReportDescriptor);
    }
}
