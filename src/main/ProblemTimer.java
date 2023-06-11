package src.main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectStreamException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class ProblemTimer extends JPanel implements ActionListener, Observer {
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
    int problemIndex;

    public ProblemTimer() {
        timerHidden = false;
        timeLable = new JLabel("12:34:56");
        bestTimeLabel = new JLabel("00:00:00");
        showHideButton = new JButton("Hide");
        showHideButton.addActionListener(this);
        this.setLayout(new GridLayout(2,3));
        this.add(new JLabel("Session best:")); //originally best time, but times are not saved after session ends
        this.add(new JLabel("Current Time:"));
        this.add(new JLabel(" "));
        this.add(bestTimeLabel);
        this.add(timeLable);
        this.add(showHideButton);
        timer.scheduleAtFixedRate(task, 0, 1000);
        this.getBestTime();
        this.problemIndex = -2;
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
    public void stopTimer() {
        task.cancel();

    }

    public void getBestTime() {
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        int bestTime = pRepo.getBestTimeForCurrentProblem();
        int hrs = 0;
        int mins = 0;
        int secs = 0;
        while(bestTime >= 3600) {
            hrs++;
            bestTime-=3600;
        }
        while(bestTime >= 60) {
            mins++;
            bestTime-=60;
        }
        secs = bestTime;

        bestTimeLabel.setText(String.format("%02d:%02d:%02d",hrs,mins,secs));
    }

    public void sendBestTime() {
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        int time = this.seconds + this.minutes*60 + this.hours*3600;
        System.out.println("Sending bestTime: "+time);
        pRepo.addBestTimeToCurrentProblem(time);
    }

    public void resetTimer() {
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
        updateLabel();
        getBestTime();
        task.cancel();
        task = new TimerTask() {
            @Override
            public void run() {
                incrementTimer();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
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

    @Override
    public void update(Observable o, Object arg) {
        FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        if(fRepo.getErrorIndex() == -1) {
            this.stopTimer();
            this.sendBestTime();
            this.getBestTime();
        }
        if(pRepo.getProblemIndex() != this.problemIndex) {
            resetTimer();
            this.problemIndex = pRepo.getProblemIndex();
        }
    }
}
