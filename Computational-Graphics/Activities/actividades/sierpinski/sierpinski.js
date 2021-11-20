class punto
{
    constructor(x, y)
    {
        this.x = x;
        this.y = y;
    }
}

function main()
{
    const canvas = document.getElementById('sierpinskiCanvas');
    const context = canvas.getContext('2d');
    
	let pointLine = canvas.height * 2 / 3;
	let length = pointLine / Math.sin(60 * Math.PI / 180);

	let punto1 = new punto(canvas.width/2, (canvas.height - pointLine)/2);
	let punto2 = new punto((canvas.width - length)/2, punto1.y + pointLine);
	let punto3 = new punto(punto2.x + length, punto2.y);

    // draw the first triangle
    drawTriangle(context, punto1, punto2, punto3, "red");

    // when slider event
    document.getElementById("slider").oninput = function(event) {
        document.getElementById("sliderValue").innerHTML = "Value: " + event.target.value;
        // clear canvas
        context.clearRect(0, 0, canvas.width, canvas.height);
        // draw the first triangle
        drawTriangle(context, punto1, punto2, punto3, "red");
        // call recursive function
        sierpinskiRecursive(context, punto1, punto2, punto3, 1, event.target.value);

    };	
}

function drawTriangle(context, punto1, punto2, punto3, color) {
    context.save();
    context.fillStyle = color;
	context.beginPath();
	context.moveTo(punto1.x, punto1.y);
	context.lineTo(punto2.x, punto2.y);
	context.lineTo(punto3.x, punto3.y);
    context.fill();
    context.stroke();
	context.restore();
}

function sierpinskiRecursive(context, punto1, punto2, punto3, cont, steps) {
    if (cont > steps) 
        return;
    
	cont++;
	var puntoMedio1 = puntoMedio(punto1, punto2);
	var puntoMedio2 = puntoMedio(punto2, punto3);
	var puntoMedio3 = puntoMedio(punto1, punto3);
	drawTriangle(context, puntoMedio1, puntoMedio2, puntoMedio3, "white");
	
	sierpinskiRecursive(context, punto1, puntoMedio1, puntoMedio3, cont, steps);
	sierpinskiRecursive(context, puntoMedio1, punto2, puntoMedio2, cont, steps);
	sierpinskiRecursive(context, puntoMedio3, puntoMedio2, punto3, cont, steps);
	cont--;
}

function puntoMedio(punto1, punto2) {
	return new punto ( (punto1.x + punto2.x)/2, (punto1.y + punto2.y)/2 );
}