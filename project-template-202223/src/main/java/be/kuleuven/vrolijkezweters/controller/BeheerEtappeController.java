package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Etappe;
import be.kuleuven.vrolijkezweters.ProjectMain;
import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.WeakSetChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Query;

import java.util.List;
import java.util.Map;

public class BeheerEtappeController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView tblConfigs4;

    public void initialize() {
        initTable();
        btnAdd.setOnAction(e -> addNewRow());
        btnModify.setOnAction(e -> {
            verifyOneRowSelected();
            modifyCurrentRow();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
        });

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });

        //btnEtappe.setOnAction(event -> showEtappeBeheerscherm());
    }

    public TableView getConfic4(){return tblConfigs4;}
    private void initTable() {

        tblConfigs4.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigs4.getColumns().clear();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();
        List<Etappe> list = handle.createQuery("SELECT * FROM etappe").mapToBean(Etappe.class).list();

        int colIndex = 0;
        for(var colName : new String[]{"EtappeId", "WedstrijdId", "EtappeNummer", "BeginLocatie", "Eindlocatie", "EtappeAfstand"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            tblConfigs4.getColumns().add(col);
            colIndex++;
        }

        for(int i = 0; i < list.size(); i++) {
            Etappe etappe = list.get(i);

            tblConfigs4.getItems().add(FXCollections.observableArrayList(etappe.getEtappeId() + "", etappe.getWedstrijdid()+ "", etappe.getEtappeNummer()+ "", etappe.getBeginLocatie()+ "", etappe.getEindLocatie() + "", etappe.getEtappeAfstand() + ""));
        }
        handle.close();
    }

    private void addNewRow() {

        var resourceName = "addEtappe.fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add etappe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        int geselecteerdeRij = tblConfigs4.getSelectionModel().getSelectedIndex();
        System.out.println("Geselecteerde rij is rij " +geselecteerdeRij);

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();


        // SQL statement die de WedstrijdId laat zien van geselecteerdeRij
        String SQL_getWedstrijdId = "SELECT WedstrijdId FROM Wedstrijden LIMIT 1 OFFSET "+geselecteerdeRij;
        int resultaat = handle.execute(SQL_getWedstrijdId);
        // Probleem hier is dat er voor elke WedstrijdId -1 wordt teruggegeven, zoals deze println laat zien
        System.out.println(resultaat);

        // Als de juiste WestrijdId wordt teruggegeven --> rij met deze WedstrijdId verwijderen
        // Ook problemen met foreign keys wanneer ik rij probeer te verwijderen in DB Browser


        /* Iets als dit proberen:
        String SQL2 = "DELETE FROM Wedstrijden WHERE WedstrijdId IN (SELECT WedstrijdId FROM Wedstrijden LIMIT 1 OFFSET 1)";
        */

        handle.close();
    }

    private void modifyCurrentRow() {
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if(tblConfigs4.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren hé.");
        }
    }

}
