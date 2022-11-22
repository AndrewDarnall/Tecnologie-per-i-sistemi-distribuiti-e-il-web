#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <signal.h>
#include <unistd.h>
#include <time.h>

typedef struct pdata {
	pthread_mutex_t *mux;
	pthread_cond_t *cond;
	int tid;
	int *m;
} pdata_t;

void *p1(void *data) {
	pdata_t *pd = (pdata_t *)data;

	while (1) {
		pthread_mutex_lock(pd->mux);

		if (*(pd->m) >= 6) {
			pthread_cond_wait(pd->cond, pd->mux);

		} else {
			pthread_cond_broadcast(pd->cond);

			*(pd->m) = (random() % 10) + 1;

			printf("p1 set %d\n", *(pd->m));
			usleep(200*1000);
		}

		pthread_mutex_unlock(pd->mux);
	}
}

void *p2(void *data) {
	pdata_t *pd = (pdata_t *)data;

	while (1) {
		pthread_mutex_lock(pd->mux);

		if (*(pd->m) <= 5) {
			pthread_cond_wait(pd->cond, pd->mux);

		} else {
			pthread_cond_broadcast(pd->cond);

			*(pd->m) = (random() % 10) + 1;

			printf("p2 set %d\n", *(pd->m));
			usleep(200*1000);
		}

		pthread_mutex_unlock(pd->mux);
	}
}

void freeM(pdata_t *pdata) {
	pthread_cond_broadcast(pdata[0].cond);
	pthread_cond_destroy(pdata[0].cond);
	pthread_mutex_destroy(pdata[0].mux);
	free(pdata[0].m);
	free(pdata);
}

int main() {
	pthread_t thd[2];
	pdata_t *pdata = malloc(sizeof(pdata_t)*2);
	pthread_mutex_t *pmux = malloc(sizeof(pthread_mutex_t));
	pthread_cond_t *pcond = malloc(sizeof(pthread_cond_t));
	int *m = malloc(sizeof(int));
	void *(*fn_ptr[2])(void *) = {p1, p2};
	int ret;

	if (m == NULL || pdata == NULL || pmux == NULL || pcond == NULL) {
		fprintf(stderr, "alloc err\n");
		return 1;
	}

	srand(time(0));
	pthread_mutex_init(pmux, NULL);
	pthread_cond_init(pcond, NULL);

	*m = (rand() % 10) + 1;

	for (int i=0; i<2; ++i) {
		pdata[i].tid = i;
		pdata[i].m = m;
		pdata[i].mux = pmux;
		pdata[i].cond = pcond;

		ret = pthread_create(&thd[i], NULL, fn_ptr[i], (void *)&pdata[i]);
		if (ret != 0) {
			fprintf(stderr, "thread err\n");

			if (i > 0)
				goto kill_all;

			return 1;
		}
	}

	for (int i=0; i<2; ++i)
		pthread_join(thd[i], NULL);

	freeM(pdata);
	return 0;

kill_all:
	for (int i=0; i<2; ++i)
		pthread_kill(thd[i], SIGTERM);

	freeM(pdata);
	return 1;
}
