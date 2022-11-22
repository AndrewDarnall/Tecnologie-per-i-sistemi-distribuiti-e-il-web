#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <regex.h>
#include <stdlib.h>

void trim(char *s) {
	for (int i=0, j=0, l=strlen(s); i<l; ++i) {
		if (!isalnum(s[i]) && s[i] != '+' && s[i] != '-') {
			s[i] = '\0';

		} else {
			s[j++] = s[i];

			if (j < i)
				s[i] = '\0';
		}
	}
}

int isValid(const char *s) {
	int ret = REG_NOMATCH;
	regex_t reg;

	ret = regcomp(&reg, "^[\\+\\-][0-9]{4}$", REG_EXTENDED);
	if (ret) {
		fprintf(stderr, "regex err\n");
		return 0;
	}

	ret = regexec(&reg, s, 0, NULL, 0);

	regfree(&reg);
	return ret != REG_NOMATCH;
}

int decodeAndCompute(const char *s) {
	int n = s[1] - '0';

	switch (s[0]) {
		case '+':
			for (int i=2; i<5; ++i)
				n += s[i] - '0';
			break;
		case '-':
			for (int i=2; i<5; ++i)
				n -= s[i] - '0';
			break;
		default:
			break;
	}

	return n;
}

int main() {
	struct sockaddr_in sadr = {0}, cadr = {0};
	socklen_t slen;
	char buf[10] = {0};
	int sfd, asfd, ret;

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	sadr.sin_family = AF_INET;
	sadr.sin_addr.s_addr = INADDR_ANY;
	sadr.sin_port = htons(4242);

	ret = bind(sfd, (struct sockaddr *)&sadr, sizeof(sadr));
	if (ret < 0) {
		fprintf(stderr, "bind err\n");
		return 1;
	}

	listen(sfd, 0);

	while (1) {
		asfd = accept(sfd, (struct sockaddr *)&cadr, &slen);
		if (asfd < 0)
			continue;

		memset(&buf, 0, sizeof(char)*10);

		ret = read(asfd, buf, 10);
		if (ret < 0)
			break;

		buf[9] = '\0';

		trim(buf);

		if (!isValid(buf)) {
			write(asfd, "inv inpt\n", strlen("inv inpt\n"));
			continue;
			close(asfd);
		}

		snprintf(buf, sizeof(char)*10, "%d\n", decodeAndCompute(buf));
		write(asfd, buf, strlen(buf));
		close(asfd);
	}

	return 0;
}
