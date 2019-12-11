/*
	Program to handle signals   
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

#define LIFETIME 20

// Function declarations
void waitLoop();
void onInterrupt(int signal);

int main(int argc, char * argv[])
{
	int seconds;
	struct sigaction new_action;
	struct sigaction old_action;


	// Use a timeout obtained as an argument
	if (argc >= 2)
		seconds = atoi(argv[1]);
	// Otherwise use a default time
	else
		seconds = LIFETIME;

	// Ignore the Ctrl-C interruption
	//signal(SIGINT, SIG_IGN);

	// Ignore the Ctrl-C interruption and call function
	new_action.sa_handler = onInterrupt;
	new_action.sa_flags = SA_RESETHAND;
	sigaction(SIGINT, &new_action, &old_action);
	//signal(SIGINT, onInterrupt); // SIMPLER METHOD

	// Wait a few seconds before quiting on its own
	waitLoop(seconds);

	// Restore the Ctrl-C interruption
	sigaction(SIGINT, &old_action, NULL);
	//signal(SIGINT, SIG_DFL); // SIMPLER METHOD

	// Wait a few seconds before quiting on its own
	waitLoop(seconds);

	return 0;
}

// Function definitions

// Wait a few seconds until the program ends
void waitLoop(int seconds)
{
	int i;

	// Show the PID so that it can be sent signals form the terminal
	//  or other process
	printf("My PID is: %d\n", getpid());

	printf("Will finish on my own in %3d s", seconds);
	fflush(stdout);
	// Loop to wait for something to happen
	for (i=seconds-1; i>=0; i--)
	{
		sleep(1);
		printf("\b\b\b\b\b%3d s", i);
		fflush(stdout);
	}
	printf("\nTime out. Good bye\n");
}

// Signal handler
void onInterrupt(int signal)
{
	printf("\n The signal %d has been received \n", signal);
}


