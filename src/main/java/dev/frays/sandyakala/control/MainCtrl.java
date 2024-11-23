package dev.frays.sandyakala.control;

import dev.frays.sandyakala.App;
import dev.frays.sandyakala.Time;
import dev.frays.sandyakala.enums.TimeStatus;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainCtrl {
    private App app;
    private Time time;

    private Scene scene;
    private Button lockButton;

    private TimerCtrl timerControl;

    public MainCtrl(App app) {
        // Initiate control
        this.app = app;
        this.time = app.time;
        timerControl = new TimerCtrl(this);

        // Lock button
        lockButton = new Button();
        lockButton.setText("Lock");
        lockButton.setOnAction(event -> {
            boolean isLocked = app.lockTime();
            if (isLocked) lockButton.setText("Unlock");
            else lockButton.setText("Lock");
        });
        lockButton.getStyleClass().add("lock-btn");

        // Selection box
        HBox navigationBox = new HBox();
        navigationBox.getChildren().addAll(lockButton);

        // Control box (default: Timer)
        BorderPane controlBox = timerControl.getControl();

        // Wrapper box and scene
        BorderPane wrapperBox = new BorderPane();
        wrapperBox.getStyleClass().addAll("spacing", "main-control");
        wrapperBox.setTop(navigationBox);
        wrapperBox.setCenter(controlBox);

        // Set scene
        scene = new Scene(wrapperBox, 350, 400);
        scene.getStylesheets().add(
                getClass().getResource("/style/control.css").toExternalForm());
    }

    public void showTime() {
        lockButton.setText("Show");
    }

    public Scene getScene() {
        return scene;
    }

    public Time getTime() {
        return time;
    }
}
