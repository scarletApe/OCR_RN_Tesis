
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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import edu.uaz.jmmc.filtros_imagen.PbmManager;
import edu.uaz.jmmc.mlp.MLP;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author juanmartinez
 */
public class EntrenarImagenesService extends Service<Void> {

    private double ta;
    private double et;
    private double[][] salida;
    private byte[][] entrada;
    private MLP red;
    private boolean iterar;
    private int iteraciones;
    private ArrayList<File> pbm_files;

    private final double[][] patrones_salidas;

    public EntrenarImagenesService() {
        this.patrones_salidas = new double[][]{
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
        };
    }

    public void init(double ta,
            double et,
            //            double[][] salida,
            //            byte[][] entrada,
            ArrayList<File> pbm_files,
            MLP red,
            boolean iterar,
            int iteraciones) {
        this.ta = ta;
        this.et = et;

        this.red = red;
        this.iterar = iterar;
        this.iteraciones = iteraciones;

        this.pbm_files = pbm_files;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws IOException, MalformedURLException {
                //primero preparar los datos de entrad y de salidas esperadas

                //inizializar los arreglos de datos de entrada y salida
                entrada = new byte[pbm_files.size()][];
                salida = new double[pbm_files.size()][];

                PbmManager pm = new PbmManager();
                try {
                    for (int i = 0; i < pbm_files.size(); i++) {
                        File file = pbm_files.get(i);

                        System.out.println("\tFile:" + file);

                        byte[][] arreglo = pm.cargarArregloDeImagen(file.toURI());
                        entrada[i] = pm.flattenArray(arreglo);

                        System.out.println("\tArreglo Tamaño:" + entrada[i].length);

                        char digito = file.getName().charAt(0);

                        System.out.println("\nDigito=" + digito);

                        int parseInt = Integer.parseInt(digito + "");
                        salida[i] = patrones_salidas[parseInt];

                        System.out.println("");
                    }
                } catch (Exception e) {
                    System.out.println("Error en Task=" + e);
                    return null;
                }
                /**
                 * @todo
                 */

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
