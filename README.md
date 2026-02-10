# Graph Route Simulator - UPS Proyecto Final

## Introducción
Este proyecto consiste en el desarrollo de una aplicación interactiva en Java para el modelado y análisis de rutas sobre un mapa urbano. Utilizando la teoría de grafos, se representan las intersecciones como nodos y las calles como aristas. El objetivo principal es comparar el comportamiento de los algoritmos de búsqueda en anchura (**BFS**) y búsqueda en profundidad (**DFS**) en términos de eficiencia, nodos explorados y tiempo de respuesta.

---

## Controles y Funcionalidades
Para garantizar la integridad de los datos y una experiencia de usuario fluida, el sistema cuenta con los siguientes controles:

* **Modelado Dinámico (`Shift + Click`):** Creación de nodos directamente sobre la interfaz gráfica en las coordenadas del cursor.
* **Gestión de Aristas (`Click Derecho`):** Selección de dos nodos para establecer conexiones dirigidas (un solo sentido) o bidireccionales (doble sentido) con visualización de flechas.
* **Modo Bloqueo:** Permite inhabilitar intersecciones o calles específicas, simulando cortes de vía o zonas restringidas.
* **Sistema de Seguridad (Locks):** La interfaz bloquea automáticamente las funciones de edición mientras se ejecuta una búsqueda, evitando errores en el flujo de datos.

---

## Algoritmos Implementados
Se implementaron dos estrategias fundamentales de recorrido en grafos:

1.  **BFS (Breadth-First Search):** Utiliza una **Cola (FIFO)**. Garantiza encontrar la ruta con el menor número de saltos entre nodos.
2.  **DFS (Depth-First Search):** Utiliza una **Pila (LIFO)**. Explora lo más profundo posible de cada rama antes de retroceder.



### Fase de Visualización
Cumpliendo con los requerimientos, la aplicación como tal separa la ejecución en dos fases visuales:
* **Modo Exploración:** Los nodos visitados se marcan en color **amarillo** progresivamente.
* **Modo Ruta Final:** Se traza una línea **verde** resaltada sobre el camino óptimo reconstruido.

---

## Video Demostrativo
En el siguiente enlace se presenta la funcionalidad completa del sistema, incluyendo el modelado del grafo, la simulación de bloqueos y la comparativa visual entre los algoritmos BFS y DFS.

**Nota:** El video cuenta con audio explicativo. Si el reproductor lo inicia en silencio, activar el sonido en los controles del video o abrir el archivo `proyectoFinalVideo.mp4` directamente con su reproductor local.

## Requisitos para Ejecución
1. Tener instalado **Java 17** o superior.
2. Mantener la carpeta `assets` en el mismo directorio que el archivo `.jar`.

---

## Análisis y Persistencia
* **Reporte Automático:** Tabla adaptativa con el tiempo de ejecución (ms) y conteo de nodos explorados.
* **Archivo CSV:** Exportación de datos de rendimiento a `assets/tiempos.csv`.
* **Persistencia del Grafo:** Almacenamiento de la estructura en `assets/grafo.txt` para carga automática.

---

## Bibliografía
* **Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009).** *Introduction to Algorithms*. MIT Press.
* **Oracle. (2024).** *Java Documentation - Swing & Collections Framework*. Recuperado de [docs.oracle.com](https://docs.oracle.com/javase/8/docs/api/)

---
**Desarrollado por:** Bryan Santos.    
**Materia:** Estructura de Datos.  
**Docente:** Ing. Pablo Torres.  
**Carrera:** Computacion.   
**Institución:** Universidad Politécnica Salesiana.  
