package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MODIFIED_PAY_OVERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BONUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;

/**
 *  Modify the salary and bonus of an employee's in CHRS
 */
public class ModifyAllPayCommand extends Command {

    public static final String COMMAND_WORD = "modifyAllPay";
    public static final String COMMAND_ALIAS = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Modify the pay of all the employee(s) "
            + "shown in displayed list. "
            + "Existing salary will be updated based on the user input.\n"
            + "Parameters: "
            + PREFIX_SALARY + "[INCREASE AMOUNT]"
            + " OR "
            + PREFIX_SALARY + "%[PERCENTAGE INCREASE] AND/OR "
            + PREFIX_BONUS + "[MONTH(S) OF SALARY FOR BONUS]\n"
            + "Example 1: " + COMMAND_WORD + " "
            + PREFIX_SALARY + "300 "
            + PREFIX_BONUS + "2\n"
            + "Example 2: " + COMMAND_WORD + " "
            + PREFIX_SALARY + "%10 "
            + PREFIX_BONUS + "2";

    public static final String MESSAGE_NEGATIVE_PAY = "Pay are not allowed to be zero or negative in value";
    public static final String MESSAGE_NOT_MODIFIED = "Employee's pay not modified yet. "
            + "Min of 1 field "
            + PREFIX_SALARY
            + " AND/OR "
            + PREFIX_BONUS
            + " must be provided";
    private static final double LIMIT = 0.0;
    private static final double PERCENT = 100.0;
    private static final String OUTPUT_FORMAT = "#0.00";
    private final ModSalaryDescriptor modSalaryDescriptor;

    /**
     *
     * @param modSalaryDescriptor details to modify the person with.
     */

