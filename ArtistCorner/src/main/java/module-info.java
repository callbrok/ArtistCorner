module com.artistcorner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires org.eclipse.jgit;
    requires org.apache.commons.lang3;
    requires org.slf4j.simple;
    requires org.slf4j;
    requires org.junit.platform.commons;
    requires org.junit.platform.engine;
    requires org.seleniumhq.selenium.api;


    opens com.artistcorner to javafx.fxml;
    exports com.artistcorner;

    opens com.artistcorner.controller.guicontroller.login.summarypanel to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.login.summarypanel;

    opens com.artistcorner.controller.guicontroller.login to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.login;

    opens com.artistcorner.controller.applicationcontroller to javafx.fxml;
    exports com.artistcorner.controller.applicationcontroller;

    opens com.artistcorner.controller.guicontroller.getreccomandation to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.getreccomandation;

    opens com.artistcorner.controller.guicontroller.viewartgalleryproposals to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewartgalleryproposals;

    opens com.artistcorner.controller.guicontroller.viewprofile to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewprofile;

    opens com.artistcorner.controller.guicontroller.viewsaleshistory to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewsaleshistory;

    opens com.artistcorner.controller.guicontroller.uploadartwork to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.uploadartwork;

    opens com.artistcorner.controller.guicontroller.viewloganalytics to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewloganalytics;

    opens com.artistcorner.controller.guicontroller.viewsearchartworkbuyer to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewsearchartworkbuyer;

    opens com.artistcorner.controller.guicontroller.getartwork to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.getartwork;

    opens com.artistcorner.controller.guicontroller.viewfavouritesbuyer to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewfavouritesbuyer;

    opens com.artistcorner.controller.guicontroller.viewsearchartworkgallery to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewsearchartworkgallery;

    opens com.artistcorner.controller.guicontroller.viewprofilogallery to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewprofilogallery;

    opens com.artistcorner.engclasses.exceptions to javafx.fxml;
    exports com.artistcorner.engclasses.exceptions;

    opens com.artistcorner.engclasses.bean to javafx.fxml;
    exports com.artistcorner.engclasses.bean;

    opens com.artistcorner.controller.guicontroller.mobile.login to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.login;

    opens com.artistcorner.controller.guicontroller.mobile.login.summarypanel to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.login.summarypanel;

    opens com.artistcorner.controller.guicontroller.mobile.getreccomandation to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.getreccomandation;

    opens com.artistcorner.controller.guicontroller.mobile.uploadartwork to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.uploadartwork;

    opens com.artistcorner.controller.guicontroller.mobile.viewprofile to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewprofile;

    opens com.artistcorner.controller.guicontroller.mobile.viewartgalleryproposals to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewartgalleryproposals;

    opens com.artistcorner.controller.guicontroller.mobile.viewsaleshistory to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewsaleshistory;

    opens com.artistcorner.controller.guicontroller.mobile.viewfavouritesbuyer to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewfavouritesbuyer;

    opens com.artistcorner.controller.guicontroller.mobile.viewsearchartworkbuyer to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewsearchartworkbuyer;

    opens com.artistcorner.controller.guicontroller.mobile.viewsearchartworkgallery to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewsearchartworkgallery;

    opens com.artistcorner.controller.guicontroller.mobile.viewprofilogallery to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewprofilogallery;

}