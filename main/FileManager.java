package main;

import java.io.*;
import java.util.*;
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
        Repository.getInstance().clear();
        // List to store read in blocks
        ArrayList<CodeBlock> blocks = new ArrayList<>();
        try {
            // Create an input stream, read in the file, casting to ArrayList
            FileInputStream fis = new FileInputStream(filename + ".diag");
            ObjectInputStream ois = new ObjectInputStream(fis);
            blocks = (ArrayList<CodeBlock>) ois.readObject();
            ois.close();
            // For each CodeBlock, add line to outbound CodeBlocks
            for(CodeBlock b : blocks) {
                for(CodeBlock out : b.getOutboundCodeBlocks()) {
                    Repository.getInstance().addLine(new Line(b, out));
                }
            }
            Repository.getInstance().setCodeBlocks(blocks);
        } catch (IOException | ClassNotFoundException e) {
            // Print Error if file not read
            e.printStackTrace();
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void writeFile(String filename) {
        try {
            // Open output stream, write the CodeBlocks from Repository
            FileOutputStream fos = new FileOutputStream(filename + ".diag");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Repository.getInstance().getCodeBlocks());
            oos.flush();
            oos.close();
        } catch (IOException e) {
            // Print error if it could not write to file
            e.printStackTrace();
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
