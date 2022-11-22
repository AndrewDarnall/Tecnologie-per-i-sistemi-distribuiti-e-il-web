/* account0.c */
// implementa operazioni non atomiche su account_t
// rispetto a account1.c, si notera` maggior velocita`
// (maggior parallelismo senza mutua esclusione, ma corse critiche!)

#include <pthread.h>
#include <unistd.h>
#include "account0.h"

void init_acc(account_t * acc)   // va eseguita "fuori da"
{                                // thread
  pthread_mutex_init(&(acc->mutex), NULL);
  acc->val = 0;
  acc->n_ops = 0;
  pthread_cond_init(&(acc->disponibilita), NULL);
}

int get_acc_val(account_t * acc)
{
  int v = acc->val;
  return v;
}

int add_acc(account_t * acc, int delta) // dovrebbe essere atomica!
{
  // qui occorrerebbe sezione critica (vedi main())!
  // per rendere questa funzione atomica

  usleep(10000);    // simula ritardo lettura da DB
  int v = acc->val; // legge saldo in var locale v
  v += delta;       // incrementa var locale
  usleep(10000);    // simula ritardo scrittura su DB
  acc->val = v;     // salva risultato in v sul saldo

  // qui manca chiusura eventuale sezione critica
  
  return v;
}
