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
        Repository dataRepository = DataRepository.getInstance();
        DrawableData drawableData = (DrawableData) dataRepository.getData();
        drawableData.clear();

        StateData stateData = (StateData) StateRepository.getInstance().getData();
        // List to store read in blocks
        try {
            // Create an input stream, read in the file, casting to ArrayList
            FileInputStream fis = new FileInputStream(filename + ".diag");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Drawable> drawables = (ArrayList<Drawable>) ois.readObject();
            ois.close();
            drawableData.addAll(drawables);
            stateData.setStatus("File Loaded");
        } catch (IOException | ClassNotFoundException e) {
            // Print Error if file not read
            e.printStackTrace();
            stateData.setStatus("Error reading file");
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void writeFile(String filename) {
        StateData stateData = (StateData) StateRepository.getInstance().getData();
        try {
            Repository dataRepository = DataRepository.getInstance();
            DrawableData drawableData = (DrawableData) dataRepository.getData();
            // Open output stream, write the CodeBlocks from Repository
            FileOutputStream fos = new FileOutputStream(filename + ".diag");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(drawableData.getDrawables());
            oos.flush();
            oos.close();
            stateData.setStatus("File " + filename + ".diag saved");
        } catch (IOException e) {
            // Print error if it could not write to file
            e.printStackTrace();
            stateData.setStatus("Error writing to " + filename + ".diag");
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
