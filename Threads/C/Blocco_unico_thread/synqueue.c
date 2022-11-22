/* synqueue.c */

/* ADT queue, synchronized nel senso del Java, cioe` 
 * con operazioni atomiche. Si tratta quindi di un monitor.
 * 
 * Funzionamento: 
 * a. chi non trova dati, fa wait su for_data  
 * b. chi produce un dato, sveglia (BROADCAST) tutti i thread in WAIT 
 * 
 * Il thread svegliato non ha modo di essere certo 
 * di avere ancora a disposizione il dato per cui e`stato svegliato,
 * perche' c'e`piu`di un thread consumatore attivo, in generale;
 * per questo, il WAIT() e` protetto da while()
 */

#include <stdlib.h>
#include <pthread.h>
#include "synqueue.h"

// long e' il tipo degli elementi della coda,
// si potrebbe rendere generico con un typedef

void * q_init(syn_queue_t * q, int size)
{
	q->num = q->head = q->tail = 0;
	q->size = size;
	q->data = (long *) malloc(sizeof(long) * size);
	pthread_mutex_init(&q->mutex, NULL);
	pthread_cond_init(&q->for_space, NULL);
	pthread_cond_init(&q->for_data, NULL);
	return (q->data);
}
////

void q_free(syn_queue_t * q)
{
  if (q->data != NULL)
    free(q->data);
}

long q_get(syn_queue_t * q)
{
	long val;
	LOCK(&q->mutex);
	while (q->num == 0)  // try to change while/if -> error!
		WAIT(&q->for_data, &q->mutex);
	val = q->data[q->tail];
	q->tail = (q->tail + 1) % q->size;
	q->num--;
	BROADCAST(&q->for_space);
	UNLOCK(&q->mutex);	
	return val;
}

void q_put(syn_queue_t * q, long val)
{
	LOCK(&q->mutex);
	while (q->num == q->size)
		WAIT(&q->for_space, &q->mutex);
	q->data[q->head] = val;
	q->head = (q->head + 1) % q->size;
	q->num++;
	BROADCAST(&q->for_data); // after broadcast(), also need unlock()
	UNLOCK(&q->mutex);       // or threads blocked on wait
}                            // will not proceed

