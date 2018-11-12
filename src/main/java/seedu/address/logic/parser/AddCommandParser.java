package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEOFBIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEEID, PREFIX_NAME, PREFIX_DATEOFBIRTH, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_DEPARTMENT, PREFIX_POSITION, PREFIX_ADDRESS, PREFIX_SALARY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_EMPLOYEEID, PREFIX_NAME, PREFIX_DATEOFBIRTH, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_DEPARTMENT, PREFIX_POSITION, PREFIX_ADDRESS, PREFIX_SALARY)
                || !argMultimap.getPreamble().isEmpty() || !didPrefixesAppearOnlyOnce(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
;
        EmployeeId employeeId = ParserUtil.parseEmployeeId(argMultimap.getValue(PREFIX_EMPLOYEEID).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        DateOfBirth dateOfBirth = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DATEOFBIRTH).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Department department = ParserUtil.parseDepartment(argMultimap.getValue(PREFIX_DEPARTMENT).get());
        Position position = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Salary salary = ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get());

        if (!checkSalaryFormat(salary.toString())) {
            throw new ParseException(Salary.MESSAGE_SALARY_CONSTRAINTS);
        }

        Bonus bonus = new Bonus("0.0");
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(employeeId, name, dateOfBirth, phone, email, department, position, address,
                salary, bonus, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Check whether the input salary from the user conforms to the required format
     * @param salary The user's input after salary prefix.
     */
    private static boolean checkSalaryFormat(String salary) {
        return Pattern.matches("[0-9.]+", salary);
    }

    /**
     * Check whether prefixes except tag's prefix appeared more than once within the argument.
     * @param argument The user's input
     */
    public boolean didPrefixesAppearOnlyOnce(String argument) {
        String employeeIdPrefix = " " + PREFIX_EMPLOYEEID.toString();
        String namePrefix = " " + PREFIX_NAME.toString();
        String dateOfBirthPrefix = " " + PREFIX_DATEOFBIRTH.toString();
        String phonePrefix = " " + PREFIX_PHONE.toString();
        String emailPrefix = " " + PREFIX_EMAIL.toString();
        String departmentPrefix = " " + PREFIX_DEPARTMENT.toString();
        String positionPrefix = " " + PREFIX_POSITION.toString();
        String addressPrefix = " " + PREFIX_ADDRESS.toString();
        String salaryPrefix = " " + PREFIX_SALARY.toString();

        return argument.indexOf(employeeIdPrefix) == argument.lastIndexOf(employeeIdPrefix)
                && argument.indexOf(namePrefix) == argument.lastIndexOf(namePrefix)
                && argument.indexOf(dateOfBirthPrefix) == argument.lastIndexOf(dateOfBirthPrefix)
                && argument.indexOf(phonePrefix) == argument.lastIndexOf(phonePrefix)
                && argument.indexOf(emailPrefix) == argument.lastIndexOf(emailPrefix)
                && argument.indexOf(departmentPrefix) == argument.lastIndexOf(departmentPrefix)
                && argument.indexOf(positionPrefix) == argument.lastIndexOf(positionPrefix)
                && argument.indexOf(addressPrefix) == argument.lastIndexOf(addressPrefix)
                && argument.indexOf(salaryPrefix) == argument.lastIndexOf(salaryPrefix);
    }
}
