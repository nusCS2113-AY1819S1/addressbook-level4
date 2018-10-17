package seedu.address.model.util;

import java.nio.file.Paths;

import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserDatabase;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;

/**
 * Contains utility methods for populating {@code User Database} with sample data.
 */
public class SampleUsersUtil {

    public static final String ALEX_AB_PATH = "data/addressbook-alex.xml";
    public static final String BERNICE_AB_PATH = "data/addressbook-bernice.xml";

    public static User[] getSampleUsers() {
        return new User[] {
            new User(new Username("alex"), new Password("87438807"), Paths.get(ALEX_AB_PATH)),
            new User(new Username("bernice"), new Password("99272758"), Paths.get(BERNICE_AB_PATH))
        };
    }

    public static ReadOnlyUserDatabase getSampleUserDatabase() {
        UserDatabase sampleUd = new UserDatabase();

        for (User sampleUser : getSampleUsers()) {
            sampleUd.addUser(sampleUser);
        }
        return sampleUd;
    }
}
