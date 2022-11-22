/* hello2.c */
// gestisce il valore restituito dal (la funzione del) thread, qui print_message_fun()

/* NB: questa funzione DEVE restituire (void *), farle restituire altro NON e`
 * una buona idea (costringe a type cast su tipi-funzione...).
 * Si noti tra l'altro che, in essa, return(...) e pthread_exit(...) sono del tutto
 * equivalenti.
 *
 * Ne deriva che, se si vuole che la funzione restituisca in effetti altro, p.es. 
 * VAL di tipo T, dovra` terminare con qualcosa come:
 *    pthread_exit((void *) VAL)
 *
 * Sia th1 il thread che ha eseguito la funzione. Per attendere la terminazione di th1, 
 * si usera`, tipicamente: 
 *    pthread_join(th1, &res);   
 * dove la variabile res ha tipo (void *); dopo il join(), res conterrà il valore 
 * restituito da pthread_exit(), che è in realtà un T; quindi si usera` il 
 * contenuto di res attraverso un cast, così: (T) res
 *
 * In sostanza, si usa il (void *) restituito dalla funzione come "contenitore" 
 * per un valore di un altro tipo (T) che, beninteso, deve "entrare" nel contenitore,
 * cosa che sarà assicurata dal fatto che il type cast (T) sia accettabile.
 * In questo esempio, il (void *) viene usato per contenere un long (entrambi 64 bit).
 * Potrebbe contenere anche un int (32 bit), ma non una struct grande 1K!
 */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <ctype.h> 

extern unsigned int gettid(void); // vedi gettid.c, restituisce thread id numerico

void * print_message_fun(void * arg)   // funzione eseguita dai thread
{
	char *msg;
	msg = (char *) arg;
	long real_result;         // "vero" risultato
    void * result;            // risultato restituito; NB: long e (void *) hanno
                                                    // uguale dimensione in byte
	sleep(toupper(msg[0])-'A');
	printf("\npid %d: %s", getpid(), msg);

	real_result =                  // restituisci 1 per il thread1 (messaggio "Ciao")
		msg[0] == 'C' ? 1L : 2L;   //    e 2 per il thread2 (l'altro)
	result = (void *) real_result; // type_cast da long a (void *) OK per dimensione
	pthread_exit(result);          // oppure...
//	return(result);                // equivalente, cf. man pthread_create()
                                   //  NB: si sta restituendo il *valore* della 
}                                  //  var locale result (mai restituirne l'indirizzo!) 

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
	printf("\nthread1->%ld, thread2->%ld\n", (long) thrd1_res, (long) thrd2_res);	
	printf("\nThread padre pid=%d terminato\n\n", getpid());
}
