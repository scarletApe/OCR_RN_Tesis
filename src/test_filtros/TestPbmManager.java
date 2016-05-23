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
package test_filtros;

import edu.uaz.jmmc.filtros_imagen.PbmManager;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author juanmartinez
 */
public class TestPbmManager {
    
    //        byte[][] entrada = new byte[][]{
//            new byte[]{0, 0},
//            new byte[]{0, 1},
//            new byte[]{1, 0},
//            new byte[]{1, 1},};

    byte[][] entrada = new byte[][]{
        new byte[]{0, 0, 0, 0},
        new byte[]{0, 0, 0, 1},
        new byte[]{0, 0, 1, 0},
        new byte[]{0, 0, 1, 1},
        new byte[]{0, 1, 0, 0},
        new byte[]{0, 1, 0, 1},
        new byte[]{0, 1, 1, 0},
        new byte[]{0, 1, 1, 1},
        new byte[]{1, 0, 0, 0},
        new byte[]{1, 0, 0, 1},
        new byte[]{1, 0, 1, 0},
        new byte[]{1, 0, 1, 1},
        new byte[]{1, 1, 0, 0},
        new byte[]{1, 1, 0, 1},
        new byte[]{1, 1, 1, 0},
        new byte[]{1, 1, 1, 1}};

    public static void main(String[] args) {
        new TestPbmManager().testGuardarImagen();
    }

    public void testGuardarImagen() {

        PbmManager p = new PbmManager();

        p.guardarArregloEnImagen("/Users/juanmartinez/temp/and.pbm", entrada);

        String ssl = p.construirContenido(entrada);
        System.out.println(ssl);
    }

    public void testLoad() throws URISyntaxException {
        PbmManager p = new PbmManager();
        URL resource = getClass().getResource("/and.pbm");
        System.out.println(resource.toString());

        byte[][] arr = p.cargarArregloDeImagen(resource.toURI());

        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public void testFlatten() {
        PbmManager p = new PbmManager();
        byte[] a = p.flattenArray(entrada);

        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }
}
