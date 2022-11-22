/* hello3.c */
// gestisce il valore restituito dal (la funzione del) thread, qui print_message_fun()

/* NB: questa funzione DEVE restituire (void *), farle restituire altro NON e`
 * una buona idea (costringe a type cast su tipi-funzione...).
 * Si noti tra l'altro che, in essa, return(...) e pthread_exit(...) sono del tutto
 * equivalenti.
 *
 * In hello2.c si usa il (void *) restituito, come contenitore per un valore di un 
 * altro tipo T (es. T un double). Ma questo non si puo` fare per qualunque T, p.es. una 
 * grossa struct.
 *
 * L'alternativa e` usare il (void *) restituito per contenere un puntatore a T (T *), 
 * il che tra l'altro e` piu` omogeneo.
 * In questo caso l'oggetto puntato deve vivere in un ambiente visibile al codice chiamante, 
 * p.es. heap (malloc()) o memoria globale/statica.
 * 
 * Ne deriva che, se l'oggetto da restituire e` una var x di tipo T, la funzione 
 * dovra` terminare con qualcosa come:
 *    pthread_exit((void *) UN-PUNTATORE-A-T)
 *
 * Quindi il thread che attende la terminazione del primo, sia th1, avra` un
 *    pthread_join(th1,&res), dove la variabile res ha tipo (void *)
 * e, usera` in realta` res attraverso cast e dereferencing: *((T *) res)
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <ctype.h> 
#include <string.h> 

extern unsigned int gettid(void); // vedi gettid.c, restituisce thread id numerico

struct res {
	char nome[10];
	int valore;
}
;

void * print_message_fun(void * arg)   // funzione eseguita dai thread
{
	char *msg;
	msg = (char *) arg;
	struct res * result_p;   // result_p punta al risultato "logico"

	sleep(toupper(msg[0])-'A');
	printf("\npid %d: %s", getpid(), msg);

	result_p = malloc(sizeof(struct res));     // costruiamo struct dinamica
	strcpy(result_p->nome, "Thread");          // riempiamo i suoi 
	result_p->valore = msg[0] == 'C' ? 1 : 2;  // membri e restituiamo il 
                                               // puntatore ad essa come
	pthread_exit((void *) result_p);           // risultato di funzione/thread
}

int main(int argc, char * argv[])  // genera due thread
{
	pthread_t thread1, thread2;
	void *thrd1_res, *thrd2_res;
	struct res * sp;

	char message1[] = "Ciao\n";
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

	pthread_join(thread1, &thrd1_res);      // ora thrd1_res punta a struct con il risultato del thread
	sp = (struct res *) thrd1_res;          // type cast serve perche' thrd1_res è un (void *), per cui
	printf("\nThread1->%s,%d\n", sp->nome, sp->valore);          // thrd1_res->nome e thrd1_res->valore
                                                                 // non sarebbero accettabili  
	pthread_join(thread2, &thrd2_res);
	sp = (struct res *) thrd2_res;
	printf("\nThread2->%s,%d\n", sp->nome, sp->valore);	

	printf("\nThread padre pid=%d terminato\n\n", getpid());
}
