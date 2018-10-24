//package seedu.address.model.grade;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import seedu.address.commons.core.index.Index;
//
//import seedu.address.logic.Logic;
//import seedu.address.model.person.Person;
//import seedu.address.model.util.SortGrade;
//
///**
// * Represents persons to be added to a scorelist in the address book.
// * Guarantees: details are present and not null, field values are validated, immutable.
// */
//public class AddTestMarks {
//
//    private static final String COLON_SEPARATOR = " : ";
//    private final List<Person> personListName;
//
//    private Logic model;
//    // Identity fields
//    private final List<Person> personList = model.getFilteredPersonList();
//
//   // private final Person personName = personListName.iterator().next().getName();
//
//
//    //Data Fields
//    private final Set<Test> TestSet = new HashSet<>();
//
//
//    /**
//     * Every field must be present and not null.
//     */
//    public AddTestMarks(List<Person> personListName, Set<Test> TestSet) {
//        requireAllNonNull(personListName, TestSet);
//        this.personListName = personListName;
//        this.TestSet.addAll(TestSet);
//    }
//
//    public Set<Test> getTests() {
//        return personList.iterator().next().getTests();
//    }
//
//
//    /**
//     * Add Test to testSet if not duplicated.
//     */
//    public void fillTestSet(List<Test> test,) {
//        requireNonNull(test);
//        if (test.contains())
//        TestSet.add(test.get(groupIndex.getZeroBased()));
//    }
//
//    /**
//     * Check if person index input by user is in range of what
//     * is displayed on the person list panel.
//     * @param size
//     * @return
//     */
//    public boolean validPersonIndexSet(int size) {
//        for (Index i : personIndices) {
//            if (i.getZeroBased() >= size) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Check if group index input by user is in range of what
//     * is displayed on the group list panel.
//     * @param size
//     * @return
//     */
//    public boolean validGroupIndex(int size) {
//        if (groupIndex.getZeroBased() >= size) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof AddTestMarks // instanceof handles nulls
//                && groupIndex.equals(((AddTestMarks) other).groupIndex) // state check
//                && personIndices.equals(((AddTestMarks) other).personIndices)
//                && personSet.equals(((AddTestMarks) other).personSet));
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append(groupIndex)
//                .append(COLON_SEPARATOR);
//        personIndices.forEach(builder::append);
//        return builder.toString();
//    }
//
//}
