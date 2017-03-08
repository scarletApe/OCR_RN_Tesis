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
package edu.uaz.jmmc.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Esta clase se encarga de hacer una imagen que representa al arreglo dado.
 *
 * @author juanmartinez
 */
public class DrawMuestra {

//    private static final int WIDTH = 50;
//    private static final int HEIGHT = 70;
    private static final int WIDTH = 125;
    private static final int HEIGHT = 175;
    private static final int HCELL = 25;
    private static final int VCELL = 25;

    /**
     * Toma un arreglo bidimensional y regresa su representacion en imagen. El
     * arreglo debe ser de valores binarios 1 o 0s.
     *
     * @param data Arreglo bidimensional 1 o 0s.
     * @return Imagen de tipo BufferedImage
     */
    public static Image dibujarMuestra(byte[][] data) {
        Image image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.black);
        //draw the squares
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] == 1) {
                    g.fillRect(j * HCELL, i * VCELL, HCELL, VCELL);
                }
            }
        }

        return image;
    }
}
