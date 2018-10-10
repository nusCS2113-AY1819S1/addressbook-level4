//@@author QzSG
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import org.kohsuke.github.GHGist;
import org.kohsuke.github.GHGistBuilder;
import org.kohsuke.github.GitHub;

import seedu.address.commons.exceptions.OnlineBackupFailureException;

/**
 * A class to handle saving data to Github Gists.
 */
public class GitHubStorage implements OnlineStorage {

    private static GitHub gitHub = null;
    private String authToken = null;

    public GitHubStorage(String authToken) {
        requireNonNull(authToken);
        this.authToken = authToken;
    }

    @Override
    public void saveContentToStorage(String content, String fileName) throws IOException, OnlineBackupFailureException {
        throw new UnsupportedOperationException("This online storage does not "
                + "support saveContentToStorage with 2 variables");
    }

    @Override
    public void saveContentToStorage(String content, String fileName, String description)
            throws IOException, OnlineBackupFailureException {
        requireNonNull(content);
        requireNonNull(fileName);

        gitHub = GitHub.connectUsingOAuth(authToken);
        GHGistBuilder ghGistBuilder = buildGistFromContent(content, fileName, description);
        GHGist ghGist = ghGistBuilder.create();
    }

    private GHGistBuilder buildGistFromContent(String content, String fileName, String description) {
        GHGistBuilder ghGistBuilder = new GHGistBuilder(gitHub);
        ghGistBuilder.public_(false).description(description).file(fileName, content);
        return ghGistBuilder;
    }
}
