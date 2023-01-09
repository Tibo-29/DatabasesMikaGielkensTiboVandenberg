package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Loper;
import be.kuleuven.vrolijkezweters.ProjectMain;
import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.controller.BeheerWedstrijdenController;
import be.kuleuven.vrolijkezweters.databaseConnection;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
        BeheerWedstrijdenController wedstrijdenController = new BeheerWedstrijdenController();

        float afstand = Float.parseFloat(afstandId.getText());
        String aantalEtappes = aantalEtappesId.getText().toString();
        float inschrijvingsgeld = Float.parseFloat(inschrijvingsgeldId.getText());
        String datum = datumId.getText().toString();
        String locatie = locatieId.getText().toString();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        handle.execute("INSERT INTO wedstrijden (afstand, aantaletappes, inschrijvingsgeld, datum, locatie) VALUES (?, ?, ?, ?, ?)", afstand, aantalEtappes, inschrijvingsgeld, datum, locatie);

        handle.close();

        var stage = (Stage) addId.getScene().getWindow();
        stage.close();

        refreshPreviousScene();
    }

    private void refreshPreviousScene(){
        var resourceName = "beheerwedstrijden.fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin wedstrijden");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

}
