package com.mycompany.proyectogpx;

import com.mycompany.proyectogpx.beans.Gpx;
import com.mycompany.proyectogpx.beans.TrkPT;
import com.mycompany.proyectogpx.util.UtilXML;
import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
    // Declaración de las variabels usadas
    private final double DEFAULT_LAT = 36.67969;
    private final double DEFAULT_LON = -5.44484;
    private final int DEFAULT_ZOOM = 15;
    private WebView webView = new WebView();
    private WebEngine webEngine = webView.getEngine();
    TextArea textArea = new TextArea();
    private static VBox vboxItems = new VBox();
    private static Label labelTitulo = new Label();
    private static Label labelTiempo = new Label();
    VBox panelCentral = new VBox(textArea);
    Gpx gpx;
    
    @Override
    public void start(Stage stage) {
        BorderPane rootPane = new BorderPane();
        var scene = new Scene(rootPane, 640, 480);
        stage.setScene(scene);
        stage.show();
        rootPane.setTop(paneTop(stage, textArea));
        rootPane.setCenter(panelCentral);
        textArea.setEditable(false);
        // Para cargar el mapa
        URL urlWebMap = this.getClass().getResource("/web/mapa.html");
        webEngine.load(urlWebMap.toString());
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState)->{
            if(newState == Worker.State.SUCCEEDED ) {
                webEngine.executeScript("initMap("+DEFAULT_LAT+","+DEFAULT_LON+","+DEFAULT_ZOOM+")");
            }
        });
        rootPane.setRight(webView);
        
        
    }
    // Se distribuyen los elemntos de la aplicación
    public VBox paneTop(Stage stage, TextArea textArea){
        Button buttonAbrirFichero = new Button("Abrir archivo");
        Button buttonBrowse = new Button("Dibujar línea");
        HBox hbox  = new HBox(buttonAbrirFichero, buttonBrowse);
        hbox.setSpacing(10);
        VBox vbox = new VBox(hbox, labelTitulo, labelTiempo);
        vbox.setSpacing(10);
        buttonAbrirFichero.setOnAction((t) ->{
            procesarXmlFileGPX(stage, textArea);
        });
        buttonBrowse.setOnAction((t) -> {
            dibujarLinea();
        });
        return vbox;
    }
    // Para procesar la ruta
    public void procesarXmlFileGPX(Stage stage, TextArea textArea){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir Ruta");
            File archivoSeleccionado = fileChooser.showOpenDialog(stage);
            gpx = UtilXML.procesarGPX(archivoSeleccionado);
            String strGpxNombreRuta = "";
            strGpxNombreRuta += gpx.getMetadata().getName();
            String strGpxTiempoRuta = "";
            strGpxTiempoRuta += gpx.getMetadata().getTime();
            
            //Mostrar los datos elegidos en el textArea con un salto de línea
            textArea.setText(strGpxNombreRuta + "\n" + strGpxTiempoRuta);
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText(ex.toString());
            alert.showAndWait();
        }
    }
    // Para dibujar la linea de la ruta
    public void dibujarLinea() {
        String r = "[";
        for(TrkPT trkpt : gpx.getTrk().getTrkseg().getTrkpt()) {           
                r +="[" + trkpt.getLat()+", " + trkpt.getLon()+ "]" + ", ";
        }
            r += "]";
        webEngine.executeScript("clearTrack()");
        webEngine.executeScript("drawTrack("+r+")");
    }

    public static void main(String[] args) {
        launch();
    }

}