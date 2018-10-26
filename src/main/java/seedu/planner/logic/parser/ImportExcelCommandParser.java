package seedu.planner.logic.parser;

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
        DirectoryPath directoryPath = ParserUtil.parseFilePath(trimmedArgs);
        return new ImportExcelCommand(directoryPath);
    }
}
