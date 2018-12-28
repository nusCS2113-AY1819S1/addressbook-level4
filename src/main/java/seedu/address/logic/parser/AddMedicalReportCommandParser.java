package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddMedicalReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicalreport.Information;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.medicalreport.ReportDate;
import seedu.address.model.medicalreport.Title;

//@@author chewkahmeng
/**
 * Parses input arguments and creates a new AddMedicalReport object
 */
public class AddMedicalReportCommandParser implements Parser<AddMedicalReportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddMedicalReportCommand
     * and returns an AddMedicalReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddMedicalReportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DATE,
                PREFIX_INFO);
        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_DATE, PREFIX_INFO)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND, AddMedicalReportCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND, AddMedicalReportCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        ReportDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Information information = ParserUtil.parseInformation(argMultimap.getValue(PREFIX_INFO).get());

        return new AddMedicalReportCommand(index, new MedicalReport(title, date, information));
    }
}
