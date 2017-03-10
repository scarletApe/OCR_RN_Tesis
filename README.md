# OCR_RN_Tesis
Mi trabajo de tesis de OCR con Redes Neuronales

es un proyecto de eclipse, utiliza javafx.


Instrucciones para crear, entrenar y probar una red neuronal para dígitos manuscritos:

-Dar click en el botón con la cruz verde para crear una red.

-Dar el nombre de la red, el numero de neuronas de entrada son 35, en Ocultas poner
 un numero entre 12 y 35, en neuronas de salida poner 10.

-Hacer click en el botón de Crear Red

-Ir a la pestaña de Entrenamiento con Imágenes.

-Elegir el directorio que contiene las muestras de los dígitos para el entrenamiento (folder “train filtered”),
 en Taza de Aprendizaje poner 1.0, hacer check al check box de Limitar Iteraciones,
 poner algo como 5,000. 

-Dar click en Entrenar y esperar que acabe.

-Una vez que termine (no se tarda tanto), se puede ir a la pestaña de Grafo de Entrenamiento para ver como fue disminuyendo el error.

-Se puede ir a la pestaña de Descodificación de Imágenes para calcular la precisión de la red entrenada.

-Se elite el directorio (la carpeta “train filtered”) y se le da click en Decodificar, al
 final de el texto se muestra el porcentaje de precisión. 

-Se puede ir a la pestaña Probar con Dibujo, (la cual se muestra del drop down menu View),
 y aquí se dibuja un dígito en la area en blanco y dar click en Reconocer.