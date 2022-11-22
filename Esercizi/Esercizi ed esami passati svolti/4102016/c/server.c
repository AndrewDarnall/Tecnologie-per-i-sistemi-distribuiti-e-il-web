#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>
#include <regex.h>
#include <ctype.h>
#include <stdlib.h>

char *trim(char *str) {
	for (int i=0, j=0, len=strlen(str); i<len; ++i) {
		if (!isalpha(str[i])) {
			str[i] = '\0';
		} else {
			str[j++] = str[i];

			if (j < i)
				str[i] = '\0';
		}
	}

	return str;
}

int isValidStr(const char *str) {
	int res = REG_NOMATCH;
	regex_t reg;
	int ret;

	ret = regcomp(&reg, "^[VF]+$", REG_EXTENDED);
	if (ret) {
		fprintf(stderr, "regex err %d\n", ret);
		goto out;
	}

	res = regexec(&reg, str, 0, NULL, 0);

out:
	regfree(&reg);
	return res != REG_NOMATCH;
}

char *and(const char *str) {
	size_t sz = sizeof(char) * strlen("falso") + 1;
	char *ret = malloc(sz);
	const char *it = str;
	int cnt = 0;

	if (ret == NULL)
		return ret;

	while (*it) {
		if (*it++ == 'V')
			++cnt;
	}

	snprintf(ret, sz, (cnt == strlen(str) ? "Vero":"Falso"));
	return ret;
}

int main() {
	struct sockaddr_in saddr = {0}, caddr = {0};
	char buf[256] = {0};
	socklen_t asockL = 0;
	int sockfd, asockfd, ret;

	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sockfd < 0) {
		fprintf(stderr, "sock err\n");
		goto out;
	}

	saddr.sin_family = AF_INET;
	saddr.sin_addr.s_addr = INADDR_ANY;
	saddr.sin_port = htons(3333);

	ret = bind(sockfd, (struct sockaddr *) &saddr, sizeof(saddr));
	if (ret < 0) {
		fprintf(stderr, "bind err\n");
		goto out;
	}

	listen(sockfd, 0);

	while (1) {
		asockfd = accept(sockfd, (struct sockaddr *) &caddr, &asockL);
		if (asockfd < 0)
			continue;

		memset(&buf, 0, sizeof(char)*256);

		ret = read(asockfd, buf, 256);
		if (ret < 0)
			break;

		buf[255] = '\0';

		trim(buf);

		if (isValidStr(buf)) {
			printf("ricevuto: %s\n", buf);

			char *re = and(buf);

			if (re == NULL)
				fprintf(stderr, "alloc err\n");
			else
				write(asockfd, re, strlen(re));

			free(re);

		} else {
			fprintf(stderr, "invalid input\n");
			write(asockfd, "invalid", strlen("invalid"));
		}
	}

	close(asockfd);

out:
	close(sockfd);
	return 0;
}
