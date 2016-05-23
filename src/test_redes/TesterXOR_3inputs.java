package test_redes;


import edu.uaz.jmmc.mlp.FuncionSigmoidea;
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
public class TesterXOR_3inputs {

    public static void main(String[] args) {

        byte[][] entrada = new byte[][]{
            new byte[]{0, 0, 0},
            new byte[]{0, 1, 0},
            new byte[]{1, 0, 0},
            new byte[]{1, 1, 1}
        };

        double[][] salida = new double[][]{
            new double[]{0.0},
            new double[]{1.0},
            new double[]{1.0},
            new double[]{0.0}
        };
        //crear y entrenear la red
        RedNeuronal red = new RedNeuronal("Red_XOR_3E");
        red.crearRed(3, new int[]{6}, 1, new FuncionSigmoidea());
//        red.entrenarRed(entrada, salida, 0.1, 5000);
        red.entrenarRed(entrada, salida, 0.5, 0.001);

        //probar la red
        for (int h = 0; h < salida.length; h++) {
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
//        Frame f = new Frame();
//        f.setVisible(true);
//        FileDialog dialogo = new FileDialog(f,
//                "Save?",
//                FileDialog.SAVE);
//        dialogo.setFile(red.getNombre() + ".ser"); //Filtro de archivos
//        dialogo.setDirectory("."); //Directorio actual
//        dialogo.setVisible(true); //Muestra el cuadro de dialogo
//        String filename = dialogo.getDirectory();//Obtenemos el directorio
//        filename += dialogo.getFile();//Obtenemos es nombre del archivo seleccionado
//        if (dialogo.getDirectory() != null && dialogo.getFile() != null) {
//            RedSerializer ser = new RedSerializer();
//            ser.serializeRed(red, filename);
//        }
//        System.exit(0);
        //TODO probando

        //FIXME
        //@todo
        //@TODO
        /* @TODO */
    }
}
