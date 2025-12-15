package app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Main.
 * Tests the application entry point.
 */
class MainTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void mainExecutesWithoutException() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    void mainPrintsExpectedMessages() {
        Main.main(new String[]{});

        String output = outputStream.toString();
        assert output.contains("BiCIAM");
        assert output.contains("contenedor Docker");
        assert output.contains("funcionando correctamente");
    }
}
