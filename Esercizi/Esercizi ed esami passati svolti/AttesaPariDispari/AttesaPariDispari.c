#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<unistd.h>
#include<time.h>

pthread_mutex_t mutex=PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t condition=PTHREAD_COND_INITIALIZER;
int m=0;

void*fun_1(void*dummy)
{
	int mtemp;
	while(1)
	{
		mtemp=m;
		printf("T1 -> %d\n",m);
		m=rand()%11;
		usleep(300*1000);
		pthread_mutex_lock(&mutex);
		if(mtemp%2!=0)
		{
			printf("T1 si mette in attesa\n");
			pthread_cond_wait(&condition,&mutex);
		}
		pthread_mutex_unlock(&mutex);
	}
}

void*fun_2(void*dummy)
{
	int mtemp;
	while(1)
	{
		mtemp=m;
		printf("\t\t\tT2 -> %d\n",m);
		m=rand()%11;
		usleep(300*1000);
		pthread_mutex_lock(&mutex);
		if(mtemp%2==0)
		{
			printf("\t\t\tT2 si mette in attesa\n");
			pthread_cond_wait(&condition,&mutex);
		}
		pthread_mutex_unlock(&mutex);
	}
}

void*fun_3(void*dummy)
{
    while(1)
    {
        usleep(2*1000000);
        pthread_cond_broadcast(&condition);
    }
}

int main(int argc,char**argv)
{
    pthread_t T1,T2,T3;
    srand(time(NULL));
    pthread_create(&T1,NULL,(void*)&fun_1,NULL);
    pthread_create(&T2,NULL,(void*)&fun_2,NULL);
    pthread_create(&T3,NULL,(void*)&fun_3,NULL);
    pthread_join(T1,NULL);
    pthread_join(T2,NULL);
    pthread_join(T3,NULL);
    pthread_detach(T1);
    pthread_detach(T2);
    pthread_detach(T3);
}
