package seedu.address.model.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seedu.address.testutil.Assert;

public class ModuleNameTest {

    @Test
    public void constructor_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ModuleName(null));
    }

    @Test
    public void constructor_invalidModuleName_throwsIllegalArgumentException() {
        String invalidModuleName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ModuleName(invalidModuleName));
    }

    @Test
    public void isValidModuleName() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> ModuleName.isValidModuleName(null));

        // invalid module names
        assertFalse(ModuleName.isValidModuleName(""));  // empty string
        assertFalse(ModuleName.isValidModuleName(" "));  // spaces only
        assertFalse(ModuleName.isValidModuleName(" Software"));  // starting with a space
        assertFalse(ModuleName.isValidModuleName("^"));  // invalid symbols only
        assertFalse(ModuleName.isValidModuleName(" Software+")); // contains invalid symbols
        assertFalse(ModuleName.isValidModuleName("&Software"));  // accepted symbol as the first char

        // valid module names
        assertTrue(ModuleName.isValidModuleName("Asking Questions"));  // letters only
        assertTrue(ModuleName.isValidModuleName("Physics 2"));  // alphanumeric characters
        assertTrue(ModuleName.isValidModuleName("12345"));  // numbers only
        assertTrue(ModuleName.isValidModuleName("Software & OOP"));  // contains ampersand (&)
        assertTrue(ModuleName.isValidModuleName("Object-oriented"));  // contains hyphen (-)
        assertTrue(ModuleName.isValidModuleName("linear algebra"));  // all lowercase
        assertTrue(ModuleName.isValidModuleName("Software Engineering & Object-Oriented Programming"));  // long
    }

    @Test
    public void toString_outputsModuleNameAsString() {
        final String validModuleName = "Quantitative Reasoning";
        final ModuleName validModuleNameObj = new ModuleName(validModuleName);

        assertEquals(validModuleNameObj.toString(), validModuleName);
    }

    @Test
    public void equals() {
        String askingQuestions = "Asking Questions";
        String linearAlgebra = "Linear Algebra";
        ModuleName askingQuestionsName = new ModuleName(askingQuestions);
        ModuleName linearAlgebraName = new ModuleName(linearAlgebra);

        // same object -> returns true
        assertTrue(askingQuestionsName.equals(askingQuestionsName));

        // same module code -> returns true
        ModuleName askingQuestionsNameCopy = new ModuleName(askingQuestions);
        assertTrue(askingQuestionsName.equals(askingQuestionsNameCopy));

        // different types -> returns false
        assertFalse(askingQuestionsName.equals(1));

        // null -> returns false
        assertFalse(askingQuestionsName.equals(null));

        // different module codes -> returns false
        assertFalse(askingQuestionsName.equals(linearAlgebraName));
    }
}
