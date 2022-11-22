#include <stdio.h>
#include <pthread.h>
#include <time.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

typedef struct pdata {
	pthread_mutex_t *mux;
	pthread_mutex_t *cmux;
	pthread_cond_t *wtp0;
	pthread_cond_t *wtp1;
	int *pos;
	int *wintp0;
	int *wintp1;
	int rounds;
} pdata_t;

void *tp0(void *data) {
	pdata_t *pd = (pdata_t *)data;

	while (1) {
		int rec = rand() % 3;
		int str = rand() % 5;

		sleep(rec);

		if (*(pd->pos) >= 10) {
			++(*pd->wintp1);

			pthread_mutex_lock(pd->mux);

			*(pd->pos) = 0;
			
			pthread_mutex_unlock(pd->mux);
			pthread_cond_signal(pd->wtp0);

		} else {
			if (*(pd->wintp0) >= pd->rounds || *(pd->wintp1) >= pd->rounds) {
				printf("tp0 exit %s\n", (*(pd->wintp0) >= pd->rounds ? ":D":";("));
				pthread_cond_signal(pd->wtp0);
				break;
			}

			pthread_mutex_lock(pd->mux);

			*(pd->pos) -= str;
			
			pthread_mutex_unlock(pd->mux);

			if (*(pd->pos) <= -10) {
				printf("tp0 wins\n");
				pthread_cond_wait(pd->wtp1, pd->cmux);
			}
		}
	}

	return NULL;
}

void *tp1(void *data) {
	pdata_t *pd = (pdata_t *)data;

	while (1) {
		int rec = rand() % 3;
		int str = rand() % 5;

		sleep(rec);

		if (*(pd->pos) <= -10) {
			++(*pd->wintp0);

			pthread_mutex_lock(pd->mux);

			*(pd->pos) = 0;
			
			pthread_mutex_unlock(pd->mux);
			pthread_cond_signal(pd->wtp1);

		} else {
			if (*(pd->wintp0) >= pd->rounds || *(pd->wintp1) >= pd->rounds) {
				printf("tp1 exit %s\n", (*(pd->wintp0) >= pd->rounds ? ";(":":D"));
				pthread_cond_signal(pd->wtp1);
				break;
			}

			pthread_mutex_lock(pd->mux);

			*(pd->pos) += str;
			
			pthread_mutex_unlock(pd->mux);

			if (*(pd->pos) >= 10) {
				printf("tp1 wins\n");
				pthread_cond_wait(pd->wtp0, pd->cmux);
			}
		}
	}

	return NULL;
}

void killThds(int lim, const pthread_t thds[2]) {
	for (int i=0; i<lim; ++i)
		pthread_kill(thds[i], SIGTERM);
}

int main() {
	pthread_mutex_t pmux = PTHREAD_MUTEX_INITIALIZER;
	pthread_mutex_t cpmux = PTHREAD_MUTEX_INITIALIZER;
	pthread_cond_t wtp0 = PTHREAD_COND_INITIALIZER;
	pthread_cond_t wtp1 = PTHREAD_COND_INITIALIZER;
	pdata_t *data = malloc(sizeof(pdata_t)*2);
	void *(*fnPtr[2])(void *) = {tp0, tp1};
	int pos = 0, win0 = 0, win1 = 0, ret;
	pthread_t thds[2] = {0};

	if (data == NULL) {
		fprintf(stderr, "alloc err\n");
		return 1;
	}

	srand(time(0));

	for (int i=0; i<2; ++i) {
		data[i].mux = &pmux;
		data[i].cmux = &cpmux;
		data[i].wtp0 = &wtp0;
		data[i].wtp1 = &wtp1;
		data[i].pos = &pos;
		data[i].wintp0 = &win0;
		data[i].wintp1 = &win1;
		data[i].rounds = 2;

		ret = pthread_create(&thds[i], NULL, fnPtr[i], &data[i]);
		if (ret != 0) {
			fprintf(stderr, "thds err\n");
			killThds(i, thds);
			goto out;
		}
	}

	for (int i=0; i<2; ++i)
		pthread_join(thds[i], NULL);

	printf("\n\nThe winner is: tp%d, [tp0 %d - tp1 %d]\n\n", (win0 > win1 ? 0:1), win0, win1);

out:
	free(data);
	pthread_mutex_destroy(&pmux);
	pthread_mutex_destroy(&cpmux);
	pthread_cond_destroy(&wtp0);
	pthread_cond_destroy(&wtp1);
	return 0;
}
