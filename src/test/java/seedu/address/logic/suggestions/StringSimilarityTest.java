package seedu.address.logic.suggestions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringSimilarityTest {
    private StringSimilarity stringsimilarity = new StringSimilarity();

    @Test
    public void editDistance() {

        // "add" -> "add", has edit distance of 0
        assertEquals(stringsimilarity.editDistance("add", "add"), 0);

        // "" -> "add", has edit distance of 3
        assertEquals(stringsimilarity.editDistance("", "add"), 3);

        // "ADD" -> "add", has edit distance of 3
        assertEquals(stringsimilarity.editDistance("ADD", "add"), 3);

        // "sched" -> "schedule", has edit distance of 3
        assertEquals(stringsimilarity.editDistance("sched", "schedule"), 3);

        // "lister" -> "list", has edit distance of 2
        assertEquals(stringsimilarity.editDistance("lister", "list"), 2);

        // "schadule" -> "schedule" has edit distance of 1
        assertEquals(stringsimilarity.editDistance("schadule", "schedule"), 1);

        // "asdf" -> "add" has edit distance of 2
        assertEquals(stringsimilarity.editDistance("asdf", "add"), 2);
    }
}
