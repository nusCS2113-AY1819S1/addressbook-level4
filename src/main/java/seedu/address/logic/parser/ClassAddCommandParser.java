package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAXENROLLMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ClassAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.Enrollment;
import seedu.address.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new ClassAddCommand object
 */
public class ClassAddCommandParser implements Parser<ClassAddCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ClassAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASSNAME, PREFIX_MODULE_CODE, PREFIX_MAXENROLLMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASSNAME, PREFIX_MODULE_CODE, PREFIX_MAXENROLLMENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassAddCommand.MESSAGE_USAGE));
        }

        String className = argMultimap.getValue(PREFIX_CLASSNAME).get().toUpperCase();
        ClassroomParserUtil.parseClassName(className);
        String moduleCode = argMultimap.getValue(PREFIX_MODULE_CODE).get().toUpperCase();
        ParserUtil.parseModuleCode(moduleCode);
        String maxEnrollment = argMultimap.getValue(PREFIX_MAXENROLLMENT).get();
        ClassroomParserUtil.parseEnrollment(maxEnrollment);

        Classroom classRoom = new Classroom(
                new ClassName(className),
                new ModuleCode(moduleCode),
                new Enrollment(maxEnrollment));
        return new ClassAddCommand(classRoom);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
