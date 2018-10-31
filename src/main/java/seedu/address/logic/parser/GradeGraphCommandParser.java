package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GradeGraphCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradeGraphCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;
import seedu.address.model.module.ModuleManager;

/**
 * Parses input arguments and creates a new GradeGraphCommand object
 */
public class GradeGraphCommandParser implements Parser<GradeGraphCommand> {
    public static final String MESSAGE_EMPTY_INPUTS = "Module code and gradebook component name cannot be empty";
    public static final String MESSAGE_EMPTY_MODULE_CODE = "Module code cannot be empty";
    public static final String MESSAGE_EMPTY_COMPONENT_NAME = "Component name cannot be empty";
    public static final String MESSAGE_MODULE_CODE_INVALID = "Module code does not exist";
    public static final String MESSAGE_GRADEBOOK_INVALID = "Gradebook component does not exist";
    public static final String MESSAGE_GRADE_COMPONENT_INCOMPLETE = "Not all marks of students enrolled in module are "
            + "keyed in";

    /**
     * Parses the given {@code String args} of arguments in the context of the GradeGraphCommand
     * and returns a GradeGraphCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradeGraphCommand parse(String args) throws ParseException {
        GradebookManager gradebookManager = new GradebookManager();
        GradesManager gradesManager = new GradesManager();
        ModuleManager moduleManager = ModuleManager.getInstance();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        String gradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();
        boolean isModuleCodeAndComponentNameEmpty = gradebookManager.isModuleCodeAndComponentNameEmpty(
                moduleCodeArg,
                gradeComponentNameArg);
        if (isModuleCodeAndComponentNameEmpty) {
            throw new ParseException(MESSAGE_EMPTY_INPUTS);
        }
        boolean isModuleCodeEmpty = gradebookManager.isModuleCodeEmpty(moduleCodeArg);
        if (isModuleCodeEmpty) {
            throw new ParseException(MESSAGE_EMPTY_MODULE_CODE);
        }
        boolean isComponentNameEmpty = gradebookManager.isComponentNameEmpty(gradeComponentNameArg);
        if (isComponentNameEmpty) {
            throw new ParseException(MESSAGE_EMPTY_COMPONENT_NAME);
        }
        boolean doesModuleExist = moduleManager.doesModuleExist(moduleCodeArg);
        if (!doesModuleExist) {
            throw new ParseException(MESSAGE_MODULE_CODE_INVALID);
        }
        boolean isGradeComponentValid = gradebookManager.isGradeComponentValid(moduleCodeArg, gradeComponentNameArg);
        if (!isGradeComponentValid) {
            throw new ParseException(MESSAGE_GRADEBOOK_INVALID);
        }
        boolean isGradesComplete = gradesManager.isGradesComplete(moduleCodeArg, gradeComponentNameArg);
        if (!isGradesComplete) {
            throw new ParseException(MESSAGE_GRADE_COMPONENT_INCOMPLETE);
        }

        Grades grade = new Grades(
                moduleCodeArg,
                gradeComponentNameArg);
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
