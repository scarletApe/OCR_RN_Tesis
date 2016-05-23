
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

import edu.uaz.jmmc.filtros_imagen.DownSampler;
import edu.uaz.jmmc.filtros_imagen.PbmManager;
import edu.uaz.jmmc.filtros_imagen.Muestra;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author juanmartinez
 */
public class TestDownSampler {
    public static void main(String[] args) throws IOException {
        new TestDownSampler().run();
    }
    
    public void run() throws IOException{
        BufferedImage img = ImageIO.read(getClass().getResource("/4.png"));
        Muestra downSample = new DownSampler().downSample(img);
        
        byte[][] arr = downSample.getGrid();
        
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("");
        }
        PbmManager pbm = new PbmManager();
        pbm.guardarArregloEnImagen("/Users/juanmartinez/temp/test.pbm", arr);
    }
}
