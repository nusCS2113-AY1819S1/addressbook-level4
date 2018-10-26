package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // multiple whitespaces between keywords
        assertParseFailure(
                parser,
                " " + PREFIX_NAME + "Alice \n Bob  \t",
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        String[] names = {"Alice", "Bob"};

        Map<Prefix, String[]> prefixKeywordsMap = new HashMap<>();
        prefixKeywordsMap.put(PREFIX_NAME, names);
        Set<Prefix> keys = prefixKeywordsMap.keySet();

         FindCommand expectedFindCommand =
                new FindCommand(prefixKeywordsMap, keys.toArray(new Prefix[0]));

         String userInput = " " + PREFIX_NAME + "Bob Alice";

         assertParseSuccess(parser,  userInput, expectedFindCommand);
    }

    //@@author lws803
    @Test
    public void parse_validMultipleArgs_returnsFindCommand () {
        Person Alice = TypicalPersons.ALICE;
        String[] names = {"Alice", "Bob"};
        String[] emails = {Alice.getEmail().value};
        String[] addresses = {Alice.getAddress().value};
        String[] notes = {Alice.getNote().value};
        String[] tags = {Alice.getStringTags()};
        String[] phones = {Alice.getPhone().value};
        // Refine search result to Alice

        Map<Prefix, String[]> prefixKeywordsMap = new HashMap<>();
        prefixKeywordsMap.put(PREFIX_NAME, names);
        prefixKeywordsMap.put(PREFIX_EMAIL, emails);
        prefixKeywordsMap.put(PREFIX_TAG, tags);
        prefixKeywordsMap.put(PREFIX_ADDRESS, addresses);
        prefixKeywordsMap.put(PREFIX_NOTE, notes);
        prefixKeywordsMap.put(PREFIX_PHONE, phones);

        Set<Prefix> keys = prefixKeywordsMap.keySet();

        FindCommand expectedFindCommand =
                new FindCommand(prefixKeywordsMap, keys.toArray(new Prefix[0]));

        String userInput = " " +
                PREFIX_NAME +
                "Bob Alice " +
                PREFIX_EMAIL +
                Alice.getEmail().value +
                " " +
                PREFIX_PHONE +
                Alice.getPhone().value +
                " " +
                PREFIX_ADDRESS +
                Alice.getAddress().value +
                " " +
                PREFIX_NOTE +
                Alice.getNote().value +
                " " +
                PREFIX_TAG +
                Alice.getStringTags();

        assertParseSuccess(parser,  userInput, expectedFindCommand);

    }
    //@@author

}
