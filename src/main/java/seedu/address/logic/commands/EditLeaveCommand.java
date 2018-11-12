package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Approval;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.EmployeeId;
import seedu.address.model.leave.Leave;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.session.SessionManager;

//@@author Hafizuddin-NUS
/**
 * Edits the details of an existing leave in the leave list.
 */
public class EditLeaveCommand extends Command {

    public static final String COMMAND_APPROVE = "approve";
    public static final String COMMAND_REJECT = "reject";

    public static final String MESSAGE_USAGE = COMMAND_APPROVE
            + ": Approve of the leave identified "
            + "by the index number used in the displayed leave list."
            + "\n" + COMMAND_REJECT + ": Reject of the leave identified "
            + "by the index number used in the displayed leave list.\n"
            + "\nParameters: INDEX (must be a positive integer) "
            + "[" + COMMAND_APPROVE + " INDEX] " + "or [" + COMMAND_REJECT + " INDEX]"
            + "\nExample: " + COMMAND_APPROVE + " 1 , " + COMMAND_REJECT + " 1";

    public static final String MESSAGE_EDIT_LEAVE_SUCCESS = "Approve/Reject Leave: %1$s";
    public static final String MESSAGE_ALREADY_APPROVE = "This leave application is already approved.";
    public static final String MESSAGE_ALREADY_REJECTED = "This leave application is already rejected.";
    public static final String MESSAGE_INVALID_LEAVE_APPROVAL =
            "Not authorized to approve/reject this leave application.";
    public static final String MESSAGE_INVALID_LEAVE_APPROVAL2 =
            "Not authorized to approve/reject your leave application. "
                    + "Only another Administrator can approve your leave.";

    private final Index index;
    private final EditLeaveDescriptor editLeaveDescriptor;

    /**
     * @param index of the leave in the filtered leave list to edit
     * @param editLeaveDescriptor details to edit the leave with
     */
    public EditLeaveCommand(Index index, EditLeaveDescriptor editLeaveDescriptor) {
        requireNonNull(index);
        requireNonNull(editLeaveDescriptor);

        this.index = index;
        this.editLeaveDescriptor = new EditLeaveDescriptor(editLeaveDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        SessionManager sessionManager = SessionManager.getInstance(model);

        if (!sessionManager.isLoggedIn()) {
            throw new CommandException(SessionManager.NOT_LOGGED_IN);
        }

        List<Leave> lastShownList = model.getFilteredLeaveList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
        }

        Leave leaveToEdit = lastShownList.get(index.getZeroBased());
        Leave editedLeave = createEditedLeave(leaveToEdit, editLeaveDescriptor);

        if (sessionManager.getLoggedInPriorityLevel().priorityLevelCode == 0
                && leaveToEdit.getEmployeeId().nric.equals(sessionManager.getLoggedInSessionNric().nric)) {
            throw new CommandException(MESSAGE_INVALID_LEAVE_APPROVAL2);
        } else if (sessionManager.getLoggedInPriorityLevel().priorityLevelCode != 0
                && sessionManager.getLoggedInPriorityLevel().priorityLevelCode
                >= leaveToEdit.getPriorityLevel().priorityLevelCode) {
            throw new CommandException(MESSAGE_INVALID_LEAVE_APPROVAL);
        }

        if (leaveToEdit.equals(editedLeave)) {
            if (leaveToEdit.getApproval().status == "APPROVED") {
                throw new CommandException(MESSAGE_ALREADY_APPROVE);
            } else if (leaveToEdit.getApproval().status == "REJECTED") {
                throw new CommandException(MESSAGE_ALREADY_REJECTED);
            }
        }

        model.updateLeave(leaveToEdit, editedLeave);
        model.commitLeaveList();
        return new CommandResult(String.format(MESSAGE_EDIT_LEAVE_SUCCESS, editedLeave));
    }

    /**
     * Creates and returns a {@code Leave} with the details of {@code leaveToEdit}
     * edited with {@code editLeaveDescriptor}.
     */
    public static Leave createEditedLeave(Leave leaveToEdit, EditLeaveDescriptor editLeaveDescriptor) {
        assert leaveToEdit != null;

        Approval updatedApproval = editLeaveDescriptor.getApproval().orElse(leaveToEdit.getApproval());
        PriorityLevel updatedPriority = editLeaveDescriptor.getPriorityLevel().orElse(leaveToEdit.getPriorityLevel());

        return new Leave(leaveToEdit.getEmployeeId(), leaveToEdit.getDate(),
                updatedApproval, updatedPriority);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditLeaveCommand)) {
            return false;
        }

        // state check
        EditLeaveCommand e = (EditLeaveCommand) other;
        return index.equals(e.index)
                && editLeaveDescriptor.equals(e.editLeaveDescriptor);
    }

    /**
     * Stores the details to edit the leave with. Each non-empty field value will replace the
     * corresponding field value of the leave.
     */
    public static class EditLeaveDescriptor {
        private EmployeeId nric;
        private Date date;
        private Approval approval;
        private PriorityLevel priorityLevel;


        public EditLeaveDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditLeaveDescriptor(EditLeaveDescriptor toCopy) {

            setNric(toCopy.nric);
            setDate(toCopy.date);
            setApproval(toCopy.approval);
            setPriorityLevel(priorityLevel);
        }

        public void setNric(EmployeeId nric) {
            this.nric = nric;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public void setApproval(Approval approval) {
            this.approval = approval;
        }

        public void setPriorityLevel(PriorityLevel priorityLevel) {
            this.priorityLevel = priorityLevel;
        }

        public Optional<EmployeeId> getNric() {
            return Optional.ofNullable(nric);
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public Optional<Approval> getApproval() {
            return Optional.ofNullable(approval);
        }

        public Optional<PriorityLevel> getPriorityLevel() {
            return Optional.ofNullable(priorityLevel);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLeaveDescriptor)) {
                return false;
            }

            // state check
            EditLeaveDescriptor e = (EditLeaveDescriptor) other;

            return getApproval().equals(e.getApproval()) && getNric().equals(e.getNric())
                    && getDate().equals(e.getDate()) && getPriorityLevel().equals(e.getPriorityLevel());
        }
    }
}
