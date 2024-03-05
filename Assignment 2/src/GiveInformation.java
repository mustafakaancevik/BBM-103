import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GiveInformation {
    readAndWrite write = new readAndWrite();

    /**
     * This method write smart devices information using toString method.
     * @param Devices Array list of contains smart devices.
     * @param time current date and time.
     * @param output the output to write txt.
     * @throws IOException  if an error occurs when writing to the output file.
     */
    public void GiveInformation(ArrayList<SmartDevices> Devices, Date time,String output) throws ParseException, IOException {
        timePrint(time,output);

        for (SmartDevices smartDevices:Devices){
            write.writeInput(output,smartDevices.toString());
        }

    }

    /**
     * This method write one smart device information using toString method.
     * @param smartDevices one of the smart devices.
     * @param output the output to write txt.
     * @throws IOException if an error occurs when writing to the output file.
     */
    public void oneDeviceInformation(SmartDevices smartDevices,String output) throws  IOException {
        write.writeInput(output,smartDevices.toString());

    }

    /**
     * This method print the date and time to txt when is needed.
     * @param time current date and time.
     * @param output the output to write txt.
     * @throws ParseException if an error occurs when parsing.
     * @throws IOException if an error occurs when writing to the output file.
     */
    public void timePrint(Date time,String output) throws ParseException, IOException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("TRT");
        inputFormat.setTimeZone(timeZone);
        Date date = inputFormat.parse(String.valueOf(time));

        String formattedDate = outputFormat.format(date);
        write.writeInput(output,"Time is:\t"+formattedDate);
    }

    /**
     * This method changes the date format from "EEE MMM dd HH:mm:ss zzz yyyy" to "yyyy-MM-dd_HH:mm:ss".
     * @param time current date and time.
     * @return current date in formatted type.
     * @throws ParseException if and error occurs when parsing.
     */
    public String returnFormattedTime(Date time) throws ParseException {
        if (time == null){
            return null;
        }else {
            SimpleDateFormat inputFormat
                    = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("TRT");
            inputFormat.setTimeZone(timeZone);
            Date date = inputFormat.parse(String.valueOf(time));

            String  formattedDate = outputFormat.format(date);
            return formattedDate;

        }

    }

}


