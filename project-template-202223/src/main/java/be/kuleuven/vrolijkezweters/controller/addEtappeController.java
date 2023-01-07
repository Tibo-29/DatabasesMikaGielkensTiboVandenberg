package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    }

}
