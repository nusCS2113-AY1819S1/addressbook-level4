package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Time;
import seedu.address.model.eventContacts.EventContacts;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    public AddEventCommandParser() throws ParseException {
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public AddEventCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_START, PREFIX_TIME, PREFIX_CONTACT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get());
        Date date = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_START).get());
        Time time = ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_TIME).get());
        Set<EventContacts> eventContactsList = ParserUtil.parseEventContacts(argMultimap.getAllValues(PREFIX_CONTACT));

        Event event = new Event(eventName, date, time, eventContactsList);

        return new AddEventCommand(event);

    }
}
