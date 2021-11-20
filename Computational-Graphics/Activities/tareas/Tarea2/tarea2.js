let renderer = null, 
scene = null, 
camera = null,
geometry = null,
planet = null,
moon = null,
asteroid = null,
orbitControls = null,
elementGroup = null,
moonGroup = null,
asteroidGroup = null,
asteroidGroupForOrbit = null,
elementGroupForOrbit = null,
pivot = null;

// Pivots arrays for all rotations and translations, for pivots added into scene
let pivotArray = [];
let pivotArrayForOrbit = [];
let pivotArrayMoons = [];
let pivotArrayAsteroids = [];
let pivotArrayAsteroidsForOrbit = [];

// Array for all objects added into scene
let objects = [];

// Array for planets information
let planets = [];

let duration = 5000; // ms
let currentTime = Date.now();

// Function for animating the scene, making each rotation and movement
function animate() 
{
    let now = Date.now();
    let deltat = now - currentTime;
    currentTime = now;
    let fract = deltat / duration;
    let angle = Math.PI * 2 * fract;
    
    // Rotation of each moon around planets
    pivotArray.forEach(item => {
        // Random between 0.02 and 0.05
        item.rotation.y += (Math.random() * (0.03) + 0.02) + angle;
    });

    // Rotation for each moon around its own axis
    pivotArrayMoons.forEach(item => {
        // Random between 0.05 and 0.1
        item.rotation.y += (Math.random() * (0.05) + 0.05) + angle;
    });

    // Rotation for each asteroid around its own axis
    pivotArrayAsteroids.forEach(item => {
        // Random between 0.05 and 0.1
        item.rotation.y += (Math.random() * (0.05) + 0.05) + angle;
    });

    // Rotation of each asteroid around the sun
    pivotArrayAsteroidsForOrbit.forEach(item => {
        // Random between 0.02 and 0.05
        item.rotation.y += (Math.random() * (0.03) + 0.02) + angle;
    });

    // Orbit movement for each planet around the sun, with its corresponding speed
    pivotArrayForOrbit.forEach((item, i) => {
        item.rotation.y += planets[i+1]["orbitSpeed"];
    }); 
    
    // Rotate each planet about its own axis, with its corresponding speed
    objects.forEach((element, i) => {
        element.children[0].rotation.y += planets[i+1]["rotationSpeed"];
        
    });

    // Update for orbit controls
    orbitControls.update();
   
}

// Function for running the scene
function run() 
{
    requestAnimationFrame(function() { run(); });
    
    // Render the scene
    renderer.render( scene, camera );

    // Spin all pivots for next frame
    animate();
}

// Function for creating the scene, wich creates the scene, the camera and calls each corresponding function for setting the scene
function createScene(canvas)
{
    // Create the Three.js renderer and attach it to our canvas
    renderer = new THREE.WebGLRenderer( { canvas: canvas, antialias: true } );

    // Set the viewport size
    renderer.setSize(canvas.width, canvas.height);
    
    // Create a new Three.js scene
    scene = new THREE.Scene();

    // Set the background image 
    scene.background = new THREE.TextureLoader().load("../../images/solarSystem/spaceBackground.jpg");
    
    // Add a camera so we can view the scene
    camera = new THREE.PerspectiveCamera( 45, canvas.width / canvas.height, 1, 4000 );
    camera.position.x = 5;
    camera.position.y = 2;
    camera.position.z = -1.2;
    scene.add(camera);

    // let ambientLight = new THREE.AmbientLight(0xffffff, 0.8);
    // scene.add(ambientLight);

    // Set the orbit controls for moving the camera with mouse
    orbitControls = new THREE.OrbitControls(camera, renderer.domElement);

    // Call function for loading each planet data
    loadPlanetData();

    // Call function for creating each planet
    createPlanets();

    console.log(objects);

    // Add orbits by number of planets in array
    addOrbits(objects.length);

}

