let keysDown = {
    'w': false,
    's': false,
    'ArrowUp': false,
    'ArrowDown': false
};

class barra
{
    constructor(x, y, width, height, keyUp, keyDown, speed=2)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.keyUp = keyUp;
        this.keyDown = keyDown;
    }

    moveUp() 
    {
        // for respecting upper boundaries, check if y is not negative
        if (this.y > 0) {
            this.y -= this.speed;
        }
    }

    moveDown() 
    {
        // for respecting lower boundaries, check if y is not higher than canvas height - bar height
        if (this.y < (300 - this.height)) {
            this.y += this.speed;
        }
    }

    draw(context)
    {
        context.fillStyle = 'white';
        context.fillRect(this.x, this.y, this.width, this.height);
    }

    update()
    {
        if (keysDown[this.keyUp]) {
            this.moveUp();
        }

        if (keysDown[this.keyDown]) {
            this.moveDown();
        }
    }
}

class pelota
{
    constructor(x, y, radius, speed=1)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;

        this.up = true;
        this.right = true;
    }

    draw(context)
    {
        context.fillStyle = 'white';
        context.beginPath();
        context.arc(this.x, this.y, this.radius, 0, Math.PI * 2);
        context.closePath();
        context.fill();
    }

    update(up, down, left, right, barras)
    {
        let topBall = this.y - this.radius;
        let rightBall = this.x + this.radius;
        let bottomBall = this.y + this.radius;
        let leftBall = this.x - this.radius;

        // for diagonal movement
        if(this.up)
            this.y -= this.speed;
        else    
            this.y += this.speed;
        if(this.right)
            this.x += this.speed;
        else
            this.x -= this.speed;
        
        // if reaches to top, go down
        if(topBall <= up) 
            this.up = false;

        // if reaches to bottom, go up
        if(bottomBall >= down) 
            this.up = true;
    
        // if reaches to right wall, go left
        if(rightBall >= right) 
            this.right = false;

        // if reaches to left wall, go right
        if(leftBall <= left) 
            this.right = true;

        // if ball hits left bar, go right
        if (leftBall < (barras[0].x + barras[0].width) && topBall < (barras[0].y + barras[0].height) && rightBall > barras[0].x && bottomBall > barras[0].y)
            this.right = true;

        // if ball hits right bar, go left
        if (leftBall < (barras[1].x + barras[1].width) && topBall < (barras[1].y + barras[1].height) && rightBall > barras[1].x && bottomBall > barras[1].y)
            this.right = false;
    
    }
}

function update(canvas, context, barras, bola)
{
    requestAnimationFrame(()=>update(canvas, context, barras, bola));

    context.clearRect(0,0, canvas.width, canvas.height);
    
    barras.forEach(barra =>{
        barra.draw(context);
        barra.update();
    });

    bola.draw(context);

    bola.update(0, canvas.height, 0, canvas.width, barras);
}

function main()
{
    const canvas = document.getElementById("pongCanvas");
    const context = canvas.getContext("2d");

    let barraIzq = new barra(10, 120, 20, 60, 'w', 's');
    let barraDer = new barra(570, 120, 20, 60, 'ArrowUp', 'ArrowDown');
    let bola = new pelota(canvas.width/2, canvas.height/2, 10);

    let barras = [];

    barras.push(barraIzq, barraDer);

    document.addEventListener("keydown", event=> {
        keysDown[event.key] = true;      
        
    });

    document.addEventListener("keyup", event=> { 
        keysDown[event.key] = false;

    });

    update(canvas, context, barras, bola);
}