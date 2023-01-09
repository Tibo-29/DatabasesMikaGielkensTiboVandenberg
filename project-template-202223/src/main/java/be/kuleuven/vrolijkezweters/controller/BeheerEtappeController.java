package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.*;
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

    private List<Etappe> list;

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
        list = handle.createQuery("SELECT * FROM etappe").mapToBean(Etappe.class).list();

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

        var stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    private void deleteCurrentRow() {
        int geselecteerdeRij = tblConfigs4.getSelectionModel().getSelectedIndex();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        int id = list.get(geselecteerdeRij).getEtappeId();

        String SQL_deleteLoper = "DELETE from etappe WHERE etappeid = " + id;
        handle.execute(SQL_deleteLoper);

        handle.close();

        ObservableList<Etappe> data = tblConfigs4.getItems();
        data.remove(geselecteerdeRij);
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
