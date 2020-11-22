/*
	Árboles Binarios
	Análisis y diseño de algoritmos

	Gabriel Schlam
	Basado en: https://www.tutorialesprogramacionya.com/cya/detalleconcepto.php?punto=52&codigo=52&inicio=45
*/

#include <stdio.h>
#include <stdlib.h>

struct nodo {
	int dato;
	struct nodo *izq;
	struct nodo *der;
};

struct nodo *raiz=NULL;

void insertar(int num) {
	struct nodo *nuevo;
	nuevo = malloc(sizeof(struct nodo));
	nuevo->dato = num;
	nuevo->izq = NULL;
	nuevo->der = NULL;
	if (raiz == NULL)
		raiz = nuevo;
	else {
		struct nodo *ant, *reco;
		ant = NULL;
		reco = raiz;
		while (reco != NULL) {
			ant = reco;
			if (num < reco->dato)
				reco = reco->izq;
			else
				reco = reco->der;
		}
		if (num < ant->dato)
			ant->izq = nuevo;
		else
			ant->der = nuevo;
	}
}
void printNivelActual(struct nodo *raiz, int nivel) {
	if (raiz == NULL)
		return;
	if (nivel == 1)
		printf("%d ", raiz->dato);
	else if (nivel > 1) {
		printNivelActual(raiz->izq, nivel-1);
		printNivelActual(raiz->der, nivel-1);
	}
}

int numNiveles(struct nodo *raiz) {
	if (raiz == NULL)
		return 0;
	else {
		int lheight = numNiveles(raiz->izq);
		int rheight = numNiveles(raiz->der);
		
		if (lheight > rheight)
			return (lheight+1);
		else
			return (rheight+1);
	}
}

void printNivel(struct nodo *raiz) {
	printf("Imprimiendo arbol: \n");
	int h = numNiveles(raiz);
	for (int i = 1; i <= h; i++) {
		printNivelActual(raiz, i);
		printf("\n");
	}
}

int main() {
	int n;
	
	printf("Dame la cantidad de valores a introducir \n");
	scanf("%d", &n);
	
	int a[n];
	
	scanf("%d", &a[0]);
	
	for (int i = 1; i<n; i++) {
		scanf(",%d",&a[i]);
	}
	
	for (int j = 0; j<n; j++) {
		insertar(a[j]);
	}
	
	printNivel(raiz);
	
	return 0;
}
