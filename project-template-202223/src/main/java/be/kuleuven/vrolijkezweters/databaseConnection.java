package be.kuleuven.vrolijkezweters;

import org.jdbi.v3.core.Jdbi;

public class databaseConnection {

    private Jdbi jdbi;
     public databaseConnection() {jdbi = Jdbi.create("jdbc:sqlite:Database.db");}

    public Jdbi getJdbi(){ return jdbi;}
}
