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

#include "IDL.h"

void cubo_rpc_10(char *host, int n, int *ret) {
    CLIENT *clnt;
    int  *result_1;
    int cubo_10_arg = n;

#ifndef DEBUG
    clnt = clnt_create (host, CUBO_RPC, CUBO_VER, "udp");
    if (clnt == NULL) {
        clnt_pcreateerror (host);
        exit (1);
    }
#endif  /* DEBUG */

    result_1 = cubo_10(&cubo_10_arg, clnt);
    if (result_1 == (int *) NULL) {
        clnt_perror (clnt, "call failed");
    }

	*ret = *result_1;

#ifndef DEBUG
    clnt_destroy (clnt);
#endif   /* DEBUG */
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
	int ret = REG_NOMATCH;

	ret = regcomp(&reg, "^[0-9]+$", REG_EXTENDED);
	if (ret) {
		fprintf(stderr, "regex err\n");
		return 0;
	}

	ret = regexec(&reg, s, 0, NULL, 0);

	regfree(&reg);
	return ret != REG_NOMATCH;
}

int main(int argc, char *argv[]) {
	struct sockaddr_in saddr = {0}, caddr = {0};
	socklen_t slen = 0;
	char buf[20] = {0};
	int sfd, asfd, val, ret;
	char *host;

	if (argc < 2) {
        printf ("usage: %s server_host\n", argv[0]);
        return 1;
    }

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	host = argv[1];
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

		cubo_rpc_10 (host, atoi(buf), &val);

		snprintf(buf, sizeof(char)*20, "result: %d\n", val);
		write(asfd, buf, 20);

		close(asfd);
	}

out:
	close(sfd);
	return 0;
}
