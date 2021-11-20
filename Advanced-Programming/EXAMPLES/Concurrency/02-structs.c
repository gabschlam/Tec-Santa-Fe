/*
	Second example of using threads in C
	Passing structures
	Advanced Programming
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

// Function declaration
void * threadStart(void * arg);

typedef struct data_struct {
	int num;
	int square;
	int cube;
} data_t;

int main()
{
	int num_threads;
	pthread_t * tid = NULL;
	data_t * thread_data = NULL;

	printf("Number of threads to run: ");
	scanf("%d", &num_threads);

	// Dynamic allocation of the array for the thread indices
	thread_data = malloc(num_threads * sizeof(data_t));
	tid = malloc(num_threads * sizeof(pthread_t));

	// Create a new thread with the function as a starting point
	for (int i = 0; i < num_threads; i++)
	{
		thread_data[i].num = i + 1;
		pthread_create(&tid[i], NULL, threadStart, &thread_data[i]);
		//printf("Created a new thread with id: %ld\n", tid);
	}

	printf("This is the main thread\n");

	// Wait for all threads to finish, and get the values they return
	for (int i = 0; i < num_threads; i++)
	{
		pthread_join(tid[i], NULL);
		printf("Thread %d returned: %d %d %d\n", i+1, thread_data[i].num, thread_data[i].square, thread_data[i].cube);
	}

	free(thread_data);
	free(tid);

	pthread_exit(NULL);

	//return 0;
}

void * threadStart(void * arg)
{
	data_t * my_data = (data_t *) arg;
	int num = my_data->num;

	my_data->square = num * num;
	my_data->cube = num * num * num;

	printf("This is the new thread: %d\n", num);

	pthread_exit(NULL);
}


