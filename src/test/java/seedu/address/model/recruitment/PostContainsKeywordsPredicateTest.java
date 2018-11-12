package seedu.address.model.recruitment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.recruitment.RecruitmentBuilder;

public class PostContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordsList = Collections.singletonList("Network Engineer");
        List<String> secondPredicateKeywordsList = Arrays.asList("Network Engineer", "Accountants");

        PostContainsKeywordsPredicate firstPredicateList =
                new PostContainsKeywordsPredicate(firstPredicateKeywordsList);
        PostContainsKeywordsPredicate secondPredicateList =
                new PostContainsKeywordsPredicate(secondPredicateKeywordsList);

        // same object -> returns true
        assertTrue(firstPredicateList.equals(firstPredicateList));

        // same values -> returns true
        PostContainsKeywordsPredicate firstPredicateListCopy =
                new PostContainsKeywordsPredicate(firstPredicateKeywordsList);
        assertTrue(firstPredicateList.equals(firstPredicateListCopy));

        // different types -> returns false
        assertFalse(firstPredicateList.equals(1));

        // null -> returns false
        assertFalse(firstPredicateList == null);

        // different posts -> returns false
        assertFalse(firstPredicateList.equals(secondPredicateList));
    }

    @Test
    public void test_postContainsKeywords_returnsTrue() {
        // One keyword
        PostContainsKeywordsPredicate predicate =
                new PostContainsKeywordsPredicate(Collections.singletonList("Network Engineer"));
        assertTrue(predicate.test(new RecruitmentBuilder().withPost("Network Engineer").build()));

        // Multiple keywords
        predicate = new PostContainsKeywordsPredicate(Arrays.asList("Network Engineer", "Accountants"));
        assertTrue(predicate.test(new RecruitmentBuilder().withPost("Accountants").build()));

        // Only one matching keyword
        predicate = new PostContainsKeywordsPredicate(Arrays.asList("Accountants", "Purchaser"));
        assertTrue(predicate.test(new RecruitmentBuilder().withPost("Accountants").build()));
    }

    @Test
    public void test_postDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PostContainsKeywordsPredicate predicate =
                new PostContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new RecruitmentBuilder().withPost("Network Engineer").build()));

        // Non-matching keyword
        predicate = new PostContainsKeywordsPredicate(Arrays.asList("Accountants"));
        assertFalse(predicate.test(new RecruitmentBuilder().withPost("Network Engineer").build()));
    }

    @Test
    public void test_postDoesNotContainKeyword_returnsFalse() {
        // Non-matching keyword
        PostContainsKeywordsPredicate predicate =
                new PostContainsKeywordsPredicate(Arrays.asList("Accountants"));
        assertFalse(predicate.test(new RecruitmentBuilder().withPost("Network Engineer").build()));
    }

}
