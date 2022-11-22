#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
	if (argc < 2) {
		fprintf(stderr, "%s [string]\n", argv[0]);
		return 1;
	}

	struct sockaddr_in addr = {0};
	int sockfd = socket(AF_INET, SOCK_STREAM, 0);
	socklen_t sockL;
	char buf[256] = {0};
	int ret;

	if (sockfd < 0) {
		fprintf(stderr, "sock err\n");
		goto out;
	}

	addr.sin_family = AF_INET;
	addr.sin_addr.s_addr = INADDR_ANY;
	addr.sin_port = htons(3333);

	ret = connect(sockfd, (struct sockaddr *) &addr, sizeof(addr));
	if (ret < 0) {
		fprintf(stderr, "connect err\n");
		goto out;
	}

	ret = write(sockfd, argv[1], sizeof(char)*strlen(argv[1]));
	if (ret < 0) {
		fprintf(stderr, "write err\n");
		goto out;
	}

	ret = read(sockfd, buf, sizeof(buf));
	if (ret < 0) {
		fprintf(stderr, "read err\n");
		goto out;
	}

	printf("risultato: %s\n", buf);

out:
	close(sockfd);
	return 0;
}
