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

import java.awt.Image;
import java.awt.image.PixelGrabber;

/**
 *
 * @author juanmartinez
 */
public class DownSampler {

    /**
     * The image that the user is drawing into.
     */
    private Image entryImage;
    /**
     * Specifies the left boundary of the cropping rectangle.
     */
    private int downSampleLeft;

    /**
     * Specifies the right boundary of the cropping rectangle.
     */
    private int downSampleRight;

    /**
     * Specifies the top boundary of the cropping rectangle.
     */
    private int downSampleTop;

    /**
     * Specifies the bottom boundary of the cropping rectangle.
     */
    private int downSampleBottom;

    /**
     * The downsample ratio for x.
     */
    private double ratioX;

    /**
     * The downsample ratio for y
     */
    private double ratioY;
    /**
     * The pixel map of what the user has drawn. Used to downsample it.
     */
    private int pixelMap[];

    /**
     * The downsample width for the application.
     */
    private static final int DOWNSAMPLE_WIDTH = 5;//original 5

    /**
     * The down sample height for the application.
     */
    private static final int DOWNSAMPLE_HEIGHT = 7;//original 7

    /**
     * Called to downsample the image and store it in the down sample component.
     *
     * @param image
     * @return
     */
    public Muestra downSample(Image image) {
        entryImage = image;
        Muestra data = null;
        int w = entryImage.getWidth(null);
        int h = entryImage.getHeight(null);

        PixelGrabber grabber = new PixelGrabber(
                entryImage,
                0,
                0,
                w,
                h,
                true);
        try {

            grabber.grabPixels();
            pixelMap = (int[]) grabber.getPixels();
            findBounds(w, h);

            // now downsample
//            SampleData data = sample.getData();
            data = new Muestra(' ', DOWNSAMPLE_WIDTH, DOWNSAMPLE_HEIGHT);

            ratioX = (double) (downSampleRight
                    - downSampleLeft) / (double) data.getWidth();
            ratioY = (double) (downSampleBottom
                    - downSampleTop) / (double) data.getHeight();

            for (int y = 0; y < data.getHeight(); y++) {
                for (int x = 0; x < data.getWidth(); x++) {
                    if (downSampleQuadrant(x, y)) {
                        data.setData(x, y, (byte) 1);
                    } else {
                        data.setData(x, y, (byte) 0);
                    }
                }
            }

        } catch (InterruptedException e) {
        }
        return data;
    }

    /**
     * Called to downsample a quadrant of the image.
     *
     * @param x The x coordinate of the resulting downsample.
     * @param y The y coordinate of the resulting downsample.
     * @return Returns true if there were ANY pixels in the specified quadrant.
     */
    private boolean downSampleQuadrant(int x, int y) {
        int w = entryImage.getWidth(null);
        int startX = (int) (downSampleLeft + (x * ratioX));
        int startY = (int) (downSampleTop + (y * ratioY));
        int endX = (int) (startX + ratioX);
        int endY = (int) (startY + ratioY);

        for (int yy = startY; yy <= endY; yy++) {
            for (int xx = startX; xx <= endX; xx++) {
                int loc = xx + (yy * w);

                if (pixelMap[loc] != -1) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * This method is called to automatically crop the image so that whitespace
     * is removed.
     *
     * @param w The width of the image.
     * @param h The height of the image
     */
    private void findBounds(int w, int h) {
        // top line
        for (int y = 0; y < h; y++) {
            if (!hLineClear(y)) {
                downSampleTop = y;
                break;
            }

        }
        // bottom line
        for (int y = h - 1; y >= 0; y--) {
            if (!hLineClear(y)) {
                downSampleBottom = y;
                break;
            }
        }
        // left line
        for (int x = 0; x < w; x++) {
            if (!vLineClear(x)) {
                downSampleLeft = x;
                break;
            }
        }

        // right line
        for (int x = w - 1; x >= 0; x--) {
            if (!vLineClear(x)) {
                downSampleRight = x;
                break;
            }
        }
    }

    /**
     * This method is called to determine ....
     *
     * @param x The vertical line to scan.
     * @return True if there are any pixels in the specified vertical line.
     */
    private boolean vLineClear(int x) {
        int w = entryImage.getWidth(null);
        int h = entryImage.getHeight(null);
        for (int i = 0; i < h; i++) {
            if (pixelMap[(i * w) + x] != -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is called internally to see if there are any pixels in the
     * given scan line. This method is used to perform autocropping.
     *
     * @param y The horizontal line to scan.
     * @return True if there were any pixels in this horizontal line.
     */
    private boolean hLineClear(int y) {
        int w = entryImage.getWidth(null);
        for (int i = 0; i < w; i++) {
            if (pixelMap[(y * w) + i] != -1) {
                return false;
            }
        }
        return true;
    }
}

