package app.dbhradio.ModelAndController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian Wendlinger
 * <p>
 * Diese Klasse repräsentiert ein Modell für ein Radio, welches verschiedene Grundfunktionen wie beispielsweise
 * das Einstellen der Frequenz ermöglicht.
 */

public class Radio {
    private static final int MAX_VOLUME = 100;
    private static final int MIN_VOLUME = 0;
    private static final int VOLUME_STEP = 5;

    private Boolean isTurnedOn;
    private Integer volume;
    private Double frequency;
    private String sender;
    private final Map<String, Double> knownRadioStations;

    public Radio() {
        isTurnedOn = false;
        volume = 50;
        frequency = 100.0;
        sender = "Unbekannt";
        knownRadioStations = new HashMap<>();

        initializeRadioStations();
    }

    // getter and setter
    public Boolean getTurnedOn() {
        return isTurnedOn;
    }

    public void setTurnedOn(Boolean turnedOn) {
        isTurnedOn = turnedOn;
    }

    public Integer getVolume() {
        return volume;
    }

    private void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Map<String, Double> getKnownRadioStations() {
        return knownRadioStations;
    }


    // methods
    private void initializeRadioStations() {
        getKnownRadioStations().put("Station Nord", 86.5);
        getKnownRadioStations().put("Station West", 91.9);
        getKnownRadioStations().put("Radio 404", 404.0);
        getKnownRadioStations().put("Mars 3", 106.2);
        getKnownRadioStations().put("Radio Amerika", 216.6);
    }

    public void turnVolumeDown() {
        setVolume(Math.max(MIN_VOLUME, getVolume() - VOLUME_STEP));
    }

    public void turnVolumeUp() {
        setVolume(Math.min(MAX_VOLUME, getVolume() + VOLUME_STEP));
    }
}
