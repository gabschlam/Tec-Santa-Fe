/*
	Example to send signals to other programs
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include <signal.h>


int main(int argc, char * argv[])
{
	if (argc != 2)
	{
		printf("Please provide a PID as argument\n");
		exit(EXIT_SUCCESS);
	}

	// Get the PID of the other process
	pid_t victim = atoi(argv[1]);

	if (kill(victim, SIGKILL) == -1)
	{
		printf("Could not find the process\n");
	}

	else
	{
		printf("Process %d killed \n", victim);
	}

	return 0;
}

