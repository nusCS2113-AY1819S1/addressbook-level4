package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_INDEX;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.AddCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.person.Address;
import com.t13g2.forum.model.person.Email;
import com.t13g2.forum.model.person.Name;
import com.t13g2.forum.model.person.Person;
import com.t13g2.forum.model.person.Phone;
import com.t13g2.forum.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_THREAD, PREFIX_INDEX, PREFIX_COMMENT, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_COMMENT, PREFIX_THREAD, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_MODULE).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_THREAD).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_INDEX).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_COMMENT).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, address, tagList);

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
