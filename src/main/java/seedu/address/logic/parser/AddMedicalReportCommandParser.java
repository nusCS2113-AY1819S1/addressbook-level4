package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_REPORT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddMedicalReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicalreport.MedicalReport;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MEDICAL_REPORT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND, AddMedicalReportCommand.MESSAGE_USAGE));
        }

        String report = argMultimap.getValue(PREFIX_MEDICAL_REPORT).orElse("");

        return new AddMedicalReportCommand(index, new MedicalReport(report));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }*/
}
