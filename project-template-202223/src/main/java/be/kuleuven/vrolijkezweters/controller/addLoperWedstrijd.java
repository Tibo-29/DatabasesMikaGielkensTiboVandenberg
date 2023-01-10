package be.kuleuven.vrolijkezweters.controller;

import be.kuleuven.vrolijkezweters.Loper;
import be.kuleuven.vrolijkezweters.Wedstrijden;
import be.kuleuven.vrolijkezweters.databaseConnection;
import be.kuleuven.vrolijkezweters.loperWedstrijd;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.sqlobject.config.RegisterCollectorFactory;

import java.util.List;

public class addLoperWedstrijd {

    @FXML
    private Button addBtn;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClose;
    @FXML
    private TableView tblConfigsLoperWedstrijd;

    private List<Loper> list;

    private List<loperWedstrijd> loperWedstrijdList;

    public void initialize() {
        initTable();
        addBtn.setOnAction(e -> addLoperWedstrijd());
        /*btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
        });*/

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void initTable() {

        tblConfigsLoperWedstrijd.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsLoperWedstrijd.getColumns().clear();

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();
        list = handle.createQuery("SELECT * FROM loper").mapToBean(Loper.class).list();

        int colIndex = 0;
        for(var colName : new String[]{"ID", "Naam", "Leeftijd", "Gewicht", "Totale looptijd", "Totale loopafstand"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            tblConfigsLoperWedstrijd.getColumns().add(col);
            colIndex++;
        }

        for(int i = 0; i < list.size(); i++) {
            Loper loper = list.get(i);

            tblConfigsLoperWedstrijd.getItems().add(FXCollections.observableArrayList(loper.getLoperId() + "", loper.getNaam()+ "", loper.getLeeftijd()+ "", loper.getGewicht()+ "", loper.getTotaleLooptijd() + "", loper.getTotaleAfstand() + ""));
        }
        handle.close();
    }

    private void addLoperWedstrijd(){

        databaseConnection databaseConnection = new databaseConnection();
        Handle handle = databaseConnection.getJdbi().open();

        int rugnummer;
        int geselecteerdeRij = tblConfigsLoperWedstrijd.getSelectionModel().getSelectedIndex();
        int loperId = list.get(geselecteerdeRij).getLoperId();

        loperWedstrijdList = handle.createQuery("SELECT * FROM loperperwedstrijd ORDER BY rugnummer DESC").mapToBean(loperWedstrijd.class).list();

        if(loperWedstrijdList.size() == 0){
            rugnummer = 1;
        }else{
            rugnummer = Integer.parseInt(loperWedstrijdList.get(0).getRugnummer()) + 1;
        }

        var stage = (Stage) tblConfigsLoperWedstrijd.getScene().getWindow();
        int wedstrijdId = (int) stage.getUserData();

        handle.execute("INSERT INTO loperperwedstrijd (loperid, wedstrijdid, rugnummer) VALUES (?, ?, ?)", loperId, wedstrijdId, String.valueOf(rugnummer));
        /*for(int i=0; i<loperWedstrijdList.size(); i++){
            if(loperWedstrijdList.contains(loperId)){
                verifyLoperNotYetInWedstrijd();
            } else {
                handle.execute("INSERT INTO loperperwedstrijd (loperid, wedstrijdid, rugnummer) VALUES (?, ?, ?)", loperId, wedstrijdId, String.valueOf(rugnummer));
            }
        }*/
        handle.close();
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyLoperNotYetInWedstrijd() {
        showAlert("Error!", "Loper is al in de lijst van de wedstrijd.");
    }
}
