package seedu.planner.logic.parser;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.logic.commands.SetPathCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.DirectoryPath;

/**
 * Parses input arguments and create SetPathCommand object.
 */
public class SetPathCommandParser implements Parser<SetPathCommand> {
    private Logger logger = LogsCenter.getLogger(SetPathCommandParser.class);

    @Override
    public SetPathCommand parse(String args) throws ParseException {
        if (!DirectoryPath.isValidDirectory(args)) {
            throw new ParseException(DirectoryPath.MESSAGE_DIRECTORYPATH_CONSTRAINTS);
        }
        DirectoryPath directoryPath = new DirectoryPath(args);
        return new SetPathCommand(directoryPath);
    }
}
