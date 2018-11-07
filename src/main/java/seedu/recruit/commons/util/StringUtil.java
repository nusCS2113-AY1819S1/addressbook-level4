package seedu.recruit.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param keyword cannot be null, cannot be empty, can have more than one word
     */
    public static boolean containsWordIgnoreCase(String sentence, String keyword) {
        requireNonNull(sentence);
        requireNonNull(keyword);

        String preppedWord = keyword.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        if (preppedWord.equalsIgnoreCase(sentence)) {
            return true;
        }

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code age} falls within the {@code ageRange}.
     * {@code minAge}, {@code maxAge} and {@code age} should be an integer >= 16 and <60
     * <br>examples:<pre>
     *     isWithinRange("18", "16-30") == true
     *     isWithinRange("10", "20-35") == false
     *     isWithinRange("62", "20-35") == false
     * </pre>
     * @param minAge cannot be null
     * @param maxAge cannot be null
     * @param userAge cannot be null
     */

    public static boolean isWithinRange(int minAge, int maxAge, int userAge) {
        requireNonNull(userAge);
        requireNonNull(minAge);
        requireNonNull(maxAge);

        return ((userAge >= minAge) && (userAge <= maxAge));
    }

    /**
     * Returns true if the
     */

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
