/*
	Simple example of Sockets: Server
	Operating Systems
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

int main(){
	int sock = socket(AF_INET, SOCK_STREAM, 0); //CREA SOCKET
	
	//GUARDAR VARIABLE DONDE VA A ESTAR DIRECCION
	struct sockaddr_in address; 
	int addrlen = sizeof(address);
	address.sin_family = AF_INET;
	address.sin_addr.s_addr = INADDR_ANY;
	address.sin_port = htons(1024);
	//GUARDAR VARIABLE DONDE VA A ESTAR DIRECCION
	
	char buffer[1024];
	bind(sock, (struct sockaddr*)&address, addrlen); // ASIGNA DIRECCION
	listen(sock, 2); //ESTA AL PENDIENTE DE QUE EL CLIENTE MANDE ALGO
	
	//DEPENDE DE COMUNICACION CON CLIENTE...
	int new = accept(sock, (struct sockaddr*)&address, (socklen_t*)&addrlen); //DESPUES DE QUE EL CLIENTE SE CONECTA SE ACEPTA LA CONEXION
	read(new, buffer, 1024); //LEE TEXTO ENVIADO POR CLIENTE
	printf("El texto leido es: %s\n", buffer); //IMPRIME TEXTO LEIDO
	close(new);
	close(sock);
	
	return 0;
}
