package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAXENROLLMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ClassEditCommand;
import seedu.address.logic.commands.ClassEditCommand.EditClassDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClassEditCommand object
 */
public class ClassEditCommandParser implements Parser<ClassEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClassEditCommand
     * and returns an ClassEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClassEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASSNAME, PREFIX_MODULE_CODE, PREFIX_MAXENROLLMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASSNAME, PREFIX_MODULE_CODE, PREFIX_MAXENROLLMENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassEditCommand.MESSAGE_USAGE));
        }

        String className = argMultimap.getValue(PREFIX_CLASSNAME).get().toUpperCase();
        String moduleCode = argMultimap.getValue(PREFIX_MODULE_CODE).get().toUpperCase();
        String maxEnrollment = argMultimap.getValue(PREFIX_MAXENROLLMENT).get();

        EditClassDescriptor editClassroomDescriptor = new EditClassDescriptor();
        if (argMultimap.getValue(PREFIX_CLASSNAME).isPresent()) {
            editClassroomDescriptor.setClassName(ClassroomParserUtil.parseClassName(className));
        }
        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            editClassroomDescriptor.setModuleCode(ParserUtil.parseModuleCode(moduleCode));
        }
        if (argMultimap.getValue(PREFIX_MAXENROLLMENT).isPresent()) {
            editClassroomDescriptor.setEnrollment(ClassroomParserUtil.parseEnrollment(maxEnrollment));
        }

        if (!editClassroomDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ClassEditCommand.MESSAGE_NOT_EDITED);
        }

        return new ClassEditCommand(className, moduleCode, editClassroomDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
