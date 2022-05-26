module app.dbhradio {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens app.dbhradio to javafx.fxml;
    exports app.dbhradio;
    exports app.dbhradio.ModelAndController;
    opens app.dbhradio.ModelAndController to javafx.fxml;
}