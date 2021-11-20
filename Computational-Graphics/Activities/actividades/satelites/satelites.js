let renderer = null, 
scene = null, 
camera = null,
element = null,
satelite = null,
elementGroup = null,
pivot = null,
sateliteGroup = null;

// Array for all pivots added into scene
let pivotArray = [];

// Array for all elementsGroups added into scene
let elementsArray = [];

let duration = 5000; // ms
let currentTime = Date.now();

function animate() 
{
    let now = Date.now();
    let deltat = now - currentTime;
    currentTime = now;
    let fract = deltat / duration;
    let angle = Math.PI * 2 * fract;

    // If there is at least one element in pivotArray' array
    if (pivotArray.length != 0) {
        // Rotate each satelite around pivot about its Y axis
        pivotArray.forEach(item => {
            item.rotation.y += 0.03;
            
        }); 

        // Rotate each element about its X axis
        elementsArray.forEach(element => {
            element.children[0].rotation.x += angle;
        })
        
    }

   
}

function run() {
    requestAnimationFrame(function() { run(); });
    
    // Render the scene
    renderer.render( scene, camera );

    // Spin all pivots for next frame
    animate();
}

function createScene(canvas)
{    
    // Create the Three.js renderer and attach it to our canvas
    renderer = new THREE.WebGLRenderer( { canvas: canvas, antialias: true } );

    // Set the viewport size
    renderer.setSize(canvas.width, canvas.height);
    
    // Create a new Three.js scene
    scene = new THREE.Scene();

    // Set the background color 
    scene.background = new THREE.Color( 0.2, 0.2, 0.2 );

    // Add a camera so we can view the scene
    camera = new THREE.PerspectiveCamera( 45, canvas.width / canvas.height, 1, 4000 );
    camera.position.z = 30;
    scene.add(camera);

    // This light globally illuminates all objects in the scene equally.
    // Cannot cast shadows
    let ambientLight = new THREE.AmbientLight(0xffffff, 0.8);
    scene.add(ambientLight);
}

// Function for adding each "main" element.
function addElement() {
    // Create a group to hold pivot and element (Empty Object in Unity)
    elementGroup = new THREE.Object3D;

    pivot = new THREE.Object3D;

    let textureUrl = "../../images/ash_uvgrid01.jpg";
    let texture = new THREE.TextureLoader().load(textureUrl);
    let material = new THREE.MeshPhongMaterial({ map: texture });

    // Call function for getting random geometry, with an offset of 1 (explained on getRandomGeometry())
    geometry = getRandomGeometry(1);

    // Put the random geometry and material together into a mesh
    element = new THREE.Mesh(geometry, material);
    
    // Tilt the mesh toward the viewer
    element.rotation.x = Math.PI / 5;
    element.rotation.y = Math.PI / 5;

    // Add the element mesh into the element group
    elementGroup.add( element );
    elementGroup.add( pivot );

    // If there is no elements on array (on scene), place it on center
    if (pivotArray.length == 0) {
        elementGroup.position.set(0, 0, 0);
    }
    else {
        // Set the new element group in a random position:
        // between -13 and 13 in x, -9 and 9 in y, for leaving object inside scene.
        elementGroup.position.set((Math.random() * 26) + -13, (Math.random() * 18) + -9, 0);
        console.log(elementGroup.position);
    }

    // Push pivot group into pivotArray array
    pivotArray.push(pivot);

    // Push elementGroup group into elementsArray array
    elementsArray.push(elementGroup);

    console.log(pivotArray.length)

    // Now add the group to our scene
    scene.add( elementGroup );

    console.log(elementsArray);
}

/* Function for getting a random geometry.
offset: for sizing each geometry depending on the specific use.
If it is called from addElement(), the offset should be one.
If it is called from addSatelite(), the offset should be less than one (0.5), for making the satelite smaller than the element */
function getRandomGeometry(offset) {
    let geometry;
    // Random for choosing geometry
    let random = Math.floor(Math.random() * 4);
    switch (random) {
        case 0:
            // Create the cube geometry
            geometry = new THREE.CubeGeometry(2 * offset, 2 * offset, 2 * offset);
            return geometry;
        case 1:
            // Create the sphere geometry
            geometry = new THREE.SphereGeometry(1.5 * offset, 30 * offset, 30 * offset);
            return geometry;
        case 2:
            // Create the cone geometry
            geometry = new THREE.CylinderGeometry(0 * offset, 1.5 * offset, 3 * offset, 20 * offset, 20 * offset);
            return geometry;
        default:
            // Create the octahedron geometry
            geometry = new THREE.OctahedronBufferGeometry(1.5 * offset);
            return geometry;
      }
}

// Function for adding satelite to the last element added
function addSatelite() {
    let textureUrl = "../../images/ash_uvgrid01.jpg";
    let texture = new THREE.TextureLoader().load(textureUrl);
    let material = new THREE.MeshPhongMaterial({ map: texture });

    console.log(pivotArray.length)
    // If there is no pivots added in scene, do not do anything
    if (pivotArray.length == 0) {
        return;
    }

    // Create a group for the satelites
    sateliteGroup = new THREE.Object3D;

    // Add satelite group to the last element group
    pivot.add(sateliteGroup);

    // Call function for getting random geometry, with an offset of 0.5
    geometry = getRandomGeometry(0.5);

    // Put the random geometry and material together into a mesh
    satelite = new THREE.Mesh(geometry, material);
    
    // Add the satelite mesh to our group
    sateliteGroup.add( satelite );

    // Set satelite group in random position between -3 and 3
    sateliteGroup.position.set(Math.floor(Math.random() * 6) + -3, 0, Math.floor(Math.random() * 6) + -3);

    console.log(elementsArray);

}

// Function for reseting scene, without deleting light, because that element is not in elements' array
function reset() {
    // While there are elements on elements' array
    while(elementsArray.length > 0){ 
        // Remove the first element from scene
        scene.remove(elementsArray[0]);
        // Remove that element from array
        elementsArray.splice(elementsArray, 1);
        
    }

    pivotArray = [];
}