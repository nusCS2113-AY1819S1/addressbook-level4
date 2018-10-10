package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_HIGHEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDIAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEVENTY_FIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TWENTY_FIVE;

import seedu.address.logic.commands.GradeSummaryCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class GradeSummaryCommandParser implements Parser<GradeSummaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradeSummaryCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TWENTY_FIVE, PREFIX_SEVENTY_FIVE,
                        PREFIX_HIGHEST, PREFIX_MEAN, PREFIX_MEDIAN);

        String commandToRun = "";

        if (argMultimap.getValue(PREFIX_TWENTY_FIVE).isPresent()) {
            commandToRun = "25";
        }
        if (argMultimap.getValue(PREFIX_SEVENTY_FIVE).isPresent()) {
            commandToRun = "75";
        }
        if (argMultimap.getValue(PREFIX_HIGHEST).isPresent()) {
            commandToRun = "H";
        }
        if (argMultimap.getValue(PREFIX_MEAN).isPresent()) {
            commandToRun = "MEAN";
        }
        if (argMultimap.getValue(PREFIX_MEDIAN).isPresent()) {
            commandToRun = "MEDIAN";
        }

        return new GradeSummaryCommand(commandToRun);
    }

}
