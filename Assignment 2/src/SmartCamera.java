import java.text.ParseException;
import java.util.Date;

public class SmartCamera extends SmartDevices{
    private double megabytesConsumedPerMinutes;
    private Date onTime;

    /**
     * Returns the on time of the smart device.
     * @return the on time of the smart device.
     */
    public Date getOnTime() {
        return onTime;
    }

    /**
     * Sets the on time of the smart device.
     * @param plugOnTime a Date object representing the time when the device is turned on.
     * @param status a String indicating the current status of the device ("On" or "Off").
     */
    public void setOnTime(Date plugOnTime,String status) {
        if (status.equals("On")){
            this.onTime = plugOnTime;
        } else if (status.equals("Off")) {
            this.onTime = null;
        }
    }

    /**
     * Returns the off time of the smart device.
     * @return the off time of the smart device.
     */
    public Date getOffTime() {
        return offTime;
    }

    /**
     * Sets the off time of the smart device.
     * @param offTime a Date object representing the time when the device is turned off.
     */
    public void setOffTime(Date offTime) {
        this.offTime = offTime;
    }

    private Date offTime;

    /**
     * The current status of the smart device (On/Off).
     * @return device status.
     */
    @Override
    public String getStatus() {
        return status;
    }

    /**
     * Calculates the amount of data used by a device in megabytes (MB) during a given time period.
     * @param usedMegabytes the amount of data used by the device in MB.
     * @param startTime a Date object representing the start time of the period.
     * @param finishTime a Date object representing the end time of the period.
     * @return the total amount of data used in MB during the specified time period.
     */
    public double calculateUsedMb(double usedMegabytes,Date startTime,Date finishTime){;
        double usedMb = usedMegabytes*timeDifference(startTime,finishTime);
        return usedMb;


    }

    /**
     * Calculates the difference of the start time and end time in minutes.
     * @param startTime a Date object representing the start time of the period.
     * @param finishTime a Date object representing the end time of the period.
     * @return the time difference between the period.
     */
    public float timeDifference(Date startTime, Date finishTime){

        float diffInMs = finishTime.getTime() - startTime.getTime();
        float diffInMinutes = diffInMs / (60  * 1000);

        return  diffInMinutes;
    }

    /**
     * Sets the status of the smart device.
     * @param status the new status of the smart device.
     * @return the SmartDevices instance with the new status.
     * @throws ERROR if the status is not "On" or "Off".
     */
    public SmartDevices setStatus(String status) throws ERROR {
        if (!status.equals("On") && !status.equals("Off")) {
            throw new ERROR("HATALI GİRDİNİZ");

        }

        this.status = status;
        return null;
    }

    private String status;

    /**
     * Returns used megabytes by smart camera in per minutes.
     * @return used megabytes by smart camera in per minutes.
     */
    public float getUsedMegabytes() {
        return usedMegabytes;
    }

    /**
     * Updates the amount of data used by the device in megabytes based on its usage during a certain time period.
     * @param usedMb the amount of data used by the device during the current usage period in MB.
     * @param plugOnTime a Date object representing the start time of the current usage period.
     * @param plugOutTime a Date object representing the end time of the current usage period.
     * @return the updated amount of data used in MB.
     */
    public double setUsedMegabytes(double usedMb, Date plugOnTime, Date plugOutTime) {
        if (plugOnTime == null){
            return usedMegabytes;
        } else if (plugOutTime == null) {
            return usedMb;
        }else {
            this.usedMegabytes += calculateUsedMb(usedMb,plugOnTime,plugOutTime);
        }

        return usedMegabytes;
    }

    private float usedMegabytes;

    /**
     * Constructs a new SmartCamera object with the specified name.
     * @param name the name of the SmartCamera.
     */
    public SmartCamera(String name) {
        super(name);
        this.megabytesConsumedPerMinutes = megabytesConsumedPerMinutes;
        this.usedMegabytes = 0;
        this.status = "Off";
    }

    /**
     * Returns the amount of data consumed by the camera per minute.
     * @return the amount of data consumed by the camera per minute
     */
    public double getMegabytesConsumedPerMinutes() {
        return megabytesConsumedPerMinutes;
    }

    /**
     * Sets the number of megabytes consumed per second.
     * @param megabytesConsumedPerMinutes a positive double value representing the number of megabytes consumed per minute.
     * @throws ERROR if the argument value is negative or equal to zero.
     */
    public void setMegabytesConsumedPerSecond(double megabytesConsumedPerMinutes) throws ERROR {
        if (megabytesConsumedPerMinutes <= 0) {
            throw new ERROR("Megabyte value must be a positive number!");
        } else {
            this.megabytesConsumedPerMinutes = megabytesConsumedPerMinutes;

        }
    }

    /**
     * @return a string representation of the Smart Camera object.
     * @throws RuntimeException if parsing error occurs when formatting the time.
     */
    public String toString(){
        GiveInformation information = new GiveInformation();
        try {
            return String.format("Smart Camera %s is %s and used %.2f MB of storage so far " +
                    "(excluding current status), and its time to switch its status is %s."
                    ,getName(),getStatus().toLowerCase(),getUsedMegabytes(),information.returnFormattedTime(getSwitchTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

