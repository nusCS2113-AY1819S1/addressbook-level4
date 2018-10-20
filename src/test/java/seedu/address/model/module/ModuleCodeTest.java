package seedu.address.model.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seedu.address.testutil.Assert;

public class ModuleCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidModuleCode = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidModuleCode));
    }

    @Test
    public void isValidModuleCode() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid module codes
        assertFalse(ModuleCode.isValidModuleCode(""));  // empty string
        assertFalse(ModuleCode.isValidModuleCode("  "));  // whitespace only
        assertFalse(ModuleCode.isValidModuleCode("cs2113"));  // lowercase in front
        assertFalse(ModuleCode.isValidModuleCode("CS2113t"));  // lowercase behind
        assertFalse(ModuleCode.isValidModuleCode("CS2ii3"));  // letters instead of numbers
        assertFalse(ModuleCode.isValidModuleCode("CS01134"));  // first digit is 0
        assertFalse(ModuleCode.isValidModuleCode(" CS2113"));  // extra space in front
        assertFalse(ModuleCode.isValidModuleCode("CS2113 "));  // extra space at the end
        assertFalse(ModuleCode.isValidModuleCode("iloveCS2113"));  // contains extra characters in front
        assertFalse(ModuleCode.isValidModuleCode("CS21134life"));  // contains extra characters behind

        // valid module codes
        assertTrue(ModuleCode.isValidModuleCode("CS2113"));  // module code with 2 letters and no optional char
        assertTrue(ModuleCode.isValidModuleCode("CS2113T"));  // optional character at the end
        assertTrue(ModuleCode.isValidModuleCode("GEQ1000"));  // 3-letter module code
        assertTrue(ModuleCode.isValidModuleCode("GEQ1000X"));  // 3-letter code with optional character
    }

    @Test
    public void toString_outputsModuleCodeInString() {
        final String validModuleCode = "CS2113";
        final ModuleCode validModuleCodeObj = new ModuleCode(validModuleCode);

        assertEquals(validModuleCodeObj.toString(), validModuleCode);
    }

    @Test
    public void equals() {
        String codeCs2113 = "CS2113";
        String codeGeq1000 = "GEQ1000";
        ModuleCode cs2113 = new ModuleCode(codeCs2113);
        ModuleCode geq1000 = new ModuleCode(codeGeq1000);

        // same object -> returns true
        assertTrue(cs2113.equals(cs2113));

        // same module code -> returns true
        ModuleCode cs2113Copy = new ModuleCode(codeCs2113);
        assertTrue(cs2113.equals(cs2113Copy));

        // different types -> returns false
        assertFalse(cs2113.equals(1));

        // null -> returns false
        assertFalse(cs2113.equals(null));

        // different module codes -> returns false
        assertFalse(cs2113.equals(geq1000));
    }
}
