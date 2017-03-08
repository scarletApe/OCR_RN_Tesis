
//<editor-fold defaultstate="collapsed" desc="licence">
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
//</editor-fold>


package edu.uaz.jmmc.gui.servicios;

import java.io.IOException;
import java.net.MalformedURLException;

import edu.uaz.jmmc.mlp.MLP;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author juanmartinez
 */
public class EntrenarService extends Service<Void> {

    private double ta;
    private double et;
    private double[][] salida;
    private byte[][] entrada;
    private MLP red;
    private boolean iterar;
    private int iteraciones;

    public void init(double ta,
            double et,
            double[][] salida,
            byte[][] entrada,
            MLP red,
            boolean iterar,
            int iteraciones) {
        this.ta = ta;
        this.et = et;
        this.salida = salida;
        this.entrada = entrada;
        this.red = red;
        this.iterar = iterar;
        this.iteraciones = iteraciones;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws IOException, MalformedURLException {
                if (iterar) {
                    red.entrenarRed(entrada, salida, ta, iteraciones);
                } else {
                    red.entrenarRed(entrada, salida, ta, et);
                }
                updateProgress(5, 5);
                return null;
            }

        };
    }
}
