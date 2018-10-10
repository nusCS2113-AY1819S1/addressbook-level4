package seedu.address.logic.parser;


import java.util.stream.Stream;

import seedu.address.logic.commands.GradeSummaryCommand;

import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.parser.CliSyntax.PREFIX_25th;
import static seedu.address.logic.parser.CliSyntax.PREFIX_75th;

import static seedu.address.logic.parser.CliSyntax.PREFIX_HIGHEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDIAN;


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
                ArgumentTokenizer.tokenize(args, PREFIX_25th,PREFIX_75th,PREFIX_HIGHEST,PREFIX_MEAN,PREFIX_MEDIAN);

        String commandToRun = "";

        if(argMultimap.getValue(PREFIX_25th).isPresent()){
            commandToRun = "25";
        }
        if(argMultimap.getValue(PREFIX_75th).isPresent()){
            commandToRun = "75";
        }
        if(argMultimap.getValue(PREFIX_HIGHEST).isPresent()){
            commandToRun = "H";
        }
        if(argMultimap.getValue(PREFIX_MEAN).isPresent()){
            commandToRun = "MEAN";
        }
        if(argMultimap.getValue(PREFIX_MEDIAN).isPresent()){
            commandToRun = "MEDIAN";
        }

        return new GradeSummaryCommand(commandToRun);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}