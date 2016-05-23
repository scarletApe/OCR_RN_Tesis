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
package edu.uaz.jmmc.gui;

import edu.uaz.jmmc.filtros_imagen.DownSampler;
import edu.uaz.jmmc.filtros_imagen.PbmManager;
import edu.uaz.jmmc.filtros_imagen.Muestra;
import edu.uaz.jmmc.filtros_imagen.Segmentador;
import edu.uaz.jmmc.mlp.Capa;
import edu.uaz.jmmc.mlp.FuncionSigmoidea;
import edu.uaz.jmmc.mlp.RedNeuronal;
import edu.uaz.jmmc.util.RNPainter;
import edu.uaz.jmmc.util.RNSerializer;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.vecmath.Point2d;

/**
 * FXML Controller class
 *
 * @author juanmartinez
 */
public class VentanaMainController implements Initializable {

    @FXML
    private CheckMenuItem cmiTrainImg;
    @FXML
    private CheckMenuItem cmiTestImg;
    @FXML
    private CheckMenuItem cmiTestDibujo;
    @FXML
    private CheckMenuItem cmiSegmentar;
    @FXML
    private CheckMenuItem cmiFiltro;
    @FXML
    private CheckMenuItem cmiTrainBinario;
    @FXML
    private CheckMenuItem cmiTestBinario;
    @FXML
    private CheckMenuItem cmiGrafo;
    @FXML
    private CheckMenuItem cmiPesos;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnAbrir;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNEntrada;
    @FXML
    private TextField tfNOcultas;
    @FXML
    private TextField tfNSalida;
    @FXML
    private ToggleButton tbEntranada;
    @FXML
    private Tab tabRedNeuronal;
    @FXML
    private AnchorPane apRedNeuronal;
    @FXML
    private ImageView ivRedNeuronal;
    @FXML
    private Tab tabEntrenamientoImagen;
    @FXML
    private TextArea taEntImgs;
    @FXML
    private CheckBox cbLimitIter;
    @FXML
    private TextField tfEITaza;
    @FXML
    private TextField tfEIErrorMin;
    @FXML
    private ProgressIndicator piEI;
    @FXML
    private TextField tfEIIteraciones;
    @FXML
    private Tab tabPruebasImagen;
    @FXML
    private Tab tabPruebaDibujo;
    @FXML
    private Tab tabSegmentar;
    @FXML
    private TilePane tpSegment;
    @FXML
    private TextField tfNumRenglones;
    @FXML
    private TextField tfNumColumnas;
    @FXML
    private TextField tfCharacteresRenglon;
    @FXML
    private Tab tabFiltrar;
    @FXML
    private Label lbDirDestino;
    @FXML
    private Label lbDirFuente;
    @FXML
    private Tab tabEntrenamientoBinario;
    @FXML
    private Button btnDGuardar;
    @FXML
    private Button btnDCargar;
    @FXML
    private Button btnDQuitar;
    @FXML
    private Button btnAnadirRenglon;
    @FXML
    private SwingNode snEntrenamiento;
    @FXML
    private TextField tfErrorMaximo;
    @FXML
    private TextField tfTazaAprendizaje;
    @FXML
    private Button btnEntrenar;
    @FXML
    private CheckBox cbLimitarIteraciones;
    @FXML
    private TextField tfIteraciones;
    @FXML
    private ProgressIndicator piProgreso;
    @FXML
    private Button btnDetener;
    @FXML
    private Tab tabPruebasBinario;
    @FXML
    private Button btnCopiarDatos;
    @FXML
    private Button btnProbar;
    @FXML
    private SwingNode snPruebas;
    @FXML
    private Tab tabPesos;
    @FXML
    private TextArea taReportePesos;
    @FXML
    private Button btnActualizarPesos;
    @FXML
    private Tab tabResultados;
    @FXML
    private BorderPane paneResultados;
    @FXML
    private TabPane tpMain;
    @FXML
    private TilePane tpFiltrar;
    @FXML
    private TextArea taDescodificacion;

    private RedNeuronal red;
    private int entradas;
    private int ocultas;
    private int salidas;
    private javafx.scene.image.Image img;
    private EntrenarService service;

    private SwingTable tablaEntrenamiento;
    private SwingTable tablaPruebas;

