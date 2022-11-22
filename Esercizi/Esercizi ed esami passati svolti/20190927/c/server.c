/**
 * IFDEF usage example :P
 */ 
#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>

#ifdef D
typedef struct book {
	char *title;
	char *cont;
} book_t;

int initBooks(book_t **blist) {
	book_t *b = malloc(sizeof(book_t));

	if (b == NULL)
		return -1;

	for (int i=0; i<5; ++i) {
		size_t len = sizeof(char)*20+1;
		b[i].title = malloc(len);
		b[i].cont = malloc(len);
		
		if (b[i].title == NULL || b[i].cont == NULL)
			return -1;

		snprintf(b[i].title, len, "Book%d", i);
		snprintf(b[i].cont, len, "lalala Book%d :D", i);
	}

	*blist = b;

	return 0;
}

char *inizioFine(const char *s, const book_t *blist, int sz) {
	int found = 0, idx = 0;
	size_t len = sizeof(char)*20+1;
	char *ret = malloc(len);

	if (ret == NULL)
		return ret;

	for (int i=0; i<sz; ++i) {
		if (strncmp(blist[i].title, s, strlen(s)) == 0) {
			found = 1;
			idx = i;
			break;
		}
	}

	snprintf(ret, len, "%s", (found ? blist[idx].cont:"not found"));

	return ret;
}

#elif defined (C)
char *inizioFine(char *s) { return s; }
#endif

void trim(char *s) {
	for (int i=0, j=0, l=strlen(s); i<l; ++i) {
		if (!isalnum(s[i])) {
			s[i] = '\0';

		} else {
			s[j++] = s[i];

			if (j < i)
				s[i] = '\0';
		}
	}
}

int main() {
	struct sockaddr_in sadr = {0}, cadr = {0};
	int sfd, asfd, ret;
	socklen_t slen = 0;
	char buf[50] = {0};
#ifdef D
	book_t *blist = NULL;

	ret = initBooks(&blist);
	if (ret < 0 || blist == NULL) {
		fprintf(stderr, "initBooks err\n");
		return 1;
	}
#endif

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	sadr.sin_family = AF_INET;
	sadr.sin_addr.s_addr = INADDR_ANY;
	sadr.sin_port = htons(9999);

	ret = bind(sfd, (struct sockaddr *)&sadr, sizeof(sadr));
	if (ret < 0)
		goto out;

	listen(sfd, 0);

	while (1) {
		asfd = accept(sfd, (struct sockaddr *)&cadr, &slen);
		if (asfd < 0)
			goto out;

		memset(buf, 0, 50);

		ret = read(asfd, buf, 50);
		if (ret < 0)
			goto out;

		buf[49] = '\0';

		trim(buf);

#if defined (C) || defined (D)
#ifdef C
		char *res = inizioFine(buf);
#else
		char *res = inizioFine(buf, blist, 5);
#endif
		printf("recv: %s\n", buf);
		write(asfd, res, strlen(res));

#else
		printf("recv: %s\n", buf);

#ifdef B
		write(asfd, buf, strlen(buf));
#endif
#endif
		close(asfd);
	}

out:
	fprintf(stderr, "close!");
	close(sfd);
	return 0;
}
