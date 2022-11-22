#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <regex.h>
#include <ctype.h>

#include "IDL.h"

void magaz_rpc_20(char *host, char *req, int *ret) {
	CLIENT *clnt;
	int  *result_1;
	char * isitemqtaavail_20_arg = req;

#ifndef	DEBUG
	clnt = clnt_create (host, MAGAZ_RPC, MAGZ_VER, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */

	result_1 = isitemqtaavail_20(&isitemqtaavail_20_arg, clnt);
	if (result_1 == (int *) NULL)
		clnt_perror (clnt, "call failed");
	
	*ret = *result_1;

#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}

void trim(char *s) {
	for (int i=0, j=0, l=strlen(s); i<l; ++i) {
		if (!isdigit(s[i]) && s[i] != ':') {
			s[i] = '\0';

		} else {
			s[j++] = s[i];

			if (j < i)
				s[i] = '\0';
		}
	}
}

int isValid(const char *req) {
	regex_t reg;
	int ret;

	ret = regcomp(&reg, "^[0-9]+:[0-9]+$", REG_EXTENDED);
	if (ret) {
		fprintf(stderr, "reg err\n");
		return 0;
	}

	ret = regexec(&reg, req, 0, NULL, 0);

	regfree(&reg);
	return ret != REG_NOMATCH;
}

char *handleReq(const char *req, char *host) {
	size_t len = sizeof(char)*10;
	char *ret = malloc(len);
	int found = 0;

	if (ret == NULL)
		return ret;

	magaz_rpc_20(host, req, &found);
	
	snprintf(ret, len, (found ? "True":"False"));
	return ret;
}

int main(int argc, char *argv[]) {
	struct sockaddr_in sadr = {0}, cadr = {0};
	socklen_t slen = 0;
	char buf[50] = {0};
	int sfd, asfd, ret;
	char *res = NULL;

	if (argc < 2) {
		printf ("usage: %s server_host\n", argv[0]);
		exit (1);
	}

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	sadr.sin_family = AF_INET;
	sadr.sin_addr.s_addr = INADDR_ANY;
	sadr.sin_port = htons(39999);

	ret = bind(sfd, (struct sockaddr *)&sadr, sizeof(sadr));
	if (ret < 0) {
		fprintf(stderr, "bind err\n");
		return 1;
	}

	listen(sfd, 0);

	while (1) {
		asfd = accept(sfd, (struct sockaddr *)&cadr, &slen);
		if (asfd < 0)
			goto out;

		memset(buf, 0, 50);

		ret = read(asfd, buf, 50);
		if (ret < 0)
			goto out;

		trim(buf);

		if (!isValid(buf)) {
			fprintf(stderr, "invalid input\n");
			write(asfd, "invalid input", strlen("invalid input"));
			close(asfd);
			continue;
		}

		res = handleReq(buf, argv[1]);

		ret = write(asfd, res, strlen(res));
		if (ret < 0)
			goto out;

		close(asfd);
		free(res);

		res = NULL;
	}

out:
	close(sfd);
	free(res);
	return 0;
}
