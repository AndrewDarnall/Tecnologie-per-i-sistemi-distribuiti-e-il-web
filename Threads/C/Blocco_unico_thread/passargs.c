/* passargs.c */

/* Mostra come passare un argomento (logicamente piu` arg.) *
 * alla funzione eseguita da un thread.                     *
 */

#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

struct thrd_arg {
	char * name;
	int val;
};

void * prnval(void * x)
{
	int i, arg;
	char * thrd_name;

	thrd_name = ((struct thrd_arg *) x)->name;
	arg = ((struct thrd_arg *) x)->val;
	
	for (i = 0; i < 8 ; i++) {
		printf("%s, i=%d, arg=%d\n", 
		       thrd_name, i, arg);
		sleep(1 + arg%2);   // attese diverse secondo arg
	}
	return NULL;
}

int main(int argc,char *argv[])
{
	pthread_t t1, t2;
	struct thrd_arg x1, x2;

	x1.name =       "Thread 1"; x1.val = 5;
	x2.name = "\t\t\tThread 2"; x2.val = 4;
	pthread_create(&t1, NULL, prnval, (void *) &x1);
	pthread_create(&t2, NULL, prnval, (void *) &x2);
	pthread_join(t1,NULL);
	pthread_join(t2,NULL);	
}
