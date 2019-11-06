/*
	Ejemplo de cálculo de Fibonacci
	Análisis y diseño de algoritmos

	Gabriel Schlam
*/

#include <iostream>
using namespace std; // PARA NO PONER std:: EN CADA INSTRUCCION DE PRINT Y RECIBIR DE USUARIO

int fib(int n){
	if (n<2)
		return n;
	else
		return fib(n-1) + fib(n-2);
}

long fibED(int n){
	long F[n+1];
	F[0] = 0;
	F[1] = 1;
	for (int i = 2; i <= n; i++) {
		F[i] = F[i-1] + F[i-2];
	}
	return F[n];
}

int main() {
	int n = 0;
	cout << "Dame el valor de n ";
	cin >> n;
	//cout << "El fibonacci es: " << fib(n) << endl;
	cout << "El fibonacci es: " << fibED(n) << endl; // ASI SE TARDA MUCHISIMO MENOS
	return 0;
}
