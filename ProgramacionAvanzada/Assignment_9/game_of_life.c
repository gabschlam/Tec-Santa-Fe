/*
	Simple program to use the pgm library

	Gilberto Echeverria
	17/11/2019
*/

#include <stdio.h>
#include <stdlib.h>
#include "pgm_image.h"

#define STRING_LENGTH 60

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
	Remove extension from filename
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

	pgm_new_image.image.width = pgm_image->image.width;
	pgm_new_image.image.height = pgm_image->image.height;
	sprintf(pgm_new_image.magic_number, "%s", pgm_image->magic_number);
	pgm_new_image.max_value = pgm_image->max_value;

	allocateImage(&(pgm_new_image.image));

	for (int i = 0; i < iter; i++)
	{
		sprintf(filename, "%s_NO%d.pgm", out_filename, i+1);

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

	for (int i=0; i<pgm_image->image.height; i++)
	{
		for (int j=0; j<pgm_image->image.width; j++)
		{
			count = 0;

			above = (i - 1 + pgm_image->image.height) % pgm_image->image.height;
			below = (i+1) % pgm_image->image.height;
			left = (j-1 + pgm_image->image.width) % pgm_image->image.width;
			right = (j+1) % pgm_image->image.width;

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

			// Check number of neighbours alive
			if (pgm_image->image.pixels[i][j].value == 1)
			{
				if (count < 2)
				{
					pgm_new_image->image.pixels[i][j].value = 0;
				}

				else if (count == 2 || count == 3)
				{
					pgm_new_image->image.pixels[i][j].value = 1;
				}

				else if (count > 3)
				{
					pgm_new_image->image.pixels[i][j].value = 0;
				}
			}
			else
			{
				if (count == 3)
				{
					pgm_new_image->image.pixels[i][j].value = 1;
				}

				else
				{
					pgm_new_image->image.pixels[i][j].value = 0;
				}
			}
			
		}

	}
}

