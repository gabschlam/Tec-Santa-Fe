/*
	Assignment 9: Game of life - OMP version
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include "pgm_image.h"

#define STRING_LENGTH 60

// Functions declarations
void usage(char * program);
void strip_ext(char *fname);
void interations(pgm_t * pgm_image, int iter, char * out_filename);
void changePGM(pgm_t * pgm_image, pgm_t * pgm_new_image);


int main(int argc, char * argv[])
{
	char * in_filename;
	char out_filename[STRING_LENGTH];
	pgm_t pgm_image;
	int iter;

	// Check the correct arguments
	if (argc != 3)
	{
		usage(argv[0]);
	}

	iter = atoi(argv[1]);
	in_filename = argv[2];

	readPGMFile(in_filename, &pgm_image);

	strcpy(out_filename, in_filename);
	strip_ext(out_filename);

	// Leaving it commented because in Mac doesn't work
	// Convert the ASCII format into Binary, to generate smaller images
	// Change the P2 format to P5
	//pgm_image.magic_number[1] = '5';

	interations(&pgm_image, iter, out_filename);

	return 0;
}

// Explanation to the user of the parameters required to run the program
void usage(char * program)
{
	printf("Usage:\n");
	printf("\t%s {number_iterations} {filename_image}\n", program);
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

void interations(pgm_t * pgm_image, int iter, char * out_filename)
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
		sprintf(filename, "%s_OMP%d.pgm", out_filename, i+1);

		// Checking if the number of iteration is even or odd, for being able to swap pointer between images
		if (i%2 == 0)
		{
			changePGM(pgm_image, &pgm_new_image);
			writePGMFile(filename, &pgm_new_image);
		}
		else
		{
			changePGM(&pgm_new_image, pgm_image);
			writePGMFile(filename, pgm_image);
		}

	}

	// Free new image
	freeImage(&pgm_new_image.image);

	printf("%d iterations completed\n", iter);

}


void changePGM(pgm_t * pgm_image, pgm_t * pgm_new_image)
{
	int count;
	int above;
	int below;
	int right;
	int left;

	// Initializing omp parallel, with the only variables shared as the current image and the new image
	#pragma omp parallel default(none) shared(pgm_image, pgm_new_image) private(count, above, below, right, left)
	{
		// Making parallel the first for
		#pragma omp for
		for (int i=0; i<pgm_image->image.height; i++)
		{
			for (int j=0; j<pgm_image->image.width; j++)
			{
				count = 0;

				// Initialize values for above, below, left and right of current pixel
				above = (i - 1 + pgm_image->image.height) % pgm_image->image.height;
				below = (i+1) % pgm_image->image.height;
				left = (j-1 + pgm_image->image.width) % pgm_image->image.width;
				right = (j+1) % pgm_image->image.width;

				// Checking every neighbours to see how many are alive
				if(pgm_image->image.pixels[above][left].value == 1) 
				{
					count++;
				}
				if (pgm_image->image.pixels[above][j].value == 1)
				{
					count++;
				}
				if(pgm_image->image.pixels[above][right].value == 1)
				{
					count++;
				}
				if(pgm_image->image.pixels[i][left].value == 1)
				{
					count++;
				}
				if(pgm_image->image.pixels[i][right].value == 1)
				{
					count++;
				}
				if(pgm_image->image.pixels[below][left].value == 1)
				{
					count++;
				}
				if(pgm_image->image.pixels[below][j].value == 1)
				{
					count++;
				}
				if(pgm_image->image.pixels[below][right].value == 1)
				{
					count++;
				}

				// Check number of neighbours alive, and make decisions according to rules

				// If current pixel is one (alive)
				if (pgm_image->image.pixels[i][j].value == 1)
				{
					// If the number of neighbours alive is fewer than two, current pixel dies
					if (count < 2)
					{
						pgm_new_image->image.pixels[i][j].value = 0;
					}
	
					// If the number of neighbours alive is two or three, current pixel lives
					else if (count == 2 || count == 3)
					{
						pgm_new_image->image.pixels[i][j].value = 1;
					}

					// If the number of neighbours alive is higher than three, current pixel dies
					else if (count > 3)
					{
						pgm_new_image->image.pixels[i][j].value = 0;
					}
				}

				// If current pixel is zero (dead)
				else
				{
					// If the number of neighbours alive is exactly three, current pixel lives
					if (count == 3)
					{
						pgm_new_image->image.pixels[i][j].value = 1;
					}

					// If not, current pixel dies
					else
					{
						pgm_new_image->image.pixels[i][j].value = 0;
					}
				}	
				
			}

		}
	}
}

