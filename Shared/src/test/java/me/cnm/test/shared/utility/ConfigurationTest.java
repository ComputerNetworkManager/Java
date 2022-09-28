package me.cnm.test.shared.utility;

import me.cnm.impl.shared.utility.UtilityHandler;
import me.cnm.shared.utility.IUtilityHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigurationTest {

    private static IUtilityHandler utilityHandler;

    @BeforeClass
    public static void setup() {
        utilityHandler = new UtilityHandler();
    }

    @Test
    public void saveValues() {

    }

}
