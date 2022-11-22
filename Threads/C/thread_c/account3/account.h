/* account.h */

#include <pthread.h>
#include <unistd.h>

#define MSLEEPRAND(x) (usleep(rand() % ((x)*1000)))

typedef void *(*pthread_func)(void *);

typedef struct {
   int val;                      // saldo
   int n_ops;                    // n. operazioni compiute
   pthread_mutex_t mutex;        // per eseguire operazioni in mutex
   pthread_cond_t disponibilita; // attende disponibilita` per prelievo
} account_t;

void init_acc(account_t *acc);

int get_acc_val(account_t *acc);

int add_acc(account_t *acc, int delta);
int sub_acc(account_t *acc, int prel);
