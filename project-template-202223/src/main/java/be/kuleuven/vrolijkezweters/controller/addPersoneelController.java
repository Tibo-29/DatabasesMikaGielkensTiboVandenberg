package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    }

}
