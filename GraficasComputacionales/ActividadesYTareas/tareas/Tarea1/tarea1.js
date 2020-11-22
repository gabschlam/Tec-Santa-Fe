let mat4 = glMatrix.mat4;

let projectionMatrix;

let shaderProgram, shaderVertexPositionAttribute, shaderVertexColorAttribute, shaderProjectionMatrixUniform, shaderModelViewMatrixUniform;

let duration = 10000; // ms

// Attributes: Input variables used in the vertex shader. Since the vertex shader is called on each vertex, these will be different every time the vertex shader is invoked.
// Uniforms: Input variables for both the vertex and fragment shaders. These do not change values from vertex to vertex.
// Varyings: Used for passing data from the vertex shader to the fragment shader. Represent information for which the shader can output different value for each vertex.
let vertexShaderSource =    
    "    attribute vec3 vertexPos;\n" +
    "    attribute vec4 vertexColor;\n" +

    "    uniform mat4 modelViewMatrix;\n" +
    "    uniform mat4 projectionMatrix;\n" +

    "    varying vec4 vColor;\n" +

    "    void main(void) {\n" +
    "		// Return the transformed and projected vertex value\n" +
    "        gl_Position = projectionMatrix * modelViewMatrix * \n" +
    "            vec4(vertexPos, 1.0);\n" +
    "        // Output the vertexColor in vColor\n" +
    "        vColor = vertexColor * 0.8;\n" +
    "    }\n";

/* precision lowp float
This determines how much precision the GPU uses when calculating floats. The use of highp depends on the system.
- highp for vertex positions,
- mediump for texture coordinates,
- lowp for colors. */
let fragmentShaderSource = 
    "    precision lowp float;\n" +
    "    varying vec4 vColor;\n" +
    "    void main(void) {\n" +
    "    gl_FragColor = vColor;\n" +
    "}\n";

// Function for initializing WebGL
// Input parameter: canvas
// Output parameter: canvas context for webgl
function initWebGL(canvas)
{
    let gl = null;
    let msg = "Your browser does not support WebGL, " +
        "or it is not enabled by default.";
    try 
    {
        gl = canvas.getContext("experimental-webgl");
    } 
    catch (e)
    {
        msg = "Error creating WebGL Context!: " + e.toString();
    }

    if (!gl)
    {
        alert(msg);
        throw new Error(msg);
    }

    return gl;        
}

// Function for initializing the viewport with the canvas information
// Input parameters: canvas context for webgl, canvas
// Output parameter: none
function initViewport(gl, canvas)
{
    gl.viewport(0, 0, canvas.width, canvas.height);
}

// Function for initializing GL, with the perspective and translation
// Input parameter: canvas
// Output parameter: none
function initGL(canvas)
{
    // Create a project matrix from the mat4 library
    projectionMatrix = mat4.create();
    
    // Setting the perspective with the projectionMatrix (out), fovy, aspect ratio, near and far
    mat4.perspective(projectionMatrix, Math.PI / 4, canvas.width / canvas.height, 1, 100);
    mat4.translate(projectionMatrix, projectionMatrix, [0, 0, -5]);
}

