package test_redes;


import edu.uaz.jmmc.mlp.FuncionSigmoidea;
import edu.uaz.jmmc.mlp.RedNeuronal;
import edu.uaz.jmmc.util.RNPainter;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

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
/**
 *
 * @author juanmartinez
 */
public class TestPainter {

    public static void main(String[] args) {

        //creo la red
        RedNeuronal red = new RedNeuronal("Red AND");
        red.crearRed(4, new int[]{8}, 1, new FuncionSigmoidea());

        //el painter
        RNPainter painter = new RNPainter();
        BufferedImage img = (BufferedImage) painter.paintRN(red, 100, 100);

        //save image to file

        JFrame f = new JFrame();
        f.setVisible(true);
        
        File saveFile = new File( red.getNombre()+".png");
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(saveFile);
        int rval = chooser.showSaveDialog(f);
        if (rval == JFileChooser.APPROVE_OPTION) {
            saveFile = chooser.getSelectedFile();
            try {
                ImageIO.write(img, "png", saveFile);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.exit(0);
    }
}
