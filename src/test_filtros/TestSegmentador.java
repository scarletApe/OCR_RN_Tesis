package test_filtros;

import edu.uaz.jmmc.filtros_imagen.Segmentador;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

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
public class TestSegmentador {

    public static void main(String[] args) {
        try {
            new TestSegmentador().run();
        } catch (IOException ex) {
            System.out.println("error " + ex);
        }
    }

    public void run() throws IOException {
        URL resource = getClass().getResource("/imagen.png");
        System.out.println(resource);

        Image img = ImageIO.read(resource);
        Segmentador.split(new Image[]{img}, 6, 4, "1 2 3 4", "/Users/juanmartinez/temp/");
    }
}
