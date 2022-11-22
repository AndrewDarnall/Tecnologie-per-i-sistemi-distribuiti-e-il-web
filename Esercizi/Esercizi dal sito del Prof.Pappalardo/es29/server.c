/* 

Implementare in Java o C un server che comunica tramite socket.

Il server attende sul port 33333 e memorizza in ordine tutte le stringhe che riceve (di fatto nell'implementazione ci si può limitare a memorizzare solo le ultime 100, se lo si desidera).

In risposta ad ogni nuova stringa proveniente da un client gli risponde con tutte le stringhe ricevute fino a quel momento (compresa l'ultima), con queste eccezioni:

   - a partire dalla quinta stringa memorizzata, il server risponderà: LIMITE RAGGIUNTO;
   - in risposta alla stringa RESET, il server azzera l'elenco delle stringhe memorizzate fino ad allora e risponde OK.
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>

#define DIM 100
#define SERVERPORT 33333
#define SERVERADDRESS "127.0.0.1"
#define MAXBUF 64


char strings[DIM][MAXBUF], buffer[MAXBUF], client_address, *j;
int count, i, x, server_socket, connect_socket, retcode;
struct sockaddr_in server_addr, client_addr;
socklen_t client_addr_len;

int main()
{
	count = 0;
	
	// apertura socket del server
	if ((server_socket = socket(AF_INET, SOCK_STREAM, 0)) == -1)
	{
		perror("Error in server socket()");
		exit(-1);
	}
	
	// preparazione dell'indirizzo locale del server
	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(SERVERPORT);
	server_addr.sin_addr.s_addr = inet_addr(SERVERADDRESS);
	
	// bind del socket
	if ((retcode = bind(server_socket, (struct sockaddr*) &server_addr, sizeof(server_addr))) == -1)
	{
		perror("Error in server socket bind()");
		exit(-1);
	}
	
	// listen del socket
	if ((retcode = listen(server_socket, 1)) == -1)
	{
		perror("Error in server socket listen()");
		exit(-1);
	}
	
	printf("Server ready (CTRL-C quits)\n");
	
	client_addr_len = sizeof(client_addr);
	
	// cicla in attesa di connessioni
	while(1)
	{	
		
		// resetto il server se si arrivasse alla capienza massima
		if (count == DIM)
			count = 0;
		
		// accetta le connessioni
		if ((connect_socket = accept(server_socket, (struct sockaddr *)&client_addr, &client_addr_len)) == -1)
		{
			perror("Error in accept()");
			close(server_socket);
			exit(-1);
		}
		
		// riceve la stringa
		retcode = read(connect_socket, buffer, MAXBUF-1);

		// converto buffer in formato stringa
		buffer[retcode] = '\0';
		
		// elaborazione e risposta del server
		if (strcmp(buffer, "RESET") == 0)
		{
			count = 0;
			strcpy(buffer, "OK\n");
			write(connect_socket, buffer, MAXBUF-1);
		}
		else
		{
			strcpy(strings[count], buffer);
			count++;
			
			if (count < 5)
			{
				x = 0;
				for (i = 0; i < count; i++)
				{
					j = &strings[i][0];
					while(*j != '\0')
					{
						buffer[x] = *j;
						j++;
						x++;
					}
					buffer[x] = ';';
					x++;
				}
				
				buffer[x] = '\n';
				buffer[x+1] = '\0';
					
				write(connect_socket, buffer, MAXBUF-1);
			}
			else
			{
				strcpy(buffer, "LIMITE RAGGIUNTO\n");
				write(connect_socket, buffer, MAXBUF-1);
			}
		}

		// chiudo la socket
		close(connect_socket);
	}
	
	return 0;
}