// Create the vertex, color and index data for a multi-colored pentagonal pyramid
// Input parameters: canvas context for webgl, vector for translation, vector for rotation
// Output parameter: pyramid object
function createPyramid(gl, translation, rotationAxis)
{    
    // Vertex Data
    let vertexBuffer;
    vertexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer);

    let verts = [
        // Front face
        0.0,  2.0,  0.0,
        2.0, -1.0,  0.5,
        0.0, -1.0,  2.0,

        // Right1 face
        0.0,  2.0,  0.0,
        0.0, -1.0,  2.0,
        -2.0, -1.0, 0.5,

        // Right2 face
        0.0,  2.0,  0.0,
        -2.0, -1.0, 0.5,
        -1.0, -1.0, -2.0,

        // Back face
        0.0,  2.0,  0.0,
        -1.0, -1.0, -2.0,
        1.0, -1.0, -2.0,

        // Left face
        0.0,  2.0,  0.0,
        1.0, -1.0, -2.0,
        2.0, -1.0,  0.5,

        // Bottom face
        2.0, -1.0,  0.5,
        0.0, -1.0,  2.0,
        -2.0, -1.0, 0.5,
        -1.0, -1.0, -2.0,
        1.0, -1.0, -2.0,
       ];

    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(verts), gl.STATIC_DRAW);

    // Color data
    let colorBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer);

    let faceColors = [
        [1.0, 0.0, 0.0, 1.0], // Front face
        [0.0, 1.0, 0.0, 1.0], // Right1 face
        [0.0, 0.0, 1.0, 1.0], // Right2 face
        [1.0, 1.0, 0.0, 1.0], // Back face
        [1.0, 0.0, 1.0, 1.0], // Left face
        [0.0, 1.0, 1.0, 1.0]  // Bottom face
    ];

    // Each vertex must have the color information
    let vertexColors = [];
    for (let i=0; i < faceColors.length; i++) {
        // For the last face (bottom) that has 5 vertices
        if (i== faceColors.length -1) {
            // Adding 5 times (number of vertices on face) the color information
            for (let j=0; j < 5; j++)
            vertexColors.push(...faceColors[i]);
        } else {
            // Adding 3 times (number of vertices on face) the color information
            for (let j=0; j < 3; j++)
                vertexColors.push(...faceColors[i]);  
        }
    }

    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexColors), gl.STATIC_DRAW);

    // Index data (defines the triangles to be drawn)
    let pyramidIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, pyramidIndexBuffer);

    let pyramidIndices = [
        0,  1,  2,       // Front face
        3,  4,  5,       // Right1 face
        6,  7,  8,       // Right2 face
        9,  10, 11,      // Back face
        12, 13, 14,      // Left face
        15, 16, 17,     15, 17, 18,     15, 18, 19       // Bottom face (3 triangles)
    ];

    // gl.ELEMENT_ARRAY_BUFFER: Buffer used for element indices.
    // Uint16Array: Array of 16-bit unsigned integers.
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(pyramidIndices), gl.STATIC_DRAW);
    
    let pyramid = {
            buffer: vertexBuffer, colorBuffer:colorBuffer, indices:pyramidIndexBuffer,
            vertSize:3, nVerts:20, colorSize:4, nColors: 20, nIndices:24, // nVerts must be equal as the nColors. nIndices comes from cubeIndices
            primtype:gl.TRIANGLES, modelViewMatrix: mat4.create(), currentTime : Date.now()
        };

    mat4.translate(pyramid.modelViewMatrix, pyramid.modelViewMatrix, translation);

    pyramid.update = function()
    {
        let now = Date.now();
        let deltat = now - this.currentTime;
        this.currentTime = now;
        let fract = deltat / duration;
        let angle = Math.PI * 2 * fract;
    
        // Rotates a mat4 by the given angle
        // mat4 out the receiving matrix
        // mat4 a the matrix to rotate
        // Number rad the angle to rotate the matrix by
        // vec3 axis the axis to rotate around
        mat4.rotate(this.modelViewMatrix, this.modelViewMatrix, angle, rotationAxis);
    };
    
    return pyramid;
}