    private ArrayList<BufferedImage> img_to_segment;
    private Map<String, BufferedImage> imgs_to_filter;
    private ArrayList<File> list_files;
    private ArrayList<File> list_files_des;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        img = new javafx.scene.image.Image("/edu/uaz/jmmc/imagenes/vacio.png");

        handleLimpiar(null);
        btnDetener.setDisable(true);
        btnDetener.setVisible(false);

        btnNuevo.setTooltip(new Tooltip("Crear Nueva Red Neuronal"));
        btnAbrir.setTooltip(new Tooltip("Abrir un Archivo de Red Neuronal"));
        btnGuardar.setTooltip(new Tooltip("Guardar Red Neuronal en Archivo"));
        btnLimpiar.setTooltip(new Tooltip("Limpiar Todo"));

        tpMain.getTabs().remove(tabEntrenamientoBinario);
        tpMain.getTabs().remove(tabPruebasBinario);
        tpMain.getTabs().remove(tabPesos);
        tpMain.getTabs().remove(tabPruebaDibujo);

//        cmiTestBinario.setSelected(true);
//        cmiTrainBinario.setSelected(true);
//        cmiPesos.setSelected(true);
//        cmiTestDibujo.setSelected(true);
        cmiTrainImg.setSelected(true);
        cmiTestImg.setSelected(true);
        cmiSegmentar.setSelected(true);
        cmiFiltro.setSelected(true);
        cmiGrafo.setSelected(true);

        tpSegment.setPadding(new Insets(15, 15, 15, 15));
        tpSegment.setHgap(15);
        /**
         * @todo
         */
        tpSegment.getChildren().clear();

        tpFiltrar.setPadding(new Insets(15, 15, 15, 15));
        tpFiltrar.setHgap(15);

