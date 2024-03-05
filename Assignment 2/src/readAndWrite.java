import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class readAndWrite {
    /**
     * Reads input from a file, processes commands to manipulate smart devices,
     * and writes output to a file.
     * @param input  The input file path.
     * @param output The output file path.
     * @throws ParseException If there is an error parsing a date.
     * @throws IOException     If there is an error reading or writing to a file.
     */
    public void readInput(String input,String output) throws ParseException, IOException {
        Date initialTime = null;
        ArrayList<SmartDevices> smartDevices = new ArrayList<>();
        changeDevice changeDevice = new changeDevice();
        TimeChange timeChange = new TimeChange();
        GiveInformation information = new GiveInformation();
        setLampProperties setLampProperties = new setLampProperties();
        Files.deleteIfExists(Paths.get(output));
        Scanner scanner = null;

        try {
            File inputFile = new File(input);
            scanner = new Scanner(inputFile);
            if (inputFile.exists()) {
                String content = new String(Files.readAllBytes(inputFile.toPath()), StandardCharsets.UTF_8);
                if (content.trim().isEmpty()) {
                    writeInput(output, "The file is empty!");
                    System.exit(0);
                }
            }
        }catch (IOException e){
            writeInput(output,"There is no txt file!");
            System.exit(0);

        }






        boolean m = true;
        String[] row = new String[10];
        while (scanner.hasNextLine()) {
            try {String line = scanner.nextLine();
                line = line.trim();
                if (!line.isEmpty() && !line.equals("\t")){
                    row = line.split("\t");
                    writeInput(output,"COMMAND: "+ Arrays.toString(row).replaceAll("\\[|\\]|,", "").replaceAll(" ","\t"));
                    if (m){
                        if (!row[0].equals("SetInitialTime")) {
                            writeInput(output,"ERROR: First command must be set initial time! Program is going to terminate!");
                            System.exit(0);
                        }else {

                        }
                    }m = false;
                    switch (row[0]) {
                        case "SetInitialTime":
                            initialTime = timeChange.setInitialTime(row,output);
                            break;
                        case "SetTime":
                            initialTime = timeChange.setTime(row);
                            smartDevices= changeDevice.afterSetTime(smartDevices,initialTime);
                            break;
                        case "Switch":
                            smartDevices = changeDevice.switchStatus(smartDevices,row,initialTime);
                            break;
                        case "Nop":
                            initialTime = timeChange.nop(smartDevices,row);
                            smartDevices = timeChange.nopCheck(smartDevices,initialTime);
                            break;
                        case "ChangeName":
                            smartDevices = changeDevice.changeName(smartDevices,row);
                            break;
                        case "Add":
                            switch (row[1]){
                                case "SmartPlug":
                                    smartDevices.add(changeDevice.addSmartPlug(row,initialTime,smartDevices));
                                    break;
                                case "SmartCamera":
                                    smartDevices.add(changeDevice.addSmartCamera(row,initialTime,smartDevices));
                                    break;
                                case "SmartLamp":
                                    smartDevices.add(changeDevice.addSmartLamp(row,smartDevices));
                                    break;
                                case "SmartColorLamp":
                                    smartDevices.add(changeDevice.addSmartLampWithColor(row,smartDevices));
                                    break;
                            }
                            break;
                        case "Remove":
                            smartDevices = changeDevice.deleteDevice(row[1],smartDevices,output,initialTime) ;
                            break;
                        case "PlugIn":
                            smartDevices = changeDevice.plugIn(smartDevices,row,initialTime);
                            break;
                        case "PlugOut":
                            smartDevices = changeDevice.plugOut(smartDevices,row,initialTime);
                            break;
                        case "SetKelvin":
                            smartDevices = setLampProperties.setKelvin(row,smartDevices);
                            break;
                        case "SetBrightness":
                            smartDevices = setLampProperties.setBrightness(row,smartDevices);
                            break;
                        case "SetColorCode":
                            smartDevices = setLampProperties.setColorCode(row,smartDevices);
                            break;
                        case "SetWhite":
                            smartDevices = setLampProperties.setWhite(row,smartDevices);
                            break;
                        case "SetColor":
                            smartDevices = setLampProperties.setColor(row,smartDevices);
                            break;
                        case "SetSwitchTime":
                            smartDevices = timeChange.setSwitchTime(smartDevices,row,initialTime);
                            break;
                        case "ZReport":
                            information.GiveInformation(smartDevices,initialTime,output);
                            break;
                        case "SkipMinutes":
                            initialTime = timeChange.skipMinutes(row);
                            break;

                        case "":
                            break;
                        default:
                            writeInput(output,"ERROR: Erroneous command!");
                    }
                }

            }
            catch (ERROR e){
                writeInput(output,"ERROR: " + e.getMessage());

            } catch (ParseException e) {
                writeInput(output,"Hatalı tarih");
            }catch (NumberFormatException e){
                writeInput(output,"ERROR: Erroneous command!");
            }

        }GiveInformation information1 = new GiveInformation();
        if (!row[0].equals("ZReport")){
            writeInput(output,"ZReport:");
            information1.GiveInformation(smartDevices,initialTime,output);
        }
        scanner.close();
    }

    /**
     * Checks if there is already a smart device with the same name in the list of smart devices.
     * @param row the input command in the form of an array of strings.
     * @param smartDevices the list of smart devices to check.
     * @throws ERROR if there is already a smart device with the same name in the list.
     */
    public void chechkDevice(String[] row,ArrayList<SmartDevices> smartDevices ) throws ERROR {

        for (int x = 0; x < smartDevices.size();x++) {
            if (row[2].equals(smartDevices.get(x).getName())) {
                throw new ERROR("There is already a smart device with same name!");

            }

        }

    }

    /**
     * Writes a given string value to the specified output file.
     * @param output the output file path.
     * @param value the string value to be written to the file.
     * @throws IOException if there is an error writing to the file.
     */
    public void writeInput(String output,String value) throws IOException {
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter(output, true));
        writer.write(value+"\n");
        writer.close();
    }
}
