/*
	Synchronization: Example of Race Condition
	Operating Systems
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <pthread.h>
const int false=0;
int ready[2]={0,0};//indicates if threads i and j(0, and 1) are ready to enter the critical section
int turn=0;// indicates the turn of the process 
const int true=1;

void *function(void *id){
	int *intId=(int *)id;//indicates the assigned id by main
	while(1){
		ready[*intId]=true; //Says this thread is ready to enter the critical section
		if(*intId==0){//If i am thread with id 0
			turn=1;//give the turn to the other thread
			printf("%d Waiting to enter critical section\n", *intId);
			while(ready[1]==true && turn==1){ 
			//If the other thread (1) is ready, and its the turn of the other thread
			//Then that thread is in its critical section, and i have to wait before i enter mine
			}
		}else{
			turn=0;
			printf("%d Waiting to enter critical section\n", *intId);
			while(ready[0]==true && turn==0){
			}
		}
		printf("Entering critical section of %d\n", *intId);
		sleep(10);
		printf("Exiting critical section of %d\n", *intId);
		ready[*intId]=false;//This thread finished its critical section
		//By indicating it is not ready, it allows the other process to enter the critical section
	}
}

int main(){
	pthread_t tid1, tid2;//Creation of two threads
	int i=0;
	int j=1;
	//Execution of threads
	pthread_create(&tid1, NULL, function, (void *)&i);
	pthread_create(&tid2, NULL, function, (void *)&j);
	//Waiting for threads to finish
	pthread_join(tid1, NULL);
	pthread_join(tid2, NULL);
}
