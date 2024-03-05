import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// I use this part for write some info to txt file
public class write {
    void write(String line){
        BufferedWriter writer;
        {
            try {
                writer = new BufferedWriter(new FileWriter("output.txt", true));

                writer.write(line+"\n");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    void writePlayermoves(ArrayList moves,int move){
        BufferedWriter writer;
        {
            try {
                writer = new BufferedWriter(new FileWriter("output.txt", true));
                writer.write("\nYour movement is:\n");
                for (int i = 0; i < move;i++){
                    if (i != move ){
                        writer.write((String) moves.get(i)+" ");
                    } else if (move == i) {
                        writer.write((String) moves.get(move)+" ");
                    }

                }writer.write("\n");
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
