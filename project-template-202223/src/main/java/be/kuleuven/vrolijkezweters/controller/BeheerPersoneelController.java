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

public class BeheerPersoneelController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView tblConfigs3;

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

    public TableView getConfic3(){return tblConfigs3;}
    private void initTable() {

        tblConfigs3.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigs3.getColumns().clear();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();
        List<Personeel> list = handle.createQuery("SELECT * FROM personeel").mapToBean(Personeel.class).list();

        int colIndex = 0;
        for(var colName : new String[]{"PersoneelId", "Naam", "Functie", "Vrijwilliger", "LoonPerUur", "LoonNogTeKrijgen"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            tblConfigs3.getColumns().add(col);
            colIndex++;
        }

        for(int i = 0; i < list.size(); i++) {
            Personeel personeel = list.get(i);

            tblConfigs3.getItems().add(FXCollections.observableArrayList(personeel.getPersoneelId() + "", personeel.getNaam() + "", personeel.getFunctie()+ "", personeel.getVrijwilliger()+ "", personeel.getLoonPerUur() + "", personeel.getLoonNogTeKrijgen() + ""));
        }
        handle.close();
    }

    private void addNewRow() {

        var resourceName = "beheerpersoneel.fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add personeel");
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
        if(tblConfigs3.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren hé.");
        }
    }
}
