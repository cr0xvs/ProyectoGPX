module com.mycompany.proyectogpx {
    requires javafx.controls;
    requires java.xml.bind;
    requires jakarta.activation;
    requires java.activation;
    requires javafx.web;
    opens com.mycompany.proyectogpx.beans to java.xml.bind;
    opens com.mycompany.proyectogpx.util to java.xml.bind;
    opens com.mycompany.proyectogpx to java.xml.bind;
    exports com.mycompany.proyectogpx;
}
