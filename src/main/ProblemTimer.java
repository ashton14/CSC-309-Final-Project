package src.main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class ProblemTimer extends JPanel implements ActionListener {
    int seconds;
    int minutes;
    int hours;
    boolean timerHidden;
    JLabel timeLable;
    JLabel bestTimeLabel;
    JButton showHideButton;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            incrementTimer();
        }
    };

    public ProblemTimer() {
        timerHidden = false;
        timeLable = new JLabel("12:34:56");
        showHideButton = new JButton("Hide");
        showHideButton.addActionListener(this);
        this.setLayout(new GridLayout(2,3));
        this.add(new JLabel("Best Time:"));
        this.add(new JLabel("Curr. Time:"));
        this.add(new JLabel(" "));
        this.add(new JLabel("00:00:00"));
        this.add(timeLable);
        this.add(showHideButton);
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    private void updateLabel() {
        if(timerHidden == false) timeLable.setText(String.format("%02d:%02d:%02d",hours,minutes,seconds));
        else timeLable.setText("--:--:--");
    }
    public void incrementTimer(){
        seconds++;
        if(seconds == 60) {
            minutes++;
            seconds = 0;
        }
        if(minutes == 60) {
            hours++;
            minutes = 0;
        }
        updateLabel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Hide")){
            this.timerHidden = true;
            this.showHideButton.setText("Show");
            updateLabel();
        }
        if(e.getActionCommand().equals("Show")){
            this.timerHidden = false;
            this.showHideButton.setText("Hide");
            updateLabel();
        }
    }
}