// Create the vertex, color and index data for a multi-colored dodecahedron
// Input parameters: canvas context for webgl, vector for translation, vector for rotation
// Output parameter: dodecahedron object
function createDodecahedron(gl, translation, rotationAxis)
{    
    // Vertex Data
    let vertexBuffer;
    vertexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer);

    // Names of vertices assigned for better figure construction, after drawing it on Geogebra
    let verts = [
        // Face 1
        0.5, 1.3, 0.0, // Point M
        0.8, 0.8, -0.8, // Point B
        1.3, 0.0, -0.5, // Point S
        1.3, 0.0, 0.5, // Point Q
        0.8, 0.8, 0.8, // Point A

        // Face 2
        0.5, 1.3, 0.0, // Point M
        0.8, 0.8, 0.8, // Point A
        0.0, 0.5, 1.3, // Point I
        -0.8, 0.8, 0.8, // Point E
        -0.5, 1.3, 0.0, // Point N

        // Face 3
        -0.5, 1.3, 0.0, // Point N
        -0.8, 0.8, -0.8, // Point F
        0.0, 0.5, -1.3, // Point J
        0.8, 0.8, -0.8, // Point B
        0.5, 1.3, 0.0, // Point M
        
        // Face 4
        0.8, 0.8, -0.8, // Point B
        0.0, 0.5, -1.3, // Point J
        0.0, -0.5, -1.3, // Point K
        0.8, -0.8, -0.8, // Point D
        1.3, 0.0, -0.5, // Point S

        // Face 5
        -0.5, 1.3, 0.0, // Point N
        -0.8, 0.8, 0.8, // Point E
        -1.3, 0.0, 0.5, // Point R
        -1.3, 0.0, -0.5, // Point T
        -0.8, 0.8, -0.8, // Point F

        // Face 6
        -0.8, 0.8, -0.8, // Point F
        -1.3, 0.0, -0.5, // Point T
        -0.8, -0.8, -0.8, // Point H
        0.0, -0.5, -1.3, // Point K
        0.0, 0.5, -1.3, // Point J

        // Face 7
        -1.3, 0.0, -0.5, // Point T
        -1.3, 0.0, 0.5, // Point R
        -0.8, -0.8, 0.8, // Point G
        -0.5, -1.3, 0.0, // Point P
        -0.8, -0.8, -0.8, // Point H

        // Face 8
        -0.8, 0.8, 0.8, // Point E
        0.0, 0.5, 1.3, // Point I
        0.0, -0.5, 1.3, // Point L
        -0.8, -0.8, 0.8, // Point G
        -1.3, 0.0, 0.5, // Point R

        // Face 9
        0.8, 0.8, 0.8, // Point A
        1.3, 0.0, 0.5, // Point Q
        0.8, -0.8, 0.8, // Point C
        0.0, -0.5, 1.3, // Point L
        0.0, 0.5, 1.3, // Point I

        // Face 10
        1.3, 0.0, 0.5, // Point Q
        1.3, 0.0, -0.5, // Point S
        0.8, -0.8, -0.8, // Point D
        0.5, -1.3, 0.0, // Point O
        0.8, -0.8, 0.8, // Point C

        // Face 11
        0.0, -0.5, -1.3, // Point K
        -0.8, -0.8, -0.8, // Point H
        -0.5, -1.3, 0.0, // Point P
        0.5, -1.3, 0.0, // Point O
        0.8, -0.8, -0.8, // Point D

        // Face 12
        -0.5, -1.3, 0.0, // Point P
        -0.8, -0.8, 0.8, // Point G
        0.0, -0.5, 1.3, // Point L
        0.8, -0.8, 0.8, // Point C
        0.5, -1.3, 0.0, // Point O
       ];

    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(verts), gl.STATIC_DRAW);

    // Color data
    let colorBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer);

    let faceColors = [
        [1.0, 0.0, 0.0, 1.0], // Face 1
        [0.0, 1.0, 0.0, 1.0], // Face 2
        [0.0, 0.0, 1.0, 1.0], // Face 3
        [1.0, 1.0, 0.0, 1.0], // Face 4
        [1.0, 0.0, 1.0, 1.0], // Face 5
        [0.0, 1.0, 1.0, 1.0], // Face 6
        [1.0, 1.0, 1.0, 1.0], // Face 7
        [0.5, 0.0, 0.0, 1.0], // Face 8
        [0.0, 0.5, 0.0, 1.0], // Face 9
        [0.0, 0.0, 0.5, 1.0], // Face 10
        [0.5, 1.0, 0.0, 1.0], // Face 11
        [0.0, 1.0, 0.5, 1.0]  // Face 12
    ];

    // Each vertex must have the color information, that is why the same color is concatenated 5 times, one for each vertex of each face.
    let vertexColors = [];
    faceColors.forEach(color =>{
        for (let j=0; j < 5; j++)
            vertexColors.push(...color); // ... -> Inserts each value from each subarray [1.0, 0.0, 0.0, 1.0] → 1.0, 0.0, 0.0, 1.0
    });

    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexColors), gl.STATIC_DRAW);

    // Index data (defines the triangles to be drawn).
    let dodecahedronIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, dodecahedronIndexBuffer);

    let dodecahedronIndices = [
        0,  1,  2,     0,  2,  3,    0,  3,  4,  // Face 1 (3 triangles)
        5,  6,  7,     5,  7,  8,    5,  8,  9,  // Face 2 (3 triangles)
        10, 11, 12,    10, 12, 13,   10, 13, 14, // Face 3 (3 triangles)
        15, 16, 17,    15, 17, 18,   15, 18, 19, // Face 4 (3 triangles)
        20, 21, 22,    20, 22, 23,   20, 23, 24, // Face 5 (3 triangles)
        25, 26, 27,    25, 27, 28,   25, 28, 29, // Face 6 (3 triangles)
        30, 31, 32,    30, 32, 33,   30, 33, 34, // Face 7 (3 triangles)
        35, 36, 37,    35, 37, 38,   35, 38, 39, // Face 8 (3 triangles)
        40, 41, 42,    40, 42, 43,   40, 43, 44, // Face 9 (3 triangles)
        45, 46, 47,    45, 47, 48,   45, 48, 49, // Face 10 (3 triangles)
        50, 51, 52,    50, 52, 53,   50, 53, 54, // Face 11 (3 triangles)
        55, 56, 57,    55, 57, 58,   55, 58, 59  // Face 12 (3 triangles)
    ];

    // gl.ELEMENT_ARRAY_BUFFER: Buffer used for element indices.
    // Uint16Array: Array of 16-bit unsigned integers.
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(dodecahedronIndices), gl.STATIC_DRAW);
    
    let dodecahedron = {
            buffer: vertexBuffer, colorBuffer:colorBuffer, indices:dodecahedronIndexBuffer,
            vertSize:3, nVerts:60, colorSize:4, nColors: 60, nIndices:108, // nVerts must be equal as the nColors. nIndices comes from cubeIndices
            primtype:gl.TRIANGLES, modelViewMatrix: mat4.create(), currentTime : Date.now()
        };

    mat4.translate(dodecahedron.modelViewMatrix, dodecahedron.modelViewMatrix, translation);

    dodecahedron.update = function()
    {
        let now = Date.now();
        let deltat = now - this.currentTime;
        this.currentTime = now;
        let fract = deltat / duration;
        let angle = Math.PI * 2 * fract;
    
        // Rotates a mat4 by the given angle
        // mat4 out the receiving matrix
        // mat4 a the matrix to rotate
        // Number rad the angle to rotate the matrix by
        // vec3 axis the axis to rotate around
        mat4.rotate(this.modelViewMatrix, this.modelViewMatrix, angle, rotationAxis);
    };
    
    return dodecahedron;
}

