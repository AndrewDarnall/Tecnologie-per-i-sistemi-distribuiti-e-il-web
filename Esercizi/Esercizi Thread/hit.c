/* Una variabile intera x, inizializzata a 0, è condivisa tra 2 thread tA, tB. Ogni thread dispone di una variabile locale hit ed esegue le seguenti azioni:

- attende un numero casuale di ms (N.B.: la chiamata usleep(n) attende per n microsecondi)
- se la variabile condivisa x > 500, allora scrive su stdout il valore di hit e termina la propria esecuzione
- altrimenti, incrementa x, incrementa la variabile locale hit e ricomincia da (1)

Il programma termina quando tutti i thread hanno terminato la propria esecuzione.
Nel codice, proteggere opportunamente la variabile x dagli accessi concorrenti. */

/* hit.c */
/* By Simone */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <ctype.h>
#include <time.h>
#include <unistd.h>

#include <sys/syscall.h>
#include <sys/types.h>

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
int x = 0;

void function_hit(void * arg){

   char prompt[16];
   sprintf(prompt, "Thread %s->", (char *) arg);

   for(int i = 0; ;i++)
   {
       usleep(rand() % 100000 + 1);
       pthread_mutex_lock(&mutex);
       if (x>=500) {
           printf("%s x=%d, hit= %d\n", prompt, x, i);
           pthread_mutex_unlock(&mutex);
           pthread_exit(NULL);
       } else {
           printf("%s x = %d\n", prompt, x );
           x++;
       }
       pthread_mutex_unlock(&mutex);
   }
}

int main(int argc, char * argv[])
{
    pthread_t thread1, thread2;

   srand(time(NULL));
    pthread_create( &thread1, NULL,
        (void *) &function_hit, (void *) "a");

    pthread_create(&thread2, NULL,
        (void *) &function_hit, (void *) "b");

    pthread_join(thread1, NULL);
    pthread_join(thread2, NULL);
    printf("\nProcesso padre terminato\n\n");

    return(0);
}