/* 
    Function for initializing the planets array, setting each specific data for each planet and sun.
    Each object has diferent information according to the specific needs. 
    For example: Some planets do not have bumpTexture because there are no textures available 
    Some of the values are aproximated to the real ones, some were modified for performance purposes
*/
function loadPlanetData() 
{
    // Initalize data for sun
    var sun = { 
        radius: 1.95508,
        mapTexture: '../../images/solarSystem/sun_map.jpg'
    };
    // Push sun data into planets array
    planets.push(sun);
    
    // Initalize data for mercury
    var mercury = {
        radius: 0.24, 
        rotationSpeed: 0.0030, 
        orbitSpeed: 0.048, 
        mapTexture: '../../images/solarSystem/mercury_map.jpg', 
        bumpTexture: '../../images/solarSystem/mercury_bump.jpg',
        bumpMapScale: 0.005,
        numMoons: 0
    };
    // Push mercury data into planets array
    planets.push(mercury);

    // Initalize data for venus
    var venus = {
        radius: 0.6051,
        rotationSpeed: 0.0018,
        orbitSpeed: 0.035021,
        mapTexture: '../../images/solarSystem/venus_map.jpg', 
        bumpTexture: '../../images/solarSystem/venus_bump.jpg',
        bumpMapScale: 0.005,
        numMoons: 0
    };
    // Push venus data into planets array
    planets.push(venus);

    // Initalize data for earth
    var earth = {
        radius: 0.6371, 
        rotationSpeed: 0.2651, 
        orbitSpeed: 0.029785, 
        mapTexture: '../../images/solarSystem/earth_atmos_2048.jpg', 
        bumpTexture: '../../images/solarSystem/earth_atmos_2048_bump.jpg',
        bumpMapScale: 0.005,
        specularTexture: '../../images/solarSystem/earth_specular_spec_1k.jpg',
        numMoons: 1
    };
    // Push earth data into planets array
    planets.push(earth);

    // Initalize data for mars
    var mars = {
        radius: 0.3389, 
        rotationSpeed: 0.2408,
        orbitSpeed: 0.024131,
        mapTexture: '../../images/solarSystem/mars_map.jpg', 
        bumpTexture: '../../images/solarSystem/mars_bump.jpg',
        bumpMapScale: 0.05,
        numMoons: 0
    };
    // Push mars info into planets array
    planets.push(mars);
    
    // Initalize data for jupiter
    var jupiter = {
        radius: 0.99911, 
        rotationSpeed: 10.5720,
        orbitSpeed: 0.013069,
        mapTexture: '../../images/solarSystem/jupiter_map.jpg',
        numMoons: 4
    };
    // Push jupiter info into planets array
    planets.push(jupiter);

    // Initalize data for saturn
    var saturn = {
        radius: 0.958232, 
        rotationSpeed: 8.0179, 
        orbitSpeed: 0.009672, 
        mapTexture: '../../images/solarSystem/saturn_map.jpg', 
        ringTexture: '../../images/solarSystem/saturnringcolor.jpg',
        numMoons: 7
    };
    // Push saturn info into planets array
    planets.push(saturn);

    // Initalize data for uranus
    var uranus = {
        radius: 0.925362, 
        rotationSpeed: 2.5875,
        orbitSpeed: 0.006835,
        mapTexture: '../../images/solarSystem/uranus_map.jpg',
        numMoons: 5
    };
    // Push uranus info into planets array
    planets.push(uranus);

    // Initalize data for neptune
    var neptune = {
        radius: 0.924622, 
        rotationSpeed: 2.6869,
        orbitSpeed: 0.005477,
        mapTexture: '../../images/solarSystem/neptune_map.jpg',
        numMoons: 1
    };
    // Push neptune info into planets array
    planets.push(neptune);

    // Initalize data for pluto
    var pluto = {
        radius: 0.31185, 
        rotationSpeed: 0.0123,
        orbitSpeed: 0.0047490,
        mapTexture: '../../images/solarSystem/pluto_map.jpg', 
        numMoons: 1
    };
    // Push neptune info into planets array
    planets.push(pluto);
}

