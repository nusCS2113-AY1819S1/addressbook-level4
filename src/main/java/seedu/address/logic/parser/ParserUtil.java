package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.isValidPath;
import static seedu.address.commons.util.FileUtil.isValidXmlFilename;
import static seedu.address.model.Filetype.isValidFiletype;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Filetype;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.EventName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TheDate;
import seedu.address.model.person.Time;
import seedu.address.model.reminder.Agenda;
import seedu.address.model.reminder.Date;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Title;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_FILENAME = "Filename is invalid.";
    public static final String MESSAGE_INVALID_EXTENSION = "Filename must end with \".xml\".";

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

    //@@author jitwei98
    /**
     * Parses a {@code String filename} into a {@code Path}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code filename} is invalid.
     */
    public static Path parseFilename(String filename) throws ParseException {
        requireNonNull(filename);

        String trimmedFilename = filename.trim();
        if (!isValidPath(trimmedFilename)) {
            throw new ParseException(MESSAGE_INVALID_FILENAME);
        }
        if (!isValidXmlFilename(trimmedFilename)) {
            throw new ParseException(MESSAGE_INVALID_EXTENSION);
        }
        return Paths.get("data", trimmedFilename);
    }

    //@@author jitwei98
    /**
     * Parses a {@code String filetype} into a {@code Filetype}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String filetype} is invalid.
     */
    public static Filetype parseFiletype(String filetype) throws ParseException {
        requireNonNull(filetype);

        String trimmedFiletype = filetype.trim();
        if (!isValidFiletype(trimmedFiletype)) {
            throw new ParseException(Filetype.MESSAGE_FILETYPE_CONSTRAINTS);
        }
        return new Filetype(trimmedFiletype);
    }

    //@@author linnnruo
    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String content} into a {@code Content}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code content} is invalid.
     */
    public static Content parseContent(String content) throws ParseException {
        requireNonNull(content);
        String trimmedContent = content.trim();
        if (!Content.isValidContent(trimmedContent)) {
            throw new ParseException(Content.MESSAGE_CONTENT_CONSTRAINTS);
        }
        return new Content(trimmedContent);
    }

    //@author: driedmelon

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static TheDate parseTheDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!TheDate.isValidDate(trimmedDate)) {
            throw new ParseException(TheDate.MESSAGE_DATE_CONSTRAINTS);
        }
        return new TheDate(trimmedDate);
    }

    /**
     * Parses a {@code String time} into a {@code Time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaes will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String agenda} into a {@code Agenda}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code agenda} is invalid.
     */
    public static Agenda parseAgenda(String agenda) throws ParseException {
        requireNonNull(agenda);
        String trimmedAgenda = agenda.trim();
        if (!Agenda.isValidAgenda(trimmedAgenda)) {
            throw new ParseException(Agenda.MESSAGE_AGENDA_CONSTRAINTS);
        }
        return new Agenda(trimmedAgenda);
    }

    /**
     * Parses a {@code String eventName} into a {@code EventName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventName} is invalid.
     */
    public static EventName parseEventName(String eventName) throws ParseException {
        requireNonNull(eventName);
        String trimmedEventName = eventName.trim();
        if (!EventName.isValidEventName(trimmedEventName)) {
            throw new ParseException(EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
        }
        return new EventName(trimmedEventName);
    }

    /**
     * Parses {@code Collection<String> matchSchedule} into a {@code List<Index>}.
     */
    public static List<Index> parseMatchScheduleIndex(Collection<String> matchScheduleIndex) throws ParseException {
        requireNonNull(matchScheduleIndex);
        final List<Index> matchScheduleIndexList = new ArrayList<>();
        for (String matchSchedulePerson : matchScheduleIndex) {
            matchScheduleIndexList.add(parseIndex(matchSchedulePerson));
        }
        return matchScheduleIndexList;
    }

}
