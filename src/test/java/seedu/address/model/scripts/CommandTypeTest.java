package seedu.address.model.scripts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.model.script.CommandType;
import seedu.address.testutil.Assert;

public class CommandTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CommandType(null));
    }

    @Test
    public void constructor_invalidCommandType_throwsIllegalArgumentException() {
        String invalidCommandType = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CommandType(invalidCommandType));
    }

    @Test
    public void isValidCommand() {
        // null command
        Assert.assertThrows(NullPointerException.class, () -> CommandType.isValidCommand(null));

        // blank command
        assertFalse(CommandType.isValidCommand("")); // empty string
        assertFalse(CommandType.isValidCommand(" ")); // spaces only

        // invalid commands
        assertFalse(CommandType.isValidCommand("abc"));
        assertFalse(CommandType.isValidCommand("efg"));
        assertFalse(CommandType.isValidCommand("@2123"));
        assertFalse(CommandType.isValidCommand("123"));
        assertFalse(CommandType.isValidCommand("novin"));

        // valid commands
        assertTrue(CommandType.isValidCommand("add"));
        assertTrue(CommandType.isValidCommand("a"));
        assertTrue(CommandType.isValidCommand("addgroup"));
        assertTrue(CommandType.isValidCommand("addgrp"));
        assertTrue(CommandType.isValidCommand("add_testmarks"));
        assertTrue(CommandType.isValidCommand("addt"));
        assertTrue(CommandType.isValidCommand("clear"));
        assertTrue(CommandType.isValidCommand("c"));
        assertTrue(CommandType.isValidCommand("group"));
        assertTrue(CommandType.isValidCommand("grp"));
        assertTrue(CommandType.isValidCommand("delete"));
        assertTrue(CommandType.isValidCommand("d"));
        assertTrue(CommandType.isValidCommand("distinto"));
        assertTrue(CommandType.isValidCommand("di"));
        assertTrue(CommandType.isValidCommand("edit"));
        assertTrue(CommandType.isValidCommand("e"));
        assertTrue(CommandType.isValidCommand("edit_test"));
        assertTrue(CommandType.isValidCommand("et"));
        assertTrue(CommandType.isValidCommand("sendmail"));
        assertTrue(CommandType.isValidCommand("sm"));
        assertTrue(CommandType.isValidCommand("exit"));
        assertTrue(CommandType.isValidCommand("ex"));
        assertTrue(CommandType.isValidCommand("find"));
        assertTrue(CommandType.isValidCommand("f"));
        assertTrue(CommandType.isValidCommand("genlist"));
        assertTrue(CommandType.isValidCommand("gl"));
        assertTrue(CommandType.isValidCommand("display"));
        assertTrue(CommandType.isValidCommand("disp"));
        assertTrue(CommandType.isValidCommand("help"));
        assertTrue(CommandType.isValidCommand("h"));
        assertTrue(CommandType.isValidCommand("history"));
        assertTrue(CommandType.isValidCommand("his"));
        assertTrue(CommandType.isValidCommand("list"));
        assertTrue(CommandType.isValidCommand("l"));
        assertTrue(CommandType.isValidCommand("listgroup"));
        assertTrue(CommandType.isValidCommand("lg"));
        assertTrue(CommandType.isValidCommand("redo"));
        assertTrue(CommandType.isValidCommand("r"));
        assertTrue(CommandType.isValidCommand("select"));
        assertTrue(CommandType.isValidCommand("s"));
        assertTrue(CommandType.isValidCommand("undo"));
        assertTrue(CommandType.isValidCommand("u"));
    }

    @Test
    public void test_hashcode() {
        Set<String> s1 = new HashSet<>(Arrays.asList("Hello", "World"));
        Set<String> s2 = new HashSet<>(Arrays.asList("World", "Hello"));
        assertEquals(s1, s2);
        assertTrue(s1.hashCode() == s2.hashCode());
    }
}
