package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.commands.BackupCommand;

//@@author QzSG
public class BackupCommandParserTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private BackupCommandParser parser = new BackupCommandParser();

    @Test
    public void parse_emptyArg_parsesPasses() {
        BackupCommand expectedBackupCommand =
                new BackupCommand(Optional.empty(), true, Optional.empty(), Optional.empty());

        assertParseSuccess(parser, "  ", expectedBackupCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        Path tempBackupFilePath = testFolder.getRoot().toPath().resolve("Temp.bak");

        BackupCommand expectedBackupCommand =
                new BackupCommand(Optional.ofNullable(tempBackupFilePath), true, Optional.empty(), Optional.empty());
        assertParseSuccess(parser, tempBackupFilePath.toString(), expectedBackupCommand);
    }
}
