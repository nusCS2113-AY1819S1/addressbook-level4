package seedu.address.logic.parser;

import com.sun.javafx.tools.packager.PackagerException;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Parses input arguments and creates a new GroupCommand object
 */

public class GroupCommandParser implements Parser<GroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GroupCommand
     * and returns an GroupCommand object for execution.
     */

    public GroupCommand parse(String args) {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        String name = argumentMultimap.getValue(PREFIX_NAME).orElse("");
        return new GroupCommand(name);
    }
}
