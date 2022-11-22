/* Una variabile intera x, inizializzata a 0, è condivisa tra 2 thread tA, tB. Ogni thread dispone di una variabile locale hit ed esegue le seguenti azioni:

- attende un numero casuale di ms (N.B.: la chiamata usleep(n) attende per n microsecondi)
- se la variabile condivisa x > 500, allora scrive su stdout il valore di hit e termina la propria esecuzione
- altrimenti, incrementa x, incrementa la variabile locale hit e ricomincia da (1)

Il programma termina quando tutti i thread hanno terminato la propria esecuzione.
Nel codice, proteggere opportunamente la variabile x dagli accessi concorrenti. */

/* Coded By Helias (Stefano Borzì) */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <pthread.h>
#include <unistd.h>

int x;
pthread_mutex_t mutex;
pthread_t tA, tB;

void *t_a() {

	int hit = 0;

	while(1) {

		usleep(rand() % 5000);

		pthread_mutex_lock(&mutex);

		if (x >= 500) {
			printf("hit A: %d \n", hit);
			pthread_mutex_unlock(&mutex);
			pthread_exit(NULL);
		}
		else {
			printf("Thread a -> x: %d \n", x);
			hit++;
			x++;
		}

		pthread_mutex_unlock(&mutex);
	}

}

void *t_b() {

	int hit = 0;

	while(1) {

		usleep(rand() % 5000);

		pthread_mutex_lock(&mutex);

		if (x >= 500) {
			printf("hit B: %d \n", hit);
			pthread_mutex_unlock(&mutex);
			pthread_exit(NULL);
		}
		else {
			printf("Thread b -> x: %d \n", x);
			hit++;
			x++;
		}

		pthread_mutex_unlock(&mutex);
	}

}


int main() {

	srand(time(NULL));
	x = 0;

	pthread_mutex_init(&mutex, NULL);

	pthread_create(&tA, NULL, (void *)t_a, NULL);
	pthread_create(&tB, NULL, (void *)t_b, NULL);
	
	pthread_join(tA, NULL);
	pthread_join(tB, NULL);

	return 0;
}

