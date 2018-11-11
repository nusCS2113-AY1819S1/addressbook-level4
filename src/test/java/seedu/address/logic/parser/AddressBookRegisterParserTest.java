package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.RegisterCommand;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookRegisterParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_register() throws Exception {
        Person person = new PersonBuilder().build();
        parser.parseCommand(PersonUtil.getRegisterCommand(person));
        RegisterCommand command = (RegisterCommand) parser.parseCommandArguments();
        assertEquals(new RegisterCommand(person), command);
    }

    @Test
    public void parseCommandAlias_register() throws Exception {
        Person person = new PersonBuilder().build();
        parser.parseCommand(PersonUtil.getRegisterCommandAlias(person));
        RegisterCommand command = (RegisterCommand) parser.parseCommandArguments();
        assertEquals(new RegisterCommand(person), command);
    }
}
