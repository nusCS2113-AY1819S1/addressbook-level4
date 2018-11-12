package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Department;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.Position;
import seedu.address.model.person.PositionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    private static final List<String> ACCEPTED_ORDERS = new ArrayList<>(Arrays.asList(FilterCommand.ASCENDING,
            FilterCommand.DESCENDING));
    private static final String DEPARTMENT_KEYWORD_VALIDATION_REGEX = "[A-Za-z ]{1,30}";
    private static final String POSITION_KEYWORD_VALIDATION_REGEX = "[A-Za-z ]{1,30}";
    private static final int INDEX_ONE = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        String sortOrder = trimmedArgs.split("\\s")[INDEX_ONE].toLowerCase();
        String[] departmentKeywords = new String[]{""};
        String[] positionKeywords = new String[]{""};
        FilterCommand filterCommand = new FilterCommand(new DepartmentContainsKeywordsPredicate(Arrays
                .asList(departmentKeywords)), new PositionContainsKeywordsPredicate(Arrays.asList(positionKeywords)),
                sortOrder);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DEPARTMENT, PREFIX_POSITION);

        if (trimmedArgs.isEmpty() || (!argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()
                && !argMultimap.getValue(PREFIX_POSITION).isPresent()) || !didPrefixesAppearOnlyOnce(trimmedArgs)
                || !ACCEPTED_ORDERS.contains(sortOrder)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
            filterCommand.setIsDepartmentPrefixPresent(false);
        } else if (argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()
                && !processDepartmentKeywords(argMultimap, filterCommand)) {
            throw new ParseException(Department.MESSAGE_DEPARTMENT_KEYWORD_CONSTRAINTS);
        }

        if (!argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            filterCommand.setIsPositionPrefixPresent(false);
        } else if (argMultimap.getValue(PREFIX_POSITION).isPresent()
                && !processPositionKeywords(argMultimap, filterCommand)) {
            throw new ParseException(Position.MESSAGE_POSITION_KEYWORD_CONSTRAINTS);
        }

        return filterCommand;
    }

    /**
     * Process the department keyword(s) from the user that are to be searched.
     * @param argMultimap The user input that has been tokenized based on the prefixes
     * @param command The FilterCommand to be returned for execution
     */
    public boolean processDepartmentKeywords(ArgumentMultimap argMultimap, FilterCommand command) {
        String trimmedDepartment = (argMultimap.getValue(PREFIX_DEPARTMENT).get().trim());
        String[] departmentKeywords = trimmedDepartment.split("\\s+");

        return validityCheckForDepartments(command, departmentKeywords);
    }

    /**
     * Validity check for department(s) and setting the department predicate of filter command.
     * @param command The FilterCommand to be returned for execution
     * @param keywords The user's input split by space
     */
    public boolean validityCheckForDepartments(FilterCommand command, String[] keywords) {
        if (!areDepartmentKeywordsValid(keywords)) {
            return false;
        }

        command.setIsDepartmentPrefixPresent(true);
        command.setDepartmentPredicate(new DepartmentContainsKeywordsPredicate(Arrays.asList(keywords)));
        return true;
    }

    /**
     * Checks whether given keyword(s) are valid department(s).
     * @param keywords The user input
     */
    public boolean areDepartmentKeywordsValid(String[] keywords) {
        for (String keyword: keywords) {
            if (!keyword.matches(DEPARTMENT_KEYWORD_VALIDATION_REGEX)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Process the position keyword(s) from the user that are to be searched.
     * @param argMultimap The user input that has been tokenized based on the prefixes
     * @param command The FilterCommand to be returned for execution
     */
    public boolean processPositionKeywords(ArgumentMultimap argMultimap, FilterCommand command) {
        String trimmedPosition = (argMultimap.getValue(PREFIX_POSITION).get().trim());
        String[] positionKeywords = trimmedPosition.split("\\s+");

        return validityCheckForPositions(command, positionKeywords);
    }

    /**
     * Validity check for position(s) and setting the position predicate of filter command.
     * @param command The FilterCommand to be returned for execution
     * @param keywords The user's input split by space
     */
    public boolean validityCheckForPositions(FilterCommand command, String[] keywords) {
        if (!arePositionKeywordsValid(keywords)) {
            return false;
        }

        command.setIsPositionPrefixPresent(true);
        command.setPositionPredicate(new PositionContainsKeywordsPredicate(Arrays.asList(keywords)));
        return true;
    }

    /**
     * Checks whether given keyword(s) are valid position(s).
     * @param keywords The user's input
     */
    public boolean arePositionKeywordsValid(String[] keywords) {
        for (String keyword: keywords) {
            if (!keyword.matches(POSITION_KEYWORD_VALIDATION_REGEX)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether department and position prefix appeared more than once within the argument.
     * @param argument The user's input
     */
    public boolean didPrefixesAppearOnlyOnce(String argument) {
        String departmentPrefix = " " + PREFIX_DEPARTMENT.toString();
        String positionPrefix = " " + PREFIX_POSITION.toString();

        return argument.indexOf(departmentPrefix) == argument.lastIndexOf(departmentPrefix)
                && argument.indexOf(positionPrefix) == argument.lastIndexOf(positionPrefix);
    }
}
