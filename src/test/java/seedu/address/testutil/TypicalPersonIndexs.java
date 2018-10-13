package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.PersonIndex;

public class TypicalPersonIndexs {
    public static final String INDEX_1 = "1";
    public static final String INDEX_2 = "2";
    public static final PersonIndex PERSON_INDEX_1 = new PersonIndex(INDEX_1);
    public static final PersonIndex PERSON_INDEX_2 = new PersonIndex(INDEX_2);

    public static Set<PersonIndex> getTypicalPersonIndexs(){
        Set<PersonIndex> personIndexSet = new HashSet<>();
        personIndexSet.add(PERSON_INDEX_1);
        personIndexSet.add(PERSON_INDEX_2);
        return personIndexSet;
    }
    public static Set<PersonIndex> getSingleTypicalPersonIndexs(){
        Set<PersonIndex> personIndexSet = new HashSet<>();
        personIndexSet.add(PERSON_INDEX_1);
        return personIndexSet;
    }
}
