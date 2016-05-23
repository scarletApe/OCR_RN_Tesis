
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
public class Muestra implements Comparable, Cloneable {

    /**
     * The downsampled data as a grid of booleans.
     */
    protected byte grid[][];

    /**
     * The letter.
     */
    protected char letter;

    /**
     * The constructor
     *
     * @param letter What letter this is
     * @param width The width
     * @param height The height
     */
    public Muestra(char letter, int width, int height) {
        grid = new byte[width][height];
        grid = new byte[height][width];
        this.letter = letter;
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
//        grid[x][y] = v;
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
//        return grid[x][y];
        return grid[y][x];
    }

    /**
     * Clear the downsampled image
     */
    public void clear() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                grid[x][y] = 0;
            }
        }
    }

    /**
     * Get the height of the down sampled image.
     *
     * @return The height of the downsampled image.
     */
    public int getHeight() {
        //return grid[0].length;
        return grid.length;
    }

    /**
     * Get the width of the downsampled image.
     *
     * @return The width of the downsampled image
     */
    public int getWidth() {
        //return grid.length;
        return grid[0].length;
    }

    /**
     * Get the letter that this sample represents.
     *
     * @return The letter that this sample represents.
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Set the letter that this sample represents.
     *
     * @param letter The letter that this sample represents.
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }

    /**
     * Compare this sample to another, used for sorting.
     *
     * @param o The object being compared against.
     * @return Same as String.compareTo
     */
    @Override
    public int compareTo(Object o) {
        Muestra obj = (Muestra) o;
        if (this.getLetter() > obj.getLetter()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Convert this sample to a string.
     *
     * @return Just returns the letter that this sample is assigned to.
     */
    @Override
    public String toString() {
        return "" + letter;
    }

    /**
     * Create a copy of this sample
     *
     * @return A copy of this sample
     */
    @Override
    public Object clone() {

        Muestra obj = new Muestra(letter, getWidth(), getHeight());
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                obj.setData(x, y, getData(x, y));
            }
        }
        return obj;
    }

}