        img_to_segment = new ArrayList<>();
        imgs_to_filter = new TreeMap<>();
        list_files = new ArrayList<>();
    }

    public void initData(Stage stage) {
        this.stage = stage;

    }

    @FXML
    private void handleNueva(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/uaz/jmmc/gui/CrearRedVenta.fxml"));

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Especifique la red");
            stage.setScene(new Scene((Parent) loader.load()));

            CrearRedVentaController controller = loader.<CrearRedVentaController>getController();

            stage.show();
            controller.initData(this);
        } catch (Exception e) {

        }
    }

    @FXML
    private void handleAbrir(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");

        // Set extension filter
        List<String> l = new ArrayList();
        l.add("*.ser");
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("Java Object files", l);
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                RNSerializer ser = new RNSerializer();
                RedNeuronal net = ser.deserializeRed(file.getCanonicalPath());

                if (net != null) {
                    Capa[] capasOcultas = net.getCapasOcultas();
                    int[] cs = new int[capasOcultas.length];
                    for (int i = 0; i < capasOcultas.length; i++) {
                        cs[i] = capasOcultas[i].getNeuronas().length;

                    }

                    inicializarRed(net.getNombre(),
                            net.getCapaEntrada().getNeuronas().length,
                            cs,
                            net.getCapaSalida().getNeuronas().length);
                    this.red = net;
                    handleActualizarPesos(event);
                }
            } catch (IOException | java.lang.IllegalArgumentException e) {
                System.out.println("Error en cargar imagen " + e);
            }

        }
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        if (red == null) {
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Red Neuronal");
        fileChooser.setInitialFileName(red.getNombre() + ".ser");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                RNSerializer ser = new RNSerializer();
                ser.serializeRed(red, file.toString());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void handleSalir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleLimpiar(ActionEvent event) {
        //limpiar los campos
        tfNombre.setText("");
        tfNEntrada.setText("");
        tfNOcultas.setText("");
        tfNSalida.setText("");

        tbEntranada.setSelected(false);
        tbEntranada.setText("No Entrenada");

        taReportePesos.setText("");

        ivRedNeuronal.setImage(img);

        paneResultados.setCenter(new ImageView(img));
        this.red = null;

        piProgreso.progressProperty().unbind();
        piProgreso.setProgress(0);
    }

    @FXML
    private void handleTabTrainImagen(ActionEvent event) {
        manejarTabs(cmiTrainImg, tabEntrenamientoImagen);
    }

    @FXML
    private void handleTabPruebaImagen(ActionEvent event) {
        manejarTabs(cmiTestImg, tabPruebasImagen);
    }

    @FXML
    private void handleTabDibujo(ActionEvent event) {
        manejarTabs(cmiTestDibujo, tabPruebaDibujo);
    }

    @FXML
    private void handleTabSegmentar(ActionEvent event) {
        manejarTabs(cmiSegmentar, tabSegmentar);
    }

    @FXML
    private void handleTabFiltro(ActionEvent event) {
        manejarTabs(cmiFiltro, tabFiltrar);
    }

    @FXML
    private void handleTabTrainBinario(ActionEvent event) {
        manejarTabs(cmiTrainBinario, tabEntrenamientoBinario);
    }

    @FXML
    private void handleTabPruebaBinario(ActionEvent event) {
        manejarTabs(cmiTestBinario, tabPruebasBinario);
    }

    @FXML
    private void handleTabGrafo(ActionEvent event) {
        manejarTabs(cmiGrafo, tabResultados);
    }

    @FXML
    private void handleTabPesos(ActionEvent event) {
        manejarTabs(cmiPesos, tabPesos);
    }

    @FXML
    private void handleElegirDirEntrenamientoImg(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            System.out.println("No Directory selected");
            return;
        }
        String directorio = selectedDirectory.getAbsolutePath();

        File folder = new File(directorio);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            String name = file.getName();
            if (name.endsWith(".pbm")) {
                list_files.add(file);
                taEntImgs.appendText(name + "\n");
            }
        }
    }

    @FXML
    private void handleEIDetener(ActionEvent event) {
    }

    @FXML
    private void handleEntrenarImagenes(ActionEvent event) {
        if (red == null) {
            return;
        }
        double ta = Double.parseDouble(tfEITaza.getText());
        double et = Double.parseDouble(tfEIErrorMin.getText());
        boolean selected = cbLimitIter.isSelected();
        int iteraciones = (selected) ? Integer.parseInt(tfEIIteraciones.getText()) : 0;

        EntrenarImagenesService service = new EntrenarImagenesService();
        service.init(ta, et, list_files, red, selected, iteraciones);
        service.setOnSucceeded((WorkerStateEvent t) -> {
            System.out.println("Termino el Entrenamiento con Imagenes: " + t.getSource().getValue());
            tbEntranada.setSelected(true);
            tbEntranada.setText("Entrenada");
            LineChart<Number, Number> lineChartError = lineChartError(red.getErrores());
            paneResultados.setCenter(lineChartError);
//            btnDetener.setDisable(true);
//            btnDetener.setVisible(false);
            //IMPRIMIR LOS ERRORES A LA CONSOLA
            System.out.println("");
            ArrayList<Point2d> errores = red.getErrores();
            for (int i = 0; i < errores.size(); i++) {
                Point2d p = errores.get(i);
                System.out.println(p.x+"\t"+p.y);
            }
            System.out.println("");
        });
        service.start();

        piEI.progressProperty().bind(service.progressProperty());
//        btnDetener.setDisable(false);
//        btnDetener.setVisible(true);
    }

    @FXML
    private void handleElegirImagenSegmentador(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");

        // Set extension filter
        List<String> l = new ArrayList();
        l.add("*.png");
        l.add("*.jpg");
        l.add("*.gif");
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("Image files (png, jpg, gif)", l);
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
        if (file != null) {
            try {

                ImageView createdImageView = createImageView(file.getAbsoluteFile());
                tpSegment.getChildren().addAll(createdImageView);

                BufferedImage bi = ImageIO.read(file.getAbsoluteFile());
                img_to_segment.add(bi);

            } catch (java.lang.IllegalArgumentException e) {
                System.out.println("Error en cargar imagen " + e);
            } catch (IOException ex) {
                Logger.getLogger(VentanaMainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void handleSegmentar(ActionEvent event) {
        String num_reng = tfNumRenglones.getText();
        String num_cols = tfNumColumnas.getText();
        String chars = tfCharacteresRenglon.getText();

        if (num_cols.isEmpty() || num_reng.isEmpty() || chars.isEmpty()) {
            return;
        }

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory
                = directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            return;
        }
        try {
            int nr = Integer.parseInt(num_reng);
            int nc = Integer.parseInt(num_cols);

            Image[] arr = new Image[img_to_segment.size()];
            arr = img_to_segment.toArray(arr);
            Segmentador.split(arr, nr, nc, chars, selectedDirectory.getAbsolutePath() + "/");
        } catch (NumberFormatException | IOException e) {
            System.out.println("error " + e);
        }
    }

    @FXML
    private void handleSelectDirDestino(ActionEvent event) {
    }

    @FXML
    private void handleSelectDirFuente(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory
                = directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            System.out.println("No Directory selected");
            return;
        }
        String directorio = selectedDirectory.getAbsolutePath();

        File folder = new File(directorio);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            try {
                String name = file.getName();
                if (name.endsWith(".png")) {
                    ImageView imageView;
                    imageView = createImageView(file);
                    tpFiltrar.getChildren().addAll(imageView);

                    BufferedImage bi = ImageIO.read(file.getAbsoluteFile());
                    imgs_to_filter.put(name, bi);
                }
            } catch (IOException ex) {
                System.out.println("Error " + ex);
            }
        }
    }

    @FXML
    private void handleProcesarImagenes(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory
                = directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            System.out.println("No Directory selected");
            return;
        }
        String directorio = selectedDirectory.getAbsolutePath();
        System.out.println(directorio);

        PbmManager p = new PbmManager();
        DownSampler ds = new DownSampler();

        Set<String> keySet = imgs_to_filter.keySet();
        Muestra muestra;
        for (String key : keySet) {
            muestra = ds.downSample(imgs_to_filter.get(key));
//            System.out.println(key);
            String name = removeFileSuffix(key);
            p.guardarArregloEnImagen(directorio + "/" + name + ".pbm", muestra.getGrid());
        }

    }

    @FXML
    private void handleGuardarDatos(ActionEvent event) {
        if (red == null) {
            return;
        }
        SwingUtilities.invokeLater(() -> {

            Object[][] tableData = getTableData(tablaEntrenamiento.getTabla());
            RNSerializer s = new RNSerializer();

            Frame f = new Frame();
            f.setVisible(true);
            FileDialog dialogo = new FileDialog(f,
                    "Save?",
                    FileDialog.SAVE);
            dialogo.setFile("datos_entrenamiento" + ".ser"); //Filtro de archivos
            dialogo.setDirectory("."); //Directorio actual
            dialogo.setVisible(true); //Muestra el cuadro de dialogo
            String filename = dialogo.getDirectory();//Obtenemos el directorio
            filename += dialogo.getFile();//Obtenemos es nombre del archivo seleccionado
            if (dialogo.getDirectory() != null && dialogo.getFile() != null) {

                s.serializeObject(tableData, filename);
            }
            f.setVisible(false);
            f.dispose();

        });
    }

    @FXML
    private void handleCargarDatos(ActionEvent event) {
        if (red == null) {
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");

        // Set extension filter
        List<String> l = new ArrayList();
        l.add("*.ser");
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("Java Object files", l);
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
        if (file != null) {
            try {
                RNSerializer ser = new RNSerializer();
                Object[][] datos = (Object[][]) ser.deserializeObject(file.getCanonicalPath());

                SwingUtilities.invokeLater(() -> {
                    //crear los headers de las columnas
                    String columnas[] = new String[entradas + salidas];
                    for (int i = 0; i < entradas; i++) {
                        columnas[i] = "In " + (i + 1);
                    }
                    int k = 1;
                    for (int i = entradas; i < columnas.length; i++) {
                        columnas[i] = "Out " + (k);
                        k++;
                    }

                    DefaultTableModel dtm = new DefaultTableModel(datos, columnas);
                    tablaEntrenamiento.getTabla().setModel(dtm);
                });

            } catch (IOException | java.lang.IllegalArgumentException e) {
                System.out.println("Error en cargar imagen " + e);
            }

        }
    }

    @FXML
    private void handleQuitarRenglon(ActionEvent event) {
        if (red == null) {
            return;
        }
        SwingUtilities.invokeLater(() -> {

            int selRow = tablaEntrenamiento.getTabla().getSelectedRow();
            if (selRow != -1) {
                DefaultTableModel model = (DefaultTableModel) tablaEntrenamiento.getTabla().getModel();
                model.removeRow(selRow);
            }
        });
    }

    @FXML
    private void hangleAnadirRenglon(ActionEvent event) {
        if (red == null) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) tablaEntrenamiento.getTabla().getModel();
            String row[] = new String[entradas + salidas];
            for (int i = 0; i < row.length; i++) {
                row[i] = "0";
            }
            model.addRow(row);

        });
    }

    @FXML
    private void handleEntrenar(ActionEvent event) {
        //verificar si hay red
        if (red == null) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            //obtener los datos de entrada de la tabla
            JTable jt = tablaEntrenamiento.getTabla();
            Object[][] tableData = getTableData(jt);
            byte entrad[][] = new byte[tableData.length][];
            for (int i = 0; i < tableData.length; i++) {
                entrad[i] = new byte[entradas];
                for (int j = 0; j < entradas; j++) {
                    entrad[i][j] = Byte.parseByte((String) tableData[i][j]);
                }
            }

            //obtener los datos de las salidas esperadas de la tabla
            double salid[][] = new double[tableData.length][];
            for (int i = 0; i < tableData.length; i++) {
                salid[i] = new double[salidas];
                int k = 0;
                for (int j = entradas; j < entradas + salidas; j++) {
                    salid[i][k] = Double.parseDouble((String) tableData[i][j]);
                    k++;
                }
            }
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            System.out.println("Los datos de entrada");
            for (byte[] entrad1 : entrad) {
                for (int j = 0; j < entrad1.length; j++) {
                    System.out.print(entrad1[j]);
                }
                System.out.println("");
            }

            System.out.println("Los datos de salida");
            for (double[] salid1 : salid) {
                for (int j = 0; j < salid1.length; j++) {
                    System.out.print(salid1[j] + " ");
                }
                System.out.println("");
            }
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            //entrenar la red
            Platform.runLater(() -> {
                entrenarRed(entrad, salid);
            });

        });
    }

    @FXML
    private void handleLimitarIteraciones(ActionEvent event) {
        boolean disabled = tfIteraciones.isDisabled();
        tfIteraciones.setDisable(!disabled);
    }

    @FXML
    private void handleDetener(ActionEvent event) {
        /**
         * @todo
         */
        System.exit(0);

        boolean canceled = service.cancel();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.err.println("was canceled task = " + canceled);
        if (canceled) {
            btnDetener.setDisable(true);
            btnDetener.setVisible(false);
        }
    }

    @FXML
    private void handleCopiarDatos(ActionEvent event) {
        if (red == null) {
            return;
        }
        SwingUtilities.invokeLater(() -> {

            Object[][] tableData = getTableData(tablaEntrenamiento.getTabla());
            //crear los headers de las columnas
            String columnas[] = new String[entradas + salidas];
            for (int i = 0; i < entradas; i++) {
                columnas[i] = "In " + (i + 1);
            }
            int k = 1;
            for (int i = entradas; i < columnas.length; i++) {
                columnas[i] = "Out " + (k);
                k++;
            }
            tablaPruebas.getTabla().setModel(new DefaultTableModel(tableData, columnas));

        });
    }

    @FXML
    private void handleProbarDatos(ActionEvent event) {
        //verificar si hay una red
        if (red == null) {
            return;
        }
//        if (!tbEntranada.isSelected()) {
//            return;
//        }

        SwingUtilities.invokeLater(() -> {

            //primero obtengo los datos de la tabla 
            Object[][] datos = getTableData(tablaPruebas.getTabla());

            //despues hago un arreglo nuevo con solo los datos de entrada y
            //creo otro arreglo para los datos de salida
            byte entrad[][] = new byte[datos.length][];
            for (int i = 0; i < datos.length; i++) {
                entrad[i] = new byte[entradas];
                for (int j = 0; j < entradas; j++) {
                    entrad[i][j] = Byte.parseByte((String) datos[i][j]);
                }
            }

            //crear un arreglo para las  salidas de la red neuronal
            double salid[][] = new double[datos.length][salidas];

            Platform.runLater(() -> {
                //le doy los datos de entrada a la red neuronal y voy guardando las salidas
                for (int i = 0; i < entrad.length; i++) {
                    salid[i] = red.clasificar(entrad[i]);
                }

                //las salidas las muestro en la tabla
                SwingUtilities.invokeLater(() -> {
                    for (int i = 0; i < datos.length; i++) {
                        for (int j = entradas, m = 0; j < entradas + salidas; j++, m++) {
                            datos[i][j] = salid[i][m];
                        }
                    }

                    //crear los headers de las columnas
                    String columnas[] = new String[entradas + salidas];
                    for (int i = 0; i < entradas; i++) {
                        columnas[i] = "In " + (i + 1);
                    }
                    for (int i = entradas, k = 1; i < columnas.length; i++, k++) {
                        columnas[i] = "Out " + (k);
                    }

                    JTable tabla = tablaPruebas.getTabla();
                    tabla.setModel(new DefaultTableModel(datos, columnas));
                });
            });

        });
    }

    @FXML
    private void handleActualizarPesos(ActionEvent event) {
        if (red != null) {
            taReportePesos.setText(red.toString());
        }
    }

    @FXML
    private void handleElegirDirDescofificarImg(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            System.out.println("No Directory selected");
            return;
        }
        String directorio = selectedDirectory.getAbsolutePath();

        File folder = new File(directorio);
        File[] listOfFiles = folder.listFiles();
        list_files_des = new ArrayList<>(listOfFiles.length);

        for (final File file : listOfFiles) {
            String name = file.getName();
            if (name.endsWith(".pbm")) {
                list_files_des.add(file);
                taDescodificacion.appendText(name + "\n");
            }
        }
    }

    //handleDescodificarImagenes
    @FXML
    private void handleDescodificarImagenes(ActionEvent event) {
        //taDescodificacion
        if (red == null) {
            return;
        }

        DescodificarImagenesService ser = new DescodificarImagenesService();
        ser.init(list_files_des, red);
        ser.setOnSucceeded((WorkerStateEvent t) -> {
            System.out.println("Termino la Descodificacion con Imagenes");// " + t.getSource().getValue());
            taDescodificacion.setText((String) t.getSource().getValue());
        });
        ser.start();

    }

    @FXML
    private void handleLimitarIteracionesImgs(ActionEvent event) {
        boolean disabled = tfEIIteraciones.isDisabled();
        tfEIIteraciones.setDisable(!disabled);
    }

    protected void inicializarRed(String nombre, int entradas, int[] ocultas, int salida) {
        //limpiar todo
        handleLimpiar(null);

        red = new RedNeuronal(nombre);
        this.entradas = entradas;
//        this.ocultas = ocultas;
        this.salidas = salida;
        red.crearRed(entradas, ocultas, salida, new FuncionSigmoidea());

        //llenar los campos
        tfNombre.setText(nombre);
        tfNEntrada.setText(entradas + "");
        tfNOcultas.setText(arrayToString(ocultas));
        tfNSalida.setText(salida + "");
        handleActualizarPesos(null);

        //poner la imagen de la red 
        double width = apRedNeuronal.getWidth();
        double height = apRedNeuronal.getHeight();
        Image paintRN = new RNPainter().paintRN(red, (int) width, (int) height);
        ivRedNeuronal.setImage(SwingFXUtils.toFXImage((BufferedImage) paintRN, null));

        //crear las columnas en las tablas
        //crear las tablas
        //crear el arreglo de datos de contendios de la tabla
        String datos[][] = new String[4][entradas + salida];

        //inicializar todos los datos con cero
        for (String[] dato : datos) {
            for (int j = 0; j < dato.length; j++) {
                dato[j] = "0";
            }
        }

        //crear los headers de las columnas
        String columnas[] = new String[entradas + salida];
        for (int i = 0; i < entradas; i++) {
            columnas[i] = "In " + (i + 1);
        }
        int k = 1;
        for (int i = entradas; i < columnas.length; i++) {
            columnas[i] = "Out " + (k);
            k++;
        }

        crearTablas(datos, columnas);

    }

    protected LineChart<Number, Number> lineChartError(ArrayList<Point2d> ps) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Epoca");
        yAxis.setLabel("Error Promedio");
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Error Promedio de la Red Neuronal " + red.getNombre());

        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);

        XYChart.Series series = new XYChart.Series();
        series.setName("Resultados de Red " + red.getNombre());
        for (Point2d p : ps) {
            series.getData().add(new XYChart.Data(p.x, p.y));
        }
        lineChart.getData().add(series);

        return (lineChart);
    }

    public String arrayToString(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]).append(" ");

        }
        return sb.toString();
    }

    private void entrenarRed(byte entrada[][], double salida[][]) {
        double ta = Double.parseDouble(tfTazaAprendizaje.getText());
        double et = Double.parseDouble(tfErrorMaximo.getText());
        boolean selected = cbLimitarIteraciones.isSelected();
        int iteraciones = (selected) ? Integer.parseInt(tfIteraciones.getText()) : 0;

        service = new EntrenarService();
        service.init(ta, et, salida, entrada, red, selected, iteraciones);
        service.setOnSucceeded((WorkerStateEvent t) -> {
            System.out.println("done:" + t.getSource().getValue());
            tbEntranada.setSelected(true);
            tbEntranada.setText("Entrenada");
            LineChart<Number, Number> lineChartError = lineChartError(red.getErrores());
            paneResultados.setCenter(lineChartError);
            btnDetener.setDisable(true);
            btnDetener.setVisible(false);
        });
        service.start();

        piProgreso.progressProperty().bind(service.progressProperty());
        btnDetener.setDisable(false);
        btnDetener.setVisible(true);
    }

    private void crearTablas(String[][] datos, String[] columnas) {
        SwingUtilities.invokeLater(() -> {
            tablaEntrenamiento = new SwingTable(datos, columnas);
            snEntrenamiento.setContent(tablaEntrenamiento);

            tablaPruebas = new SwingTable(datos.clone(), columnas.clone());
            snPruebas.setContent(tablaPruebas);
        });
    }

    public Object[][] getTableData(JTable table) {
        TableModel dtm = table.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                tableData[i][j] = dtm.getValueAt(i, j);
            }
        }
        return tableData;
    }

    public void manejarTabs(CheckMenuItem cmi, Tab tab) {
        if (cmi.isSelected()) {
            //hide
            tpMain.getTabs().add(tab);
        } else {
            //show
            tpMain.getTabs().remove(tab);

        }
    }

    private ImageView createImageView(final File imageFile) throws IOException {
        // DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
        // The last two arguments are: preserveRatio, and use smooth (slower)
        // resizing

        ImageView imageView = null;
        try {
            final javafx.scene.image.Image image = new javafx.scene.image.Image(
                    new FileInputStream(imageFile), 150, 0, true, true);

            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        try {
                            BorderPane borderPane = new BorderPane();
                            ImageView imageView1 = new ImageView();
                            javafx.scene.image.Image image1 = new javafx.scene.image.Image(new FileInputStream(imageFile));
                            imageView1.setImage(image1);
                            imageView1.setStyle("-fx-background-color: BLACK");
                            imageView1.setFitHeight(stage.getHeight() - 10);
                            imageView1.setPreserveRatio(true);
                            imageView1.setSmooth(true);
                            imageView1.setCache(true);
                            borderPane.setCenter(imageView1);
                            borderPane.setStyle("-fx-background-color: BLACK");
                            Stage newStage = new Stage();
                            newStage.setWidth(stage.getWidth());
                            newStage.setHeight(stage.getHeight());
                            newStage.setTitle(imageFile.getName());
                            Scene scene = new Scene(borderPane, Color.BLACK);
                            newStage.setScene(scene);
                            newStage.show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }

    private String removeFileSuffix(String s) {
        int i2 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                i2 = i;
                break;
            }
        }
        return s.substring(0, i2);
    }
}

