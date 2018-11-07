package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DIR;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.util.ExcelUtil;
import seedu.planner.logic.commands.ImportExcelCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

//@author nguyenngoclinhchi
/**
 * Parses input arguments and create ImportExcelCommand object.
 */
public class ImportExcelCommandParser implements Parser<ImportExcelCommand> {
    /**
     * Parses the given code {@code String} of arguments in the context of the ImportExcelCommand
     * @param args the given input
     * @return an ImportExcelCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ImportExcelCommand parse (String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ImportExcelCommand.MESSAGE_USAGE));
        }
        return createImportExcelCommand(args);
    }

    /**
     * Create the Import Command with given String args
     */
    private static ImportExcelCommand createImportExcelCommand (String args) throws ParseException {
        String directoryPath = " ";
        String checkedDirectoryPath;
        String nameFile;
        boolean isNameExist = true;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DIR);
        if (!arePrefixesPresent(argMultimap, PREFIX_DIR) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ImportExcelCommand.MESSAGE_USAGE));
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            isNameExist = false;
        }
        if (isNameExist && argMultimap.getValue(PREFIX_NAME).isPresent()
                && !(argMultimap.getValue(PREFIX_NAME).get() + " ").trim().isEmpty()) {
            nameFile = argMultimap.getValue(PREFIX_NAME).get().trim();
            directoryPath = ParserUtil.parseDirectoryString(argMultimap.getValue(PREFIX_DIR).get().trim());
            checkedDirectoryPath = ParserUtil.parseFilePathString(ExcelUtil.setPathFile(nameFile, directoryPath));
        } else {
            checkedDirectoryPath = ParserUtil.parseFilePathString(directoryPath);
        }
        return new ImportExcelCommand(checkedDirectoryPath);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
