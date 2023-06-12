package src.main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class CloudDataPage extends JFrame{

    private TeachingApp teachingApp;

    public CloudDataPage(List<String> diagramNames, TeachingApp teachingApp) {
        this.teachingApp = teachingApp;

        setTitle("Cloud Saved Diagrams");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());

        JButton returnButton = new JButton("Return to Courses Page");
        contentPane.add(returnButton, BorderLayout.NORTH);


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
        contentPane.add(scrollPane, BorderLayout.CENTER);


        returnButton.addActionListener(e -> returnToCoursesPage());

        setContentPane(contentPane);
        setVisible(true);
    }

    private void returnToCoursesPage(){
        this.setVisible(false);
        teachingApp.setVisible(true);
        //CoursesPage coursesPage = new CoursesPage();
    }

    private void displayDiagram(String filename){
        SqlControlHandler.downloadFlowchart(filename);
        String trimmedStr = filename.substring(0, filename.length() - 5);
        FileManager.readFile(trimmedStr);
        this.setVisible(false);
        DiagramApp diagramApp = new DiagramApp(new TeachingApp(false));
        diagramApp.setVisible(true);
        diagramApp.setSize(700,700);
        diagramApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