    public ModifyAllPayCommand(ModSalaryDescriptor modSalaryDescriptor) {
        requireNonNull(modSalaryDescriptor);

        this.modSalaryDescriptor = new ModSalaryDescriptor(modSalaryDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException, ParseException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> modifiedList = new ArrayList<>();
        List<Person> newList = new ArrayList<>();

        for (Person person : lastShownList) {
            Person modifiedPerson = createModifiedPerson(person, modSalaryDescriptor);

            newList.add(person);
            modifiedList.add(modifiedPerson);
        }

        for (int i = 0; i < newList.size(); i++) {
            model.updatePerson(newList.get(i), modifiedList.get(i));
        }

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_MODIFIED_PAY_OVERVIEW, lastShownList.size()));
    }

    /**
     * Creates and returns a boolean with the details of {@code salary}
     */
    private static boolean isNegative (double salary) {
        return salary <= LIMIT;
    }
    /**
     * Creates and returns a new String of Salary with the details of {@code personToEdit}
     * edited with {@code modSalaryDescriptor}.
     */
    private static double addSalaryAmount (Person personToEdit, ModSalaryDescriptor modSalaryDescriptor) {
        String newSalary = personToEdit.getSalary().toString();
        double payOut = Double.parseDouble(newSalary);
        String change = modSalaryDescriptor.getSalary().toString().replaceAll("[^0-9.-]", "");
        payOut += Double.parseDouble(change);

        return payOut;
    }

    /**
     * Creates and returns a new String of Salary with the details of {@code personToEdit}
     * edited with {@code modSalaryDescriptor}.
     */
    private static double modifySalaryPercent (Person personToEdit, ModSalaryDescriptor modSalaryDescriptor) {
        String newSalary = personToEdit.getSalary().toString();
        double payOut = Double.parseDouble(newSalary);
        String change = modSalaryDescriptor.getSalary().toString().replaceAll("[^0-9.-]", "");
        payOut += Math.abs(payOut) * (Double.parseDouble(change) / PERCENT);

        return payOut;
    }

    /**
     * Creates and returns a new String of Salary with the functions modifySalaryPercent and addSalaryAmount
     * details of {@code personToEdit}
     * edited with {@code modSalaryDescriptor}.
     */
    private static String typeOfSalaryMod (Person personToEdit, ModSalaryDescriptor modSalaryDescriptor)
        throws CommandException {
        String newSalary = personToEdit.getSalary().toString();
        NumberFormat formatter = new DecimalFormat(OUTPUT_FORMAT);
        double payOut = Double.parseDouble(newSalary);

        if (!modSalaryDescriptor.getSalary().equals(Optional.empty())) {
            String change = modSalaryDescriptor.getSalary().toString();
            char type = change.charAt(9);

            if (type == '%') {
                payOut = modifySalaryPercent(personToEdit, modSalaryDescriptor);
            } else {
                payOut = addSalaryAmount(personToEdit, modSalaryDescriptor);
            }
        }

        if (isNegative(payOut)) {
            throw new CommandException(MESSAGE_NEGATIVE_PAY);
        }

        newSalary = String.valueOf(formatter.format(payOut));

        return newSalary;
    }

    /**
     * Creates and returns a new String value of Bonus with the details of {@code personToEdit}
     * edited with {@code modSalaryDescriptor}.
     */
    private static String modifyBonusMonth (Person personToEdit, ModSalaryDescriptor modSalaryDescriptor,
                                            Salary newSalary) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String bonus = personToEdit.getBonus().toString();
        double currentSalary = Double.parseDouble(newSalary.toString());
        if (!modSalaryDescriptor.getBonus().equals(Optional.empty())) {
            String bonusMonth = modSalaryDescriptor.getBonus().toString().replaceAll("[^0-9.]", "");
            double payOut = currentSalary * Double.parseDouble(bonusMonth);
            bonus = String.valueOf(formatter.format(payOut));
        }
        return bonus;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code modSalaryDescriptor}.
     */
    private static Person createModifiedPerson(Person personToEdit,
                               ModSalaryDescriptor modSalaryDescriptor) throws ParseException, CommandException {
        assert personToEdit != null;

        EmployeeId updatedEmployeeId = personToEdit.getEmployeeId();
        Name updatedName = personToEdit.getName();
        DateOfBirth updatedDateOfBirth = personToEdit.getDateOfBirth();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Department updatedDepartment = personToEdit.getDepartment();
        Position updatedPosition = personToEdit.getPosition();
        Address updatedAddress = personToEdit.getAddress();
        Salary updatedSalary = ParserUtil.parseSalary(typeOfSalaryMod(personToEdit, modSalaryDescriptor));
        Bonus updatedBonus = ParserUtil.parseBonus(modifyBonusMonth(personToEdit, modSalaryDescriptor, updatedSalary));
        Set<Tag> updatedTags = personToEdit.getTags();

        return new Person(updatedEmployeeId, updatedName, updatedDateOfBirth, updatedPhone, updatedEmail,
                updatedDepartment, updatedPosition, updatedAddress, updatedSalary, updatedBonus, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModifyAllPayCommand)) {
            return false;
        }

        // state check
        ModifyAllPayCommand m = (ModifyAllPayCommand) other;
        return modSalaryDescriptor.equals(m.modSalaryDescriptor);
    }

    /**
     * Stores the details to modify the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class ModSalaryDescriptor {
        private Salary salary;
        private Bonus bonus;

        public ModSalaryDescriptor() {}
        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */

        public ModSalaryDescriptor(ModSalaryDescriptor toCopy) {
            setSalary(toCopy.salary);
            setBonus(toCopy.bonus);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(salary, bonus);
        }

        public void setSalary(Salary salary) {
            this.salary = salary;
        }

        public Optional<Salary> getSalary() {
            return Optional.ofNullable(salary);
        }

        public void setBonus(Bonus bonus) {
            this.bonus = bonus;
        }

        public Optional<Bonus> getBonus() {
            return Optional.ofNullable(bonus);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ModSalaryDescriptor)) {
                return false;
            }

            // state check
            ModSalaryDescriptor m = (ModSalaryDescriptor) other;

            return getSalary().equals(m.getSalary())
                    && getBonus().equals(m.getBonus());
        }
    }
}
