#include <stdio.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include <regex.h>
#include <math.h>

#define LISTLEN 5

typedef struct animal {
	char *name;
	int *totV;
	int v;
} animal_t;

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

	ret = regcomp(&reg, "^[[]G[]]$|^[[]v[0-9]+[]]$", REG_EXTENDED);
	if (ret != 0) {
		return 0;
	}

	ret =  regexec(&reg, cmd, 0, NULL, 0);

	regfree(&reg);
	return ret != REG_NOMATCH;
}

char *handleReq(const char *req, animal_t *data) {
	size_t len = sizeof(char)*100;
	char *ret = calloc(1, len);
	char cmd = req[1];

	if (ret == NULL)
		return ret;

	switch (cmd) {
		case 'G': {
			for (int i=0; i<LISTLEN; ++i) {
				char t[100] = {0};

				snprintf(t, len, "%s%s,", ret, data[i].name);
				strncpy(ret, t, len);
			}

			ret[strlen(ret)-1] = '\0'; // last comma
		}
			break;
		case 'v': {
			int idx;

			sscanf(req, "[v%d]", &idx);
			
			if (idx >= LISTLEN) {
				strncpy(ret, "ERROR", strlen("ERROR"));
				break;
			}

			++data[idx].v;
			++(*data[idx].totV);

			for (int i=0; i<LISTLEN; ++i) {
				char t[100] = {0};
				float p = (float)data[i].v / (*data[i].totV) * 100;
				
				snprintf(t, len, "%s%s:%.0f%%,", ret, data[i].name, floorf(p));
				strncpy(ret, t, len);
			}

			ret[strlen(ret)-1] = '\0'; // last comma
		}
			break;
		default: {
			strncpy(ret, "ERROR", strlen("ERROR"));
		}
			break;
	}
	
	return ret;
}

void freeM(animal_t *alist) {
	for (int i=0; i<LISTLEN; ++i)
		free(alist[i].name);

	free(alist[0].totV);
	free(alist);
}

int main() {
	const char *names[] = {
		"cane", "gatto", "paperino", "pluto", "pikachu"
	};
	struct sockaddr_in saddr = {0}, caddr = {0};
	animal_t *alist; 
	socklen_t sLen = 0;
	char buf[100] = {0};
	int sfd, asfd, ret;
	int *totV;
	char *reqRet = NULL;

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	totV = calloc(1, sizeof(int));
	alist = malloc(sizeof(animal_t)*LISTLEN);

	if (alist == NULL || totV == NULL) {
		fprintf(stderr, "alloc err\n");
		free(totV);
		free(alist);
		return 1;
	}

	for (int i=0; i<LISTLEN; ++i) {
		size_t len = sizeof(char)*strlen(names[i])+1;
		alist[i].name = malloc(len);
		alist[i].totV = totV;
		alist[i].v = 0;

		snprintf(alist[i].name, len, "%s", names[i]);
	}

	saddr.sin_family = AF_INET;
	saddr.sin_addr.s_addr = INADDR_ANY;
	saddr.sin_port = htons(7777);

	ret = bind(sfd, (struct sockaddr *)&saddr, sizeof(saddr));
	if (ret < 0) {
		fprintf(stderr, "bind err\n");
		goto out;
	}

	listen(sfd, 0);

	while (1) {
		asfd = accept(sfd, (struct sockaddr *)&caddr, &sLen);
		if (asfd < 0)
			break;

		memset(&buf, 0, 100);

		ret = read(asfd, buf, 100);
		if (ret < 0)
			break;

		buf[99] = '\0';
		
		trim(buf);

		if (!isValid(buf)) {
			snprintf(buf, sizeof(char)*100, "invalid input\n");
			write(asfd, buf, strlen(buf));
			fprintf(stderr, "invalid input\n");
			continue;
		}

		reqRet = handleReq(buf, alist);
		ret = write(asfd, reqRet, strlen(reqRet));

		if (ret < 0) {
			fprintf(stderr, "write err\n");
			free(reqRet);
			break;
		}

		free(reqRet);
		close(asfd);
	}

out:
	close(sfd);
	freeM(alist);
	return 0;
}
