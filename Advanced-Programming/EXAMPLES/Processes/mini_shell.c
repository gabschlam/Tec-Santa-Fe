/*
	Simplified shell program to run commands as children
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> // UNIX functions

int main()
{
	//char * command = "ls";
	//char * arguments[] = {"ls", "-a", (char *) NULL};

	char * command = "./factorial";
	char * arguments[] = {"./factorial", "5", (char *) NULL};
	pid_t new_pid;
	int status;

	new_pid = fork();

	if (new_pid > 0)
	{
		printf("The parent (%d) is waiting for the child (%d)\n", getpid(), new_pid);
		wait(&status);
		printf("Now the parent can continue\n");

		if (WIFEXITED(status))
        {
            printf("Child finished with status: %d\n", WEXITSTATUS(status));
        }	
    }

	else if (new_pid == 0)
	{
		printf("Switching to command '%s'\n", command);
		status = execvp(command, arguments);

		if (status < 0)
		{
			printf("ERROR on exec\n");
		}
	}

	else
	{
		printf("ERROR on fork\n");
	}

	return 0;
}