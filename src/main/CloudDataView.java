package src.main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CloudDataView extends JPanel implements AppPage {

    private TeachingApp teachingApp;
    private List<String> diagramNames;

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

    private void displayDiagram(String filename){
        SqlControlHandler.downloadFlowchart(filename);
        String trimmedStr = filename.substring(0, filename.length() - 5);
        FileManager.readFile(trimmedStr);
        DiagramApp diagramApp = new DiagramApp(teachingApp);
        diagramApp.setVisible(true);
        diagramApp.setSize(700,700);
        teachingApp.setVisible(false);
    }

    @Override
    public void showContents() {
        this.setVisible(true);
    }

    @Override
    public String getHeaderInfo() {
        return "Cloud Saved Diagrams";
    }

    public List<String> getDiagramNames() {
        return diagramNames;
    }

    public void setDiagramNames(List<String> diagramNames) {
        this.diagramNames = diagramNames;
    }
}
