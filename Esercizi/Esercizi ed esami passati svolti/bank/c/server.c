#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <regex.h>

void trim(char *s) {
	for (int i=0, j=0, l=strlen(s); i<l; ++i) {
		if (!isalnum(s[i]) && s[i] != '[' && s[i] != ']') {
			s[i] = '\0';

		} else {
			s[j++] = s[i];

			if (j < i)
				s[i] = '\0';
		}
	}
}

int isValid(const char *cmd) {
	regex_t reg;
	int ret;
	
	ret = regcomp(&reg, "^[[]S[]]$|^[[]U[0-9][]]$|^[[](V|P)[0-9]{4}[]]$", REG_EXTENDED);
	if (ret) {
		fprintf(stderr, "reg err\n");
		return 0;
	}

	ret = regexec(&reg, cmd, 0, NULL, 0);
	
	regfree(&reg);
	return ret != REG_NOMATCH;
}

char *handleReq(const char *req, int *cidx, int conto[]) {
	char cmd = req[1];
	char buf[20] = {0};
	char *ret = NULL;
	size_t l = 0;

	if (*cidx == -1 && cmd != 'U')
		goto err;

	switch (cmd) {
		case 'U': {
			int idx;

			sscanf(req, "[U%d]", &idx);
			if (idx > 9)
				goto err;

			*cidx = idx;

			snprintf(buf, sizeof(buf), "Sel conto %d\n", idx);

			l = strlen(buf)+1;
			ret = calloc(1, l);

			strncpy(ret, buf, l);
		}
			break;
		case 'V': {
			int q;

			sscanf(req, "[V%d]", &q);
			conto[*cidx] += q;

			snprintf(buf, sizeof(buf), "Verso [%d]: %d\n", *cidx, q);

			l = strlen(buf)+1;
			ret = calloc(1, l);

			strncpy(ret, buf, l);
		}
			break;
		case 'P': {
			int q;

			sscanf(req, "[P%d]", &q);
			if (q > conto[*cidx])
				goto err;

			conto[*cidx] -= q;
			
			snprintf(buf, sizeof(buf), "Prendo [%d]: %d\n", *cidx, q);
			
			l = strlen(buf)+1;
			ret = calloc(1, l);

			strncpy(ret, buf, l);
		}
			break;
		case 'S': {
			snprintf(buf, sizeof(buf), "Saldo: %d\n", conto[*cidx]);

			l = strlen(buf)+1;
			ret = calloc(1, l);

			strncpy(ret, buf, l);
		}
			break;
		default:
			goto err; // :D
			break;
	}

	return ret;
err:
	l = strlen("ERROR")+1;
	ret = calloc(1, l);

	strncpy(ret, "ERROR", l);
	return ret;
}

int main() {
	struct sockaddr_in sadr = {0}, cadr = {0};
	int conto[10] = {0};
	char buf[20] = {0};
	socklen_t slen = 0;
	int sfd, asfd, ret;
	int curConto = -1;

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}
	
	sadr.sin_family = AF_INET;
	sadr.sin_addr.s_addr = INADDR_ANY;
	sadr.sin_port = htons(7777);

	ret = bind(sfd, (struct sockaddr *)&sadr, sizeof(sadr));
	if (ret < 0) {
		fprintf(stderr, "bind err\n");
		goto out;
	}

	listen(sfd, 0);

	while (1) {
		char *res;

		asfd = accept(sfd, (struct sockaddr *)&cadr, &slen);
		if (asfd < 0)
			break;

		memset(&buf, 0, 20);

		ret = read(asfd, buf, 20);
		if (ret < 0)
			break;
		
		buf[9] = '\0';

		trim(buf);

		if (!isValid(buf)) {
			snprintf(buf, sizeof(char)*20, "invalid input\n");
			fprintf(stderr, "invalid input\n");
			write(asfd, buf, strlen(buf));
			close(asfd);
			continue;
		}

		res = handleReq(buf, &curConto, conto);

		if (res != NULL) {
			ret = write(asfd, res, strlen(res));
			if (ret < 0) {
				free(res);
				break;
			}
		}

		free(res);
		close(asfd);

		res = NULL;
	}
out:
	close(sfd);
	return 0;
}
