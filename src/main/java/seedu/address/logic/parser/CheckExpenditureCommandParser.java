package seedu.address.logic.parser;

import seedu.address.logic.commands.AddExpenditureCommand;
import seedu.address.logic.commands.CheckExpenditureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenditureinfo.Date;
import seedu.address.model.expenditureinfo.Expenditure;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;

public class CheckExpenditureCommandParser implements Parser<CheckExpenditureCommand>{
    /**
         * Parses the given {@code String} of arguments in the context of the AddCommand
         * and returns an AddCommand object for execution.
         * @throws ParseException if the user input does not conform the expected format
         */
        public CheckExpenditureCommand parse(String args) throws ParseException {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_DATE);


           Date date1 = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
           Date date2 = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());


            return new CheckExpenditureCommand(date1, date2);
        }



    }



