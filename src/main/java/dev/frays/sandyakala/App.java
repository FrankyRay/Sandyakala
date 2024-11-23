package dev.frays.sandyakala;

import dev.frays.sandyakala.control.MainCtrl;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.URISyntaxException;

public class App extends Application {
    public MainCtrl control;

    public Time time;
    private Stage timeStage;

    @Override
    public void start(Stage stage) {
        time = new Time();
        timeStage = new Stage();
        timeStage.setTitle("Time - Sandyakala");
        timeStage.setScene(time.getScene());
        timeStage.setOnHidden(event -> {
            control.showTime();
        });
        timeStage.initOwner(stage);

        control = new MainCtrl(this);
        stage.setTitle("Sandyakala");
        stage.setScene(control.getScene());

        // Show stage
        stage.show();
        timeStage.show();
    }

    public boolean lockTime() {
        if (!timeStage.isShowing()) {
            timeStage.show();
        } else {
            // Update lock
            timeStage.setAlwaysOnTop(!timeStage.isAlwaysOnTop());
            timeStage.setTitle((timeStage.isAlwaysOnTop() ? "Locked - " : "")
                    + "Time - Sandyakala");
        }

        return timeStage.isAlwaysOnTop();
    }

    public static void main(String[] args) {
        launch();
    }
}