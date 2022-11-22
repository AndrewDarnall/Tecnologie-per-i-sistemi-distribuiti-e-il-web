#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <math.h>
#include <regex.h>

int cubo(int n) {
	return pow(n, 3);
}

void trim(char *s) {
	for (int i=0, j=0, len=strlen(s); i<len; ++i) {
		if (!isdigit(s[i])) {
			s[i] = '\0';

		} else {
			s[j++] = s[i];

			if (j < i)
				s[i] = '\0';
		}
	}
}

int isValid(const char *s) {
	regex_t reg;
	int ret;

	ret = regcomp(&reg, "^[0-9]+$", REG_EXTENDED);
	if (ret) {
		fprintf(stderr, "regex err\n");
		return 0;
	}

	ret = regexec(&reg, s, 0, NULL, 0);

	regfree(&reg);
	return ret != REG_NOMATCH;
}

int main() {
	struct sockaddr_in saddr = {0}, caddr = {0};
	socklen_t slen = 0;
	char buf[20] = {0};
	int sfd, asfd, val, ret;

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	saddr.sin_family = AF_INET;
	saddr.sin_addr.s_addr = INADDR_ANY;
	saddr.sin_port = htons(3333);

	ret = bind(sfd, (struct sockaddr *)&saddr, sizeof(saddr));
	if (ret < 0) {
		fprintf(stderr, "bind err\n");
		goto out;
	}

	listen(sfd, 0);

	while (1) {
		asfd = accept(sfd, (struct sockaddr *)&caddr, &slen);
		if (asfd < 0)
			continue;

		memset(&buf, 0, sizeof(char)*20);

		ret = read(asfd, buf, 20);
		if (ret < 0)
			break;

		buf[19] = '\0';

		trim(buf);

		ret = isValid(buf);
		if (!ret) {
			write(asfd, "invalid input\n", strlen("invalid input\n"));
			continue;
		}

		val = cubo(atoi(buf));

		snprintf(buf, sizeof(char)*20, "result: %d\n", val);
		write(asfd, buf, 20);

		close(asfd);
	}

out:
	close(sfd);
	return 0;
}
