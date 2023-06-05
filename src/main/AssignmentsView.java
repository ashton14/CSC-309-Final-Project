package src.main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Connor Hickey
 */
class AssignmentsView extends JPanel implements AppPage {

    private List<String> assignments;

    public AssignmentsView() {
        this.assignments = new ArrayList<>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // set layout to BoxLayout for vertical arrangement
    }

    @Override
    public void showContents() {
        this.removeAll();
        Random rand = new Random();

        for (String assignment : this.assignments) {
            JButton assignmentButton = new JButton(assignment);
            assignmentButton.setUI(new RoundedButtonUI(800, 80));
            assignmentButton.setBackground(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            this.add(assignmentButton);
            this.add(Box.createRigidArea(new Dimension(0, 15))); // add spacing between buttons
        }
        this.revalidate();
        this.repaint();
    }

    public List<String> getAssignments() {
        return this.assignments;
    }

    public void setAssignments(List<String> assignments) {
        this.assignments = assignments;
    }
}
