/*
 * Copyright (C) 2016 juanmartinez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.uaz.jmmc.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author juanmartinez
 */
public class CrearRedVentaController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tbNEntrada;
    @FXML
    private TextField tfNOcultas;
    @FXML
    private TextField tfNSalida;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnCrearRed;

    private VentanaMainController vmc;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        //hide this current window 
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void handleCrearRed(ActionEvent event) {
        String nombre = tfNombre.getText();
        String ne = tbNEntrada.getText();
        String no = tfNOcultas.getText();
        String ns = tfNSalida.getText();

        if (nombre.isEmpty()
                || ne.isEmpty()
                || no.isEmpty()
                || ns.isEmpty()) {
            return;
        }
        int entradas, ocultas[], salida;
        try {
            entradas = Integer.parseInt(ne);
            String[] split = no.split(" ");
            ocultas = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                ocultas[i] = Integer.parseInt(split[i]);
            }
            salida = Integer.parseInt(ns);
        } catch (Exception e) {
            return;
        }
        vmc.inicializarRed(nombre, entradas, ocultas, salida);
        handleCancelar(event);
    }

    public void initData(VentanaMainController v) {
        vmc = v;
    }
}
