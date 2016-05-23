package test_redes;


import edu.uaz.jmmc.mlp.RedNeuronal;
import edu.uaz.jmmc.util.RNSerializer;
import java.awt.FileDialog;
import java.awt.Frame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author juanmartinez
 */
public class TestLoadRedNegacion {

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

        if (red != null) {
            byte[][] entrada = new byte[][]{
                new byte[]{0, 0, 0, 0},
                new byte[]{0, 0, 0, 1},
                new byte[]{0, 0, 1, 0},
                new byte[]{0, 0, 1, 1},
                new byte[]{0, 1, 0, 0},
                new byte[]{0, 1, 0, 1},
                new byte[]{0, 1, 1, 0},
                new byte[]{0, 1, 1, 1},
                new byte[]{1, 0, 0, 0},
                new byte[]{1, 0, 0, 1},
                new byte[]{1, 0, 1, 0},
                new byte[]{1, 0, 1, 1},
                new byte[]{1, 1, 0, 0},
                new byte[]{1, 1, 0, 1},
                new byte[]{1, 1, 1, 0},
                new byte[]{1, 1, 1, 1}};

            //probar la red
            for (int h = 0; h < entrada.length; h++) {
                byte[] prueba = entrada[h];

                System.out.print("\nProbando " + red.getNombre() + " con:");
                for (int i = 0; i < prueba.length; i++) {
                    System.out.print("" + prueba[i] + ", ");
                }
                double[] result = red.clasificar(prueba);
                System.out.print("\nResultados:");
                for (int i = 0; i < result.length; i++) {
                    System.out.println("" + result[i]);
                }
            }
        }
        System.exit(0);
    }
}
