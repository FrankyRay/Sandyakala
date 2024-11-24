package dev.frays.sandyakala;

import dev.frays.sandyakala.enums.TimerStatus;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Time {
    private Scene scene;
    private HBox timeBox;

    private Label hhLabel;
    private Label mmLabel;
    private Label ssLabel;

    private Label hmSeparator;
    private Label msSeparator;

    private String activeColor = "#888888";
    private String inactiveColor = "#888888";
    private String background = "#1E2E20";

    private int hh;
    private int mm;
    private int ss;

    private int fontSize = 6;

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
        timeBox.setAlignment(Pos.CENTER);
        timeBox.getChildren().addAll(
                hhLabel, hmSeparator,
                mmLabel, msSeparator, ssLabel);

        // Main Box
        BorderPane mainBox = new BorderPane();
        mainBox.getStyleClass().add("main-box");
        BorderPane.setAlignment(timeBox, Pos.CENTER);
        mainBox.setCenter(timeBox);

        // Set scene
        scene = new Scene(mainBox);
        scene.getStylesheets().add(
                getClass().getResource("/style/time.css").toExternalForm());
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.EQUALS || key.getCode() == KeyCode.PLUS)
                fontSize++;
            else if (key.getCode() == KeyCode.MINUS)
                fontSize--;

            System.out.println("[time][font] font-size: " + fontSize);

            Font newSize = Font.loadFont(getClass().getResource(
                    "/style/fonts/RobotoMono-Medium.ttf").toExternalForm(),
                    fontSize);

            // Set time
            styleLabel(hhLabel, hh > 0, hh);
            styleLabel(hmSeparator, hh > 0);
            styleLabel(mmLabel, mm > 0, mm);
            styleLabel(msSeparator, mm > 0);
            styleLabel(ssLabel, ss > 0, ss);
        });
    }

    public void styleLabel(Label label, boolean active) {
        label.setStyle(String.format(
                "-fx-font-size: %dem; -fx-text-fill: %s;",
                fontSize,
                active ? activeColor : inactiveColor
        ));
    }

    public void styleLabel(Label label, boolean active, int val) {
        label.setText(String.format("%02d", val));
        label.setStyle(String.format(
                "-fx-font-size: %dem; -fx-text-fill: %s;",
                fontSize,
                active ? activeColor : inactiveColor
        ));
    }

    public void setTime(TimerStatus status, int hh, int mm, int ss) {
        // Set data
        activeColor = status.getColorActive();
        inactiveColor = status.getColorInactive();
        background = status.getBackground();
        this.hh = hh;
        this.mm = mm;
        this.ss = ss;

        // Set time
        styleLabel(hhLabel, hh > 0, hh);
        styleLabel(hmSeparator, hh > 0);
        styleLabel(mmLabel, mm > 0, mm);
        styleLabel(msSeparator, mm > 0);
        styleLabel(ssLabel, ss > 0, ss);

        // Set background color
        timeBox.setStyle(String.format("-fx-background-color: %s", status.getBackground()));
    }

    public Scene getScene() {
        return scene;
    }
}
