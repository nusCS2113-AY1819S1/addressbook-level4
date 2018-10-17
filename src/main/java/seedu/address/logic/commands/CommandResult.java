package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;
    public final String feedbackToUserWebView;

    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.feedbackToUserWebView = null;
    }

    public CommandResult(String feedbackToUser, String feedbackToUserWebView) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.feedbackToUserWebView = requireNonNull(feedbackToUserWebView);
    }

}
