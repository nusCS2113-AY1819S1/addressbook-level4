package seedu.address.model.searchhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class KeywordsBundleTest {
    @Test
    public void equals() {
        KeywordsBundle firstBundle = new KeywordsBundle(KeywordType.IncludeNames, new ArrayList<>());
        KeywordsBundle secondBundle = new KeywordsBundle(KeywordType.IncludeNames, Collections.singletonList("Name"));
        KeywordsBundle thirdBundle = new KeywordsBundle(KeywordType.IncludeTags, Collections.singletonList("Tag"));
        KeywordsBundle fourthBundle = new KeywordsBundle(KeywordType.IncludeNames, Collections.singletonList("Name"));
        // same object -> returns true
        assertTrue(firstBundle.equals(firstBundle));
        assertTrue(secondBundle.equals(secondBundle));

        // different types -> returns false
        assertFalse(firstBundle.equals(1));

        // null -> returns false
        assertFalse(firstBundle.equals(null));

        //different list same type-> return false
        assertFalse(secondBundle.equals(firstBundle));

        //different type same list-> return false
        assertFalse(secondBundle.equals(thirdBundle));

        // same type same list -> returns true
        assertTrue(secondBundle.equals(fourthBundle));
    }
}
