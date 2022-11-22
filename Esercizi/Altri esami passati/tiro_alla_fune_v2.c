/*
    Scrivere un programma in C che simuli un incontro di “tiro alla fune” tra due 2 thread “giocatori” tp[0], tp[1].

    E’ data una variabile globale intera posizione (con valore iniziale 0) condivisa da tutti i thread. 
    Sono date inoltre due variabili globali intere, vittorie_tp0 e vittorie_tp1.

    Ogni thread giocatore esegue un ciclo in cui:
    - genera un intero casuale recupero compreso tra 0 e 3
    - genera un intero casuale forza compreso tra 0 e 5
    - attende recupero secondi 

    - se tp[0]:
        - se posizione >= 10 riconosce la vittoria di tp[1] e: 
            - incrementa vittorie_tp1
            - setta posizione = 0
            - sveglia tp[1]
        -altrimenti: 
            - decrementa posizione di forza
            - se posizione <= -10 ha vinto, e si mette in attesa di tp[1]
            
    - se tp[1]:
        - se posizione <= -10 riconosce la vittoria di tp[0] e: 
            - incrementa vittorie_tp0
            - setta posizione = 0
            - sveglia tp[0]
        - altrimenti:
            - incrementa posizione di forza
            - se posizione >= 10 ha vinto, e si mette in attesa di tp[0]

    (Opzionale) quando uno dei giocatori ha raggiunto 10 vittorie interrompere il gioco, 
    entrambi i giocatori tp[0], tp[1] devono aver terminato la loro esecuzione, 
    e la funzione main() se ne deve accorgere scrivendo 
    sullo standard output il giocatore che ha totalizzato più vittorie.
*/


#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <time.h>

#define T0_OP 0
#define T1_OP 1

pthread_t thread[2]; //array di pthread
pthread_mutex_t mutex;

int pos = 0; //posizione della corda
int threads_wins[2] = {0}; //array di numero vittorie

pthread_cond_t vittoria_t; //variabile di condizione su cui attendere

const int thread_limit = 10; //limite da non superare per non perdere

int get_rand_numb(int start_range_incl, int end_range_incl){
    return (rand()%(end_range_incl - start_range_incl + 1)) + start_range_incl;
}

void * game_routine(void * thread_numb){

    int* thread_id = (int*)(thread_numb); //id del thread che esegue la game_routine()
    int other_thread_id = 1 - (*thread_id); //id dell'altro thread 
    int force_sign = (*thread_id == T0_OP) ? -1 : 1; //segno della forza con cui tirare
    int thread_fact = (*thread_id == T0_OP) ? 1 : -1; //fattore per cambiare "sistema di riferimento" quando dobbiamo
    						      //constatare che l'altro thread ha vinto

    while(1){
        int rest_time = get_rand_numb(0,3); //rest_time viene inizializzato con un numero randomico tra 0 e 5 estremi inclusi
        int forza = force_sign * get_rand_numb(0,5); //force_sign viene inizializzato con un numero randomico tra 0 e 5 estremi inclusi
        printf("Sono il thread [%d] e mi addormenterò per %ds z..Z.ZZZ\n", *thread_id, rest_time);

        sleep(rest_time);

        pthread_mutex_lock(&mutex); //lock prima di una regione critica

        if(threads_wins[0]>= 10 || threads_wins[1] >= 10){
            pthread_mutex_unlock(&mutex); //unlock prima di uscire per evitare deadlock
            return NULL;
        }
        //Ha vinto l'altro thread, ovvero, ho perso?
        if((thread_fact * pos) >= thread_limit){ //vince l'altro thread se la posizione assoluta è >= 10 
                                                //(cambio segno solo se sono il thread 1 poichè non riuscirei ad accorgermi che ho perso )
            threads_wins[other_thread_id]+=1; //si incrementa la varaibile delle vittorie dell'altro thread
            pos = 0; //si predispone una nuova partita
            printf("Sono il thread [%d] il valore di pos è %d e sto per svegliare il thread [%d]\n", *thread_id, pos, other_thread_id);
            pthread_cond_signal(&vittoria_t); //sveglio il thread vincitore

        }else{

            pos +=forza; //aggiorniamo pos aggiungendo il valore di forza
            printf("Sono il thread [%d] e il valore di force è %d,      il valore di pos è %d\n", *thread_id, forza, pos);

            //l'altro non ha vinto, dunque, ho vinto io?
            if(abs(pos) >= thread_limit){ //dato che non ho perso controllo se ho vinto verificando la posizione in modo assoluto 
                
                printf("Sono il thread [%d] il valore di pos è %d e aspetterò che qualcuno mi svegli!\n", *thread_id, pos);
                
                pthread_cond_wait(&vittoria_t, &mutex); //aspetto che l'altro thread si accorga che ho vinto e che quindi mi possa svegliare
                printf("Ho vinto! - thread[%d]\n", *thread_id); //notifica sulla vittoria

            }

        }

        pthread_mutex_unlock(&mutex); //unlock alla fine della regione critica

    }

}

int main(){
    srand(time(NULL));
    int t0_id = 0;
    int t1_id = 1;


    pthread_create(&thread[0], NULL, (void *)game_routine, (void *) &t0_id);
    pthread_create(&thread[1], NULL, (void *)game_routine, (void *) &t1_id);

    pthread_join(thread[0], NULL);
    pthread_join(thread[1], NULL);

    if(threads_wins[0] > threads_wins[1]){ //nel main si controlla chi ha vinto
        printf("Il thread t0 ha vinto!\n");
    }else{
        printf("Il thread t1 ha vinto!\n");
    }
    
    printf("Il processo padre è finito correttamente!\n");
    return 0;
}
