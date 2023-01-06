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

public class BeheerLopersController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView tblConfigs2;

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
    }

    public TableView getConfic2(){return tblConfigs2;}
    private void initTable() {

        tblConfigs2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigs2.getColumns().clear();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();
        List<Loper> list = handle.createQuery("SELECT * FROM loper").mapToBean(Loper.class).list();

        int colIndex = 0;
        for(var colName : new String[]{"LoperId", "Naam", "Leeftijd", "Gewicht", "TotaleLooptijd", "TotaleAfstand"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            tblConfigs2.getColumns().add(col);
            colIndex++;
        }

        for(int i = 0; i < list.size(); i++) {
            Loper loper = list.get(i);

            tblConfigs2.getItems().add(FXCollections.observableArrayList(loper.getLoperId() + "", loper.getNaam() + "", loper.getLeeftijd()+ "", loper.getGewicht()+ "", loper.getTotaleLooptijd() + "", loper.getTotaleAfstand() + ""));
        }
        handle.close();
    }

    private void addNewRow() {

        var resourceName = "addLopers.fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("beheerlopers.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add loper");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        /*
        int geselecteerdeRij = tblConfigs2.getSelectionModel().getSelectedIndex();
        System.out.println("Geselecteerde rij is rij " +geselecteerdeRij);

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();


        // SQL statement die de WedstrijdId laat zien van geselecteerdeRij
        String SQL_getLoperId = "SELECT LoperId FROM Loper LIMIT 1 OFFSET "+geselecteerdeRij;
        int resultaat = handle.execute(SQL_getLoperId);
        // Zelfde probleem als in BeheerWedstrijdenController


        handle.close();
        */
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
        if(tblConfigs2.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren hé.");
        }
    }
}
