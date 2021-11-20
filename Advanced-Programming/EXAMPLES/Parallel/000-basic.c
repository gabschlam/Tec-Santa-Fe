#include <stdio.h>
#include <omp.h>

int main()
{
	int num_threads;
	int tid;

	#pragma omp parallel
	{
		#pragma omp master
		{
			num_threads = omp_get_num_threads();

			printf("%d threads generated\n", num_threads);
		}

		// Get the thread id of each thread
		tid = omp_get_thread_num();
		printf("Hello from thread %d\n", tid);
	}

	return 0;
}