class EntrenarService extends Service<Void> {

    private double ta;
    private double et;
    private double[][] salida;
    private byte[][] entrada;
    private RedNeuronal red;
    private boolean iterar;
    private int iteraciones;

    public void init(double ta,
            double et,
            double[][] salida,
            byte[][] entrada,
            RedNeuronal red,
            boolean iterar,
            int iteraciones) {
        this.ta = ta;
        this.et = et;
        this.salida = salida;
        this.entrada = entrada;
        this.red = red;
        this.iterar = iterar;
        this.iteraciones = iteraciones;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws IOException, MalformedURLException {
                if (iterar) {
                    red.entrenarRed(entrada, salida, ta, iteraciones);
                } else {
                    red.entrenarRed(entrada, salida, ta, et);
                }
                updateProgress(5, 5);
                return null;
            }

        };
    }
}

class SwingTable extends JPanel {

    public JTable tabla;

    public SwingTable(String[][] datos, String[] cloumnas) {
        DefaultTableModel dtm = new DefaultTableModel(datos, cloumnas);
        tabla = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(tabla);

        this.add(scrollPane);
    }

    public JTable getTabla() {
        return tabla;
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

}

class EntrenarImagenesService extends Service<Void> {

    private double ta;
    private double et;
    private double[][] salida;
    private byte[][] entrada;
    private RedNeuronal red;
    private boolean iterar;
    private int iteraciones;
    private ArrayList<File> pbm_files;

