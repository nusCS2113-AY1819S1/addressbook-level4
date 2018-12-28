package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ADDINFO_DESC_AMY;

import org.junit.Test;

import seedu.address.logic.commands.AddInfoCommand.AddInfoPersonDescriptor;

public class AddInfoPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        AddInfoPersonDescriptor descriptorWithSameValue = new AddInfoPersonDescriptor(ADDINFO_DESC_AMY);
        assertTrue(ADDINFO_DESC_AMY.equals(descriptorWithSameValue));

        // same object -> returns true
        assertTrue(ADDINFO_DESC_AMY.equals(ADDINFO_DESC_AMY));
    }
}
