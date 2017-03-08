//<editor-fold defaultstate="collapsed" desc="licence">
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//</editor-fold>
package edu.uaz.jmmc.util;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Este elemento es un canvas en el cual el usuario puede dibujar lineas negras.
 * @author juanmartinez
 */
public class DrawingCanvas extends Canvas {

    private GraphicsContext gc;
    private final double width;
    private final double height;
    private int strokeWidth;

    /**
     * The last x and y that the user was drawing at.
     */
    protected double lastX = -1;
    protected double lastY = -1;

    public DrawingCanvas(double width, double height) {
        super(width, height);

        this.width = width;
        this.height = height;

        init();
    }

    private void init() {
        gc = this.getGraphicsContext2D();

        reset(Color.WHITE);
        strokeWidth = 2;

        // draw black line
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent e) -> {

            gc.setFill(Color.BLACK);
            gc.setLineWidth(strokeWidth);
            gc.strokeLine(lastX, lastY, e.getX(), e.getY());

            lastX = e.getX();
            lastY = e.getY();
        });

        // get last x and y 
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            lastX = e.getX();
            lastY = e.getY();
        });
    }

    /**
     * Resets the canvas to its original look by filling in a rectangle covering
     * its entire width and height. Color.BLUE is used in this demo.
     *
     * @param color The color to fill
     */
    public void reset(Color color) {
        gc.setFill(color);
        gc.fillRect(0, 0, width, height);
    }

    /**
     * Moves the canvas to a new location within the Scene. This is accomplished
     * by performing a translation transformation on the Canvas object, passing
     * in the desired x and y coordinates. Passing in values of 0,0 will
     * position the Canvas in the upper left corner of the Scene.
     *
     * @param x The new x coordinate
     * @param y The new y coordinate
     */
    public void moveCanvas(int x, int y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public java.awt.Image getImage() {
        WritableImage image = this.snapshot(null, null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        return bImage;
    }
}
