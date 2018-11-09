package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.events.security.GetAuthenticationEvent;
import seedu.address.commons.events.security.GetAuthenticationReplyEvent;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Friend;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TimeTable;
import seedu.address.model.tag.Tag;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Parses input arguments and creates a new RegisterCommand object
 */
public class RegisterCommandParser extends ParserClass implements Parser<RegisterCommand> {

    private boolean isAuthenticated;

    /**
     * Parses the given {@code String} of arguments in the context of the RegisterCommand
     * and returns an RegisterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RegisterCommand parse(String args) throws ParseException, SecurityAuthenticationException {
        raise(new GetAuthenticationEvent());
        if (isAuthenticated) {
            throw new SecurityAuthenticationException("Please logout if you would like register a new account");
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_USERNAME).get());
        String password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).orElse(""));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(""));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(""));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        TimeTable timeTable = new TimeTable();
        Set<Friend> friendList = new HashSet<Friend>();

        Person person = new Person(name, phone, email, address, tagList, timeTable, friendList);

        return new RegisterCommand(person, password);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Subscribe
    public void handleGetAuthenticationReplyEvent(GetAuthenticationReplyEvent e) {
        isAuthenticated = e.isAuthenticated();
    }

}