    private final double[][] patrones_salidas;

    public EntrenarImagenesService() {
        this.patrones_salidas = new double[][]{
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
        };
    }

    public void init(double ta,
            double et,
            //            double[][] salida,
            //            byte[][] entrada,
            ArrayList<File> pbm_files,
            RedNeuronal red,
            boolean iterar,
            int iteraciones) {
        this.ta = ta;
        this.et = et;

        this.red = red;
        this.iterar = iterar;
        this.iteraciones = iteraciones;

        this.pbm_files = pbm_files;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws IOException, MalformedURLException {
                //primero preparar los datos de entrad y de salidas esperadas

                //inizializar los arreglos de datos de entrada y salida
                entrada = new byte[pbm_files.size()][];
                salida = new double[pbm_files.size()][];

                PbmManager pm = new PbmManager();
                try {
                    for (int i = 0; i < pbm_files.size(); i++) {
                        File file = pbm_files.get(i);

                        System.out.println("\tFile:" + file);

                        byte[][] arreglo = pm.cargarArregloDeImagen(file.toURI());
                        entrada[i] = pm.flattenArray(arreglo);

                        System.out.println("\tArreglo Tamaño:" + entrada[i].length);

                        char digito = file.getName().charAt(0);

                        System.out.println("\nDigito=" + digito);

                        int parseInt = Integer.parseInt(digito + "");
                        salida[i] = patrones_salidas[parseInt];

                        System.out.println("");
                    }
                } catch (Exception e) {
                    System.out.println("Error en Task=" + e);
                    return null;
                }
                /**
                 * @todo
                 */

                if (iterar) {
                    red.entrenarRed(entrada, salida, ta, iteraciones);
                } else {
                    red.entrenarRed(entrada, salida, ta, et);
                }
                updateProgress(5, 5);
                return null;
            }

        };
    }
}

