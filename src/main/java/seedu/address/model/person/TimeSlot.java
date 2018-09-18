package seedu.address.model.person;

/**
 * Represents a single hour in TimeTable Class
 *
 */
public class TimeSlot {

    private boolean isFilled;
    private String modName;

    public TimeSlot() {
        this.isFilled = false;
        this.modName = "";
    }

    public void setIsFilled(){
        this.isFilled  = true;
    }

    public boolean getIsFilled(){
        return isFilled;
    }

    public void removeIsFilled(){
        this.isFilled = false;
    }

    public void setModName(String Name){
        modName = Name;
    }

    public String getModName(){
        return modName;
    }
}
