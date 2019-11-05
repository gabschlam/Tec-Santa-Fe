/*
	Synchronization: Mutex
	Operating Systems
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

pthread_mutex_t lock;

void *function(void *id){
	while(1){
		int *intId=(int *)id;//indicates the assigned id by main
		sleep(1);
		pthread_mutex_lock(&lock);//Acquire lock
		printf("Entering critical section of %d\n", *intId);
		sleep(3);
		printf("Exiting critical section of %d\n", *intId);
		pthread_mutex_unlock(&lock);//Leave the lock for other thread to grab
		//By indicating it is not ready, it allows the other process to enter the critical section
	}
}

int main(){
	pthread_mutex_init(&lock, NULL);
	pthread_t tid1, tid2;//Creation of two threads
	int i=0;
	int j=1;
	//Execution of threads
	pthread_create(&tid1, NULL, function, (void *)&i);
	pthread_create(&tid2, NULL, function, (void *)&j);
	//Waiting for threads to finish
	pthread_join(tid1, NULL);
	pthread_join(tid2, NULL);
	pthread_mutex_destroy(&lock);
}
