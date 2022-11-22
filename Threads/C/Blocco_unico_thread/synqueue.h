/* synqueue.h */

/* implementazione coda di long int con array circolare */

#include "show.h"

typedef struct {
	int num, size;    // num (elementi presenti in coda), size (dim max coda)
	int head, tail;   // cursori a testa e coda array circolare
	long * data;      // punta a area dati
	pthread_mutex_t mutex;
	pthread_cond_t for_space, for_data;   // wait for space (prod) or data (cons)
	int n_wait_sp, n_wait_dt;   // # of prod waiting for space, # of  
} syn_queue_t;                      // cons waiting for data

void * q_init(syn_queue_t * q, int size);
void q_put(syn_queue_t * q, long val);
long q_get(syn_queue_t * q);
void q_free(syn_queue_t * q);

