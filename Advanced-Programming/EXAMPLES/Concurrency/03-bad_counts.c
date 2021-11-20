/*
	Multiple threads working in the same variable
	Advanced Programming
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define NUM_TRHEADS 3
#define MAX_COUNT 1000000

int counter = 0;

// Function declaration
void * increment(void * arg);

int main()
{
	pthread_t tid[NUM_TRHEADS];

	// Create a new thread with the function as a starting point
	for (int i = 0; i < NUM_TRHEADS; i++)
	{
		pthread_create(&tid[i], NULL, increment, NULL);
	}

	printf("This is the main thread\n");

	// Wait for all threads to finish, and get the values they return
	for (int i = 0; i < NUM_TRHEADS; i++)
	{
		pthread_join(tid[i], NULL);
	}

	printf("The counter is %d / %d\n", counter, NUM_TRHEADS * MAX_COUNT);

	pthread_exit(NULL);

	//return 0;
}

void * increment(void * arg)
{
	printf("Thread started\n");

	for (int i = 0; i < MAX_COUNT; i++)
	{
		counter++;
	}

	pthread_exit(NULL);
}


