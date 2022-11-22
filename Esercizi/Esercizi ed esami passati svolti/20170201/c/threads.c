#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#include <signal.h>

typedef struct pdata {
	int tid;
	int *sample;
	pthread_mutex_t *mux;
} pdata_t;

void *incSmpl(void *pdata) {
	pdata_t *pd = (pdata_t *)pdata;
	int v = 0;

	while (1) {
		v = (rand() % 81) + 10;

		if (*(pd->sample) == v)
			break;

		pthread_mutex_lock(pd->mux);
		printf("Sono thread %d: sample valeva %d, ora vale %d\n", pd->tid, *(pd->sample), v);

		*(pd->sample) = v;

		pthread_mutex_unlock(pd->mux);
	}

	printf("thread %d done, kill :(\n", pd->tid);
	pthread_exit(NULL);
}

void freeM(pdata_t *data) {
	pthread_mutex_destroy(data[0].mux);
	free(data[0].sample);
	free(data);
}

int main() {
	pthread_t thd[3] = {0};
	pthread_mutex_t *pmux = malloc(sizeof(pthread_mutex_t));
	pdata_t *pdata = malloc(sizeof(pdata_t)*3);
	int *sample = malloc(sizeof(int));
	int ret;

	if (pmux == NULL || pdata == NULL || sample == NULL) {
		fprintf(stderr, "alloc err\n");
		return 1;
	}

	srand(time(0));
	pthread_mutex_init(pmux, NULL);

	*sample = 50;

	for (int i=0; i<3; ++i) {
		pdata[i].tid = i;
		pdata[i].sample = sample;
		pdata[i].mux = pmux;

		ret = pthread_create(&thd[i], NULL, &incSmpl, (void *)&pdata[i]);
		if (ret != 0) {
			fprintf(stderr, "thd err\n");

			if (i > 0)
				goto kill;

			return 1;
		}
	}

	for (int i=0; i<3; ++i)
		pthread_join(thd[i], NULL);

	freeM(pdata);
	return 0;

kill:
	for (int i=0; i<3; ++i)
		pthread_kill(thd[i], SIGTERM);

	freeM(pdata);
	return 1;
}
