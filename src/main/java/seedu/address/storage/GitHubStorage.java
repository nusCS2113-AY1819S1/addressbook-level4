//@@author QzSG
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import org.kohsuke.github.GHGist;
import org.kohsuke.github.GHGistBuilder;
import org.kohsuke.github.GHGistFile;
import org.kohsuke.github.GitHub;

import seedu.address.commons.exceptions.OnlineBackupFailureException;

/**
 * A class to handle saving data to Github Gists.
 */
public class GitHubStorage implements OnlineStorage {

    private static GitHub github_ = null;
    private static final String SUCCESS_MESSAGE = "Successfully saved to Github Gists\nGists can be found at: %s";
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
    public String saveContentToStorage(String content, String fileName, String description)
            throws IOException {
        requireNonNull(content);
        requireNonNull(fileName);

        github_ = GitHub.connectUsingOAuth(authToken);
        GHGistBuilder ghGistBuilder = buildGistFromContent(content, fileName, description);
        GHGist ghGist = ghGistBuilder.create();
        return String.format(SUCCESS_MESSAGE, ghGist.getHtmlUrl().toString());
    }

    private GHGistBuilder buildGistFromContent(String content, String fileName, String description) {
        GHGistBuilder ghGistBuilder = new GHGistBuilder(github_);
        ghGistBuilder.public_(false).description(description).file(fileName, content);
        return ghGistBuilder;
    }

    /**
     * Reads content from Gist and returns it as a string
     * @param gistId
     * @return
     * @throws IOException
     */
    public String readContentFromGist(String gistId) throws IOException {
        github_ = GitHub.connectUsingOAuth(authToken);
        GHGist ghGist = github_.getGist(gistId);
        GHGistFile gistFile = ghGist.getFile("AddressBook.bak");
        return gistFile.getContent();
    }
}
