#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#include <signal.h>

typedef struct pdata {
	int *x;
	int hit;
	int tid;
	pthread_mutex_t *mux;
} pdata_t;

void *worker(void *data) {
	pdata_t *pd = (pdata_t *)data;

	while (*(pd->x) <= 500) {
		usleep((rand() % 2) * 1000);

		pthread_mutex_lock(pd->mux);
		if (*(pd->x) <= 500) {
			++(*pd->x);
			++pd->hit;
		}
		pthread_mutex_unlock(pd->mux);
	}

	printf("thread %d: %d hits\n", pd->tid, pd->hit);
	pthread_exit(NULL);
}

void freeM(pdata_t *data) {
	pthread_mutex_destroy(data[0].mux);
	free(data[0].x);
	free(data);
}

int main() {
	pthread_mutex_t *pmux = malloc(sizeof(pthread_mutex_t));
	pdata_t *pdata = malloc(sizeof(pdata_t)*2);
	int *x = calloc(1, sizeof(int));
	pthread_t thd[2];
	int ret;

	srand(time(0));

	for (int i=0; i<2; ++i) {
		pdata[i].x = x;
		pdata[i].hit = 0;
		pdata[i].mux = pmux;
		pdata[i].tid = i;

		ret = pthread_create(&thd[i], NULL, worker, (void *)&pdata[i]);
		if (ret != 0) {
			fprintf(stderr, "thd err\n");

			if (i > 0)
				goto kill_all;

			break;
		}
	}

	for (int i=0; i<2; ++i)
		pthread_join(thd[i], NULL);

	freeM(pdata);
	return 0;

kill_all:
	freeM(pdata);
	for (int i=0; i<2; ++i)
		pthread_kill(thd[i], SIGTERM);

	return 1;
}
