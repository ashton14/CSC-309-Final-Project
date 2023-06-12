package src.main;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Stack;

/**
 * The TeachingApp class represents the main application for the teaching app.
 * It manages the pages stack, frame, buttons, header panel, side panel, and UI components.
 * The TeachingApp class acts as a controller between different app pages.
 *
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

    /**
     * Constructs a TeachingApp object.
     * @param isTesting indicates whether the app is in testing mode.
     */
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

    /**
     * Updates the UI components based on the current page.
     */
    public void updateUIComponents() {

        String className = pages.peek().getClass().getSimpleName();
        addDashboardHeader(pages.peek().getHeaderInfo());

        switch (className) {
            case "LoginPage":
                logoutButton.setVisible(false);
                backButton.setVisible(false);
                messageButton.setVisible(false);
                frame.getContentPane().remove(sidePanel);
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

            case "CloudDataView":
                backButton.setVisible(true);
                logoutButton.setVisible(true);
                setSidePanel(generateDashboardSidePanel());
                messageButton.setVisible(true);
                break;

            default:
                break;
        }
    }

    /**
     * Shows the current page on the frame and updates the UI components.
     */
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

    /**
     * Adds the dashboard header panel to the frame with the specified header text.
     * @param headerText the header text to display.
     */
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
            for (AppPage page : pages) {
                if (page instanceof CourseView) {
                    courses = ((CourseView) page).getCourses();
                    break;
                }
            }

            if (courses != null) {
                pushPage(new MessagesPage(courses));
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

    /**
     * Sets the side panel with the provided panel.
     * @param newPanel the new side panel to set.
     */
    public void setSidePanel(JPanel newPanel) {
        frame.getContentPane().remove(sidePanel);
        sidePanel = newPanel;
        frame.getContentPane().add(sidePanel, BorderLayout.WEST);
        frame.validate();
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Generates the dashboard side panel with the sandbox and cloud buttons.
     * @return the generated dashboard side panel.
     */
    private JPanel generateDashboardSidePanel() {
        JPanel panel = new JPanel();

        JButton sandboxButton = new JButton("Sandbox");
        sandboxButton.addActionListener(e -> {
            showSandbox();
        });

        JButton cloudCoursesButton = new JButton("Cloud");
        cloudCoursesButton.addActionListener(e -> {
            pushPage(new CloudDataView(this, SqlControlHandler.getFlowchartFileNames()));
            /*frame.setVisible(false);
            CloudDataPage cdp = new CloudDataPage(SqlControlHandler.getFlowchartFileNames(), this);
            cdp.setVisible(true);*/
        });

        panel.setLayout(new GridLayout(2, 1));
        panel.add(sandboxButton);
        panel.add(cloudCoursesButton);

        return panel;
    }

    /**
     * Pushes a new page to the pages stack, shows the current page, and updates the UI components.
     * @param page the page to push.
     */
    public void pushPage(AppPage page) {
        this.pages.push(page);
        showCurrentPage();
    }

    /**
     * Logs out the user by clearing the pages stack, showing the login page, and updating the UI components.
     */
    private void logout() {
        if (pages.peek() instanceof DiagramApp) {
            DiagramApp diagramApp = (DiagramApp) pages.pop();
            diagramApp.dispose();
        }

        this.pages.clear();
        LoginPage loginPage = new LoginPage(isTesting, this);
        pushPage(loginPage);
        frame.setVisible(true);

    }

    /**
     * Goes back to the previous page by popping the current page from the stack,
     * showing the current page, and updating the UI components.
     */
    public void goBack() {
        if (!this.pages.isEmpty()) {
            this.pages.pop();
            showCurrentPage();
        }
    }

    /**
     * Sets the visibility of the TeachingApp frame.
     * @param visible true to make the frame visible, false otherwise.
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void showSandbox() {
        DiagramApp diagramApp = new DiagramApp(this);
        diagramApp.setVisible(true);
        diagramApp.setSize(900, 900);
        diagramApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    /**
     * The main method to start the TeachingApp application.
     * @param args command-line arguments (unused).
     */
    public static void main(String[] args) {
        boolean isTesting = true;  // change to true for testing mode
        TeachingApp app = new TeachingApp(isTesting);

        LoginPage loginPage = new LoginPage(isTesting, app);
        app.pushPage(loginPage);
    }
}
