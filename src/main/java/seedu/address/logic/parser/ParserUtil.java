package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenses.ExpensesAmount;
import seedu.address.model.expenses.MedicalExpenses;
import seedu.address.model.expenses.MiscellaneousExpenses;
import seedu.address.model.expenses.TravelExpenses;
import seedu.address.model.person.Address;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;
import seedu.address.model.recruitment.JobDescription;
import seedu.address.model.recruitment.Post;
import seedu.address.model.recruitment.WorkExp;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Type;
import seedu.address.model.schedule.Year;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String employeeId} into a {@code EmployeeId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code employeeId} is invalid.
     */
    public static EmployeeId parseEmployeeId(String employeeId) throws ParseException {
        requireNonNull(employeeId);
        String trimmedEmployeeId = employeeId.trim();
        if (!EmployeeId.isValidEmployeeId(trimmedEmployeeId)) {
            throw new ParseException(EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS);
        }
        return new EmployeeId(trimmedEmployeeId);
    }

    /**
     * Parses a {@code String job position} into a {@code jobPosition}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code jobPosition} is invalid.
     */
    public static Post parsePost(String post) throws ParseException {
        requireNonNull(post);
        String trimmedPost = post.trim();
        if (!Post.isValidPost(trimmedPost)) {
            throw new ParseException(Post.MESSAGE_POST_CONSTRAINTS);
        }
        return new Post(trimmedPost);
    }

    /**
     * Parses a {@code String job description} into a {@code jobDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code jobDescription} is invalid.
     */
    public static JobDescription parseJobDescription(String jobDescription) throws ParseException {
        requireNonNull(jobDescription);
        String trimmedJobDescription = jobDescription.trim();
        if (!JobDescription.isValidJobDescription(trimmedJobDescription)) {
            throw new ParseException(JobDescription.MESSAGE_JOB_DESCRIPTION_CONSTRAINTS);
        }
        return new JobDescription(trimmedJobDescription);
    }

    /**
     * Parses a {@code String workExp} into a {@code WorkExp}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code workExp} is invalid.
     */
    public static WorkExp parseWorkExp(String workExp) throws ParseException {
        requireNonNull(workExp);
        String trimmedWorkExp = workExp.trim();
        if (!WorkExp.isValidWorkExp(trimmedWorkExp)) {
            throw new ParseException(WorkExp.MESSAGE_WORK_EXP_CONSTRAINTS);
        }
        return new WorkExp(trimmedWorkExp);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidScheduleDate(trimmedDate)) {
            throw new ParseException(Date.getDateConstraintsError());
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Year parseYear(String date) throws ParseException {
        requireNonNull(date);
        String trimmedYear = date.trim();
        if (!Year.isValidYear(trimmedYear)) {
            throw new ParseException(Year.MESSAGE_YEAR_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

    /**
     * Parses {@code Collection<String> dates} into a {@code Set<Date>}.
     */
    public static Set<Date> parseDates(Collection<String> dates) throws ParseException {
        requireNonNull(dates);
        final Set<Date> dateSet = new HashSet<>();
        for (String dateFormat : dates) {
            dateSet.add(parseDate(dateFormat));
        }
        return dateSet;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Type parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Type.isValidType(trimmedStatus)) {
            throw new ParseException(Type.MESSAGE_TYPE_CONSTRAINTS);
        }
        return new Type(trimmedStatus);
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
     * Parses a {@code String dateOfBirth} into a {@code DateOfBirth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateOfBirth} is invalid.
     */
    public static DateOfBirth parseDateOfBirth(String dateOfBirth) throws ParseException {
        requireNonNull(dateOfBirth);
        String trimmedDateOfBirth = dateOfBirth.trim();
        if (!DateOfBirth.isValidDateOfBirth(trimmedDateOfBirth)) {
            throw new ParseException(DateOfBirth.getMessageDateOfBirthConstraints());
        }
        return new DateOfBirth(trimmedDateOfBirth);
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
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
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
     * Parses a {@code String department} into an {@code Department}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code department} is invalid.
     */
    public static Department parseDepartment(String department) throws ParseException {
        requireNonNull(department);
        String trimmedDepartment = department.trim();
        if (!Department.isValidDepartment(trimmedDepartment)) {
            throw new ParseException(Department.MESSAGE_DEPARTMENT_CONSTRAINTS);
        }
        return new Department(trimmedDepartment);
    }

    /**
     * Parses a {@code String position} into an {@code Position}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code position} is invalid.
     */
    public static Position parsePosition(String position) throws ParseException {
        requireNonNull(position);
        String trimmedPosition = position.trim();
        if (!Position.isValidPosition(trimmedPosition)) {
            throw new ParseException(Position.MESSAGE_POSITION_CONSTRAINTS);
        }
        return new Position(trimmedPosition);
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
     * Parses a {@code String bonus} into an {@code Bonus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code bonus} is invalid.
     */
    public static Bonus parseBonus(String bonus) throws ParseException {
        requireNonNull(bonus);
        String trimmedBonus = bonus.trim();
        if (!Bonus.isValidBonus(trimmedBonus)) {
            throw new ParseException(Bonus.MESSAGE_BONUS_CONSTRAINTS);
        }
        return new Bonus(trimmedBonus);
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
     * Parses a {@code String expensesAmount} into a {@code ExpensesAmount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expensesAmount} is invalid.
     */
    public static ExpensesAmount parseExpensesAmount(String expensesAmount) throws ParseException {
        requireNonNull(expensesAmount);
        String trimmedExpensesAmount = expensesAmount.trim();
        if (!ExpensesAmount.isValidExpensesAmount(trimmedExpensesAmount)) {
            throw new ParseException(ExpensesAmount.MESSAGE_EXPENSES_AMOUNT_CONSTRAINTS);
        }
        return new ExpensesAmount(trimmedExpensesAmount);
    }

    /**
     * Parses a {@code String expensesAmount} into a {@code ExpensesAmount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expensesAmount} is invalid.
     */
    public static TravelExpenses parseTravelExpenses(String travelExpenses) throws ParseException {
        requireNonNull(travelExpenses);
        String trimmedTravelExpenses = travelExpenses.trim();
        if (!TravelExpenses.isValidTravelExpenses(trimmedTravelExpenses)) {
            throw new ParseException(TravelExpenses.MESSAGE_TRAVEL_EXPENSES_CONSTRAINTS);
        }
        return new TravelExpenses(trimmedTravelExpenses);
    }

    /**
     * Parses a {@code String expensesAmount} into a {@code ExpensesAmount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expensesAmount} is invalid.
     */
    public static MedicalExpenses parseMedicalExpenses(String medicalExpenses) throws ParseException {
        requireNonNull(medicalExpenses);
        String trimmedMedicalExpenses = medicalExpenses.trim();
        if (!MedicalExpenses.isValidMedicalExpenses(trimmedMedicalExpenses)) {
            throw new ParseException(MedicalExpenses.MESSAGE_MEDICAL_EXPENSES_CONSTRAINTS);
        }
        return new MedicalExpenses(trimmedMedicalExpenses);
    }

    /**
     * Parses a {@code String expensesAmount} into a {@code ExpensesAmount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expensesAmount} is invalid.
     */
    public static MiscellaneousExpenses parseMiscellaneousExpenses(String miscellaneousExpenses) throws ParseException {
        requireNonNull(miscellaneousExpenses);
        String trimmedMiscellaneousExpenses = miscellaneousExpenses.trim();
        if (!MiscellaneousExpenses.isValidMiscellaneousExpenses(trimmedMiscellaneousExpenses)) {
            throw new ParseException(MiscellaneousExpenses.MESSAGE_MISCELLANEOUS_EXPENSES_CONSTRAINTS);
        }
        return new MiscellaneousExpenses(trimmedMiscellaneousExpenses);
    }
}
