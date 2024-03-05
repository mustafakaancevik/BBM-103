import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class changeDevice {
    readAndWrite write = new readAndWrite();
    readAndWrite correct = new readAndWrite();
    /**
     * Creates a new SmartPlug object with the given name and properties specified in the input row.
     * If the input row contains three elements, only the device name is used and a default SmartPlug object is returned.
     * If the input row contains four elements, the status property is set and a SmartPlug object with the specified status is returned.
     * If the input row contains five elements, the status and ampere properties are set and a SmartPlug object with the specified properties is returned.
     *
     * @param row an array of string inputs containing the device name, status, and ampere properties (if specified)
     * @param time the current time
     * @param devices the list of smart devices to check if the device name is unique
     * @return a new SmartPlug object with the specified properties
     * @throws ERROR if the input row has an incorrect number of elements or if the device name already exists in the list of smart devices
     */
    public SmartDevices addSmartPlug(String[] row,Date time,ArrayList<SmartDevices> devices) throws ERROR {
        SmartPlug plug = new SmartPlug(row[2]);
        if (row.length == 3) {
            correct.chechkDevice(row,devices);
        } else if (row.length == 4) {
            correct.chechkDevice(row,devices);
            plug.setStatus(row[3]);

        } else if (row.length == 5) {
            correct.chechkDevice(row,devices);
            plug.setStatus(row[3]);
            plug.setAmpere(Float.parseFloat(row[4]));

        }else{
            throw new ERROR("Erroneous command!");
        }
        if (plug.getStatus().equals("On")){
            plug.setPlugInTime(time,"On");
        }
        return plug;

    }
    /**
     * Creates a new SmartLamp object with the given name and properties specified in the input row.
     * If the input row contains three elements, only the device name is used and a default SmartLamp object is returned.
     * If the input row contains four elements, the status property is set and a SmartLamp object with the specified status is returned.
     * If the input row contains six elements, the status, kelvin, and brightness properties are set and a SmartLamp object with the specified properties is returned.
     *
     * @param row an array of string inputs containing the device name, status, kelvin, and brightness properties (if specified)
     * @param devices the list of smart devices to check if the device name is unique
     * @return a new SmartLamp object with the specified properties
     * @throws ERROR if the input row has an incorrect number of elements or if the device name already exists in the list of smart devices
     */

    public SmartDevices addSmartLamp(String[] row,ArrayList<SmartDevices> devices) throws ERROR {
        SmartLamp lamp = new SmartLamp(row[2]);
        if (row.length == 3) {
            correct.chechkDevice(row,devices);
            return lamp;
        } else if (row.length == 4) {
            correct.chechkDevice(row,devices);
            lamp.setStatus(row[3]);
            return lamp;

        } else if (row.length == 6) {
            correct.chechkDevice(row,devices);
            lamp.setStatus(row[3]);
            lamp.setKelvin(Integer.parseInt(row[4]));
            lamp.setBrightness(Integer.parseInt(row[5]));
            return lamp;

        }else {
            throw new ERROR("Erroneous command!");
        }
    }
    /**
     * Creates a new SmartLampWithColor object with the given name and properties specified in the input row.
     * If the input row contains three elements, only the device name is used and a default SmartLampWithColor object is returned.
     * If the input row contains four elements, the status property is set and a SmartLampWithColor object with the specified status is returned.
     * If the input row contains six elements, the status, color code, and brightness properties are set and a SmartLampWithColor object with the specified properties is returned.
     *
     * @param row an array of string inputs containing the device name, status, color code, and brightness properties (if specified)
     * @param devices the list of smart devices to check if the device name is unique
     * @return a new SmartLampWithColor object with the specified properties
     * @throws ERROR if the input row has an incorrect number of elements or if the device name already exists in the list of smart devices
     */

    public SmartDevices addSmartLampWithColor(String[] row,ArrayList<SmartDevices> devices) throws ERROR {
        SmartLampWithColor lampWithColor = new SmartLampWithColor(row[2]);
        if (row.length == 3) {
            correct.chechkDevice(row,devices);
            return lampWithColor;
        } else if (row.length == 4) {
            correct.chechkDevice(row,devices);
            lampWithColor.setStatus(row[3]);
            return lampWithColor;


        } else if (row.length == 6) {
            lampWithColor.setStatus(row[3]);
            if (row[4].startsWith("0x")){
                lampWithColor.setColorCode(row[4]);
            }else {
                lampWithColor.setKelvin(Integer.parseInt(row[4]));

            }
            lampWithColor.setBrightness(Integer.parseInt(row[5]));
            return lampWithColor;

        }else {
            throw new ERROR("Erroneous command!");
        }
    }
    /**

     Creates and returns a new SmartLampWithColor object based on the input row.
     @param row an array of Strings containing information about the new SmartLampWithColor object.
     @param devices an ArrayList of SmartDevices containing all existing smart devices.
     @return a new SmartLampWithColor object with the specified properties.
     @throws ERROR if the input row does not have the correct format.
     */
    public SmartDevices addSmartCamera(String[] row,Date time,ArrayList<SmartDevices> devices) throws ERROR {
        SmartCamera smartCamera = new SmartCamera(row[2]);
        if (row.length == 4) {
            correct.chechkDevice(row,devices);
            smartCamera.setMegabytesConsumedPerSecond(Double.parseDouble(row[3]));
            return smartCamera;
        } else if (row.length == 5) {
            correct.chechkDevice(row,devices);
            smartCamera.setMegabytesConsumedPerSecond(Double.parseDouble(row[3]));
            smartCamera.setStatus(row[4]);
            if (smartCamera.getStatus().equals("On")){
                smartCamera.setOnTime(time,"On");
            }
            return smartCamera;

        }else {
            throw new ERROR("Erroneous command!");
        }
    }

    /**
     * Deletes and returns a new SmartDevices Arraylist.
     * @param deviceName a String contains of SmartDevice name that wants to be deleted.
     * @param smartDevices an array of Smartdevice containing information about the SmartDevice.
     * @param output the output to write txt.
     * @param date the current date and time.
     * @return the updated ArrayList of smart devices after the device has been removed
     * @throws ERROR if there is not a device in given ArrayList of SmartDevices.
     * @throws ParseException if the date is not in the expected format.
     * @throws IOException if there is an error with writing the output to a file
     */
    public ArrayList<SmartDevices> deleteDevice(String deviceName, ArrayList<SmartDevices> smartDevices,String output,Date date) throws ERROR, ParseException, IOException {
        GiveInformation information = new GiveInformation();
        boolean notHere = false;
        for (int i = 0; i < smartDevices.size(); i++) {

            if (smartDevices.get(i).getName().equals(deviceName)) {
                write.writeInput(output,"SUCCESS: Information about removed smart device is as follows:");
                if (smartDevices.get(i).getStatus().equals("On")){
                    smartDevices = switchStatus(smartDevices, new String[]{"",smartDevices.get(i).getName(),"Off"},date);
                }
                information.oneDeviceInformation(smartDevices.get(i),output);
                smartDevices.remove(i);
                notHere = false;
                return smartDevices;
            } else {
                notHere = true;
            }

        }if (notHere){
            throw new ERROR("There is not such a device!");
        }

        return smartDevices;
    }

    /**
     * Changes SmartDevices name and returns changed SmartDevices Arraylist.
     * @param smartDevices an Arraylist of SmartDevices.
     * @param row an Array of String containing information about which SmartDevices name is changing.
     * @return the updated ArrayList of smart devices after the devices name has been changed.
     * @throws ERROR if both name are the same or the name to be change is in already in ArrayList.
     */
    public ArrayList<SmartDevices> changeName(ArrayList<SmartDevices> smartDevices,String[] row) throws ERROR {
        if (!(row.length == 3)){
            throw new ERROR("Erroneous command!");
        }
        if (row[1].equals(row[2])){
            throw new ERROR("Both of the names are the same, nothing changed!");

        }
        boolean isHere = true;
        for (SmartDevices smartDevice : smartDevices) {
            if (row[2].equals(smartDevice.getName())) {
                isHere = false;
                throw new ERROR("There is already a smart device with same name!");
            }
        }
        if (isHere){
            for (int i = 0;i<smartDevices.size();i++) {
                if (row[1].equals(smartDevices.get(i).getName())) {
                    isHere = false;
                    smartDevices.get(i).setName(row[2]);
                    return smartDevices;
                }
            }

        }


        return smartDevices;
    }

    /**
     * Save the plug in times for smart plugs and if they status on starts calculate consumed energy.
     * @param smartDevices the ArrayList of SmartDevices to search for the SmartPlug to be plugged.
     * @param row an Array of String containing information about smart plug.
     * @param time the current date and time.
     * @return the updates ArrayList of smart devices after the smart plug's plug status and plug in time has been changed.
     * @throws ERROR if that smart plug already plugged in to plug,if that device is not a smart plug or there is not such a device.
     */
    public ArrayList<SmartDevices> plugIn(ArrayList<SmartDevices> smartDevices,String[] row,Date time) throws ERROR {
        if (row.length != 3){
            throw new ERROR("Erroneous command!");
        }
        boolean notHere = true;
        for (int j = 0;j<smartDevices.size();j++){
            if (smartDevices.get(j).getName().equals(row[1])){
                if (smartDevices.get(j) instanceof SmartPlug){
                    if (((SmartPlug) smartDevices.get(j)).getPlugStatus().equals("in")){
                        throw new ERROR("There is already an item plugged in to that plug!");
                    }else {
                        ((SmartPlug) smartDevices.get(j)).setAmpere(Float.parseFloat(row[2]));
                        ((SmartPlug) smartDevices.get(j)).setPlugStatus("in");
                        if (smartDevices.get(j).getStatus().equals("On")){
                            ((SmartPlug) smartDevices.get(j)).setPlugInTime(time, (smartDevices.get(j)).getStatus());
                        }
                        notHere = false;
                        return smartDevices;

                    }
                }else {
                    throw new ERROR("This device is not a smart plug!");
                }
            }
        }if (notHere){
            throw new ERROR("There is not such a device!");
        }
        return smartDevices;
    }

    /**
     * Sets the plug status out and calculated the consumed energy during the time device was plugged in.
     * @param smartDevices the ArrayList of SmartDevices to search for the SmartPlug to be unplugged.
     * @param row an Array of String containing information about smart plug.
     * @param time current date and time.
     * @return the updated ArrayList of SmartDevices after the SmartPlug has been unplugged.
     * @throws ERROR if the smart plug is not found in arraylist or that device is not a plug.
     */
    public ArrayList<SmartDevices> plugOut(ArrayList<SmartDevices> smartDevices,String[] row,Date time) throws ERROR {
        if (row.length != 2){
            throw new ERROR("Erroneous command!");
        }
        boolean notHere = true;
        for (int j = 0;j<smartDevices.size();j++){
            if (smartDevices.get(j).getName().equals(row[1])){
                if (smartDevices.get(j) instanceof SmartPlug){
                    if (((SmartPlug) smartDevices.get(j)).getPlugStatus().equals("out")){
                        throw new ERROR("This plug has no item to plug out from that plug!");
                    }else {
                        ((SmartPlug) smartDevices.get(j)).setPlugStatus("out");
                        ((SmartPlug) smartDevices.get(j)).setPlugOutTime(time);
                        ((SmartPlug) smartDevices.get(j)).setConsumedEnergy(((SmartPlug) smartDevices.get(j)).getAmpere()
                                ,((SmartPlug) smartDevices.get(j)).getPlugInTime(),((SmartPlug) smartDevices.get(j)).getPlugOutTime());
                        ((SmartPlug) smartDevices.get(j)).setPlugOutTime(null);
                        ((SmartPlug) smartDevices.get(j)).setPlugInTime(null,"On");
                        notHere = false;
                        return smartDevices;

                    }

                }
            }
        }if (notHere){
            throw new ERROR("There is not such a device!");
        }
        return smartDevices;
    }

    /**
     * This method useing for switch the status on or off.
     * If the devices is smart plug and status off, turn status to on and save plug in time and starts calculating consumed energy.
     * If the devices is smart camera and status off, turn status to on and save on time and starts calculating megabytes used per minutes.
     * If the devices is smart plug and status on, turn status to off and save plug out time and stop calculating consumed energy.
     * If the devices is smart camera and status on, turn status to off and save off time and stop calculating megabytes used per minutes.
     * @param smartDevices the ArrayList of SmartDevices.
     * @param row an Array of String containing information about device which wants to change status.
     * @param time current date and time.
     * @return An ArrayList of SmartDevices containing all smart devices, with the status of the target device switched as on or off.
     * @throws ERROR If the device to switch status is not found in the smartDevices ArrayList, or if the device is already in the desired status.
     */
    public ArrayList<SmartDevices> switchStatus(ArrayList<SmartDevices> smartDevices, String[] row,Date time) throws ERROR {
        if (row.length != 3){
            throw new ERROR("Erroneous command!");
        }
        boolean isNotHere = true;

        for (int j = 0;j <smartDevices.size();j++){
            if (smartDevices.get(j).getName().equals(row[1])){
                isNotHere = false;
                if (smartDevices.get(j).getStatus().equals("On")){
                    if (row[2].equals("On")){
                        throw new ERROR("This device is already switched on!");
                    } else if (row[2].equals("Off")) {
                        smartDevices.get(j).setStatus("Off");
                        smartDevices.get(j).setSwitchTime(null);

                        if (smartDevices.get(j) instanceof SmartPlug){
                            ((SmartPlug) smartDevices.get(j)).setPlugOutTime(time);
                            ((SmartPlug) smartDevices.get(j)).setConsumedEnergy(((SmartPlug) smartDevices.get(j)).getAmpere()
                                    ,((SmartPlug) smartDevices.get(j)).getPlugInTime(),((SmartPlug) smartDevices.get(j)).getPlugOutTime());
                            ((SmartPlug) smartDevices.get(j)).setPlugInTime(null,"Off");
                        }else if (smartDevices.get(j) instanceof SmartCamera){
                            ((SmartCamera) smartDevices.get(j)).setOffTime(time);
                            ((SmartCamera) smartDevices.get(j)).setUsedMegabytes(((SmartCamera) smartDevices.get(j)).getMegabytesConsumedPerMinutes()
                                    ,((SmartCamera) smartDevices.get(j)).getOnTime(),((SmartCamera) smartDevices.get(j)).getOffTime());
                            ((SmartCamera) smartDevices.get(j)).setOnTime(null,"Off");
                        }

                    }
                } else if (smartDevices.get(j).getStatus().equals("Off")) {
                    if (row[2].equals("Off")) {
                        throw new ERROR("This device is already switched off!");

                    } else if (row[2].equals("On")) {
                        smartDevices.get(j).setStatus("On");
                        smartDevices.get(j).setSwitchTime(null);

                        if (smartDevices.get(j) instanceof SmartPlug) {

                            if (((SmartPlug) smartDevices.get(j)).getPlugStatus().equals("in")) {
                                ((SmartPlug) smartDevices.get(j)).setPlugInTime(time, ( smartDevices.get(j)).getStatus());

                            }
                        } else if (smartDevices.get(j) instanceof SmartCamera) {
                            ( smartDevices.get(j)).setStatus("On");
                            ((SmartCamera) smartDevices.get(j)).setOnTime(time, "On");

                        }

                    }
                }

            }


        }


        if (isNotHere){
            throw new ERROR("There is not such a device!");
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

    /**
     * This method takes in an ArrayList of SmartDevices and a Date object and returns an ArrayList of SmartDevices
     * after setting the switch time for each device.
     * If a device's switch time is earlier than the new time, its status will be toggled and
     * its switch time will be set to null. The device will be placed in
     * the earliest available slot in the ArrayList based on its new switch time. The ArrayList is then sorted
     * for each device's switch time.
     * @param smartDevices An ArrayList contains of all smart devices.
     * @param newTime A time desired to change.
     * @return the updated ArrayList of SmartDevices after the all smart devices has been sorted.
     * @throws ERROR If there is an error when switchStatus method called.
     */
    public ArrayList<SmartDevices> afterSetTime(ArrayList<SmartDevices> smartDevices,Date newTime) throws ERROR {
        SmartDevices device = new SmartDevices();
        for (int j = 0;j<smartDevices.size();j++){
            if (smartDevices.get(j).getSwitchTime() != null ){
                if (newTime.compareTo(smartDevices.get(j).getSwitchTime()) >= 0){
                    if (smartDevices.get(j+1).getSwitchTime() != null && smartDevices.get(j).getSwitchTime().compareTo(smartDevices.get(j+1).getSwitchTime()) == 0 ){
                        if (smartDevices.get(j+1).getStatus().equals("Off")){
                            if (smartDevices.get(j) instanceof SmartPlug) {

                                if (((SmartPlug) smartDevices.get(j)).getPlugStatus().equals("in")) {
                                    ((SmartPlug) smartDevices.get(j)).setPlugInTime(newTime, ( smartDevices.get(j)).getStatus());

                                }
                            } else if (smartDevices.get(j) instanceof SmartCamera) {
                                ( smartDevices.get(j)).setStatus("On");
                                ((SmartCamera) smartDevices.get(j)).setOnTime(newTime, "On");

                            }
                            smartDevices.get(j+1).setSwitchTime(null);
                            device = smartDevices.get(j+1);
                        }else {
                            if (smartDevices.get(j+1) instanceof SmartPlug){
                                ((SmartPlug) smartDevices.get(j+1)).setPlugOutTime(newTime);
                                ((SmartPlug) smartDevices.get(j+1)).setConsumedEnergy(((SmartPlug) smartDevices.get(j+1)).getAmpere()
                                        ,((SmartPlug) smartDevices.get(j+1)).getPlugInTime(),((SmartPlug) smartDevices.get(j+1)).getPlugOutTime());
                                ((SmartPlug) smartDevices.get(j+1)).setPlugInTime(null,"Off");
                            }else if (smartDevices.get(j+1) instanceof SmartCamera){
                                ((SmartCamera) smartDevices.get(j+1)).setOffTime(newTime);
                                ((SmartCamera) smartDevices.get(j+1)).setUsedMegabytes(((SmartCamera) smartDevices.get(j)).getMegabytesConsumedPerMinutes()
                                        ,((SmartCamera) smartDevices.get(j+1)).getOnTime(),((SmartCamera) smartDevices.get(j+1)).getOffTime());
                                ((SmartCamera) smartDevices.get(j+1)).setOnTime(null,"Off");
                            }
                            smartDevices.get(j+1).setSwitchTime(null);
                            device = smartDevices.get(j+1);
                        }
                        for (int x = 0;x<smartDevices.size();x++){
                            if (smartDevices.get(x).getSwitchTime() == null){
                                smartDevices.remove(j+1);
                                smartDevices.add(x, device);
                                break;
                            }
                        }
                    }
                    if (smartDevices.get(j).getStatus().equals("Off")){
                        if (smartDevices.get(j) instanceof SmartPlug) {

                            if (((SmartPlug) smartDevices.get(j)).getPlugStatus().equals("in")) {
                                ((SmartPlug) smartDevices.get(j)).setPlugInTime(newTime, (smartDevices.get(j)).getStatus());

                            }
                        } else if (smartDevices.get(j) instanceof SmartCamera) {
                            (smartDevices.get(j)).setStatus("On");
                            ((SmartCamera) smartDevices.get(j)).setOnTime(newTime, "On");

                        }
                        smartDevices.get(j).setSwitchTime(null);
                        device = smartDevices.get(j);
                    }else {
                        if (smartDevices.get(j) instanceof SmartPlug){
                            ((SmartPlug) smartDevices.get(j)).setPlugOutTime(newTime);
                            ((SmartPlug) smartDevices.get(j)).setConsumedEnergy(((SmartPlug) smartDevices.get(j)).getAmpere()
                                    ,((SmartPlug) smartDevices.get(j)).getPlugInTime(),((SmartPlug) smartDevices.get(j)).getPlugOutTime());
                            ((SmartPlug) smartDevices.get(j)).setPlugInTime(null,"Off");
                        }else if (smartDevices.get(j) instanceof SmartCamera){
                            ((SmartCamera) smartDevices.get(j)).setOffTime(newTime);
                            ((SmartCamera) smartDevices.get(j)).setUsedMegabytes(((SmartCamera) smartDevices.get(j)).getMegabytesConsumedPerMinutes()
                                    ,((SmartCamera) smartDevices.get(j)).getOnTime(),((SmartCamera) smartDevices.get(j)).getOffTime());
                            ((SmartCamera) smartDevices.get(j)).setOnTime(null,"Off");
                        }
                        smartDevices.get(j).setSwitchTime(null);
                        device = smartDevices.get(j);
                    }
                    for (int x = 0;x<smartDevices.size();x++){
                        if (smartDevices.get(x).getSwitchTime() == null){
                            smartDevices.remove(j);
                            smartDevices.add(x, device);
                            break;
                        }
                    }
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


