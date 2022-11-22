/* unsrvr.c */
/* semplice server, Unix domain; notare signal handling */

#include <stdio.h> 
#include <unistd.h> 
#include <stdlib.h>  
#include <sys/socket.h> 
#include <sys/un.h>
#include <signal.h>
#include <string.h>

#define MAXBUF 16	// small, so that multiple reads will happen
#define NOMESOCK "servsock" 

void terminazione(int), cleanup(); 
int terminated = 0; 
int server_socket, connect_socket;	// global so both main() and
									// cleanup() may refer to them
char buffer[MAXBUF]; 

int main(int argc, char * argv[])
{
	socklen_t client_addr_len; 		// necessario tipo previsto da accept
	int retcode; 
	struct sockaddr_un client_addr,	server_addr;

	// installa handler per terminazione server da tastiera
//	signal(SIGINT,terminazione);
//	signal(SIGKILL, terminazione);

	// apertura del socket del server 
	if ( (server_socket = socket(AF_UNIX,SOCK_STREAM,0)) == -1)
		{perror("opening server socket: "); exit(-1);} 

	// preparazione dell'indirizzo per il bind sulla socket
	if (unlink(NOMESOCK) == -1) {  // unlink() serve prima di bind()
		perror("cancellando");     // sulla socket NOMESOCK
		fprintf(stderr, "no old socket \"%s\" to delete\n", 
		        NOMESOCK);
	} else
		fprintf(stderr, "old socket %s deleted\n", NOMESOCK);
	server_addr.sun_family = AF_UNIX; 
	strcpy(server_addr.sun_path, NOMESOCK);
////
	// pubblicazione socket / listen
	retcode = 
	bind(server_socket,
	     (struct sockaddr *) &server_addr, //NB: type cast
	     sizeof(server_addr) ); 
	if(retcode == -1)
		{perror("error binding socket"); exit(-1);}
	retcode = listen(server_socket, 1);
	if(retcode == -1)
		{perror("error listening"); exit(-1);}	
	printf("Server ready (CTRL-C quits)\n");

	// ciclo che accetta le connessioni
	client_addr_len = sizeof(client_addr); 
	while (!terminated &&
	       (connect_socket = 		//diversa da server_socket!
	        accept(server_socket,
		           (struct sockaddr *) & client_addr, // cast
	               &client_addr_len)) != -1 ) { 

		// cycle: process data from accepted connection 
		printf("Server: new connection from client\n");
		while ((retcode = read(connect_socket, buffer, MAXBUF))
		       > 0)     // multiple read()s likely if MAXBUF small
			write(fileno(stdout), buffer, retcode);
		if (retcode == 0)		// connessione chiusa
			printf("\nClient connection closed\n");
        else
            perror("errore su read");
		close(connect_socket);
	}                  // qui si arriva per errore in 
	cleanup();         // accept() o terminated==TRUE
	return(0);
}

void cleanup()
{	close(connect_socket); close(server_socket);
	if (unlink(NOMESOCK) < 0) 
		{perror("removing socket");}
	printf("Terminated\n");
	exit(0);
}

#define MOREMSG "need another message to terminate\n"
void terminazione(int signo)
{
	printf("Signal handler started. Terminating ...\n");	
	terminated=1; 
//	Next two lines are alternative; comment out either one!
	printf(MOREMSG); return;        // go to main's blocking accept()
//	cleanup();					    // terminate at once
}