// Create the vertex, color and index data for a multi-colored octahedron
// Input parameters: canvas context for webgl, vector for translation, vector for rotation
// Output parameter: octahedron object
function createOctahedron(gl, translation, rotationAxis)
{    
    // Vertex Data
    let vertexBuffer;
    vertexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer);

    let verts = [
        // Face 1
        1.0,  0.0,  0.0,
        0.0, -1.0,  0.0,
        0.0,  0.0, 1.0,
        
        // Face 2
        1.0,  0.0,  0.0,
        0.0, -1.0,  0.0,
        0.0,  0.0, -1.0,

        // Face 3
        1.0,  0.0,  0.0,
        0.0,  1.0,  0.0,
        0.0,  0.0, -1.0,

        // Face 4
        1.0,  0.0,  0.0,
        0.0,  1.0,  0.0,
        0.0,  0.0,  1.0,

        // Face 5
        -1.0,  0.0,  0.0,
        0.0,  1.0,  0.0,
        0.0,  0.0,  1.0,

        // Face 6
        -1.0,  0.0,  0.0,
        0.0,  1.0,  0.0,
        0.0,  0.0,  -1.0,

        // Face 7
        -1.0,  0.0,  0.0,
        0.0,  -1.0,  0.0,
        0.0,  0.0,  1.0,

        // Face 8
        -1.0,  0.0,  0.0,
        0.0,  -1.0,  0.0,
        0.0,  0.0,  -1.0,
       ];

    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(verts), gl.STATIC_DRAW);

    // Color data
    let colorBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer);

    let faceColors = [
        [1.0, 0.0, 0.0, 1.0], // Face 1
        [0.0, 1.0, 0.0, 1.0], // Face 2
        [0.0, 0.0, 1.0, 1.0], // Face 3
        [1.0, 1.0, 0.0, 1.0], // Face 4
        [1.0, 0.0, 1.0, 1.0], // Face 5
        [0.0, 1.0, 1.0, 1.0], // Face 6
        [1.0, 1.0, 1.0, 1.0], // Face 7
        [0.5, 0.0, 0.0, 1.0]  // Face 8
    ];

    // Each vertex must have the color information, that is why the same color is concatenated 3 times, one for each vertex of each face.
    let vertexColors = [];
    faceColors.forEach(color =>{
        for (let j=0; j < 3; j++)
            vertexColors.push(...color); // ... -> Inserts each value from each subarray [1.0, 0.0, 0.0, 1.0] → 1.0, 0.0, 0.0, 1.0
    });

    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexColors), gl.STATIC_DRAW);

    // Index data (defines the triangles to be drawn).
    let octahedronIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, octahedronIndexBuffer);

    let octahedronIndices = [
        0,  1,  2,       // Face 1
        3,  4,  5,       // Face 2
        6,  7,  8,       // Face 3
        9,  10, 11,      // Face 4
        12, 13, 14,      // Face 5
        15, 16, 17,      // Face 6
        18, 19, 20,      // Face 7
        21, 22, 23       // Face 8
    ];

    // gl.ELEMENT_ARRAY_BUFFER: Buffer used for element indices.
    // Uint16Array: Array of 16-bit unsigned integers.
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(octahedronIndices), gl.STATIC_DRAW);
    
    let octahedron = {
            buffer: vertexBuffer, colorBuffer:colorBuffer, indices:octahedronIndexBuffer,
            vertSize:3, nVerts:24, colorSize:4, nColors: 24, nIndices:24, // nVerts must be equal as the nColors. nIndices comes from cubeIndices
            primtype:gl.TRIANGLES, modelViewMatrix: mat4.create(), currentTime : Date.now()
        };

    mat4.translate(octahedron.modelViewMatrix, octahedron.modelViewMatrix, translation);

    // Variables for controlling movement
    let bottom = true;
    let top = false;
    
    octahedron.update = function()
    {
        let now = Date.now();
        let deltat = now - this.currentTime;
        this.currentTime = now;
        let fract = deltat / duration;
        let angle = Math.PI * 2 * fract;

        // when the figure has not reached the top and it is going up
        if (bottom == true) {
            // Move figure with a speed of 0.025 upwards
            mat4.translate(this.modelViewMatrix, this.modelViewMatrix, [0, 0.025, 0]);

            // modelViewMatrix[13] is current position in y, taken from the model view matrix
            // When the current posiion of y is higher of 1.5, the top was reached and change the boolean variables
            if(this.modelViewMatrix[13] > 1.5) {
                bottom = false;
                top = true;
            }
        }

        // when the figure has not reached the bottom and it is going down
        if (top == true) {
            // Move figure with a speed of 0.025 downwards
            mat4.translate(this.modelViewMatrix, this.modelViewMatrix, [0, -0.025, 0]);

            // modelViewMatrix[13] is current position in y, taken from the model view matrix
            // When the current posiion of y is lower of -1.5, the bottom was reached and change the boolean variables            
            if(this.modelViewMatrix[13] < -1.5) {
                bottom = true;
                top = false;
            }
        }
    
        // Rotates a mat4 by the given angle
        // mat4 out the receiving matrix
        // mat4 a the matrix to rotate
        // Number rad the angle to rotate the matrix by
        // vec3 axis the axis to rotate around
        mat4.rotate(this.modelViewMatrix, this.modelViewMatrix, angle, rotationAxis);
    };
    
    return octahedron;
}

