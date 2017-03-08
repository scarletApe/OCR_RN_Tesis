
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
public class DescodificarImagenesService extends Service<String> {

    private ArrayList<File> files;
    private MLP red;

    public void init(ArrayList<File> pbm_files, MLP red) {
        this.red = red;
        this.files = pbm_files;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {

            @Override
            protected String call() throws IOException, MalformedURLException {
                PbmManager pbm = new PbmManager();
                StringBuilder sb = new StringBuilder();
                int num_correct = 0;
                for (int i = 0; i < files.size(); i++) {
                    File f = files.get(i);
                    byte[][] cargarImagenEnArreglo = pbm.cargarArregloDeImagen(f.toURI());
                    byte[] flat = pbm.flattenArray(cargarImagenEnArreglo);
                    char expected = f.getName().charAt(0);

                    double[] resultado = red.clasificar(flat);
                    byte[] binarizedArray = binarizeArray(resultado);

                    char check = check(binarizedArray);

                    boolean same = false;
                    if (expected == check) {
                        same = true;
                        num_correct++;
                    }

                    sb.append(f.getName())
                            .append("  \t")
                            .append(arrayToString(binarizedArray))
                            .append("\t")
                            .append(expected)
                            .append(" clasificado como ")
                            .append(check)
                            .append(" ")
                            .append(same);
                    sb.append("\n");
                }
                double precision = (double) num_correct / (double) files.size();
                precision = precision * 100;
                sb.append("Porcentaje de Presición ").append(precision);

                return sb.toString();
            }

        };

    }
    byte[][] patrones_salidas = new byte[][]{
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

    public char check(byte[] ch) {
        char is = '?';
        for (int i = 0; i < patrones_salidas.length; i++) {
            boolean es = true;
            for (int j = 0; j < patrones_salidas[i].length; j++) {
//                if (ch[j] == patrones_salidas[i][j]) {
//                    break;
//                }
                es = es && (ch[j] == patrones_salidas[i][j]);

            }
            if (es) {
//                String s= i+"";
//                is = s.charAt(0);
                is = (char) (i + 48);
                break;
            }
        }
        return is;
    }

    public String arrayToString(byte[] d) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < d.length; i++) {
            sb.append(d[i]).append(" ");
        }
//        sb.append("")
        return sb.toString();
    }

    public byte[] binarizeArray(double[] arr) {
        byte[] bi = new byte[arr.length];

        for (int i = 0; i < bi.length; i++) {
            bi[i] = (arr[i] > 0.5) ? (byte) 1 : (byte) 0;
        }
        return bi;
    }
}
