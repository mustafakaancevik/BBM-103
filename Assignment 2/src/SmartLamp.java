import java.text.ParseException;

public class SmartLamp extends SmartDevices{
    private int kelvin;
    private int brightness;
    private String colorCode;

    /**
     * Constructs a new SmartLamp object with the specified name.
     * @param name the name of the SmartLamp.
     */
    public SmartLamp(String name){
        super(name);
        kelvin = 4000;
        brightness = 100;
        colorCode = "";
    }

    /**
     * Sets the Kelvin value of the SmartCamera's color temperature.
     * @param kelvin the new Kelvin value to set.
     * @throws ERROR if the Kelvin value is not within the valid range of 2000K-6500K.
     */

    public void setKelvin(int kelvin) throws ERROR {
        if (kelvin <2000){
            throw new ERROR("Kelvin value must be in range of 2000K-6500K!");
        } else if (kelvin > 6500) {
            throw new ERROR("Kelvin value must be in range of 2000K-6500K!");
        } else {
            this.kelvin = kelvin;

        }
    }

    /**
     * Returns the Kelvin value of the SmartLamp's color temperature.
     * @return the Kelvin value of the SmartLamp's color temperature.
     */
    public int getKelvin(){
        return kelvin;
    }

    /**
     * Sets the Brightness value of the SmartCamera.
     * @param brightness the value of the SmartCamera.
     * @throws ERROR if the value of brightness is not in range.
     */
    public void  setBrightness(int brightness) throws ERROR {
        if (brightness < 0){
            throw new ERROR("Brightness must be in range of 0%-100%!");
        } else if (brightness >100) {
            throw new ERROR("Brightness must be in range of 0%-100%!");
        } else {
            this.brightness = brightness;

        }
    }

    /**
     * Returns the Brightness value of the SmartCamera.
     * @return the Brightness value of the SmartCamera.
     */
    public int getBrightness(){
        return brightness;
    }

    /**
     * Sets Color Code value of the SmartCamera.
     * @param colorCode value of the SmartCamera.
     * @throws ERROR if the Color Code is invalid.
     */
    public void setColorCode(String colorCode) throws ERROR {
        this.colorCode = colorCode;
    }

    /**
     * Returns the Color Code value of the SmartCamera.
     * @return the Color Code value of the SmartCamera.
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * @return a string representation of the Smart Lamp object.
     * @throws RuntimeException if parsing error occurs when formatting the time.
     */
    public String toString(){
        GiveInformation information = new GiveInformation();
        try {
            return String.format("Smart Lamp %s is %s and its kelvin value is %dK with %d%% brightness, and" +
                    " its time to switch its status is %s.",getName(),getStatus().toLowerCase(),getKelvin(),getBrightness(),information.returnFormattedTime(getSwitchTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
