package dev.frays.sandyakala.control;

import dev.frays.sandyakala.Time;
import dev.frays.sandyakala.enums.TimeStatus;
import dev.frays.sandyakala.enums.TimerStatus;
import dev.frays.sandyakala.utils.Timer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class TimerCtrl {
    private final Time time;
    private final MainCtrl main;

    private final BorderPane control;

    private final Timer timer;
    private int hh, mm, ss;
    private TimerStatus status;
    private Timeline timeline;

    public TimerCtrl(MainCtrl main) {
        // Initialize
        this.main = main;
        this.time = main.getTime();
        this.timer = new Timer("Timer");
        this.status = TimerStatus.NORMAL;

        // Setup input
        TextField mainInput = new TextField();
        mainInput.setText("00:00:00");
        mainInput.getStyleClass().add("main-input");
        mainInput.textProperty().addListener((arg, oldVal, newVal) -> {
            try {
                timer.setTimer('m', newVal);
            } catch (Exception e) {
                System.out.println("[timer][main] " + e);
            }
        });

        TextField titleInput = new TextField();
        titleInput.setText("Timer");
        titleInput.getStyleClass().addAll("secondary-input", "title-input");
        titleInput.textProperty().addListener((arg, oldVal, newVal) -> {
            timer.id = newVal;
        });

        TextField extendInput = new TextField();
        extendInput.setText("00:00:00");
        extendInput.getStyleClass().addAll("secondary-input", "extend-input");
        extendInput.textProperty().addListener((arg, oldVal, newVal) -> {
            try {
                timer.setTimer('e', newVal);
            } catch (Exception e) {
                System.out.println("[timer][extend] " + e);
            }
        });

        // Box input
        VBox inputBox = new VBox();
        inputBox.getStyleClass().add("input-box");
        inputBox.getChildren().addAll(titleInput, mainInput, extendInput);

        // Setup button
        Button stopButton = new Button();
        stopButton.setText("Stop");

        Button playButton = new Button();
        playButton.setText("Play");

        // Box button
        HBox buttonBox = new HBox();
        buttonBox.getStyleClass().addAll("spacing", "button-box");
        buttonBox.getChildren().addAll(playButton);

        // Main control box
        VBox controlBox = new VBox();
        controlBox.getStyleClass().addAll("spacing", "control-box");
        controlBox.getChildren().addAll(inputBox, buttonBox);

        // Preset timer
        Label presetTitle = new Label();
        presetTitle.getStyleClass().addAll("title");
        presetTitle.setText("Timer Preset");

        VBox presetBox = new VBox();
        presetBox.getStyleClass().add("preset-box");
        presetBox.getChildren().addAll(presetTitle);

        // Timer control
        control = new BorderPane();
        control.setCenter(presetBox);
        control.setBottom(controlBox);
        BorderPane.setMargin(presetBox, new Insets(5, 0, 5, 0));

        // Button event
        stopButton.setOnAction(event -> {
            stopTimer();
            buttonBox.getChildren().removeFirst();
            playButton.setText("Play");
        });

        playButton.setOnAction(event -> {
            playTimer();
            if (status.timeStatus == TimeStatus.PLAY)
                playButton.setText("Pause");
            else if (status.timeStatus == TimeStatus.PAUSE)
                playButton.setText("Play");

            if (!buttonBox.getChildren().contains(stopButton))
                buttonBox.getChildren().addFirst(stopButton);
        });
    }

    public BorderPane getControl() {
        return control;
    }

    public void playTimer() {
        if (status.timeStatus == TimeStatus.PAUSE) {
            resumeTimer();
            return;
        } else if (status.timeStatus == TimeStatus.PLAY) {
            pauseTimer();
            return;
        }

        // Update timer
        hh = timer.mainHH;
        mm = timer.mainMM;
        ss = timer.mainSS;
        status.timeStatus = TimeStatus.PLAY;

        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            // Run the timer
            if (status != TimerStatus.OVERTIME) {
                runTimer();
            } else {
                runTimerOvertime();
            }

            // Update the timer
            time.setTime(status, hh, mm, ss);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        time.setTime(status, hh, mm, ss);
    }

    public void resumeTimer() {
        status.timeStatus = TimeStatus.PLAY;
        timeline.play();
        time.setTime(status, hh, mm, ss);
    }

    public void pauseTimer() {
        status.timeStatus = TimeStatus.PAUSE;
        timeline.pause();
        time.setTime(status, hh, mm, ss);
    }

    public void stopTimer() {
        status = TimerStatus.NORMAL;
        status.timeStatus = TimeStatus.STOP;
        timeline.stop();
        time.setTime(status, 0, 0, 0);
    }

    public void runTimer() {
        if (ss > 0 && status != TimerStatus.OVERTIME) {
            ss--;
        } else if (mm > 0 && status != TimerStatus.OVERTIME) {
            mm--;
            ss = 59;
        } else if (hh > 0 && status != TimerStatus.OVERTIME) {
            hh--;
            mm = 59;
            ss = 59;
        } else if (status == TimerStatus.NORMAL) {
            hh = timer.extendHH;
            mm = timer.extendMM;
            ss = timer.extendSS;

            if (hh == 0 && mm == 0 && ss == 0)
                status = TimerStatus.OVERTIME;
            else
                status = TimerStatus.EXTEND;

            status.timeStatus = TimeStatus.PLAY;
        } else if (status == TimerStatus.EXTEND) {
            status = TimerStatus.OVERTIME;
            status.timeStatus = TimeStatus.PLAY;
        }
    }

    public void runTimerOvertime() {
        if (ss < 59) {
            ss++;
        } else if (mm < 59) {
            mm++;
            ss = 0;
        } else {
            hh++;
            mm = 0;
            ss = 0;
        }
    }
}
