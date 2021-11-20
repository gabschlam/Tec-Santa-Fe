let renderer = null, 
scene = null, 
camera = null,
root = null,
group = null,
objectGroup = null,
objLoader = null,
spotLight = null,
ambientLight = null;

let mapUrl = "../../images/snow_texture.jpg";

let SHADOW_MAP_WIDTH = 4096, SHADOW_MAP_HEIGHT = 4096;

let animator = null,
animatorBalancing = null,
duration = 10, // sec
loopAnimation = true;

let objModelUrl = {
    obj:'../../models/obj/Penguin_obj/penguin.obj', 
    map:'../../models/obj/Penguin_obj/peng_texture.jpg'
};

// Function to load a promise
function promisifyLoader ( loader, onProgress ) 
{
    function promiseLoader ( url ) {
  
      return new Promise( ( resolve, reject ) => {
  
        loader.load( url, resolve, onProgress, reject );
  
      } );
    }
  
    return {
      originalLoader: loader,
      load: promiseLoader,
    };
}

const onError = ( ( err ) => { console.error( err ); } );

// Function to load a Obj Model
async function loadObj(objModelUrl)
{
    // Call the promise loader function
    const objPromiseLoader = promisifyLoader(new THREE.OBJLoader());

    try {
        // Load with the promise created the obj url
        const object = await objPromiseLoader.load(objModelUrl.obj);
        
        // Load different textures
        let texture = objModelUrl.hasOwnProperty('map') ? new THREE.TextureLoader().load(objModelUrl.map) : null;
        let normalMap = objModelUrl.hasOwnProperty('normalMap') ? new THREE.TextureLoader().load(objModelUrl.normalMap) : null;
        let specularMap = objModelUrl.hasOwnProperty('specularMap') ? new THREE.TextureLoader().load(objModelUrl.specularMap) : null;

        console.log(object);
        
        // Assign values to object
        object.traverse(function (child) {
            if (child instanceof THREE.Mesh) {
                child.castShadow = true;
                child.receiveShadow = true;
                child.material.map = texture;
                child.material.normalMap = normalMap;
                child.material.specularMap = specularMap;
            }
        });

        object.scale.set(0.25, 0.25, 0.25);
        object.position.set(0,-4,0);
        object.name = "objObject";
        objectGroup.add(object);

    }
    catch (err) {
        return onError(err);
    }
}

function run() 
{
    requestAnimationFrame(function() { run(); });

    // Render the scene
    renderer.render( scene, camera );

    // Update the animations
    KF.update();

    // Update the camera controller
    orbitControls.update();
}

function createScene(canvas) 
{
    // Create the Three.js renderer and attach it to our canvas
    renderer = new THREE.WebGLRenderer( { canvas: canvas, antialias: true } );

    // Set the viewport size
    renderer.setSize(canvas.width, canvas.height);

    // For receiving and casting shadows
    renderer.shadowMap.enabled = true;

    // Create a new Three.js scene
    scene = new THREE.Scene();

    // Add a camera so we can view the scene
    camera = new THREE.PerspectiveCamera( 45, canvas.width / canvas.height, 1, 4000 );
    camera.position.set(0, 6, 20);
    scene.add(camera);

    // Create the orbit controls
    orbitControls = new THREE.OrbitControls(camera, renderer.domElement);
    
    // Create a group to hold all the objects
    root = new THREE.Object3D;

    // Create and add all the lights
    spotLight = new THREE.SpotLight (0xaaaaaa);
    spotLight.position.set(2, 8, 15);
    spotLight.target.position.set(-2, 0, -2);
    root.add(spotLight);

    spotLight.castShadow = true;
    spotLight.shadow.camera.near = 1;
    spotLight.shadow.camera.far = 200;
    spotLight.shadow.camera.fov = 45;
    spotLight.shadow.mapSize.width = SHADOW_MAP_WIDTH;
    spotLight.shadow.mapSize.height = SHADOW_MAP_HEIGHT;

    ambientLight = new THREE.AmbientLight ( 0xebedeb, 0.8);
    root.add(ambientLight);

    // Create a group to hold the object
    objectGroup = new THREE.Object3D;
    root.add(objectGroup);

    // Create the object
    loadObj(objModelUrl);

    // Create a group to hold the plane
    group = new THREE.Object3D;
    root.add(group);

    // Create a texture map
    let map = new THREE.TextureLoader().load(mapUrl);
    map.wrapS = map.wrapT = THREE.RepeatWrapping;
    map.repeat.set(8, 8);

    let color = 0xffffff;

    // Put in a ground plane to show off the lighting
    let geometry = new THREE.PlaneGeometry(200, 200, 50, 50);
    let mesh = new THREE.Mesh(geometry, new THREE.MeshPhongMaterial({color:color, map:map, side:THREE.DoubleSide}));

    mesh.rotation.x = -Math.PI / 2;
    mesh.position.y = -4.02;
    mesh.castShadow = false;
    mesh.receiveShadow = true;
    group.add( mesh );

    // Now add the group to our scene
    scene.add( root );
}

function playAnimations() 
{
    // Animator only for rotation and movement
    animator = new KF.KeyFrameAnimator;
    animator.init({ 
        interps:
            [
                // Keys for the rotation in y for the Penguin for facing forward
                { 
                    keys:[0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1], 
                    values:[
                            // 135º (because starting facing to camera)
                            { y : (3 * Math.PI) / 4  },
                            // 135º (because starting facing to camera)
                            { y : (3 * Math.PI) / 4  },
                            // 30º
                            { y : Math.PI / 6 },
                            // -45º
                            { y : -(Math.PI / 4) },
                            // -130º
                            { y : -((3 * Math.PI) / 4) },
                            // -130º (for continuing movement)
                            { y : -((3 * Math.PI) / 4) },
                            // -30º
                            { y : -(Math.PI / 6) },
                            // 45º
                            { y : Math.PI / 4 },
                            // 130º
                            { y : (3 * Math.PI) / 4 },
                            ],
                    target:objectGroup.rotation
                }, 
                // Keys for the movement in ∞ by the Penguin
                { 
                    keys:[0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1], 
                    values:[
                            { x : 0, z : 0 },
                            { x : 4, z : -4 },
                            { x : 8, z : 0 },
                            { x : 4, z : 4 },
                            { x : 0, z : 0 },
                            { x : -4, z : -4 },
                            { x : -8, z : 0 },
                            { x : -4, z : 4 },
                            { x : 0, z : 0 },
                            ],
                    target:objectGroup.position,
                    easing:TWEEN.Easing.Exponential.Out
                }
            ],
        loop: loopAnimation,
        duration: duration * 1000,
    });
    animator.start();

    // Animator for balancing the Penguin
    animatorBalancing = new KF.KeyFrameAnimator;
    animatorBalancing.init({ 
        interps:
            [
                // Keys for balancing side to side the Penguin
                { 
                    keys:[0, .25, .5, .75, 1], 
                    values:[
                            { z : 0 },
                            // 20º
                            { z : Math.PI / 9 },
                            { z : 0  },
                            // -20º
                            { z : -(Math.PI / 9) },
                            { z : 0 },
                            ],
                    target:objectGroup.rotation
                },
            ],
        loop: loopAnimation,
        // Different duration, faster than the other animator
        duration: duration * 150,
    });
    animatorBalancing.start();

}