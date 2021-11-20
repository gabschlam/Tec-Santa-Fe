# Tarea 2
## Descripción de la tarea: 
Crear un sistema solar con three.js, en donde se cumplan las siguentes instrucciones y condiciones:
1. Crea 8 planetas (y plutón), con sus respectivas lunas, el sun, y el cinturón de asteroides.
2. Los astros se pueden crear como esferas.
3. Los planetas y lunas tienen que tener su propia rotación.
4. Las lunas tienen que rotar al rededor de los planetas, y los planetas tienen que rotar al rededor del sol.
5. Dibuja las orbitas de cada planeta.
6. Cada elemento tiene que tener su propio materia, con texturas, normales, y bump maps (de existir).
7. Investiga cómo funciona el orbit controller de three.js e integralo en la escena.
## ¿Cómo ataqué el problema de la tarea?
1. Primero que nada, tomé el código de la actividad de satélites ([satelites.js](../../actividades/satelites/satelites.js)) para ver cómo podría modificarlo para usarlo de base.
2. Después, pensé en la estructura del sistema solar, para pensar en tema de pivotes y de jerarquías, para resolver las dependencias.
3. De allí, empecé a crear las figuras pasando los valores directamente a la función de createPlanet, sin utilizar ningún tipo de variables dinámicas, y con una textura y material sencilla, pero sí tomando las texturas reales por elemento.
4. A continuación, empecé a implementar los movimientos de rotación y traslación de cada figura alrededor del sol, pero al igual que antes, sin valores independientes por figura, nada más con un valor fijo.
5. Al ver que el paso anterior funcionaba, tomé como base la función de addSatelite del ejemplo anterior y la fui modificando para agregar las lunas, empezando por agregar una luna por planeta, para ver que funcione.
6. Seguí dibujando las órbitas, usando una figura de elipse.
7. Igualmente, después de asegurarme que funcionara así el programa, busque cómo obtener los datos para cada figura, para hacer que roten y se muevan alrededor del sol de forma independiente. Llegué a la conclusión de utilizar una función para inicializar los datos, en donde se llenara un arreglo *planets*, en donde se guardara los datos correspondientes a cada planeta, siguiendo el siguiente formato tipo *json*:
    ```json
    {
        "radius": 0.6371, 
        "rotationSpeed": 0.2651, 
        "orbitSpeed": 0.029785, 
        "mapTexture": "../../images/solarSystem/earth_atmos_2048.jpg", 
        "bumpTexture": "../../images/solarSystem/earth_atmos_2048_bump.jpg",
        "bumpMapScale": 0.05,
        "specularTexture": "../../images/solarSystem/earth_specular_spec_1k.jpg",
        "numMoons": 1  
    }
    ```
    El formato anterior cambia dependiendo la cantidad de información que haya por planeta. Por ejemplo, algunos planetas no tienen *bumpTexture*, *bumpMapScale* y *specularTexture* ya que no hay texturas disponibles.
8. Al tener los datos, cambié el valor fijo de las diferentes velocidades por la información específica para que cada uno rote y se mueva de forma independiente.
9. Después, agregué una función que recorriera el arreglo de planetas y dependiendo de ciertas validaciones del índice del ciclo for, se manda a llamar cierta función para crear el planeta. Por ejemplo, los primeros 4 planetas, después del sol, tienen *bumpTexure*, y por defecto, *bumpMapScale*, así que se manda a llamar la función correspondiente para crear un Phong Material con esa textura.
10. Por último, se implementó el cinturón de asteroides, con sus respectivas rotaciones y traslaciones y se modificaron ciertas variables al crear las órbitas y al posicionar los planetas, para que el cinturón no tenga órbita dibujada.
11. Después del desarrollo, llegué al tema de las luces. Primero tuve una luz ambiental para ver y confirmar que funcione todo como debería. Ya después, al leer el capítulo del libro para las diferentes luces, decidí implementar una spotLight como parte del Sol. Después descubrí y el profe me dio el consejo de usar pointLight y funcionó mejor. Para terminar, fuí moviendo los parámetros como posición e intensidad de la luz para lograr el objetivo de ver el día y la noche.
