package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.classroom.ClassModule;
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.Enrollment;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ClassroomParserUtil {
    /**
     * Parses a {@code String className} into a {@code ClassName}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code className} is invalid.
     */
    public static ClassName parseClassName(String className) throws ParseException {
        requireNonNull(className);
        String trimmedClassName = className.trim();
        if (!ClassName.isValidClassName(className)) {
            throw new ParseException(ClassName.MESSAGE_CLASSNAME_CONSTRAINTS);
        }
        return new ClassName(trimmedClassName);
    }

    /**
     *
     * Parses a {@code String moduleCode} into a {@code ClassModule}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code className} is invalid.
     */
    public static ClassModule parseClassModule(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        if (!ClassModule.isValidClassModule(moduleCode)) {
            throw new ParseException(ClassModule.MESSAGE_CLASSMODULE_CONSTRAINTS);
        }
        return new ClassModule(trimmedModuleCode);
    }

    /**
     *
     * Parses a {@code String moduleCode} into a {@code ClassModule}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code className} is invalid.
     */
    public static Enrollment parseEnrollment(String maxEnrollment) throws ParseException {
        requireNonNull(maxEnrollment);
        String trimmedMaxEnrollment = maxEnrollment.trim();
        if (!Enrollment.isValidEnrollment(maxEnrollment)) {
            throw new ParseException(Enrollment.MESSAGE_ENROLLMENT_CONSTRAINTS);
        }
        return new Enrollment(trimmedMaxEnrollment);
    }
}
