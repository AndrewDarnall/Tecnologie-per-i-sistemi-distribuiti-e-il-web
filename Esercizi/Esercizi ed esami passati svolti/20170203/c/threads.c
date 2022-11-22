#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#include <signal.h>

typedef struct pdata {
	int tid;
	int *n;
	pthread_mutex_t *mux;
} pdata_t;

void *tE(void *pdata) {
	pdata_t *pd = (pdata_t *)pdata;
	int c = 0, v = 0;

	while (1) {
		usleep(200000);

		while((v = rand() % 50) % 2 != 0);

		pthread_mutex_lock(pd->mux);
		*(pd->n) += v;
#ifdef DEBUG
		printf("%d inc n %d c %d\n",pd->tid, *(pd->n),c);
#endif
		pthread_mutex_unlock(pd->mux);

		if ((++c == 10 && *(pd->n) % 2 == 0) || c == 1000)
			break;
	}

	printf("tE thread %d done, n=%d, c=%d, kill :O\n", pd->tid, *(pd->n), c);
	pthread_exit(NULL);
}

void *tO(void *pdata) {
	pdata_t *pd = (pdata_t *)pdata;
	int c = 0, v = 0;

	while (1) {
		usleep(200000);

		while((v = rand() % 50) % 2 == 0);

		pthread_mutex_lock(pd->mux);
		*(pd->n) += v;
#ifdef DEBUG
		printf("%d inc n %d c %d\n",pd->tid,*(pd->n),c);
#endif
		pthread_mutex_unlock(pd->mux);

		if ((++c == 10 && *(pd->n) % 2 != 0) || c == 1000)
			break;
	}

	printf("tO thread %d done, n=%d, c=%d, kill :O\n", pd->tid, *(pd->n), c);
	pthread_exit(NULL);
}

void freeM(pdata_t *data) {
	pthread_mutex_destroy(data[0].mux);
	free(data[0].n);
	free(data);
}

int main() {
	pthread_t thd[2] = {0};
	pthread_mutex_t *pmux = malloc(sizeof(pthread_mutex_t));
	pdata_t *pdata = malloc(sizeof(pdata_t)*2);
	int *n = malloc(sizeof(int));
	void *(*fn_ptr[2])(void *) = {tE, tO};
	int ret;

	if (pdata == NULL || n == NULL || pmux == NULL) {
		fprintf(stderr, "alloc err\n");
		return 1;
	}

	srand(time(0));
	pthread_mutex_init(pmux, NULL);

	*n = 0;

	for (int i=0; i<2; ++i) {
		pdata[i].tid = i;
		pdata[i].n = n;
		pdata[i].mux = pmux;

		ret = pthread_create(&thd[i], NULL, fn_ptr[i], (void *)&pdata[i]);
		if (ret != 0) {
			fprintf(stderr, "thd err\n");

			if (i > 0)
				goto kill;

			return 1;
		}
	}

	for (int i=0; i<2; ++i)
		pthread_join(thd[i], NULL);

	freeM(pdata);
	return 0;

kill:
	for (int i=0; i<2; ++i)
		pthread_kill(thd[i], SIGTERM);

	freeM(pdata);
	return 1;
}
