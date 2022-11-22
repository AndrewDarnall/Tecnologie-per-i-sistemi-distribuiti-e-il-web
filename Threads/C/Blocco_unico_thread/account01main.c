/* account01_main.c */

#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

#include "account.h"


account_t an_acc;
unsigned n_vers = 0;
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

void * versa(void * arg)
{
	while (1) {
		add_acc(&an_acc, 10);
		pthread_mutex_lock(&lock);
		printf("%d: %d\n", ++n_vers, get_acc_val(&an_acc));
		pthread_mutex_unlock(&lock);

// a ogni ciclo, printf() deve mostrare che il saldo del conto
// e` esattamente 10*n_vers, cio` si avra` a patto che l'operazione
// add_acc() sia "serializzabile", cioè "atomica"
// rispetto alla concorrenza (vedi commenti in account0.c e account1.c)

		usleep(5000);
	}

}

int main(int argc, char * argv[])
{
	pthread_t t_vers1, t_vers2;
	init_acc(&an_acc);
	pthread_create(&t_vers1, NULL, versa, NULL);
	pthread_create(&t_vers2, NULL, versa, NULL);
	// servirebbero join(), ma i thread secondari non terminano
	pthread_exit(NULL);
}