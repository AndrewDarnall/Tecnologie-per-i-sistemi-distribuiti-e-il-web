/* account.h */

typedef struct {
  int val;
  pthread_mutex_t mutex;        // per eseguire operazioni in mutex
  pthread_cond_t disponibilita; // attende disponibilita` per prelievo
} account_t;

int get_acc_val(account_t * a);

void add_acc(account_t * a, int delta);   // delta<0 implica prelievo

void init_acc(account_t * a);