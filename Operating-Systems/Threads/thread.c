/*
	Threads: Example of threads
	Operating Systems
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <pthread.h>

int a = 1;

void *print_message(void *mes){
	char *message;
	message = (char *)mes;
	a++;
	printf("%s\n", message);
}

int main(){
	pthread_t tid; //tid -> thread id
	char *message = "Thread 1";
	printf("El valor de a antes del thread es: %d\n", a);
	pthread_create(&tid, NULL, print_message, (void *)message);
	printf("Creado thread con tid %ld\n", tid);
	printf("El valor de a despues del thread es: %d\n", a);
	pthread_join(tid, NULL);
	
	return 0;
}
