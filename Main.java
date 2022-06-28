import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Main {

    public static int PLANE_SIZE = 100;
    public static int COL_SIZE   = 2;
    public static int NUM_COLS   = 2;

    public static int NUM_TESTS  = 100;
    
    public static void main(String[] args) throws Exception {

        Plane plane         = new Plane(PLANE_SIZE, COL_SIZE, NUM_COLS);
        Boarding boarding   = new Boarding(plane);

        createTests(boarding, plane, "Random");
        createTests(boarding, plane, "AmericanFirst");
        createTests(boarding, plane, "AmericanLast");
        createTests(boarding, plane, "Southwest");
    }

    public static void createTests(Boarding boarding, Plane plane, String testType){
        LineFactory lineFactory = new LineFactory();

        String fileName = testType + ".txt";
        String data = "";

        createFile(fileName);

        for (int i = 0; i < NUM_TESTS; i++)
            data += boarding.board(lineFactory.createLine(plane, testType)) + "\n";

        createOutput(fileName, data);
    }

    public static boolean createFile(String fileName){
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
              return true;
            } else {
              System.out.println("File already exists.");
              return false;
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
          }
    }

    public static boolean createOutput(String fileName, String data){
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(data);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            return true;
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
          }
    }
}
