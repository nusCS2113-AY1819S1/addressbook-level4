package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.login.User;

public interface ReadOnlyUserDatabase { ObservableList<User> getUsersList(); }
