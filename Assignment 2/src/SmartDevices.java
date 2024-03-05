import java.util.Date;

public class SmartDevices{

    private String name;
    private String status;
    private Date switchTime;
    private IllegalArgumentException exception;

    /**
     * A class to represent a smart device.
     * @param name of the smart devices.
     */
    public SmartDevices(String name){
        this.setName(name);
        this.status = "Off";
        this.switchTime = null;


    }

    /**
     * A class to represent a smart device.
     */
    public SmartDevices() {

    }

    /**
     * The current status of the smart device (On/Off).
     * @return device status.
     */
    public String getStatus() {

        return status;
    }

    /**
     * Sets the status of the smart device.
     * @param status the new status of the smart device.
     * @return the SmartDevices instance with the new status.
     * @throws ERROR if the status is not "On" or "Off".
     */
    public SmartDevices setStatus(String status) throws ERROR {
        if (!status.equals("On") && !status.equals("Off")) {;
            throw new ERROR("Erroneous command!");

        }
        this.status = status;
        return null;
    }

    /**
     * Returns the time when the smart device was switched on/off.
     * @return the time when the smart device was switched on/off.
     */
    public Date getSwitchTime() {
        return switchTime;
    }

    /**
     * Sets the time when the smart device was switched on/off.
     * @param switchTime the time when the smart device was switched on/off.
     */
    public void setSwitchTime(Date switchTime) {
        this.switchTime = switchTime;
    }

    /**
     * Returns the name of the smart device.
     * @return the name of the smart device.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the smart device.
     * @param name the name of the smart device.
     */
    public void setName(String name) {
        this.name = name;
    }

}
