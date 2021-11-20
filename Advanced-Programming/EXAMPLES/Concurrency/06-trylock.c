/*
	Multiple threads working on two variables
	Using trylock on mutexes to avoid a deadlock
	Each variable has a mutex
	
	Advanced Programming
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define NUM_TRHEADS 2
#define MAX_COUNT 1000000

pthread_mutex_t mutex_1 = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_2 = PTHREAD_MUTEX_INITIALIZER;

int counter_1 = 0;
int counter_2 = MAX_COUNT;

// Function declaration
void * increment(void * arg);
void * decrement(void * arg);

int main()
{
	pthread_t tid[NUM_TRHEADS];

	// Create a new thread with the function as a starting point
	pthread_create(&tid[0], NULL, increment, NULL);
	pthread_create(&tid[1], NULL, decrement, NULL);

	printf("This is the main thread\n");

	// Wait for all threads to finish, and get the values they return
	for (int i = 0; i < NUM_TRHEADS; i++)
	{
		pthread_join(tid[i], NULL);
	}

	printf("The counter is %d / %d\n", counter_1, counter_2);

	pthread_exit(NULL);

	//return 0;
}

void * increment(void * arg)
{
	printf("Increment Thread started\n");

	for (int i = 0; i < MAX_COUNT; i++)
	{
		// Critical section
		pthread_mutex_lock(&mutex_1);

		// Loop while the mutex is locked by another thread
		while(pthread_mutex_trylock(&mutex_2))
		{
			pthread_mutex_unlock(&mutex_1);
			pthread_mutex_lock(&mutex_1);
		}
		counter_1++;
		counter_2--;
		
		pthread_mutex_unlock(&mutex_1);
		pthread_mutex_unlock(&mutex_2);
	}

	pthread_exit(NULL);
}

void * decrement(void * arg)
{
	printf("Decrement Thread started\n");

	for (int i = 0; i < MAX_COUNT; i++)
	{
		// Critical section
		pthread_mutex_lock(&mutex_2);

		// Loop while the mutex is locked by another thread
		while(pthread_mutex_trylock(&mutex_1))
		{
			pthread_mutex_unlock(&mutex_2);
			pthread_mutex_lock(&mutex_2);
		}
			counter_2--;
			counter_1++;
		pthread_mutex_unlock(&mutex_2);
		pthread_mutex_unlock(&mutex_1);
	}

	pthread_exit(NULL);
}


