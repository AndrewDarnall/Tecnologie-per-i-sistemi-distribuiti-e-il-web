/* account1.c */

#include <pthread.h>
#include "account.h"


void init_acc(account_t * a)
{
  a->val = 0;
  pthread_mutex_init(&(a->mutex), NULL);
  pthread_cond_init(&(a->disponibilita), NULL);
}

int get_acc_val(account_t * a)
{
  int v;
  pthread_mutex_lock(&(a->mutex));
  v = a->val;
  pthread_mutex_unlock(&(a->mutex));
  return(v);
}

void add_acc(account_t * a, int delta)
{
  int v;
  pthread_mutex_lock(&(a->mutex));   // senza, cf. account0, si perdono soldi

  v = a->val;  // update passa da var locale v,
  v += delta;  // imita update record DB, che non si
  a->val = v;  // puo' fare "sul posto", cioè su a->val

  pthread_mutex_unlock(&(a->mutex));
}
