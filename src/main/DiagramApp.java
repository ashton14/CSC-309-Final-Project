package src.main;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Ashton Alonge, Aaron Bettencourt
 * main class
 * frame with menu bar, drawing area, and status bar
 */
public class DiagramApp extends JFrame implements Observer {

    private JPanel westPanel;
    /**
     * Constructor to add menu bar with 4 menus, a drawing area, and a status bar
     */
    public DiagramApp(){
        super("Diagram App");
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
        JMenu mode = new JMenu("Mode");

        JMenuItem newFile = new JMenuItem("New");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem about = new JMenuItem("About");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem clear = new JMenuItem("Clear");
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
        help.add(about);
        actions.add(undo);
        actions.add(clear);
        actions.add(delete);
        actions.add(changeText);
        mode.add(sandbox);
        mode.add(translateCode);
        mode.add(translateFlowchart);
        MenuBarControlHandler menuController = new MenuBarControlHandler();
        

        newFile.addActionListener(menuController);
        save.addActionListener(menuController);
        load.addActionListener(menuController);
        about.addActionListener(menuController);
        shape.addActionListener(menuController);
        undo.addActionListener(menuController);
        clear.addActionListener(menuController);
        delete.addActionListener(menuController);
        sandbox.addActionListener(menuController);
        translateFlowchart.addActionListener(menuController);
        translateCode.addActionListener(menuController);
        changeText.addActionListener(menuController);

        menuBar.add(file);
        menuBar.add(mode);
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
        FeedbackRepository hintRepository = (FeedbackRepository) FeedbackRepository.getInstance();
        if(arg == null)
            return;
        String command = (String) arg;
        //System.out.println(command);
        if(command.equals("Sandbox")){
            westPanel.setVisible(false);
        } else if(command.equals("Translate Code")){
            remove(westPanel);
            CodeProblemView codeProblemView = new CodeProblemView();
            CodeProblemViewControlHandler handler = new CodeProblemViewControlHandler();
            problemRepository.addObserver(codeProblemView);
            codeProblemView.addActionListener(handler);
            hintRepository.addObserver(codeProblemView);
            add(codeProblemView, BorderLayout.WEST);
            westPanel = codeProblemView;
        } else if (command.equals("Translate Flowchart")) {
            remove(westPanel);
            SidePanel flowchartProblemView = new SidePanel();
            add(flowchartProblemView, BorderLayout.WEST);
            westPanel = flowchartProblemView;
        }
    }

}
