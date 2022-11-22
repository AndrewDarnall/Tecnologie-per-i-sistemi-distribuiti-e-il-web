#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>
#include <time.h>

typedef struct invec {
	struct in_addr *vec;
	int sz;
	int cur;
} invec_t;

void trim(char *s) {
	for (int i=0, j=0, l=strlen(s); i<l; ++i) {
		if (!isalpha(s[i])) {
			s[i] = '\0';

		} else {
			s[j++] = s[i];

			if (j < i)
				s[i] = '\0';
		}
	}
}

char *getTime() {
	time_t now = time(0);
	struct tm *locT = localtime(&now);
	size_t len = sizeof(char)*50;
	char *ret = malloc(len);

	if (ret == NULL)
		return ret;

	snprintf(ret, len, "Sono le ore %d e %d minuti", locT->tm_hour, locT->tm_min);

	return ret;
}

char *parseCmd(const char *cmd) {
	char *ret;

	if (strncmp(cmd, "TIME", strlen(cmd)) == 0) {
		ret = getTime();

	} else {
		size_t len = sizeof(char)*10;

		ret = malloc(len);
		if (ret == NULL)
			return ret;

		snprintf(ret, len, "N/A");
	}

	return ret;
}

int tryBan(invec_t *adrList, struct in_addr adr, struct in_addr padr, time_t firstRq) {
	time_t now = time(0);

	if (adr.s_addr != padr.s_addr)
		return 0;

	for (int i=0; i<adrList->sz; ++i) {
		if (adrList->vec[i].s_addr == adr.s_addr)
			return 1;
	}

	if (difftime(now, firstRq) > 4.0f)
		return 0;

	if (adrList->vec == NULL) {
		adrList->vec = malloc(sizeof(struct in_addr));

		if (adrList->vec == NULL)
			return 2;

		adrList->vec[0].s_addr = adr.s_addr;
		adrList->sz = 1;
		adrList->cur = 0;

	} else {
		if ((adrList->cur + 1) == adrList->sz) {
			adrList->sz *= 2;
			adrList->vec = realloc(adrList->vec, sizeof(struct in_addr)*adrList->sz);

			if (adrList->vec == NULL)
				return 2;
		}

		adrList->vec[adrList->cur].s_addr = adr.s_addr;
		++adrList->cur;
	}

	return 1;
}

int main() {
	struct sockaddr_in sadr = {0}, cadr = {0};
	struct in_addr padr = {0}; // prev addr
	invec_t adrList = {0};
	socklen_t slen = 0;
	char buf[50] = {0};
	char *res = NULL;
	int sfd, asfd, ret;
	time_t firstRq = 0;

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	sadr.sin_family = AF_INET;
	sadr.sin_addr.s_addr = INADDR_ANY;
	sadr.sin_port = htons(3233);

	ret = bind(sfd, (struct sockaddr *)&sadr, sizeof(sadr));
	if (ret < 0) {
		fprintf(stderr, "bind err\n");
		goto out;
	}

	listen(sfd, 0);

	while (1) {
		asfd = accept(sfd, (struct sockaddr *)&cadr, &slen);
		res = NULL;

		if (asfd < 0)
			break;

		memset(buf, 0, 50);

		ret = read(asfd, buf, 50);
		if (ret < 0)
			goto out;

		ret = tryBan(&adrList, cadr.sin_addr, padr, firstRq);
		if (ret == 2)
			goto out;

		if (!ret) {
			trim(buf);

			res = parseCmd(buf);

		} else { // ban
			size_t len = sizeof(char)*20;

			res = malloc(len);

			snprintf(res, len, "BANNED");
		}

		ret = write(asfd, res, strlen(res));
		if (ret < 0)
			goto out;

		firstRq = time(0);
		padr.s_addr = cadr.sin_addr.s_addr; // first address after connect may be zero ??(telnet)

		close(asfd);
		free(res);
	}

out:
	close(sfd);
	free(res);
	return 0;
}
