/**
 * 
 * - Soluzione proposta -> Andrew R. Darnall
 * 
 */ 
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;

int turn = 0;

void *procedure(void *index)
{

    int *in = (int*) index;
    int indx = *in;
    int position = 0;

    while(1) {

        usleep(500000);
        //sleep(1);
    
        pthread_mutex_lock(&mutex);

        if(turn == indx) {

            position += ((rand() % 10) + 1);

            printf("Current turn -> Thread[%d] - pos:\t%d\n",indx, position);

            if(position >= 100) {


                printf("Thread '%d' wins with '%d' points!\n",indx,position);
                turn = -1;        
                
                pthread_cond_broadcast(&cond);
                pthread_mutex_unlock(&mutex);
                
                break;

            }

            turn = 1 - indx;

        } else if(turn == -1) {

            break;

        }

        pthread_cond_broadcast(&cond);
        pthread_cond_wait(&cond,&mutex);
        pthread_mutex_unlock(&mutex);

    }

    pthread_exit(NULL);

}

int main(int argc, char** argv)
{

    pthread_t threads[2];
    int first = 0; int second = 1;

    printf("Gioco dell'oca! {Quack}\n");

    pthread_create(&threads[first], NULL, procedure, (void*)&first);
    pthread_create(&threads[second], NULL, procedure, (void*)&second);

    for(int i = 0; i < 2; i++) {
        pthread_join(threads[i],NULL);
    }    

    printf("Game over!\n");

    return EXIT_SUCCESS;
}