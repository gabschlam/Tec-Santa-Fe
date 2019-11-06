/*
	BFS - Breadth First Search
	Análisis y diseño de algoritmos

	Gabriel Schlam
	Basado en: https://www.programiz.com/dsa/graph-bfs
*/

#include <stdio.h>
#include <stdlib.h>
#define MAX_TAM 40

struct queue {
	int items[MAX_TAM];
	int siguiente;
	int anterior;
};

struct queue* createQueue(void);
void enqueue(struct queue* q, int);
int dequeue(struct queue* q);
void mostrar(struct queue* q);
int isEmpty(struct queue* q);
void printQueue(struct queue* q);

struct nodo {
	int vertice;
	struct nodo* next;
};

struct nodo* crearNodo(int);

struct Grafo {
	int numVertices;
	struct nodo** adjLists;
	int* visitado;
};

struct Grafo* crearGrafo(int vertices);
void nuevoVertice(struct Grafo* Grafo, int origen, int dest);
void printGrafo(struct Grafo* Grafo);
void bfs(struct Grafo* Grafo, int raiz);

void bfs(struct Grafo* Grafo, int raiz) {
	struct queue* q = createQueue();
	
	Grafo->visitado[raiz] = 1;
	enqueue(q, raiz);
	
	while(!isEmpty(q)) {
		printQueue(q);
		int verticeActual = dequeue(q);
		printf("Visitado %d\n", verticeActual);
		
		struct nodo* temp = Grafo->adjLists[verticeActual];
		
		while(temp) {
			int adjvertice = temp->vertice;
			
			if(Grafo->visitado[adjvertice] == 0){
				Grafo->visitado[adjvertice] = 1;
				enqueue(q, adjvertice);
			}
			temp = temp->next;
		}
	}
}

struct nodo* crearNodo(int v) {
	struct nodo* nuevoNodo = malloc(sizeof(struct nodo));
	nuevoNodo->vertice = v;
	nuevoNodo->next = NULL;
	return nuevoNodo;
}

struct Grafo* crearGrafo(int vertices) {
	struct Grafo* Grafo = malloc(sizeof(struct Grafo));
	Grafo->numVertices = vertices;
	
	Grafo->adjLists = malloc(vertices * sizeof(struct nodo*));
	Grafo->visitado = malloc(vertices * sizeof(int));
	
	for (int i = 0; i < vertices; i++) {
		Grafo->adjLists[i] = NULL;
		Grafo->visitado[i] = 0;
	}
	
	return Grafo;
}

void nuevoVertice(struct Grafo* Grafo, int origen, int dest) {
	// Nuevo vertice de origen a dest
	struct nodo* nuevoNodo = crearNodo(dest);
	nuevoNodo->next = Grafo->adjLists[origen];
	Grafo->adjLists[origen] = nuevoNodo;
	
	// Nuevo vertice de dest a origen
	nuevoNodo = crearNodo(origen);
	nuevoNodo->next = Grafo->adjLists[dest];
	Grafo->adjLists[dest] = nuevoNodo;
}

struct queue* createQueue() {
	struct queue* q = malloc(sizeof(struct queue));
	q->siguiente = -1;
	q->anterior = -1;
	return q;
}

int isEmpty(struct queue* q) {
	if(q->anterior == -1)
		return 1;
	else
		return 0;
}

void enqueue(struct queue* q, int value){
	if(q->siguiente == -1)
		q->siguiente = 0;
	q->anterior++;
	q->items[q->anterior] = value;
}

int dequeue(struct queue* q){
	int item;
	if(isEmpty(q)){
		printf("Cola vacia");
		item = -1;
	}
	else{
		item = q->items[q->siguiente];
		q->siguiente++;
		if(q->siguiente > q->anterior){
			printf("Reiniciando cola. ");
			q->siguiente = q->anterior = -1;
		}
	}
	return item;
}

void printQueue(struct queue *q) {
	int i = q->siguiente;
	
	if(isEmpty(q)) {
		printf("Cola vacia");
	} else {
		printf("En cola: ");
		for(i = q->siguiente; i < q->anterior + 1; i++) {
			printf("%d ", q->items[i]);
		}
	}
}

int main() {
	int cant, n, raiz;
	int vert;
	char *c;
	
	printf("Dame la cantidad de parejas a introducir \n");
	scanf("%d", &cant);
	
	n = (cant*6)+1;
	
	char string[n];
	
	printf("Dame el numero de vertices \n");
	scanf("%d", &vert);
	getc(stdin);
	
	struct Grafo* Grafo = crearGrafo(vert);
	
	fgets(string, n, stdin);

	c = &string[0];
	
	raiz = atoi(c);
	
	for(int i = 0; i< sizeof(string); i++){
		if(string[i] == '('){
			char *c1, *c2;
			c1 = &string[i+1];
			c2 = &string[i+3];
			int origen = atoi(c1);
			int destino = atoi(c2);
			nuevoVertice(Grafo, origen, destino);
		}
	}
	
	bfs(Grafo, raiz);
	
	return 0;
}
