/*
	Interprocess Communication: Shared Memory
	Operating Systems
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <sys/mman.h> //mmaap
#include <string.h> //memcpy
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

int main() {
	char *parent = "hello world";
	void *shared = mmap(NULL, 200, PROT_READ | PROT_WRITE, MAP_ANONYMOUS | MAP_SHARED, 0, 0);
	//void *shared = malloc(200);
	memcpy(shared, parent, strlen(parent)+1); //+1 -> TOMA EL FINAL DEL TEXTO
	/*memcpy(shared, parent, sizeof(parent)); -> sizeof dice el tamaño del 	
	espacio de memoria y no el tamaño de lo que hay adentro, como strlen*/
	printf("En memoria tenemos: %s\n", (char *)shared);
	int child_id = fork();
	if(child_id>0){
		wait(NULL);
		printf("En memoria tenemos despues de dormir: %s\n", (char *)shared);
	}else{
		char *child = "goodbye";
		memcpy(shared, child, strlen(child)+1);
		printf("En memoria del hijo tenemos: %s\n", (char *)shared);
		exit(5);
	}
	
	return 0;
}
