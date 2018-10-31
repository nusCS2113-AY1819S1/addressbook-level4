package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.LoginManager;
import seedu.address.logic.commands.LoginUserIdPasswordRoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.login.UserRole;

public class LoginUserIdPasswordRoleCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LoginUserIdPasswordRoleCommandParser parser = new LoginUserIdPasswordRoleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LoginUserIdPasswordRoleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArgs_throwsParseException() {
        assertParseFailure(parser, "A1234567M zaq1xsw2cde3 member member",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LoginUserIdPasswordRoleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertParseFailure(parser, "A1234567M zaq1xsw2cde3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        LoginUserIdPasswordRoleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_memberRoleArg_setRoleCondition() throws ParseException {
        String trimmedArgs = "A1234567M zaq1xsw2cde3 member".trim();
        String[] keywords = trimmedArgs.split("\\s+");
        List<String> keywordsList = new ArrayList<>(Arrays.asList(keywords));
        parser.setRoleReturnLoginCommandObject(keywords, keywordsList);
        assertTrue(LoginManager.getIsMember());
        assertFalse(LoginManager.getIsPresident());
        assertFalse(LoginManager.getIsTreasurer());
    }

    @Test
    public void parse_treasurerRoleArg_setRoleCondition() throws ParseException {
        String trimmedArgs = "A1234567M zaq1xsw2cde3 treasurer".trim();
        String[] keywords = trimmedArgs.split("\\s+");
        List<String> keywordsList = new ArrayList<>(Arrays.asList(keywords));
        parser.setRoleReturnLoginCommandObject(keywords, keywordsList);
        assertTrue(LoginManager.getIsTreasurer());
        assertFalse(LoginManager.getIsPresident());
        assertFalse(LoginManager.getIsMember());
    }

    @Test
    public void parse_presidentRoleArg_setRoleCondition() throws ParseException {
        String trimmedArgs = "A1234567M zaq1xsw2cde3 president".trim();
        String[] keywords = trimmedArgs.split("\\s+");
        List<String> keywordsList = new ArrayList<>(Arrays.asList(keywords));
        parser.setRoleReturnLoginCommandObject(keywords, keywordsList);
        assertTrue(LoginManager.getIsPresident());
        assertFalse(LoginManager.getIsMember());
        assertFalse(LoginManager.getIsTreasurer());
    }

    @Test
    public void parse_nonExistentRoleArg_throwParseException() throws ParseException {
        String trimmedArgs = "A1234567M zaq1xsw2cde3 janitor".trim();
        String[] keywords = trimmedArgs.split("\\s+");
        List<String> keywordsList = new ArrayList<>(Arrays.asList(keywords));
        thrown.expect(ParseException.class);
        parser.setRoleReturnLoginCommandObject(keywords, keywordsList);
        assertParseFailure(parser, "A1234567M zaq1xsw2cde3 janitor", UserRole.MESSAGE_USERROLE_CONSTRAINTS);
    }
}
