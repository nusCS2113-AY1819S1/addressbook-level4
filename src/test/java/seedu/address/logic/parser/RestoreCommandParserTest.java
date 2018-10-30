package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BACKUP_SERVICE_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.commands.RestoreCommand;
import seedu.address.storage.OnlineStorage;

//@@author QzSG
public class RestoreCommandParserTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private RestoreCommandParser parser = new RestoreCommandParser();

    private RestoreCommand expectedValidOnlineRestoreCommand =
            new RestoreCommand(Optional.empty(), false,
                    Optional.ofNullable(OnlineStorage.Type.GITHUB),
                    Optional.empty());


    @Test
    public void parse_emptyArg_parsesPasses() {
        RestoreCommand expectedBackupCommand =
                new RestoreCommand(Optional.empty(), true, Optional.empty(), Optional.empty());

        assertParseSuccess(parser, "  ", expectedBackupCommand);
    }

    @Test
    public void parse_supportedOnlineBackupService_parsesSuccess() {
        assertParseSuccess(parser, " github", expectedValidOnlineRestoreCommand);
    }

    @Test
    public void parse_unsupportedOnlineBackupService_parsesSuccess() {
        String expectedMessage = String.format(MESSAGE_INVALID_BACKUP_SERVICE_FORMAT,
                RestoreCommand.MESSAGE_SHOW_SUPPORTED);
        assertParseFailure(parser, " owncloud", expectedMessage);
    }

    @Test
    public void parse_githubBackupNoTokenArg_parsesSuccess() {
        assertParseSuccess(parser, " github", expectedValidOnlineRestoreCommand);
    }

    @Test
    public void parse_githubBackupHasTokenArg_parsesFails() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " github AUTH_TOKEN", expectedMessage);
    }

    @Test
    public void parse_invalidArgs_parsesFails() {
        String expectedMessage = String.format(MESSAGE_INVALID_BACKUP_SERVICE_FORMAT,
                RestoreCommand.MESSAGE_SHOW_SUPPORTED);

        Path tempRestoreFilePath = testFolder.getRoot().toPath().resolve("Temp.bak");
        // invalid arguments
        assertParseFailure(parser, tempRestoreFilePath.toString(), expectedMessage);

    }
}
