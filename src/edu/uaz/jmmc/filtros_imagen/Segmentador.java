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
package edu.uaz.jmmc.filtros_imagen;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Esta clase solo segmenta la imagen entera en pequeñas regiones especificadas
 * por el usuario, no auto segmaneta una palabra en characteres idividuos.
 *
 * @author juanmartinez
 */
public class Segmentador {

    /**
     * Este metodo toma un arreglo de imagenes y las divide en celulas
     * individuales.
     *
     * @param imagens Las imagenes todas deben ser del mismo tamaño
     * @param num_rows numero de renglones a dividir
     * @param num_cols numero de columnas a dividir
     * @param characteres los caracteres que se representan en cada renglon,
     * tiene que que el mismo numero que numero de columnas
     * @param directorio El directorio a escribir las imagenes resultantes
     * @throws IOException
     */
    public static void split(Image[] imagens,
            int num_rows, int num_cols,
            String characteres,
            String directorio) throws IOException {

        List<BufferedImage[]> list_imagenes = new ArrayList<>(num_rows * num_cols);
        String[] chars = characteres.split(" ");
        int width = imagens[0].getWidth(null) / num_cols;
        int height = imagens[0].getHeight(null) / num_rows;

        System.out.println("small cell width=" + width);
        System.out.println("small cell height=" + height);
        System.out.println("characters=" + characteres);
        System.out.println("directorio=" + directorio);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration();

        //creo pequenas imagenes y las meto a un arrelgo y luego a la lista
        for (int i = 0; i < imagens.length; i++) {

            //split into rows and add to arraylist
            Image imagen_original = imagens[i];

            for (int k = 0; k < num_rows; k++) {

                BufferedImage[] arreglo = new BufferedImage[num_cols];

                for (int j = 0; j < num_cols; j++) {
                    BufferedImage image = gc.createCompatibleImage(width, height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = image.createGraphics();

                    boolean drawImage = g.drawImage(imagen_original,
                            0, 0, width, height,
                            //                            k * height, j * width, (k * height) + width, (j * width) + height,
                            j * width, k * height, (j + 1) * width, (k + 1) * height,
                            //                            k * width, j * height, (k * width) + height, (j * height) + width,
                            null);

                    System.out.println(i+" drew image,,,," + drawImage);
                    g.dispose();
                    arreglo[j] = image;
                }

                list_imagenes.add(arreglo);
            }

        }

        //escribir las imagenes al directorio
        for (int i = 0; i < list_imagenes.size(); i++) {
            BufferedImage[] ia = list_imagenes.get(i);
            for (int j = 0; j < ia.length; j++) {
                //save to disk
                File f = new File(directorio + chars[j] + "_" + i + ".png");
                boolean write = ImageIO.write(ia[j], "png", f);
                System.out.println("escribiendo... " + f.toString() + " " + write);
            }
        }

        System.out.println("numero renglones=" + list_imagenes.size());
        System.out.println("numero columnas=" + list_imagenes.get(0).length);
    }
}
