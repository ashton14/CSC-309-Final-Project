package src.main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Ashton Alonge
 * @author Alex Banham
 * Controller for the menu bar
 */
public class MenuBarControlHandler implements ActionListener {

    private TeachingApp teachingApp;
    private DiagramApp diagramApp;
    /**
     * Constructor
     */
    public MenuBarControlHandler(TeachingApp teachingApp, DiagramApp diagramApp) {
        this.teachingApp = teachingApp;
        this.diagramApp = diagramApp;
    }

    /**
     * Method to control action when any menu item is selected
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        switch (e.getActionCommand()) {
            case "New":
                dataRepository.clearFlowchart();
                stateRepository.setStatus("New File Created.");
                System.out.println("New");
                break;

            case "Save":
                stateRepository.setStatus("Attempting to save file");
                String saveFile = (String) JOptionPane.showInputDialog(
                        null,
                        "Select a file name to save your diagram",
                        "Save file",
                        1, null, null, "myDiagram");
                if (saveFile != null && saveFile.length() > 0) {
                    FileManager.writeFile(saveFile);
                } else {
                    stateRepository.setStatus("Please select a valid file name");
                }
                break;
            case "Load":
                stateRepository.setStatus("Attempting to load file.");
                String loadFile = (String) JOptionPane.showInputDialog(
                        null,
                        "Select a save file to load a diagram",
                        "Load file",
                        1);
                if (loadFile != null && loadFile.length() > 0) {
                    FileManager.readFile(loadFile);
                    //drawableData.modifiedDrawables();
                } else {
                    stateRepository.setStatus("Please select a valid save file");
                }
                break;
            case "Upload":
                stateRepository.setStatus("Attempting to upload file.");
                String uploadFile = (String) JOptionPane.showInputDialog(
                        null,
                        "Select a save file to upload a diagram",
                        "Upload file",
                        1);
                if (uploadFile != null && uploadFile.length() > 0) {
                    if (SqlControlHandler.uploadFlowchart(uploadFile)){
                        stateRepository.setStatus("File uploaded successfully");
                    }
                } else {
                    stateRepository.setStatus("Please select a valid save file");
                }
                break;
            case "Courses":
                teachingApp.setVisible(true); // Show the TeachingApp frame
                diagramApp.dispose();
                break;


            case  "About":
                stateRepository.setSelectedMenuItem("About");
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Welcome to version 1.1 of the Diagram App.\nThis is an overview of the features and how to use them.\nBy clicking on \"File\", you have the options to make a new diagram,\nsave your diagram, or load an existing diagram.\nBy clicking on help, you managed to open this dialog.\nClicking on Actions gives the options to clear the diagram or undo the last action.\nIf you click on Start, you can select which component you would like to draw.\nClicking anywhere in the area will draw the selected shape at that location.\nIf you scroll down in the shapes menu and select connections, you can draw lines\nbetween shapes by clicking them consecutively.\nAuthors:\nAshton Alonge\nAaron Bettencourt\nAlex Banham\nConnor Hickey" +
                                "\nPatrick Whitlock\nCameron Hardy");
                break;
                case "Undo":
                    dataRepository.undo();
                    break;
                case "Clear Flowchart":
                    dataRepository.clearFlowchart();
                    break;
                case "Clear Code":
                    FlowchartProblemViewControlHandler.clearCode();
                    break;
                case "Delete":
                    stateRepository.deleteSelectedItem();
                    break;
                case "Change Text":
                    String text = (String) JOptionPane.showInputDialog(
                            null,
                            "Rename text from: \"" + stateRepository.getCurrentlySelectedCodeBlock().getText() + "\" to:",
                            "Rename the " + stateRepository.getCurrentlySelectedCodeBlock().toString() + " Block",
                            1, null, null, stateRepository.getCurrentlySelectedCodeBlock().getText());
                    stateRepository.getCurrentlySelectedCodeBlock().setText(text);
                    dataRepository.modifiedDrawables();
                    break;
                case "Sandbox":
                    stateRepository.changeMode("Sandbox");
                    break;
                case "Translate Code":
                    stateRepository.changeMode("Translate Code");
                    //clear flowchart
                    dataRepository.clearFlowchart();
                    break;
                case "Translate Flowchart":
                    stateRepository.changeMode("Translate Flowchart");
                    //DataRepository.getInstance().removeCodeBlockSelection();
                    //load a flowchart for the user to write code for
                    ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
                        pRepo.setCurrentProblem();
                    break;

            }
            if (e.getActionCommand().equals("comboBoxChanged")) {
                JComboBox tmp = (JComboBox) e.getSource();
                System.out.println((String) tmp.getSelectedItem());
                stateRepository.setMenuBarCodeBlock((String) tmp.getSelectedItem());
            }
        }
    }
