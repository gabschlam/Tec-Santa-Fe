let data = [
    ["Juan", "Pérez", "González", "jperez@gmail.com", "img/persona1.jpg", "5540302010"],
    ["Manuel", "Ramírez", "López", "mram@gmail.com", "img/persona2.jpg", "5560708090"],
    ["Gabriel", "Marquez", "Díaz", "gmarq@gmail.com", "img/persona3.jpg", "5540404040"],
    ["María", "De Los Ángeles", "Pérez", "mangeles@gmail.com", "img/persona4.jpg", "5550502010"],
    ["Juana", "González", "Pérez", "juana123@gmail.com", "img/persona5.jpg", "5540602010"],
    ["Dolores", "Reyes", "González", "dolrey@gmail.com", "img/persona6.jpg", "5540305010"],
    ["Zacarías", "Flores", "Del Campo", "zflores@gmail.com", "img/persona7.jpg", "5540402040"],
    ["Elsa", "Pato", "Blanco", "elsapato@gmail.com", "img/persona8.jpg", "5550305010"],
    ["Elvis", "Tek", "González", "elvistek@gmail.com", "img/persona9.jpg", "5540404040"],
    ["Marla", "Rojo", "Morse", "marcrojo@gmail.com", "img/persona10.jpg", "5540305060"]
];
var fieldName = document.getElementsByClassName('name');
var fieldLname = document.getElementsByClassName('lname');
var fieldLname2 = document.getElementsByClassName('lname2');
var fieldEmail = document.getElementsByClassName('email');
var fieldTel = document.getElementsByClassName('tel');
var row = document.getElementsByClassName('Cell');
var index = 0;
// Function for sorting data by email, for display
function sortByEmail(a, b) {
    a = a[3];
    b = b[3];
    return a == b ? 0 : (a < b ? -1 : 1);
}
// Function for displaying data in html table
function getData() {
    var data_sort = data.sort(sortByEmail);
    for (let i = 0; i < data_sort.length; i++) {
        var imageID = 'image' + (i + 1);
        var fieldImg = document.getElementById(imageID);
        fieldName[i].innerHTML = data_sort[i][0];
        fieldLname[i].innerHTML = data_sort[i][1];
        fieldLname2[i].innerHTML = data_sort[i][2];
        fieldEmail[i].innerHTML = data_sort[i][3];
        fieldImg.src = data_sort[i][4];
        fieldTel[i].innerHTML = data_sort[i][5];
    }
}
// Function for search person by email, from text inserted in html
function searchPerson() {
    resetColorRow(index); // Reset highlight from previous search
    var email = document.getElementById("email").value;
    // If text is not empty
    if (email != "") {
        var emails_sort = data.sort(sortByEmail);
        index = binarySearch(email, emails_sort, 3);
        if (index != -1) {
            alert("Lo encontramos!");
            highlightRow(index);
        }
        else {
            alert("Lo siento!");
        }
        document.getElementById("email").value = "";
    }
}
// Function for highlight row after search
function highlightRow(index) {
    var size = data[0].length;
    for (let i = 0; i < size; i++) {
        row[((index + 1) * size) + i].style.background = 'lightgreen';
    }
}
// Function for reset color from row after search
function resetColorRow(index) {
    var size = data[0].length;
    for (let i = 0; i < size; i++) {
        row[((index + 1) * size) + i].style.background = '';
    }
}
// Function for binary search for email
function binarySearch(value, array, posEmail) {
    let start = 0;
    let end = array.length - 1;
    let position = -1;
    let middle;
    // Iterate while start do not meet end 
    while (start <= end) {
        middle = Math.floor((start + end) / 2);
        // If element is in the middle, return position 
        if (array[middle][posEmail] == value) {
            position = middle;
            return position;
        }
        // Search in the right half 
        else if (array[middle][posEmail] > value) {
            end = middle - 1;
        }
        // Search in the left half 
        else {
            start = middle + 1;
        }
    }
    return position;
}
// Function to convert to Morse Code
function convertToMorseCode() {
    var alphabet = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'];
    var morseAlphabet = [".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "_", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----"];
    var textToConvert = document.getElementById("text").value;
    // Time between each digit, from html
    var time_digit = document.getElementById("t_digit").value;
    // Time between each character, from html
    var time_character = document.getElementById("t_character").value;
    // Time between each word, from html
    var time_word = document.getElementById("t_word").value;
    var morseText = "";
    var space = false;
    // Go through text to convert and compare it with alphabet, and substitute it for morse alphabet
    for (let i = 0; i < textToConvert.length; i++) {
        for (let j = 0; j < alphabet.length; j++) {
            // If letter matches letter from alphabet
            if (textToConvert[i].toLowerCase() == alphabet[j]) {
                morseText += morseAlphabet[j]; // Add corresponding letter from morse alphabet
                morseText += "\xa0"; // Add space between character
                break;
            }
            // If letter matches space
            else if (textToConvert[i].toLowerCase() == " " && space == false) {
                morseText = morseText.replace(/\s/g, ''); // Remove space from before
                morseText += "*"; // Add character for knowing space between words (* is arbitrary)
                space = true; // For replacing just one space in each iteration
            }
        }
        space = false;
    }
    console.log("Morse Text: " + morseText);
    var light = document.getElementById("light"); // Light for short stimulus
    var light2 = document.getElementById("light2"); // Light for long stimulus
    var time; // Time for each promise
    // Loop for each promise, depending on morse text length
    for (let i = 0, p = Promise.resolve(); i < morseText.length; i++) {
        time = 0;
        p = p.then(_ => new Promise(resolve => setTimeout(function () {
            var char = morseText.charAt(i);
            console.log(char);
            // If character is short stimulus
            if (char == '.') {
                //console.log("green")
                changeColor(light, 'lightgreen');
                time = +time_digit; // Set time for this promise the time between each digit (+ before is to cast it to number)
            }
            // If character is long stimulus
            else if (char == '-') {
                //console.log("red")
                changeColor(light2, 'red');
                time = +time_digit; // Set time for this promise the time between each digit (+ before is to cast it to number)
            }
            // If character is space (*) between words
            else if (char == '*') {
                //console.log("space")
                time = +time_word; // Set time for this promise the time between each word (+ before is to cast it to number)
            }
            // If character is space between character
            else {
                //console.log("character")
                time = +time_character; // Set time for this promise the time between each character (+ before is to cast it to number)
            }
            console.log("Time: " + time);
            resolve();
        }, time / 2) // time/2 for dividing time between on and off
        ));
        p = p.then(_ => new Promise(resolve => setTimeout(function () {
            var char = morseText.charAt(i);
            if (char == '.') {
                //console.log("off")
                light.style.background = ''; // Reset background
            }
            else if (char == '-') {
                //console.log("off2")
                light2.style.background = ''; // Reset background
            }
            resolve();
        }, time / 2) // time/2 for dividing time between on and off
        ));
    }
}
// Function for changing color to respective field
function changeColor(field, color) {
    field.style.background = color;
}
