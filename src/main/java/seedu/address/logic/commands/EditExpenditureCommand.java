package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.*;


public class EditExpenditureCommand extends Command{

    public static final String COMMAND_WORD = "ET_edit";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the expenditure identified "
            + "by the index number used in the displayed expenditure list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_MONEY + "MONEY] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE+ "25/12/2017 "
            + PREFIX_MONEY + "500";

    public static final String MESSAGE_EDIT_EXPENDITURE_SUCCESS = "Edited Expenditure: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EXPENDITURE = "This expenditure already exists in the address book.";

    private final Index index;
    private final EditExpenditureDescriptor editExpenditureDescriptor;

    public EditExpenditureCommand(Index index, EditExpenditureDescriptor editExpenditureDescriptor) {
        requireNonNull(index);
        requireNonNull(editExpenditureDescriptor);

        this.index = index;
        this.editExpenditureDescriptor = new EditExpenditureDescriptor(editExpenditureDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Expenditure> lastShownList = model.getFilteredExpenditrueList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
        }

        Expenditure expenditureToEdit = lastShownList.get(index.getZeroBased());
        Expenditure editedexpenditure = createEditedExpenditure(expenditureToEdit, editExpenditureDescriptor);

        if (!expenditureToEdit.isSameExpenditure(editedexpenditure) && model.hasExpenditure(editedexpenditure)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENDITURE);
        }

        model.updateExpenditure(expenditureToEdit, editedexpenditure);
        model.updateFilteredExpenditureList(Model.PREDICATE_SHOW_ALL_EXPENDITURES);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_EXPENDITURE_SUCCESS, editedexpenditure));
    }

    /**
     * Creates and returns a {@code Expenditure} with the details of {@code ExpenditureToEdit}
     * edited with {@code editExpenditureDescriptor}.
     */
    private static Expenditure createEditedExpenditure(Expenditure expenditureToEdit, EditExpenditureDescriptor editExpenditureDescriptor) {
        assert expenditureToEdit != null;

        Date updatedDate = editExpenditureDescriptor.getDate().orElse(expenditureToEdit.getDate());
        Money updatedMoney = editExpenditureDescriptor.getMoney().orElse(expenditureToEdit.getMoney());
        Category updatedCategory = editExpenditureDescriptor.getCategory().orElse(expenditureToEdit.getCategory());

        return new Expenditure(updatedDate, updatedMoney, updatedCategory);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditExpenditureCommand)) {
            return false;
        }

        // state check
        EditExpenditureCommand e = (EditExpenditureCommand) other;
        return index.equals(e.index)
                && editExpenditureDescriptor.equals(e.editExpenditureDescriptor);
    }

    /**
     * Stores the details to edit the Expenditure with. Each non-empty field value will replace the
     * corresponding field value of the Expenditure.
     */
    public static class EditExpenditureDescriptor {
        private Date date;
        private Money money;
        private Category category;



        public EditExpenditureDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExpenditureDescriptor(EditExpenditureDescriptor toCopy) {
            setDate(toCopy.date);
            setMoney(toCopy.money);
            setCategory(toCopy.category);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, money, category);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setMoney(Money money) {
            this.money = money;
        }

        public Optional<Money> getMoney() {
            return Optional.ofNullable(money);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }





        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditExpenditureDescriptor)) {
                return false;
            }

            // state check
            EditExpenditureDescriptor e = (EditExpenditureDescriptor) other;

            return getDate().equals(e.getDate())
                    && getMoney().equals(e.getMoney())
                    && getCategory().equals(e.getCategory());

        }
    }



}
