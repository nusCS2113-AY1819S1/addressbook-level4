package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LOANER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import seedu.address.MainApp;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.LoanerDescription;
import seedu.address.storage.XmlAdaptedLoanList;
import seedu.address.storage.XmlAdaptedLoanerDescription;

/**
 * Creates a loan list, and updates the status from Ready to On_Loan
 */

public class LoanListCommand extends Command {
    public static final String COMMAND_WORD = "loanList";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a loan list. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_LOANER + "LOANER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Arduino "
            + PREFIX_QUANTITY + "20 "
            + PREFIX_LOANER + "KinWhye";

    public static final String MESSAGE_SUCCESS = "Loan list created";

    private final LoanerDescription loaner;

    public LoanListCommand(LoanerDescription loaner) {
        this.loaner = loaner;
    }
    /**
     * Updates the XmlLoanListFile
     */

    public static void updateXmlLoanListFile(XmlAdaptedLoanList xmlAdaptedLoanList) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlAdaptedLoanList.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(xmlAdaptedLoanList, System.out);
        jaxbMarshaller.marshal(xmlAdaptedLoanList, MainApp.getLoanListFile());
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        updateStatus(model, history);
        try {
            updateLoanList();
        } catch (JAXBException e) {
            System.out.println(e.toString());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
    /**
     * Updates the XmlAdaptedLoanList, then updates the XmlLoanListFile
     */
    private void updateLoanList() throws JAXBException {
        XmlAdaptedLoanerDescription toAdd = new XmlAdaptedLoanerDescription(loaner);
        JAXBContext context = JAXBContext.newInstance(XmlAdaptedLoanList.class);
        XmlAdaptedLoanList xmlAdaptedLoanList = new XmlAdaptedLoanList();
        if (MainApp.getLoanListFile().exists()) {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            xmlAdaptedLoanList = (XmlAdaptedLoanList) unmarshaller
                    .unmarshal(MainApp.getLoanListFile());
        }
        xmlAdaptedLoanList.addLoaner(toAdd);
        updateXmlLoanListFile(xmlAdaptedLoanList);
    }
    /**
     * Changes the status from On_Loan to Ready
     */
    private void updateStatus(Model model, CommandHistory history) throws CommandException {
        ChangeStatusCommand.ChangeStatusDescriptor changeStatusDescriptor =
                new ChangeStatusCommand.ChangeStatusDescriptor(loaner.getItemName(),
                        loaner.getQuantity().toInteger(), "Ready", "On_Loan");
        ChangeStatusCommand changeStatusCommand = new ChangeStatusCommand(changeStatusDescriptor);
        changeStatusCommand.execute(model, history);
    }
}
