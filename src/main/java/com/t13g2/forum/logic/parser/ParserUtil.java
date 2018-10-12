package com.t13g2.forum.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.t13g2.forum.commons.core.index.Index;
import com.t13g2.forum.commons.util.AppUtil;
import com.t13g2.forum.commons.util.StringUtil;
import com.t13g2.forum.logic.parser.exceptions.ParseException;
import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.model.person.Address;
import com.t13g2.forum.model.person.Email;
import com.t13g2.forum.model.person.Name;
import com.t13g2.forum.model.person.Phone;
import com.t13g2.forum.model.tag.Tag;

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
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static String parseUserName(String userName) throws ParseException {
        String trimmedUserName = userName.trim();
        if (!User.isValidUserName(trimmedUserName)) {
            throw new ParseException(User.MESSAGE_USER_NAME_CONSTRAINTS);
        }
        return trimmedUserName;
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

    /**
     * Parses {@code title, content} into a {@code Announcement}
     */
    public static Announcement parseAnnouncement(String title, String content) throws ParseException {
        requireNonNull(title);
        requireNonNull(content);
        String trimmedTitle = title.trim();
        String trimmedContent = content.trim();
        if (!Announcement.isValidAnnouncement(trimmedTitle, trimmedContent)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Announcement(trimmedTitle, trimmedContent);
    }

    /**
     * moduleCode
     */
    public static String parseModule(String module) throws ParseException{
        requireNonNull(module);
        String trimmedModule = module.trim();
        if (!isValidModule(trimmedModule)) {
            throw new ParseException(Module.MESSAGE_MODULE_CONSTRAINTS);
        }
        return trimmedModule;
    }
    //Returns true if a given string is a valid module.
    public static boolean isValidModule(String trimmedModule) {
        return trimmedModule.matches(Module.MODULE_VALIDATION_REGEX);
    }

    /**
     * threadTitle
     */
    public static String parseThread(String threadTitle) throws ParseException{
        requireNonNull(threadTitle);
        String trimmedThreadTitle = threadTitle.trim();
        if (!isValidThreadTitle(trimmedThreadTitle)) {
            throw new ParseException(ForumThread.MESSAGE_THREAD_CONSTRAINTS);
        }
        return trimmedThreadTitle;
    }
    //Returns true if a given string is a valid thread title.
    public static boolean isValidThreadTitle(String trimmedThreadTitle) {
        return trimmedThreadTitle.matches(ForumThread.THREAD_VALIDATION_REGEX);
    }

    /**
     * threadId
     */
    public static String parseThreadId(String threadId) throws ParseException{
        requireNonNull(threadId);
        String trimmedThreadId = threadId.trim();
        if (!isValidThreadId(trimmedThreadId)) {
            throw new ParseException(ForumThread.MESSAGE_THREAD_ID_CONSTRAINTS);
        }
        return trimmedThreadId;
    }
    //Returns true if a given string is a valid thread id.
    public static boolean isValidThreadId(String trimmedThreadId) {
        return trimmedThreadId.matches(ForumThread.THREAD_ID_VALIDATION_REGEX);
    }

    /**
     * comment content
     */
    public static String parseComment(String comment) throws ParseException{
        requireNonNull(comment);
        String trimmedComment = comment.trim();
        if (!isValidComment(trimmedComment)) {
            throw new ParseException(Comment.MESSAGE_COMMENT_CONSTRAINTS);
        }
        return trimmedComment;
    }
    //Returns true if a given string is a valid comment content.
    public static boolean isValidComment(String trimmedComment) {
        return trimmedComment.matches(Comment.COMMENT_VALIDATION_REGEX);
    }

}
