/* unclnt.c */

/* spedisce 1o arg al server, non attende risposta */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>

#define MAXBUF 1024
#define NOMESOCK "servsock" 


int main(int argc, char * argv[]) 
{
	int client_socket; 
	int retcode; size_t msglen;
	struct sockaddr_un server_addr; 
	char msg[MAXBUF];

	// apertura del socket del client
	client_socket = socket(AF_UNIX, SOCK_STREAM, 0); 
	if (client_socket == -1)
		{perror("creating client socket"); exit(1);}

	// preparazione indirizzo su cui connettere il socket
	server_addr.sun_family = AF_UNIX; 
	strcpy(server_addr.sun_path, NOMESOCK);
	
	// connessione al socket del server
	retcode = 
	connect(client_socket, (struct sockaddr *) &server_addr, 
		    sizeof(server_addr) ); 
	if (retcode == -1) 
		{perror("connecting socket"); exit(2);}

	// scrittura del messaggio sul socket
	strcpy(msg, argc > 1 ? argv[1] : "<>"); 
	msglen = strlen(msg)+1;		// msg e` una stringa
	retcode = write(client_socket, msg, msglen); 
	printf("sent %d (%u requested) bytes on socket %d\n", 
		   retcode, (unsigned) msglen, client_socket);

	close(client_socket);     // chiusura connessione
	exit(0);
}
