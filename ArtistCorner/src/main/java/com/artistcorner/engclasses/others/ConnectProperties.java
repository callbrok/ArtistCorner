package com.artistcorner.engclasses.others;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectProperties {


    /**
     * Prende i valori nel file "login.properties" per effettuare la connessione al DBMS.
     */
    public static Connection getConnection(){
        Connection conn = null;

        try{

            // load the properties file
            Properties prop = new Properties();
            prop.load(new FileInputStream("ArtistCorner/src/main/resources/dbproperties/login.properties"));

            // assign db parameters
            String dbhostname = prop.getProperty("hostname");
            String dbname = prop.getProperty("dbname");
            String dbusername = prop.getProperty("username");
            String dbpassword = prop.getProperty("password");
            String dbdriverused = prop.getProperty("driverused");

            String dburl = "jdbc:" + dbdriverused + "://" + dbhostname + ":3306/" + dbname;


            // create a connection to the database
            conn = DriverManager.getConnection(dburl, dbusername, dbpassword);

        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Ritorna il nome del driver jdbc da utilizzare, leggendo il file "login.properties".
     */
    public static String getDriverClassName(){
        String driverClassName="NO DRIVER SPECIFIED";

        try{

            // load the properties file
            Properties prop = new Properties();
            prop.load(new FileInputStream("ArtistCorner/src/main/resources/dbproperties/login.properties"));

            // assign db parameters
            driverClassName = prop.getProperty("driver");


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return driverClassName;
    }

}
