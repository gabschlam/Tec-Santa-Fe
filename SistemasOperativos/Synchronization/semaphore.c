/*
	Synchronization: Semaphores
	Operating Systems
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <semaphore.h>

sem_t s;

void *producer(void *id){
	sem_post(&s);
}

void *consumer(void *id){
	sem_wait(&s);
}

void *function(void *id){
	while(1){
		int *intId=(int *)id;//indicates the assigned id by main
		sleep(1);
		sem_wait(&s);
		printf("Entering critical section of %d\n", *intId);
		sleep(3);
		printf("Exiting critical section of %d\n", *intId);
		sem_post(&s);
		//By indicating it is not ready, it allows the other process to enter the critical section
	}
}

int main(){
	sem_init(&s, 0, 1);
	pthread_t tid1, tid2;//Creation of two threads
	int i=0;
	int j=1;
	//Execution of threads
	pthread_create(&tid1, NULL, function, (void *)&i); //With producer we can stop using function
	pthread_create(&tid2, NULL, function, (void *)&j); //With consumer we can stop using function
	//Waiting for threads to finish
	pthread_join(tid1, NULL);
	pthread_join(tid2, NULL);
	sem_destroy(&s);
}
