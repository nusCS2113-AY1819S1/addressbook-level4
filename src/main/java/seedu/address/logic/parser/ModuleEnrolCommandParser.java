package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ModuleEnrolCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new ModuleEnrolCommand object.
 */
public class ModuleEnrolCommandParser implements Parser<ModuleEnrolCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleEnrolCommand
     * and returns an ModuleEnrolCommand object for execution.
     * @param args represents the new module details from the user's input
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ModuleEnrolCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_MATRIC);

        // Module code and at least 1 matric no. must be present
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_MATRIC)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleEnrolCommand.MESSAGE_USAGE));
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        String studentMatricNo = argMultimap.getValue(PREFIX_MATRIC).get();

        return new ModuleEnrolCommand(moduleCode, studentMatricNo);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
