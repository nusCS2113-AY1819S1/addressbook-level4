package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.commons.util.StringUtil;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.candidate.Age;
import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.candidate.Name;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.AgeRange;
import seedu.recruit.model.joboffer.Job;
import seedu.recruit.model.joboffer.Salary;
import seedu.recruit.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */

public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_INDEX_ARGS = "Index arguments are not valid.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        return Index.fromOneBased(parseIndexStringToInteger(oneBasedIndex));
    }

    /**
     * Parses an Array {@code indexArgs} into an {@code Set<Index>} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */

    public static Set<Index> parseIndexSet(String[] indexArgs) throws ParseException {
        Set<Index> orderedIndexSet = new TreeSet<Index> (new LargestToSmallestIndexComparator());
        for (String index : indexArgs) {
            String[] indexRange = index.split("-");
            orderedIndexSet.addAll(parseIndexRange(indexRange));
        }
        return orderedIndexSet;
    }

    /**
     * Parses {@code indexRange} into a  {@code indexSet} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     *         or if user enters an invalid format of arguments.
     */
    private static Set<Index> parseIndexRange (String ...indexRange) throws ParseException {
        ArrayList<Integer> integerList = new ArrayList<>();
        Set<Index> indexSet = new HashSet<>();

        for (int i = 0; i < indexRange.length; i++) {
            if (i == 2) {
                throw new ParseException(MESSAGE_INVALID_INDEX_ARGS);
            }
            integerList.add(parseIndexStringToInteger(indexRange[i]));
        }

        for (int index = Collections.min(integerList); index <= Collections.max(integerList); index++) {
            indexSet.add(Index.fromOneBased(index));
        }
        return indexSet;
    }

    /**
     * Parses String @param index to an integer and returns it
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    private static Integer parseIndexStringToInteger(String index) throws ParseException {
        String trimmedIndex = index.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Integer.parseInt(trimmedIndex);
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

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_GENDER_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String age} into a {@code Age}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code age} is invalid.
     */
    public static Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        String trimmedAge = age.trim();
        if (!Age.isValidAge(trimmedAge)) {
            throw new ParseException(Age.MESSAGE_AGE_CONSTRAINTS);
        }
        return new Age(trimmedAge);
    }


    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String recruit} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code recruit} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String job} into an {@code Job}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code job} is invalid.
     */
    public static Job parseJob(String job) throws ParseException {
        requireNonNull(job);
        String trimmedJob = job.trim();
        if (!Job.isValidJob(trimmedJob)) {
            throw new ParseException(Job.MESSAGE_JOB_CONSTRAINTS);
        }
        return new Job(trimmedJob);
    }

    /**
     * Parses a {@code String education} into an {@code Education}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code education} is invalid.
     */
    public static Education parseEducation(String education) throws ParseException {
        requireNonNull(education);
        String trimmedEducation = education.trim();
        if (!Education.isValidEducation(trimmedEducation)) {
            throw new ParseException(Education.MESSAGE_EDUCATION_CONSTRAINTS);
        }
        return new Education(trimmedEducation);
    }

    /**
     * Parses a {@code String salary} into an {@code Salary}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code salary} is invalid.
     */
    public static Salary parseSalary(String salary) throws ParseException {
        requireNonNull(salary);
        String trimmedSalary = salary.trim();
        if (!Salary.isValidSalary(trimmedSalary)) {
            throw new ParseException(Salary.MESSAGE_SALARY_CONSTRAINTS);
        }
        return new Salary(trimmedSalary);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }


    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String company} into a {@code CompanyName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static CompanyName parseCompanyName(String company) throws ParseException {
        requireNonNull(company);
        String trimmedCompany = company.trim();
        if (!CompanyName.isValidCompanyName(trimmedCompany)) {
            throw new ParseException(CompanyName.MESSAGE_COMPANY_CONSTRAINTS);
        }
        return new CompanyName(trimmedCompany);
    }

    /**
     * Parses a {@code String ageRange} into a {@code AgeRange}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ageRange} is invalid.
     */
    public static AgeRange parseAgeRange (String ageRange) throws ParseException {
        requireNonNull(ageRange);
        String trimmedAgeRange = ageRange.trim();
        if (!AgeRange.isValidAgeRange(trimmedAgeRange)) {
            throw new ParseException(AgeRange.MESSAGE_AGE_RANGE_CONSTRAINTS);
        }
        return new AgeRange(trimmedAgeRange);
    }

}
