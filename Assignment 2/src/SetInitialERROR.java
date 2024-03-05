import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SetInitialERROR extends Exception {
    /**
     * This method throws error for initial time format. Checks if format is valid or invalid.
     * @param value the initial date.
     * @param Time the time in the format of "yyyy-MM-dd_HH:mm:ss".
     * @throws ERROR if the format of the initial date is wrong.
     * @throws ParseException if there is an error while parsing the date string.
     */
    public SetInitialERROR(String value,String Time,String output) throws ERROR, ParseException, IOException {
        readAndWrite write = new readAndWrite();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String[] timePart = Time.split("_");
        String[] timePart1 = timePart[0].split("-");
        String[] timePart2 = timePart[1].split(":");
        int month = Integer.parseInt(timePart1[1]);
        int day = Integer.parseInt(timePart1[2]);
        int hour = Integer.parseInt(timePart2[0]);
        int minute = Integer.parseInt(timePart2[1]);
        int second = Integer.parseInt(timePart1[2]);
        if (minute < 0 || minute > 59){
            write.writeInput(output,"ERROR: Format of the initial date is wrong! Program is going to terminate!");
            System.exit(0);

        }if (day < 1 || day > 31){
            write.writeInput(output,"ERROR: Format of the initial date is wrong! Program is going to terminate!");
            System.exit(0);

        }if (hour < 0 || hour > 23){
            write.writeInput(output,"ERROR: Format of the initial date is wrong! Program is going to terminate!");
            System.exit(0);

        }if (second < 0 || second > 59){
            write.writeInput(output,"ERROR: Format of the initial date is wrong! Program is going to terminate!");
            System.exit(0);

        }if (month < 1 || month > 12){
            write.writeInput(output,"ERROR: Format of the initial date is wrong! Program is going to terminate!");
            System.exit(0);
        }

    }
}
