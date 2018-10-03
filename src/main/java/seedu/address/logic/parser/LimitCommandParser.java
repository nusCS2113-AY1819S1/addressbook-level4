package seedu.address.logic.parser;


import seedu.address.logic.commands.exceptions.LimitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.Date;
import seedu.address.model.record.Limit;
import seedu.address.model.record.Money;


import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;

public class LimitCommandParser  implements Parser<LimitCommand> {
    /**
     * Parses the information required for the limit command.
     * and returns a limit object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public LimitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_DATE,PREFIX_MONEY );

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)||
                 !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LimitCommand.MESSAGE_USAGE));
        }

        Money money =  ParserUtil.parseMoney(argMultimap.getValue(PREFIX_MONEY).get());
        Date date1 = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Date date2 =ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Limit limit = new Limit(date1,date2,money);


        return new LimitCommand(limit);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