/*
switch (digito) {
                            case '0':
                                break;
                            case '1':
                                break;
                            case '2':
                                break;
                            case '3':
                                break;
                            case '4':
                                break;
                            case '5':
                                break;
                            case '6':
                                break;
                            case '7':
                                break;
                            case '8':
                                break;
                            case '9':
                                break;
                        }
 */
class DescodificarImagenesService extends Service<String> {

    private ArrayList<File> files;
    private RedNeuronal red;

    public void init(ArrayList<File> pbm_files, RedNeuronal red) {
        this.red = red;
        this.files = pbm_files;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {

            @Override
            protected String call() throws IOException, MalformedURLException {
                PbmManager pbm = new PbmManager();
                StringBuilder sb = new StringBuilder();
                int num_correct = 0;
                for (int i = 0; i < files.size(); i++) {
                    File f = files.get(i);
                    byte[][] cargarImagenEnArreglo = pbm.cargarArregloDeImagen(f.toURI());
                    byte[] flat = pbm.flattenArray(cargarImagenEnArreglo);
                    char expected = f.getName().charAt(0);

                    double[] resultado = red.clasificar(flat);
                    byte[] binarizedArray = binarizeArray(resultado);

                    char check = check(binarizedArray);

                    boolean same = false;
                    if (expected == check) {
                        same = true;
                        num_correct++;
                    }

                    sb.append(f.getName())
                            .append("  \t")
                            .append(arrayToString(binarizedArray))
                            .append("\t")
                            .append(expected)
                            .append(" clasificado como ")
                            .append(check)
                            .append(" ")
                            .append(same);
                    sb.append("\n");
                }
                double precision = (double) num_correct / (double) files.size();
                precision = precision * 100;
                sb.append("Porcentaje de Presición ").append(precision);

                return sb.toString();
            }

        };

    }
    byte[][] patrones_salidas = new byte[][]{
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
    };

    public char check(byte[] ch) {
        char is = '?';
        for (int i = 0; i < patrones_salidas.length; i++) {
            boolean es = true;
            for (int j = 0; j < patrones_salidas[i].length; j++) {
//                if (ch[j] == patrones_salidas[i][j]) {
//                    break;
//                }
                es = es && (ch[j] == patrones_salidas[i][j]);

            }
            if (es) {
//                String s= i+"";
//                is = s.charAt(0);
                is = (char) (i + 48);
                break;
            }
        }
        return is;
    }

    public String arrayToString(byte[] d) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < d.length; i++) {
            sb.append(d[i]).append(" ");
        }
//        sb.append("")
        return sb.toString();
    }

    public byte[] binarizeArray(double[] arr) {
        byte[] bi = new byte[arr.length];

        for (int i = 0; i < bi.length; i++) {
            bi[i] = (arr[i] > 0.5) ? (byte) 1 : (byte) 0;
        }
        return bi;
    }
}
