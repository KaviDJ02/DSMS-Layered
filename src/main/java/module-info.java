module com.drivesmart.dsms {
    requires java.sql;
    requires static lombok;
    requires dotenv.java;
    requires javax.mail.api;
    requires jfxtras.agenda;
    requires com.calendarfx.view;
    requires ical4j.core;
    requires javafx.web;
    requires java.desktop;
    requires net.sf.jasperreports.core;
    requires activation;
    requires java.net.http;

    opens com.drivesmart.dsms to javafx.fxml;
    opens com.drivesmart.dsms.controller to javafx.fxml;
    opens com.drivesmart.dsms.dto to javafx.base;

    exports com.drivesmart.dsms;
    exports com.drivesmart.dsms.controller;
}