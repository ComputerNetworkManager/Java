package me.cnm.test.shared.utility;

import com.google.gson.JsonSyntaxException;
import me.cnm.impl.shared.utility.UtilityHandler;
import me.cnm.shared.utility.configuration.IConfigurationHandler;
import me.cnm.shared.utility.json.JsonDocument;
import me.cnm.shared.utility.scope.Scopes;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConfigurationTest {

    private static IConfigurationHandler configurationHandler;
    private static JsonDocument oldConfig;

    @BeforeAll
    public static void setup() {
        File configFile = new File(".", "config.json");
        if (configFile.exists()) oldConfig = Scopes.throwRuntime(() -> new JsonDocument(configFile));

        configurationHandler = new UtilityHandler().getConfigurationHandler();
    }

    @AfterAll
    public static void cleanup() {
        File configFile = new File(".", "config.json");
        if (oldConfig == null) {
            //noinspection ResultOfMethodCallIgnored
            configFile.delete();
        }
        else Scopes.throwRuntime(() -> oldConfig.write(configFile));
    }

    @Test
    @Order(1)
    public void testSave() {
        assertDoesNotThrow(() -> {
            configurationHandler.saveEntry("byteTest", (byte) 8);
            configurationHandler.saveEntry("shortTest", (short) 79);
            configurationHandler.saveEntry("intTest", 15);
            configurationHandler.saveEntry("longTest", 79L);
            configurationHandler.saveEntry("floatTest", 7.98f);
            configurationHandler.saveEntry("doubleTest", 19.798);
            configurationHandler.saveEntry("booleanTest", false);
            configurationHandler.saveEntry("charTest", 'c');
            configurationHandler.saveEntry("stringTest", "string");
            configurationHandler.saveEntry("documentTest", new JsonDocument()
                    .append("first", "a")
                    .append("second", 2));
            configurationHandler.saveEntry("overwrite", "5");
            configurationHandler.saveEntry("overwrite", 5);
        });
    }

    @Test
    @Order(2)
    public void testGet() {
        assertEquals((byte) 8, configurationHandler.getEntry("byteTest", byte.class));
        assertEquals((short) 79, configurationHandler.getEntry("shortTest", short.class));
        assertEquals(15, configurationHandler.getEntry("intTest", int.class));
        assertEquals(79L, configurationHandler.getEntry("longTest", long.class));
        assertEquals(7.98f, configurationHandler.getEntry("floatTest", float.class));
        assertEquals(19.798, configurationHandler.getEntry("doubleTest", double.class));
        assertEquals(false, configurationHandler.getEntry("booleanTest", boolean.class));
        assertEquals('c', configurationHandler.getEntry("charTest", char.class));
        assertEquals("string", configurationHandler.getEntry("stringTest", String.class));
        assertEquals(5, configurationHandler.getEntry("overwrite", int.class));

        JsonDocument testDocument = configurationHandler.getEntry("documentTest", JsonDocument.class);
        assertNotNull(testDocument);

        assertEquals("a", testDocument.getString("first"));
        assertEquals(2, testDocument.getInt("second"));

        assertNull(configurationHandler.getEntry("nullTest", String.class));
        assertThrows(JsonSyntaxException.class, () -> configurationHandler.getEntry("stringTest", int.class));
    }

    @Test
    @Order(3)
    public void testGetDef() {
        assertEquals("test", configurationHandler.getEntry("defTest", "test", String.class));
        assertEquals("test", configurationHandler.getEntry("defTest", "abc", String.class));
    }

}
