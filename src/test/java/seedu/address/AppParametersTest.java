package seedu.address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import javafx.application.Application;

public class AppParametersTest {

    private final ParametersStub parametersStub = new ParametersStub();
    private final AppParameters expected = new AppParameters();

    @Test
    public void parse_validConfigPath_success() {
        parametersStub.namedParameters.put("config", "config.json");
        expected.setConfigPath(Paths.get("config.json"));
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_nullConfigPath_success() {
        parametersStub.namedParameters.put("config", null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_invalidConfigPath_success() {
        parametersStub.namedParameters.put("config", "a\0");
        expected.setConfigPath(null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    private static class ParametersStub extends Application.Parameters {
        private Map<String, String> namedParameters = new HashMap<>();

        @Override
        public List<String> getRaw() {
            throw new AssertionError("should not be called");
        }

        @Override
        public List<String> getUnnamed() {
            throw new AssertionError("should not be called");
        }

        @Override
        public Map<String, String> getNamed() {
            return Collections.unmodifiableMap(namedParameters);
        }
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(expected.equals(expected));

        // null -> returns false
        assertFalse(expected.equals(null));

        // equal path -> return true
        AppParameters anotherApp = new AppParameters();
        parametersStub.namedParameters.put("config", "config2.json");
        anotherApp.setConfigPath(Paths.get("config2.json"));
        assertFalse(anotherApp.equals(expected));
    }

    @Test
    public void hashCode_validAppParameters_correctHashCodeRepresentation() {
        AppParameters anotherApp = new AppParameters();
        anotherApp.setConfigPath(Paths.get("config2.json"));
        Path configPath = Paths.get("config2.json");
        assertEquals(anotherApp.hashCode(), configPath.hashCode());
    }

}
