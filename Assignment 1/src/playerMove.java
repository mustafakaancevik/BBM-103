import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// In this part, I add a played move in a list, when game over I write them to txt file
public class playerMove {

    ArrayList moveList(Object listLocation){
        ArrayList board = new ArrayList();
        try {
            File movelist = new File(String.valueOf(listLocation));
            Scanner moves = new Scanner(movelist);

            while (moves.hasNext()){
                String move = moves.next();
                board.add(move);

            }
            return board;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
