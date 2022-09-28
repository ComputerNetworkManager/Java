package me.cnm.test.shared.utility;

import me.cnm.shared.utility.scope.Scopes;
import me.cnm.shared.utility.scope.fi.ThrowSupplier;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScopeTest {

    @Test
    @Order(1)
    public void testScope() {
        assertNull(Scopes.ifNotNull(null, value -> "test"));
        assertArrayEquals(new String[]{"te", "st"}, Scopes.ifNotNull("te st", value -> value.split(" ")));

        assertDoesNotThrow(() -> Scopes.throwRuntime(this::emptyRunnable));
        assertThrows(RuntimeException.class, () -> Scopes.throwRuntime(() -> {
            throw new IOException("test");
        }));

        assertDoesNotThrow(() -> Scopes.throwRuntime(() -> "abc"));
        assertThrows(RuntimeException.class, () -> Scopes.throwRuntime((ThrowSupplier<String>) () -> {
            throw new IOException("test");
        }));
    }

    private void emptyRunnable() {

    }

}
