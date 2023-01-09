package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Etappe;
import be.kuleuven.vrolijkezweters.ProjectMain;
import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import com.sun.javafx.image.IntPixelGetter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jdbi.v3.core.Handle;

import java.util.List;

public class addEtappeController {

    @FXML
    private TextField BeginLocatie;
    @FXML
    private TextField EindLocatie;
    @FXML
    private Button addEtappe;
    @FXML
    private Text etappeText;

    private List<Wedstrijden> list;

    private int i = 1;

    public void initialize() {
        addEtappe.setOnAction(e -> addEtappe());
    }
    private void addEtappe() {

        float beginLocatie = Float.parseFloat(BeginLocatie.getText());
        float eindLocatie = Float.parseFloat(EindLocatie.getText());
        float etappeAfstand = eindLocatie-beginLocatie;

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        list = handle.createQuery("SELECT * FROM wedstrijden ORDER BY wedstrijdid DESC LIMIT 1").mapToBean(Wedstrijden.class).list();

        int wedstrijdId = list.get(0).getWedstrijdId();
        int aantalEtappes = Integer.parseInt(list.get(0).getAantalEtappes());

        var stage = (Stage) addEtappe.getScene().getWindow();

        if(i==aantalEtappes){
            handle.execute("INSERT INTO etappe (wedstrijdId, etappeNummer, beginLocatie, eindLocatie, etappeAfstand) VALUES (?, ?, ?, ?, ?)", wedstrijdId, i, beginLocatie, eindLocatie, etappeAfstand);
            handle.close();
            stage.close();
            refreshPreviousScene();
        } else {
            etappeText.setText("Etappe " + (i + 1));
            i++;
            handle.execute("INSERT INTO etappe (wedstrijdId, etappeNummer, beginLocatie, eindLocatie, etappeAfstand) VALUES (?, ?, ?, ?, ?)", wedstrijdId, i, beginLocatie, eindLocatie, etappeAfstand);
            BeginLocatie.clear();
            EindLocatie.clear();
        }


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
