//@@author QzSG
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import org.kohsuke.github.GHGist;
import org.kohsuke.github.GHGistBuilder;
import org.kohsuke.github.GHGistFile;
import org.kohsuke.github.GitHub;

import seedu.address.model.UserPrefs;

/**
 * A class to handle saving data to Github Gists.
 */
public class GithubStorage implements OnlineStorage {

    public static final String SUCCESS_MESSAGE = "Successfully saved to Github Gists";
    private static GitHub github_ = null;

    private Optional<String> authToken;

    public GithubStorage(){}
    public GithubStorage(Optional<String> authToken) {
        this.authToken = authToken;
    }

    @Override
    public void saveContentToStorage(String content, String fileName) {
        throw new UnsupportedOperationException("This online storage does not "
                + "support saveContentToStorage with 2 variables");
    }

    @Override
    public URL saveContentToStorage(String content, String fileName, String description)
            throws IOException {
        requireNonNull(content);
        requireNonNull(fileName);
        if (!authToken.isPresent()) {
            throw new NullPointerException();
        }

        github_ = GitHub.connectUsingOAuth(authToken.get());
        GHGistBuilder ghGistBuilder = buildGistFromContent(content, fileName, description);
        GHGist ghGist = ghGistBuilder.create();
        return ghGist.getHtmlUrl();
    }

    @Override
    public String readContentFromStorage(UserPrefs.TargetBook targetBook, String ref) throws IOException {
        requireNonNull(ref);
        return readContentFromGist(targetBook, ref);
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
    public String readContentFromGist(UserPrefs.TargetBook targetBook, String gistId) throws IOException {
        github_ = GitHub.connectAnonymously();
        GHGist ghGist = github_.getGist(gistId);
        GHGistFile gistFile = ghGist.getFile(String.format("%s.bak", targetBook.name()));
        return gistFile.getContent();
    }
}
