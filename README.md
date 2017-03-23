# OCR_RN_Tesis

Mi trabajo de tesis de OCR con Redes Neuronales, en este proyecto se puede crear una red neuronal
y entrenarla para reconocer dígitos manuscritos.

Este es un proyecto de eclipse, utiliza javafx.


## Instrucciones para crear, entrenar y probar una red neuronal para dígitos manuscritos:

* Dar click en el botón con la cruz verde para crear una red.

* Dar el nombre de la red, el numero de neuronas de entrada son 35, en Ocultas poner un numero entre 12 y 35, en neuronas de salida poner 10.

![Ventana crear red neuronal](/images/crear_red.png)

* Hacer click en el botón de Crear Red

![Ventana con gráfo de la red neuronal](/images/red.png)

* Ir a la pestaña de Entrenamiento con Imágenes.

* Elegir el directorio que contiene las muestras de los dígitos para el entrenamiento (folder “train filtered”), en Taza de Aprendizaje poner 1.0, hacer check al check box de Limitar Iteraciones, poner algo como 5,000. 

* Dar click en Entrenar y esperar que acabe.

![Pestaña donde se entrena la red neuronal](/images/entrenar.png)

* Una vez que termine (no se tarda tanto), se puede ir a la pestaña de Grafo de Entrenamiento para ver como fue disminuyendo el error.

![Grafo resultante del entrenamiento](/images/grafo.png)

* Se va a la pestaña de Descodificación de Imágenes para calcular la precisión de la red entrenada.

* Se elige el directorio (la carpeta “train filtered”) y se le da click en Decodificar, al final de el texto se muestra el porcentaje de precisión. 

![Calcular la taza de precision de reconocimiento](/images/prueba.png)

* Se va a la pestaña Probar con Dibujo, (la cual se muestra del drop down menu View), y aquí se dibuja un dígito en la area en blanco y dar click en Reconocer.

![Reconocer un dígito hecho por el usuario](/images/dibujo.png)

### Note:
* El sistema cuenta con la funcionalidad de guardar y cargar una red neuronal, entrenada o no.
* El sistema cuenta con dos pestañas adicionales para entrenar la red neuronal con datos binarios, estos datos binarios también se pueden guardar y cargar de una archivo.
* Se pueden ver los valores de los pesos sinapticos y el bias.
* Existen otras dos pestañas para procesar las imagenes en bruto con los dígitos.
* La red que se crea puede tener cualquier topologia, de cuantas capas deseé.