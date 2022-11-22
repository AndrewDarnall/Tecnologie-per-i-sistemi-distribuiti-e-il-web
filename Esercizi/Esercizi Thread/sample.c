/*
 * Scrivere un programma in C in cui tre thread aggiornano la stessa variabile globale condivisa sample, inizialmente posta a 50.
 *
 * Ogni thread ciclicamente genera un numero casuale compreso tra 10 e 90 e prova a sovrascrivere il valore corrente di sample. 
 * Il thread mostra in output un messaggio che segue il modello “Sono il thread X: sample valeva Y, adesso vale Z”, con opportuni valori al posto di X, Y e Z.
 * Se il valore casuale generato è uguale al valore attuale di sample il thread termina il suo ciclo, comunicando in output la propria terminazione.
 * Quando tutti i thread sono terminati, il programma principale termina e mostra in output il valore finale di sample.
 */

/* Coded By Helias (Stefano Borzì) */
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#include <unistd.h>

int sample = 50;
pthread_mutex_t mutex;
pthread_t t1, t2, t3;

void *handle_sample(void *arg) {

	int n;

	while (1) {

		usleep(rand() % 5000);

		pthread_mutex_lock(&mutex);

		n = ((rand() % 90) + 10);

		if (n != sample) {
			printf("Sono il thread %s,sample valeva %d, ora vale %d \n",  (char *)arg, sample, n);
			sample = n;
			pthread_mutex_unlock(&mutex);
		}
		else {
			printf("Sono il thread %s, sample: %d n: %d EXIT \n", (char *)arg, sample, n);
			pthread_mutex_unlock(&mutex);
			pthread_exit(NULL); // return;
		}
	}
}

int main() {
	srand(time(NULL));

	pthread_mutex_init(&mutex, NULL);

	pthread_create(&t1, NULL, (void *)handle_sample, (void *) "a");
	pthread_create(&t2, NULL, (void *)handle_sample, (void *) "b");
	pthread_create(&t3, NULL, (void *)handle_sample, (void *) "c");

	pthread_join(t1, NULL);
	pthread_join(t2, NULL);
	pthread_join(t3, NULL);

	return 0;
}
