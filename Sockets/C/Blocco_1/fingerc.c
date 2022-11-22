/* fingerc.c */

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>

//#include "mkaddr.h"     // al posto del prototipo di mkaddr()

#define MAXHN 128
#define MAXBUF 8*1024       // provare a variare
#define IPPORT_FINGER 79    // definito anche in netinet/in.h

int main(int argc, char * argv[])
{
    char buffer[MAXBUF];     // message buffer
    struct sockaddr_in addr; // server socket's address
    u_int16_t fport;
    int s, retcode;

    int mkaddr(struct sockaddr_in * skaddr, char * ipaddr, u_int16_t port);
    /* mkaddr pone in *skaddr l'indirizzo IP dato dalla coppia
     * ipaddr (una stringa, tipo "151.97.252.37") e port (p.es 79, in host order)
     * ipaddr puo` anche essere un nome tipo "directory.mit.edu"       */

    // analisi riga di comando
    if(argc != 3 && argc != 4) {
        fprintf(stderr, "  Uso: %s server_host message [port].\n", argv[0]);
        fprintf(stderr, "P.es.: %s directory.mit.edu smith\n", argv[0]);
        fprintf(stderr, "    o: %s directory.mit.edu smith | grep -e '---'\n\n", argv[0]);
        exit(1);
    }
    // Creazione socket
    if ((s = socket(AF_INET, SOCK_STREAM, 0)) == -1)
        {perror("aprendo la socket"); exit(-1);}

    // port da riga di comando o costante
    fport = argc==4 ? atoi(argv[3]) : IPPORT_FINGER;

    mkaddr(&addr, argv[1], fport);  // costruisce l'indirizzo del server (IP/nome in argv[1])

    if (connect(s, (struct sockaddr *) &addr, sizeof(addr))
        == -1 ) {
            fprintf(stderr,"errno=%d\n", errno);
            perror("mentre mi connetto");
            exit(-2);
    }

    sprintf(buffer,"%s\n", argv[2]);  // buffer conterra` di certo una stringa
    if (write(s, buffer, strlen(buffer)) == -1)   // invia messaggio al server
        {perror("scrivendo sulla socket"); exit(-3);}

    // Il seguente read(s,...) singolo e' errato (anche per grandi MAXBUF)!
    // read non e` atomica rispetto a write all'altro endpoint (v. lucidi)!

//  retcode = read(s, buffer, MAXBUF-1);

    // la soluzione corretta per ricevere dati è il seguente
    // while con read(s,...) ripetuti

    while ((retcode = read(s, buffer, MAXBUF)) != 0)
    {
        if (retcode == -1)
            {perror("leggendo dalla socket"); exit(-4); }
        fprintf(stderr, "\n----Letti %d byte----\n", retcode);
        write(STDOUT_FILENO, buffer, retcode);
    }

    fprintf(stderr, "\n>>>Exiting: server closed connection\n");

    close(s);  exit(0);
}
