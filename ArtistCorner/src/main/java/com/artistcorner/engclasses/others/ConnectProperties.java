package com.artistcorner.engclasses.others;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectProperties {

    private ConnectProperties(){
        throw new IllegalStateException("Utility Cconnect class");
    }

    /**
     * Prende i valori nel file "login.properties" per effettuare la connessione al DBMS.
     */
    public static Connection getConnection(){
        Connection conn = null;
        InputStream stream = ConnectProperties.class.getClassLoader().getResourceAsStream("login.properties");

        try{

            // Carica il file di configurazione.
            Properties prop = new Properties();
            prop.load(stream);

            // Preleva i parametri di configurazione.
            String dbhostname = prop.getProperty("hostname");
            String dbname = prop.getProperty("dbname");
            String dbusername = prop.getProperty("username");
            String dbpassword = prop.getProperty("password");
            String dbdriverused = prop.getProperty("driverused");

            String dburl = "jdbc:" + dbdriverused + "://" + dbhostname + ":3306/" + dbname;


            // Crea connessione con il DB.
            conn = DriverManager.getConnection(dburl, dbusername, dbpassword);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Ritorna il nome del driver jdbc da utilizzare, leggendo il file "login.properties".
     */
    public static String getDriverClassName(){
        String driverClassName="NO DRIVER SPECIFIED";
        InputStream stream = ConnectProperties.class.getClassLoader().getResourceAsStream("login.properties");

        try{

            // Carica il file di configurazione.
            Properties prop = new Properties();
            prop.load(stream);

            // Preleva il driver da utilizzare.
            driverClassName = prop.getProperty("driver");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return driverClassName;
    }

}
