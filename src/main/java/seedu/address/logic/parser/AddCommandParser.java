package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITYLEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORKINGRATE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.CheckedInTime;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Mode;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.WorkingRate;
import seedu.address.model.person.password.Password;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_DEPARTMENT, PREFIX_ADDRESS, PREFIX_WORKINGRATE, PREFIX_TAG, PREFIX_PASSWORD,
                        PREFIX_PRIORITYLEVEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_NRIC, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_DEPARTMENT, PREFIX_WORKINGRATE, PREFIX_PASSWORD) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Department department = ParserUtil.parseDepartment(argMultimap.getValue(PREFIX_DEPARTMENT).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Mode mode = new Mode("out");
        // add command does not allow adding modes straight away
        WorkingRate workingRate = ParserUtil.parseWorkingRate(argMultimap.getValue(PREFIX_WORKINGRATE).get());
        CheckedInTime checkedInTime = new CheckedInTime("");
        // add command does not allow adding checked in time straight away
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());

        /**
         * Assign the lowest priority level as default if the prefix is missing.
         */
        PriorityLevel priorityLevel;
        if (!arePrefixesPresent(argMultimap, PREFIX_PRIORITYLEVEL)) {
            priorityLevel = new PriorityLevel(PriorityLevelEnum.BASIC.getPriorityLevelCode());
        } else {
            priorityLevel = ParserUtil.parsePriorityLevel(argMultimap.getValue(PREFIX_PRIORITYLEVEL).get());
        }

        Person person = new Person(name, nric, password, phone, email,
            department, priorityLevel, address, mode, workingRate, checkedInTime, tagList, null);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
