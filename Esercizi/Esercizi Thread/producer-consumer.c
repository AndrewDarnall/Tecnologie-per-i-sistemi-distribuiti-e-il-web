#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

typedef struct
{
	int max, size, head, tail;
	int *data;
	pthread_mutex_t mutex;
	pthread_cond_t for_data, for_space;
} t_queue;

void init_queue(t_queue *q, int max)
{
	q->size = q->head = q->tail = 0;
	q->max = max;
	q->data = (int *)malloc(max*sizeof(int));
	
	pthread_mutex_init(&q->mutex, NULL);
	pthread_cond_init(&q->for_data, NULL);
	pthread_cond_init(&q->for_space, NULL);
}

void put(t_queue *q, int value)
{
	pthread_mutex_lock(&q->mutex);
	
	while(q->size == q->max)
	{
		printf("Waiting for space...\n");
		pthread_cond_wait(&q->for_space, &q->mutex);
	}
	
	q->data[q->tail] = value;
	q->size++;
	q->tail = (q->tail + 1) % q->max;
	
	pthread_cond_broadcast(&q->for_data);
	
	pthread_mutex_unlock(&q->mutex);
}

void get(t_queue *q, int *value)
{
	pthread_mutex_lock(&q->mutex);
	
	while(q->size == 0)
	{
		printf("Waiting for data...\n");
		pthread_cond_wait(&q->for_data, &q->mutex);
	}
	
	*value = q->data[q->head];
	q->size--;
	q->head = (q->head + 1) % q->max;
	
	pthread_cond_broadcast(&q->for_space);
	
	pthread_mutex_unlock(&q->mutex);
}

void delete_queue(t_queue *q)
{
	q->max = q->size = q->head = q->tail = 0;
	free(q->data);
}

void *producer_function(void *p)
{
	int value;
	t_queue *q = (t_queue *)p;
	while(1)
	{
		value = rand() % 100;
		put(q, value);
		printf("Get: %d\n", value);
		sleep(3);
	}
}

void *consumer_function(void *p)
{
	int value;
	t_queue *q = (t_queue *)p;
	while(1)
	{
		get(q, &value);
		printf("Put: %d\n", value);
		sleep(1);
	}
}

int main()
{
	t_queue q;
	pthread_t consumer, producer;
	
	init_queue(&q, 50);
	pthread_create(&producer, NULL, producer_function, &q);
	pthread_create(&consumer, NULL, consumer_function, &q);
	
	pthread_join(producer, NULL);
	pthread_join(consumer, NULL);
	
	delete_queue(&q);
	
	return 0;
}
