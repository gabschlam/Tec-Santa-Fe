/*
	Example of read with Pipe Type FIFO
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>     // UNIX functions

#define BUFFER_SIZE 100

int main()
{
	char * fifo_file = "test";
	FILE * file_ptr = NULL;
	char buffer[BUFFER_SIZE];

	file_ptr = fopen(fifo_file, "r");

	if (!file_ptr)
	{
		perror("fopen");
		exit(EXIT_FAILURE);
	}

	// Read what comes from the fifo file until the end
    while (fgets(buffer, BUFFER_SIZE, file_ptr))
    {
        printf("%s", buffer);
    }

    fclose(file_ptr);

    return 0;
}