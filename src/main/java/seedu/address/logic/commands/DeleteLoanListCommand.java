package seedu.address.logic.commands;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Name;
import seedu.address.storage.XmlAdaptedLoanList;
import seedu.address.storage.XmlAdaptedLoanerDescription;

/**
 * Deletes an entry in the loan list base on the Index
 */

public class DeleteLoanListCommand extends Command {
    public static final String COMMAND_WORD = "deleteLoanList";
    public static final String MESSAGE_SUCCESS = "Loan list entry has been deleted";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the loan list entry identified "
            + "by the index number used in the display of the loan list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_EMPTY = "Loan list is currently empty";
    public static final String MESSAGE_INVALID_INDEX = "The input index is invalid";

    private final Index index;

    public DeleteLoanListCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        File loanListFile = new File("C:/Users/ckinw/OneDrive/Documents/JalilEnterprisesCKW/data/LoanList.xml");
        if (!loanListFile.exists()) {
            throw new CommandException(MESSAGE_EMPTY);
        }
        try {
            JAXBContext context = JAXBContext.newInstance(XmlAdaptedLoanList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XmlAdaptedLoanList xmlAdaptedLoanList = (XmlAdaptedLoanList) unmarshaller
                    .unmarshal(loanListFile);
            ArrayList<XmlAdaptedLoanerDescription> loanList = xmlAdaptedLoanList.getLoanList();

            if (index.getOneBased() > loanList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }

            updateStatus(model, history, loanList.get(index.getZeroBased()));

            loanList.remove(index.getZeroBased());

            LoanListCommand.updateXmlLoanListFile(new XmlAdaptedLoanList(loanList));
        } catch (JAXBException e) {
            System.out.println(e.toString());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
    /**
     * Changes the status from Ready to On_Loan
     */
    private void updateStatus(Model model, CommandHistory history, XmlAdaptedLoanerDescription loanerDescription)
            throws CommandException {
        ChangeStatusCommand.ChangeStatusDescriptor changeStatusDescriptor =
                new ChangeStatusCommand.ChangeStatusDescriptor(new Name(loanerDescription.getItemName()),
                        loanerDescription.getQuantity(), "Ready", "On_Loan");
        ChangeStatusCommand changeStatusCommand = new ChangeStatusCommand(changeStatusDescriptor);
        changeStatusCommand.execute(model, history);
    }
}
