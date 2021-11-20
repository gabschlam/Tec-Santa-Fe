/*
	Signals Handling
	Operating Systems
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <signal.h>

//This is the Signal Handler
void CatchInt(int sig_num){
    printf("\n Don’t do that! \n"); fflush(stdout);
}

int main (){
    /* Sets the SIGINT signal handler to the function CatchInt for this process
	Thus, when you try to kill the process with CTRL-C you will not be able.
	To kill the process find the Process ID using ps and kill it using the command
	kill -9 <pid>
	This sends a signal called SIGKILL that ends the process. 
	*/
    signal(SIGINT, CatchInt);
    while(1); /* infinite loop */
}

