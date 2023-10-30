package gamelog;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the LogFileWriter class
 * @author Yusuke Ishii
 */
public class LogFileWriterTest {

    // Global constant for the log directory path
    private static final Path LOG_DIR_PATH = Paths.get(System.getProperty("user.dir"), "src/test/resources");

    private LogFileWriter d_logFileWriter;
    private LogEntryBuffer d_logEntryBuffer;
    private String d_testFileName = "test.log";

    /**
     * Setup method to initialize necessary objects before each test
     */
    @BeforeEach
    public void setUp() {
        Path l_logFilePath = LOG_DIR_PATH.resolve(d_testFileName);
        d_logFileWriter = new LogFileWriter(l_logFilePath);
        d_logEntryBuffer = new LogEntryBuffer();
        d_logEntryBuffer.addObserver(d_logFileWriter);
    }

    /**
     * Tests the update functionality of the LogFileWriter
     * @throws IOException if there's an error reading the log file
     */
    @Test
    public void testUpdate() throws IOException {
        String l_testLogEntry = "Test Log Entry :D";

        d_logEntryBuffer.setActionInfo(l_testLogEntry);

        Path l_logFilePath = LOG_DIR_PATH.resolve(d_testFileName);
        String l_logFileContent = new String(Files.readAllBytes(l_logFilePath));

        assertTrue(l_logFileContent.contains(l_testLogEntry), "The log file does not contain the expected log entry.");
    }
}
