package me.cnm.test.shared.utility;

import me.cnm.impl.shared.utility.UtilityHandler;
import me.cnm.shared.utility.format.IFormatHandler;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FormatTest {

    private static IFormatHandler formatHandler;
    private static boolean deleteConfig;

    @BeforeAll
    public static void setup() {
        File configFile = new File(".", "config.json");
        deleteConfig = !configFile.exists();

        formatHandler = new UtilityHandler().getFormatHandler();
    }

    @AfterAll
    public static void cleanup() {
        if (deleteConfig) {
            //noinspection ResultOfMethodCallIgnored
            new File(".", "config.json").delete();
        }
    }

    @Test
    @Order(1)
    public void testNumbers() {
        assertEquals(15.12, formatHandler.formatDouble(15.1223));
        assertEquals(15.1, formatHandler.formatDouble(15.0879, 1));
        assertEquals("1 byte", formatHandler.formatBytes(1));
        assertEquals("6 bytes", formatHandler.formatBytes(6));
        assertEquals("160,93 KiB", formatHandler.formatBytes(164797));
        assertEquals("1 byte", formatHandler.formatBytesDecimal(1));
        assertEquals("46 bytes", formatHandler.formatBytesDecimal(46));
        assertEquals("5,77 GB", formatHandler.formatBytesDecimal(5_768_786_687L));
        assertEquals("115 Hz", formatHandler.formatValue(115, "Hz"));
        assertEquals("487,65 THz", formatHandler.formatValue(487_654_746_987_869L, "Hz"));
    }

    @Test
    @Order(2)
    public void testTime() {
        assertEquals("29 11 359 17 10937 16 262494 20 15749660 41 944979641",
                formatHandler.formatTimeDiff(0, 944979641349L,
                        "%y %mo %fmo %d %fd %h %fh %m %fm %s %fs"));
        assertEquals("0, 0:0:15", formatHandler.formatElapsedSecs(15));
    }

}
