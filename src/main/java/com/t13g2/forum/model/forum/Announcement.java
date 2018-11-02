package com.t13g2.forum.model.forum;

import com.t13g2.forum.commons.util.AppUtil;
import com.t13g2.forum.commons.util.CollectionUtil;

/**
 * Represents the announcement in ForumBook.
 * Guarantees: is valid as declared in {@link #isValidAnnouncement(String)}
 */
public class Announcement extends BaseModel {
    /**
     * Show message if announcement is not valid
     */
    public static final String MESSAGE_ANNOUNCEMENT_TITLE_CONSTRAINTS =
        "aTitle can take any values, and they should not be blank";
    public static final String MESSAGE_ANNOUNCEMENT_CONTENT_CONSTRAINTS =
        "aContent can take any values, and they should not be blank";

    /**
     * The first character of the announcement must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ANNOUNCEMENT_VALIDATION_REGEX = "[^\\s].*";
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    public Announcement() {
    }
    /**
     * Every field must be present and not null.
     */
    public Announcement(String title, String content) {
        CollectionUtil.requireAllNonNull(title, content);
        AppUtil.checkArgument(isValidAnnouncement(title), MESSAGE_ANNOUNCEMENT_TITLE_CONSTRAINTS);
        AppUtil.checkArgument(isValidAnnouncement(content), MESSAGE_ANNOUNCEMENT_CONTENT_CONSTRAINTS);
        this.title = title;
        this.content = content;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAnnouncement(String testString) {
        return (testString.matches(ANNOUNCEMENT_VALIDATION_REGEX));
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nTitle: ")
            .append(title)
            .append("\nContent: ")
            .append(content);
        return builder.toString();
    }
}
