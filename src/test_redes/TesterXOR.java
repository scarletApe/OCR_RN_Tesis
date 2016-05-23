package test_redes;


import edu.uaz.jmmc.mlp.FuncionSigmoidea;
import edu.uaz.jmmc.mlp.RedNeuronal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author juanmartinez
 */
public class TesterXOR {

    public static void main(String[] args) {

        byte[][] entrada = new byte[][]{
            new byte[]{0, 0},
            new byte[]{0, 1},
            new byte[]{1, 0},
            new byte[]{1, 1}};

        double[][] salida = new double[][]{
            new double[]{0.0},
            new double[]{1.0},
            new double[]{1.0},
            new double[]{0.0}
        };
        //crear y entrenear la red
        RedNeuronal red = new RedNeuronal();
        red.crearRed(2, new int[]{4}, 1, new FuncionSigmoidea());
//        red.entrenarRed(entrada, salida, 0.1, 5000);
        red.entrenarRed(entrada, salida, 0.99, 0.01);//usual learning rate 0.1

        //probar la red
        for (int h = 0; h < salida.length; h++) {
            byte[] prueba = entrada[h];

            System.out.print("\nProbando red XOR con:");
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
}
