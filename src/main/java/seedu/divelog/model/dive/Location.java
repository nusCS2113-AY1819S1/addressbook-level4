package seedu.divelog.model.dive;

/**
 * @author arjo
 * Stores location information
 */
public class Location {
    private final String locationName;

    public Location(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location)) {
            return false;
        }
        Location other = (Location) obj;
        return other.getLocationName().equals(locationName);
    }
}
