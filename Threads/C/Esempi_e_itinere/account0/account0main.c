/* account01main_v0.c */

// driver per account0, account1, version 0 
// Due thread versano 10 in loop infinito e 
// contano il n. di versamenti n_vers

#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <signal.h>

#include "account0.h" // definisce add_acc(), etc.
                     // che si presumono atomiche

account_t an_acc;
unsigned n_vers = 0;
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

void * versa_task(void * arg)
{
   int res;
   while (1)
   {                              // l'operazione add_acc() e` atomica,
      res = add_acc(&an_acc, 10); // non per proteggere an_acc, bensi`

      sleep(3);
      pthread_mutex_lock(&lock); // ma la si esegue in sezione critica,

      ++n_vers;                   // n_vers e assicurare che n_vers e an_acc
      printf("N. versamenti %4d,  ", n_vers);     // abbiano valori coerenti
      printf("Saldo %d$ ", res);
      printf("(should be %d$)\n", 10*n_vers);
      pthread_mutex_unlock(&lock);
//
// a ogni ciclo, printf() sopra deve mostrare che il saldo del conto vale
// esattamente 10*n_vers, cio` si avra` a patto che l'operazione
// add_acc() sia "serializzabile", cioè "atomica"
// rispetto alla concorrenza (vedi commenti in account0.c e account1.c);
// in altre parole, se i due thread eseguono add_acc() "contemporanente"
// l'effetto deve essere lo stesso che eseguirli in successione (add_acc(10) del
// thread 1, poi add_acc(10) del thread 2 o viceversa) e cioe` un incremento di 20
   }
}

int main(int argc, char *argv[])
{
   pthread_t t_vers1, t_vers2;
   init_acc(&an_acc);
   pthread_create(&t_vers1, NULL, versa_task, NULL);
   pthread_create(&t_vers2, NULL, versa_task, NULL);
   // servirebbero join(), ma i thread creati non terminano
   pthread_exit(NULL);
   // NB: se il main non termina con pthread_exit() esplicito,
   // termina con exit() implicito e arresta tutto, compresi
   // i thread (teoricamente) infiniti
}
