package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.GradebookAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GradebookAdd object
 */
public class GradebookAddParser implements Parser<GradebookAddCommand> {

    public GradebookAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_NAME, PREFIX_GB_ITEM,
                + PREFIX_GB_MAXMARKS, PREFIX_GB_WEIGHTAGE);
    }
}