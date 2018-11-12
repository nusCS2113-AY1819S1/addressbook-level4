package seedu.address.logic;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.Role.getLegalCommands;

public class RoleTest {

    @Test
    public void getLegalCommandsTest() {
        Role roleOwner = new Role(Role.ROLE_ADMIN);
        ArrayList<String> legalCommands = getLegalCommands();
        String commandWord;
        boolean testPass = false;
        for (String cmd : legalCommands) {
            commandWord = AddCommand.COMMAND_WORD;
            if (cmd.equals(commandWord)) {
                testPass = true;
            }
        }
        assertTrue(testPass);
    }
}
