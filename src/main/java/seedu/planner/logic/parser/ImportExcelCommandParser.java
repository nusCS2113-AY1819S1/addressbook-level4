package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DIR;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.commands.ImportExcelCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.DirectoryPath;

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
        String directoryPath;
        String nameFile;
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ImportExcelCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DIR);
        if (!argMultimap.getValue(PREFIX_DIR).isPresent()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ImportExcelCommand.MESSAGE_USAGE));
        }
        directoryPath = ParserUtil.parseFilePathString(argMultimap.getValue(PREFIX_DIR).get().trim());
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            nameFile = argMultimap.getValue(PREFIX_NAME).get().trim();
            directoryPath += DirectoryPath.FILE_SEPERATOR + nameFile + ".xlsx";
        }
        return new ImportExcelCommand(directoryPath);
    }
}
