package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.ModuleEditCommand;
import seedu.address.logic.commands.ModuleEditCommand.EditModuleDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ModuleEditCommandParser implements Parser<ModuleEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleEditCommand
     * and returns an ModuleEditCommand object for execution.
     * @param args represents the new module details from the user's input
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ModuleEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_MODULE_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleEditCommand.MESSAGE_USAGE));
        }

        String moduleCode = argMultimap.getValue(PREFIX_MODULE_CODE).get();

        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();
        editModuleDescriptor.setModuleCode(ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get()));
        if (argMultimap.getValue(PREFIX_MODULE_NAME).isPresent()) {
            editModuleDescriptor.setModuleName(
                    ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_MODULE_NAME).get()));
        }

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ModuleEditCommand.MESSAGE_NOT_EDITED);
        }

        return new ModuleEditCommand(moduleCode, editModuleDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
