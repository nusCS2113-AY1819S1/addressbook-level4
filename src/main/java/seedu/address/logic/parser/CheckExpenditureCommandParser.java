package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.CheckExpenditureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenditureinfo.Date;

public class CheckExpenditureCommandParser implements Parser<CheckExpenditureCommand> {
        /**
         * Parses the given {@code String} of arguments in the context of the AddCommand
         * and returns an AddCommand object for execution.
         * @throws ParseException if the user input does not conform the expected format
         */
    public CheckExpenditureCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CheckExpenditureCommand.MESSAGE_USAGE));
            }
        Date date1 = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Date date2 = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());


        return new CheckExpenditureCommand(date1, date2);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}



