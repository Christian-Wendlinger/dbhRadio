package app.dbhradio.ModelAndController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.List;

/**
 * @author Christian Wendlinger
 * <p>
 * Diese Klasse steuert die gesamte Anwendung. Hier werden alle Elemente auf der GUI initialisiert, dazu gehört das
 * Auslesen der Daten aus dem Radio, aber auch das Behandeln von Interaktionen mit der GUI.
 */

public class RadioController {

    private final Radio radio;

    @FXML
    private Label anOderAusLabel;
    @FXML
    private Label volumeLabel;
    @FXML
    private Label frequencyLabel;
    @FXML
    private Label senderLabel;
    @FXML
    private Label errorLabel;


    @FXML
    private Button anOderAusButton;
    @FXML
    private Button leiserButton;
    @FXML
    private Button lauterButton;
    @FXML
    private Button selectFrequencyButton;
    @FXML
    private Button selectFromSendersButton;

    @FXML
    private TextField selectFrequencyTextField;

    @FXML
    private ListView<String> savedSenders;

    public RadioController(Radio radio) {
        this.radio = radio;
    }

    @FXML
    private void initialize() {
        updateRadioStatusInformation();

        // Buttons
        initializeAnOderAusButton();
        initializeLeiserButton();
        initializeLauterButton();
        initializeSelectFrequencyButton();

        // TextField
        initializeFrequencyTextField();

        // ListView
        initializeSavedSenders();
        initializeSelectFromSendersButton();
    }

    /*
    Hier kommen alle Methoden für die Initialisierungen und nötige UpdateMethoden für Labels etc.
     */
    private void updateRadioStatusInformation() {
        updateAnOderAusLabel();
        updateVolumeLabel();
        updateFrequencyLabel();
        updateSenderLabel();
    }

    private void updateAnOderAusLabel() {
        String text = radio.getTurnedOn() ? "angeschaltet" : "ausgeschaltet";
        anOderAusLabel.setText("Radio ist " + text);
    }

    private void updateVolumeLabel() {
        String text = radio.getTurnedOn() ? radio.getVolume().toString() : "N/A";
        volumeLabel.setText("Lautstärke: " + text);
    }

    private void updateFrequencyLabel() {
        // rounded to 1 digit after komma
        String frequencyValue = String.format("%.1f", radio.getFrequency());
        String text = radio.getTurnedOn() ? frequencyValue : "N/A";
        frequencyLabel.setText("Frequenz: " + text);
    }

    private void updateSenderLabel() {
        String text = radio.getTurnedOn() ? radio.getSender() : "N/A";
        senderLabel.setText("Sender: " + text);
    }

    private void updateAnOderAusButtonText() {
        String text = radio.getTurnedOn() ? "ausschalten" : "anschalten";
        anOderAusButton.setText(text);
    }

    private void initializeAnOderAusButton() {
        anOderAusButton.setOnAction(actionEvent -> {
            radio.setTurnedOn(!radio.getTurnedOn());
            updateRadioStatusInformation();
            updateAnOderAusButtonText();
        });
        updateAnOderAusButtonText();
    }

    private void initializeLeiserButton() {
        leiserButton.setOnAction(actionEvent -> {
            // only works when radio is turned on
            if (!radio.getTurnedOn()) return;

            radio.turnVolumeDown();
            updateVolumeLabel();
        });
    }

    private void initializeLauterButton() {
        lauterButton.setOnAction(actionEvent -> {
            // only works when radio is turned on
            if (!radio.getTurnedOn()) return;

            radio.turnVolumeUp();
            updateVolumeLabel();
        });
    }

    private void initializeSelectFrequencyButton() {
        selectFrequencyButton.setOnAction(actionEvent -> {
            // only works when radio is turned on
            if (!radio.getTurnedOn()) return;

            updateFrequencyValue();
        });
    }

    private void initializeFrequencyTextField() {
        selectFrequencyTextField.setOnKeyReleased(keyEvent -> {
            // only works when radio is turned on
            if (!radio.getTurnedOn()) return;

            // react when enter is pressed
            if (keyEvent.getCode() == KeyCode.ENTER) {
                updateFrequencyValue();
            }
        });
    }

    private void updateFrequencyValue() {
        Double frequency = parseInput(selectFrequencyTextField.getText());

        // show error, if necessary
        if (frequency == null) {
            if (!errorLabel.isVisible()) errorLabel.setVisible(true);
        } else {
            radio.setFrequency(frequency);
            radio.setSender("Unknown");
            updateFrequencyLabel();
            updateSenderLabel();

            errorLabel.setVisible(false);

            selectFrequencyTextField.setText("");
        }
    }

    private Double parseInput(String text) {
        // Standardmäßig werden nur Punkte geparsed, also hier nochmal Eingaben mit Komma anpassen
        text = text.replace(',', '.');

        Double result = null;
        try {
            result = Double.parseDouble(text);
        } catch (NumberFormatException ignored) {
        }

        return result;
    }

    private void initializeSavedSenders() {
        List<String> senders = radio.getKnownRadioStations().keySet().stream().toList();
        savedSenders.setItems(FXCollections.observableList(senders));
    }

    private void initializeSelectFromSendersButton() {
        selectFromSendersButton.setOnAction(actionEvent -> {
            // only works when radio is turned on
            if (!radio.getTurnedOn()) return;

            String senderName = savedSenders.getSelectionModel().getSelectedItem();
            Double frequency = radio.getKnownRadioStations().get(senderName);

            // Nothing selected when pressing the button or sender for some reason unknown
            if (senderName == null || frequency == null) return;

            radio.setSender(senderName);
            radio.setFrequency(frequency);

            updateSenderLabel();
            updateFrequencyLabel();
        });
    }
}