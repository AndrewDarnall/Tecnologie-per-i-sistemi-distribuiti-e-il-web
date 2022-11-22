/*
 * Scrivere un programma in C in cui tre thread aggiornano la stessa variabile globale condivisa sample, inizialmente posta a 50.
 *
 * Ogni thread ciclicamente genera un numero casuale compreso tra 10 e 90 e prova a sovrascrivere il valore corrente di sample. 
 * Il thread mostra in output un messaggio che segue il modello “Sono il thread X: sample valeva Y, adesso vale Z”, con opportuni valori al posto di X, Y e Z.
 * Se il valore casuale generato è uguale al valore attuale di sample il thread termina il suo ciclo, comunicando in output la propria terminazione.
 * Quando tutti i thread sono terminati, il programma principale termina e mostra in output il valore finale di sample.
 */

#include <pthread.h>
#include <time.h>
#include <stdio.h>
#include <stdlib.h>

struct __thread_conf {
    unsigned int thread_id;
    int* value;
    pthread_mutex_t* mutex;
};

typedef struct __thread_conf thread_conf_t;

typedef int bool_t;

void* overwrite(thread_conf_t* config) {
    printf("[Thread %u] Initializing\n", config->thread_id);
    bool_t isNotSame = 1;
    while(isNotSame) {
        pthread_mutex_lock(config->mutex);
        int tmp = (rand()%81) + 10;
        if(*(config->value) == tmp) {
            printf("[Thread %u]: Valore uguale\n", config->thread_id);
            isNotSame = 0;
        }
        else {
            int oldValue = *(config->value);
            *(config->value) = tmp;
            printf("Sono il thread %u: sample valeva %d, adesso vale %d\n", config->thread_id, oldValue, *(config->value));
        }
        pthread_mutex_unlock(config->mutex);
    }
    printf("[Thread %u] Terminating\n", config->thread_id);

    pthread_exit(NULL);
}

int main() {
    printf("[Main thread] Initializing\n");
    pthread_t t1, t2, t3;
    srand(time(NULL));

    int value = 50;
    pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

    thread_conf_t t1conf;
    t1conf.thread_id = 1;
    t1conf.value = &value;
    t1conf.mutex = &mutex;

    thread_conf_t t2conf;
    t2conf.thread_id = 2;
    t2conf.value = &value;
    t2conf.mutex = &mutex;

    thread_conf_t t3conf;
    t3conf.thread_id = 3;
    t3conf.value = &value;
    t3conf.mutex = &mutex;

    pthread_create(&t1, NULL, (void*) overwrite, (void*) &t1conf);
    pthread_create(&t2, NULL, (void*) overwrite, (void*) &t2conf);
    pthread_create(&t3, NULL, (void*) overwrite, (void*) &t3conf);

    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    pthread_join(t3, NULL);

    printf("[Main thread] Terminating\n");
    return 0;
}
