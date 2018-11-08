package seedu.planner.model.tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//@author tztzt
/**
 * Contains a set of pre-defined tags that the user may use to their convenience and will
 * always be in the suggested list of tags.
 */
public class DefaultTags {

    private static final Tag TAG_CLOTHES = new Tag("clothes");
    private static final Tag TAG_SHOPPING = new Tag("shopping");
    private static final Tag TAG_GROCERIES = new Tag("groceries");

    private static final Tag TAG_FAMILY = new Tag("family");
    private static final Tag TAG_FURNITURE = new Tag("furniture");
    private static final Tag TAG_APPLIANCES = new Tag("appliances");
    private static final Tag TAG_ELECTRONICS = new Tag("electronics");
    private static final Tag TAG_PHONE = new Tag("phone");
    private static final Tag TAG_BILL = new Tag("bill");
    private static final Tag TAG_RENT = new Tag("rent");
    private static final Tag TAG_SCHOOL = new Tag("school");
    private static final Tag TAG_GIRLFRIEND = new Tag("girlfriend");
    private static final Tag TAG_BOYFRIEND = new Tag("boyfriend");

    private static final Tag TAG_SALARY = new Tag("salary");
    private static final Tag TAG_LOTTERY = new Tag("lottery");
    private static final Tag TAG_ALLOWANCE = new Tag("allowance");

    private static final Tag TAG_TRAVEL = new Tag("travel");
    private static final Tag TAG_CAB = new Tag("cab");
    private static final Tag TAG_PETROL = new Tag("petrol");
    private static final Tag TAG_CAR = new Tag("car");

    private static final Tag TAG_FOOD = new Tag("food");
    private static final Tag TAG_MACS = new Tag("macs");
    private static final Tag TAG_KFC = new Tag("kfc");
    private static final Tag TAG_CAIFAN = new Tag("caifan");
    private static final Tag TAG_CHICKENRICE = new Tag("jifan");
    private static final Tag TAG_LAKSA = new Tag("laksa");
    private static final Tag TAG_PRATA = new Tag("prata");
    private static final Tag TAG_TECHNO = new Tag("techno");
    private static final Tag TAG_DECK = new Tag("deck");

    private static Set<Tag> defaultTagSet = new HashSet<>(Arrays.asList(TAG_CLOTHES, TAG_SHOPPING, TAG_GROCERIES,
            TAG_FAMILY, TAG_FURNITURE, TAG_APPLIANCES, TAG_ELECTRONICS, TAG_PHONE, TAG_BILL, TAG_RENT, TAG_SCHOOL,
            TAG_GIRLFRIEND, TAG_BOYFRIEND, TAG_SALARY, TAG_LOTTERY, TAG_ALLOWANCE, TAG_TRAVEL, TAG_CAB, TAG_PETROL,
            TAG_CAR, TAG_FOOD, TAG_MACS, TAG_KFC, TAG_CAIFAN, TAG_CHICKENRICE, TAG_LAKSA, TAG_PRATA, TAG_TECHNO,
            TAG_DECK));

    public static Set<String> getSampleTagsForSuggestion() {
        Set<String> stringTagSet = new HashSet<>();
        for (Tag tag: defaultTagSet) {
            stringTagSet.add(tag.tagName);
        }
        return stringTagSet;
    }

}
