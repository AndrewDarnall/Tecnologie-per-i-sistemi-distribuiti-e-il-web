/* Realizzare due thread A e B, che stampano rispettivamente i numeri pari e i numeri dispari (da 1 a 100). */

// in caso di errore compilare aggiungendo il parametro "-pthread", esempio: "gcc Thread.c -pthread -o ex"

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int x;
pthread_t a, b;
pthread_mutex_t mutex;


void *incr_pari()
{
	while(1)
	{
		pthread_mutex_lock(&mutex);
		
		if (x > 100)
			exit(0);
		
		if ((x % 2) != 0)
		{
			x++;
			printf("Thread A: %d\n", x);
		}

		pthread_mutex_unlock(&mutex);
	}
}

void *incr_dispari()
{
	while(1)
	{
		pthread_mutex_lock(&mutex);
		
		if (x > 99)
			exit(0);
		
		if ((x % 2) == 0)
		{
			x++;
			printf("Thread B: %d\n", x);
		}
		
		pthread_mutex_unlock(&mutex);
	}
}


int main()
{
	x = 0;
	
	pthread_mutex_t mutex;
	pthread_mutex_init(&mutex, NULL);

	pthread_create(&a, NULL, (void *)incr_pari, NULL);
	pthread_create(&b, NULL, (void *)incr_dispari, NULL);
	
	pthread_join(a, NULL);
	pthread_join(b, NULL);
	
	return 0;
}
