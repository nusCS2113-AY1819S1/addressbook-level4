package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.t13g2.forum.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static com.t13g2.forum.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static com.t13g2.forum.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static com.t13g2.forum.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static com.t13g2.forum.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static com.t13g2.forum.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static com.t13g2.forum.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static com.t13g2.forum.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static com.t13g2.forum.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static com.t13g2.forum.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static com.t13g2.forum.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static com.t13g2.forum.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static com.t13g2.forum.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import com.t13g2.forum.logic.commands.AddCommand;
import com.t13g2.forum.model.person.Address;
import com.t13g2.forum.model.person.Email;
import com.t13g2.forum.model.person.Name;
import com.t13g2.forum.model.person.Person;
import com.t13g2.forum.model.person.Phone;
import com.t13g2.forum.model.tag.Tag;
import com.t13g2.forum.testutil.PersonBuilder;
import com.t13g2.forum.testutil.TypicalPersons;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(TypicalPersons.BOB)
            .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, PREAMBLE_WHITESPACE
            + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_AMY
            + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BOB
            + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(TypicalPersons.BOB)
            .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
            new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(TypicalPersons.AMY).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB
                + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB
                + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_BOB
                + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME_DESC
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB
            + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB
            + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
            Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME_DESC
                + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, PREAMBLE_NON_EMPTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