/* 
    Function for creating all planets and sun. 
    This function goes through the planets array with all information, and compares the current index 
    for knowing with planet is going to be created in order to call the corresponding function
*/
function createPlanets() {
    // Variables for creating the ring in Saturn
    let ringSegments = 70;
    let innerRadius, outerRadius;

    // Go through the planets array
    for (let i = 0; i < planets.length; i ++) {
        // Create the sphere geometry for each planet that will be created next (global variable)
	    geometry = new THREE.SphereGeometry( planets[i]["radius"]/10, 32, 32 );

        // If i = 0, it means that the sun will be created (first element on planets' array)
        if (i == 0) {
            // Create sun passing as parameter the Sun's texture url
            createSun(planets[i]["mapTexture"]);
        }
        // If i between 1 and 4, it means that we reach the planets that have a bump Texture to use.
        else if (i > 0 && i < 5) {
            // If i = 3, it means that the planet Earth has been reached in the array, 
            // meaning that it has a specular Texture, for passing it to the createPlanetWithBump function
            if (i == 3) {
                createPlanetWithBump( planets[i]["numMoons"], planets[i]["bumpMapScale"], planets[i]["mapTexture"], planets[i]["bumpTexture"], planets[i]["specularTexture"]);
            }
            else {
                createPlanetWithBump( planets[i]["numMoons"], planets[i]["bumpMapScale"], planets[i]["mapTexture"], planets[i]["bumpTexture"]);

            }
            // If i = 4, it means that Mars have been created, and the asteroid Belt needs to be created
            if (i == 4) {
                addAsteroidBelt(100);
            }
        }
        // If i = 6, it means that Saturn has been reached in the array, and the createRing function has to be called 
        else if (i == 6) {
            createPlanet( planets[i]["numMoons"], planets[i]["mapTexture"]);
            // Initialize inner and outer radius for ring. The following values were debugged to find the best size for the ring
            innerRadius = (planets[i]["radius"] * 0.16) / planets[i]["radius"];
            outerRadius = (planets[i]["radius"] * 0.08) / planets[i]["radius"];
            createRing(innerRadius, outerRadius, ringSegments, planets[i]["ringTexture"]);
        }
        // Else, the current planet to create is a normal one
        else {
            createPlanet( planets[i]["numMoons"], planets[i]["mapTexture"]);
        }
    }

}

/*
    Function for creating the Sun and add it into the scene and its corresponding group and pivot
    Input parameter: textureURL
    Output parameter: none
*/
function createSun(textureURL) 
{
    // Create a group to hold pivot and element (Empty Object in Unity)
    elementGroup = new THREE.Object3D;
    // Create a pivot for the Sun
    pivot = new THREE.Object3D;

    // Create and assing texture loading the textureURL passed to the function.
    let texture = new THREE.TextureLoader().load( textureURL );
    // Create and assign the material, with the texture loaded
    let material = new THREE.MeshBasicMaterial( { map: texture });
    // Create and assign mesh with the geometry initialized in the createPlanets function
    let sun = new THREE.Mesh( geometry, material );
    // For the pointlight light
    sun.receiveShadow = false;
    sun.castShadow = false;

    // Add the mesh and the pivot into the element group
    elementGroup.add( sun );
    elementGroup.add( pivot );

    // Set the element group in the center
    elementGroup.position.set(0, 0, 0);

    // Add the element group into scene
    scene.add ( elementGroup );

    // Create poinyLight light
    let pointLight = new THREE.PointLight(0xffffff);
    pointLight.position.set(0, 0, 0);
    pointLight.intensity = 1.5;

    // Add light as a child to the sun
    sun.add(pointLight);
}

