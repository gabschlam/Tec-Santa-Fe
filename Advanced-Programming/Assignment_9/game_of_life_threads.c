/*
	Assignment 9: Game of life - Threads version
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>
#include "pgm_image.h"

#define STRING_LENGTH 60

// Structure to pass information to the threads
typedef struct start_stop_struct {
	pgm_t * pgm_image;
	pgm_t * pgm_new_image;
	unsigned int start;
	unsigned int stop;
} start_stop_t;

// Functions declarations
void usage(char * program);
void strip_ext(char *fname);
void interations(pgm_t * pgm_image, int iter, char * out_filename, int num_threads);
void createThreads(pgm_t * pgm_image, pgm_t * pgm_new_image, int num_threads);
void * changePGM(void * arg);


int main(int argc, char * argv[])
{
	char * in_filename;
	char out_filename[STRING_LENGTH];
	pgm_t pgm_image;
	int iter ;
	int num_threads;

	// Check the correct arguments
	if (argc != 4)
	{
		usage(argv[0]);
	}

	iter = atoi(argv[1]);
	in_filename = argv[2];
	num_threads = atoi(argv[3]);

	readPGMFile(in_filename, &pgm_image);

	strcpy(out_filename, in_filename);
	strip_ext(out_filename);

	// Leaving it commented because in Mac doesn't work
	// Convert the ASCII format into Binary, to generate smaller images
	// Change the P2 format to P5
	//image.magic_number[1] = '5';

	interations(&pgm_image, iter, out_filename, num_threads);

	return 0;
}

// Explanation to the user of the parameters required to run the program
void usage(char * program)
{
	printf("Usage:\n");
	printf("\t%s {number_iterations} {filename_image} {number_threads}\n", program);
	exit(EXIT_FAILURE);
}

/* 
	Remove extension from filename, for better handeling of filename while writing new one
	From: https://stackoverflow.com/questions/43163677/how-do-i-strip-a-file-extension-from-a-string-in-c/43163740
*/
void strip_ext(char *fname)
{
	char *end = fname + strlen(fname);

	while (end > fname && *end != '.' && *end != '\\' && *end != '/') {
		--end;
	}
	if ((end > fname && *end == '.') &&
		(*(end - 1) != '\\' && *(end - 1) != '/')) {
		*end = '\0';
	}  
}


void interations(pgm_t * pgm_image, int iter, char * out_filename, int num_threads)
{
	pgm_t pgm_new_image;
	char filename[STRING_LENGTH];

	// Copying the necessary elements from first image to the new one
	pgm_new_image.image.width = pgm_image->image.width;
	pgm_new_image.image.height = pgm_image->image.height;
	sprintf(pgm_new_image.magic_number, "%s", pgm_image->magic_number);
	pgm_new_image.max_value = pgm_image->max_value;

	// Alocating new image
	allocateImage(&(pgm_new_image.image));

	for (int i = 0; i < iter; i++)
	{
		// Preparing the filename for the new image, with the original name + number of iteration
		sprintf(filename, "%s_TH%d.pgm", out_filename, i+1);

		// Checking if the number of iteration is even or odd, for being able to swap pointer between images
		if (i%2 == 0)
		{
			// Function for creating the threads for each iteration
			createThreads(pgm_image, &pgm_new_image, num_threads);
			writePGMFile(filename, &pgm_new_image);
		}
		else
		{
			// Function for creating the threads for each iteration
			createThreads(&pgm_new_image, pgm_image, num_threads);
			writePGMFile(filename, pgm_image);
		}

	}

	// Free new image
	freeImage(&pgm_new_image.image);

	printf("%d iterations completed\n", iter);

}

