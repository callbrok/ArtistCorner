module com.artistcorner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;


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

    opens com.artistcorner.controller.guicontroller.viewsearchartworkbuyer to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewsearchartworkbuyer;

    opens com.artistcorner.controller.guicontroller.viewfavouritesbuyer to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewfavouritesbuyer;

    opens com.artistcorner.controller.guicontroller.viewsearchartworkgallery to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewsearchartworkgallery;

    opens com.artistcorner.controller.guicontroller.viewprofilogallery to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewprofilogallery;

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

}