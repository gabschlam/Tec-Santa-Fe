//setTimeout(()=>console.log("Primero"),1000);
//setTimeout(()=>console.log("Segundo"),500);
//console.log("Tercero");
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
function callback() {
    console.log("Primer callback");
    setTimeout(callback2, 3000);
}
function callback2() {
    console.log("Segundo callback");
}
setTimeout(callback, 1000);
function funcionAsincrona() {
    return __awaiter(this, void 0, void 0, function* () {
        let promesa = new Promise((resolve, reject) => {
            setTimeout(() => {
                console.log("primera ejecucion en la promesa");
                if (false) {
                    reject("error");
                }
                else {
                    resolve("exito");
                }
            }, 1000);
        });
        let resultado = yield promesa;
        console.log("resultado " + resultado);
        return resultado;
    });
}
console.log(funcionAsincrona());
function ejecucion() {
    var entrada = +document.getElementById("entrada1").value;
    //alert("hola: "+ entrada);
    setTimeout(() => {
        document.getElementById("titulo1").style.color = "blue";
        document.getElementById("titulo1").innerText = "Ya termino la clase";
        document.getElementById("titulo1").style.fontSize = "50px";
        document.getElementById("titulo1").className = "claseNuevaCSS";
    }, entrada);
}
