package vm_manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Executes commands on terminal.
 */
public class ExecuteCommand {
    /**
     * Execute given command in terminal.
     *
     * @param command   Command to be executed in terminal.
     * @return          Output from terminal.
     */
    public static String exec(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            System.err.println("The process was interrupted!");
        }

        return output.toString();
    }
}
