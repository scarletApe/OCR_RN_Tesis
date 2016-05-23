package test_redes;


import edu.uaz.jmmc.mlp.RedNeuronal;
import edu.uaz.jmmc.util.RNPainter;
import edu.uaz.jmmc.util.RNSerializer;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

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
public class TestLoadAndPaint {

    public static void main(String[] args) {

        RedNeuronal red = null;

        Frame f = new Frame();
        f.setVisible(true);
        FileDialog dialogo = new FileDialog(f,
                "Load?",
                FileDialog.LOAD);
        dialogo.setFile(".ser"); //Filtro de archivos
        dialogo.setDirectory("."); //Directorio actual
        dialogo.setVisible(true); //Muestra el cuadro de dialogo
        String filename = dialogo.getDirectory();//Obtenemos el directorio
        filename += dialogo.getFile();//Obtenemos es nombre del archivo seleccionado
        if (dialogo.getDirectory() != null && dialogo.getFile() != null) {
            RNSerializer ser = new RNSerializer();
            red = ser.deserializeRed(filename);
        }

        //el painter
        RNPainter painter = new RNPainter();
        BufferedImage img = (BufferedImage) painter.paintRN(red, 2000, 1200);

        //save image to file
        if (red != null) {
//        JFrame f = new JFrame();
//        f.setVisible(true);

            File saveFile = new File(red.getNombre() + ".png");
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
        }
        System.exit(0);
    }
}
