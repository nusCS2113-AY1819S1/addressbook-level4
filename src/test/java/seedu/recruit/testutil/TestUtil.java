package seedu.recruit.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the candidate in the {@code model}'s candidate list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredCandidateList().size() / 2);
    }

    /**
     * Returns the last index of the candidate in the {@code model}'s candidate list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredCandidateList().size());
    }

    /**
     * Returns the candidate in the {@code model}'s candidate list at {@code index}.
     */
    public static Candidate getPerson(Model model, Index index) {
        return model.getFilteredCandidateList().get(index.getZeroBased());
    }

    /**
     * Parse an array {@code indexes} into a set sorted in descending order
     */

    public static Set<Index> getIndexSet(Index ... indexes) {
        Set<Index> indexSet = new TreeSet<Index>(new LargestToSmallestIndexComparator());
        for (int i = 0; i < indexes.length; i++) {
            indexSet.add(indexes[i]);
        }
        return indexSet;
    }

    /**
     * Comparator for returning Index Sets
     */

    private static class LargestToSmallestIndexComparator implements Comparator<Index> {
        @Override
        public int compare(Index a, Index b) {
            return a.getZeroBased() < b.getZeroBased() ? 1 : a.getZeroBased() == b.getZeroBased() ? 0 : -1;
        }
    }

}
