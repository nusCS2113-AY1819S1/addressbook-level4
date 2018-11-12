package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetDepartmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Department;

//@@author Woonhian
/**
 * Parses the given {@code String} of arguments in the context of the SetDepartmentCommand
 * and returns a SetDepartmentCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class SetDepartmentCommandParser implements Parser<SetDepartmentCommand> {

    @Override
    public SetDepartmentCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(userInput, PREFIX_DEPARTMENT);

        if (!arePrefixesPresent(argMultiMap, PREFIX_DEPARTMENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetDepartmentCommand.MESSAGE_USAGE));
        }
        if (!didPrefixAppearOnlyOnce(userInput, PREFIX_DEPARTMENT.toString())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDepartmentCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetDepartmentCommand.MESSAGE_USAGE), pe);
        }

        Department department = ParserUtil.parseDepartment(argMultiMap.getValue(PREFIX_DEPARTMENT).get());
        return new SetDepartmentCommand(index, department);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks whether prefixes appeared more than once within the argument
     */
    public boolean didPrefixAppearOnlyOnce(String argument, String prefix) {
        String precedeWhitespacePrefix = " " + prefix;
        return argument.indexOf(precedeWhitespacePrefix) == argument.lastIndexOf(precedeWhitespacePrefix);
    }
}
