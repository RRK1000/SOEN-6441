package gamelog;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * LogManager is responsible for initializing and managing the logging system
 * It configures the LogEntryBuffer and LogFileWriter to capture and write log entries
 * * @author Yusuke Ishii
 */
public class LogManager {
    private static final LogEntryBuffer d_logBuffer;
    private static final LogFileWriter d_logWriter;

    static {
        Path l_logPath = Paths.get(System.getProperty("user.dir"), "src/main/resources/logs", "gamelog-" + LocalDateTime.now().toString().replaceAll(":", ".") + ".log");
        d_logBuffer = new LogEntryBuffer();
        d_logWriter = new LogFileWriter(l_logPath);
        d_logBuffer.addObserver(d_logWriter);
    }

    /**
     * Logs an action by setting the action information in the log buffer
     * The LogEntryBuffer will notify the LogFileWriter to write the log entry
     *
     * @param p_action The action information to log
     */

    public static void logAction(String p_action) {
        d_logBuffer.setActionInfo(p_action);
    }
}
