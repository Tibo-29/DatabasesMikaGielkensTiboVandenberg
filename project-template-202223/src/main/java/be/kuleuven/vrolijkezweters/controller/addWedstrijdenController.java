package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jdbi.v3.core.Handle;

public class addWedstrijdenController {

    @FXML
    private Button addId;
    @FXML
    private TextField afstandId;
    @FXML
    private TextField aantalEtappesId;
    @FXML
    private TextField inschrijvingsgeldId;
    @FXML
    private TextField datumId;
    @FXML
    private TextField locatieId;

    public void initialize() {
        addId.setOnAction(e -> addWedstrijd());
    }
    private void addWedstrijd() {

        int wedstrijdid = 6;
        float afstand = Float.parseFloat(afstandId.getText());
        String aantalEtappes = aantalEtappesId.getText().toString();
        float inschrijvingsgeld = Float.parseFloat(inschrijvingsgeldId.getText());
        String datum = datumId.getText().toString();
        String locatie = locatieId.getText().toString();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        handle.execute("INSERT INTO wedstrijden (afstand, aantaletappes, inschrijvingsgeld, datum, locatie) VALUES (?, ?, ?, ?, ?)", afstand, aantalEtappes, inschrijvingsgeld, datum, locatie);

        handle.close();
    }

}
