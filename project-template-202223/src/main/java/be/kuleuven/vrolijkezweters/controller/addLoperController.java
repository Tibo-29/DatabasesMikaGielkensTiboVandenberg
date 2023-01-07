package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jdbi.v3.core.Handle;

public class addLoperController {

    @FXML
    private TextField NaamId;
    @FXML
    private TextField LeeftijdId;
    @FXML
    private TextField GewichtId;
    @FXML
    private TextField TotaleLooptijdId;
    @FXML
    private TextField TotaleAfstandId;
    @FXML
    private Button addLoper;

    public void initialize() {
        addLoper.setOnAction(e -> addLoper());
    }
    private void addLoper() {

        String naam = NaamId.getText().toString();
        String leeftijd = LeeftijdId.getText().toString();
        String gewicht = GewichtId.getText().toString();
        float totalelooptijd = Float.parseFloat(TotaleAfstandId.getText());
        float totaleafstand = Float.parseFloat(TotaleAfstandId.getText());

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        handle.execute("INSERT INTO loper (naam, leeftijd, gewicht, totalelooptijd, totaleafstand) VALUES (?, ?, ?, ?, ?)", naam, leeftijd, gewicht, totalelooptijd, totaleafstand);

        handle.close();
    }

}
