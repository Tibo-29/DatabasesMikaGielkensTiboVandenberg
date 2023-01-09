package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.ProjectMain;
import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.LightBase;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jdbi.v3.core.Handle;

import java.util.List;

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
    private Button addEtappe;
    @FXML Button btnCloseAddEtappe;

    private List<Wedstrijden> list;

    public void initialize() {
        addEtappe.setOnAction(e -> addEtappe());
        btnCloseAddEtappe.setOnAction(e -> {
            var stage = (Stage) btnCloseAddEtappe.getScene().getWindow();
            stage.close();
            showPreviousWindow();
        });
    }
    private void addEtappe() {

        int wedstrijdId = Integer.parseInt(WedstrijdId.getText());
        float etappeNummer = Integer.parseInt(EtappeNummer.getText());
        float beginLocatie = Float.parseFloat(BeginLocatie.getText());
        float eindLocatie = Float.parseFloat(EindLocatie.getText());
        float etappeAfstand = eindLocatie-beginLocatie;

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        list = handle.createQuery("SELECT * FROM wedstrijden ORDER BY wedstrijdid DESC LIMIT 1").mapToBean(Wedstrijden.class).list();

        //int wedstrijdId = list.get(0).getWedstrijdId();
        //int aantalEtappes = Integer.parseInt(list.get(0).getAantalEtappes());

        var stage = (Stage) addEtappe.getScene().getWindow();

        handle.execute("INSERT INTO etappe (wedstrijdId, etappeNummer, beginLocatie, eindLocatie, etappeAfstand) VALUES (?, ?, ?, ?, ?)", wedstrijdId, etappeNummer, beginLocatie, eindLocatie, etappeAfstand);
        handle.close();
        stage.close();
        showPreviousWindow();
    }

    private void showPreviousWindow(){
        var resourceName = "beheeretappes.fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin etappe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

}