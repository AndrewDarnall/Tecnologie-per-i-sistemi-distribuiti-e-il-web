/* synqueue1.c */

/* ADT queue, synchronized nel senso del Java, cioe` 
 * con operazioni atomiche. Si tratta quindi di un monitor.
 * 
 * Funzionamento: 
 * a. chi non trova dati, fa wait su for_data  
 * b. chi produce un dato, sveglia (SIGNAL) 1 dei thread in WAIT 
 * 
 * Differenze vs. synqueue.c: si sveglia 1 solo dei thread in
 * attesa e si usano le var di stato n_wait_sp, n_wait_dt per
 * evitare SIGNAL() inutili
 *
 * Come per synqueue.c, il thread svegliato non puo` essere certo
 * di avere ancora a disposizione il dato per cui e`stato svegliato,
 * perche' c'e`piu`di un thread consumatore attivo, in generale;
 * per questo, il WAIT() e` protetto da while()
 */

#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

#include "synqueue.h"


void * q_init(syn_queue_t * q, int size)
{
	q->num = q->head = q->tail = 0;
	q->size = size;
	q->data = (long *) malloc(sizeof(long) * size);
	pthread_mutex_init(&q->mutex, NULL);
	pthread_cond_init(&q->for_space, NULL);
	pthread_cond_init(&q->for_data, NULL);
	q->n_wait_dt = q->n_wait_sp = 0;
	return (q->data);
}

void q_free(syn_queue_t * q)
{
  if (q->data != NULL)
    free(q->data);
}
////

long q_get(syn_queue_t * q)
{
	long val;
	LOCK(&q->mutex);
	while (q->num == 0) {
		q->n_wait_dt++;
		WAIT(&q->for_data, &q->mutex);
		q->n_wait_dt--;
	}
	q->num--;
	val = q->data[q->tail];
	q->tail = (q->tail + 1) % q->size;
	if (q->n_wait_sp > 0)           // check on n.threads waiting for space
		SIGNAL(&q->for_space);      // after signal also need unlock()
	UNLOCK(&q->mutex);              // or threads blocked on wait
	return val;                     // will not proceed
}                                       

void q_put(syn_queue_t * q, long val)
{
	LOCK(&q->mutex);
	while (q->num == q->size) {
		q->n_wait_sp++;
		WAIT(&q->for_space, &q->mutex);
		q->n_wait_sp--;
	}
	q->num++;
	q->data[q->head] = val;
	q->head = (q->head + 1) % q->size;
	if (q->n_wait_dt > 0)           // check on n.threads waiting for data
		SIGNAL(&q->for_data);       // after signal also need unlock() 
	UNLOCK(&q->mutex);              // or threads blocked on wait
}									// will not proceed
