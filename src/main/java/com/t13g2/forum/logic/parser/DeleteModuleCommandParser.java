package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.DeleteModuleCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

//@@author xllx1
/**
 * Parses input arguments and deletes a module from forum book.
 */
public class DeleteModuleCommandParser implements Parser<DeleteModuleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteModuleCommand
     * and returns an DeleteModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteModuleCommand.MESSAGE_USAGE));
        }

        String moduleCode = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE_CODE).get());

        return new DeleteModuleCommand(moduleCode);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
