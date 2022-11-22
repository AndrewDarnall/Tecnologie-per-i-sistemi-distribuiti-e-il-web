/* account.h */

typedef struct {
  int val;                      // saldo
  int n_ops;                    // n. operazioni compiute
  pthread_mutex_t mutex;        // per eseguire operazioni in mutex
  pthread_cond_t disponibilita; // attende disponibilita` per prelievo
} account_t;

void init_acc(account_t *acc);

int get_acc_val(account_t *acc);

int add_acc(account_t *acc, int delta);   // per prelievo: delta<0
