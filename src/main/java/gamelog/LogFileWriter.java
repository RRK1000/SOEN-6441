package gamelog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Shows a writer that writes log entries to a file
 * @author Yusuke Ishii
 */
public class LogFileWriter implements Observer {
    private Path d_logFilePath;



    /**
     * Constructor for LogFileWriter.
     * 
     * @param p_logFilePath The complete path to the log file.
     */
    public LogFileWriter(Path p_logFilePath) {
        this.d_logFilePath = p_logFilePath;
    }

    /**
     * Writes the log info to the game log file
     * @param p_observable Observable object
     * @param p_arg Argument object
     */
    @Override
    public void update(Observable p_observable, Object p_arg) {
        if (p_observable instanceof LogEntryBuffer) {
            String l_logInfo = ((LogEntryBuffer) p_observable).getActionInfo();
            writeToLogFile(l_logInfo);
        }
    }

    /**
     * Writes the given info to the log file.
     *
     * @param p_info The info to write.
     */
    private void writeToLogFile(String p_info) {
        try {
            Files.write(d_logFilePath, (p_info + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException l_e) {
            System.err.println("Error writing to log file: " + l_e.getMessage());
        }
    }
}
