import java.io.IOException;
import java.text.ParseException;


public class Main {
    /**
     * The main method of the program that reads input from a file and writes output to another file.
     * @param args an array of command line arguments that specify the input and output file.
     * @throws IOException if there is an error while reading or writing file.
     * @throws ParseException if there is an error while parsing the input file.
     */
    public static void main(String[] args) throws IOException, ParseException {

        readAndWrite readInput = new readAndWrite();
        readInput.readInput(args[0],args[1]);
    }

}
