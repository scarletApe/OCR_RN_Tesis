package edu.uaz.jmmc.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author manuelmartinez
 */
public class GUI_MLP extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/uaz/jmmc/gui/VentanaMain.fxml"));

        stage.setTitle("Sistema de OCR con Red Neuronal");
        stage.setScene(new Scene((Parent) loader.load()));

        VentanaMainController controller = loader.<VentanaMainController>getController();
        controller.initData(stage);

        stage.setMaximized(true);
//        stage.setMinWidth(800);
//        stage.setMaxHeight(600);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
