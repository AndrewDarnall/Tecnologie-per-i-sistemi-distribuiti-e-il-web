#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#include "IDL.h"

#define QLEN 10

void str_len_10(char *host, char *str, int *ret) {
    CLIENT *clnt;
    int  *result_1;
	char * conta_10_arg = str;

#ifndef DEBUG
    clnt = clnt_create (host, STR_LEN, STR_LEN_VER, "udp");
    if (clnt == NULL) {
        clnt_pcreateerror (host);
        exit (1);
    }
#endif  /* DEBUG */

    result_1 = conta_10(&conta_10_arg, clnt);
    if (result_1 == (int *) NULL) {
        clnt_perror (clnt, "call failed");
    }

	*ret = *result_1;

#ifndef DEBUG
    clnt_destroy (clnt);
#endif   /* DEBUG */
}

void trim(char *str) {
	for (int i=0, j=0, len=strlen(str); i<len; ++i) {
		if (!isalpha(str[i]) && !isdigit(str[i])) {
			str[i] = '\0';

		} else {
			str[j++] = str[i];

			if (j < i)
				str[i] = '\0';
		}
	}
}

int list(int sfd, char q[][QLEN]) {
	int ret = 0;

	for (int i=0; i<QLEN; ++i) {
		if (!q[i][0])
			continue;

		char str[12] = {0};

		snprintf(str, sizeof(str), "%s\n", q[i]);
		ret = write(sfd, str, strlen(str));

		if (ret < 0)
			return -1;
	}

	return ret;
}

int exist(char q[][QLEN], const char *s) {
	for (int i=0; i<QLEN; ++i) {
		if (strncmp(q[i], s, strlen(s)) == 0)
			return 1;
	}

	return 0;
}

int add_req(char q[][QLEN], const char *req) {
	int i=0;

	if (exist(q, req))
		return 2;

	while (i < QLEN && q[i][0])
		++i;

	if (i == QLEN)
		i = random() % 10;

	snprintf(q[i], sizeof(char)*QLEN, "%s", req);
	return 0;
}

int main(int argc, char *argv[]) {
	struct sockaddr_in saddr = {0}, caddr = {0};
	socklen_t slen = 0;
	int sfd, asfd, ret;
	char queue[QLEN][QLEN] = {0};
	char buf[20] = {0};
	char *host = NULL;

	if (argc < 2) {
        printf ("usage: %s server_host\n", argv[0]);
        return 1;
    }

	host = argv[1];

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	saddr.sin_family = AF_INET;
	saddr.sin_addr.s_addr = INADDR_ANY;
	saddr.sin_port = htons(7777);

	ret = bind(sfd, (struct sockaddr *) &saddr, sizeof(saddr));
	if (ret < 0) {
		fprintf(stderr, "bind err\n");
		goto out;
	}

	listen(sfd, 0);

	while (1) {
		asfd = accept(sfd, (struct sockaddr*) &caddr, &slen);
		if (asfd < 0)
			break;

		memset(&buf, 0, 19);

		ret = read(asfd, buf, 20);
		if (ret < 0)
			break;

		buf[19] = '\0';

		trim(buf);

		if (strncmp(buf, "LIST", strlen("LIST")) == 0) {
			list(asfd, queue);

		} else {
			ret = add_req(queue, buf);

			if (ret == 2) {
				char buf2[100] = {0};

			    str_len_10 (host, buf, &ret);
				snprintf(buf2, sizeof(buf2), "dati presenti, %d caratteri\n", ret);
				write(asfd, buf2, strlen(buf2));

			} else if (ret == 0) {
				write(asfd, "inserita\n", strlen("inserita\n"));
			}
		}

		close(asfd);
	}

out:
	close(sfd);
	return 0;
}
