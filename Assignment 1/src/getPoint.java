// Points are collected in this part
public class getPoint {
    int getPoint(String value, int total){
        switch (value){
            case "R":
                total += 10;
                break;
            case "Y":
                total += 5;
                break;
            case "B":
                total -=5;
                break;
        }
        return total;
    }
}
