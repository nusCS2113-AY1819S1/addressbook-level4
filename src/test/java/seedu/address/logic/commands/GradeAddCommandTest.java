package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.grades.GradesManager;
import seedu.address.testutil.GradeBuilder;

public class GradeAddCommandTest {
    private static GradesManager gradesManager = new GradesManager();
    private static GradeBuilder dummyGrade = new GradeBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        gradesManager.clearGrade();
        gradesManager.saveGradeList();
    }

    @Test
    public void constructor_nullGradebook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new GradebookAddCommand(null);
    }
}
