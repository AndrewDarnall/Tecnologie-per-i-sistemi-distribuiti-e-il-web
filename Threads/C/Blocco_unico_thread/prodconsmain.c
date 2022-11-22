/* prodconsmain.c */

/* Thread producer-consumer */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <limits.h>
#include <pthread.h>

#include "synqueue.h"

#define MAXCONS 4

syn_queue_t queue;
int qsize, cons_n, delay;

pthread_t thrd[1+MAXCONS];
int indx[1+MAXCONS];

void * producer()
{
	long n, i = 0;

	for (;;) {
		n = rand() % (cons_n * qsize * 5) + 1;
		while (n-- > 0)
			q_put(&queue, i++);
		sleep(delay);
	}
}

void * consumer()
{
	long n;

	for (;;) {
		n = rand() % (2*qsize) + 1;
		while (n-- > 0)
			q_get(&queue);
		sleep(delay);
	}
}
////

int main(int argc, char *argv[])
{
	int i;

	if ( (argc != 3 && argc != 4) ||
	     (cons_n = atoi(argv[2])) > MAXCONS ) {
		printf("Usage: %s qsize #_consumers (<= %d) [delay]\n", 
		       argv[0], MAXCONS);
		exit(1);
	}
	delay = (argc == 4) ? atoi(argv[3]) : 1;
	srand((unsigned int) time(NULL));
	
	if ( q_init(&queue, qsize = atoi(argv[1]))
		== NULL)
		exit(2);
	for (i = 1; i <= cons_n; i++) 
		pthread_create(&thrd[i], NULL, consumer, NULL);
	pthread_create(&thrd[0],  NULL, producer, NULL);

	pthread_exit(NULL);
}
