#include <time.h>

#include "IDL.h"

typedef struct item {
	int id;
	int n;
} item_t;

int initItemsList(item_t **list, int n) {
	item_t *t = malloc(sizeof(item_t)*10);

	if (t == NULL)
		return 2;

	srand(time(0));

	printf("Magazzino:\n");

	for (int i=0; i<n; ++i) {
		t[i].id = i;
		t[i].n = rand() % 30;

		printf("item %d: %d\n", i, t[i].n);
	}

	*list = t;

	return 0;
}

int *isitemqtaavail_20_svc(char **argp, struct svc_req *rqstp) {
	static item_t *items = NULL; // bad :s ?
	static int result = 0;
	int id, n;

	if (items == NULL) {
		result = initItemsList(&items, 10);
		if (result == 2)
			goto out;
	}

	sscanf(*argp, "%d:%d", &id, &n);
	
	result = 0;
	for (int i=0; i<10; ++i) {
		if (items[i].id == id && items[i].n >= n) {
			result = 1;
			break;
		}
	}

out:
	return &result;
}
