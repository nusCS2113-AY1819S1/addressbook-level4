package com.t13g2.forum.model.forum;

import com.t13g2.forum.commons.util.AppUtil;
import com.t13g2.forum.commons.util.CollectionUtil;

/**
 * Represents the announcement in ForumBook.
 * Guarantees: is valid as declared in {@link #isValidAnnouncement(String, String)}
 */
public class Announcement extends BaseModel {
    /**
     * Show message if announcement is not valid
     */
    public static final String MESSAGE_ANNOUNCEMENT_CONSTRAINTS =
        "Announcement can take any values, and it should not be blank";

    /**
     * The first character of the announcement must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ANNOUNCEMENT_VALIDATION_REGEX = "[^\\s].*";
    private String title;
    private String content;

    /**
     * Every field must be present and not null.
     */
    public Announcement(String title, String content) {
        CollectionUtil.requireAllNonNull(title, content);
        AppUtil.checkArgument(isValidAnnouncement(title, content), MESSAGE_ANNOUNCEMENT_CONSTRAINTS);
        this.title = title;
        this.content = content;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAnnouncement(String testTitle, String testContent) {
        return (testTitle.matches(ANNOUNCEMENT_VALIDATION_REGEX) || testContent.matches(ANNOUNCEMENT_VALIDATION_REGEX));
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
        builder.append(" Title: ")
            .append(getTitle())
            .append(" Content: ")
            .append(getContent());
        return builder.toString();
    }
}
