package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jdbi.v3.core.Handle;

import java.util.List;

import static javafx.scene.input.KeyCode.R;

public class addWedstrijden {

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

        float afstand = Float.parseFloat(afstandId.getText());
        String aantalEtappes = aantalEtappesId.getText().toString();
        float inschrijvingsgeld = Float.parseFloat(inschrijvingsgeldId.getText());
        String datum = datumId.getText().toString();
        String locatie = locatieId.getText().toString();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        Wedstrijden wedstrijden = new Wedstrijden();

        wedstrijden.setAfstand(afstand);
        wedstrijden.setAantalEtappes(aantalEtappes);
        wedstrijden.setInschrijvingsgeld(inschrijvingsgeld);
        wedstrijden.setDatum(datum);
        wedstrijden.setLocatie(locatie);

        handle.execute("INSERT INTO wedstrijden VALUES (:afstand, :aantaletappes, :inschrijvingsgeld, :datum, :Locatie)", wedstrijden.getAfstand(), wedstrijden.getAantalEtappes(), wedstrijden.getInschrijvingsgeld(), wedstrijden.getDatum(), wedstrijden.getLocatie());


    }

}
