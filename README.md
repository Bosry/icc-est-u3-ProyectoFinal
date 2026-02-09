# Proyecto Final – Simulador de Rutas con Grafos (BFS y DFS)

## Descripción del Proyecto
Este proyecto corresponde al trabajo final de la unidad y consiste en el desarrollo de una aplicación de escritorio en **Java**, utilizando **Swing** para la interfaz gráfica.  
La aplicación simula un mapa de calles mediante el uso de la estructura de datos **Grafo**, permitiendo representar intersecciones como nodos y calles como conexiones entre ellos.

El sistema permite al usuario crear nodos de manera interactiva, conectarlos y obtener rutas entre dos puntos utilizando los algoritmos **BFS** y **DFS**, mostrando visualmente el recorrido sobre el mapa.

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
- Un identificador único
- Coordenadas en el mapa (x, y)
- Una lista de nodos vecinos que representan las conexiones

### Grafo
La clase `Grafo` administra el conjunto de nodos y sus conexiones.
Permite:
- Agregar y eliminar nodos
- Crear conexiones bidireccionales
- Obtener todos los nodos registrados
- Guardar y cargar el grafo desde un archivo de texto

---

## Algoritmos Implementados

### BFS (Breadth-First Search)
El algoritmo BFS recorre el grafo por niveles, explorando primero los nodos más cercanos al punto de inicio.  
Este algoritmo es útil para encontrar rutas más cortas en términos de número de nodos.

### DFS (Depth-First Search)
El algoritmo DFS explora el grafo en profundidad, avanzando por un camino hasta que no existan más nodos disponibles antes de retroceder.  
No garantiza la ruta más corta, pero permite recorrer completamente el grafo.

Los tiempos de ejecución de ambos algoritmos se registran para su comparación.

---

## Interfaz Gráfica
La interfaz fue desarrollada con **Java Swing** y permite:
- Visualizar el mapa base
- Crear nodos mediante clics
- Conectar nodos entre sí
- Ejecutar BFS y DFS mediante botones
- Mostrar gráficamente la ruta encontrada
- Limpiar la ruta visualizada

---

## Persistencia de Datos
La información del grafo se guarda en un archivo de texto (`grafo.txt`).
Cuando la aplicación se inicia, los nodos y conexiones se cargan automáticamente desde dicho archivo, permitiendo mantener la información entre ejecuciones.

---

## Resultados y Conclusiones
- El uso de grafos facilita la representación de mapas y rutas.
- BFS es más adecuado para encontrar rutas mínimas.
- DFS permite un recorrido más profundo del grafo.
- Los tiempos de ejecución pueden variar debido al orden de exploración y al estado del sistema.
- La aplicación refuerza el uso práctico de estructuras de datos vistas en clase.

---

## Herramientas Utilizadas
- Java
- Java Swing
- Estructuras de Datos (List, Map, Queue, Stack)
- Git y GitHub

---

## Autor
**Bryan Santos**  
Computacion  
Universidad Politécnica Salesiana
