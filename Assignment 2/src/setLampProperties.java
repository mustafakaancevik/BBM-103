import java.util.ArrayList;

public class setLampProperties {
    /**
     * This method sets smart lamps kelvin.
     * @param row array of String containing smart lamps information.
     * @param smartDevices array list of all smart devices.
     * @return  the updated ArrayList of SmartDevices.
     * @throws ERROR if the device with the provided name does not exist in the system or that device is not a smart lamp.
     */
    public ArrayList<SmartDevices> setKelvin(String[] row, ArrayList<SmartDevices> smartDevices) throws ERROR {
        boolean isNotHere = true;
        for (int j = 0;j<smartDevices.size();j++){
            if (smartDevices.get(j).getName().equals(row[1])){
                if (smartDevices.get(j) instanceof SmartLamp){
                    ((SmartLamp) smartDevices.get(j)).setKelvin(Integer.parseInt(row[2]));
                    isNotHere = false;
                    return smartDevices;
                }else if (smartDevices.get(j) instanceof SmartLampWithColor){
                    ((SmartLampWithColor) smartDevices.get(j)).setKelvin(Integer.parseInt(row[2]));
                    isNotHere = false;
                    return smartDevices;
                }else {
                    throw new ERROR("This device is not a smart lamp!");
                }
            }
        }if (isNotHere){
            throw new ERROR("There is not such a device!");
        }
        return smartDevices;
    }

    /**
     * This method sets smart lamps brightness, that value must be in between 1-100.
     * @param row array of String containing smart lamp information that name and brightness.
     * @param smartDevices array list of all smart devices.
     * @return  the updated ArrayList of SmartDevices.
     * @throws ERROR if the device with the provided name does not exist in the system or that device is not a smart lamp.
     */
    public ArrayList<SmartDevices> setBrightness(String[] row, ArrayList<SmartDevices> smartDevices) throws ERROR {
        boolean isNotHere = true;
        for (int j = 0;j<smartDevices.size();j++){
            if (smartDevices.get(j).getName().equals(row[1])){
                if (smartDevices.get(j) instanceof SmartLamp){
                    ((SmartLamp) smartDevices.get(j)).setKelvin(Integer.parseInt(row[2]));
                    isNotHere = false;
                    return smartDevices;
                }else if (smartDevices.get(j) instanceof SmartLampWithColor){
                    ((SmartLampWithColor) smartDevices.get(j)).setKelvin(Integer.parseInt(row[2]));
                    isNotHere = false;
                    return smartDevices;
                }else {
                    throw new ERROR("This device is not a smart lamp!");
                }
            }
        }if (isNotHere){
            throw new ERROR("There is not such a device!");
        }
        return smartDevices;
    }
    /**
     * Sets the color code of a SmartLampWithColor device based on the provided row of data.
     * @param row an array of strings containing the data of the device to be updated.
     * @param smartDevices an ArrayList of SmartDevices containing all the smart devices.
     * @return  the updated ArrayList of SmartDevices.
     * @throws ERROR if the device with the provided name does not exist in the system or that device is not a smart lamp.
     */
    public ArrayList<SmartDevices> setColorCode(String[] row, ArrayList<SmartDevices> smartDevices) throws ERROR {
        boolean isNotHere = true;
        for (int j = 0;j<smartDevices.size();j++) {
            if (smartDevices.get(j).getName().equals(row[1])) {
                if (smartDevices.get(j) instanceof SmartLampWithColor) {
                    ((SmartLampWithColor) smartDevices.get(j)).setColorCode(row[2]);
                    isNotHere = false;
                    return smartDevices;
                } else {
                    throw new ERROR("This device is not a smart color lamp!");
                }
            }
        }if (isNotHere) {
            throw new ERROR("There is not such a device!");
        }
        return smartDevices;
    }
    /**
     * Sets the color temperature and brightness of a SmartLamp or SmartLampWithColor device to white.
     * @param row an array of strings containing information of the device to be updated.
     * @param smartDevices an ArrayList of SmartDevices containing all the smart devices.
     * @return the updated ArrayList of SmartDevices.
     * @throws ERROR if the device with the provided name does not exist in the system or that device is not a smart lamp.
     */
    public ArrayList<SmartDevices> setWhite(String[] row, ArrayList<SmartDevices> smartDevices) throws ERROR {
        boolean isNotHere = true;
        for (int j = 0;j<smartDevices.size();j++){
            if (smartDevices.get(j).getName().equals(row[1])){
                if (smartDevices.get(j) instanceof SmartLampWithColor){
                    ((SmartLampWithColor) smartDevices.get(j)).setKelvin(Integer.parseInt(row[2]));
                    ((SmartLamp) smartDevices.get(j)).setBrightness(Integer.parseInt(row[3]));
                    ((SmartLampWithColor) smartDevices.get(j)).setColorCode("0");

                    isNotHere = false;

                }else if (smartDevices.get(j) instanceof SmartLamp){

                    ((SmartLamp) smartDevices.get(j)).setKelvin(Integer.parseInt(row[2]));
                    ((SmartLamp) smartDevices.get(j)).setBrightness(Integer.parseInt(row[3]));
                    isNotHere = false;

                }
                else {
                    throw new ERROR("This device is not a smart lamp!");
                }
            }
        }if (isNotHere){
            throw new ERROR("There is not such a device!");
        }
        return smartDevices;
    }
    /**
     * Sets the color code and brightness of a SmartLampWithColor device based on the provided row of data.
     * @param row an array of strings containing information of the device to be updated.
     * @param smartDevices an ArrayList of SmartDevices containing all the smart devices.
     * @return the updated ArrayList of SmartDevices.
     * @throws ERROR if the device with the provided name does not exist in the system or that device
     * is not a smart lamp. If the color code is invalid or not in range.
     */
    public ArrayList<SmartDevices> setColor(String[] row, ArrayList<SmartDevices> smartDevices) throws ERROR {
        boolean isNotHere = true;
        SmartLampWithColor.ColorCodeChecker codeChecker = new SmartLampWithColor.ColorCodeChecker();
        for (int j = 0;j<smartDevices.size();j++) {
            if (smartDevices.get(j).getName().equals(row[1])) {
                if (smartDevices.get(j) instanceof SmartLampWithColor) {
                    SmartLampWithColor devices;
                    devices = (SmartLampWithColor) smartDevices.get(j);
                    if (codeChecker.codeChecker(row[2]) == 0){
                        devices.setBrightness(Integer.parseInt(row[3]));
                        devices.setColorCode(row[2]);
                        smartDevices.set(j,devices);
                    }else if (codeChecker.codeChecker(row[2]) == 1){
                        throw new ERROR("Erroneous command!");
                    } else if (codeChecker.codeChecker(row[2]) == 2) {
                        throw new ERROR("Color code value must be in range of 0x0-0xFFFFFF!");
                    }
                    isNotHere = false;
                    return smartDevices;
                } else {
                    throw new ERROR("This device is not a smart color lamp!");
                }
            }
        }if (isNotHere) {
            throw new ERROR("There is not such a device!");
        }
        return smartDevices;
    }
}