/*
    Function for creating each planet that does not have a bump Texture and add it into the scene and its corresponding group and pivot
    Input parameter: number of Moons and mapTexture url
    Output parameter: none
*/
function createPlanet(numMoons, mapTexture) {
    // Create a group to hold pivot and element (Empty Object in Unity)
    elementGroup = new THREE.Object3D;
    // Create a group to hold the element and to function as a pivot to make the corresponding orbit, in order to achieve independent movement around the sun
    elementGroupForOrbit = new THREE.Object3D;

    // Create a pivot for the planet, which will allow the moons to rotate around it
    pivot = new THREE.Object3D;
    // Create a pivot for the planet, which will allow it to make the orbit around the sun
    pivotPlanetForOrbit = new THREE.Object3D;

    // Create and assing texture loading the textureURL passed to the function.
    let texture = new THREE.TextureLoader().load(mapTexture);
    // Create and assign the material, with the texture loaded
    let material = new THREE.MeshBasicMaterial( { map: texture } );

    // Assign mesh with the geometry initialized in the createPlanets function
    planet = new THREE.Mesh(geometry, material);
    // Set the receiveShadow into true for the pointlight
    planet.receiveShadow = true;

    // Add the planet and the pivot into the element group
    elementGroup.add( planet );
    elementGroup.add( pivot );

    // Set the element group into 0.2 times the number of elements already in the scene for spacing them through the canvas, 
    // plus two for skipping the asteroid belt.
    elementGroup.position.set((0.2 * (objects.length + 3)), 0, (0.2 * (objects.length + 1)));

    // If the current planet we are creating has moons
    if (numMoons > 0) {
        // Add as many moons as the planet have
        for (let i = 0; i < numMoons; i++) {
            addMoon(pivot);
        }
    }

    // Push pivot group into pivotArray array
    pivotArray.push(pivot);

    // Push elementGroup group into objects array
    objects.push(elementGroup);

    // Add the element group and corresponding pivot into the element group for the orbit around the sun
    elementGroupForOrbit.add( elementGroup );
    elementGroupForOrbit.add( pivotPlanetForOrbit );
 
    // Set the element group for the orbit in the center where the Sun is
    elementGroupForOrbit.position.set(0, 0, 0);

    // Push element group for orbit into into pivotArray for orbit
    pivotArrayForOrbit.push(elementGroupForOrbit);
 
    // Add into scene the element group for the orbit
    scene.add ( elementGroupForOrbit );

}

/*
    Function for creating each planet that have a bump Texture and add it into the scene and its corresponding group and pivot
    Input parameter: number of Moons, bumpMapScale, mapTexture, bumpTexture and in some cases specularTexture
    Output parameter: none
*/
function createPlanetWithBump(numMoons, bumpMapScale, mapTexture, bumpTexture, specularTexture) 
{
    // Create a group to hold pivot and element (Empty Object in Unity)
    elementGroup = new THREE.Object3D;
    // Create a group to hold the element and to function as a pivot to make the corresponding orbit, in order to achieve independent movement around the sun
    elementGroupForOrbit = new THREE.Object3D;

    // Create a pivot for the planet, which will allow the moons to rotate around it
    pivot = new THREE.Object3D;
    // Create a pivot for the planet, which will allow it to make the orbit around the sun
    pivotPlanetForOrbit = new THREE.Object3D;

    // Create and assing texture map loading the mapTexture passed to the function.
    let textureMap = new THREE.TextureLoader().load(mapTexture);
    // Create and assing bump texture map loading the bumpTexture passed to the function.
    let bumpMap = new THREE.TextureLoader().load(bumpTexture);

    // Create and assign the material, in this case the Phong Material, with both textures loaded
    let material = new THREE.MeshPhongMaterial({
        map: textureMap,
        bumpMap: bumpMap,
        // Bump scale: the intensity of the bump map texture in the figure
        bumpScale: bumpMapScale
    });

    // If a specular Texture was passed into the function (only with the Earth case), add to the material that specular map
    if (specularTexture) {
        material.specularMap = new THREE.TextureLoader().load(specularTexture);
        material.specular = new THREE.Color(0x808080);
        material.shininess = 50.0;
    }
    // If not, only assign to the material some shininess
    else {
        material.shininess = 1.0;
    }

    // Assign mesh with the geometry initialized in the createPlanets function
    planet = new THREE.Mesh(geometry, material);
    // Set the receiveShadow into true for the pointlight
    planet.receiveShadow = true;

    // Add the planet and the pivot into the element group
    elementGroup.add( planet );
    elementGroup.add( pivot );

    // Set the element group into 0.2 times the number of elements already in the scene for spacing them through the canvas
    elementGroup.position.set((0.2 * (objects.length + 1)), 0, (0.2 * (objects.length + 1)));

    // If the current planet we are creating has moons
    if (numMoons > 0) {
        // Add as many moons as the planet have
        for (let i = 0; i < numMoons; i++) {
            addMoon(pivot);
        }
    }

    // Push pivot group into pivotArray array
    pivotArray.push(pivot);

    // Push elementGroup group into objects array
    objects.push(elementGroup);

    // Add the element group and corresponding pivot into the element group for the orbit around the sun
    elementGroupForOrbit.add( elementGroup );
    elementGroupForOrbit.add( pivotPlanetForOrbit );
 
    // Set the element group for the orbit in the center where the Sun is
    elementGroupForOrbit.position.set(0, 0, 0);

    // Push element group for orbit into into pivotArray for orbit
    pivotArrayForOrbit.push(elementGroupForOrbit);
 
    // Add into scene the element group for the orbit
    scene.add ( elementGroupForOrbit );
}   

