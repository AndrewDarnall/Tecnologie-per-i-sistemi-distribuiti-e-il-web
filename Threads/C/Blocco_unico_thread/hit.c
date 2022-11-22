/* COMPITO DI ESAME
 *
 Una variabile intera x, inizializzata a 0, e` condivisa tra 2 thread tA, tB. 
 Ogni thread dispone di una variabile locale hit ed esegue le seguenti azioni:
 1. attende un numero casuale di ms 
    (N.B.: la chiamata usleep(n) attende per n microsecondi)
 2. se la variabile condivisa x > 500, 
    - allora scrive su stdout il valore di hit e termina 
     la propria esecuzione
    - altrimenti, incrementa x, incrementa la variabile locale hit e 
     ricomincia da (1)
 Il programma termina quando tutti i thread hanno terminato la propria esecuzione.
 Nel codice, proteggere opportunamente la variabile x dagli accessi concorrenti. 

 Tempo a disposizione: 30 minuti.
 */

/* hit.c */
/* Soluzione by Simone */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <ctype.h>
#include <time.h>
#include <unistd.h>
#include <string.h>

#include <sys/syscall.h>
#include <sys/types.h>

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
int x = 0;

void function_hit(void * arg)    // nelle chiamate, arg e` "A" oppure "B"
{

   char prompt[24];
   char * indent;

   // il prompt sara` "Thread A: " o (con indent opportuno) "Thread B: "
   indent = strcmp((char *) arg,"B") ? "" : "\t\t\t\t"; 
   sprintf(prompt, "%sThread %s: ", indent, (char *) arg);

   for(int i = 0; ;i++)
   {
       usleep(rand()%90000 + 1);   // ritardo casuale, da 1 a 50 ms
       pthread_mutex_lock(&mutex);
       if (x>=500) {
           printf("%s x=%d, hit= %d\n", 
                  prompt, x, i);
           pthread_mutex_unlock(&mutex);  // spesso questo unlock() si dimentica, 
           pthread_exit(NULL);            // causando blocco dell'altro thread!!! 
       } else {
           printf("%s x = %d\n", prompt, x );
           x++;
       }
       pthread_mutex_unlock(&mutex);
   }
}

typedef void * (* thread_fun) (void *);

int main(int argc, char * argv[])
{
  pthread_t tA, tB;
  srand(time(NULL));
	pthread_create(&tA, NULL, (thread_fun) function_hit, (void *) "A");
	pthread_create(&tB, NULL, (thread_fun) function_hit, (void *) "B");
  pthread_join(tA, NULL);
  pthread_join(tB, NULL);
	printf("***Processo padre terminato***\n\n");
}


