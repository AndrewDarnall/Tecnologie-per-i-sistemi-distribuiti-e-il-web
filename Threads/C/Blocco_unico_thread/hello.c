/* hello.c */
// usa gettid(), get thread's id
// gestisce il valore restituito dal (la funzione del) thread

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <ctype.h> 

extern unsigned int gettid(void); // vedi gettid.c, restituisce thread id numerico

void * print_message_fun(void * arg) // funzione eseguita dai thread
{
	char *msg;
	msg = (char *) arg;
	long delay;
	sleep(delay = toupper(msg[0])-'A');
	printf("\npid/tid %d/%d: %s", getpid(), gettid(), msg);
	pthread_exit( (void *) delay);  // restituisce ritardo in sec
}

int main(int argc, char * argv[])  // genera due thread
{
	pthread_t thread1, thread2;
	void *thrd1_res, *thrd2_res;
	char message1[] = "Ciao\n";
//	char message2[] = "All\\n";
	char message2[100];

	if (argc == 1) {
		fprintf(stderr, "Usage: %s STRING\n", argv[0]);
		fprintf(stderr, "    due thread distinti scrivono su stdout, uno scrive \"Ciao\" e l'altro \n");
		fprintf(stderr, "    scrive STRING con ritardo dato da STRING[0] (0 per 'a', 1 per 'b'...)\n");
		exit(5);
	}
	sprintf(message2, "%s\n", argv[1]);

	pthread_create( &thread1, NULL, print_message_fun, (void*) message1 );
	pthread_create( &thread2, NULL, print_message_fun, (void*) message2 );

	pthread_join(thread1, &thrd1_res);
	pthread_join(thread2, &thrd2_res);
	printf("\nthread1->%ld sec, thread2->%ld sec\n", (long) thrd1_res, (long) thrd2_res);

	printf("\nThread padre pid/tid=%d/%d terminato\n\n", getpid(), gettid());
}
