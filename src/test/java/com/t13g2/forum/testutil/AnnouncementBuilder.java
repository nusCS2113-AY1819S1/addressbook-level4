package com.t13g2.forum.testutil;

import com.t13g2.forum.model.forum.Announcement;

/**
 * A utility class to help with building Announcement objects.
 */
public class AnnouncementBuilder {

    public static final String DEFAULT_TITLE = "System Maintenance";
    public static final String DEFAULT_CONTENT = "Regular system maintenance on every Wednesday 4pm to 5pm.";

    private String title;
    private String content;

    public AnnouncementBuilder() {
        title = DEFAULT_TITLE;
        content = DEFAULT_CONTENT;
    }

    /**
     * Initializes the AnnouncementBuilder with the data of {@code announcementToCopy}.
     */
    public AnnouncementBuilder(Announcement announcementToCopy) {
        title = announcementToCopy.getTitle();
        content = announcementToCopy.getContent();
    }

    /**
     * Sets the {@code title} of the {@code Announcement} that we are building.
     */
    public AnnouncementBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the {@code content} of the {@code Announcement} that we are building.
     */
    public AnnouncementBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public Announcement build() {
        return new Announcement(title, content);
    }

}
