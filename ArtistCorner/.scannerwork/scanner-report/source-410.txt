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
    requires javax.mail.api;


    opens com.artistcorner to javafx.fxml;
    exports com.artistcorner;

    opens com.artistcorner.controller.guicontroller.login.summaries to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.login.summaries;

    opens com.artistcorner.controller.guicontroller.login to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.login;

    opens com.artistcorner.controller.applicationcontroller to javafx.fxml;
    exports com.artistcorner.controller.applicationcontroller;

    opens com.artistcorner.controller.applicationcontroller.login to javafx.fxml;
    exports com.artistcorner.controller.applicationcontroller.login;


    opens com.artistcorner.controller.applicationcontroller.login.summaries to javafx.fxml;
    exports com.artistcorner.controller.applicationcontroller.login.summaries;

    opens com.artistcorner.controller.guicontroller.getreccomandation to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.getreccomandation;

    opens com.artistcorner.controller.guicontroller.manageproposals to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.manageproposals;

    opens com.artistcorner.controller.guicontroller.manageartworks to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.manageartworks;

    opens com.artistcorner.controller.guicontroller.viewsoldartworks to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewsoldartworks;

    opens com.artistcorner.controller.guicontroller.uploadartwork to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.uploadartwork;

    opens com.artistcorner.engclasses.others.analytics to javafx.fxml;
    exports com.artistcorner.engclasses.others.analytics;

    opens com.artistcorner.controller.guicontroller.viewsearchartworkbuyer to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewsearchartworkbuyer;

    opens com.artistcorner.controller.guicontroller.viewfavouritesbuyer to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewfavouritesbuyer;

    opens com.artistcorner.controller.guicontroller.viewsearchartworkgallery to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewsearchartworkgallery;

    opens com.artistcorner.controller.guicontroller.viewsentartgalleryproposal to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewsentartgalleryproposal;

    opens com.artistcorner.controller.guicontroller.viewprofilogallery to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewprofilogallery;

    opens com.artistcorner.engclasses.others to javafx.fxml;
    exports com.artistcorner.engclasses.others;

    opens com.artistcorner.engclasses.exceptions to javafx.fxml;
    exports com.artistcorner.engclasses.exceptions;

    opens com.artistcorner.engclasses.bean to javafx.fxml;
    exports com.artistcorner.engclasses.bean;

    opens com.artistcorner.controller.guicontroller.mobile.login to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.login;

    opens com.artistcorner.controller.guicontroller.mobile.login.summaries to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.login.summaries;

    opens com.artistcorner.controller.guicontroller.mobile.getreccomandation to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.getreccomandation;

    opens com.artistcorner.controller.guicontroller.mobile.uploadartwork to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.uploadartwork;

    opens com.artistcorner.controller.guicontroller.mobile.manageartworks to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.manageartworks;

    opens com.artistcorner.controller.guicontroller.mobile.manageproposals to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.manageproposals;

    opens com.artistcorner.controller.guicontroller.mobile.viewsoldartworks to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewsoldartworks;

    opens com.artistcorner.controller.guicontroller.mobile.viewfavouritesbuyer to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewfavouritesbuyer;

    opens com.artistcorner.controller.guicontroller.mobile.viewsearchartworkbuyer to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewsearchartworkbuyer;

    opens com.artistcorner.controller.guicontroller.mobile.viewsearchartworkgallery to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewsearchartworkgallery;

    opens com.artistcorner.controller.guicontroller.mobile.viewsentartgalleryproposal to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewsentartgalleryproposal;

    opens com.artistcorner.controller.guicontroller.mobile.viewprofilegallery to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewprofilegallery;


}