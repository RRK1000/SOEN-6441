package gamelog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Shows a writer that writes log entries to a file
 * @author Yusuke Ishii
 */
public class LogFileWriter implements Observer {
    private String d_logFileName;

    /**
     * Constructor for LogFileWriter.
     *
     * @param p_fileName The name of the log file.
     */
    public LogFileWriter(String p_fileName) {
        this.d_logFileName = p_fileName;
    }

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
        Path l_path = Paths.get("/risk/src/main/resources", d_logFileName);
        try {
            Files.write(l_path, (p_info + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException l_e) {
            System.err.println("Error writing to log file: " + l_e.getMessage());
        }
    }
}
