package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.kohsuke.github.HttpException;

import seedu.address.commons.util.XmlUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.model.UserPrefs;

public class GithubStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "GithubStorageTest");
    private static final String TEST_VALID_ADDRESS_BOOK_FILENAME = "AddressBook.bak";
    private static final String TEST_VALID_ADDRESS_BOOK_GIST_ID = "c877006f34937fa5133b9619e2d7be1b";
    private static final String TEST_VALID_EXPENSE_BOOK_GIST_ID = "6e6d4388b672da5a29c951630f4610db";
    private static final String TEST_VALID_MOCK_CONTENT = "SAMPLE CONTENT STRING";
    private static final String TEST_VALID_TOKEN_GIST_URL = "CORRECT_TOKEN";
    private static final String TRAVIS_ENV = System.getenv("IS_TRAVIS");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private GithubStorage githubStorage = new GithubStorage(Optional.empty());
    private GithubStorageStub githubStorageStub;
    private ReadOnlyAddressBook readOnlyAddressBook;
    private ReadOnlyExpenseBook readOnlyExpenseBook;
    private URL testValidAddressBookGistUrl;

    @Before
    public void setUp() throws Exception {
        assumeFalse(TRAVIS_ENV != null && TRAVIS_ENV.equals("true"));
        testValidAddressBookGistUrl = new URL("https://gist.github.com/QzSG/c877006f34937fa5133b9619e2d7be1b");
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(
                TEST_DATA_FOLDER.resolve("ValidAddressBook.bak"));
        readOnlyAddressBook = xmlAddressBookStorage.readAddressBook().get();
        XmlExpenseBookStorage xmlExpenseBookStorage = new XmlExpenseBookStorage(
                TEST_DATA_FOLDER.resolve("ValidExpenseBook.bak"));
        readOnlyExpenseBook = xmlExpenseBookStorage.readExpenseBook().get();
    }

    @Test
    public void readAddressBookContent_validGistId_contentReadSuccess() throws Exception {
        assumeFalse(TRAVIS_ENV != null && TRAVIS_ENV.equals("true"));
        ReadOnlyAddressBook restoredAddressBook = XmlUtil.getDataFromString(
                githubStorage.readContentFromStorage(UserPrefs.TargetBook.AddressBook, TEST_VALID_ADDRESS_BOOK_GIST_ID),
                XmlSerializableAddressBook.class).toModelType();
        assertEquals(readOnlyAddressBook, restoredAddressBook);
    }

    @Test
    public void readExpenseBookContent_validGistId_contentReadSuccess() throws Exception {
        assumeFalse(TRAVIS_ENV != null && TRAVIS_ENV.equals("true"));
        ReadOnlyExpenseBook restoredExpenseBook = XmlUtil.getDataFromString(
                githubStorage.readContentFromStorage(UserPrefs.TargetBook.ExpenseBook, TEST_VALID_EXPENSE_BOOK_GIST_ID),
                XmlSerializableExpenseBook.class).toModelType();
        assertEquals(readOnlyExpenseBook.hashCode(), restoredExpenseBook.hashCode());
    }

    @Test
    public void readAddressBookContent_validGistIdInvalidContent_throws() throws Exception {
        assumeFalse(TRAVIS_ENV != null && TRAVIS_ENV.equals("true"));
        thrown.expect(NullPointerException.class); //Gist does not contain file named AddressBook.bak
        githubStorage.readContentFromStorage(UserPrefs.TargetBook.AddressBook, TEST_VALID_EXPENSE_BOOK_GIST_ID);
    }

    @Test
    public void readExpenseBookContent_validGistIdValidContent_readsSuccess() throws Exception {
        assumeFalse(TRAVIS_ENV != null && TRAVIS_ENV.equals("true"));
        String content = githubStorage.readContentFromStorage(UserPrefs.TargetBook.ExpenseBook,
                TEST_VALID_EXPENSE_BOOK_GIST_ID);
        assertTrue(!content.isEmpty()); //Non empty string content
    }

    @Test
    public void saveAddressBookContent_validContent_backupsSuccess() throws Exception {
        assumeFalse(TRAVIS_ENV != null && TRAVIS_ENV.equals("true"));
        String validContent = XmlUtil.convertDataToString(
                new XmlSerializableAddressBook(readOnlyAddressBook));
        githubStorageStub = new GithubStorageStub(Optional.ofNullable(TEST_VALID_TOKEN_GIST_URL));
        URL url = githubStorageStub.saveContentToStorage(validContent, TEST_VALID_ADDRESS_BOOK_FILENAME,
                "Address Book Backup Data");
        assertEquals(url, testValidAddressBookGistUrl);
    }

    @Test
    public void unSupportedOperationCalled_throws() {
        assumeFalse(TRAVIS_ENV != null && TRAVIS_ENV.equals("true"));
        thrown.expect(UnsupportedOperationException.class);
        githubStorage.saveContentToStorage(TEST_VALID_MOCK_CONTENT, TEST_VALID_ADDRESS_BOOK_FILENAME);
    }

    private class GithubStorageStub extends GithubStorage {
        private Optional<String> authToken;
        public GithubStorageStub(Optional<String> authToken) {
            this.authToken = authToken;
        }

        @Override
        public URL saveContentToStorage(String content, String fileName, String description)
                throws MalformedURLException, HttpException {
            requireNonNull(content);
            requireNonNull(fileName);
            if (!authToken.isPresent()) {
                throw new NullPointerException();
            }
            if (!authToken.get().equals("CORRECT_TOKEN")) {
                throw new HttpException("Bad credentials", 400,
                        "Bad credentials", "https://developer.github.com/v3/#authentication");
            }
            return (testValidAddressBookGistUrl);
        }

        @Override
        public void saveContentToStorage(String content, String fileName) {
            throw new AssertionError("This method should not be called");
        }
    }
}
