/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uaz.jmmc.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import edu.uaz.jmmc.mlp.MLP;

/**
 *
 * @author juanmartinez
 */
public class RNPainter {

    private MLP red;
    private int num_capas;
    private ArrayList<PNeurona[]> capas;
    private int panel_width;
    private int panel_height;
    private int w_space;
//    private boolean draw_weights;
    private BufferedImage neurona;
    private BufferedImage in;
    private BufferedImage arrow;

    public RNPainter() {

    }

    public Image paintRN(MLP red, int pwidth, int pheight) {
        this.red = red;
        panel_width = pwidth;
        panel_height = pheight;
//        draw_weights = drw_weights;

        loadNeuronImg();
        initPainter();

        // create an accelerated image of the right size to store our sprite in
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(panel_width, pheight, Transparency.BITMASK);
        Graphics2D graphics = (Graphics2D) image.getGraphics();

        paintSinapsis(graphics);
        paintNeurons(graphics);
        //paints the little arrows
        paintInOutArrows(graphics);

        graphics.dispose();

        return image;
    }

    private void initPainter() {
        num_capas = 2; //la capa de entrada y la de salida
//        num_capas += red.getCapasOcultas().length; //mas el numero de capas ocultas
        num_capas += red.getSizeofCapasOcultas().length; //mas el numero de capas ocultas

        w_space = panel_width / (num_capas + 1);

        //create the guts
        capas = new ArrayList<>();
        int nn;
        int vspace;
        int cap_ocult_count = 0;
        for (int i = 0; i < num_capas; i++) {
            if (i == 0) {
                //first one
//                nn = red.getCapaEntrada().getNeuronas().length;
                nn = red.getSizeofCapaEntrada();
                
                vspace = panel_height / (nn + 1);
                PNeurona c[] = new PNeurona[nn];

                for (int j = 0; j < c.length; j++) {
                    c[j] = new PNeurona(in, w_space, (j + 1) * vspace);
                }
                capas.add(c);
                continue;
            }
            if (i == num_capas - 1) {
                //last one
//                nn = red.getCapaSalida().getNeuronas().length;
                nn = red.getSizeofCapaSalida();
                
                vspace = panel_height / (nn + 1);
                PNeurona c[] = new PNeurona[nn];

                for (int j = 0; j < c.length; j++) {
                    c[j] = new PNeurona(neurona, num_capas * w_space, (j + 1) * vspace);
                }
                capas.add(c);
                continue;
            }

            //in a hidden layer
//            nn = red.getCapasOcultas()[cap_ocult_count].getNeuronas().length;
            nn = red.getSizeofCapasOcultas()[cap_ocult_count];
            
            vspace = panel_height / (nn + 1);
            PNeurona c[] = new PNeurona[nn];

            for (int j = 0; j < c.length; j++) {
                c[j] = new PNeurona(neurona, (i + 1) * w_space, (j + 1) * vspace);
            }
            capas.add(c);
            cap_ocult_count++;
        }

    }

    private void paintSinapsis(Graphics2D g) {
        PNeurona[] este;
        PNeurona[] next;
        PNeurona n;
        PNeurona o;

        //get the old stroke
//        Stroke old_stroke = g.getStroke();
        //set the new stroke
//        g.setStroke(new BasicStroke(1f));
        //to smothen the lines
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //set the color
        g.setColor(Color.black);

        for (int i = 0; i < capas.size() - 1; i++) {
            este = capas.get(i);
            next = capas.get(i + 1);
            for (int j = 0; j < este.length; j++) {
                n = este[j];
                for (int k = 0; k < next.length; k++) {
                    o = next[k];
                    g.drawLine(n.getX(), n.getY(),
                            o.getX(), o.getY());
                }
            }
        }
        //set the old stroke back
//        g.setStroke(old_stroke);
    }

    private void paintInOutArrows(Graphics2D g) {
        int x;
        int y;

        //la capa de entrada
        PNeurona[] c = capas.get(0);
        for (PNeurona c1 : c) {
            x = c1.getX();
            y = c1.getY();
            g.drawImage(arrow, null, x - (arrow.getWidth() / 2) - 40, y - (arrow.getHeight() / 2));
        }

        //la capa de salida
        c = capas.get(capas.size() - 1);
        for (PNeurona c1 : c) {
            x = c1.getX();
            y = c1.getY();
            g.drawImage(arrow, null, x - (arrow.getWidth() / 2) + 40, y - (arrow.getHeight() / 2));
        }
    }

    private void paintNeurons(Graphics2D g) {
        for (PNeurona[] capa : capas) {
            for (PNeurona pn : capa) {
                pn.paint(g);
            }
        }
    }

    private void loadNeuronImg() {
        try {
            neurona = ImageIO.read(this.getClass().getResource("/edu/uaz/jmmc/imagenes/neurona.png"));
            in = ImageIO.read(this.getClass().getResource("/edu/uaz/jmmc/imagenes/in.png"));
            arrow = ImageIO.read(this.getClass().getResource("/edu/uaz/jmmc/imagenes/arrow.png"));
        } catch (Exception e) {
        }
    }
}

class PNeurona {

    private BufferedImage img;
    private int x;
    private int y;

    public PNeurona(BufferedImage img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics2D g) {
        g.drawImage(img, null, x - (img.getWidth() / 2), y - (img.getHeight() / 2));
    }
}
