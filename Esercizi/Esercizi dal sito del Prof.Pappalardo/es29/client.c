/*
Si implementi in C o Java un cliente che riceve da standard input una stringa, la invia al server su localhost, port 33333 e infine attende da esso come risposta una stringa che visualizzerà sulla standard output.
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>

#define SERVERPORT 33333
#define SERVERADDRESS "127.0.0.1"
#define MAXBUF 64

int main(int argc, char *argv[])
{	
	int client_socket, retcode;
	char buffer[MAXBUF];
	struct sockaddr_in server_addr;
	
	// verifico la correttezza dei parametri
	if (argc != 2)
	{
		printf("Usage: %s \"message to sent\"\n", argv[0]);
		exit(-1);
	}
	
	// apertura socket del client
	if ((client_socket = socket(AF_INET, SOCK_STREAM, 0)) == -1)
	{
		perror("Error in socket()");
		exit(-1);
	}
	
	// preparazione dell'indirizzo del server con cui connettere il socket
	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(SERVERPORT);
	server_addr.sin_addr.s_addr = inet_addr(SERVERADDRESS);
	
	// connessione del socket al server
	if ((connect(client_socket, (struct sockaddr *)&server_addr, sizeof(server_addr))) == -1)
	{
		perror("Error in connect()");
		exit(-1);
	}
	
	printf("Client connected to %s\n", SERVERADDRESS);
	
	write(client_socket, argv[1], strlen(argv[1]));
	retcode = read(client_socket, buffer, MAXBUF-1);
	
	buffer[retcode] = '\0';
	
	printf(">>> %s", buffer);
	
	return 0;
}
