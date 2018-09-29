package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javafx.collections.ObservableList;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UniqueAccountList;

public class LoginBook implements ReadOnlyLoginBook{

    private final UniqueAccountList accounts;

    public LoginBook() {
        accounts = new UniqueAccountList();
    }

    //// login-level operations

    /**
     * Returns true if an account with the same credentials as {@code loginDetails} exists in the login book.
     */
    public boolean hasAccount(LoginDetails loginDetails) {
        requireNonNull(loginDetails);
        return accounts.contains(loginDetails);
    }

    /**
     * Adds an account to the login book.
     * The account must not already exist in the login book.
     */
    public void createAccount(LoginDetails l) {
        accounts.add(l);
    }

    //// util methods


    @Override
    public FileSystem getFileSystem() {
        return null;
    }

    @Override
    public boolean isAbsolute() {
        return false;
    }

    @Override
    public Path getRoot() {
        return null;
    }

    @Override
    public Path getFileName() {
        return null;
    }

    @Override
    public Path getParent() {
        return null;
    }

    @Override
    public int getNameCount() {
        return 0;
    }

    @Override
    public Path getName(int index) {
        return null;
    }

    @Override
    public Path subpath(int beginIndex, int endIndex) {
        return null;
    }

    @Override
    public boolean startsWith(Path other) {
        return false;
    }

    @Override
    public boolean endsWith(Path other) {
        return false;
    }

    @Override
    public Path normalize() {
        return null;
    }

    @Override
    public Path resolve(Path other) {
        return null;
    }

    @Override
    public Path relativize(Path other) {
        return null;
    }

    @Override
    public URI toUri() {
        return null;
    }

    @Override
    public Path toAbsolutePath() {
        return null;
    }

    @Override
    public Path toRealPath(LinkOption... options) throws IOException {
        return null;
    }

    @Override
    public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers) throws IOException {
        return null;
    }

    @Override
    public int compareTo(Path other) {
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginBook // instanceof handles nulls
                && accounts.equals(((LoginBook) other).accounts));
    }

    @Override
    public int hashCode() {
        return accounts.hashCode();
    }

    @Override
    public ObservableList<LoginDetails> getLoginDetailsList() {
        return accounts.asUnmodifiableObservableList();
    }
}
