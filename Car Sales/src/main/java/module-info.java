module com.example.kusach {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    exports com.example.semproj.entity;
    opens com.example.semproj.entity to javafx.fxml;
    exports com.example.semproj.controller;
    opens com.example.semproj.controller to javafx.fxml;
    exports com.example.semproj.client;
    opens com.example.semproj.client to javafx.fxml;
}