// Function for creating shaders, based on the ones already defined on the top of the code (vertexShaderSource, fragmentShaderSource)
// Input parameters: canvas context for webgl, string with code for shader, type of shader
// Output parameter: shader
function createShader(gl, str, type)
{
    let shader;

    // Checking the type of shader
    if (type == "fragment") {
        shader = gl.createShader(gl.FRAGMENT_SHADER);
    } else if (type == "vertex") {
        shader = gl.createShader(gl.VERTEX_SHADER);
    } else {
        return null;
    }

    gl.shaderSource(shader, str);
    gl.compileShader(shader);

    if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
        alert(gl.getShaderInfoLog(shader));
        return null;
    }

    return shader;
}

// Function for initializing both shaders
// Input parameter: canvas context for webgl
// Output parameter: none
function initShader(gl)
{
    // load and compile the fragment and vertex shader
    let fragmentShader = createShader(gl, fragmentShaderSource, "fragment");
    let vertexShader = createShader(gl, vertexShaderSource, "vertex");

    // link them together into a new program
    shaderProgram = gl.createProgram();
    gl.attachShader(shaderProgram, vertexShader);
    gl.attachShader(shaderProgram, fragmentShader);
    gl.linkProgram(shaderProgram);

    // get pointers to the shader params
    shaderVertexPositionAttribute = gl.getAttribLocation(shaderProgram, "vertexPos");
    gl.enableVertexAttribArray(shaderVertexPositionAttribute);

    shaderVertexColorAttribute = gl.getAttribLocation(shaderProgram, "vertexColor");
    gl.enableVertexAttribArray(shaderVertexColorAttribute);
    
    shaderProjectionMatrixUniform = gl.getUniformLocation(shaderProgram, "projectionMatrix");
    shaderModelViewMatrixUniform = gl.getUniformLocation(shaderProgram, "modelViewMatrix");

    if (!gl.getProgramParameter(shaderProgram, gl.LINK_STATUS)) {
        alert("Could not initialise shaders");
    }
}

