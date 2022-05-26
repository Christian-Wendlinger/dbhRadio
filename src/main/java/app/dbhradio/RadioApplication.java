package app.dbhradio;

import app.dbhradio.ModelAndController.Radio;
import app.dbhradio.ModelAndController.RadioController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Christian Wendlinger
 * <p>
 * Diese Klasse stellt den Ausgangspunkt der Anwendung dar. Sie initialisiert das Radio, setzt die Eigenschaften für das
 * Fenster fest und erzeugt einen passenden Controller.
 */

public class RadioApplication extends Application {

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 400;
    private static final String WINDOW_TITLE = "Radio Anwendung";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(RadioApplication.class.getResource("radio-view.fxml"));

        // set custom controller
        RadioController controller = new RadioController(new Radio());
        fxmlLoader.setController(controller);

        // set scene
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
