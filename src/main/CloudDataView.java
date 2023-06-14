package src.main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The CloudDataView class represents a view that displays the diagrams saved in the cloud.
 * It implements the AppPage interface.
 * It provides methods to show the contents of the view, retrieve header information, and handle diagram button clicks.
 *
 * The diagrams are retrieved from the cloud.
 * The view is displayed as a vertical list of diagram buttons.
 * Clicking on a diagram button opens the DiagramApp and displays the selected diagram.
 *
 * @author Alex Banham, Connor Hickey
 */
public class CloudDataView extends JPanel implements AppPage {

    private TeachingApp teachingApp;
    private List<String> diagramNames;

    /**
     * Constructs a CloudDataView object with the specified TeachingApp and list of diagram names.
     *
     * @param teachingApp the TeachingApp instance.
     * @param diagramNames the list of diagram names.
     */
    public CloudDataView(TeachingApp teachingApp, List<String> diagramNames) {
        this.teachingApp = teachingApp;
        this.diagramNames = diagramNames;
        setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        for (String diagramName : diagramNames) {
            JButton button = new JButton(diagramName);
            button.setForeground(Color.WHITE);
            button.setBackground(Color.decode("#80a1a1"));
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            button.setFont(new Font("Arial", Font.BOLD, 13));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.addActionListener(e -> displayDiagram(diagramName));
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 4),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                }
            });
            button.setUI(new BasicButtonUI());
            button.setPreferredSize(new Dimension(200, 40));
            buttonsPanel.add(button);
        }
        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Displays the selected diagram by downloading it from the cloud and reading it into the DiagramApp.
     *
     * @param filename the filename of the selected diagram.
     */
    private void displayDiagram(String filename){
        SqlControlHandler.downloadFlowchart(filename);
        String trimmedStr = filename.substring(0, filename.length() - 5);
        FileManager.readFile(trimmedStr);
        DiagramApp diagramApp = new DiagramApp(teachingApp);
        diagramApp.setVisible(true);
        diagramApp.setSize(700,700);
        teachingApp.setVisible(false);
    }

    /**
     * Shows the contents of the CloudDataView by making it visible.
     */
    @Override
    public void showContents() {
        this.setVisible(true);
    }

    /**
     * Retrieves the header information of the CloudDataView.
     * The header information consists of the string "Cloud Saved Diagrams".
     *
     * @return the string "Cloud Saved Diagrams".
     */
    @Override
    public String getHeaderInfo() {
        return "Cloud Saved Diagrams";
    }

}
