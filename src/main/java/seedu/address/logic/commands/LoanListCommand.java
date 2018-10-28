package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Loaner;
import seedu.address.storage.XmlAdaptedLoaner;

public class LoanListCommand extends Command {
    public static final String COMMAND_WORD = "loanList";
    private JAXBContext context;
    private final Loaner loaner;

    public LoanListCommand(Loaner loaner) {
        this.loaner = loaner;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            this.context = JAXBContext.newInstance(XmlAdaptedLoaner.class);
            Marshaller marshaller = this.context.createMarshaller();
            marshaller.marshal(new XmlAdaptedLoaner(loaner), new File("LoanList.xml"));
        }
        catch (JAXBException e) {
            System.out.println("GG");
        }

        return new CommandResult("SUPP");
    }
}
