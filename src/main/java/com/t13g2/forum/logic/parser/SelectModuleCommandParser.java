package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.SelectModuleCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectModuleCommand object
 */
public class SelectModuleCommandParser implements Parser<SelectModuleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectModuleCommand
     * and returns an SelectModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                SelectModuleCommand.MESSAGE_USAGE));
        }
        String moduleCode = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        return new SelectModuleCommand(moduleCode);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
