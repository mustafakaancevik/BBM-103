import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class SmartPlug extends SmartDevices{
    private float ampere;
    private String status;

    /**
     * Returns smart plug's plug out time.
     * @return smart plug's plug out time.
     */
    public Date getPlugOutTime() {
        return plugOutTime;
    }

    /**
     * Sets plug out time of smart plugs.
     * @param plugOutTime smart plug's plug out time.
     */
    public void setPlugOutTime(Date plugOutTime) {
        this.plugOutTime = plugOutTime;
    }

    private Date plugOutTime;

    public Date getPlugInTime() {
        return plugOnTime;
    }

    /**
     * Sets plug in time of smart plugs.
     * @param plugOnTime smart plug's plug in time.
     * @param status smart plug's status.
     */

    public void setPlugInTime(Date plugOnTime,String status) {
        if (status.equals("On")){
            this.plugOnTime = plugOnTime;
        } else if (status.equals("Off")) {
            this.plugOnTime = null;
        }
    }

    private Date plugOnTime;

    /**
     * Returns smart plugs status
     * @return smart plugs status.
     */
    public String getPlugStatus() {

        return plugStatus;
    }

    /**
     * Sets smart plugs plug status.
     * @param plugStatus current plug status.
     */
    public void setPlugStatus(String plugStatus){

         if (plugStatus.equals("out")){
            this.plugStatus = "out";
        } else {
            this.plugStatus = "in";


        }
    }

    private String plugStatus;

    /**
     *
     * @return smart plugs consumed energy value.
     */
    public double getConsumedEnergy() {
        return consumedEnergy;

    }

    /**
     * Calculates the consumed energy based on the provided amperes and plug on/off times,
     * and updates the consumed energy value of the object.
     * @param ampere the amperes consumed by the device.
     * @param plugOnTime the time at which the device was plugged in.
     * @param plugOutTime the time at which the device was unplugged.
     * @return the updated consumed energy value.
     * @throws IllegalArgumentException if the plug on time is null or the plug off time is null.
     */
    public double setConsumedEnergy(float ampere, Date plugOnTime, Date plugOutTime) {
        if (plugOnTime == null){
            return consumedEnergy;
        } else if (plugOutTime == null) {
            return consumedEnergy;
        }else {
            this.consumedEnergy += calculateEnergy(ampere,plugOnTime,plugOutTime);
        }

        return 0;
    }

    /**
     * Calculates the energy based on amperes and plug ın/off times, and returns energy.
     * @param ampere the amperes consumed by the device.
     * @param startTime the time at which the device was plugged in.
     * @param finisTime the time at which the device was unplugged.
     * @return the consumed energy.
     */
    public double calculateEnergy(float ampere,Date startTime,Date finisTime){
        double power = 220 * ampere;
        double energy = power*timeDifference(startTime,finisTime);
        return energy;


    }

    /**
     * Calculates time difference between plug on time and plug off time.
     * @param startTime the time at which the device was plugged in.
     * @param finishTime the time at which the device was unplugged.
     * @return the time difference in hours.
     */
    public float timeDifference(Date startTime, Date finishTime){


        float diffInMs = finishTime.getTime() - startTime.getTime();
        float diffInHours = diffInMs / (60 * 60 * 1000);

        return  diffInHours;
    }

    private double consumedEnergy;

    /**
     * Constructor of SmartPlug.
     * @param name Smart plug name.
     */

    public SmartPlug(String name) {
        super( name);
        this.consumedEnergy = 0.00;
        this.plugStatus = "out";
        this.status = "Off";
        this.plugOnTime = null;
        this.plugOutTime = null;
    }

    /**
     *
     * @return smart plug ampere.
     */
    public float getAmpere() {
        return ampere;
    }

    /**
     * Set smart plug ampere.
     * @param ampere the amperes of device.
     * @return ampere.
     * @throws ERROR if ampere negative or zero.
     */
    public SmartDevices setAmpere(float ampere) throws ERROR {
        if (ampere<=0){
            throw new ERROR("Ampere value must be a positive number!");
        }else {
            this.ampere = ampere;
        }



        return null;
    }

    /**
     *
     * @return smart plugs status.
     */
    public String getStatus() {

        return status;
    }

    /**
     * Set smart plug status.
     * @param status devices status.
     * @return status.
     * @throws ERROR If desired status are not on or off.
     */
    public SmartDevices setStatus(String status) throws ERROR {
        if (!status.equals("On") && !status.equals("Off")) {;
            throw new ERROR("HATALI GİRDİNİZ");

        }
        if (status.equals("On")){

        }
        this.status = status;
        return null;
    }

    /**
     *
     * @return a string representation of the Smart Plug object.
     * @throws RuntimeException if parsing error occurs when formatting the time.
     */
    public String toString(){
        GiveInformation information = new GiveInformation();
        try {
            return String.format("Smart Plug %s is %s and consumed %.2fW so far (excluding current device), " +
                    "and its time to switch its status is %s.",getName(),getStatus().toLowerCase(),getConsumedEnergy(),information.returnFormattedTime(getSwitchTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