/*
    Function for adding a moon to the corresponding planet
    Input parameter: pivot of the corresponding planet, and pos (in this case, the index of the moon)
    Output parameter: none
*/
function addMoon(pivot) 
{
    // Create and assing texture map loading the mapTexture
    let textureMap = new THREE.TextureLoader().load('../../images/solarSystem/moon_map.jpg');
    // Create and assing bump texture map loading the bumpTexture.
    let bumpMap = new THREE.TextureLoader().load('../../images/solarSystem/moon_bump.jpg');

    // Create and assign the material, in this case the Phong Material, with both textures loaded
    let material = new THREE.MeshPhongMaterial({
        map: textureMap,
        bumpMap: bumpMap,
        // Bump scale: the intensity of the bump map texture in the figure
        bumpScale: 0.05
    });

    // Create a group for the moon
    moonGroup = new THREE.Object3D;

    // Add moon group to the pivot passed into the function
    pivot.add(moonGroup);

    // Create a sphere geometry
    geometry = new THREE.SphereGeometry( 0.02, 32, 32 );

    // Put the geometry and material together into a mesh
    moon = new THREE.Mesh(geometry, material);
    // Set the receiveShadow into true for the pointlight
    moon.receiveShadow = true;
    
    // Add the moon mesh to our group
    moonGroup.add( moon );

    // Push pivot group into pivotArrayMoons array
    pivotArrayMoons.push(moon);

    // Set moon group in random position
    moonGroup.position.set((Math.floor(Math.random() * 2) -1) / 10, (Math.floor(Math.random() * 4) -2) / 10, (Math.floor(Math.random() * 2) -1) / 10);

}

/*
    Function for adding a ring to the corresponding planet
    Input parameter: innerRadius, outerRadius, ringSegments and textureUrl
    Output parameter: none
*/
function createRing(innerRadius, outerRadius, ringSegments, textureUrl) 
{
    // Create and assing texture loading the textureURL passed to the function.
    let texture = new THREE.TextureLoader().load(textureUrl);
    // Create and assign the material, with the texture loaded
    let material = new THREE.MeshBasicMaterial( {  map: texture, side: THREE.DoubleSide, transparent: true, opacity: 0.8} );
    // Create and assign the Ring Geometry with the parameters.
    let geometry = new THREE.RingGeometry( innerRadius, outerRadius, ringSegments );
    // Assign mesh with the geometry created
    let ring = new THREE.Mesh( geometry, material );
    // Rotate the ring into de x axis were all scene is placed on.
    ring.rotation.x = Math.PI/2;
    // Set the receiveShadow and castShadow into true for the pointlight
    ring.receiveShadow = true;
    ring.castShadow = true;

    // Add ring into the last planet (which is the one that needs it)
    planet.add( ring );
}

