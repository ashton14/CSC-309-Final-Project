package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.plaf.basic.BasicButtonUI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Connor Hickey
 */

public class CoursesPage {
    private JFrame frame;
    public JPanel coursePanel;
    public JPanel assignmentPanel;
    private JButton backButton;
    public Map<String, List<String>> courseAssignments;
    private Color shadowColor = new Color(0, 0, 0, 50);

    public Color generateRandomColor() {
        float hue = (float) Math.random();
        float saturation = (float) (Math.random() * 0.5f + 0.5f);
        float brightness = (float) (Math.random() * 0.5f + 0.5f);
        return Color.getHSBColor(hue, saturation, brightness);
    }


    public CoursesPage() {
        frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(generateRandomColor());
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 70));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);

        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setBackground(generateRandomColor());
        dashboardPanel.setPreferredSize(new Dimension(100, frame.getHeight()));
        dashboardPanel.setLayout(new BorderLayout());


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        coursePanel = new JPanel();
        coursePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        addCourse("Course 1");
        addCourse("Course 2");
        addCourse("Course 3");

        JButton sandboxButton = new JButton("Sandbox");
        sandboxButton.setForeground(Color.WHITE);
        sandboxButton.setBackground(generateRandomColor());
        sandboxButton.setBorderPainted(false);
        sandboxButton.setFocusPainted(false);
        sandboxButton.setContentAreaFilled(true);
        sandboxButton.setOpaque(true);
        sandboxButton.setFont(new Font("Arial", Font.BOLD, 13));
        sandboxButton.addActionListener(e -> showSandbox());
        sandboxButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sandboxButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(shadowColor, 4),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sandboxButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            }
        });
        sandboxButton.setUI(new BasicButtonUI());
        dashboardPanel.add(sandboxButton);


        assignmentPanel = new JPanel();
        assignmentPanel.setLayout(new BoxLayout(assignmentPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(assignmentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(coursePanel, BorderLayout.WEST);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        frame.getContentPane().add(dashboardPanel, BorderLayout.WEST);
        frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        courseAssignments = new HashMap<>();
        courseAssignments.put("Course 1", List.of("Assignment 1", "Assignment 2", "Assignment 3"));
        courseAssignments.put("Course 2", List.of("Assignment 4", "Assignment 5", "Assignment 6"));
        courseAssignments.put("Course 3", List.of("Assignment 7", "Assignment 8", "Assignment 9"));
    }

    public void addCourse(String courseName) {
        JButton courseButton = new JButton(courseName);
        courseButton.setForeground(Color.WHITE);
        courseButton.setBackground(generateRandomColor());
        courseButton.setBorderPainted(false);
        courseButton.setFocusPainted(false);
        courseButton.setContentAreaFilled(false);
        courseButton.setOpaque(true);
        courseButton.setPreferredSize(new Dimension(200, 200));
        courseButton.setMaximumSize(new Dimension(200, 200));
        courseButton.setMinimumSize(new Dimension(200, 200));
        courseButton.setFont(new Font("Arial", Font.BOLD, 18));
        courseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAssignmentsForCourse(courseName);
            }
        });
        courseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                courseButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(shadowColor, 4),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                courseButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            }
        });
        courseButton.setUI(new RoundedButtonUI());
        coursePanel.add(courseButton);
    }

    public void showAssignmentsForCourse(String courseName) {
        coursePanel.setVisible(false);
        assignmentPanel.removeAll();
        assignmentPanel.revalidate();
        assignmentPanel.repaint();

        JLabel courseLabel = new JLabel(courseName);
        courseLabel.setFont(new Font("Arial", Font.BOLD, 24));
        courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        courseLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        assignmentPanel.add(courseLabel);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCourseList();
            }
        });
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        assignmentPanel.add(backButton);
        assignmentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        List<String> assignments = courseAssignments.get(courseName);
        for (String assignment : assignments) {
            JButton assignmentButton = new JButton(assignment);
            assignmentButton.setForeground(Color.WHITE);
            assignmentButton.setBackground(generateRandomColor());
            assignmentButton.setBorderPainted(false);
            assignmentButton.setFocusPainted(false);
            assignmentButton.setContentAreaFilled(false);
            assignmentButton.setOpaque(true);
            assignmentButton.setPreferredSize(new Dimension(400, 75));
            assignmentButton.setMaximumSize(new Dimension(400, 75));
            assignmentButton.setMinimumSize(new Dimension(400, 75));
            assignmentButton.setFont(new Font("Arial", Font.BOLD, 18));
            assignmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // handle assignment button click
                }
            });
            assignmentButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    assignmentButton.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(shadowColor, 4),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    assignmentButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                }
            });
            assignmentButton.setUI(new RoundedButtonUI());
            assignmentPanel.add(assignmentButton);
            assignmentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        frame.revalidate();
        frame.repaint();
    }


    public void showCourseList() {
        coursePanel.setVisible(true);
        assignmentPanel.removeAll();
        assignmentPanel.revalidate();
        assignmentPanel.repaint();
        frame.revalidate();
        frame.repaint();
    }

    private void showSandbox() {
        frame.setVisible(false);
        DiagramApp app = new DiagramApp();
        app.setVisible(true);
        app.setSize(700,700);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public class RoundedButtonUI extends BasicButtonUI {
        private Color shadowColor = new Color(0, 0, 0, 50); // semi-transparent black for drop shadow

        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            AbstractButton button = (AbstractButton) c;
            button.setOpaque(false);
            button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int x = 0;
            int y = 0;
            int w = b.getWidth();
            int h = b.getHeight();
            int arc = h / 2;
            if (b.getModel().isPressed()) {
                g2.setColor(shadowColor);
                g2.fill(new RoundRectangle2D.Double(x + 2, y + 2, w - 4, h - 4, arc, arc));
                g2.setColor(b.getBackground());
                g2.fill(new RoundRectangle2D.Double(x, y, w - 4, h - 4, arc, arc));
            } else {
                g2.setColor(shadowColor);
                g2.fill(new RoundRectangle2D.Double(x + 2, y + 2, w - 4, h - 4, arc, arc));
                g2.setColor(b.getBackground());
                g2.fill(new RoundRectangle2D.Double(x, y, w - 4, h - 4, arc, arc));
            }
            super.paint(g2, c);
        }

        @Override
        protected void paintButtonPressed(Graphics g, AbstractButton b) {
            // don't paint the default button pressed state
        }

        @Override
        public Dimension getPreferredSize(JComponent c) {
            AbstractButton b = (AbstractButton) c;
            Dimension d = super.getPreferredSize(c);
            d.width = Math.max(d.width, d.height);
            return d;
        }
    }
}