void createThreads(pgm_t * pgm_image, pgm_t * pgm_new_image, int num_threads)
{
	// Creating threads data and struct
	pthread_t tids[num_threads];
	start_stop_t thread_limits[num_threads];

	unsigned int range;
	bool dif = false;
	int status;

	// Set the range of each thread, for being able to divide properly the image's height
	range = pgm_image->image.height / num_threads;

	// Create the threads
	for (int i=0; i<num_threads; i++)
	{
		// Initialize data for each thread
		thread_limits[i].pgm_image = pgm_image;
		thread_limits[i].pgm_new_image = pgm_new_image;
		thread_limits[i].start = i * range;
		thread_limits[i].stop = i * range + range - 1;

		// If there is a remainder in the range, add one more to the start, starting from the second thread
		if (dif == true)
		{
			thread_limits[i].start +=1;
		}

		// If there is a remainder in the range, add one more to the stop
		if (range<pgm_image->image.height)
		{
			thread_limits[i].stop += 1;
			dif = true;

		}

		// Start each thread
		status = pthread_create(&tids[i], 0, changePGM, &thread_limits[i]);
		if (status < 0)
		{
			perror("ERROR: pthread_create");
			exit(EXIT_FAILURE);
		}
	}

	// Join the threads to wait for them to finish
	for (int i=0; i<num_threads; i++)
	{
		pthread_join(tids[i], NULL);
	}
}

void * changePGM(void * arg)
{
	start_stop_t * thread_limits = (start_stop_t *)arg;

	int count;
	int above;
	int below;
	int right;
	int left;

	for (unsigned int i=thread_limits->start; i<=thread_limits->stop; i++)
	{
		for (int j=0; j<thread_limits->pgm_image->image.width; j++)
		{
			count = 0;

			// Initialize values for above, below, left and right of current pixel
			above = (i - 1 + thread_limits->pgm_image->image.height) % thread_limits->pgm_image->image.height;
			below = (i+1) % thread_limits->pgm_image->image.height;
			left = (j-1 + thread_limits->pgm_image->image.width) % thread_limits->pgm_image->image.width;
			right = (j+1) % thread_limits->pgm_image->image.width;

			// Checking every neighbours to see how many are alive
			if(thread_limits->pgm_image->image.pixels[above][left].value == 1) 
			{
				count++;
			}

			if(thread_limits->pgm_image->image.pixels[above][j].value == 1)
			{
				count++;
			}

			if(thread_limits->pgm_image->image.pixels[above][right].value == 1)
			{
				count++;
			}

			if(thread_limits->pgm_image->image.pixels[i][left].value == 1)
			{
				count++;
			}

			if(thread_limits->pgm_image->image.pixels[i][right].value == 1)
			{
				count++;
			}

			if(thread_limits->pgm_image->image.pixels[below][left].value == 1)
			{
				count++;
			}

			if(thread_limits->pgm_image->image.pixels[below][j].value == 1)
			{
				count++;
			}

			if(thread_limits->pgm_image->image.pixels[below][right].value == 1)
			{
				count++;
			}

			// Check number of neighbours alive, and make decisions according to rules

			// If current pixel is one (alive)
			if (thread_limits->pgm_image->image.pixels[i][j].value == 1)
			{
				// If the number of neighbours alive is fewer than two, current pixel dies
				if (count < 2)
				{
					thread_limits->pgm_new_image->image.pixels[i][j].value = 0;
				}

				// If the number of neighbours alive is two or three, current pixel lives
				else if (count == 2 || count == 3)
				{
					thread_limits->pgm_new_image->image.pixels[i][j].value = 1;
				}

				// If the number of neighbours alive is higher than three, current pixel dies
				else if (count > 3)
				{
					thread_limits->pgm_new_image->image.pixels[i][j].value = 0;
				}
			}

			// If current pixel is zero (dead)
			else
			{
				// If the number of neighbours alive is exactly three, current pixel lives
				if (count == 3)
				{
					thread_limits->pgm_new_image->image.pixels[i][j].value = 1;
				}

				// If not, current pixel dies
				else
				{
					thread_limits->pgm_new_image->image.pixels[i][j].value = 0;
				}
			}	
			
		}

	}

	pthread_exit(NULL);

}

