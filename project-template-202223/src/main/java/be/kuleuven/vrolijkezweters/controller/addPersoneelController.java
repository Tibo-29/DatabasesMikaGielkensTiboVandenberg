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

public class addPersoneelController {

    @FXML
    private TextField Naam;
    @FXML
    private TextField Functie;
    @FXML
    private TextField Vrijwilliger;
    @FXML
    private TextField LoonPerUur;
    @FXML
    private TextField LoonNogTeKrijgen;
    @FXML
    private Button addPersoneel;

    public void initialize() {
        addPersoneel.setOnAction(e -> addPersoneel());
    }
    private void addPersoneel() {

        String naam = Naam.getText().toString();
        String functie = Functie.getText().toString();
        String vrijwilliger = Vrijwilliger.getText().toString();
        float loonPerUur = Float.parseFloat(LoonPerUur.getText());
        float loonNogTeKrijgen = Float.parseFloat(LoonNogTeKrijgen.getText());

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        handle.execute("INSERT INTO personeel (naam, functie, vrijwilliger, loonPerUur, loonNogTeKrijgen) VALUES (?, ?, ?, ?, ?)", naam, functie, vrijwilliger, loonPerUur, loonNogTeKrijgen);

        handle.close();

        var stage = (Stage) addPersoneel.getScene().getWindow();
        stage.close();

        refreshPreviousScene();
    }

    private void refreshPreviousScene(){
        var resourceName = "beheerpersoneel.fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin personeel");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

}
