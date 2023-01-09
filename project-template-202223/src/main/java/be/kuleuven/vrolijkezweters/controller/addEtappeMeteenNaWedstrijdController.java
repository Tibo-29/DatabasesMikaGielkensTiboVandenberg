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

public class addEtappeMeteenNaWedstrijdController {

    @FXML
    private TextField BeginLocatie2;
    @FXML
    private TextField EindLocatie2;
    @FXML
    private Button addEtappe2;
    @FXML
    private Text etappeText2;


    private List<Wedstrijden> list;

    private int i = 1;

    public void initialize() {
        addEtappe2.setOnAction(e -> addEtappe2());
    }
    private void addEtappe2() {

        float beginLocatie = Float.parseFloat(BeginLocatie2.getText());
        float eindLocatie = Float.parseFloat(EindLocatie2.getText());
        float etappeAfstand = eindLocatie-beginLocatie;

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        list = handle.createQuery("SELECT * FROM wedstrijden ORDER BY wedstrijdid DESC LIMIT 1").mapToBean(Wedstrijden.class).list();

        int wedstrijdId = list.get(0).getWedstrijdId();
        int aantalEtappes = Integer.parseInt(list.get(0).getAantalEtappes());

        var stage = (Stage) addEtappe2.getScene().getWindow();

        if(i==aantalEtappes){
            handle.execute("INSERT INTO etappe (wedstrijdId, etappeNummer, beginLocatie, eindLocatie, etappeAfstand) VALUES (?, ?, ?, ?, ?)", wedstrijdId, i, beginLocatie, eindLocatie, etappeAfstand);
            handle.close();
            stage.close();
            refreshPreviousScene();
        } else {
            etappeText2.setText("Etappe " + (i + 1));
            handle.execute("INSERT INTO etappe (wedstrijdId, etappeNummer, beginLocatie, eindLocatie, etappeAfstand) VALUES (?, ?, ?, ?, ?)", wedstrijdId, i, beginLocatie, eindLocatie, etappeAfstand);
            i++;
            BeginLocatie2.clear();
            EindLocatie2.clear();
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
