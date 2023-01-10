package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Loper;
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

public class BeheerWedstrijdenController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnEtappe;
    @FXML
    private TableView tblConfigs;
    private List<Wedstrijden> list;
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

        btnEtappe.setOnAction(event -> showEtappeBeheerscherm());
    }

    private void initTable() {

        tblConfigs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigs.getColumns().clear();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();
        list = handle.createQuery("SELECT * FROM wedstrijden").mapToBean(Wedstrijden.class).list();

        int colIndex = 0;
        for(var colName : new String[]{"WedstrijdId", "Afstand", "Aantal etappes", "Inschrijvingsgeld", "Datum", "Locatie"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            tblConfigs.getColumns().add(col);
            colIndex++;
        }

        for(int i = 0; i < list.size(); i++) {
            Wedstrijden wedstrijden = list.get(i);

            tblConfigs.getItems().add(FXCollections.observableArrayList(wedstrijden.getWedstrijdId() + "", wedstrijden.getAfstand()+ "", wedstrijden.getAantalEtappes()+ "", wedstrijden.getInschrijvingsgeld()+ "", wedstrijden.getDatum() + "", wedstrijden.getLocatie() + ""));
        }
        handle.close();
    }
    private void addNewRow() {

        var resourceName = "addWedstrijden.fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("addWedstrijden.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add wedstrijd");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }

        var stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    private void deleteCurrentRow() {
        int geselecteerdeRij = tblConfigs.getSelectionModel().getSelectedIndex();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        int id = list.get(geselecteerdeRij).getWedstrijdId();

        String SQL_deleteWedstrijd = "DELETE from wedstrijden WHERE wedstrijdid = " + id;
        String SQL_deleteteEtappes = "DELETE from etappe WHERE wedstrijdid = " + id;

        handle.execute(SQL_deleteteEtappes);
        handle.execute(SQL_deleteWedstrijd);

        handle.close();

        ObservableList<Wedstrijden> data = tblConfigs.getItems();
        data.remove(geselecteerdeRij);
    }

    public void modifyCurrentRow() {
        //int geselecteerdeRij = tblConfigs.getSelectionModel().getSelectedIndex();
        //int id = list.get(geselecteerdeRij).getWedstrijdId();
        showWijzigWedstrijdenScherm();
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if(tblConfigs.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren hé.");
        }
    }

    private void showEtappeBeheerscherm() {
        var resourceName = "beheeretappes.fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin Etappe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    private void showWijzigWedstrijdenScherm() {
        var resourceName = "wijzigWedstrijd.fxml";
        try {
            var stage = new Stage();

            //var stage2 = (Stage) btnModify.getScene().getWindow();
            int geselecteerdeRij = tblConfigs.getSelectionModel().getSelectedIndex();
            int id = list.get(geselecteerdeRij).getWedstrijdId();
            stage.setUserData(id);

            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin wijzig wedstrijd");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }
}
