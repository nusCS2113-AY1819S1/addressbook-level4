package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LOANER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.io.File;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.LoanerDescription;
import seedu.address.storage.XmlAdaptedLoanList;
import seedu.address.storage.XmlAdaptedLoanerDescription;

public class LoanListCommand extends Command {
    public static final String COMMAND_WORD = "loanList";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a loan list. "
            + "Parameters: "
            + PREFIX_LOANER + "LOANER"
            + PREFIX_NAME + "NAME "
            + PREFIX_QUANTITY + "QUANTITY"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LOANER + "KinWhye"
            + PREFIX_NAME + "Arduino "
            + PREFIX_QUANTITY + "20 ";
    public static final String MESSAGE_SUCCESS = "Loan list created";

    private final LoanerDescription loaner;

    public LoanListCommand(LoanerDescription loaner) {
        this.loaner = loaner;
    }

    public static void addToFile(XmlAdaptedLoanList xmlAdaptedLoanList) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlAdaptedLoanList.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(xmlAdaptedLoanList, System.out);
        jaxbMarshaller.marshal(xmlAdaptedLoanList, new File("C:/Users/ckinw/OneDrive/Documents/JalilEnterprisesCKW/data/LoanList.xml"));
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        updateStatus(model, history);
        try {
            updateLoanList();
        }
        catch (JAXBException e) {
            System.out.println(e.toString());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    private void updateLoanList() throws JAXBException{
        File loanListFile = new File("C:/Users/ckinw/OneDrive/Documents/JalilEnterprisesCKW/data/LoanList.xml");
        XmlAdaptedLoanerDescription toAdd = new XmlAdaptedLoanerDescription(loaner);
        JAXBContext context = JAXBContext.newInstance(XmlAdaptedLoanList.class);

        if(loanListFile.exists()) {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XmlAdaptedLoanList xmlAdaptedLoanList = (XmlAdaptedLoanList) unmarshaller
                    .unmarshal(loanListFile);
            xmlAdaptedLoanList.addLoaner(toAdd);
            addToFile(xmlAdaptedLoanList);
        }
        else {
            XmlAdaptedLoanList xmlAdaptedLoanList = new XmlAdaptedLoanList();
            xmlAdaptedLoanList.addLoaner(toAdd);
            addToFile(xmlAdaptedLoanList);
        }
    }

    private void updateStatus(Model model, CommandHistory history) throws CommandException {
        ChangeStatusCommand.ChangeStatusDescriptor changeStatusDescriptor = new ChangeStatusCommand.ChangeStatusDescriptor();
        changeStatusDescriptor.setName(loaner.getItemName());
        changeStatusDescriptor.setQuantity(loaner.getQuantity().toInteger());
        changeStatusDescriptor.setInitialStatus("Ready");
        changeStatusDescriptor.setUpdatedStatus("On_Loan");
        ChangeStatusCommand changeStatusCommand = new ChangeStatusCommand(changeStatusDescriptor);
        changeStatusCommand.execute(model, history);
    }
}
