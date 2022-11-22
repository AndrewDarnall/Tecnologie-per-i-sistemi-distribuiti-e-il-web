/* fingers.c */

#include <sys/types.h>
#include <sys/socket.h> 
#include <netinet/in.h>
#include <arpa/inet.h>     // per inet_aton(), ...

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define LOCIP "127.0.0.1"  // per ascoltare *ogni* interfaccia locale di
//#define LOCIP "0.0.0.0"  // rete, usare "0.0.0.0" -> INADDR_ANY (0x0)
#define MYFINGERPORT 2000

#define MAXQ 512
#define MAXBUF 1024
#define NITER 10
#define MANYBYTES 16*1024
#define DELAY 7


int main()
{
    int s, s1;
    struct sockaddr_in locAddr, farAddr;
    socklen_t farAddrL, ipAddrL; 
    int iter, retcode;
    char buf[MAXBUF], msg[MAXBUF], outbuf[MANYBYTES], *p, *q;
    int mkaddr(struct sockaddr_in * skaddr, char * ipaddr, u_int16_t port);
    
    // Creazione socket
    if ( (s = socket(PF_INET, SOCK_STREAM, 0)) == -1 )
        {perror("creating socket"); exit(-10);}
    
    // Costruzione indirizzo socket nella struct locAddr
    mkaddr(&locAddr, LOCIP,         // costruisce l'indirizzo del server 
           MYFINGERPORT);    // nella struct locAddr
    ipAddrL = farAddrL = sizeof(struct sockaddr_in);
    if ( bind(s, (struct sockaddr *) &locAddr, ipAddrL) == -1 )
        {perror("trying to bind"); exit(-1);}
    listen(s, MAXQ);
    ////
    while ((s1 = 
        accept(s, (struct sockaddr *) &farAddr, &farAddrL)
        ) != -1) {
        printf("Client from %s/%d connected, its address is %d bytes long\n", 
        inet_ntoa(farAddr.sin_addr), 
        ntohs(farAddr.sin_port), farAddrL);
           
            // NB: NON E' CORRETTO un solo read, ma basta se cliente manda 
        if ( (retcode = read(s1,buf,MAXBUF)) > 0) {  // pochi byte
            buf[retcode-1] = '\0';               // overwrite last char
            printf("Client asks: <%s>\n", buf);  // to make buf a string
            buf[retcode-1] = '\n';
        }
    
        // reply to client, send various data
        sprintf(msg, "Hi from server: sending back %d bytes\n", 
                retcode*NITER);
        write(s1, msg, strlen(msg));    // msg e` di certo una stringa
        sleep(DELAY);
        for (iter = 0; iter < NITER; iter++)
            write(s1,buf,retcode);
        
        sprintf(msg, "Hi from server: sending back %d bytes\n", MANYBYTES);
        write(s1, msg, strlen(msg));    // msg e` di certo una stringa
        for (p = outbuf, q = p+MANYBYTES; p < q; p++)  // riempie outbuf
            *p = 'X';                                  // di 'X'
        sleep(DELAY);
        write(s1, outbuf, MANYBYTES);
        close(s1);
        printf("Connection with client closed\n\n");
    } // exit while (solo se accept() torna con -1)
    
    // Server non termina; servirebbe gestore SIGINT
    exit(0);
}
    
