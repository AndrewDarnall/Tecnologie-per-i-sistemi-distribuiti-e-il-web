/* account_main.c */

#include <pthread.h>
#include <stdio.h>
#include "account.h"

account_t an_acc;

void * preleva()
{
	int i;
	for (i = 0; ;i++) {
		add_acc(&an_acc, -200);
		printf("%d\n", get_acc_val(&an_acc));
	}
}

void * versa()
{
	int i;
	for (i = 0; ;i++) {
		add_acc(&an_acc, 100);
		printf("\t\t%d\n", get_acc_val(&an_acc));
	}

}

int main(int argc, char * argv[])
{
	pthread_t t_prel, t_vers;
	init_acc(&an_acc);
	pthread_create(&t_prel, NULL, preleva, NULL);
	pthread_create(&t_vers, NULL, versa, NULL);
	// servirebbero join(), ma i thread secondari non terminano
	pthread_exit(NULL);
}