package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ExpensesCardHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.RecruitmentCardHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.ScheduleCardHandle;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.person.Person;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.schedule.Schedule;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEqualsSchedule(ScheduleCardHandle expectedCard, ScheduleCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getEmployeeId(), actualCard.getEmployeeId());
        assertEquals(expectedCard.getType(), actualCard.getType());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEqualsRecruitment(RecruitmentCardHandle expectedCard,
                RecruitmentCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getPost(), actualCard.getPost());
        assertEquals(expectedCard.getWorkExp(), actualCard.getWorkExp());
        assertEquals(expectedCard.getJobDescription(), actualCard.getJobDescription());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEqualsExpenses(ExpensesCardHandle expectedCard, ExpensesCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getEmployeeId(), actualCard.getEmployeeId());
        assertEquals(expectedCard.getExpensesamount(), actualCard.getExpensesamount());
        assertEquals(expectedCard.getTravelexpenses(), actualCard.getTravelexpenses());
        assertEquals(expectedCard.getMedicalexpenses(), actualCard.getMedicalexpenses());
        assertEquals(expectedCard.getMiscellaneousexpenses(), actualCard.getMiscellaneousexpenses());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getEmployeeId().value, actualCard.getEmployeeId());
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getDateOfBirth().value, actualCard.getDateOfBirth());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getDepartment().value, actualCard.getDepartment());
        assertEquals(expectedPerson.getPosition().value, actualCard.getPosition());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getSalary().value, actualCard.getSalary());
        assertEquals(expectedPerson.getBonus().value, actualCard.getBonus());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedSchedule}.
     */
    public static void assertCardDisplaysSchedule(Schedule expectedSchedule, ScheduleCardHandle actualCard) {
        assertEquals(expectedSchedule.getEmployeeId().value, actualCard.getEmployeeId());
        assertEquals(expectedSchedule.getScheduleDate().value, actualCard.getDate());
        assertEquals(expectedSchedule.getType().value, expectedSchedule.getType().value);
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRecruitment}.
     */
    public static void assertCardDisplaysRecruitment(Recruitment expectedRecruitment,
                RecruitmentCardHandle actualCard) {
        assertEquals(expectedRecruitment.getPost().value, actualCard.getPost());
        assertEquals(expectedRecruitment.getWorkExp().workExp + " years", actualCard.getWorkExp());
        assertEquals(expectedRecruitment.getJobDescription().value, actualCard.getJobDescription());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysExpenses(Expenses expectedExpenses, ExpensesCardHandle actualCard) {
        assertEquals(expectedExpenses.getEmployeeId().value, actualCard.getEmployeeId());
        assertEquals(expectedExpenses.getExpensesAmount().expensesAmount, actualCard.getExpensesamount());
        assertEquals(expectedExpenses.getTravelExpenses().travelExpenses, actualCard.getTravelexpenses());
        assertEquals(expectedExpenses.getMedicalExpenses().medicalExpenses, actualCard.getMedicalexpenses());
        assertEquals(expectedExpenses.getMiscellaneousExpenses().miscellaneousExpenses, actualCard
                .getMiscellaneousexpenses());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
