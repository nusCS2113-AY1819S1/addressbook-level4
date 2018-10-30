package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Candidate> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<Company> PREDICATE_MATCHING_NO_COMPANIES = unused -> false;

    // ============================= CANDIDATE BOOK ======================================= //

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setCandidateFilteredList(Model model, List<Candidate> toDisplay) {
        Optional<Predicate<Candidate>> predicate =
                toDisplay.stream().map(ModelHelper::getCandidatePredicateMatching).reduce(Predicate::or);
        model.updateFilteredCandidateList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setCandidateFilteredList(Model, List)
     */
    public static void setCandidateFilteredList(Model model, Candidate... toDisplay) {
        setCandidateFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Candidate} equals to {@code other}.
     */
    private static Predicate<Candidate> getCandidatePredicateMatching(Candidate other) {
        return person -> person.equals(other);
    }

    // ============================= COMPANY BOOK ======================================= //

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setCompanyFilteredList(Model model, List<Company> toDisplay) {
        Optional<Predicate<Company>> predicate =
                toDisplay.stream().map(ModelHelper::getCompanyPredicateMatching).reduce(Predicate::or);
        model.updateFilteredCompanyList(predicate.orElse(PREDICATE_MATCHING_NO_COMPANIES));
    }

    /**
     * @see ModelHelper#setCompanyFilteredList(Model, List)
     */
    public static void setCompanyFilteredList(Model model, Company... toDisplay) {
        setCompanyFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Company} equals to {@code other}.
     */
    private static Predicate<Company> getCompanyPredicateMatching(Company other) {
        return person -> person.equals(other);
    }
}
