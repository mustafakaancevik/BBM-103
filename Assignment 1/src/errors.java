
// In this part I write a special error exception for our assignment
public class errors extends Exception{
    void moveListAmount(int movelist){
        if (movelist >30){
            System.out.println("You can't move more than 30 moves.");
            throw new IndexOutOfBoundsException("Error!");
        }


    }
    void boardSize(int rows,int columns){
        if (rows>20 || columns>20){
            System.out.println("Board size can't be more than 20 rows or columns.");
            throw new IndexOutOfBoundsException("Error!");
        }
    }

}
