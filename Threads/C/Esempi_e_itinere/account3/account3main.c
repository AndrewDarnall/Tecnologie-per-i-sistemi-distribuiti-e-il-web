/* account3main.c */

// I thread A,B versano $100 rep volte; C,D prelevano $50 2*rep volte 

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#include "account.h"

account_t an_acc;
int rep = 20;

void *versa_task(void *arg)         // stress test del codice: sul conto
{                                   // an_acc si opera un versamento ogni
    for (int i = 0; i < rep; i++)   // 100ms (in media) per rep volte;
    {                               // a questo scopo si introduce un
        MSLEEPRAND(200);            // ritardo casuale tra 0 e 200 ms 
        int res = add_acc(&an_acc, 100);   // versamento (operazione atomica)
        printf("%s(%d): +100 ==> $%d\n",   // traccia del versamento +100 
               (char *)arg, i+1, res    ); // stampa ciclo (i+1) e 
    }                                      // il saldo del conto (res)
    return NULL;
}

void *preleva_task(void *arg)       // stress test: sul conto
{                                   // an_acc si opera un prelievo ogni
    for (int i = 0; i < 2*rep; i++) // 50ms (in media) per 2*rep volte;
    {
        MSLEEPRAND(100);            // ritardo casuale tra 0 e 100 ms 
        int res = sub_acc(&an_acc, 50);   // prelievo (operazione atomica)
        printf("%s(%d): -50 ==> $%d\n",   // traccia del prelievo -50 
               (char *)arg, i+1, res   );
    }
    return NULL;
}

// NB: gli stress test sarebbero anche più stringenti con ritardi inferiori
// ai 100-200 ms, o nulli; ma l'output risulterebbe illeggibile

// in effetti, per causare eventuali corse critiche (a fini di testong), 
// occorre che le operazioni (presunte) atomiche durino abbastanza da essere 
// eseguite contemporaneamente; insomma, anche in esse vanno inseriti dei
// ritardi


int main(int argc, char *argv[])
{
    if (argc == 2)
        rep = atoi(argv[1]) > 0 ? atoi(argv[1]) : rep;

    pthread_t A, B, C, D;
    init_acc(&an_acc);
    srand(time(NULL));
    pthread_create(&A, NULL, (pthread_func)versa_task, "A");
    pthread_create(&B, NULL, (pthread_func)versa_task, "\t\t\tB");
    pthread_create(&C, NULL, (pthread_func)preleva_task, "\t\t\t\t\t\tC");
    pthread_create(&D, NULL, (pthread_func)preleva_task, "\t\t\t\t\t\t\t\t\tD");

    pthread_join(A, NULL);
    pthread_join(B, NULL);
    pthread_join(C, NULL);
    pthread_join(D, NULL);
}
