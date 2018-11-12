package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.addressbook.DayHourGreeting.AFTERNOON;
import static seedu.address.model.addressbook.DayHourGreeting.EVENING;
import static seedu.address.model.addressbook.DayHourGreeting.MORNING;
import static seedu.address.model.addressbook.DayHourGreeting.NIGHT;

import org.junit.Test;

import seedu.address.model.addressbook.DayHourGreeting;

public class DayHourGreetingTest {

    @Test
    public void getGreeting_hourWithinMorning_greetsMorning() {
        DayHourGreeting.setGreeting(Integer.parseInt("00")); //0000hours
        assertEquals(DayHourGreeting.getGreeting(), MORNING);
        DayHourGreeting.setGreeting(Integer.parseInt("11")); //Up to 1159hours
        assertEquals(DayHourGreeting.getGreeting(), MORNING);
    }

    @Test
    public void getGreeting_hourWithinAfternoon_greetsAfternoon() {
        DayHourGreeting.setGreeting(Integer.parseInt("12")); //1200hours
        assertEquals(DayHourGreeting.getGreeting(), AFTERNOON);
        DayHourGreeting.setGreeting(Integer.parseInt("15")); //Up to 1559hours
        assertEquals(DayHourGreeting.getGreeting(), AFTERNOON);
    }

    @Test
    public void getGreeting_hourWithinEvening_greetsEvening() {
        DayHourGreeting.setGreeting(Integer.parseInt("16")); //1600hours
        assertEquals(DayHourGreeting.getGreeting(), EVENING);
        DayHourGreeting.setGreeting(Integer.parseInt("20")); //Up to 2059hours
        assertEquals(DayHourGreeting.getGreeting(), EVENING);
    }

    @Test
    public void getGreeting_hourWithinNight_greetsNight() {
        DayHourGreeting.setGreeting(Integer.parseInt("21")); //2100hours
        assertEquals(DayHourGreeting.getGreeting(), NIGHT);
        DayHourGreeting.setGreeting(Integer.parseInt("23")); //Up to 2359hours
        assertEquals(DayHourGreeting.getGreeting(), NIGHT);
    }
}
