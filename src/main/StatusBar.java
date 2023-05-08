package main;
import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Ashton Alonge
 * class for the status bar to show what the user is doing
 */
public class StatusBar extends JTextArea implements Observer {

    /**
     * Constructor for status bar
     * @param text initial text
     */
    public StatusBar(String text){
        setText(text);
    }

    /**
     * changes the output of the status bar when observer is notified
     * @param o observable
     * @param arg the updated text(output)
     */
    @Override
    public void update(Observable o, Object arg) {
        String status = (String)arg;
        if(status != null) {
            setText("Status: " + status);
        }
    }
}
