/*
	Signals Handling
	Operating Systems
 
	Gabriel Schlam
*/
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <signal.h>

void CatchInt(int sig_num){
	if(sig_num == SIGINT){
		printf("Child process received SIGINT\n");
	}
	else if(sig_num == SIGUSR1){
		printf("Child process received SIGUSR1\n");
	}
	else if(sig_num == SIGUSR2){
		printf("Child process received SIGUSR2\n");
	}
}

void CatchFather(int sig_num){
	printf("Parent process received SIGINT\n");
}

int main()
{
	int sel = 0;
	
	printf("Child process created \n");
	pid_t child_id=fork();
	
	if(child_id>0){
		printf("Options: \n");
		printf("1) SIGINT \n");
		printf("2) SIGURS1 \n");
		printf("3) SIGURS2 \n");
		printf("4) Exit \n");
		
		printf("Pick an option: ");
		scanf( "%d", &sel);
		
		signal(SIGINT, CatchFather);
		
		while(sel != 4){
	
			switch (sel)
			{
				case 1: 
				kill(child_id, SIGINT);
				printf("Parent process sending SIGINT\n");
				break;
				case 2:
				kill(child_id, SIGUSR1);
				printf("Parent process sending SIGUSR1\n");
				break;
				case 3:
				kill(child_id, SIGUSR2);
				printf("Parent process sending SIGUSR2\n");
				break;
				case 4:
				kill(child_id, SIGKILL);
				break;
				default: printf("ERROR: Incorrect option. \n" );
			}
				
			printf("Pick an option: ");
			scanf( "%d", &sel);
		}
	}
	else{
		signal(SIGINT, CatchInt);
		signal(SIGUSR1, CatchInt);
		signal(SIGUSR2, CatchInt);
		while(1);
	}
	
	return 0;
}