/*
    Function for adding all corresponding orbits
    Input parameter: number of orbits
    Output parameter: none
*/
function addOrbits(numberOfOrbits) 
{
    for (let i = 0; i < numberOfOrbits; i++) {
        // Define radius for spacing each orbit
        // The value which multiplies the current index was calculated over debbuging
        // If i is greater than 3, it means that the radius must be added two, for not drawing the asteroid's belt orbit
        if (i > 3)
        {
            var radius = (0.285 * (i+2));
        }
        else {
            var radius = (0.285 * (i+1));
        }
        // Create an ellipse curve with the radius calculated
        let curve = new THREE.EllipseCurve(0, 0, radius, radius, 0, 2 * Math.PI, false, 0);
        let points = curve.getPoints( 50 );
        let geometry = new THREE.BufferGeometry().setFromPoints( points );
        let material = new THREE.LineBasicMaterial( { color : 0xffffff } );
        let ellipse = new THREE.Line( geometry, material );
        // Rotate the orbit into de x axis were all scene is placed on.
        ellipse.rotation.x = Math.PI/2;
        scene.add( ellipse );
    }  
}

/*
    Function for adding the Asteroid Belt
    Input parameter: number of asteroids
    Output parameter: none
*/
function addAsteroidBelt(numAsteroids)
{
    var asteroidGroup;
    for (let i = 0; i < numAsteroids; i++) {
        // Create a group to hold pivot and element (Empty Object in Unity)
        asteroidGroup = new THREE.Object3D;
        // Create a group to hold the element and to function as a pivot to make the corresponding orbit around the sun
        asteroidGroupForOrbit = new THREE.Object3D;

        // Create a pivot for the asteroid, which will allow the moons to rotate around it
        pivot = new THREE.Object3D;
        // Create a pivot for the asteroid, which will allow it to make the orbit around the sun
        pivotAsteroidsForOrbit = new THREE.Object3D;

        // Create and assing texture map loading the texture
        let texture = new THREE.TextureLoader().load('../../images/solarSystem/asteroid_map.jpg');
        // Create and assign the material, with the texture loaded
        let material = new THREE.MeshBasicMaterial( { map: texture } );

        // Create a sphere geometry
        geometry = new THREE.SphereGeometry( 0.02, 32, 32 );

        // Put the geometry and material together into a mesh
        asteroid = new THREE.Mesh(geometry, material);
        // Set the receiveShadow into true for the pointlight
        asteroid.receiveShadow = true;
        asteroid.castShadow = true;

        // Add the asteroid and the pivot into the element group
        asteroidGroup.add( asteroid );
        asteroidGroup.add( pivot );

        // Set the random position
        let x = (0.28 * (objects.length + 1)) * (Math.cos(i) + ((Math.random() * 0.2) - 0.1));
        let y = (Math.random() * 0.2) - 0.1;
        let z = (0.28 * (objects.length + 1)) * (Math.sin(i) + ((Math.random() * 0.2) - 0.1));
        asteroidGroup.position.set(x,y,z);

        // Push group group into pivotArrayAsteroids array
        pivotArrayAsteroids.push(asteroidGroup);

        // Add the element group and corresponding pivot into the element group for the orbit around the sun
        asteroidGroupForOrbit.add( asteroidGroup );
        asteroidGroupForOrbit.add( pivotAsteroidsForOrbit );
    
        // Set the element group for the orbit in the center where the Sun is
        asteroidGroupForOrbit.position.set(0, 0, 0);

        // Push element group for orbit into into pivotArray for orbit
        pivotArrayAsteroidsForOrbit.push(asteroidGroupForOrbit);
    
        // Add into scene the element group for the orbit
        scene.add ( asteroidGroupForOrbit );

    }
}