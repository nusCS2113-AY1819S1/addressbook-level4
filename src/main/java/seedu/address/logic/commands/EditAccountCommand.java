package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;

/**
 * Edits the details of an existing account in the account list.
 */
public class EditAccountCommand extends Command {

    public static final String COMMAND_WORD = "editAccount";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the account identified "
            + "by the index number used in the displayed account database. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_USERNAME + "USERNAME] "
            + "[" + PREFIX_PASSWORD + "PASSWORD]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_USERNAME + "jalil";

    public static final String MESSAGE_EDIT_ACCOUNT_SUCCESS = "Edited Account: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "This account already exists in the database.";

    private final Index index;
    private final EditAccountDescriptor editAccountDescriptor;

    /**
     * @param index of the account in the filtered account list to edit
     * @param editAccountDescriptor details to edit the account with
     */
    public EditAccountCommand(Index index, EditAccountDescriptor editAccountDescriptor) {
        requireNonNull(index);
        requireNonNull(editAccountDescriptor);

        this.index = index;
        this.editAccountDescriptor = new EditAccountDescriptor(editAccountDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Account> lastShownList = model.getFilteredAccountList();


        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACCOUNT_DISPLAYED_INDEX);
        }

        Account accountToEdit = lastShownList.get(index.getZeroBased());
        Account editedAccount = createEditedAccount(accountToEdit, editAccountDescriptor);

        if (!accountToEdit.isSameAccount(editedAccount) && model.hasAccount(editedAccount)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
        }

        model.updateAccount(accountToEdit, editedAccount);
        model.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        return new CommandResult(String.format(MESSAGE_EDIT_ACCOUNT_SUCCESS, editedAccount));

    }

    /**
     * Creates and returns a {@code Account} with the details of {@code accountToEdit}
     * edited with {@code editAccountDescriptor}.
     */
    private static Account createEditedAccount(Account accountToEdit, EditAccountDescriptor editAccountDescriptor) {
        assert accountToEdit != null;

        Username updatedUsername = editAccountDescriptor.getUsername().orElse(accountToEdit.getUsername());
        Password updatedPassword = editAccountDescriptor.getPassword().orElse(accountToEdit.getPassword());

        return new Account(updatedUsername, updatedPassword);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAccountCommand)) {
            return false;
        }

        // state check
        EditAccountCommand e = (EditAccountCommand) other;
        return index.equals(e.index)
                && editAccountDescriptor.equals(e.editAccountDescriptor);
    }

    /**
     * Stores the details to edit the account with. Each non-empty field value will replace the
     * corresponding field value of the account.
     */
    public static class EditAccountDescriptor {
        private Username username;
        private Password password;

        public EditAccountDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAccountDescriptor(EditAccountDescriptor toCopy) {
            setUsername(toCopy.username);
            setPassword(toCopy.password);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(username, password);
        }

        public void setUsername(Username username) {
            this.username = username;
        }

        public Optional<Username> getUsername() {
            return Optional.ofNullable(username);
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        public Optional<Password> getPassword() {
            return Optional.ofNullable(password);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAccountDescriptor)) {
                return false;
            }

            // state check
            EditAccountDescriptor e = (EditAccountDescriptor) other;

            return getUsername().equals(e.getUsername())
                    && getPassword().equals(e.getPassword());
        }
    }
}
