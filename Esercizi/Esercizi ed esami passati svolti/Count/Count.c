#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<unistd.h>
#include<time.h>

pthread_mutex_t mutex=PTHREAD_MUTEX_INITIALIZER;
int x=0;
void*incrementa1(void*dummy)
{
	int cnt=0;
	while(1)
	{
        //usleep(1000000);
		pthread_mutex_lock(&mutex);
		if(x>=301)
		{
			printf("A -> cnt=%d\n",cnt);
			pthread_mutex_unlock(&mutex);
			pthread_exit(NULL);
		}
		else
		{
			x++;
			cnt++;
			printf("A -> x=%d\n",x);
			printf("A -> cnt=%d\n",cnt);
		}
		pthread_mutex_unlock(&mutex);
	}
	return NULL;
}

void*incrementa2(void*dummy)
{
	int cnt=0;
	while(1)
	{
        //usleep(1000000);
		pthread_mutex_lock(&mutex);
		if(x>=301)
		{
			printf("B -> cnt=%d\n",cnt);
			pthread_mutex_unlock(&mutex);
			pthread_exit(NULL);
		}
		else
		{
			x++;
			cnt++;
			printf("\t\t\tB -> x=%d\n",x);
			printf("\t\t\tB -> cnt=%d\n",cnt);
		}
		pthread_mutex_unlock(&mutex);
	}
	return NULL;
}

int main(int argc, char**argv)
{
    pthread_t A,B;
    pthread_create(&A,NULL,(void*)&incrementa1,NULL);
    pthread_create(&B,NULL,(void*)&incrementa2,NULL);
    pthread_join(A,NULL);
    pthread_join(B,NULL);
    pthread_detach(A);
    pthread_detach(B);
}
