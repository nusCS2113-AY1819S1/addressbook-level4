package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringSimilarityTest {
    private StringSimilarity stringsimilarity = new StringSimilarity();

    @Test
    public void editDistance() {

        // "test" -> "test", has edit distance of 0
        assertEquals(stringsimilarity.editDistance("test", "test"), 0);

        // "" -> "test", has edit distance of 4
        assertEquals(stringsimilarity.editDistance("", "test"), 4);

        // "TEST" -> "test", has edit distance of 4
        assertEquals(stringsimilarity.editDistance("TEST", "test"), 4);

        // "tes" -> "test", has edit distance of 1
        assertEquals(stringsimilarity.editDistance("tes", "test"), 1);

        // "abcdef" -> "test", has edit distance of 6
        assertEquals(stringsimilarity.editDistance("abcdef", "test"), 6);
    }
}
