package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;

/**
 * An event requesting to view the email preview
 */
public class ShowEmailPreviewEvent extends BaseEvent {
    private String emailPreview;

    public ShowEmailPreviewEvent(String emailPreview) {
        this.emailPreview = emailPreview;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public String getEmailPreview() {
        return emailPreview;
    }
}
