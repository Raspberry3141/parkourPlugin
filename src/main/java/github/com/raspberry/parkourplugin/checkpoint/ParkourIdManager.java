package github.com.raspberry.parkourplugin.checkpoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ParkourIdManager {
    private long id;
    private boolean canWrite = false;

    public ParkourIdManager() {
        try {
            File myObj = new File("C:\\Database\\parkourID.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            id = Long.parseLong(data);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        canWrite = true;
    }


    public long updateIdNumber() {
        canWrite = false;
        ++id;
        try {
            FileWriter myWriter = new FileWriter("C:\\Database\\parkourID.txt");
            myWriter.write(String.valueOf(id));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch ( IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        canWrite = true;
        return id;
    }
}
