package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ID_ACCOUNT1_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ID_ACCOUNT2_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERID;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERPASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERROLE;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_ACCOUNT1_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_ACCOUNT2_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_ACCOUNT1_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_ACCOUNT2_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_ACCOUNT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_ACCOUNT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_ACCOUNT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_ACCOUNT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ACCOUNT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ACCOUNT2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalAccounts.ACCOUNT1;
import static seedu.address.testutil.TypicalAccounts.ACCOUNT2;

import org.junit.Test;

import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserPassword;
import seedu.address.model.login.UserRole;
import seedu.address.testutil.AccountBuilder;

public class CreateAccountCommandParserTest {
    private CreateAccountCommandParser parser = new CreateAccountCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        LoginDetails expectedAccount = new AccountBuilder(ACCOUNT1).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ID_ACCOUNT1_DESC
                + PASSWORD_ACCOUNT1_DESC + ROLE_ACCOUNT1_DESC , new CreateAccountCommand(expectedAccount));

        // multiple user IDs - last user ID accepted
        assertParseSuccess(parser, ID_ACCOUNT1_DESC + ID_ACCOUNT2_DESC + PASSWORD_ACCOUNT1_DESC
                        + ROLE_ACCOUNT1_DESC , new CreateAccountCommand(expectedAccount));

        // multiple passwords - last password accepted
        assertParseSuccess(parser, ID_ACCOUNT1_DESC + PASSWORD_ACCOUNT1_DESC + PASSWORD_ACCOUNT2_DESC
                        + ROLE_ACCOUNT1_DESC, new CreateAccountCommand(expectedAccount));

        // multiple roles - last role accepted
        assertParseSuccess(parser, ID_ACCOUNT1_DESC + PASSWORD_ACCOUNT1_DESC + ROLE_ACCOUNT1_DESC
                         + ROLE_ACCOUNT2_DESC, new CreateAccountCommand(expectedAccount));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid user id
        assertParseFailure(parser, INVALID_USERID + VALID_PASSWORD_ACCOUNT1 + VALID_ROLE_ACCOUNT1,
                UserId.MESSAGE_USERID_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, VALID_ID_ACCOUNT1 + INVALID_USERPASSWORD + VALID_ROLE_ACCOUNT1,
                UserPassword.MESSAGE_USERPASSWORD_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, VALID_ID_ACCOUNT1 + VALID_PASSWORD_ACCOUNT1 + INVALID_USERROLE,
                UserRole.MESSAGE_USERROLE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_USERID + INVALID_USERPASSWORD + VALID_ROLE_ACCOUNT1,
                UserId.MESSAGE_USERID_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_ID_ACCOUNT1 + VALID_PASSWORD_ACCOUNT1
                        + VALID_ROLE_ACCOUNT1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE));
    }
}
