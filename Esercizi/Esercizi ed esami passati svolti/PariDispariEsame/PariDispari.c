#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<pthread.h>
#include<time.h>

pthread_mutex_t mutex=PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t pari=PTHREAD_COND_INITIALIZER;
pthread_cond_t dispari=PTHREAD_COND_INITIALIZER;
int m=0;

void*fun_p(void*dummy)
{
	while(1)
	{
		usleep(300*1000);
		pthread_mutex_lock(&mutex);
		if(m%2==0)
		{
			printf("TP -> %d\n",m);
			do{
				m=rand()%10;
			}while(m%2==0);
			pthread_cond_signal(&dispari);
		}
		else
		{
			pthread_cond_wait(&pari,&mutex);
		}
		pthread_mutex_unlock(&mutex);
	}
	pthread_exit(NULL);
}

void*fun_d(void*dummy)
{
	while(1)
	{
		usleep(300*1000);
		pthread_mutex_lock(&mutex);
		if(m%2!=0)
		{
			printf("\t\t\tTD -> %d\n",m);
			m=rand()%10;
			pthread_cond_signal(&pari);
		}
		else
		{
			pthread_cond_wait(&dispari,&mutex);
		}
		pthread_mutex_unlock(&mutex);
	}
	pthread_exit(NULL);
}

int main(int argc,char**argv)
{
	pthread_t TP,TD;
	srand(time(NULL));
	pthread_create(&TP,NULL,(void*)&fun_p,NULL);
	pthread_create(&TD,NULL,(void*)&fun_d,NULL);
	pthread_join(TP,NULL);
	pthread_join(TD,NULL);
	pthread_detach(TP);
	pthread_detach(TD);
}
