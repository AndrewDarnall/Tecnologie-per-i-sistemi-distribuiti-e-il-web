/* account3.c */

#include <pthread.h>
#include "account.h"

void init_acc(account_t * acc)   // va eseguita "fuori da"
{                                // thread
    pthread_mutex_init(&(acc->mutex), NULL);
    acc->val = 0;
    acc->n_ops = 0;
    pthread_cond_init(&(acc->disponibilita), NULL);
}

int add_acc(account_t *acc, int vers)  // op. atomica
{
    if (vers < 0)   // che fare con versamento richiesto < 0? 
        vers = 0;   // qui si opta per neutralizzarlo
    pthread_mutex_lock(&(acc->mutex));
    acc->val += vers;
    ++acc->n_ops;
    pthread_cond_broadcast(&(acc->disponibilita));
    pthread_mutex_unlock(&(acc->mutex));
    return acc->val;
}

int sub_acc(account_t *acc, int prel)  // op. atomica
{
    if (prel < 0)   // che fare con prelievo richiesto < 0? 
        prel = 0;   // qui si opta per neutralizzarlo
    pthread_mutex_lock(&(acc->mutex));
    while (acc->val < prel)
        pthread_cond_wait(&(acc->disponibilita), &(acc->mutex));
    acc->val -= prel;
    ++acc->n_ops;
    pthread_mutex_unlock(&(acc->mutex));
    return acc->val;
}