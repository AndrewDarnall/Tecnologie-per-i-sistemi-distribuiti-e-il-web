/* show.c */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>

extern pthread_t thrd[];
extern int cons_n;

long upto = -1;

char * indent[5] = {	// per sfalsare output tra thread
	"", 
	"\t\t\t", 
	"\t\t\t\t\t\t", 
	"\t\t\t\t\t\t\t\t\t", 
	"\t\t\t\t\t\t\t\t\t\t\t\t"
};


int get_myindex()
{
	int i;
	for (i = 0; thrd[i] != pthread_self() ; i++)
		if (i > cons_n+1) exit(-2);   // thread not found (shouldn't)
	return(i);
}


#include "synqueue.h"

#define PRINT_Q(q) \
	printf("[%d/%d:%d", q->num, q->head, q->tail);\
	if (q->n_wait_sp + q->n_wait_dt )\
		printf(" %d-%d", q->n_wait_sp, q->n_wait_dt);\
	printf("]\n");


void show_val(char * why, long int val, void * qp)
{
	int thread_n = get_myindex();		
	printf("%s$%d %s ", indent[thread_n], thread_n, why);
	printf("%ld ", val);
#ifdef debug
	syn_queue_t * q = (syn_queue_t *) qp;
	PRINT_Q(q);
	if (strcmp(why,"put")==0) // if why is "put"
	if (++upto != val) {      // check consistency
		printf("%s$%d exiting, expected %ld\n",
			   indent[thread_n], thread_n, val);
		exit(-1);
    }
#endif
    printf("\n");
}

void show_q(char * why, void * p)
{
	syn_queue_t * q = (syn_queue_t *) p;
	int thread_n = get_myindex();	
	
	printf("%s$%d %s ", indent[thread_n], thread_n, why);
	PRINT_Q(q);
}

// unused
void show_str(char * why, char * msg)
{
	int thread_n = get_myindex();	

	printf("%s%d%s: %s\n",
	       indent[thread_n], thread_n, why, msg);
}
