#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<unistd.h>
#include<time.h>

pthread_mutex_t mutex=PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t tp0wins=PTHREAD_COND_INITIALIZER;
pthread_cond_t tp1wins=PTHREAD_COND_INITIALIZER;
int posizione=0, vittorie_tp0=0, vittorie_tp1=0;

void*P1(void*dummy)
{
    int recupero, forza;
    while(1)
    {
        recupero=rand()%4;
        printf("P1 recupero %d\n",recupero);
        forza=rand()%6;
        printf("P1 forza %d\n",forza);
        usleep(recupero*100000);
        pthread_mutex_lock(&mutex);
        if(posizione>=10)
        {
            vittorie_tp1++;
            posizione=0;
            pthread_cond_signal(&tp1wins);
        }
        else
        {
            posizione-=forza;
            if(posizione<=-10)
            {
                printf("P1 vince!!\n");
                if(vittorie_tp0>=10)
                {
                    //printf("Ciao\n");
                    printf("numVittorie %d\n",vittorie_tp0);
                    pthread_mutex_unlock(&mutex);
                    pthread_exit(NULL);
                }
                else
                {
                    printf("numVittorie %d\n",vittorie_tp0+1);
                    if(vittorie_tp1>=10)
                    {
                        pthread_mutex_unlock(&mutex);
                        pthread_exit(NULL);
                    }
                    else
                    {
                        pthread_cond_wait(&tp0wins,&mutex);
                    }
                }
            }
        }
        pthread_mutex_unlock(&mutex);
    }
    //pthread_exit(NULL);
}

void*P2(void*dummy)
{
    int recupero, forza;
    while(1)
    {
        recupero=rand()%4;
        printf("\t\t\tP2 recupero %d\n",recupero);
        forza=rand()%6;
        printf("\t\t\tP2 forza %d\n",forza);
        usleep(recupero*100000);
        pthread_mutex_lock(&mutex);
        if(posizione<=-10)
        {
            vittorie_tp0++;
            posizione=0;
            pthread_cond_signal(&tp0wins);
        }
        else
        {
            posizione+=forza;
            if(posizione>=10)
            {
                printf("\t\t\tP2 vince!!\n");
                if(vittorie_tp1>=10)
                {
                    //printf("\t\t\tCiao\n");
                    printf("\t\t\tnumVittorie %d\n",vittorie_tp1);
                    pthread_mutex_unlock(&mutex);
                    pthread_exit(NULL);
                }
                else
                {
                    printf("\t\t\tnumVittorie %d\n",vittorie_tp1+1);
                    if(vittorie_tp0>=10)
                    {
                        pthread_mutex_unlock(&mutex);
                        pthread_exit(NULL);
                    }
                    else
                    {
                        pthread_cond_wait(&tp1wins,&mutex);
                    }
                }
            }
        }
        pthread_mutex_unlock(&mutex);
    }
    //pthread_exit(NULL);
}

int main(int argc, char**argv)
{
    pthread_t tp[2];
    srand(time(NULL));
    pthread_create(&tp[0],NULL,(void*)&P1,NULL);
    pthread_create(&tp[1],NULL,(void*)&P2,NULL);
    pthread_join(tp[0],NULL);
    pthread_join(tp[1],NULL);
    pthread_detach(tp[0]);
    pthread_detach(tp[1]);
    printf("\nIl P1 totalizzato %d vittorie!\n",vittorie_tp0);
    printf("Il P2 totalizzato %d vittorie!\n",vittorie_tp1);
    if(vittorie_tp0==vittorie_tp1)
    {
        printf("Pareggio!!\n");
    }
    else if(vittorie_tp0>vittorie_tp1)
    {
        printf("P1 Vince!!\n");
    }
    else
    {
        printf("P2 Vince!!\n");
    }
}
