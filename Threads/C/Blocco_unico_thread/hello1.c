/* hello1.c */

// discussione su exit() in un thread e exit/return nel main

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <ctype.h> 

void * print_message_fun( void * arg )  // funzione eseguita dai thread
{
	char *msg;
	msg = (char *) arg;
	sleep(toupper(msg[0])-'A');
	printf("\npid %d: %s", getpid(), msg);

	pthread_exit(NULL);  // equivale a un return (anche implicito) ed
                         //    e` la maniera corretta di far terminare il thread
//	exit(1);             // exit() termina il *processo*, quindi tutti i 
}                        // thread, compreso il main...

int main(int argc, char * argv[])  // genera due thread
{
	pthread_t thread1, thread2;
	char message1[] =   "Ciao\n";
//	char message2[] =   "All\\n";
	char message2[100]; // da argv[1]

	if (argc == 1) {
		fprintf(stderr, "Usage: %s STRING\n", argv[0]);
		fprintf(stderr, "    due thread distinti scrivono su stdout, uno scrive \"Ciao\" e l'altro \n");
		fprintf(stderr, "    scrive STRING con ritardo dato da STRING[0] (0 per 'a', 1 per 'b'...)\n");
		exit(5);
	}
	sprintf(message2, "%s\n", argv[1]);

	pthread_create( &thread1, NULL, print_message_fun, (void *) message1 );  // type cast su arg 4
	pthread_create( &thread2, NULL, print_message_fun, (void *) message2 );
  
	pthread_join(thread1, NULL);
	pthread_join(thread2, NULL);

	printf("\nThread padre pid=%d terminato\n\n", getpid());

//	nel thread main, return e exit sono equivalenti e fanno terminare *il processo*, quindi tutti i thread
//	return(0);      // qui return/exit sembrerebbero innocui, anzi inutili, ma, se ne attivassimo
//	exit(0);        // uno dei due e commentassimo i due pthread_join() sopra, thread1 e thread2 
}                   // terminerebbero anzi tempo
  
