# Proyecto Final – Simulador de Rutas con Grafos (BFS y DFS)

## Descripción del Proyecto
Este proyecto corresponde al trabajo final de la unidad y consiste en el desarrollo de una aplicación de escritorio en **Java**, utilizando **Swing** para la interfaz gráfica.  
La aplicación simula un mapa de calles mediante el uso de la estructura de datos **Grafo**, permitiendo representar intersecciones como nodos y calles como conexiones entre ellos.

El sistema permite al usuario crear nodos de manera interactiva, conectarlos y obtener rutas entre dos puntos utilizando los algoritmos **BFS (Breadth-First Search)** y **DFS (Depth-First Search)**, mostrando visualmente el recorrido sobre el mapa.

---

## Objetivos

### Objetivo General
Desarrollar una aplicación gráfica que permita simular rutas en un mapa mediante grafos y aplicar los algoritmos BFS y DFS.

### Objetivos Específicos
- Representar nodos y conexiones en un entorno gráfico.
- Permitir la creación y eliminación de nodos y aristas.
- Aplicar los algoritmos BFS y DFS para la búsqueda de rutas.
- Comparar el comportamiento y tiempo de ejecución de ambos algoritmos.
- Guardar y cargar la información del grafo desde un archivo.

---

## Estructura del Proyecto

- `icc-est-u3-ProyectoFinal/`
  - `src/`
    - `controllers/`
      - `BFS.java`
      - `DFS.java`
    - `models/`
      - `Grafo.java`
      - `Nodo.java`
    - `views/`
      - `MapaPanel.java`
    - `App.java`
  - `assets/`
    - `mapa.png`
    - `grafo.txt`
  - `.gitignore`
  - `README.md`

---

## Modelo del Sistema

### Nodo
La clase `Nodo` representa una intersección del mapa.  
Cada nodo contiene:
- Un identificador único.
- Coordenadas en el mapa (x, y).
- Una lista de nodos vecinos que representan las conexiones.

### Grafo
La clase `Grafo` administra el conjunto de nodos y sus conexiones.  
Permite agregar y eliminar nodos, crear conexiones bidireccionales y guardar o cargar la información del grafo desde un archivo de texto.

---

## Algoritmos Implementados

### BFS (Breadth-First Search)
El algoritmo BFS recorre el grafo por niveles, explorando primero los nodos más cercanos al punto de inicio.  
Este algoritmo es adecuado para encontrar rutas con el menor número de nodos.

### DFS (Depth-First Search)
El algoritmo DFS explora el grafo en profundidad, avanzando por un camino hasta que no existen más nodos disponibles antes de retroceder.  
Este algoritmo no garantiza la ruta más corta, pero permite recorrer completamente el grafo.

---

## Interfaz Gráfica
La interfaz fue desarrollada con **Java Swing** y permite:
- Visualizar el mapa base.
- Crear nodos mediante clics sobre el mapa.
- Conectar nodos ingresando sus identificadores.
- Ejecutar BFS y DFS mediante botones.
- Mostrar gráficamente la ruta encontrada.
- Limpiar la ruta visualizada.

---

## Uso de la Aplicación

1. Al iniciar la aplicación, se carga el mapa y los nodos guardados previamente.
2. Para crear un nodo, se debe hacer clic izquierdo sobre el mapa.
3. Para conectar dos nodos, se utiliza el clic derecho y se ingresan los IDs correspondientes.
4. Para buscar una ruta, se ingresa el nodo de inicio y destino y se presiona el botón BFS o DFS.
5. La ruta encontrada se muestra gráficamente sobre el mapa.
6. El botón limpiar permite borrar la ruta visualizada.

---

## Persistencia de Datos

La información del grafo se guarda en el archivo `grafo.txt`.  
Este archivo permite que los nodos y conexiones creados se mantengan al cerrar y volver a abrir la aplicación.

### Formato del Archivo `grafo.txt`
- Las líneas que comienzan con `N` representan nodos y contienen su ID y coordenadas.
- Las líneas que comienzan con `A` representan conexiones entre nodos.

---

## Variación de los Tiempos de Ejecución

Aunque se ejecute el mismo algoritmo sobre la misma ruta, los tiempos de ejecución pueden variar.  
Esto se debe a factores como la gestión de memoria, el orden de exploración de los nodos y la carga del sistema en el momento de la ejecución.

---

## Limitaciones del Proyecto

- Los algoritmos BFS y DFS no consideran pesos en las conexiones.
- DFS no garantiza encontrar la ruta más corta.
- El mapa utilizado es una imagen estática.
- El sistema no cuenta con validaciones avanzadas de errores.

---

## Organización del Código

El proyecto se encuentra organizado de la siguiente manera:
- `models`: contiene la lógica del sistema y las estructuras de datos.
- `views`: maneja la interfaz gráfica y la visualización.
- `controllers`: contiene la implementación de los algoritmos BFS y DFS.

---

## Video de Funcionamiento (Pendiente)

> **Nota:**  
> En esta sección se añadirá un video explicativo donde se mostrará el funcionamiento de la aplicación, incluyendo:
> - Creación de nodos  
> - Conexión entre nodos  
> - Ejecución de BFS y DFS  
> - Visualización de rutas  
> - Limpieza del mapa  

**Enlace al video:** *(por agregar)*

---

## Herramientas Utilizadas
- Java
- Java Swing
- Estructuras de Datos (List, Map, Queue, Stack)
- Git y GitHub

---

##  Autor
**Bryan Santos**  
Computacion  
Universidad Politécnica Salesiana