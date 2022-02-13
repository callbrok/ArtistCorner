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
    requires commons.validator;
    //requires java.mail;


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

    opens com.artistcorner.controller.guicontroller.getrecommendation to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.getrecommendation;

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

    opens com.artistcorner.controller.guicontroller.findartwork to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.findartwork;

    opens com.artistcorner.controller.guicontroller.viewfavourites to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewfavourites;

    opens com.artistcorner.controller.guicontroller.forwardproposal to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.forwardproposal;

    opens com.artistcorner.controller.guicontroller.viewpendingproposals to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewpendingproposals;

    opens com.artistcorner.controller.guicontroller.managefollowedartist to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.managefollowedartist;

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

    opens com.artistcorner.controller.guicontroller.mobile.getrecommendation to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.getrecommendation;

    opens com.artistcorner.controller.guicontroller.mobile.uploadartwork to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.uploadartwork;

    opens com.artistcorner.controller.guicontroller.mobile.manageartworks to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.manageartworks;

    opens com.artistcorner.controller.guicontroller.mobile.manageproposals to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.manageproposals;

    opens com.artistcorner.controller.guicontroller.mobile.viewsoldartworks to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewsoldartworks;

    opens com.artistcorner.controller.guicontroller.mobile.viewfavourites to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewfavourites;

    opens com.artistcorner.controller.guicontroller.mobile.findartwork to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.findartwork;

    opens com.artistcorner.controller.guicontroller.mobile.forwardproposal to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.forwardproposal;

    opens com.artistcorner.controller.guicontroller.mobile.viewpendingproposals to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.viewpendingproposals;

    opens com.artistcorner.controller.guicontroller.mobile.managefollowedartist to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.mobile.managefollowedartist;


}