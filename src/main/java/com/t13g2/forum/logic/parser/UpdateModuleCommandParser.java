package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_ID;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_TITLE;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.UpdateModuleCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateModuleCommand object
 */
public class UpdateModuleCommandParser implements Parser<UpdateModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateModuleCommand
     * and returns an UpdateModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_ID, PREFIX_MODULE_CODE, PREFIX_MODULE_TITLE);

        if (!(arePrefixesPresent(argMultimap, PREFIX_MODULE_ID, PREFIX_MODULE_TITLE)
            || arePrefixesPresent(argMultimap, PREFIX_MODULE_ID, PREFIX_MODULE_CODE))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateModuleCommand.MESSAGE_USAGE));
        }

        int moduleId = ParserUtil.parseIntWithOverflow(argMultimap.getValue(PREFIX_MODULE_ID).get());
        String moduleCode = "";
        String moduleTitle = "";
        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            moduleCode = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        }
        if (argMultimap.getValue(PREFIX_MODULE_TITLE).isPresent()) {
            moduleTitle = ParserUtil.parseModuleTitle(argMultimap.getValue(PREFIX_MODULE_TITLE).get());
        }

        return new UpdateModuleCommand(moduleId, moduleCode, moduleTitle);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
