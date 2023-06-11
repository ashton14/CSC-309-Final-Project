package src.main;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Stack;

/**
 * @author Connor Hickey
 */
public class TeachingApp {

    private Stack<AppPage> pages;
    private JFrame frame;
    private JButton backButton;
    private JButton messageButton;
    private JButton logoutButton;
    private JPanel headerPanel;
    private JPanel sidePanel;
    private boolean isTesting;

    public TeachingApp(boolean isTesting) {
        this.pages = new Stack<>();
        this.frame = new JFrame("Teaching App");
        this.frame.setSize(1000, 1600);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.isTesting = isTesting;
        this.sidePanel = new JPanel();

        backButton = new JButton("< Back");
        backButton.addActionListener(e -> goBack());

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());


        // Create a dummy headerPanel initially, this will be updated based on the page being shown
        addDashboardHeader("");
    }

    public void updateUIComponents() {

        String className = pages.peek().getClass().getSimpleName();
        addDashboardHeader(pages.peek().getHeaderInfo());

        switch (className) {
            case "LoginPage":
                logoutButton.setVisible(false);
                backButton.setVisible(false);
                messageButton.setVisible(false);
                break;

            case "DiagramApp":
                logoutButton.setVisible(true);
                backButton.setVisible(true);
                break;

            case "CourseView":
                backButton.setVisible(false);
                logoutButton.setVisible(true);
                setSidePanel(generateDashboardSidePanel());
                break;

            case "AssignmentsView":
                backButton.setVisible(true);
                logoutButton.setVisible(true);
                setSidePanel(generateDashboardSidePanel());
                break;

            case "MessagesPage":
                backButton.setVisible(true);
                logoutButton.setVisible(true);
                setSidePanel(generateDashboardSidePanel());
                messageButton.setVisible(false);
                break;

            default:
                break;
        }
    }

    private void showCurrentPage() {
        Component[] components = this.frame.getContentPane().getComponents();
        for (Component component : components) {
            if (BorderLayout.CENTER.equals(((BorderLayout) this.frame.getContentPane().getLayout()).getConstraints(component))) {
                this.frame.getContentPane().remove(component);
                break;
            }
        }

        if (!this.pages.isEmpty()) {
            this.pages.peek().showContents();
            this.frame.getContentPane().add((Component) this.pages.peek(), BorderLayout.CENTER);
        }

        updateUIComponents();

        this.frame.validate();
        this.frame.repaint();
        this.frame.setVisible(true);
    }

    private void addDashboardHeader(String headerText) {
        // Remove the old headerPanel from the frame if it exists
        if (headerPanel != null) {
            frame.getContentPane().remove(headerPanel);
        }

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLUE);
        headerPanel.setPreferredSize(new Dimension(800, 70));

        JLabel titleLabel = new JLabel(headerText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);

        messageButton = new JButton("Message");
        messageButton.addActionListener(e -> {
            List<Course> courses = null;
            for(AppPage page : pages){
                if(page instanceof CourseView){
                    courses = ((CourseView) page).getCourses();
                    break;
                }
            }

            if(courses != null){
            pushPage(
                new MessagesPage(
                    courses
                )
            );
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(messageButton);
        buttonPanel.add(logoutButton);

        headerPanel.add(backButton, BorderLayout.LINE_START); // back button on the right
        headerPanel.add(titleLabel, BorderLayout.CENTER); // title in the center
        headerPanel.add(buttonPanel, BorderLayout.LINE_END);

        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
    }

    public void setSidePanel(JPanel newPanel) {
        frame.getContentPane().remove(sidePanel);
        sidePanel = newPanel;
        frame.getContentPane().add(sidePanel, BorderLayout.WEST);
        frame.validate();
        frame.revalidate();
        frame.repaint();
    }


    private JPanel generateDashboardSidePanel() {
        JPanel panel = new JPanel();

        JButton sandboxButton = new JButton("Sandbox");
        sandboxButton.addActionListener(e -> {
            DiagramApp diagramApp = new DiagramApp(this);
            diagramApp.setVisible(true);
            diagramApp.setSize(900, 900);
            diagramApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(false);
        });


        JButton cloudCoursesButton = new JButton("Cloud");
        cloudCoursesButton.addActionListener(e -> {
            // logic to show cloud courses
        });

        panel.setLayout(new GridLayout(2, 1));
        panel.add(sandboxButton);
        panel.add(cloudCoursesButton);

        return panel;
    }

    public void pushPage(AppPage page) {
        this.pages.push(page);
        showCurrentPage();
    }

    private void logout() {
        this.pages.clear();
        LoginPage loginPage = new LoginPage(isTesting, this);
        pushPage(loginPage);
    }

    public void goBack() {
        if (!this.pages.isEmpty()) {
            this.pages.pop();
            showCurrentPage();
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public static void main(String[] args) {
        boolean isTesting = true;  // change to true for testing mode
        TeachingApp app = new TeachingApp(isTesting);

        LoginPage loginPage = new LoginPage(isTesting, app);
        app.pushPage(loginPage);
    }
}
