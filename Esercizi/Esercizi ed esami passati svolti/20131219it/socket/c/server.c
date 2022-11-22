#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define QLEN 10

void trim(char *str) {
	for (int i=0, j=0, len=strlen(str); i<len; ++i) {
		if (!isalnum(str[i])) {
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

int main() {
	struct sockaddr_in saddr = {0}, caddr = {0};
	socklen_t slen = 0;
	int sfd, asfd, ret;
	char queue[QLEN][QLEN] = {0};
	char buf[20] = {0};

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		goto out;
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

			if (ret == 2)
				write(asfd, "presente\n", strlen("presente\n"));
			else if (ret == 0)
				write(asfd, "inserita\n", strlen("inserita\n"));
		}

		close(asfd);
	}

out:
	close(sfd);
	return 0;
}
