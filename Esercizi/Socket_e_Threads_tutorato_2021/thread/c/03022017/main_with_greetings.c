/*

 * Stefano Borzì X81000003

 * Una variabile intera n, inizializzata a 0, è condivisa tra 2 thread tO, tE.
 * Il thread tE, ciclicamente:
 * 	1. attende 200 ms (N.B.: la chiamata usleep(t) attende per t microsecondi)
 * 	2. genera un int casuale pari e lo somma alla variabile condivisa n
 * 	3. se ha eseguito almeno 10 cicli e n è pari termina
 * 	4. altrimenti ricomincia da (1), a meno che abbia già compiuto 1000 iterazioni, nel qual caso termina.
 * Il thread tO, ciclicamente:
 * 	1. attende 200 ms (N.B.: la chiamata usleep(n) attende per n microsecondi)
 * 	2. genera un int casuale dispari e lo somma alla variabile condivisa n
 * 	3. se ha eseguito almeno 10 cicli e n è dispari termina
 * 	4. altrimenti ricomincia da (1), a meno che abbia già compiuto 1000 iterazioni, nel qual caso termina.

 * (Non ricorrere a un array di 2 thread per l’implementazione!)

 * Il programma termina quando tutti i thread hanno terminato la propria esecuzione. I thread scriveranno di essere terminati. Possono anche visualizzare, a ogni ciclo, il valore trovato in n.
 * Nel codice, proteggere opportunamente la variabile n dagli accessi concorrenti. 
*/

#define MAX_RAND 50
#define MAX_ITER 15

#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

int n = 0;
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

typedef int bool_t;

void * operation(char* thread_name) {
    printf("Sono il thread %s: \n", thread_name);

    // se il nome del thread è "tE", i numeri da generare
    // sono pari. Di conseguenza, ogni volta aggiungiamo 0.
    // Se devono essere, dispari, aggiungiamo 1.
    bool_t should_generate_even_numbers = strcmp(thread_name, "tE") == 0;

    // f è il valore che aggiungiamo ad ogni random_value
    // Questo funziona perchè rand() * 2 darà sempre un numero pari.
    int f = should_generate_even_numbers ? 0 : 1;
    
    for(int i=0; i<MAX_ITER; i++) {
        usleep(200000);
        int random_value = ((rand() % MAX_RAND) * 2) + f;

        pthread_mutex_lock(&mutex);
        n += random_value;
        printf("Sono il thread %s: n=%d random_value = %d\n", thread_name, n, random_value);
        if(i>=10 && n%2==f) {
            pthread_mutex_unlock(&mutex);
            printf("Sono il thread %s: termino con n = %d\n", thread_name, n); 
            break;
        }
        pthread_mutex_unlock(&mutex);
    }

    printf("Sono il thread %s: sto terminando\n", thread_name);

    char* greetings = malloc(sizeof(char) * 256);
    sprintf(greetings, "ciao, sono il thread %s ed ho finito", thread_name);

    pthread_exit(greetings);
}


int main() {
    printf("Sono il thread principale: sto iniziando \n");
    pthread_t tO, tE;
    srand(time(NULL));

    pthread_create(&tO, NULL, (void *) operation, "tO");
    pthread_create(&tE, NULL, (void *) operation, "tE");

    char* tO_return;
    char* tE_return;

    pthread_join(tO, (void**) &tO_return);
    printf("Sono il thread principale: tO mi ha risposto %s\n", tO_return);

    pthread_join(tE, (void**) &tE_return);
    printf("Sono il thread principale: tE mi ha risposto %s\n", tE_return);

    free(tO_return);
    free(tE_return);

    printf("Sono il thread principale: sto terminando\n");
    return 0;
}