/* account.c */
// monitor account, versione "while(...) wait()"
// funziona sempre, test con accountmain.c 

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
  pthread_mutex_lock(&(a->mutex));

  while (-delta > a->val)
    pthread_cond_wait( &(a->disponibilita), &(a->mutex) );
  a->val += delta;      // aggiorna saldo

  pthread_mutex_unlock(&(a->mutex));
}


