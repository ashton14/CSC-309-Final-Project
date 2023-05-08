package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Ashton Alonge
 * @author Alex Banham
 * Controller for the menu bar
 */
public class MenuBarControlHandler implements ActionListener {

    /**
     * Constructor
     */
    public MenuBarControlHandler() {
    }

    /**
     * Method to control action when any menu item is selected
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Repository dataRepository = DataRepository.getInstance();
        DrawableData drawableData = (DrawableData) dataRepository.getData();
        StateRepository stateRepository = StateRepository.getInstance();
        StateData stateData = (StateData) stateRepository.getData();
        switch (e.getActionCommand()) {
            case "New":
                drawableData.clear();
                stateData.setStatus("New File Created.");
                System.out.println("New");
                break;

            case "Save":
                stateData.setStatus("Attempting to save file");
                String saveFile = (String) JOptionPane.showInputDialog(
                        null,
                        "Select a file name to save your diagram",
                        "Save file",
                        1, null, null, "myDiagram");
                if (saveFile != null && saveFile.length() > 0) {
                    FileManager.writeFile(saveFile);
                } else {
                    stateData.setStatus("Please select a valid file name");
                }
                break;
            case "Load":
                stateData.setStatus("Attempting to load file.");
                String loadFile = (String) JOptionPane.showInputDialog(
                        null,
                        "Select a save file to load a diagram",
                        "Load file",
                        1);
                if (loadFile != null && loadFile.length() > 0) {
                    FileManager.readFile(loadFile);
                    //drawableData.modifiedDrawables();
                } else {
                    stateData.setStatus("Please select a valid save file");
                }
                break;
            case  "About":
                stateData.setSelectedMenuItem("About");
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Welcome to version 1.1 of the Diagram App.\nThis is an overview of the features and how to use them.\nBy clicking on \"File\", you have the options to make a new diagram,\nsave your diagram, or load an existing diagram.\nBy clicking on help, you managed to open this dialog.\nClicking on Actions gives the options to clear the diagram or undo the last action.\nIf you click on Start, you can select which component you would like to draw.\nClicking anywhere in the area will draw the selected shape at that location.\nIf you scroll down in the shapes menu and select connections, you can draw lines\nbetween shapes by clicking them consecutively.\nAuthors:\nAshton Alonge\nAaron Bettencourt\nAlex Banham\nConnor Hickey" +
                                "\nPatrick Whitlock\nCameron Hardy");
                break;
                case "Undo":
                    drawableData.undo();
                    break;
                case "Clear":
                    drawableData.clear();
                case "Delete":
                    stateData.deleteSelectedItem();
                    break;
                case "Change Text":
                    CodeBlock currentlySelectedCodeBlock = stateData.getCurrentlySelectedCodeBlock();
                    if(currentlySelectedCodeBlock == null){
                        stateData.setStatus("No code block is selected");
                        break;
                    }
                    String text = (String) JOptionPane.showInputDialog(
                            null,
                            "Rename text from: \"" + currentlySelectedCodeBlock.getText() + "\" to:",
                            "Rename the " + currentlySelectedCodeBlock.toString() + " Block",
                            1, null, null, "");
                    stateData.getCurrentlySelectedCodeBlock().setText(text);
                    drawableData.modifiedDrawables();
                    stateData.setStatus("Renamed " + currentlySelectedCodeBlock.toString() + " Block");
                    break;
                case "Sandbox":
                    stateData.setMode("Sandbox");
                    break;
                case "Translate Code":
                    stateData.setMode("Translate Code");
                    break;
                case "Translate Flowchart":
                    stateData.setMode("Translate Flowchart");

            }
            if (e.getActionCommand().equals("comboBoxChanged")) {
                JComboBox tmp = (JComboBox) e.getSource();
                System.out.println((String) tmp.getSelectedItem());
                stateData.setMenuBarCodeBlock((String) tmp.getSelectedItem());
            }
        }
    }
