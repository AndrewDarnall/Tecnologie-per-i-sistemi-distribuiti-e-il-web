#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <signal.h>
#include <time.h>
#include <unistd.h>

typedef struct pdata {
	int *x;
	int tid;
	pthread_mutex_t *mux;
} pdata_t;

void *incX(void *pdata) {
	pdata_t *pd = (pdata_t *)pdata;
	int c = 0;

	printf("thread %d start, x=%d\n", pd->tid, *(pd->x));

	while (*(pd->x) < 300) {
		pthread_mutex_lock(pd->mux);
		++(*(pd->x));
		++c;
		pthread_mutex_unlock(pd->mux);
		usleep(random() % (50*1000));
	}

	printf("thread %d done, x=%d cnt: %d\n", pd->tid, *(pd->x), c);
	pthread_exit(NULL);
}

void freeM(pdata_t *data) {
	pthread_mutex_destroy(data->mux);
	free(data[0].x);
	free(data);
}

int main() {
	pdata_t *data = malloc(sizeof(pdata_t)*2);
	pthread_mutex_t *pmux = malloc(sizeof(pthread_mutex_t));
	int *x = malloc(sizeof(int));
	pthread_t thd[2];
	int ret;

	if (data == NULL || x == NULL || pmux == NULL) {
		fprintf(stderr, "alloc err\n");
		return 1;
	}

	srandom(time(0));
	pthread_mutex_init(pmux, NULL);

	*x = 0;

	for (int i=0; i<2; ++i) {
		data[i].x = x;
		data[i].mux = pmux;
		data[i].tid = i;

		thd[i] = i;

		ret = pthread_create(&thd[i], NULL, &incX, (void *)&data[i]);
		if (ret != 0) {
			fprintf(stderr, "thd err\n");
			if (i > 0)
				goto kill;

			break;
		}
	}

	for (int i=0; i<2; ++i)
		pthread_join(thd[i], NULL);

	freeM(data);
	return 0;

kill:
	for (int i=0; i<2; ++i)
		pthread_kill(thd[i], SIGTERM);

	freeM(data);
	return 1;
}
