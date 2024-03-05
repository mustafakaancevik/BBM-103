import java.text.ParseException;

public class SmartLampWithColor extends SmartLamp{
    private int kelvin;
    private int brightness;
    private String colorCode;

    /**
     * Returns the Kelvin value of the SmartLampWithColor's color temperature.
     * @return the Kelvin value of the SmartLampWithColor's color temperature.
     */
    @Override
    public int getKelvin() {
        return kelvin;
    }

    /**
     * Sets the Kelvin value of the SmartLampWithColor's color temperature.
     * @param kelvin the new Kelvin value to set.
     * @throws ERROR if the Kelvin value is not within the valid range of 2000K-6500K.
     */
    @Override
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
     * Returns the Brightness value of the SmartLampWithColor.
     * @return the Brightness value of the SmartLampWithColor.
     */
    @Override
    public int getBrightness() {
        return brightness;
    }

    /**
     * Sets the Brightness value of the SmartLampWithColor.
     * @param brightness the value of the SmartLampWithColor.
     * @throws ERROR if the value of brightness is not in range.
     */
    @Override
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
     * Returns the Color Code value of the SmartLampWithColor.
     * @return the Color Code value of the SmartLampWithColor.
     */
    @Override
    public String getColorCode() {
        return colorCode;
    }

    /**
     * Sets the ColorCode value of SmartLampWithColor object.
     * @param colorCode value of the SmartCamera.
     * @throws ERROR if the color code is not in range and if the color code is invalid.
     */
    @Override
    public void setColorCode(String colorCode) throws ERROR {
        ColorCodeChecker codeChecker = new ColorCodeChecker();

        if (colorCode.equals("0")){
            this.colorCode = "0";
        }
        else if (!colorCode.startsWith("0x")){
            if (Integer.parseInt(colorCode) >= 2000 && Integer.parseInt(colorCode) <= 6500){
                setKelvin(Integer.parseInt(colorCode));

            }
        }
        else if (codeChecker.codeChecker(colorCode) == 0){
            this.colorCode = colorCode;
        }else if (codeChecker.codeChecker(colorCode) == 1){
            throw new ERROR("Erroneous command!");
        } else if (codeChecker.codeChecker(colorCode) == 2) {
            throw new ERROR("Color code value must be in range of 0x0-0xFFFFFF!");

        }

    }

    /**
     * Constructs a new SmartLampWithColor object with the specified name.
     * @param name the name of the SmartLampWithColor.
     */

    public SmartLampWithColor(String name) {
        super(name);
        kelvin = 4000;
        brightness = 100;
        colorCode = "0";
    }


    public static class ColorCodeChecker {
        int j = 0;

        /**
         * This method checks the color code value is in range and is valid or invalid.
         * @param colorcode value of Smart Lamp with Color.
         * @return Integer for using in if else.
         */
        public int codeChecker(String colorcode) {
            if (colorcode.matches("^0[xX][0-9a-fA-F]{6}$")) {
                return j;
            } else if (colorcode.length() == 8 && colorcode.startsWith("0x")) {
                j = 1;
                return j;
            } else {
                j = 2;
                return j;
            }

        }
    }

    /**
     * @return a string representation of the Smart Lamp with Color object.
     * @throws RuntimeException if parsing error occurs when formatting the time.
     */
    public String toString(){
        GiveInformation information = new GiveInformation();
        try {
            if (getColorCode().equals("0")){
                return String.format("Smart Color Lamp %s is %s and its color value is %dK with %d%% brightness, and" +
                        " its time to switch its status is %s.",getName(),getStatus().toLowerCase(),getKelvin(),getBrightness(),information.returnFormattedTime(getSwitchTime()));
            }else {
                return String.format("Smart Color Lamp %s is %s and its color value is %s with %d%% brightness, and" +
                        " its time to switch its status is %s.",getName(),getStatus().toLowerCase(),getColorCode(),getBrightness(),information.returnFormattedTime(getSwitchTime()));
            }
        }catch (ParseException e){
            throw new RuntimeException(e);

        }

    }
}
