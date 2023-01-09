package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.ProjectMain;
import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jdbi.v3.core.Handle;

public class addEtappeController {

    @FXML
    private TextField WedstrijdId;
    @FXML
    private TextField EtappeNummer;
    @FXML
    private TextField BeginLocatie;
    @FXML
    private TextField EindLocatie;
    @FXML
    private TextField EtappeAfstand;
    @FXML
    private Button addEtappe;

    public void initialize() {
        addEtappe.setOnAction(e -> addEtappe());
    }
    private void addEtappe() {

        int wedstrijdId = Integer.parseInt(WedstrijdId.getText());
        String etappeNummer = EtappeNummer.getText().toString();
        float beginLocatie = Float.parseFloat(BeginLocatie.getText());
        float eindLocatie = Float.parseFloat(EindLocatie.getText());
        float etappeAfstand = Float.parseFloat(EtappeAfstand.getText());

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        handle.execute("INSERT INTO etappe (wedstrijdId, etappeNummer, beginLocatie, eindLocatie, etappeAfstand) VALUES (?, ?, ?, ?, ?)", wedstrijdId, etappeNummer, beginLocatie, eindLocatie, etappeAfstand);

        handle.close();

        var stage = (Stage) addEtappe.getScene().getWindow();
        stage.close();

        refreshPreviousScene();
    }

    private void refreshPreviousScene(){
        var resourceName = "beheeretappes.fxml";
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
