package src.main;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Ashton Alonge, Aaron Bettencourt, Connor Hickey
 * main class
 * frame with menu bar, drawing area, and status bar
 */
public class DiagramApp extends JFrame implements Observer {

    private TeachingApp teachingApp;

    private JPanel westPanel;
    /**
     * Constructor to add menu bar with 4 menus, a drawing area, and a status bar
     */
    public DiagramApp(TeachingApp teachingApp){
        super("Intelligent Tutor App");
        this.teachingApp = teachingApp;
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();

        westPanel = new JPanel();
        westPanel.setVisible(false);

        JMenuBar menuBar = new JMenuBar();
        WorkingArea drawPanel = new WorkingArea();
        StatusBar statusBar = new StatusBar("Status");

        dataRepository.addObserver(drawPanel);
        stateRepository.addObserver(statusBar);
        stateRepository.addObserver(this);


        statusBar.setEnabled(false);
        statusBar.setFont(new Font(Font.DIALOG,Font.PLAIN,17));
        statusBar.setDisabledTextColor(Color.BLACK);

        statusBar.setPreferredSize(new Dimension(700,30));
        WorkingAreaControlHandler drawAreaController = new WorkingAreaControlHandler();

        drawPanel.setBackground(Color.GRAY);
        drawPanel.addMouseListener(drawAreaController);
        drawPanel.addMouseMotionListener(drawAreaController);

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        JMenu actions = new JMenu("Actions");
        //JMenu mode = new JMenu("Mode");

        JMenuItem newFile = new JMenuItem("New");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem upload = new JMenuItem("Upload");
        JMenuItem courses = new JMenuItem("Courses");
        JMenuItem about = new JMenuItem("About");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem clearFlowchart = new JMenuItem("Clear Flowchart");
        JMenuItem clearCode = new JMenuItem("Clear Code");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem changeText = new JMenuItem("Change Text");
        JMenuItem sandbox = new JMenuItem("Sandbox");
        JMenuItem translateCode = new JMenuItem("Translate Code");
        JMenuItem translateFlowchart = new JMenuItem("Translate Flowchart");


        String [] shapes = {"Start","Stop","Print","Loop","If","Instruction", "Function", "Variable"};

        JComboBox<String> shape = new JComboBox<>(shapes);

        file.add(newFile);
        file.add(save);
        file.add(load);
        file.add(upload);
        file.add(courses);
        help.add(about);
        actions.add(undo);
        actions.add(clearFlowchart);
        actions.add(clearCode);
        actions.add(delete);
        actions.add(changeText);
        //mode.add(sandbox);
        //mode.add(translateCode);
        //mode.add(translateFlowchart);
        MenuBarControlHandler menuController = new MenuBarControlHandler(teachingApp, this);


        newFile.addActionListener(menuController);
        save.addActionListener(menuController);
        load.addActionListener(menuController);
        upload.addActionListener(menuController);
        courses.addActionListener(menuController);
        about.addActionListener(menuController);
        shape.addActionListener(menuController);
        undo.addActionListener(menuController);
        clearFlowchart.addActionListener(menuController);
        clearCode.addActionListener(menuController);
        delete.addActionListener(menuController);
        sandbox.addActionListener(menuController);
        translateFlowchart.addActionListener(menuController);
        translateCode.addActionListener(menuController);
        changeText.addActionListener(menuController);

        menuBar.add(file);
        //menuBar.add(mode);
        menuBar.add(help);
        menuBar.add(actions);
        menuBar.add(shape);

        add(menuBar,BorderLayout.NORTH);
        add(westPanel,BorderLayout.WEST);
        add(drawPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

    }

    /**
     * Update method to update the view depending on the notification received
     * from the repository.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        ProblemRepository problemRepository = (ProblemRepository) ProblemRepository.getInstance();
        if(arg == null)
            return;
        String command = (String) arg;
        if(command.equals("Sandbox")){
            westPanel.setVisible(false);
        } else if(command.equals("Translate Code")){
            remove(westPanel);
            FeedbackRepository feedbackRepository = (FeedbackRepository) FeedbackRepository.getInstance();
            MetricsPrompt metricsPrompt = new MetricsPrompt();
            feedbackRepository.addObserver(metricsPrompt);
            CodeProblemView codeProblemView = new CodeProblemView(metricsPrompt);
            feedbackRepository.addObserver(codeProblemView);
            problemRepository.addObserver(codeProblemView);
            add(codeProblemView, BorderLayout.WEST);
            westPanel = codeProblemView;
        } else if (command.equals("Translate Flowchart")) {
            remove(westPanel);
            FlowchartProblemView flowchartProblemView = new FlowchartProblemView();
            problemRepository.addObserver(flowchartProblemView);
            FeedbackRepository feedbackRepository = (FeedbackRepository) FeedbackRepository.getInstance();
            feedbackRepository.addObserver(flowchartProblemView);
            add(flowchartProblemView, BorderLayout.WEST);
            westPanel = flowchartProblemView;
        } else if(command.equals("Courses")) {
            this.setVisible(false);
        }
    }

}