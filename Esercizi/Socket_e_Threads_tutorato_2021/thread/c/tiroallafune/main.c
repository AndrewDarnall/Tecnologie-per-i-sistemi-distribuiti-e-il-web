// https://docs.google.com/document/d/1k_Hmqwzx3V0qOWaSugETHsoJS-fbR2BdknsCnpyxTqU/edit

#include <stdio.h>
#include <pthread.h>
#include <time.h>
#include <stdlib.h>
#include <unistd.h>

int position = 0;
int vittorie_tp0=0;
int vittorie_tp1=0;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond;


void * game(int* thread_id){
    while(1) {
        int recupero=rand()%4;
        int forza=rand()%6;

        sleep(recupero);

        pthread_mutex_lock(&mutex);

        if(vittorie_tp0 == 10 || vittorie_tp1 == 10) {
            pthread_mutex_unlock(&mutex);
            pthread_exit(NULL);
        }

        if(*thread_id==0){
            printf("Esecuzione thread_tp0\n");
            //thread_tp0
            if(position>=10){
                vittorie_tp1++;
                printf("Thread_tp1 vince\n");
                printf("Vittorie tp0: %d, Vittorie tp1: %d\n", vittorie_tp0, vittorie_tp1);
                position=0;
                pthread_cond_signal(&cond);
            }else{
                position -= forza;
                printf("Valore position (thread_tp0): %d\n", position);
                if(position<=-10)
                    pthread_cond_wait(&cond, &mutex);
            }
        } else {
            printf("Esecuzione thread_tp1\n");
            //thread_tp1
            if(position<=-10){
                vittorie_tp0++;
                printf("Thread_tp0 vince\n");
                printf("Vittorie tp0: %d, Vittorie tp1: %d\n", vittorie_tp0, vittorie_tp1);
                position=0;
                pthread_cond_signal(&cond);
            }else{
                position += forza; 
                printf("Valore position (thread_tp1): %d\n", position);
                if(position>=10)
                    pthread_cond_wait(&cond, &mutex);
            }
        }

        pthread_mutex_unlock(&mutex);
    }
}

int main() {
    pthread_t tp0, tp1;
    int thread_id_tp0 = 0;
    int thread_id_tp1 = 1;
    srand(time(NULL));
    pthread_cond_init(&cond, NULL);

    pthread_create(&tp0, NULL, (void *) game, (void *) &thread_id_tp0);
    pthread_create(&tp1, NULL, (void *) game, (void *) &thread_id_tp1);

    pthread_join(tp0, NULL);
    pthread_join(tp1, NULL);
    
    if(vittorie_tp0 > vittorie_tp1)
        printf("Il vincitore è tp0\n");
    else
        printf("Il vincitore è tp1\n");

    return 0;
}