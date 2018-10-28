package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GradeGraphCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradeGraphCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;

/**
 * Parses input arguments and creates a new GradeGraphCommand object
 */
public class GradeGraphCommandParser implements Parser<GradeGraphCommand> {

    /**
     * Parses the given {@code String args} of arguments in the context of the GradeGraphCommand
     * and returns a GradeGraphCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradeGraphCommand parse(String args) throws ParseException {
        GradesManager gradebookManager = new GradesManager();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        String gradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();



        Grades grade = new Grades(
                moduleCodeArg.replaceAll("\\s+", " "),
                gradeComponentNameArg.replaceAll("\\s+", " "));
        return new GradeGraphCommand(grade);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
