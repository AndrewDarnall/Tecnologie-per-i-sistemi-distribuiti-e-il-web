/* account2.c */
// monitor account, versione "if(...) wait()"
// funziona solo per 1 thread che preleva 1'unita` di valuta

#include <pthread.h>
#include "account.h"


void init_acc(account_t * a)
{
  a->val = 0;
  pthread_mutex_init(&(a->mutex), NULL);
  pthread_cond_init(&(a->disponibilita), NULL);
}

int get_acc_val(account_t * a) // restituisce saldo conto
{
  int v;
  pthread_mutex_lock(&(a->mutex));
  v = a->val;
  pthread_mutex_unlock(&(a->mutex));
  return(v);
}

void add_acc(account_t * a, int delta)
{
  pthread_mutex_lock(&(a->mutex));
  if (delta>0)        // solo se delta>0
    a->val += delta;  // aggiungilo al saldo
  pthread_cond_signal(a->disponibilita);
  pthread_mutex_unlock(&(a->mutex));
}

void sub_acc(account_t * a, int delta)
{
  pthread_mutex_lock(&(a->mutex));

  if (a->val <= 0)
    pthread_cond_wait( &(a->disponibilita), &(a->mutex) );

  // in uscita dal wait() si e` certi che a->val > 0
  // perche' il signal() che sveglia da questo wait() segue 
  // un incremento di un saldo che non puo` diventare negativo

  a->val -= 1;   // preleva sempre 1 per evitare a->val diventi <0

  pthread_mutex_unlock(&(a->mutex));
}
