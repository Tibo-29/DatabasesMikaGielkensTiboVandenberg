package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Loper;
import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.jdbi.v3.core.Handle;
import be.kuleuven.vrolijkezweters.loperWedstrijd;
import org.jdbi.v3.sqlobject.config.RegisterCollectorFactory;

import java.util.List;

public class beheerLopersInWedstrijd {

    @FXML
    private Button btnClose;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView tblConfigsBeheerLoperWedstrijd;
    @FXML
    private Button filterBtn;

    private List<loperWedstrijd> loperWedstrijdList;

    public void initialize() {
        initTable();
        btnDelete.setOnAction(e -> {
        });

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
        filterBtn.setOnAction(event -> rightData());
    }

    private void initTable() {

        tblConfigsBeheerLoperWedstrijd.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsBeheerLoperWedstrijd.getColumns().clear();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();



        loperWedstrijdList = handle.createQuery("SELECT * FROM loperperwedstrijd").mapToBean(loperWedstrijd.class).list();

        int colIndex = 0;
        for(var colName : new String[]{"Loper ID", "Wedstrijd ID", "Rugnummer"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            tblConfigsBeheerLoperWedstrijd.getColumns().add(col);
            colIndex++;
        }

        for(int i = 0; i < loperWedstrijdList.size(); i++) {
            loperWedstrijd loperWedstrijd = loperWedstrijdList.get(i);

            tblConfigsBeheerLoperWedstrijd.getItems().add(FXCollections.observableArrayList(loperWedstrijd.getLoperId() + "", loperWedstrijd.getWedstrijdId()+ "", loperWedstrijd.getRugnummer()+ ""));
        }
        handle.close();
    }

    private void rightData(){
        var stage = (Stage) btnClose.getScene().getWindow();
        int id = (int) stage.getUserData();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        ObservableList<loperWedstrijd> data = tblConfigsBeheerLoperWedstrijd.getItems();
        data.clear();

        loperWedstrijdList = handle.createQuery("SELECT * FROM loperperwedstrijd WHERE wedstrijdid is " + id).mapToBean(loperWedstrijd.class).list();

        for(int i = 0; i < loperWedstrijdList.size(); i++) {
            loperWedstrijd loperWedstrijd = loperWedstrijdList.get(i);

            tblConfigsBeheerLoperWedstrijd.getItems().add(FXCollections.observableArrayList(loperWedstrijd.getLoperId() + "", loperWedstrijd.getWedstrijdId()+ "", loperWedstrijd.getRugnummer()+ ""));
        }

        handle.close();
    }
}
