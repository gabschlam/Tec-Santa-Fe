/*
	Example of parsing arguments
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>


int main(int argc, char * argv[])
{
	int option;
	char input[50];
	int output;

	printf("This program got %d arguments:\n", argc);

	for (int i = 0; i < argc; i++)
	{
		printf("\t%d: %s\n", i, argv[i]);
	}

	while ((option = getopt(argc, argv, "i:o:n:yc::")) != -1) {
		switch(option){
			case 'i':
				strncpy(input, optarg, 50);
				break;
			case 'o':
				output = atoi(optarg);
				break;
			case 'n':
				// Do something
				break;
			case '?':
				printf("Unknown option: %c\n", option);
				break;
		}
	}

	printf("Remaining arguments: \n");
	for (int i = optind; i < argc; i++)
	{
		printf("%s\n", argv[i]);
	}

	return 0;
}