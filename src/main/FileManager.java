package main;

import java.io.*;
import java.util.ArrayList;
/**
 * @author Cameron Hardy
 * This class is responsible for saving and loading diagrams to/from file
 */
public class FileManager {
    /**
     * Reads the diagram with the given filename
     * First reads the file to construct the CodeBlocks present in the diagram
     * Then adds each line to the Repository from each outboundcodeblock reference list
     * @param filename - String representing filename to read from
     */
    public static void readFile(String filename) {
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        dataRepository.clear();

        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        // List to store read in blocks
        try {
            // Create an input stream, read in the file, casting to ArrayList
            FileInputStream fis = new FileInputStream(filename + ".diag");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Drawable> drawables = (ArrayList<Drawable>) ois.readObject();
            ois.close();
            dataRepository.addAll(drawables);
            stateRepository.setStatus("File Loaded");
        } catch (IOException | ClassNotFoundException e) {
            // Print Error if file not read
            e.printStackTrace();
            stateRepository.setStatus("Error reading file");
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void writeFile(String filename) {
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        try {
            // Open output stream, write the CodeBlocks from Repository
            FileOutputStream fos = new FileOutputStream(filename + ".diag");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dataRepository.getDrawables());
            oos.flush();
            oos.close();
            stateRepository.setStatus("File " + filename + ".diag saved");
        } catch (IOException e) {
            // Print error if it could not write to file
            e.printStackTrace();
            stateRepository.setStatus("Error writing to " + filename + ".diag");
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
