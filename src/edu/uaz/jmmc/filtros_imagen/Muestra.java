
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

/**
 *
 * @author juanmartinez
 */
public class Muestra {

    /**
     * The downsampled data as a grid of booleans.
     */
    protected byte grid[][];

    /**
     * The constructor
     *
     * @param width The width
     * @param height The height
     */
    public Muestra( int width, int height) {
        grid = new byte[width][height];
        grid = new byte[height][width];
    }

    public byte[][] getGrid() {
        return grid;
    }

    public void setGrid(byte[][] grid) {
        this.grid = grid;
    }

    /**
     * Set one pixel of sample data.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param v The value to set
     */
    public void setData(int x, int y, byte v) {
        grid[y][x] = v;
    }

    /**
     * Get a pixel from the sample.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The requested pixel
     */
    public byte getData(int x, int y) {
        return grid[y][x];
    }

    /**
     * Get the height of the down sampled image.
     *
     * @return The height of the downsampled image.
     */
    public int getHeight() {
        return grid.length;
    }

    /**
     * Get the width of the downsampled image.
     *
     * @return The width of the downsampled image
     */
    public int getWidth() {
        return grid[0].length;
    }





}
