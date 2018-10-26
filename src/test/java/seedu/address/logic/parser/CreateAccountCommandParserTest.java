package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ID_ACCOUNT1_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERID;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERROLE;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_ACCOUNT1_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_ACCOUNT1_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalAccounts.ACCOUNT1;

import org.junit.Test;

import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
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
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid user id
        assertParseFailure(parser, INVALID_USERID + PASSWORD_ACCOUNT1_DESC + ROLE_ACCOUNT1_DESC,
                UserId.MESSAGE_USERID_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, ID_ACCOUNT1_DESC + PASSWORD_ACCOUNT1_DESC + INVALID_USERROLE,
                UserRole.MESSAGE_USERROLE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_USERID + PASSWORD_ACCOUNT1_DESC + INVALID_USERROLE,
                UserId.MESSAGE_USERID_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ID_ACCOUNT1_DESC + PASSWORD_ACCOUNT1_DESC
                        + ROLE_ACCOUNT1_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE));
    }
}
