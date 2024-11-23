package dev.frays.sandyakala;

import dev.frays.sandyakala.enums.TimerStatus;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Time {
    private Scene scene;
    private HBox timeBox;

    private Label hhLabel;
    private Label mmLabel;
    private Label ssLabel;

    private Label hmSeparator;
    private Label msSeparator;

    public Time() {
        // Hour label
        hhLabel = new Label();
        hhLabel.setText("00");
        hhLabel.getStyleClass().add("time-label");

        // Minute label
        mmLabel = new Label();
        mmLabel.setText("00");
        mmLabel.getStyleClass().add("time-label");

        // Second label
        ssLabel = new Label();
        ssLabel.setText("00");
        ssLabel.getStyleClass().add("time-label");

        // Separator
        hmSeparator = new Label(":");
        hmSeparator.getStyleClass().add("time-label");
        msSeparator = new Label(":");
        msSeparator.getStyleClass().add("time-label");

        // Label Box
        timeBox = new HBox();
        timeBox.getStyleClass().add("time-box");
        timeBox.getChildren().addAll(
                hhLabel, hmSeparator,
                mmLabel, msSeparator, ssLabel);

        // Main Box
        VBox mainBox = new VBox();
        mainBox.getStyleClass().add("main-box");
        mainBox.getChildren().addAll(
                timeBox);

        // Set scene
        scene = new Scene(mainBox);
        scene.getStylesheets().add(
                getClass().getResource("/style/time.css").toExternalForm());
    }

    public void setTime(TimerStatus status, int hh, int mm, int ss) {
        // Set hour label
        hhLabel.setText(String.format("%02d", hh));
        if (hh > 0) {
            hhLabel.setStyle(String.format("-fx-text-fill: %s", status.getColorActive()));
            hmSeparator.setStyle(String.format("-fx-text-fill: %s", status.getColorActive()));
        } else {
            hhLabel.setStyle(String.format("-fx-text-fill: %s", status.getColorInactive()));
            hmSeparator.setStyle(String.format("-fx-text-fill: %s", status.getColorInactive()));
        }

        // Set minute label
        mmLabel.setText(String.format("%02d", mm));
        if (mm > 0) {
            mmLabel.setStyle(String.format("-fx-text-fill: %s", status.getColorActive()));
            msSeparator.setStyle(String.format("-fx-text-fill: %s", status.getColorActive()));
        } else {
            mmLabel.setStyle(String.format("-fx-text-fill: %s", status.getColorInactive()));
            msSeparator.setStyle(String.format("-fx-text-fill: %s", status.getColorInactive()));
        }

        // Set second label
        ssLabel.setText(String.format("%02d", ss));
        ssLabel.setStyle(String.format("-fx-text-fill: %s", status.getColorActive()));

        // Set background color
        timeBox.setStyle(String.format("-fx-background-color: %s", status.getBackground()));
    }

    public Scene getScene() {
        return scene;
    }
}
