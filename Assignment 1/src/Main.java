import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        readFile(args[0]);
    }

    public static void readFile(Object value){


        try {
            Files.deleteIfExists(Paths.get("output.txt"));
            File file = new File((String) value);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                BufferedWriter writer;

                {
                    writer = new BufferedWriter(new FileWriter("output.txt",true));
                }
                String line = reader.nextLine();
                if (line.contains("Armstrong numbers up to:")){
                    line = reader.nextLine();
                    armstrongNumbers armstrongNumbers = new armstrongNumbers();
                    armstrongNumbers.armstongNumber(Integer.parseInt(line));
                } else if (line.contains("Emirp numbers up to:")) {
                    line = reader.nextLine();
                    emirpNumbers emirpNumbers = new emirpNumbers();
                    emirpNumbers.emirpnumbers(Integer.parseInt(line));
                } else if (line.contains("Abundant numbers up to:")) {
                    line = reader.nextLine();
                    abudantNumbers abudantNumbers = new abudantNumbers();
                    abudantNumbers.abudant(Integer.parseInt(line));
                } else if (line.contains("Ascending order sorting:")) {
                    ArrayList<Integer> numbers = new ArrayList<>();
                    writer.write("\n\nAscending order sorting:\n");
                    writer.close();
                    while (true){
                        line = reader.nextLine();
                        if ("-1".equalsIgnoreCase(line)){
                            break;
                        }
                        ascendingNumbers ascendingNumbers = new ascendingNumbers();
                        ascendingNumbers.ascend(Integer.parseInt(line),numbers);

                    }

                } else if (line.contains("Descending order sorting:")) {
                    ArrayList<Integer> numbers = new ArrayList<>();
                    writer.write("\nDescending order sorting:\n");
                    writer.close();
                    while (true){
                        line = reader.nextLine();
                        if ("-1".equalsIgnoreCase(line)){
                            break;
                        }
                        descendingNumbers descendingNumbers = new descendingNumbers();
                        descendingNumbers.descend(Integer.parseInt(line),numbers);
                    }
                } else if (line.contains("Exit")) {
                    writer.write("\n\nFinished...");
                    writer.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
