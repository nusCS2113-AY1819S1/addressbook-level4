package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysRecruitment;

import org.junit.Test;

import guitests.guihandles.RecruitmentCardHandle;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.testutil.recruitment.RecruitmentBuilder;

public class RecruitmentCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Recruitment recruitment = new RecruitmentBuilder().build();
        RecruitmentCard recruitmentCard = new RecruitmentCard(recruitment, 1);
        uiPartRule.setUiPart(recruitmentCard);
        assertCardDisplay(recruitmentCard, recruitment, 1);
    }

    @Test
    public void equals() {
        Recruitment recruitment = new RecruitmentBuilder().build();
        RecruitmentCard recruitmentCard = new RecruitmentCard(recruitment, 0);

        // same recruitment, same index -> returns true
        RecruitmentCard copy = new RecruitmentCard(recruitment, 0);
        assertTrue(recruitmentCard.equals(copy));

        // same object -> returns true
        assertTrue(recruitmentCard.equals(recruitmentCard));

        // null -> returns false
        assertFalse(recruitmentCard == null);

        // different types -> returns false
        assertFalse(recruitmentCard.equals(0));

        // different post, same index -> returns false
        Recruitment differentRecruitment = new RecruitmentBuilder().withPost("Engineer").build();
        assertFalse(recruitmentCard.equals(new RecruitmentCard(differentRecruitment, 0)));

        // same recruitment, different index -> returns false
        assertFalse(recruitment.equals(new RecruitmentCard(recruitment, 1)));
    }

    /**
     * Asserts that {@code recruitmentCard} displays the details of {@code expectedRecruitment} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(RecruitmentCard recruitmentCard, Recruitment expectedRecruitment, int expectedId) {
        guiRobot.pauseForHuman();

        RecruitmentCardHandle recruitmentCardHandle = new RecruitmentCardHandle(recruitmentCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", recruitmentCardHandle.getId());

        // verify recruitment details are displayed correctly
        assertCardDisplaysRecruitment(expectedRecruitment, recruitmentCardHandle);
    }
}