// Function for drawing all of the objects
// Input parameters: canvas context for webgl, array with objects
// Output parameter: none
function draw(gl, objs) 
{
    // clear the background (with black)
    gl.clearColor(0.1, 0.1, 0.1, 1.0);
    gl.enable(gl.DEPTH_TEST);
    gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
    
    // set the shader to use
    gl.useProgram(shaderProgram);

    // For drawing each of the objects
    for(i = 0; i< objs.length; i++)
    {
        obj = objs[i];
        // connect up the shader parameters: vertex position, color and projection/model matrices
        // set up the buffers
        gl.bindBuffer(gl.ARRAY_BUFFER, obj.buffer);
        gl.vertexAttribPointer(shaderVertexPositionAttribute, obj.vertSize, gl.FLOAT, false, 0, 0);

        gl.bindBuffer(gl.ARRAY_BUFFER, obj.colorBuffer);
        gl.vertexAttribPointer(shaderVertexColorAttribute, obj.colorSize, gl.FLOAT, false, 0, 0);
        
        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, obj.indices);

        gl.uniformMatrix4fv(shaderProjectionMatrixUniform, false, projectionMatrix);
        gl.uniformMatrix4fv(shaderModelViewMatrixUniform, false, obj.modelViewMatrix);

        // Draw the object's primitives using indexed buffer information.
        // void gl.drawElements(mode, count, type, offset);
        // mode: A GLenum specifying the type primitive to render.
        // count: A GLsizei specifying the number of elements to be rendered.
        // type: A GLenum specifying the type of the values in the element array buffer.
        // offset: A GLintptr specifying an offset in the element array buffer.
        gl.drawElements(obj.primtype, obj.nIndices, gl.UNSIGNED_SHORT, 0);
    }
}

// Function for running the animation, by calling this function in a recursive way
// Input parameters: canvas context for webgl, array with objects
// Output parameter: none
function run(gl, objs) 
{
    // The window.requestAnimationFrame() method tells the browser that you wish to perform an animation and requests that the browser call a specified function to update an animation before the next repaint. The method takes a callback as an argument to be invoked before the repaint.
    requestAnimationFrame(function() { run(gl, objs); });

    draw(gl, objs);

    for(i = 0; i<objs.length; i++)
        objs[i].update();
}