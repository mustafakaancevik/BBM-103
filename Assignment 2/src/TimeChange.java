import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeChange {
    private Date time;
    readAndWrite write = new readAndWrite();
    GiveInformation information = new GiveInformation();

    /**
     * This method set Initial Time for our code. Also checked this command works one time in all code. Also checked
     * if format is valid or invalid.
     * @param initialTime current time and date.
     * @param output the output to write txt.
     * @return updated time.
     * @throws ERROR if initial time is null.
     */
    Date setInitialTime(String[] initialTime,String output) throws ERROR {
        if (time != null){
            throw new ERROR("Erroneous command!");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        try {
            if (initialTime.length == 2){
                Date time = dateFormat.parse(initialTime[1]);
                SetInitialERROR setInitialERROR = new SetInitialERROR("Format of the initial date is wrong! Program is going to terminate!",initialTime[1],output);
                write.writeInput(output,"SUCCESS: Time has been set to "+information.returnFormattedTime(time) +"!");
                this.time = time;
                return time;
            }else {
                write.writeInput(output,"ERROR: First command must be set initial time! Program is going to terminate!");
                System.exit(0);
            }
        } catch (ParseException e) {
            System.out.println("Invalid input format");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * This method advances the time by the desired minute.
     * @param row an Array of String containing time information that desired skip minutes.
     * @return updated time.
     * @throws ERROR if desired skip minutes is not positive or zero.
     */
    Date skipMinutes(String[] row) throws ERROR {
        int minutesToAdd = Integer.parseInt(row[1]);
        if (row.length != 2){
            throw new ERROR("Erroneous command!");
        }
        if (minutesToAdd<0){
            throw new ERROR("Time cannot be reversed!");
        } else if (minutesToAdd == 0) {
            throw new ERROR("There is nothing to skip!");

        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MINUTE, minutesToAdd);
        this.time = calendar.getTime();
        return time;
    }

    /**
     * Sets a new time for smart devices objects.
     * @param setTime desired time and date.
     * @return new time.
     * @throws ERROR If the input time is before the current time or same as the current time.
     */
    Date setTime(String[] setTime) throws ERROR {

        try {
            if (setTime.length != 2){
                throw new ERROR("Erroneous command!");
            }
            Date temptime = correctDate(setTime[1]);
            if (temptime.before(time)){
                throw new ERROR("Time cannot be reversed!");
            } else if (temptime.after(time)) {
                this.time = temptime;
                return time;
            }else {
                throw new ERROR("There is nothing to change!");
            }
        } catch (ParseException e) {
            System.out.println("Invalid input format");
        }
        return null;
    }

    /**
     * Sets the switch time for a Smart Device in a list of Smart Devices.
     * @param smartDevices An ArrayList of SmartDevices that contains the Smart Device.
     * @param row A String array representing the row from a txt file that contains the name of
     * the Smart Device and the switch time.
     * @return An ArrayList of SmartDevices with the changed Smart Device.
     * @throws ParseException If the switch time in the row is not in the correct format.
     * @throws ERROR if there is no such a device.
     */
    public ArrayList<SmartDevices> setSwitchTime(ArrayList<SmartDevices> smartDevices, String[] row,Date time) throws ParseException, ERROR {
        boolean isNotHere = true;
        changeDevice changeDevice = new changeDevice();
        SmartDevices device = new SmartDevices();
        if (row.length != 3){
            throw new ERROR("Erroneous command!");
        }
        for (int j = 0; j < smartDevices.size(); j++) {
            if (smartDevices.get(j).getName().equals(row[1])) {
                isNotHere = false;
                Date switchTime = correctDate(row[2]);
                if (time.compareTo(switchTime) > 0 ){
                    throw new ERROR("Switch time cannot be in the past!");
                } else if (time.compareTo(switchTime) == 0) {
                    smartDevices.get(j).setSwitchTime(switchTime);
                    if (smartDevices.get(j).getStatus().equals("Off")){
                        smartDevices = changeDevice.switchStatus(smartDevices, new String[]{"",smartDevices.get(j).getName(),"On"},time);
                        smartDevices.get(j).setSwitchTime(null);
                        device = smartDevices.get(j);
                    }else {
                        smartDevices = changeDevice.switchStatus(smartDevices, new String[]{"",smartDevices.get(j).getName(),"Off"},time);
                        smartDevices.get(j).setSwitchTime(null);
                        device = smartDevices.get(j);
                    }for (int x = 0;x<smartDevices.size();x++){
                        if (smartDevices.get(x).getSwitchTime() == null){
                            smartDevices.remove(j);
                            smartDevices.add(x, device);
                            break;
                        }
                    }


                }else {
                    smartDevices.get(j).setSwitchTime(switchTime);

                }

            }
        }
        Collections.sort(smartDevices, new Comparator<SmartDevices>() {
            @Override
            public int compare(SmartDevices o1, SmartDevices o2) {
                Date switchTime1 = o1.getSwitchTime();
                Date switchTime2 = o2.getSwitchTime();
                if (switchTime1 == null && switchTime2 == null) {
                    return 0;
                } else if (switchTime1 == null) {
                    return 1;
                } else if (switchTime2 == null) {
                    return -1;
                } else {
                    return switchTime1.compareTo(switchTime2);
                }
            }
        });
        if (isNotHere) {
            throw new ERROR("There is no such a device!");


        }return smartDevices;
    }

    /**
     * Checks if the date and time are in the correct format and parses date and time.
     * @param Time a String format time and date.
     * @return desired format.
     * @throws ERROR If the date or time in the input string is not in the correct format.
     * @throws ParseException If the input string is not in the correct format.
     */
    public Date correctDate(String Time) throws ERROR, ParseException {
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
            throw new ERROR("Time format is not correct!");

        }if (day < 1 || day > 31){
            throw new ERROR("Time format is not correct!");

        }if (hour < 0 || hour > 23){
            throw new ERROR("Time format is not correct!");

        }if (second < 0 || second > 59){
            throw new ERROR("Time format is not correct!");

        }if (month < 1 || month > 12){
            throw new ERROR("Time format is not correct!");

        }
        return dateFormat.parse(Time);

    }

    /**
     * Sets the time to the switch time of the nearest smart device.
     * @param smartDevices An ArrayList of SmartDevices that contains the Smart Device.
     * @param row An array of String for check command.
     * @return changed new time.
     * @throws ERROR If there is no switch time in smart devices.
     */
    public Date nop(ArrayList<SmartDevices> smartDevices, String[] row) throws ERROR {
        if (row.length != 1){
            throw new ERROR("Erroneous command!");
        }
        boolean isEmpty = true;
        SmartDevices tempDevice = new SmartDevices();
        for (int j = 0; j<smartDevices.size();j++){
            if (smartDevices.get(j).getSwitchTime() != null){

                return smartDevices.get(0).getSwitchTime();
            }
        }
        if (isEmpty){
            throw new ERROR("There is nothing to switch!");

        }
        return time;

    }

    /**
     * This method takes an ArrayList of SmartDevices and a Date as input parameters, and returns the updated ArrayList after
     * checking and updating the switch status of the SmartDevices.
     * @param smartDevices An ArrayList of SmartDevices that contains the Smart Device.
     * @param time current time and date.
     * @return An ArrayList of SmartDevices representing the updated devices after checking and updating the switch status.
     * @throws ERROR An exception that can be thrown in case of any errors during switchStatus method execution.
     */
    public ArrayList<SmartDevices> nopCheck(ArrayList<SmartDevices> smartDevices, Date time) throws ERROR {
        changeDevice changeDevice= new changeDevice();
        for (int j = 0;j<smartDevices.size();j++){
            if (smartDevices.get(j).getSwitchTime() == time){
                smartDevices.get(j).setSwitchTime(null);
                if (smartDevices.get(j).getStatus().equals("On")){
                    smartDevices = changeDevice.switchStatus(smartDevices, new String[]{"",smartDevices.get(j).getName(),"Off"},time);
                }else {
                    smartDevices = changeDevice.switchStatus(smartDevices, new String[]{"",smartDevices.get(j).getName(),"On"},time);

                }
            }
        }Collections.sort(smartDevices, new Comparator<SmartDevices>() {
            @Override
            public int compare(SmartDevices o1, SmartDevices o2) {
                Date switchTime1 = o1.getSwitchTime();
                Date switchTime2 = o2.getSwitchTime();
                if (switchTime1 == null && switchTime2 == null) {
                    return 0;
                } else if (switchTime1 == null) {
                    return 1;
                } else if (switchTime2 == null) {
                    return -1;
                } else {
                    return switchTime1.compareTo(switchTime2);
                }
            }
        });return smartDevices;

    }
}

