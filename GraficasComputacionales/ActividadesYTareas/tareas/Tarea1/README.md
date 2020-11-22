# Tarea 1
## Descripción de la tarea: 
Crear un canvas y dibujar 3 objectos diferentes, cada uno con una rotación previamente definida:
1. Pirámide pentagonal, rotando alrededor del eje [0.1, 1.0, 0.2].
2. Dodecaedro, rotando alrededor del eje [-0.4, 1.0, 0.1].
3. Octaedro, rotando alrededor del eje [0, 1, 0]
Además, agregar un movimiento al Octaedro de arriba a abajo (en el eje y), que rebote al llegar al límite superior o inferior.
Los objetos anteriores acomodados en un orden específico, la pirámide a la izquierda del canvas, el dodecaedro en medio, y el octaedro a la derecha, ajustando el tamaño del canvas para que quepan las 3 figuras.
## ¿Cómo ataqué el problema de la tarea?
1. Primero que nada, tomé el código visto en clase de los cubos en 3d como base para poder reutilizar las funciones útiles para esta tarea.
2. Después, decidí empezar por la primera figura, la pirámide pentagonal. Para esta primero empecé con una pirámide normal para ubicar los puntos y vértices correspondientes a cada cara y empezar a familiarizarme. Después cambié la base por la base pentagonal y agregué una cara más. Tomé la función ya hecha de createCube y modifiqué los vertices a los de la pirámide. Cambié los faceColors, dejando 6 colores diferentes, y los pyramidIndices, poniendo cada triángulo como vértice explícito, y agregando 3 triángulos para dibujar el pentágono. Por último, modifiqué las variables de vertSize, nVerts, colorSize, nColors y nIndices.
3. Al correr el html, me di cuenta que las caras no estaban pintadas como deberían de, así que vi que al asignar a cada vértice la información del color, faltaba asignar 5 en vez de 3 vértices para la última cara (la base).
4. Luego llegamos al Dodecaedro. Decidí buscar una figura ya armada en Geogebra para poder ubicar más facilmente los puntos que conforman la figura. De allí utilicé esos puntos para ponerlos en un Geogebra limpio para ir armando cara por cara, uniendo esos vértices.
5. Ya con las 12 caras, pasé esa información al arreglo de vértices, agregué 12 colores diferentes a faceColors, y por cada cara, añadí 3 triángulos (9 índices) a dodecahedronIndices y modifiqué las variables de vertSize, nVerts, colorSize, nColors y nIndices.
6. Al final, cree la última figura, el octaedro. La creación de la figura fue igual a las anteriores, pero aquí se agregó el movimiento. Después de investigar cómo acceder a la posición en "y" del objeto, vi que era el índice 13 del modelViewMatrix. Con esta información cree un par de validaciones en el update de la figura para comparar esa posición con el límite superior e inferior del canvas para invertir la velocidad y cambiar el sentido del movimiento.
