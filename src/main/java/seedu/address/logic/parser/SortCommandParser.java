package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.Parameter;


/**
 * Parses input arguments and creates a new {@code SortCommand} object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code SortCommand}
     * and returns a {@code SortCommand} object for execution.
     */
    public SortCommand parse(String args) {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT);
        Parameter parameter = new Parameter(argMultimap.getValue(PREFIX_SORT).orElse(""));
        return new SortCommand(parameter);
    }
}
