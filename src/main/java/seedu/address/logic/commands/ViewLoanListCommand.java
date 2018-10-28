package seedu.address.logic.commands;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.XmlAdaptedLoanList;
import seedu.address.storage.XmlAdaptedLoanerDescription;

public class ViewLoanListCommand extends Command{
    public static final String COMMAND_WORD = "viewLoanList";
    public static final String MESSAGE_SUCCESS = "Loan list displayed";
    public static final String MESSAGE_EMPTY = "Loan list is currently empty";

    @Override
        public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        File loanListFile = new File("C:/Users/ckinw/OneDrive/Documents/JalilEnterprisesCKW/data/LoanList.xml");
        if(!loanListFile.exists()) {
            throw new CommandException(MESSAGE_EMPTY);
        }
        String messageOutput = getMessageOutput();
            return new CommandResult(messageOutput);
        }
        private String getMessageOutput() {
            try {
                String messageOutput = new String();
                messageOutput += MESSAGE_SUCCESS + "\n";
                JAXBContext context = JAXBContext.newInstance(XmlAdaptedLoanList.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                XmlAdaptedLoanList xmlAdaptedLoanList = (XmlAdaptedLoanList) unmarshaller
                        .unmarshal(new File("C:/Users/ckinw/OneDrive/Documents/JalilEnterprisesCKW/data/LoanList.xml"));
                for(XmlAdaptedLoanerDescription loanerDescription : xmlAdaptedLoanList.getLoanList()) {
                    messageOutput += loanerDescription.getLoanerName() + ": ";
                    messageOutput += loanerDescription.getItemName() + " ";
                    messageOutput += loanerDescription.getQuantity() + "\n";
                }
                return messageOutput;
            }
            catch (JAXBException e) {
                System.out.println(e.toString());
            }
            return null;
        }
}
